/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/dude/DudeDemo.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.pirckheimer_gymnasium.engine_pi_demos.small_games.dude;

import de.pirckheimer_gymnasium.engine_pi.Bounds;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Layer;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Image;
import de.pirckheimer_gymnasium.engine_pi.actor.TileRegistration;
import de.pirckheimer_gymnasium.engine_pi.actor.TileMap;

/**
 * Eine kleine Spieldemo.
 * <p>
 * Vielen Dank an <a href="https://rvros.itch.io/animated-pixel-hero">rvros</a>
 */
public class DudeDemo extends Scene
{
    public static final int WIDTH = 1240;

    public static final int HEIGHT = 812;

    private final GameData gameData;

    private final PlayerCharacter character;
    // private final Rectangle weird;

    public DudeDemo()
    {
        gameData = new GameData();
        addLayer(new HUD(gameData));
        character = new PlayerCharacter(gameData);
        character.setPosition(0, 0);
        character.setRotationLocked(true);
        character.makeDynamic();
        /*
         * weird = new Rectangle(2, 0.2); weird.setColor(Color.ORANGE);
         * weird.makeDynamic(); weird.setPosition(0.3, 0.6);
         *
         * Rectangle weird2 = new Rectangle(2, 0.2);
         * weird2.setColor(Color.GREEN); weird2.makeDynamic();
         * weird2.setPosition(2, 0.6); weird2.createRevoluteJoint(weird, new
         * Vector(0.1, 0.1)); add(weird2);
         *
         * Rectangle boxy = new Rectangle(0.2, 0.2); boxy.setColor(Color.WHITE);
         * boxy.makeDynamic(); boxy.setPosition(3, 0); //boxy.move(10, 10);
         */
        add(character);
        // add(weird);
        // add(boxy);
        // character.createRevoluteJoint(weird, new Vector(0.4, 0.7));
        // character.createRopeJoint(boxy, new Vector(0.3, 0.3), new
        // Vector(0.1, 0.1), 4);
        // character.createDistanceJoint(boxy, new Vector(0.3, 0.3), new
        // Vector(0.1, 0.1));
        setGravity(new Vector(0, -13));
        setFocus(character);
        getCamera().setOffset(new Vector(0, 3));
        getCamera().setBounds(new Bounds(-2000, -3, 20000, 20000));
        getCamera().setMeter(30);
        setupPlayground();
        setupCosmeticLayers();
        // getMainLayer().setVisibleHeight(15, Game.getFrameSizeInPixels());
        PauseLayer pauseLayer = new PauseLayer();
        pauseLayer.setVisible(false);
        addLayer(pauseLayer);
    }

    private void setupPlayground()
    {
        makePlatform(7, -450.0 / 60.0, -200.0 / 60.0);
        makePlatform(3, 200.0 / 60.0, 0.0);
        makePlatform(5, 800.0 / 60.0, -100.0 / 60.0);
        // makeBoxes(0, 40, 5);
        for (int i = 0; i < 15; i++)
        {
            Coin coin = new Coin();
            coin.setPosition(6 + i, 6);
            coin.addCollisionListener(character, coin);
            add(coin);
        }
        for (int j = 0; j < 30; j++)
        {
            ManaPickup manaPickup = new ManaPickup();
            manaPickup.setPosition(-j, 1);
            manaPickup.addCollisionListener(character, manaPickup);
            add(manaPickup);
        }
    }

    private void setupCosmeticLayers()
    {
        Layer middleBackground = new Layer();
        middleBackground.setParallaxPosition(0.1, 0.1);
        middleBackground.setLayerPosition(-200);
        Image backgroundImage = new Image("dude/background/snow.png", 25f);
        backgroundImage.setPosition(
                -getVisibleArea(Game.getWindowSize()).width() / 2,
                -getVisibleArea(Game.getWindowSize()).height() / 2);
        middleBackground.add(backgroundImage);
        Layer furtherBackground = new Layer();
        furtherBackground.setLayerPosition(-300);
        furtherBackground.setParallaxPosition(0.05, 0.05);
        Image moon = new Image("dude/moon.png", 1, 1);
        furtherBackground.add(moon);
        moon.setPosition(300, 300);
        addLayer(middleBackground);
        addLayer(furtherBackground);
        // CLOUDS
        addCloudLayer(10, "dude/tiles/sky/clouds_MG_1.png", 300, 1.6, 1f, -100);
        addCloudLayer(10, "dude/tiles/sky/clouds_MG_2.png", -50, 0.8, 1f, -100);
        addCloudLayer(10, "dude/tiles/sky/clouds_MG_3.png", -60, 0.7, 1f, -100);
    }

    private void addCloudLayer(final int NUM_TILES, String tilePath,
            int layerLevel, double xParallax, double yParallax, double xOffset)
    {
        Layer clouds = new Layer();
        clouds.setParallaxPosition(xParallax, yParallax);
        clouds.setLayerPosition(layerLevel);
        final double SCALE = 0.08;
        TileRegistration cloudTiles = new TileRegistration(NUM_TILES, 1,
                384 * SCALE, 216 * SCALE);
        for (int i = 0; i < NUM_TILES; i++)
        {
            cloudTiles.setTile(i, 0, TileMap.createFromImage(tilePath));
        }
        cloudTiles.setPosition(xOffset,
                -getVisibleArea(Game.getWindowSize()).height() / 2 + 5);
        clouds.add(cloudTiles);
        addLayer(clouds);
    }

    private void makePlatform(int length, double pX, double pY)
    {
        Platform platform = new Platform(length);
        platform.setPosition(pX, pY);
        add(platform);
    }

    public static void main(String[] args)
    {
        Game.start(new DudeDemo(), WIDTH, HEIGHT);
    }
}
