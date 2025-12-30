#! /bin/sh

# -framerate


    #    -r[:stream_specifier] fps (input/output,per-stream)
    #        Set frame rate (Hz value, fraction or abbreviation).

    #        As  an  input option, ignore any timestamps stored in the file and instead generate timestamps assuming constant frame rate fps.  This is not the same as the -framerate option used for
    #        some input formats like image2 or v4l2 (it used to be the same in older versions of FFmpeg).  If in doubt use -framerate instead of the input option -r.

    #        As an output option:

    #        video encoding
    #            Duplicate or drop frames right before encoding them to achieve constant output frame rate fps.

    #        video streamcopy
    #            Indicate to the muxer that fps is the stream frame rate. No data is dropped or duplicated in this case. This may produce invalid files if fps does not match the actual stream frame
    #            rate as determined by packet timestamps.  See also the "setts" bitstream filter.



    #    -y (global)
    #        Overwrite output files without asking.


ffmpeg \
    -y \
    -pattern_type glob -i '/home/jf/engine-pi/*.png' \
    -c:v libx264 \
    -framerate 30 \
    -pix_fmt yuv420p \
    out.mp4
