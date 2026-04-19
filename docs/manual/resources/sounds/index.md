# `sounds` (Klänge)

{{ static_import_admonition('sounds') }}

![](./package-sounds.drawio)


Die Engine unterscheidet zwischen zwei Klassen von Audioquellen. Sound und Music.

Sound ist für Soundeffekte gedacht. Es können mehrere Sounds gleichzeitig
abgespielt werden. Die Sounds werden nur einmal abgespielt und können nicht in
einer Endlosschleife wiedergegeben werden.

Musik besteht aus mindestens einem oder beliebig vielen Sounds. Im Normalfall
kann nur eine Musik gleichzeitig abgespielt werden. Wird eine neue Musik
gestartet, so stoppt die bisher laufende Musik. Eine Music kann in einer
Endlosschleife wiedergegeben werden. Es gibt spezialisierte Unterklassen von
Music, zum Beispiel IntroMusic. Hier werden zwei Sounds zusammengefasst. Der
erste Sound wird nur einmal abgespielt, der zweite in einer Endlosschleife.

{{ contribute() }}
