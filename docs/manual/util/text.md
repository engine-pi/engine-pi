# `TextUtil` (Hilfsmethoden um Text und Zeichenketten zu bearbeiten)

## `TextUtil.countLines(String text)`

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/test/java/pi/util/TextUtilTest.java -->

Die Methode {{ javadoc('pi.util.TextUtil#countLines(java.lang.String)', 'countLines(String)') }}
zählt die Zeilen in einem Text. Dabei werden die Zeilenumbrüche erkannt und die
Anzahl der einzelnen Zeilen zurückgegeben.

Für die folgenden Code-Beispiele verwenden wir einen statischen Import, um die
Methode {{ javadoc('pi.util.TextUtil#countLines(java.lang.String)',
'countLines(String)') }} einzubinden.

```java
import static pi.util.TextUtil.countLines;
```

Ein einfacher Text mit nur einer Zeile liefert genau `1`, auch wenn kein
Zeilenumbruch vorhanden ist.

```java
int count = countLines("Hallo Welt");
System.out.println(count); // 1
```

Wenn der Text mehrere Zeilen enthält, zählt die Methode jeden durch `\n`
getrennten Abschnitt separat. So ergibt ein Satz mit drei Zeilen genau drei
Treffer.

```java
String text = "Erster Satz\nZweiter Satz\nDritter Satz";
int count = countLines(text);
System.out.println(count); // 3
```

Eine leere Zeichenkette ist kein Text mit Zeilen, deshalb liefert die Methode
hier `0`. Das ist praktisch, wenn überprüft werden soll, ob überhaupt Inhalt
vorhanden ist.

```java
int count = countLines("");
System.out.println(count); // 0
```

Wenn der Text am Ende mit einem Zeilenumbruch endet, wird dieser nicht als
zusätzliche leere Zeile gezählt.

```java
String text = "Zeile 1\nZeile 2\n";
int count = countLines(text);
System.out.println(count); // 2
```

Ein Text mit einem abschließenden Zeilenumbruch erzeugt keine
zusätzliche leere Zeile. Die Methode zählt nur tatsächlich vorhandene Zeilen.

Auch Windows- und Mac-Zeilenumbrüche werden erkannt.

```java
String windowsText = "a\r\nb\r\nc";
String macText = "a\rb\rc";
System.out.println(countLines(windowsText)); // 3
System.out.println(countLines(macText)); // 3
```
