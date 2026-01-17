# `coding style` (Programmierstil)

Die Engine Pi ist in einem eigenen
[Programmierstil](https://de.wikipedia.org/wiki/Programmierstil) ([coding
style](https://en.wikipedia.org/wiki/Programming_style)) geschrieben. Dieser
Programmierstil orientiert sich an dem Stil, der in
[BlueJ](https://www.bluej.org/versions.html) verwendet wird. BlueJ erzeugt beim
Erstellen einer neuen Klasse Beispiel-Code, der folgendermaßen aussieht:

```java
/**
 * Write a description of class MyClass here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MyClass
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class MyClass
     */
    public MyClass()
    {
        // initialise instance variables
        x = 0;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
```

Die wichtigsten Eigenschaften des Engine-Pi-Programmierstil sind:

- Zur Einrückung werden __Leerzeichen__ (_spaces_: ` `) und keine Tabulatorzeichen
  (_tabs_: `\t`) verwendet.
- Einen Einrückung ist __vier__ Leerzeichen breit.
- Beginnende geschweifte Klammern (`{`) stehen in einer __neuen Zeile__.

## Eclipse Formatter

Das Engine-Pi-Projekt verwendet den [Eclipse](https://eclipseide.org/)
[Formatter](https://help.eclipse.org/latest/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fref-java-editor-formatter.htm).
Der gewünschte Programmierstil ist in einer XML-Datei definiert. Theoretisch
könnte man per Hand eine solche XML-Datei erstellen. Das Dateiformat ist jedoch
sehr komplex, sodass es sich empfiehlt die Eclipse IDE zu verwenden, um einen
eigenen Programmierstil zu definieren (`Window` > `Preferences` > `Java` > `Code
Style` > `Formatter`).

- Line Wrapping
    - Wrapping Settings
        - Function calls
            - Arguments → *Wrap all elements, except first element if not necessary*
            - Qualified invocations → *Wrap all elements, except first element if not necessary*[^jqno.nl]
        - 'enum' declaration
            - Constants → *Force split, even if line shorter than maximum line width*

[^jqno.nl]: https://jqno.nl/post/2024/08/24/why-are-there-no-decent-code-formatters-for-java/#eclipse-jdt-formatter

## Maven Plugin

Das Engine-Pi-Repositories mit dem [Formatter Maven
Plugin](https://code.revelc.net/formatter-maven-plugin) konfiguriert. Folgende
Kommandozeilenbefehle formartieren alle Java-Datei als Subprojekte im
Repository:

`make format` oder `mvn formatter:format` oder `mvn package`

## Verwendung im Editor Visual Studio Code

Um den Engine-Pi-Programmierstil in Visual Studio Code zu verwenden ist folgende
Konfiguration in der Datei `.vscode/settings.json` nötig:

Die XML-Datei wird über eine lokale Datei referenziert:

```json
{
  "java.format.settings.url": "./subprojects/build-tools/src/main/resources/eclipse-formatter.xml",
  "editor.tabSize": 4,
  "editor.insertSpaces": true
}
```

Die XML-Datei wird über einen HTTP-URL referenziert:

```json
{
  "java.format.settings.url": "https://raw.githubusercontent.com/engine-pi/engine-pi/refs/heads/main/subprojects/build-tools/src/main/resources/eclipse-formatter.xml",
  "editor.tabSize": 4,
  "editor.insertSpaces": true
}
```

Über den Befehl `Format Document` (Shift+Alt+F) kann die aktuelle Datei formatiert werden.
