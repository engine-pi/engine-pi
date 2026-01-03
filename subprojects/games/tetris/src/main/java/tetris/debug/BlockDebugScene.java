/*
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package tetris.debug;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Scene;
import pi.Text;
import pi.event.KeyStrokeListener;
import tetris.Tetris;
import tetris.tetrominos.Block;

/**
 * @author Josef Friedrich
 */
public class BlockDebugScene extends Scene implements KeyStrokeListener
{
    private Block block;

    private Text blockName;

    public BlockDebugScene()
    {
        blockName = new Text("L", 2);
        blockName.setColor(Color.WHITE);
        blockName.setPosition(2, Tetris.HEIGHT / 2);
        add(blockName);
        createBlock("L");
    }

    private void createBlock(String imageName)
    {
        if (block != null)
        {
            block.remove();
        }
        block = new Block(this, imageName, Tetris.WIDTH / 2, Tetris.HEIGHT - 2);
        blockName.setContent(imageName);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_L:
            createBlock("L");
            break;

        case KeyEvent.VK_J:
            createBlock("J");
            break;

        case KeyEvent.VK_I:
            createBlock("I");
            break;

        case KeyEvent.VK_O:
            createBlock("O");
            break;

        case KeyEvent.VK_Z:
            createBlock("Z");
            break;

        case KeyEvent.VK_S:
            createBlock("S");
            break;

        case KeyEvent.VK_T:
            createBlock("T");
            break;

        case KeyEvent.VK_LEFT:
            block.moveLeft();
            break;

        case KeyEvent.VK_RIGHT:
            block.moveRight();
            break;

        case KeyEvent.VK_DOWN:
            block.moveDown();
            break;
        }
    }

    public static void main(String[] args)
    {
        Scene scene = new BlockDebugScene();
        scene.camera().focus(Tetris.WIDTH / 2, Tetris.HEIGHT / 2);
        Tetris.start(scene, true);
    }
}
