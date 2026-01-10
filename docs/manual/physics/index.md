# `physics` (Physiksimulation)

<!-- http://127.0.0.1:8000/engine-pi/manual/physics/ -->
<!-- https://engine-pi.github.io/engine-pi/manual/physics/ -->

Die Engine Pi nutzt eine [Java-Version](http://jbox2d.org/) von [Box2D](https://box2d.org/). Diese mächtige
und effiziente Physics-Engine ist in der Engine Pi leicht zu bedienen und
ermöglicht es, mit wenig Aufwand mechanische Phänomene in ein Spiel zu bringen.

Die Physics Engine basiert auf den Prinzipien der [klassischen
Mechanik](https://de.wikipedia.org/wiki/Klassische_Mechanik).[^engine-alpha-wiki:physics]

[^engine-alpha-wiki:physics]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Physics

<!-- Du kannst Objekte der Engine Alpha mit wenigen Aktionen einer internen Physik
unterwerfen. Sie unterliegen dann wahlweise der Schwerkraft oder prallen
voneinander ab. Hierzu werden von einer gemeinsamen Oberklasse namens EduActor
diverse Methoden zur Verfügung gestellt, über die dann alle Objekte verfügen.
Wir verwenden hier beispielhaft: -->

Die Figuren der Engine Pi können einer internen Physik unterworfen werden. Sie
unterliegen dann wahlweise der Schwerkraft oder prallen voneinander ab. Hierzu
stellt die gemeinsame Oberklasse {{ class('pi.actor.Actor') }} verschiedene
Methoden zur Verfügung, über die alle Figuren verfügen.

## Die wichtigsten Physics-Methoden von Figuren:

- {{ method('pi.actor.Actor', 'makeDynamic()') }}: Unterwirft die Figur der Schwerkraft.
- {{ method('pi.actor.Actor', 'makeStatic()') }}: Lässt die Figur  wie eine „unendlich schwere Mauer“ wirken. Dynamische Figuren prallen davon ab.
- {{ method('pi.actor.Actor', 'makeSensor()') }}: Frisch erzeugte Figuren sind Senoren. Damit kann man „dynamisch“ oder „statisch“ wieder rückgängig machen.
- {{ method('pi.actor.Actor', 'restitution(double)') }}: setzt die Elastizität der Figuren. Sie verhalten sich dann wie „Gummibälle“.
- {{ method('pi.actor.Actor', 'friction(double)') }}: Setzt den Reibungskoeffizienten der Objekte. Damit kann man „rutschen“ oder „gleiten“ beeinflussen.[^engine-alpha:grafikfenster]

[^engine-alpha:grafikfenster]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Das_Grafikfenster
