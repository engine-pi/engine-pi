# Vibe Coding

Diese Seite dokumentiert den Einsatz der AI beim Entwickeln der Engine.

## 2026-03-31 Claude Haiku 4.5 • 0.3x

https://github.com/engine-pi/engine-pi/commit/61abb42fc8a8287da6159877971a1189b8fb9e7f

> Get the String content of a TextLayout object!

<!-- Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java#1-1), lines 1 to 100

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java#100-100), lines 100 to 200 -->

Looking at your code, I can see you're trying to get the text content from a
`TextLayout` object. Unfortunately, `TextLayout.toString()` doesn't return the
text content—it returns the object representation.

The `TextLayout` class doesn't provide a direct method to extract the original
string. However, since you have access to the `AttributedCharacterIterator` when
creating the layout, here are your options:

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

I'll implement Option 3 for you. First, let me create a wrapper class to store
both the `TextLayout` and its extracted text, then update the code:

<!-- Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java#40-40), lines 40 to 50

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java#200-200), lines 200 to 220

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java#220-220), lines 220 to 224

Replacing 120 lines with 134 lines in [](file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java) -->

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

Now you can easily access the text content of any line via `line.text`
throughout the class.

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

## 2026-03-31 FileLinkChecker GPT-5.3-Codex • 0.9x

https://github.com/engine-pi/engine-pi/commit/add8ef9f43222fd975b55f163028da37b4827729

> Im Repository befinden sind in Java- und Markdown-Dateien Links zu anderen
> Dateien. Diese Links sind an dem Präfix file:// zu erkennen. Schreibe ein
> Kommandozeilenwerkzeug, das alle Java und Markdown-Datei nach diesen Links
> durchsucht und überprüft, ob die Zieldateien tatsächlich vorhanden sind.

Ich prüfe kurz die bestehende CLI-Struktur im Repository und ergänze dann ein neues Kommandozeilenwerkzeug, das -Links in Java- und Markdown-Dateien validiert.

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli)

Searched for files matching `engine-pi` · `subprojects/cli/src/**/*.java`, 13 matches

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/README.md)

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/pom.xml)

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/java2umltext/Client.java)

Als Nächstes lege ich ein eigenständiges CLI-Tool im bestehenden Modul cli an, mit rekursivem Scan von `.java`/`.md`, URI-Parsing für  und Exit-Code `1` bei defekten Links.

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli)

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/java2umltext/package-info.java)

