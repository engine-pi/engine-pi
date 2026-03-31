# Vibe Coding

Diese Seite dokumentiert den Einsatz der AI beim Entwickeln der Engine.


## 2026-03-31

> Get the String content of a TextLayout object

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java#1-1), lines 1 to 100

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java#100-100), lines 100 to 200

Looking at your code, I can see you're trying to get the text content from a `TextLayout` object. Unfortunately, `TextLayout.toString()` doesn't return the text content—it returns the object representation.

The `TextLayout` class doesn't provide a direct method to extract the original string. However, since you have access to the `AttributedCharacterIterator` when creating the layout, here are your options:

**Option 1: Extract from the iterator (at creation time)**

```java
AttributedString styledText = new AttributedString(line);
styledText.addAttribute(TextAttribute.FONT, font);
AttributedCharacterIterator iterator = styledText.getIterator();

// Get the text directly from the iterator
StringBuilder textContent = new StringBuilder();
for (char ch = iterator.first(); ch != AttributedCharacterIterator.DONE; ch = iterator.next()) {
    textContent.append(ch);
}
String text = textContent.toString();

TextLayout nextLayout = measurer.nextLayout(wrappingWidth);
```

**Option 2: Store text separately (recommended for your case)**

Create a wrapper or pair the `TextLayout` with its text:

```java
class TextLayoutLine {
    TextLayout layout;
    String text;

    TextLayoutLine(TextLayout layout, String text) {
        this.layout = layout;
        this.text = text;
    }
}
```

**Option 3: Use LineBreakMeasurer's position to extract from original**

```java
int start = measurer.getPosition() - nextLayout.getCharacterCount();
int end = measurer.getPosition();
String text = line.substring(start, end);
```

Which approach would work best for your use case?

> 3


I'll implement Option 3 for you. First, let me create a wrapper class to store both the `TextLayout` and its extracted text, then update the code:

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java#40-40), lines 40 to 50

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java#200-200), lines 200 to 220

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java#220-220), lines 220 to 224

Replacing 120 lines with 134 lines in [](file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java)


```

```
Done! I've implemented Option 3. Here's what changed:

1. **Added a wrapper class** `TextLayoutLine` that stores both the `TextLayout` and its extracted text
2. **Updated the lines list** to use `List<TextLayoutLine>` instead of `List<TextLayout>`
3. **Extracted text using position** in `splitIntoLines()`:
   ```java
   int end = measurer.getPosition();
   int start = end - nextLayout.getCharacterCount();
   String text = line.substring(start, end);
   ```
4. **Updated all methods** (`measureLines()` and `draw()`) to extract the layout from the wrapper

Now you can easily access the text content of any line via `line.text` throughout the class.

Made changes.

