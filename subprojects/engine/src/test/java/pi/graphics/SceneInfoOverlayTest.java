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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static pi.debug.ToStringAssertions.assertContainsClassName;
import static pi.debug.ToStringAssertions.assertContainsField;
import static pi.debug.ToStringAssertions.assertContainsFields;
import static pi.debug.ToStringAssertions.assertDoesNotContainField;

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
        void setTitleWithText()
        {
            overlay.title("My Title");
            assertNotNull(overlay);
        }

        @Test
        void setTitleTwice()
        {
            overlay.title("First Title").title("Second Title");
            assertNotNull(overlay);
        }
    }

    @Nested
    class SubtitleTest
    {
        @Test
        void setSubtitleWithText()
        {
            overlay.subtitle("My Subtitle");
            assertNotNull(overlay);
        }

        @Test
        void setSubtitleTwice()
        {
            overlay.subtitle("First Subtitle").subtitle("Second Subtitle");
            assertNotNull(overlay);
        }
    }

    @Nested
    class DescriptionTest
    {
        @Test
        void setDescriptionWithMultipleElements()
        {
            overlay.description("Line 1", "Line 2", "Line 3");
            assertNotNull(overlay);
        }

        @Test
        void setDescriptionWithMixedTypes()
        {
            overlay.description("Text", 42, "More Text");
            assertNotNull(overlay);
        }

        @Test
        void setDescriptionMultipleTimes()
        {
            overlay.description("First Description")
                .description("Second Description", "More");
            assertNotNull(overlay);
        }
    }

    @Nested
    class HelpTest
    {
        @Test
        void setHelpWithMultipleElements()
        {
            overlay.help("Help 1", "Help 2");
            assertNotNull(overlay);
        }

        @Test
        void setHelpWithMixedTypes()
        {
            overlay.help("Start", 1, "Middle", 2, "End");
            assertNotNull(overlay);
        }

        @Test
        void setHelpMultipleTimes()
        {
            overlay.help("First Help").help("Second Help", "More");
            assertNotNull(overlay);
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

    @Nested
    class ToStringTest
    {
        @Test
        void toStringContainsClassName()
        {
            String result = overlay.toString();
            assertContainsClassName(result, "SceneInfoOverlay");
        }

        @Test
        void toStringContainsPermanentField()
        {
            overlay.permanent();
            String result = overlay.toString();
            assertContainsField(result, "permanent", true);
        }

        @Test
        void toStringContainsDurationField()
        {
            overlay.duration(7.5);
            String result = overlay.toString();
            assertContainsField(result, "duration", 7.5);
        }

        @Test
        void toStringContainsDurationWithUnit()
        {
            String result = overlay.toString();
            assertTrue(result.contains("s"), "duration should have 's' unit");
        }

        @Test
        void toStringContainsTextColorField()
        {
            overlay.textColor(Color.RED);
            String result = overlay.toString();
            assertContainsField(result, "textColor", Color.RED);
        }

        @Test
        void toStringContainsTitleWhenSet()
        {
            overlay.title("Test Title");
            String result = overlay.toString();
            assertContainsField(result, "title", "Test Title");
        }

        @Test
        void toStringDoesNotContainTitleWhenNotSet()
        {
            String result = overlay.toString();
            assertDoesNotContainField(result, "title");
        }

        @Test
        void toStringContainsSubtitleWhenSet()
        {
            overlay.subtitle("Test Subtitle");
            String result = overlay.toString();
            assertContainsField(result, "subtitle", "Test Subtitle");
        }

        @Test
        void toStringDoesNotContainSubtitleWhenNotSet()
        {
            String result = overlay.toString();
            assertDoesNotContainField(result, "subtitle");
        }

        @Test
        void toStringContainsDescriptionWhenSet()
        {
            overlay.description("Test Description");
            String result = overlay.toString();
            assertContainsField(result, "description", "Test Description");
        }

        @Test
        void toStringDoesNotContainDescriptionWhenNotSet()
        {
            String result = overlay.toString();
            assertDoesNotContainField(result, "description");
        }

        @Test
        void toStringContainsHelpWhenSet()
        {
            overlay.help("Test Help");
            String result = overlay.toString();
            assertContainsField(result, "help", "Test Help");
        }

        @Test
        void toStringDoesNotContainHelpWhenNotSet()
        {
            String result = overlay.toString();
            assertDoesNotContainField(result, "help");
        }

        @Test
        void toStringContainsAllFieldsWhenAllSet()
        {
            overlay.title("Title")
                .subtitle("Subtitle")
                .description("Description")
                .help("Help")
                .permanent();

            String result = overlay.toString();
            assertContainsFields(result,
                "permanent",
                "duration",
                "title",
                "subtitle",
                "description",
                "help",
                "textColor");
        }
    }
}
