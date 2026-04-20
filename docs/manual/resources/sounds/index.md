# `sounds` (Klänge)

{{ static_import_admonition('sounds') }}

![](./package-sounds.drawio)


Die Engine unterscheidet zwischen zwei Klassen:
{{ javadoc('pi.resources.sound.Sound', 'Sound') }} (Klang) und
{{ javadoc('pi.resources.sound.Music', 'Music') }} (Musik).

## `Sound` (Klang)

{{ javadoc('pi.resources.sound.Sound', 'Sound') }} ist für Soundeffekte gedacht.
Es können mehrere Sounds gleichzeitig abgespielt werden. Die Sounds werden nur
einmal abgespielt und können nicht in einer Endlosschleife wiedergegeben werden.

## `Music` (Musik)

{{ javadoc('pi.resources.sound.Music', 'Music') }} besteht aus mindestens einem
oder beliebig vielen Sounds. Im Normalfall kann nur eine Musik gleichzeitig
abgespielt werden. Wird eine neue Musik gestartet, so stoppt die bisher
laufende. Eine Musik kann in einer Endlosschleife wiedergegeben werden. Es gibt
spezialisierte Unterklassen von Music, zum Beispiel IntroMusic. Hier werden zwei
Sounds zusammengefasst. Der erste Sound wird nur einmal abgespielt, der zweite
in einer Endlosschleife.
