/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package pi.resources.color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ColorSchemeTest
{
    ColorScheme scheme;

    @BeforeEach
    void setup()
    {
        scheme = new ColorScheme("Test Scheme");
    }

    @Test
    void testName()
    {
        ColorScheme scheme = new ColorScheme("Test Scheme");
        assertEquals("Test Scheme", scheme.name());
    }

    @Nested
    class YellowTest
    {
        @Test
        void testYellow()
        {
            assertNotNull(scheme.yellow());
            assertEquals(new Color(255, 255, 0), scheme.yellow());
        }

        @Test
        void testYellow2()
        {
            Color custom = new Color(200, 200, 0);
            scheme.yellow(custom);
            assertEquals(custom, scheme.yellow());
        }

        @Test
        void testYellow3()
        {
            scheme.yellow(100, 100, 50);
            assertEquals(new Color(100, 100, 50), scheme.yellow());
        }

        @Test
        void testYellow4()
        {
            scheme.yellow("#ffff00");
            assertNotNull(scheme.yellow());
        }
    }

    @Nested
    class YellowOrangeTest
    {
        @Test
        void testYellowOrange()
        {
            assertNotNull(scheme.yellowOrange());
        }

        @Test
        void testYellowOrange2()
        {
            Color custom = new Color(255, 200, 0);
            scheme.yellowOrange(custom);
            assertEquals(custom, scheme.yellowOrange());
        }

        @Test
        void testYellowOrange3()
        {
            scheme.yellowOrange(255, 200, 0);
            assertEquals(new Color(255, 200, 0), scheme.yellowOrange());
        }

        @Test
        void testYellowOrange4()
        {
            scheme.yellowOrange("#ffc800");
            assertNotNull(scheme.yellowOrange());
        }
    }

    @Nested
    class OrangeTest
    {

        @Test
        void testOrange()
        {
            assertNotNull(scheme.orange());
        }

        @Test
        void testOrange2()
        {
            Color custom = new Color(255, 128, 0);
            scheme.orange(custom);
            assertEquals(custom, scheme.orange());
        }

        @Test
        void testOrange3()
        {
            scheme.orange(255, 128, 0);
            assertEquals(new Color(255, 128, 0), scheme.orange());
        }

        @Test
        void testOrange4()
        {
            scheme.orange("#ff8000");
            assertNotNull(scheme.orange());
        }
    }

    @Nested
    class RedOrangeTest
    {
        @Test
        void testRedOrange()
        {
            assertNotNull(scheme.redOrange());
        }

        @Test
        void testRedOrange2()
        {
            Color custom = new Color(255, 100, 0);
            scheme.redOrange(custom);
            assertEquals(custom, scheme.redOrange());
        }

        @Test
        void testRedOrange3()
        {
            scheme.redOrange(255, 100, 0);
            assertEquals(new Color(255, 100, 0), scheme.redOrange());
        }

        @Test
        void testRedOrange4()
        {
            scheme.redOrange("#ff6400");
            assertNotNull(scheme.redOrange());
        }
    }

    @Nested
    class RedTest
    {
        @Test
        void testRed()
        {
            assertNotNull(scheme.red());
            assertEquals(new Color(255, 0, 0), scheme.red());
        }

        @Test
        void testRed2()
        {
            Color custom = new Color(200, 0, 0);
            scheme.red(custom);
            assertEquals(custom, scheme.red());
        }

        @Test
        void testRed3()
        {
            scheme.red(150, 0, 0);
            assertEquals(new Color(150, 0, 0), scheme.red());
        }

        @Test
        void testRed4()
        {
            scheme.red("#ff0000");
            assertNotNull(scheme.red());
        }
    }

    @Nested
    class RedPurpleTest
    {
        @Test
        void testRedPurple()
        {
            assertNotNull(scheme.redPurple());
        }

        @Test
        void testRedPurple2()
        {
            Color custom = new Color(200, 0, 100);
            scheme.redPurple(custom);
            assertEquals(custom, scheme.redPurple());
        }

        @Test
        void testRedPurple3()
        {
            scheme.redPurple(200, 0, 100);
            assertEquals(new Color(200, 0, 100), scheme.redPurple());
        }

        @Test
        void testRedPurple4()
        {
            scheme.redPurple("#c80064");
            assertNotNull(scheme.redPurple());
        }
    }

    @Nested
    class PurpleTest
    {
        @Test
        void testPurple()
        {
            assertNotNull(scheme.purple());
        }

        @Test
        void testPurple2()
        {
            Color custom = new Color(128, 0, 128);
            scheme.purple(custom);
            assertEquals(custom, scheme.purple());
        }

        @Test
        void testPurple3()
        {
            scheme.purple(100, 0, 100);
            assertEquals(new Color(100, 0, 100), scheme.purple());
        }

        @Test
        void testPurple4()
        {
            scheme.purple("#800080");
            assertNotNull(scheme.purple());
        }
    }

    @Nested
    class BluePurpleTest
    {
        @Test
        void testBluePurple()
        {
            assertNotNull(scheme.bluePurple());
        }

        @Test
        void testBluePurple2()
        {
            Color custom = new Color(100, 0, 200);
            scheme.bluePurple(custom);
            assertEquals(custom, scheme.bluePurple());
        }

        @Test
        void testBluePurple3()
        {
            scheme.bluePurple(100, 0, 200);
            assertEquals(new Color(100, 0, 200), scheme.bluePurple());
        }

        @Test
        void testBluePurple4()
        {
            scheme.bluePurple("#6400c8");
            assertNotNull(scheme.bluePurple());
        }
    }

    @Nested
    class BlueGreenTest
    {
        @Test
        void testBlueGreen()
        {
            assertNotNull(scheme.blueGreen());
        }

        @Test
        void testBlueGreen2()
        {
            Color custom = new Color(0, 128, 200);
            scheme.blueGreen(custom);
            assertEquals(custom, scheme.blueGreen());
        }

        @Test
        void testBlueGreen3()
        {
            scheme.blueGreen(0, 128, 200);
            assertEquals(new Color(0, 128, 200), scheme.blueGreen());
        }

        @Test
        void testBlueGreen4()
        {
            scheme.blueGreen("#0080c8");
            assertNotNull(scheme.blueGreen());
        }
    }

    @Nested
    class BlueTest
    {

        @Test
        void testBlue()
        {
            assertNotNull(scheme.blue());
            assertEquals(new Color(0, 0, 255), scheme.blue());
        }

        @Test
        void testBlue2()
        {
            Color custom = new Color(0, 0, 200);
            scheme.blue(custom);
            assertEquals(custom, scheme.blue());
        }

        @Test
        void testBlue3()
        {
            scheme.blue(0, 0, 150);
            assertEquals(new Color(0, 0, 150), scheme.blue());
        }

        @Test
        void testBlue4()
        {
            scheme.blue("#0000ff");
            assertNotNull(scheme.blue());
        }
    }

    @Nested
    class GreenTest
    {
        @Test
        void testGreen()
        {
            assertNotNull(scheme.green());
        }

        @Test
        void testGreen2()
        {
            Color custom = new Color(0, 255, 0);
            scheme.green(custom);
            assertEquals(custom, scheme.green());
        }

        @Test
        void testGreen3()
        {
            scheme.green(0, 200, 0);
            assertEquals(new Color(0, 200, 0), scheme.green());
        }

        @Test
        void testGreen4()
        {
            scheme.green("#00ff00");
            assertNotNull(scheme.green());
        }
    }

    @Nested
    class YelloGreenTest
    {
        @Test
        void testYellowGreen()
        {
            assertNotNull(scheme.yellowGreen());
        }

        @Test
        void testYellowGreen2()
        {
            Color custom = new Color(128, 255, 0);
            scheme.yellowGreen(custom);
            assertEquals(custom, scheme.yellowGreen());
        }

        @Test
        void testYellowGreen3()
        {
            scheme.yellowGreen(128, 255, 0);
            assertEquals(new Color(128, 255, 0), scheme.yellowGreen());
        }

        @Test
        void testYellowGreen4()
        {
            scheme.yellowGreen("#80ff00");
            assertNotNull(scheme.yellowGreen());
        }
    }

    @Nested
    class BrownTest
    {

        @Test
        void testBrown()
        {
            assertNotNull(scheme.brown());
        }

        @Test
        void testBrown2()
        {
            Color custom = new Color(165, 42, 42);
            scheme.brown(custom);
            assertEquals(custom, scheme.brown());
        }

        @Test
        void testBrown3()
        {
            scheme.brown(139, 69, 19);
            assertEquals(new Color(139, 69, 19), scheme.brown());
        }

        @Test
        void testBrown4()
        {
            scheme.brown("#8b4513");
            assertNotNull(scheme.brown());
        }
    }

    @Nested
    class WhiteTest
    {
        @Test
        void testWhite()
        {
            assertNotNull(scheme.white());
            assertEquals(Color.WHITE, scheme.white());
        }

        @Test
        void testWhite2()
        {
            Color custom = new Color(240, 240, 240);
            scheme.white(custom);
            assertEquals(custom, scheme.white());
        }

        @Test
        void testWhite3()
        {
            scheme.white(250, 250, 250);
            assertEquals(new Color(250, 250, 250), scheme.white());
        }

        @Test
        void testWhite4()
        {
            scheme.white("#ffffff");
            assertNotNull(scheme.white());
        }
    }

    @Nested
    class GrayTest
    {
        @Test
        void testGray()
        {
            assertNotNull(scheme.gray());
            assertEquals(Color.GRAY, scheme.gray());
        }

        @Test
        void testGray2()
        {
            Color custom = new Color(128, 128, 128);
            scheme.gray(custom);
            assertEquals(custom, scheme.gray());
        }

        @Test
        void testGray3()
        {
            scheme.gray(100, 100, 100);
            assertEquals(new Color(100, 100, 100), scheme.gray());
        }

        @Test
        void testGray4()
        {
            scheme.gray("#808080");
            assertNotNull(scheme.gray());
        }
    }

    @Nested
    class BlackTest
    {
        @Test
        void testBlack()
        {
            assertNotNull(scheme.black());
            assertEquals(Color.BLACK, scheme.black());
        }

        @Test
        void testBlack2()
        {
            Color custom = new Color(0, 0, 0);
            scheme.black(custom);
            assertEquals(custom, scheme.black());
        }

        @Test
        void testBlack3()
        {
            scheme.black(10, 10, 10);
            assertEquals(new Color(10, 10, 10), scheme.black());
        }

        @Test
        void testBlack4()
        {
            scheme.black("#000000");
            assertNotNull(scheme.black());
        }
    }

    @Test
    void testPrimaryColors()
    {
        Color[] primary = scheme.primaryColors();
        assertEquals(3, primary.length);
        assertEquals(scheme.yellow(), primary[0]);
        assertEquals(scheme.red(), primary[1]);
        assertEquals(scheme.blue(), primary[2]);
    }

    @Test
    void testSecondaryColors()
    {
        Color[] secondary = scheme.secondaryColors();
        assertEquals(3, secondary.length);
        assertEquals(scheme.orange(), secondary[0]);
        assertEquals(scheme.purple(), secondary[1]);
        assertEquals(scheme.green(), secondary[2]);
    }

    @Test
    void testWheelColors()
    {
        Color[] wheel = scheme.wheelColors();
        assertEquals(12, wheel.length);
        assertEquals(scheme.yellow(), wheel[0]);
        assertEquals(scheme.blue(), wheel[8]);
    }

    @Test
    void testExtraColors()
    {
        Color[] extra = scheme.extraColors();
        assertEquals(4, extra.length);
        assertEquals(scheme.brown(), extra[0]);
        assertEquals(scheme.white(), extra[1]);
        assertEquals(scheme.gray(), extra[2]);
        assertEquals(scheme.black(), extra[3]);
    }
}
