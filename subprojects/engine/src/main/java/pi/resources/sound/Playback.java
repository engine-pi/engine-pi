/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/sound/SoundPlayback.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pi.resources.sound;

import java.util.Collection;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;

/**
 * The {@code SoundPlayback} class is a wrapper {@code SourceDataLine} on which
 * a {@code Sound} playback can be carried out.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 *
 * @see #play(Sound)
 */
public abstract class Playback implements Runnable
{
    protected final SourceDataLine line;

    private final FloatControl gainControl;

    private final BooleanControl muteControl;

    private boolean started = false;

    private volatile boolean cancelled = false;

    private final Collection<PlaybackListener> listeners = ConcurrentHashMap
        .newKeySet();

    private final Collection<VolumeControl> volumeControls = Collections
        .synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));

    private final VolumeControl masterVolume;

    private final AtomicInteger miscVolume = new AtomicInteger(0x3f800000); // floatToIntBits(1f)

    Playback(AudioFormat format) throws LineUnavailableException
    {
        // acquire resources in the constructor so that they can be used before
        // the
        // task is started
        line = AudioSystem.getSourceDataLine(format);
        line.open();
        line.start();
        gainControl = (FloatControl) line
            .getControl(FloatControl.Type.MASTER_GAIN);
        muteControl = (BooleanControl) line
            .getControl(BooleanControl.Type.MUTE);
        masterVolume = createVolumeControl();
    }

    /**
     * Starts playing the audio.
     *
     * @throws IllegalStateException if the audio has already been started
     */
    public synchronized void start()
    {
        if (started)
        {
            throw new IllegalStateException("already started");
        }
        play();
        started = true;
    }

    /**
     * Adds a {@code SoundPlaybackListener} to this instance.
     *
     * @param listener The {@code SoundPlaybackListener} to be added.
     */
    public void addPlaybackListener(PlaybackListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes a {@code SoundPlaybackListener} from this instance.
     *
     * @param listener The {@code SoundPlaybackListener} to be removed.
     */
    public void removePlaybackListener(PlaybackListener listener)
    {
        listeners.remove(listener);
    }

    /**
     * Sets the paused state of this playback to the provided value.
     *
     * @param paused Whether to pause or resume this playback
     */
    @Setter
    public void paused(boolean paused)
    {
        if (paused)
        {
            pause();
        }
        else
        {
            resume();
        }
    }

    /**
     * Pauses this playback. If this playback is already paused, this call has
     * no effect.
     */
    public void pause()
    {
        if (line.isOpen())
        {
            line.stop();
        }
    }

    /**
     * Resumes this playback. If this playback is already playing, this call has
     * no effect.
     */
    public void resume()
    {
        if (line.isOpen())
        {
            line.start();
        }
    }

    /**
     * Determines if this playback is paused.
     *
     * @return Whether this playback is paused
     */
    public boolean isPaused()
    {
        return !line.isActive();
    }

    /**
     * Determines if this playback has sound to play. If it is paused but still
     * in the middle of playback, it will return {@code true}, but it will
     * return {@code false} if it has finished or it has been cancelled.
     *
     * @return Whether this playback has sound to play
     */
    public boolean isPlaying()
    {
        return line.isOpen();
    }

    /**
     * Attempts to cancel the playback of this audio. If the playback was
     * successfully cancelled, it will notify listeners.
     */
    public synchronized void cancel()
    {
        if (!started)
        {
            throw new IllegalStateException("not started");
        }
        if (!cancelled && line.isOpen())
        {
            line.stop();
            cancelled = true;
            line.flush();
            SoundEvent event = new SoundEvent(this, null);
            for (PlaybackListener listener : listeners)
            {
                listener.cancelled(event);
            }
        }
    }

    /**
     * Gets the current volume of this playback, considering all
     * {@code VolumeControl} objects created for it.
     *
     * @return The current volume.
     */
    @Getter
    public double masterVolume()
    {
        if (muteControl.getValue())
        {
            return 0;
        }
        return Math.pow(10.0, gainControl.getValue() / 20.0);
    }

    /**
     * Gets the current master volume of this playback. This will be
     * approximately equal to the value set by a previous call to
     * {@code setVolume}, though rounding errors may occur.
     *
     * @return The settable volume.
     */
    @Getter
    public double volume()
    {
        return masterVolume.get();
    }

    /**
     * Sets the master volume of this playback.
     *
     * @param volume The new volume.
     */
    @Setter
    public void volume(double volume)
    {
        masterVolume.set((float) volume);
    }

    public VolumeControl createVolumeControl()
    {
        VolumeControl control = new VolumeControl();
        volumeControls.add(control);
        return control;
    }

    public Collection<VolumeControl> getVolumeControls()
    {
        return volumeControls;
    }

    void play()
    {
        AudioEngine.getInstance().executor.submit(this);
    }

    /**
     * Plays a sound to this object's data line.
     *
     * @param sound The sound to play
     *
     * @return Whether the sound was cancelled while playing
     */
    boolean play(Sound sound) throws LineUnavailableException
    {
        line.open();
        line.start();
        byte[] data = sound.streamData();
        int len = line.getFormat().getFrameSize();
        // math hacks here: we're getting just over half the buffer size, but it
        // needs to be an integral
        // number of sample frames
        len = (line.getBufferSize() / len / 2 + 1) * len;
        for (int i = 0; i < data.length; i += line
            .write(data, i, Math.min(len, data.length - i)))
        {
            if (cancelled || !line.isOpen())
            {
                return true;
            }
        }
        return cancelled;
    }

    /**
     * Finishes the playback. If this playback was not cancelled in the process,
     * it will notify listeners.
     */
    void finish()
    {
        line.drain();
        synchronized (this)
        {
            cancel();
            if (!cancelled)
            {
                SoundEvent event = new SoundEvent(this, null);
                for (PlaybackListener listener : listeners)
                {
                    listener.finished(event);
                }
            }
        }
    }

    void updateVolume()
    {
        synchronized (volumeControls)
        {
            float volume = Float.intBitsToFloat(miscVolume.get());
            for (VolumeControl control : volumeControls)
            {
                volume *= control.get();
            }
            float dbGain = (float) (20.0 * Math.log10(volume));
            if (dbGain < gainControl.getMinimum())
            {
                muteControl.setValue(true);
            }
            else
            {
                gainControl.setValue(dbGain);
                muteControl.setValue(false);
            }
        }
    }

    protected ToStringFormatter toStringFormatter(Object object)
    {
        var formatter = new ToStringFormatter(object);
        formatter.append("started", started);
        formatter.append("canceled", cancelled);
        return formatter;
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        return toStringFormatter(this).format();
    }

    /**
     * An object for controlling the volume of a {@code SoundPlayback}. Each
     * distinct instance represents an independent factor contributing to its
     * volume.
     *
     * @see Playback#createVolumeControl()
     */
    public class VolumeControl implements AutoCloseable
    {
        private volatile float value = 1f;

        private VolumeControl()
        {
        }

        /**
         * Gets the value of this volume control.
         *
         * @return The value of this control.
         */
        public float get()
        {
            return value;
        }

        /**
         * Sets the value of this volume control.
         *
         * @param value The value to be set.
         */
        public void set(float value)
        {
            if (value < 0f)
            {
                throw new IllegalArgumentException("negative volume");
            }
            this.value = value;
            Playback.this.updateVolume();
        }

        @Override
        public void close()
        {
            // clean up the instance without affecting the volume
            Playback.this.miscVolume.accumulateAndGet(
                Float.floatToRawIntBits(this.value),
                (a, b) -> Float.floatToRawIntBits(
                    Float.intBitsToFloat(a) * Float.intBitsToFloat(b)));
        }
    }
}
