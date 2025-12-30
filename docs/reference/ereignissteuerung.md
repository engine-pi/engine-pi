# Ereignissteuerung

## Voreingestellte Tastenkürzel und Steuerungsmöglichkeiten

Die Engine registriert im Auslieferungszustand einige wenige [grundlegenden
Maus- und
Tastatur-Steuermöglichkeiten](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/event/DefaultControl.html).

Diese sind hoffentlich beim Entwickeln hilfreich. Mit den statischen Methoden
[Game.removeDefaultControl()](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/Game.html#removeDefaultControl()>)
können diese Kürzel entfernt oder mit
[Game.setDefaultControl(DefaultControl)](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/Game.html#setDefaultControl(pi.event.DefaultListener)>)
neue Kürzel gesetzt werden.

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/event/DefaultControl.java -->

- `ESCAPE` zum Schließen des Fensters.
- `ALT + a` zum An- und Abschalten der Figuren-Zeichenroutine (Es
  werden nur die Umrisse gezeichnet, nicht die Füllung).
- `ALT + d` zum An- und Abschalten des Debug-Modus.
- `ALT + p` zum Ein- und Ausblenden der Figuren-Positionen (sehr
  ressourcenintensiv).
- `ALT + r` zum Ein- oder Ausschalten der Bildschirmaufnahme (in Form von Einzelbildern).
- `ALT + s` zum Speichern eines Bildschirmfotos (unter `~/engine-pi`).
- `ALT + PLUS` Hineinzoomen.
- `ALT + MINUS` Herauszoomen.
- `ALT + SHIFT + PLUS` schnelles Hineinzoomen.
- `ALT + SHIFT + MINUS` schnelles Herauszoomen.
- `ALT + Pfeiltasten` zum Bewegen der Kamera.
- `ALT + Mausrad` zum Einstellen des Zoomfaktors.