Read [](file:///data/school/repos/inf/java/engine-pi/pom.xml)

Ich implementiere jetzt die neue CLI-Klasse inklusive Link-Erkennung, URI-Normalisierung (inkl. `#fragment`) und Ergebnisausgabe mit Exit-Code, danach kompiliere ich das Modul kurz zur Verifikation.

Created [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/checklinks)

Created [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/checklinks/FileLinkChecker.java)

Generating patch (22 lines) in [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/README.md)


```

```
Die Implementierung ist drin; ich kompiliere jetzt das CLI-Modul und führe das Tool einmal gegen das Repository aus, um Verhalten und Ausgabe zu prüfen.

Ran terminal command:  mvn -pl subprojects/cli -DskipTests compile

Ran terminal command:  mvn -q -pl subprojects/cli -DincludeScope=runtime dependency:build-classpath -Dmdep.outputFile=/tmp/engine-pi-cli.cp && java -cp "subprojects/cli/target/classes:$(cat /tmp/engine-pi-cli.cp)" cli.checklinks.FileLinkChecker . | head -n 30

Ich habe bei der Validierung noch Rauschen erkannt (z. B.  in Codebeispielen/Regexen). Ich schärfe deshalb die Erkennung auf echte absolute `file:///...`-Links und teste erneut.

Generating patch (7 lines) in [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/checklinks/FileLinkChecker.java)


```

```


Ran terminal command:  mvn -pl subprojects/cli -DskipTests compile && mvn -q -pl subprojects/cli -DincludeScope=runtime dependency:build-classpath -Dmdep.outputFile=/tmp/engine-pi-cli.cp && java -cp "subprojects/cli/target/classes:$(cat /tmp/engine-pi-cli.cp)" cli.checklinks.FileLinkChecker . | head -n 25

Kurzes Delta: Es gibt noch einen Fehlalarm aus einem Java-Regex-String. Ich schränke die Java-Prüfung jetzt auf Kommentarbereiche ein, weil die -Verweise im Repository in Java-Dateien als Kommentar-Links vorkommen.

Generating patch (86 lines) in [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/checklinks/FileLinkChecker.java)


```

```


Ran terminal command:  mvn -pl subprojects/cli -DskipTests compile && mvn -q -pl subprojects/cli -DincludeScope=runtime dependency:build-classpath -Dmdep.outputFile=/tmp/engine-pi-cli.cp && java -cp "subprojects/cli/target/classes:$(cat /tmp/engine-pi-cli.cp)" cli.checklinks.FileLinkChecker . | head -n 25

Read changed files in the active git repository

Fertig. Ich habe ein neues Kommandozeilenwerkzeug erstellt, das alle Java- und Markdown-Dateien rekursiv scannt, file:///-Links findet und prüft, ob die Zieldateien existieren.

Geänderte Dateien:
- FileLinkChecker.java
- README.md

Was das Tool macht:
1. Scannt .java, .md und .markdown.
2. Prüft in Java-Dateien nur Kommentarbereiche (vermeidet Fehlalarme aus String-Literalen).
3. Erkennt absolute file:///-URIs, entfernt ggf. Fragmentteile wie #L10.
4. Meldet defekte Links mit Quelldatei und Zeilennummer.
5. Exit-Code 0 bei Erfolg, 1 bei mindestens einem defekten Link.

Verifikation:
1. Modul kompiliert erfolgreich.
2. Tool wurde gegen das Repository ausgeführt.
3. Ergebnis: 214 file://-Links geprüft, 15 defekte Ziele gefunden.

Ausführen (Repository-Root):
mvn -pl subprojects/cli -DskipTests compile
mvn -q -pl subprojects/cli -DincludeScope=runtime dependency:build-classpath -Dmdep.outputFile=/tmp/engine-pi-cli.cp
java -cp "subprojects/cli/target/classes:$(cat engine-pi-cli.cp)" cli.checklinks.FileLinkChecker .

Optional mit Ausgabe auch gültiger Links:
java -cp "subprojects/cli/target/classes:$(cat engine-pi-cli.cp)" cli.checklinks.FileLinkChecker -v .

Wenn du willst, kann ich im nächsten Schritt die 15 gemeldeten defekten Links automatisch auflisten und passende Korrekturvorschläge je Datei machen.

Made changes.

## 2026-03-31 Unify cli command GPT-5.3-Codex • 0.9x

https://github.com/engine-pi/engine-pi/commit/7929144fbfa249ebbd7f70c70c78a2a937ba712f

> Vereinige die beiden Kommandozeilen-Programme checklinks und java2umltext zu
> einem Kommandozeilenprogramm, das auch picoli verwendet. Die beiden
> Unterprogramme sollen über die Argumente checklinks und java2umltext
> aufgerufen werden.

Ich schaue mir zuerst den aktuellen Stand von FileLinkChecker.java und der bestehenden java2umltext-CLI an, dann baue ich daraus eine gemeinsame Haupt-CLI mit Subcommands `checklinks` und `java2umltext`.

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/checklinks/FileLinkChecker.java)

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli)

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/java2umltext/Client.java)

Ich stelle jetzt die Kommandonamen um (`checklinks`) und baue eine gemeinsame Hauptklasse mit Picocli-Subcommands; dabei passe ich `java2umltext` so an, dass es als Unterbefehl sauber Exit-Codes zurückgibt.

Generating patch (7 lines) in [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/checklinks/FileLinkChecker.java)


```

```


Generating patch (51 lines) in [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/java2umltext/Client.java)


```

```


Created [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/Client.java)

Read [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/README.md)

Generating patch (38 lines) in [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/README.md)


