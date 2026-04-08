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
package pi.graphics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pi.CustomAssertions.assertToStringClassName;
import static pi.CustomAssertions.assertToStringFieldValue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import pi.Scene;
import pi.event.SingleTask;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.VAlign;

/**
 * Tests for {@link SceneInfoOverlay}.
 *
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class SceneInfoOverlayTest
{
    private Scene mockScene;

    private SingleTask mockTask;

    private SceneInfoOverlay overlay;

    @BeforeEach
    void setUp()
    {
        mockScene = mock(Scene.class);
        mockTask = mock(SingleTask.class);
        when(mockScene.delay(anyDouble(), any(Runnable.class)))
            .thenReturn(mockTask);
        overlay = new SceneInfoOverlay(mockScene);
    }

    @Nested
    class ConstructorTest
    {
        @Test
        void createsWithScene()
        {
            assertNotNull(overlay);
            assertNotNull(overlay.background);
            assertNotNull(overlay.margin);
        }

        @Test
        void initiatesHideTask()
        {
            verify(mockScene).delay(anyDouble(), any(Runnable.class));
        }
    }

    @Nested
    class BuilderPatternTest
    {
        @Test
        void titleReturnsThis()
        {
            SceneInfoOverlay result = overlay.title("Test Title");
            assertSame(overlay, result);
        }

        @Test
        void subtitleReturnsThis()
        {
            SceneInfoOverlay result = overlay.subtitle("Test Subtitle");
            assertSame(overlay, result);
        }

        @Test
        void descriptionReturnsThis()
        {
            SceneInfoOverlay result = overlay.description("Test",
                "Description");
            assertSame(overlay, result);
        }

        @Test
        void helpReturnsThis()
        {
            SceneInfoOverlay result = overlay.help("Test", "Help");
            assertSame(overlay, result);
        }

        @Test
        void textColorReturnsThis()
        {
            SceneInfoOverlay result = overlay.textColor(Color.RED);
            assertSame(overlay, result);
        }

        @Test
        void hAlignReturnsThis()
        {
            SceneInfoOverlay result = overlay.hAlign(HAlign.CENTER);
            assertSame(overlay, result);
        }

        @Test
        void vAlignReturnsThis()
        {
            SceneInfoOverlay result = overlay.vAlign(VAlign.MIDDLE);
            assertSame(overlay, result);
        }

        @Test
        void permanentReturnsThis()
        {
            SceneInfoOverlay result = overlay.permanent();
            assertSame(overlay, result);
        }

        @Test
        void durationReturnsThis()
        {
            SceneInfoOverlay result = overlay.duration(5);
            assertSame(overlay, result);
        }

        @Test
        void disableReturnsThis()
        {
            SceneInfoOverlay result = overlay.disable();
            assertSame(overlay, result);
        }

        @Test
        void toggleReturnsThis()
        {
            SceneInfoOverlay result = overlay.toggle();
            assertSame(overlay, result);
        }

        @Test
        void chainedMethods()
        {
            SceneInfoOverlay result = overlay.title("Title")
                .subtitle("Subtitle")
                .description("Description")
                .help("Help")
                .textColor(Color.BLUE)
                .permanent();

            assertSame(overlay, result);
        }
    }

    @Nested
    class TitleTest
    {
        @Test
        void setter()
        {
            assertToStringFieldValue("title",
                "My Title",
                overlay.title("My Title"));
        }

        @Test
        void setterTwice()
        {
            assertToStringFieldValue("title",
                "Second Title",
                overlay.title("First Title").title("Second Title"));
        }
    }

    @Nested
    class SubtitleTest
    {
        @Test
        void setter()
        {
            assertToStringFieldValue("subtitle",
                "My Subtitle",
                overlay.subtitle("My Subtitle"));
        }

        @Test
        void setterTwice()
        {
            assertToStringFieldValue("subtitle",
                "Second Subtitle",
                overlay.subtitle("First Subtitle").subtitle("Second Subtitle"));
        }
    }

    @Nested
    class DescriptionTest
    {
        @Test
        void multipleElements()
        {
            assertToStringFieldValue("description",
                "Line 1\nLine 2\nLine 3",
                overlay.description("Line 1", "Line 2", "Line 3"));
        }

        @Test
        void mixedTypes()
        {
            assertToStringFieldValue("description",
                "Text\n42\nMore Text",
                overlay.description("Text", 42, "More Text"));
        }

        @Test
        void setterTwice()
        {
            assertToStringFieldValue("description",
                "Second Description\nMore",
                overlay.description("First Description")
                    .description("Second Description", "More"));
        }
    }

    @Nested
    class HelpTest
    {
        @Test
        void multipleElements()
        {
            assertToStringFieldValue("help",
                "Help 1\nHelp 2",
                overlay.help("Help 1", "Help 2"));
        }

        @Test
        void mixedTypes()
        {
            assertToStringFieldValue("help",
                "Start\n1\nMiddle\n2\nEnd",
                overlay.help("Start", 1, "Middle", 2, "End"));
        }

        @Test
        void setterTwice()
        {
            assertToStringFieldValue("help",
                "Second Help\nMore",
                overlay.help("First Help").help("Second Help", "More"));
        }
    }

    @Nested
    class TextColorTest
    {
        @Test
        void setTextColorWithRed()
        {
            overlay.textColor(Color.RED);
            assertNotNull(overlay);
        }

        @Test
        void setTextColorWithBlue()
        {
            overlay.textColor(Color.BLUE);
            assertNotNull(overlay);
        }

        @Test
        void setTextColorWithCustomColor()
        {
            Color custom = new Color(128, 64, 32);
            overlay.textColor(custom);
            assertNotNull(overlay);
        }

        @Test
        void setTextColorAfterSettingTitle()
        {
            overlay.title("Title").textColor(Color.GREEN);
            assertNotNull(overlay);
        }
    }

    @Nested
    class AlignmentTest
    {
        @Test
        void setHorizontalAlignmentCenter()
        {
            overlay.hAlign(HAlign.CENTER);
            assertNotNull(overlay);
        }

        @Test
        void setHorizontalAlignmentLeft()
        {
            overlay.hAlign(HAlign.LEFT);
            assertNotNull(overlay);
        }

        @Test
        void setHorizontalAlignmentRight()
        {
            overlay.hAlign(HAlign.RIGHT);
            assertNotNull(overlay);
        }

        @Test
        void setVerticalAlignmentMiddle()
        {
            overlay.vAlign(VAlign.MIDDLE);
            assertNotNull(overlay);
        }

        @Test
        void setVerticalAlignmentTop()
        {
            overlay.vAlign(VAlign.TOP);
            assertNotNull(overlay);
        }

        @Test
        void setVerticalAlignmentBottom()
        {
            overlay.vAlign(VAlign.BOTTOM);
            assertNotNull(overlay);
        }
    }

    @Nested
    class PermanentTest
    {
        @Test
        void setPermanent()
        {
            overlay.permanent();
            assertNotNull(overlay);
        }

        @Test
        void permanentCancelsPreviousTask()
        {
            overlay.permanent();
            verify(mockScene).delay(anyDouble(), any(Runnable.class));
        }
    }

    @Nested
    class DurationTest
    {
        @Test
        void setDuration()
        {
            overlay.duration(5);
            assertNotNull(overlay);
        }

        @Test
        void setDurationZero()
        {
            overlay.duration(0);
            assertNotNull(overlay);
        }

        @Test
        void setDurationLarge()
        {
            overlay.duration(100);
            assertNotNull(overlay);
        }

        @Test
        void durationRestartHideTask()
        {
            overlay.duration(15);
            verify(mockScene, times(2)).delay(anyDouble(), any(Runnable.class));
        }
    }

    @Nested
    class EnableDisableTest
    {
        @Test
        void disable()
        {
            overlay.disable();
            assertNotNull(overlay);
        }

        @Test
        void toggle()
        {
            overlay.toggle();
            assertNotNull(overlay);
        }
    }

    @Nested
    class ComplexScenarioTest
    {
        @Test
        void setAllProperties()
        {
            overlay.title("Main Title")
                .subtitle("Subtitle Text")
                .description("Description Line 1", "Description Line 2")
                .help("Help Text 1", "Help Text 2")
                .textColor(Color.DARK_GRAY)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.TOP)
                .duration(8);

            assertNotNull(overlay);
        }

        @Test
        void replaceText()
        {
            overlay.title("First Title");
            overlay.title("Second Title");
            assertNotNull(overlay);
        }

        @Test
        void setPermanentThenChangeDuration()
        {
            overlay.permanent();
            overlay.duration(5);
            verify(mockScene, times(2)).delay(anyDouble(), any(Runnable.class));
        }
    }

    @Test
    void hasContent()
    {
        assertFalse(overlay.hasContent());
        overlay.title("A title");
        assertTrue(overlay.hasContent());
    }

    @Test
    void toStringFormatter()
    {
        assertToStringClassName(overlay);
    }
}
