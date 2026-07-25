# `fonts` (Schriftarten)

{{ static_import_admonition('fonts') }}

Die Klasse {{ javadoc('java.desktop:java.awt.Font') }} beschreibt in Java, wie
Text dargestellt werden soll. Ein `Font`-Objekt speichert vor allem drei Dinge:

- die Schriftfamilie, zum Beispiel `Serif`, `SansSerif` oder `Monospaced`
- den Stil, also normal, fett oder kursiv
- die Größe in Punkten

In Engine Pi werden Schriftarten an mehreren Stellen verwendet, zum Beispiel bei
{{ javadoc('pi.actor.Text') }} oder im {{ javadoc('pi.resources.font.FontContainer') }}.
Deshalb ist es sinnvoll, die AWT-Klasse `Font` zuerst als normale Java-Klasse zu
verstehen.

## Eine Schriftart erzeugen

Ein `Font`-Objekt wird meist mit dem Konstruktor
{{ javadoc('java.desktop:java.awt.Font#Font(java.lang.String,int,int)', 'Font(String name, int style, int size)') }}
erzeugt:

```java
import java.awt.Font;

Font titleFont = new Font("Serif", Font.BOLD, 24);
```

Die drei Argumente bedeuten:

- `"Serif"`: Name oder Familie der Schrift
- `Font.BOLD`: Stil
- `24`: Größe in Punkt

Typische Familiennamen sind:

- {{ javadoc('java.desktop:java.awt.Font#SERIF') }}
- {{ javadoc('java.desktop:java.awt.Font#SANS_SERIF') }}
- {{ javadoc('java.desktop:java.awt.Font#MONOSPACED') }}
- {{ javadoc('java.desktop:java.awt.Font#DIALOG') }}
- {{ javadoc('java.desktop:java.awt.Font#DIALOG_INPUT') }}

Sie funktionieren systemübergreifend zuverlässiger als frei gewählte
Schriftnamen.

## Schriftstile

Die Klasse `Font` kennt die Stilkonstanten:

- {{ javadoc('java.desktop:java.awt.Font#PLAIN') }}: normaler Text
- {{ javadoc('java.desktop:java.awt.Font#BOLD') }}: fetter Text
- {{ javadoc('java.desktop:java.awt.Font#ITALIC') }}: kursiver Text

Fett und kursiv können kombiniert werden:

```java
Font emphasis = new Font("SansSerif", Font.BOLD | Font.ITALIC, 18);
```

## Informationen über eine Schriftart auslesen

Viele Eigenschaften lassen sich direkt abfragen:

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/test/java/pi/resources/font/FontTest.java -->

```java
Font font = new Font("Monospaced", Font.PLAIN, 16);

String family = font.getFamily(); // "Monospaced"
String name = font.getName(); // "Monospaced"
int size = font.getSize(); // 16
boolean bold = font.isBold(); // false
boolean italic = font.isItalic(); // false
```

Wichtig ist der Unterschied zwischen `getFamily()` und `getName()`:

- `getFamily()` liefert die Schriftfamilie
- `getName()` liefert den Namen, mit dem die Schrift erstellt wurde

Je nach Betriebssystem können sich die Ergebnisse leicht unterscheiden.

## Eine vorhandene Schriftart anpassen

`Font`-Objekte sind unveränderlich. Methoden wie
{{ javadoc('java.desktop:java.awt.Font#deriveFont(float)') }} oder
{{ javadoc('java.desktop:java.awt.Font#deriveFont(int,float)') }} erzeugen daher
immer ein neues Objekt:

```java
Font base = new Font("Serif", Font.PLAIN, 14);
Font larger = base.deriveFont(20f);
Font bold = base.deriveFont(Font.BOLD);
Font boldLarge = base.deriveFont(Font.BOLD, 22f);
```

Das ist praktisch, wenn nur Größe oder Stil geändert werden sollen.

## Einsatz in Engine Pi

Für die Figur {{ javadoc('pi.actor.Text') }} kann eine AWT-Schrift direkt
übergeben werden:

```java
import java.awt.Font;

import pi.Text;

Text text = new Text("Hallo Welt")
		.font(new Font(Font.SERIF, Font.BOLD, 18))
		.height(4);
```

Oft reicht in Engine Pi auch einfach der Name einer Systemschrift:

```java
Text text = new Text("Hallo Welt")
		.font("Arial")
		.style(1);
```

Die Zahl `1` steht hier für fett. Etwas typsicherer ist die Variante mit
{{ javadoc('pi.resources.font.FontStyle') }}:

```java
import pi.resources.font.FontStyle;

Text text = new Text("Hallo Welt")
		.font("Arial")
		.style(FontStyle.BOLD);
```

## Schriftarten über den Font-Container laden

Engine Pi verwaltet Schriftarten über den
{{ javadoc('pi.resources.font.FontContainer') }}. Er ist über
{{ javadoc('pi.Controller#fonts()', 'Controller.fonts()') }} erreichbar.

Einige typische Beispiele:

```java
import java.awt.Font;

import pi.Controller;

Font arial = Controller.fonts().get("Arial");
Font arialBold = Controller.fonts().get("Arial", Font.BOLD);
Font arialLarge = Controller.fonts().get("Arial", 22);
Font arialBoldLarge = Controller.fonts().get("Arial", Font.BOLD, 22);
```

Außerdem gibt es Hilfsmethoden für Systemschriftarten:

```java
String[] installedFonts = FontContainer.systemFonts();
boolean hasArial = FontContainer.isSystemFont("Arial");
```

Damit lässt sich vorab prüfen, ob eine gewünschte Schrift auf dem aktuellen
System vorhanden ist.

## Standardschrift der Engine

Engine Pi bringt eine eigene Standardschrift mit. Sie kann direkt über den
Font-Container geladen werden:

```java
import java.awt.Font;

import pi.Controller;
import pi.resources.font.FontStyle;

Font regular = Controller.fonts().defaultFont();
Font italic = Controller.fonts().defaultFont(FontStyle.ITALIC);
Font bold = Controller.fonts().defaultFont(Font.BOLD);
```

Das ist nützlich, wenn ein Projekt unabhängig von lokal installierten
Systemschriftarten bleiben soll.

## Typische Fehlerquellen

- Nicht jede Schrift wie `"Arial"` ist auf jedem Betriebssystem installiert.
- Die Größe eines `Font`-Objekts wird in Punkten angegeben, nicht in der
	Weltgröße einer `Text`-Figur.
- `Font.BOLD` und `Font.ITALIC` beeinflussen nur den Stil der Schrift, nicht
	die Farbe oder Position des Textes.
- Methoden wie `deriveFont(...)` verändern nicht das vorhandene Objekt,
	sondern liefern eine neue Schriftart zurück.

## Fazit

Die Klasse {{ javadoc('java.desktop:java.awt.Font') }} ist die zentrale
Java-Klasse für Schriftarten. Wer Familiennamen, Stilkonstanten und
`deriveFont(...)` verstanden hat, kann Schriftarten auch in Engine Pi gezielt
einsetzen, sei es direkt über `new Font(...)`, über den Namen einer
Systemschrift oder über den {{ javadoc('pi.resources.font.FontContainer') }}.
