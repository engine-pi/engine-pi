# `Camera` (Kamera)[^engine-alpha]

[^engine-alpha]: https://engine-alpha.org/wiki/Tutorials/Kamera

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/Camera.java -->

<!-- ## Ziel des Tutorials

In diesem Tutorial lernst du,

- wie die Kamera-Struktur den zu zeigenden Ausschnitt der Zeichenebene bestimmt.
- wie man die Kamera auf der Zeichenebene bewegen kann.
- wie man Kamerafokus und Kameragrenzen benutzt. -->

<!-- ## Was ist die Kamera? -->

<!-- Spiele wie Pac-Man oder Pong haben einen statischen Bildschirm. Aber bei weit
mehr Spielen bewegt sich das Bild, geht zum Beispiel mit der Hauptfigur und
bleibt nicht statisch. -->

Bei Spielen wie [Pac-Man](https://de.wikipedia.org/wiki/Pac-Man) oder
[Pong](https://de.wikipedia.org/wiki/Pong) bleibt der Bildschirm statisch. Bei
weit mehr Spielen hingegen bewegt sich das Bild mit der Hauptfigur mit.<!-- Ich hole mal etwas weiter aus: Die ganze Zeichenebene ist unendlich weit in alle
Richtungen, aber das Spielfenster nicht. Es kann also immer nur ein begrenzter
rechteckiger Bereich der Zeichenebene im Fenster dargestellt werden. -->
Die Zeichenebene ist unendlich weit, das Spielfenster jedoch nicht. Es kann also
immer nur ein begrenzter, rechteckiger Bereich der Zeichenebene im Fenster
dargestellt werden.<!--
-->
Dieser Bereich wird bestimmt von der {{ class('pi.Camera', 'Kamera') }}. Die
Kamera blickt auf die Zeichenebene. Sie „schneidet“ <!-- stanzt --> ein Rechteck
mit den Maßen des Fensters aus und projiziert dies auf den Bildschirm. Der Rest
der Zeichenebene ist also nicht sichtbar.

{{ image('docs/main-classes/camera/Ausschnitt-durch-Kamera.svg') }}

<!-- ![]()
/// caption
Die Kamera auf der Zeichenebene...
///

![]()
/// caption
...und das Ergebnis auf dem Spielbildschirm
///

Dies macht ein Objekt der Klasse {{ class('pi.Camera') }}. -->

<!-- In der Klasse {{ class('pi.Game') }} gibt es eine Referenz auf die einzige aktive Kamera des
Spiels und diese ist so auch in der eigenen spielsteuernden Klasse über die
Referenz cam erreichbar. -->

## Verschieben der Kamera

{{ video('docs/main-classes/camera/ohne-Bounds.mp4', 'Kamerafahrt ohne Bounds') }}

Die Kamera lässt sich <!-- einfach --> folgendermaßen verschieben:

<!-- Hierfür gibt es die selbe Methode,
wie auch für ein Raum-Objekt: -->

```java
public void moveFocus(Vektor delta)

public void moveFocus(double deltaX, double deltaY)
```

Hiermit verschiebt man die Kamera. Im folgenden Beispiel wird die Kamera um
(2|2) verschoben.

<!-- Dies ist allerdings eine kaum merkbare Verschiebung. Man kann
natürlich auch größere Verschiebungswerte wählen.

Das würde im Spiel so realisiert werden: -->

```java
camera().moveFocus(new Vektor(2, 2));

// Oder alternativ:
camera().moveFocus(2, 2);
```

Die Kamera wurde um (2|2) verschoben...

... und dadurch ändert sich das Bild im Fenster

Die Kamera lässt sich also mit dieser Methode einfach und beliebig verschieben.

## Fokus

Kaum ein Spiel verlangt eine unabhängige Bewegung der Kamera. Fast immer ist ein
bestimmtes Objekt (in der Regel das, das gesteuert wird) dauerhaft im Fokus:

<!-- Diese Fokus-Funktion steht auch in der Engine Alpha zur Verfügung. Hierfür gibt
es die folgende Methode: -->

```java
public Camera focus(Actor focus)
```

Damit übergibt man der Kamera ein neues Fokus-Objekt. Dieses Objekt wird fortan
immer in der Mitte des Bildes sein.

<!-- Die Kamera und ein Objekt ohne
Fokuseinstellung

Das Objekt wurde in den Fokus genommen -->

Die Fokuseinstellung lässt sich auch problemlos wieder beenden:

```java
public Camera removeFocus()
```

Zusätzlich kann man das Fokusverhalten spezifizieren:

## `offset` (Fokus-Verzug)

Zum einen muss der Fokus nicht immer exakt im Zentrum des Bildes sein, über den
Aufruf der folgenden Methode kann man einen Verzug setzen:

```java
public Camera offset(Vector offset)
```

Hierbei wird die Kamera immer um den Verzug vom Zentrum des Fokus-Objektes wegbewegt.
Kamera ohne Fokus-Verzug. Das Fokus-Objekt ist genau in der Mitte.

'Kamera mit Fokusverzug von (0|-2) – Siehe Vektor links oben. Soviel wird das
Bild vom eigentlichen Zentrum (gestrichelt) wegbewegt. Diese Einstellung bietet
sich z.B. für Jump n' Run-Spiele an

## `bounds` (Kameragrenzen)

Auch muss die Kamera dem Fokusobjekt nicht bis ins Nirvana folgen – oder sich
manuell bis zur Unendlichkeit verschieben lassen; man kann der Kamera eine
"Grenze" vorschreiben, über die sie niemals gehen wird. Diese Grenze wird als
BoundingRechteck beschrieben.

```java
public Camera bounds(Bounds bounds)
```

{{ video('docs/main-classes/camera/mit-Bounds.mp4', 'Kamerafahrt mit Bounds') }}


Dieses Bounding-Rechteck beschreibt die Fläche auf der Zeichenebene, die diese
Kamera niemals übertreten wird. Die Grenzen der Kamera müssen natürlich
ausreichend groß sein.

<!-- Ein Spielraum mit der Höhe 10 Pixel und der Breite 2000
Pixel funktioniert nicht, denn die Kamera selbst ist ja (im Regelfall) mit einer
sehr viel größeren Breite (nämlich die des Spielfensters) ausgestattet, daher
gilt: -->

Sinnvolle Werte für die Kamera-Bounds sind solche, bei denen die Höhe und Breite
größer als die des Spielfensters ist.

<!-- # Es muss sich ja nicht alles verschieben: Statische grafische Objekte

Bei einigen grafischen Objekten macht es keinen Sinn, dass sie sich mit der
Kamera verschieben. Dies sind zum Beispiel:

- Level- und Punkteanzeigen
- Lebensenergiebalken
- Textnachrichten oder -beschreibungen
- etc.

Damit sich diese grafischen Elemente nicht verschieben, wenn sich die Kamera
bewegt, gibt es hierfür eine besondere Möglichkeit: Die statische Wurzel.

Du kennst bereits die normale Wurzel. An dieser werden alle normalen
Spielelemente angelegt. Doch für alle besonderen Spielelemente, die sich nicht
mit der Kamera verschieben sollen, gibt es die statische Wurzel.

Auch auf die statische Wurzel kann man in der Klasse Game zugreifen. In deiner
spielsteuernden Klasse kannst du auf die statische Wurzel direkt zugreifen. Die
Referenz dafür heißt statischeWurzel.

Füge hieran einfach alle deine Punkteanzeigen, Spielinformationen etc., von
denen du nicht willst, dass sie sich mit der Kamera verschieben. -->
