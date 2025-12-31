# `physics` (Physiksimulation)

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
