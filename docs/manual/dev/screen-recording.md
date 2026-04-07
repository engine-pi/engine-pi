# `screen recording` (Bildschirmaufname)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/event/DefaultControl.java -->

## `screenshot` (Bildschirmfotos)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/util/FileUtil.java -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/events/shortcuts.md -->

`ALT + s` zum Speichern eines Bildschirmfotos (unter `~/Bilder` bzw. `~/Pictures`)

## `screen recording` (Bildschirmaufnahme)

Wenn das Kommandozeilenprogramm [ffmpeg](https://www.ffmpeg.org) installiert
ist, kann die Engine Pi Bildschirmaufnahmen vom Spielfenster erstellen. Die
Bildschirmaufnahme wird mit dem Tastenkürzel `ALT + r` gestartet und mit
demselben Tastenkürzel beendet. Das resultierende Video wird im Ordner
`~/Videos` gespeichert. Das Spielfenster sollte nicht sofort nach der
Bildschirmaufnahme geschlossen werden, da [ffmpeg](https://www.ffmpeg.org) im
Hintergrund läuft und die aufgenommenen Einzelbilder zu einem Video konvertiert.

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/Controller.java -->

Mit der Methode
{{ javadoc('pi.Controller#recordScreen()', 'Controller.recordScreen()') }}
kann die Bildschirmaufnahme auch programmatisch
gesteuert werden.
