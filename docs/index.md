# Engine Pi

Die [Engine Pi](https://github.com/engine-pi/engine-pi) ist eine
einsteigerfreundliche Java-Game-Engine für den Informatik-Unterricht an
allgemeinbildenden Schulen mit {{ package('pi', 'deutscher Dokumentation') }}.

{{ video('docs/MainAnimation.mp4', 'Die Hauptanimation der Engine Pi. <br>Sie erscheint, wenn <code>Game.start()</code> ohne Szene ausgeführt wird.') }}

## Bezug zur Engine Alpha

{{ image('logo/engine-alpha/engine-alpha.png', 'Logo der Engine Alpha') }}

Die Engine Pi ist eine Fortführung und Erweiterung (Fork) der
[Engine-Alpha](https://github.com/engine-alpha/engine-alpha) von [Michael
Andonie](https://github.com/andonie) und [Niklas
Keller](https://github.com/kelunik) und zwar ein Fork der [Core Engine
v4.x](https://github.com/engine-alpha/engine-alpha/tree/4.x/engine-alpha). Die
Engine Alpha wird leider offensichtlich nicht mehr weiterentwickelt. Die
[Engine-Alpha-Edu](https://github.com/engine-alpha/engine-alpha/tree/4.x/engine-alpha-edu)
Version mit deutschen Java Bezeichnern wurde nicht geforkt.

## Bezug zur LITIENGINE

{{ image('logo/litiengine/litiengine.png', 'Logo der LITIENGINE') }}

Da die [Engine Alpha](https://github.com/engine-alpha/engine-alpha) momentan keine
Audio-Wiedergabe unterstützt, wurde der Audio-Code der
[LITIENGINE](https://github.com/gurkenlabs/litiengine) in die Engine Pi übernommen.
Die LITIENGINE ist eine Java-2D-Game-Engine der bayerischen Entwickler
[Steffen Wilke](https://github.com/steffen-wilke) und
[Matthias Wilke](https://github.com/nightm4re94). Neben der Sound-Engine kommen
viele Klassen zur Resourcen-Verwaltung, einige Hilfsklassen sowie das
Tweening-Paket aus der LITIENGINE in der Engine Pi zum Einsatz.

{{ image('logo/Logos-forked-Engines.svg', 'Engine Alpha + LITIENGINE = Engine Pi') }}

Die Dokumentation ist unter folgender URL aufrufbar: https://engine-pi.github.io/engine-pi

{{ snippet('RotationDemo.java') }}
