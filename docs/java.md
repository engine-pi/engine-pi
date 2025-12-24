
## Java

### Java-Entwicklungsumgebung: IDE - Integrated Development Environment (integrierte Entwicklungsumgebung)

Eine integrierte Entwicklungsumgebung (IDE, von englisch _integrated development
environment_) ist eine Sammlung von Computerprogrammen, mit denen die Aufgaben
der Softwareentwicklung möglichst ohne Medienbrüche bearbeitet werden können.[^wikipedia-ide]

[^wikipedia-ide] https://de.wikipedia.org/wiki/Integrierte_Entwicklungsumgebung

#### Bekannte IDEs

- [BlueJ](https://www.bluej.org/): Reduzierte IDE für pädagogische Zwecke
- [Visual Studio Code](https://code.visualstudio.com): von Microsoft entwickelt, für alle Sprachen einsetzbar, wegen vieler Erweiterungen, läuft auf Google Chrome
- [Eclipse](https://www.eclipse.org/downloads)
- [IntelliJ IDEA](https://www.jetbrains.com/de-de/idea): auf Java spezialisiert

### Java-Paketnamen

Um Pakete mit gleichem Namen zu vermeiden, haben sich in der Java-Welt folgende
Konvention für Paketnamen herausgebildet:

- Paketnamen bestehen nur aus Kleinbuchstaben und Unterstrichen `_` (um sie von Klassen zu unterscheiden).
- Paketnamen sind durch Punkte getrennt.
- Der Anfang des Paketnamens wird durch die Organisation bestimmt, die sie erstellt.

Um den Paketnamen auf der Grundlage einer Organisation zu bestimmen, wird die
URL der Organisation umgedreht. Beispielsweise wird aus der URL

    https://pirckheimer-gymnasium.de/tetris

der Paketname:

    de.pirckheimer_gymnasium.tetris

<small>Quelle: [baeldung.com](https://www.baeldung.com/java-packages#1-naming-conventions)</small>

### Importe von Java-Klassen aus Paketen

Java verfügt über unzählige vorgefertigte Klassen und Schnittstellen. Thematisch zusammengehörende Klassen und
Schnittstellen werden zu einem Paket (_package_) zusammengefasst. Die so entstehende Java-Bibliothek ist riesig und
enthält tausende verschiedener Klassen mit unterschiedlichsten Methoden. Um sich einer dieser Klassen bedienen
zu können, muss man sie in das gewünschte Projekt importieren. In Java funktioniert das mit dem Schlüsselwort
`import`.

**Syntax**

`import <paketname>.<klassenname>;` Importiert nur die gewünschte Klasse des angesprochenen Paketes.<br>
`import <paketname>.*;` Importiert sämtliche Klassen des angesprochenen Paketes.

**Beispiel**

`import java.util.Random;` Importiert die Klasse Random des Paketes java.util.<br>
`import java.util.*;` Importiert das vollständige Paket java.util.

<small>Quelle: Klett, Informatik 2, 2021, Seite 275</small>

### `super`-Schlüsselwort

Das Java-Schlüsselwort `super` hat drei explizite Verwendungsbereiche.

1. Zugriff auf die Attribute der Elternklasse, wenn die Kindklasse ebenfalls Attribute mit demselben Namen hat.
2. Aufruf des Konstruktoren der Elternklasse in der Kindklasse.
3. Aufruf der Methoden der Elternklasse in der Kindklasse, wenn die Kindklasse Methoden überschrieben hat.

<small>Quelle: [codegym.cc](https://codegym.cc/de/groups/posts/super-schlsselwort-in-java)</small>

### Überladen von Methoden

Überladen bedeutet, dass derselbe Methodenname mehrfach in einer Klasse verwendet werden kann.
Damit das Überladen möglich ist, muss wenigstens eine der folgenden Vorraussetzungen erfüllt sein:

1. Der Datentyp mindestens eines Übergabeparameters ist anders als in den übrigen
   gleichnamen Methoden.
2. Die Anzahl der Übergabeparameter ist unterschiedlich.

<small>Quelle: [Java-Tutorial.org ](https://www.java-tutorial.org/ueberladen_von_methoden.html)</small>

### Lambda-Ausdrücken

Mit Lambda-Ausdrücken kann man sich viel Schreibarbeit sparen. Klassen, die eine
sogenannten Funktionale Schnittstelle (Functional Interface) implementieren,
d. h. ein Interface mit genau einer abstrakten Methoden, können auch als
Lambda-Ausdruck formuliert werden.

Klasse, die das Interface/Schnittstelle `Runnable` implementiert.

```java
class MyRunnable implements Runnable
{
    public void run()
    {
        startTitleScene();
    }
}

delay(3, new MyRunnable());
```

Als anonyme Klasse

```java
delay(3, new Runnable()
{
    public void run()
    {
        startTitleScene();
    }
});
```

Als Lambda-Ausdruck (Name stammt vom [Lambda-Kalkül](https://de.wikipedia.org/wiki/Lambda-Kalk%C3%BCl) ab)

```java
delay(3, () -> startTitleScene());
```

### Entwurfsmuster [Schablonenmethode](https://de.wikipedia.org/wiki/Schablonenmethode)

Beim Schablonenmethoden-Entwurfsmuster wird in einer abstrakten Klasse das
Skelett eines Algorithmus definiert. Die konkrete Ausformung der einzelnen
Schritte wird an Unterklassen delegiert. Dadurch besteht die Möglichkeit,
einzelne Schritte des Algorithmus zu verändern oder zu überschreiben, ohne dass
die zu Grunde liegende Struktur des Algorithmus modifiziert werden muss. Die
Schablonenmethode (engl. template method) ruft abstrakte Methoden auf, die erst
in den Unterklassen definiert werden. Diese Methoden werden auch als
Einschubmethoden bezeichnet.

Quelle: [Wikipedia](https://de.wikipedia.org/wiki/Schablonenmethode)

### foreach-Schleife

for-each ist eine Art for-Schleife, die du verwendest, wenn du alle Elemente
eines Arrays oder einer Collection verarbeiten musst. Allerdings wird der
Ausdruck for-each in dieser Schleife eigentlich nicht verwendet. Die Syntax
lautet wie folgt:

```java
for (type itVar : array)
{
    // Operations
}
```

Wobei type der Typ der Iterator-Variable ist (der dem Datentyp der Elemente im
Array entspricht!), itVar ihr Name und array ein Array (andere Datenstrukturen
sind auch erlaubt, z. B. eine Collection, wie ArrayList), d. h. das Objekt, auf
dem die Schleife ausgeführt wird. Wie du siehst, wird bei diesem Konstrukt kein
Zähler verwendet: Die Iterator-Variable iteriert einfach über die Elemente des
Arrays oder der Collection. Wenn eine solche Schleife ausgeführt wird, wird der
Iterator-Variable nacheinander der Wert jedes Elements des Arrays oder der
Collection zugewiesen, woraufhin der angegebene Anweisungsblock (oder die
Anweisung) ausgeführt wird.

Quelle: [codegym.cc](https://codegym.cc/de/groups/posts/1011-die-for-each-schleife-in-java)
