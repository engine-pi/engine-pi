# Engine Pi

Die [Engine Pi](https://github.com/engine-pi/engine-pi) ist eine
einsteigerfreundliche Java-Game-Engine für den Informatik-Unterricht an
allgemeinbildenden Schulen mit {{ javadoc('pi', 'deutscher Dokumentation') }}.

{{ video('docs/MainAnimation.mp4', 'Die Hauptanimation der Engine Pi. <br>Sie erscheint, wenn <code>Controller.start()</code> ohne Szene ausgeführt wird.') }}

## Betreuer des Projekts

Josef Friedrich,
josef.friedrich@pirckheimer-gymnasium.de,
Fachschaftsleiter für Informatik am Pirckheimer-Gymnasium, Nürnberg

## Über den Namen „Engine Pi“

Da die Engine Pi ein Fork der
[Engine-Alpha](https://github.com/engine-alpha/engine-alpha) ist, musste ein
anderer griechischer Buchstabe gewählt werden.
[Pi](https://de.wikipedia.org/wiki/Kreiszahl) ($\pi$) ist ein in der Mathematik
sehr häufig verwendetes Symbol und Pi lässt sich auch zu Pi(rckheimer)
erweitern, oder zu Pi(rckheimer-Gymnasium), an dem die Engine entwickelt wird.

## Bezug zur Engine Alpha

{{ image('logo/related/Engine-Alpha.png', 'Logo der Engine Alpha') }}

Die Engine Pi ist eine Fortführung und Erweiterung (Fork) der
[Engine-Alpha](https://github.com/engine-alpha/engine-alpha) von [Michael
Andonie](https://github.com/andonie) und [Niklas
Keller](https://github.com/kelunik) und zwar ein Fork der [Core Engine
v4.x](https://github.com/engine-alpha/engine-alpha/tree/4.x/engine-alpha). Die
Engine Alpha wird leider offensichtlich nicht mehr weiterentwickelt. Die
[Engine-Alpha-Edu](https://github.com/engine-alpha/engine-alpha/tree/4.x/engine-alpha-edu)
Version mit deutschen Java Bezeichnern wurde nicht geforkt.

## Bezug zur LITIENGINE

{{ image('logo/related/LITIENGINE.png', 'Logo der LITIENGINE') }}

Da die [Engine Alpha](https://github.com/engine-alpha/engine-alpha) momentan keine
Audio-Wiedergabe unterstützt, wurde der Audio-Code der
[LITIENGINE](https://github.com/gurkenlabs/litiengine) in die Engine Pi übernommen.
Die LITIENGINE ist eine Java-2D-Game-Engine der bayerischen Entwickler
[Steffen Wilke](https://github.com/steffen-wilke) und
[Matthias Wilke](https://github.com/nightm4re94). Neben der Sound-Engine kommen
viele Klassen zur Resourcen-Verwaltung, einige Hilfsklassen sowie das
Tweening-Paket aus der LITIENGINE in der Engine Pi zum Einsatz.

{{ image('logo/Logos-forked-Engines.svg', 'Engine Alpha + LITIENGINE = Engine Pi') }}

## Standing on the shoulders of giants

[Standing on the shoulders of
giants](https://en.wikipedia.org/wiki/Standing_on_the_shoulders_of_giants) oder
auf deutsch [Zwerge auf den Schultern von
Riesen](https://de.wikipedia.org/wiki/Zwerge_auf_den_Schultern_von_Riesen) ist
eine Metapher, die das Verhältnis der jeweils aktuellen Wissenschaft und Kultur
zu Tradition und den Leistungen früherer Generationen darstellt.[^wikipedia]

{{ image('logo/Standing-on-the-shoulders-of-giants.svg', 'Standing on the shoulders of giants') }}

Die Engine Pi wäre ohne folgende Projekte nicht möglich gewesen:

[^wikipedia]: https://de.wikipedia.org/wiki/Zwerge_auf_den_Schultern_von_Riesen

    Die Redewendung von den Zwergen auf den Schultern von Riesen (oder Giganten;
    lateinische Phrase Nanos gigantum humeris insidentes, „Zwerge auf den
    Schultern von Riesen sitzend“) ist eine Metapher, das Verhältnis der jeweils
    aktuellen Wissenschaft und Kultur zu Tradition und den Leistungen früherer
    Generationen darzustellen. Aus der Sicht traditionsbewusster Gelehrter
    erscheinen deren Vorgänger in vergangenen Epochen als Riesen und sie selbst
    als Zwerge, die von den Pionierleistungen der Vergangenheit profitieren:
    Indem sie dem vorgefundenen Wissensschatz ihren eigenen bescheidenen Beitrag
    hinzufügen, kommt Fortschritt zustande.

- [Engine Alpha](https://github.com/engine-alpha/engine-alpha) verwendet als Physics-Engine JBox2D.
    - [JBox2D](https://github.com/jbox2d/jbox2d) ist ein Java Port der früheren C++- und heute C-Physics-Engine [Box2D](https://box2d.org).
        - [LiquidFun](https://github.com/google/liquidfun) ist eine Erweiterung von Box2D. Es ergänzt das auf die Simulation von starren Körpern beschränkte Box2D um eine partikelbasierte Flüssigkeits- und Weichkörpersimulation.
            - [Box2D](https://box2d.org)
- [LITIENGINE](https://github.com/gurkenlabs/litiengine)
    - [Java](https://openjdk.org)

Herzlichen Dank an die vielen Open-Source- bzw. Free-Software-Entwickler und -Entwicklerinnnen.
