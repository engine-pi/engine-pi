# `physics` (Physiksimulation)

Die Engine Pi nutzt eine [Java-Version](http://jbox2d.org/) von [Box2D](https://box2d.org/). Diese mächtige
und effiziente Physics-Engine ist in der Engine Pi leicht zu bedienen und
ermöglicht es, mit wenig Aufwand mechanische Phänomene in ein Spiel zu bringen.

Die Physics Engine basiert auf den Prinzipien der [klassischen
Mechanik](https://de.wikipedia.org/wiki/Klassische_Mechanik).[^engine-alpha-wiki:physics]

[^engine-alpha-wiki:physics]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Physics

Du kannst Objekte der Engine Alpha mit wenigen Aktionen einer internen Physik
unterwerfen. Sie unterliegen dann wahlweise der Schwerkraft oder prallen
voneinander ab. Hierzu werden von einer gemeinsamen Oberklasse namens EduActor
diverse Methoden zur Verfügung gestellt, über die dann alle Objekte verfügen.
Wir verwenden hier beispielhaft:

- macheDynamisch() : Unterwirft das Objekt der Schwerkraft.
- macheStatisch() : Lässt das Objekt wie eine "unendlich schwere Mauer" wirken. Dynamische Objekte prallen davon ab.
- macheSensor() : Frisch erzeugte Objeke sind Senoren. Damit kann man "dynamisch" oder "statisch" wieder rückgängig machen.
- setzeElastizitaet(double elastizitaet) : setzt die Elastizität der Objekte. Sie verhalten sich dann wie "Gummibälle".
- setzeReibung(double reibungsKoeffizient) : Setzt den Reibungskoeffizienten der Objekte. Damit kann man "rutschen" oder "gleiten" beeinflussen.[^engine-alpha:grafikfenster]

[^engine-alpha:grafikfenster] https://engine-alpha.org/wiki/v4.x/Das_Grafikfenster