```diff
diff --git a/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java b/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java
index 06b945a2..37a0c151 100644
--- a/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java
+++ b/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java
@@ -35,10 +35,10 @@ import pi.annotations.ChainableMethod;
 import pi.annotations.Setter;
 import pi.resources.font.FontUtil;

-// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/TextBlockBoxDemo.java
+// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/boxes/TextBlockBoxDemo.java

 /**
- * Ein mehrzeiliger Textblock
+ * Ein mehrzeiliger <b>Textblock</b>.
  *
  * @author Josef Friedrich
  *
@@ -46,7 +46,26 @@ import pi.resources.font.FontUtil;
  */
 public class TextBlockBox extends TextBox
 {
-    List<TextLayout> lines = new ArrayList<>();
+    /**
+     * Wrapper class to store both TextLayout and its extracted text content.
+     */
+    private static class TextLayoutLine
+    {
+        private TextLayout layout;
+
+        private String content;
+
+        TextLayoutLine(TextLayout layout, String content)
+        {
+            this.layout = layout;
+            this.content = content;
+        }
+
+        public String content()
+        {
+            return content;
+        }
+    }

     /**
      * Erzeugt einen mehrzeiligen <b>Textblock</b>.
@@ -60,6 +79,13 @@ public class TextBlockBox extends TextBox
         super(content);
     }

+    List<TextLayoutLine> lines = new ArrayList<>();
+
+    public List<TextLayoutLine> getLines()
+    {
+        return lines;
+    }
+
     /* hAlign */

     HAlign hAlign = HAlign.LEFT;
@@ -94,12 +120,12 @@ public class TextBlockBox extends TextBox
         return this;
     }

-    private static List<TextLayout> splitIntoLines(String content,
+    private static List<TextLayoutLine> splitIntoLines(String content,
             FontRenderContext context, Font font, float wrappingWidth)
     {
         // Source:
         // https://github.com/gurkenlabs/litiengine/blob/0fae965994a30757b078153a67f095fe122ae456/litiengine/src/main/java/de/gurkenlabs/litiengine/graphics/TextRenderer.java#L279-L315
-        ArrayList<TextLayout> lines = new ArrayList<>();
+        ArrayList<TextLayoutLine> lines = new ArrayList<>();
         for (String line : content.split(System.lineSeparator()))
         {
             final AttributedString styledText = new AttributedString(line);
@@ -111,7 +137,17 @@ public class TextBlockBox extends TextBox
             while (true)
             {
                 TextLayout nextLayout = measurer.nextLayout(wrappingWidth);
-                lines.add(nextLayout);
+
+                // Extract text using measurer position
+                int end = measurer.getPosition();
+                int start = end - nextLayout.getCharacterCount();
+                String text = line.substring(start, end);
+
+                System.out.println("Measured line: '" + text + "' (start="
+                        + start + ", end=" + end + ")");
+
+                lines.add(new TextLayoutLine(nextLayout, text));
+
                 if (measurer.getPosition() >= line.length())
                 {
                     break;
@@ -121,18 +157,19 @@ public class TextBlockBox extends TextBox
         return lines;
     }

-    private static PixelDimension measureLines(List<TextLayout> lines)
+    private static PixelDimension measureLines(List<TextLayoutLine> lines)
     {
         // Source:
         // https://github.com/gurkenlabs/litiengine/blob/0fae965994a30757b078153a67f095fe122ae456/litiengine/src/main/java/de/gurkenlabs/litiengine/graphics/TextRenderer.java#L279-L315
         PixelDimension dim = new PixelDimension();
         float maxWidth = 0;
         float height = 0;
-        for (TextLayout line : lines)
+        for (TextLayoutLine line : lines)
         {
+            TextLayout layout = line.layout;
             // Advance: Der Vorschub ist der Abstand vom Ursprung bis zum
             // Vorschub des Zeichens ganz rechts.
-            float width = line.getAdvance();
+            float width = layout.getAdvance();
             if (width > maxWidth)
             {
                 maxWidth = width;
@@ -140,15 +177,14 @@ public class TextBlockBox extends TextBox
             height +=
                     // Ascent: der Abstand von der oberen rechten Ecke des
                     // Textlayouts zur Grundlinie.
-                    line.getAscent() +
+                    layout.getAscent() +
                     // Descent: Entfernung von der Grundlinie zum unteren linken
-                    // Rand
-                    // des Textlayouts
-                            line.getDescent() +
+                    // Rand des Textlayouts
+                            layout.getDescent() +
                             // Leading: empfohlener Zeilenabstand relativ zur
                             // Grundlinie.
                             // Scheint meistens 0.0 zu sein?
-                            line.getLeading();
+                            layout.getLeading();
         }
         dim.width = (int) Math.ceil(maxWidth);
         dim.height = (int) Math.ceil(height);
@@ -164,15 +200,16 @@ public class TextBlockBox extends TextBox
             g.setColor(color);
         }
         float yCursor = (float) y;
-        for (TextLayout line : lines)
+        for (TextLayoutLine line : lines)
         {
+            TextLayout layout = line.layout;
             // Ascent: der Abstand von der oberen rechten Ecke des
             // Textlayouts zur Grundlinie.
-            yCursor += line.getAscent();
+            yCursor += layout.getAscent();
             float xCursor = (float) x;
             // Advance: Der Vorschub ist der Abstand vom Ursprung bis zum
             // Vorschub des Zeichens ganz rechts.
-            float lineWidth = line.getAdvance();
+            float lineWidth = layout.getAdvance();
             switch (hAlign)
             {
             case LEFT:
@@ -187,13 +224,13 @@ public class TextBlockBox extends TextBox
                 break;
             }

-            line.draw(g, xCursor, yCursor);
+            layout.draw(g, xCursor, yCursor);
             // Descent: Entfernung von der Grundlinie zum unteren linken Rand
             // des Textlayouts
-            yCursor += line.getDescent() +
+            yCursor += layout.getDescent() +
             // Leading: empfohlener Zeilenabstand relativ zur Grundlinie.
             // Scheint meistens 0.0 zu sein?
-                    line.getLeading();
+                    layout.getLeading();
         }
         g.setColor(oldColor);
     }
```