```

```
Die Umstellung ist umgesetzt; jetzt kompiliere ich das CLI-Modul und prüfe die neuen Subcommands `checklinks` und `java2umltext` direkt über die gemeinsame Hauptklasse.

Ran terminal command:  mvn -pl subprojects/cli -DskipTests compile

Ran terminal command:  mvn -q -pl subprojects/cli -DincludeScope=runtime dependency:build-classpath -Dmdep.outputFile=/tmp/engine-pi-cli.cp && java -cp "subprojects/cli/target/classes:$(cat /tmp/engine-pi-cli.cp)" cli.Client --help && echo '---' && java -cp "subprojects/cli/target/classes:$(cat /tmp/engine-pi-cli.cp)" cli.Client checklinks --help && echo '---' && java -cp "subprojects/cli/target/classes:$(cat /tmp/engine-pi-cli.cp)" cli.Client java2umltext --help | head -n 30

Read changed files in the active git repository

Ich ergänze noch die Konfigurationsdatei-Unterstützung für das Subcommand `java2umltext` im neuen Root-Command, damit das bisherige Verhalten mit `java2umltext.properties` erhalten bleibt.

Generating patch (34 lines) in [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/Client.java)


```

```


Ran terminal command:  mvn -pl subprojects/cli -DskipTests compile

Ran terminal command:  mvn -q -pl subprojects/cli -DincludeScope=runtime dependency:build-classpath -Dmdep.outputFile=/tmp/engine-pi-cli.cp && java -cp "subprojects/cli/target/classes:$(cat /tmp/engine-pi-cli.cp)" cli.Client --help

Ich habe noch einen kleinen UX-Punkt gefunden: Der Root-Help zeigt aktuell eine Warnung, wenn keine `java2umltext.properties` existiert. Ich behebe das kurz, indem der Provider nur bei vorhandener Datei gesetzt wird.

Generating patch (18 lines) in [](file:///data/school/repos/inf/java/engine-pi/subprojects/cli/src/main/java/cli/Client.java)


```

```


Ran terminal command:  mvn -pl subprojects/cli -DskipTests compile && mvn -q -pl subprojects/cli -DincludeScope=runtime dependency:build-classpath -Dmdep.outputFile=/tmp/engine-pi-cli.cp && java -cp "subprojects/cli/target/classes:$(cat /tmp/engine-pi-cli.cp)" cli.Client --help

Erledigt: Die beiden Tools sind jetzt zu einem gemeinsamen Picocli-Programm mit Subcommands zusammengeführt.

Umgesetzt in:
- Client.java
- FileLinkChecker.java
- Client.java
- README.md

Was geändert wurde:
1. Neues Root-CLI erstellt: `cli.Client` mit Subcommands `checklinks` und `java2umltext`.
2. `checklinks`-Kommando umbenannt von `check-file-links` auf `checklinks`.
3. `java2umltext`-Command (`Config`) auf `Callable<Integer>` umgestellt, damit es als Subcommand sauber Exit-Codes liefert (ohne `System.exit` in der Business-Logik).
4. Unterstützung für `java2umltext.properties` im neuen Root-CLI beibehalten (nur wenn Datei vorhanden/lesbar, damit keine Picocli-Warnung erscheint).
5. README auf die neue einheitliche Nutzung aktualisiert.

Verifiziert:
1. `mvn -pl subprojects/cli -DskipTests compile` läuft erfolgreich.
2. `cli.Client --help` zeigt beide Unterprogramme korrekt:
   - `checklinks`
   - `java2umltext`

Beispielaufrufe:
1. `java -cp "subprojects/cli/target/classes:$(cat engine-pi-cli.cp)" cli.Client checklinks .`
2. `java -cp "subprojects/cli/target/classes:$(cat engine-pi-cli.cp)" cli.Client java2umltext plantuml subprojects/engine/src/main/java`

Wenn du möchtest, kann ich als nächsten Schritt noch einen kleinen Alias-Launcher (z. B. Shell-Skript) hinzufügen, damit der Aufruf ohne langen Classpath möglich ist.

Made changes.
