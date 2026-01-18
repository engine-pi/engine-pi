# Tetris auf dem Gameboy

Nachbau des Spiels Tetris auf dem klassischem Gameboy mit Hilfe von Java und
der Engine Pi.

## Über das Spiel Tetris

### Bildschirmauflösung

Der Bildschirmausschnitt des klassische Gameboys ist `160x144` (Breite `x` Höhe)
Pixel. Ein Block hat die Größe `8x8`. Der Bildschirmausschnitt lässt
sich als mit `20x18` Blöcken ausfüllen.

### Hauptmodi

Es gibt zwei Hauptmodi: _„A-Type“_ und _„B-Type“_.

Im _„A-Type“_-Modus muss eine möglichst hohe Punktzahl erreicht werden, indem
Blockreihen auflöst werden, während die Tetrominos immer schneller fallen.

Im _„B-Type“_-Modus müssen 25 Reihen beseitigt werden, um zu siegen. Der
Schwierigkeitsgrad kann erhöht werden, indem
die Fallgeschwindigkeit heraufsetzt und dadurch mehr Tetrominos
eingestellt werden.[^nintendo]

### Drehung

Die Tetrominos drehen sich nur in eine Richtung, nämlich nach rechts.
Je nach Tetromino müssten unterschiedlich viele Zustände abgebildet werden.

- ein Zustand: `O`
- zwei Zustände: `I`, `Z` und `S`
- vier Zustände: `L`, `J` und `T`

Den begrenzten grafischen Möglichkeiten des originalen Gameboys geschuldet,
drehen sich die Tetrominos nicht geometrisch korrekt, sondern simulieren die
Rotation nur. Bei dieser Rotationssimulation sind je nach Tetromino mehrere
Möglichkeiten realisierbar.
In verschiedenen Internetforen wird vom `right handed` bzw.
`left handed` Rotationssystem gesprochen. Die Gameboy-Variante verwendet
das `left handed` System. Das `I`, `Z` und `S` sind bei der Drehung auf
linken Seite des Rotationsquadrats angesiedelt.
[^strategywiki]

![Blueprint](https://raw.githubusercontent.com/Josef-Friedrich/tetris/main/misc/Blueprint.svg)

### Farben

Der ursprüngliche Game Boy verwendet einen monochromen Bildschirm, der lediglich
vier verschiedene Farbschattierungen anzeigen kann.
Da der Hintergrund des nicht beleuchteten LCD-Displays grünlich ist, führt
dies zu einer „grünstichigen“ Grafikanzeige.

![](https://raw.githubusercontent.com/Josef-Friedrich/tetris/main/misc/Colors.svg)

| deutsch | englisch | grün[^gimp-green]       | grün[^wikipedia-green]   | grau[^mgba-gray]          |
| ------- | -------- | ----------------------- | ------------------------ | ------------------------- |
| weiß    | white    | `#aaaa00` `(170,170,0)` | `#7b8210` `(123,130,16)` | `#ffffff` `(255,255,255)` |
| hell    | light    | `#556633` `(85,102,52)` | `#5a7942` `(90,121,66)`  | `#adadad` `(173,173,173)` |
| dunkel  | dark     | `#335544` `(51,85,68)`  | `#39594a` `(57,89,74)`   | `#525252` `(82,82,82)`    |
| schwarz | black    | `#223322` `(34,51,34)`  | `#294139` `(41,65,57)`   | `#000000` `(0,0,0)`       |

### Start-Positionen

Die Tetrominos erscheinen auf der Koordinate `(4,16)` und als Vorschau auf der Koordinate `(14,3)`.

![Blueprint](https://raw.githubusercontent.com/Josef-Friedrich/tetris/main/misc/Start-Positions.svg)

### Geschwindigkeit

Gameboy läuft mit einer Framerate von `59.73` Bildern pro Sekunde.

![Die Tetris-ROM im Hex-Editor bei Byte `1B06h`](https://raw.githubusercontent.com/Josef-Friedrich/tetris/main/misc/graphics/Level-Speed-Table_1B06.png)

| Level              | 0     | 1     | 2     | 3     | 4     | 5     | 6     | 7     | 8     | 9     | 10    | 11    | 12    | 13    | 14    | 15    | 16    | 17    | 18    | 19    | 20    |
| ------------------ | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- |
| Frames per row     | 53    | 49    | 45    | 41    | 37    | 33    | 28    | 22    | 17    | 11    | 10    | 9     | 8     | 7     | 6     | 6     | 5     | 5     | 4     | 4     | 3     |
| Frames per row - 1 | 52    | 48    | 44    | 40    | 36    | 32    | 27    | 21    | 16    | 10    | 9     | 8     | 7     | 6     | 5     | 5     | 4     | 4     | 3     | 3     | 2     |
| Hexadezimal        | 0x34  | 0x30  | 0x2C  | 0x28  | 0x24  | 0x20  | 0x1B  | 0x15  | 0x10  | 0x0A  | 0x09  | 0x08  | 0x07  | 0x06  | 0x05  | 0x05  | 0x04  | 0x04  | 0x03  | 0x03  | 0x02  |
| Hexadezimal        | 34h   | 30h   | 2Ch   | 28h   | 24h   | 20h   | 1Bh   | 15h   | 10h   | 0Ah   | 09h   | 08h   | 07h   | 06h   | 05h   | 05h   | 04h   | 04h   | 03h   | 03h   | 02h   |
| Sekunden           | 0.887 | 0.820 | 0.753 | 0.686 | 0.619 | 0.552 | 0.469 | 0.368 | 0.285 | 0.184 | 0.167 | 0.151 | 0.134 | 0.117 | 0.100 | 0.100 | 0.084 | 0.084 | 0.067 | 0.067 | 0.050 |

Diese Tabelle befindet sich bei Byte `1B06h` in der ROM; `XXXh` steht für eine
hexadezimale Zahl `XXX`. Eine andere Schreibweise wäre `0xXXX`.
Jeder Eintrag ist um eins
kleiner als die tatsächliche Anzahl der Frames. So wird z.B. bei Level `1` `49`
(= `31h`) Frames als `30h` gespeichert.[^harddrop]

https://github.com/alexsteb/tetris_disassembly/blob/b4bbceb3cc086121ab4fe9bf4dad6752fe956ec0/main.asm#L4558-L4559

https://github.com/osnr/tetris/blob/4ca2f8bf3013a13a4c54d59ee03c929036045f93/tetris.asm#L3845-L3846

### Soft und Hard Drop

Ein **Soft Drop** ist eine Bewegung, bei dem ein Tetromino seine Abwärtsbewegung
beschleunigt. Dieser Zug bringt mehr Punkte, als den Tetromino von selbst fallen
zu lassen, aber weniger als ein Hard Drop.[^fandom]

Bei einem **Hard Drop** erreicht ein Tetromino sofort seine endgültige Position.

### Punkteverteilung / Scoring

| Level | Punkte für 1 Zeile | für 2 Zeilen   | für 3 Zeilen   | für 4 Zeilen    |
| ----- | ------------------ | -------------- | -------------- | --------------- |
| 0     | 40                 | 100            | 300            | 1200            |
| 1     | 80                 | 200            | 600            | 2400            |
| 2     | 120                | 300            | 900            | 3600            |
| ...   |                    |                |                |                 |
| 9     | 400                | 1000           | 3000           | 12000           |
| n     | 40 \* (n + 1)      | 100 \* (n + 1) | 300 \* (n + 1) | 1200 \* (n + 1) |

Neben der Bepunktung für abgebaute vollständige Zeilen, gibt es Punkte für durch
Soft-Drop platzierte Tetrominos. Die Punkteanzahl entspricht dabei der
Zeilenanzahl der kontinuierlichen, d. h. nicht unterbrochenen
Soft-Drop-Bewegung.[^harddrop_scoring] [^gamespot]

[^harddrop_scoring]: https://harddrop.com/wiki/Scoring
[^gamespot]: https://gamefaqs.gamespot.com/gameboy/585960-tetris/faqs/8483

### Animation bei getilgten Zeilen

Folgendes Bild zeigt eine im Programm Gimp geöffnete animierte PNG-Datei. In
dieser Animation ist zu sehen, wie eine Zeile getilgt wird.

![](https://raw.githubusercontent.com/engine-pi/tetris/main/misc/graphics/cleared-row/Screenshot-Gimp_animated-cleared-row.png)

1. ![](https://raw.githubusercontent.com/engine-pi/tetris/main/misc/graphics/cleared-row/01_row-visible.png) zu tilgende Zeile ist sichtbar
2. ![](https://raw.githubusercontent.com/engine-pi/tetris/main/misc/graphics/cleared-row/02_gray-overlay.png) graue Überblendung
3. ![](https://raw.githubusercontent.com/engine-pi/tetris/main/misc/graphics/cleared-row/03_row-visible.png) zu tilgende Zeile ist sichtbar
4. ![](https://raw.githubusercontent.com/engine-pi/tetris/main/misc/graphics/cleared-row/04_gray-overlay.png) graue Überblendung
5. ![](https://raw.githubusercontent.com/engine-pi/tetris/main/misc/graphics/cleared-row/05_row-visible.png) zu tilgende Zeile ist sichtbar
6. ![](https://raw.githubusercontent.com/engine-pi/tetris/main/misc/graphics/cleared-row/06_gray-overlay.png) graue Überblendung
7. ![](https://raw.githubusercontent.com/engine-pi/tetris/main/misc/graphics/cleared-row/07_row-visible.png) zu tilgende Zeile ist sichtbar
8. ![](https://raw.githubusercontent.com/engine-pi/tetris/main/misc/graphics/cleared-row/08_cleared.png) Die Zeile ist getilgt.
9. ![](https://raw.githubusercontent.com/engine-pi/tetris/main/misc/graphics/cleared-row/09_finished.png) Die Zeilen oberhalb sind nach unten gerutscht.

### Sound

[Game Boy Sound System](https://en.wikipedia.org/wiki/Game_Boy_Sound_System)

GBS-Dateien: [ocremix.org](https://ocremix.org/chip/265) [zophar.net](https://www.zophar.net/music/gameboy-gbs/tetris)

[gbsplay](https://github.com/mmitch/gbsplay)


| Title                     | Composer                 | Arranger        |
| :------------------------ | :----------------------- | :-------------- |
| Title Screen              | Hirokazu Tanaka          | Hirokazu Tanaka |
| A-Type (V1.0)             | Hirokazu Tanaka          | Hirokazu Tanaka |
| A-Type (V1.1) Korobeiniki | Traditional              | Hirokazu Tanaka |
| B-Type                    | Hirokazu Tanaka          | Hirokazu Tanaka |
| C-Type French Suite No. 3 | Johann Sebastian Bach    | Hirokazu Tanaka |
| Game Over                 | Hirokazu Tanaka          | Hirokazu Tanaka |
| Success!                  | Tommy Walker             | Hirokazu Tanaka |
| Trepak 1                  | Pyotr Ilyich Tchaikovsky | Hirokazu Tanaka |
| Trepak 2                  | Pyotr Ilyich Tchaikovsky | Hirokazu Tanaka |
| Trepak 3                  | Pyotr Ilyich Tchaikovsky | Hirokazu Tanaka |
| Trepak 4                  | Pyotr Ilyich Tchaikovsky | Hirokazu Tanaka |
| Trepak 5                  | Pyotr Ilyich Tchaikovsky | Hirokazu Tanaka |
| Trepak 6                  | Pyotr Ilyich Tchaikovsky | Hirokazu Tanaka |
| Rocket Blastoff           | Hirokazu Tanaka          | Hirokazu Tanak  |
| High Scores               | Hirokazu Tanaka          | Hirokazu Tanaka |
| Toréador Song             | Georges Bizet            | Hirokazu Tanaka |
| 2 Player ~ End of Round   | Hirokazu Tanaka          | Hirokazu Tanaka |
| 2 Player ~ Results        | Hirokazu Tanaka          | Hirokazu Tanaka |
[^vgmp-recording]

[^vgmp-recording]: https://www.vgmpf.com/Wiki/index.php?title=Tetris_(GB)#Recording

* [2-Player_End-of-Round.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/2-Player_End-of-Round.mp3)
* [2-Player_Results.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/2-Player_Results.mp3)
* [A-Type-Music_V1.0.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/A-Type-Music_V1.0.mp3)
* [A-Type-Music_V1.1-Korobeiniki.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/A-Type-Music_V1.1-Korobeiniki.mp3)
* [Block_drop.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Block_drop.mp3)
* [Block_move.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Block_move.mp3)
* [Block_rotate.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Block_rotate.mp3)
* [B-Type-Music.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/B-Type-Music.mp3)
* [C-Type-Music-French-Suite.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/C-Type-Music-French-Suite.mp3)
* [Game-Over.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Game-Over.mp3)
* [High-beep.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/High-beep.mp3)
* [High-Score-Intro.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/High-Score-Intro.mp3)
* [High-Score-Loop.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/High-Score-Loop.mp3)
* [Low-beep.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Low-beep.mp3)
* [Pause.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Pause.mp3)
* [Rocket-Blastoff.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Rocket-Blastoff.mp3)
* [Row_clear4.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Row_clear4.mp3)
* [Row_clear.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Row_clear.mp3)
* [Success.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Success.mp3)
* [Title-Intro.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Title-Intro.mp3)
* [Title-Loop.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Title-Loop.mp3)
* [Toreador-Song.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Toreador-Song.mp3)
* [Trepak-1.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Trepak-1.mp3)
* [Trepak-2.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Trepak-2.mp3)
* [Trepak-3.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Trepak-3.mp3)
* [Trepak-4.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Trepak-4.mp3)
* [Trepak-5.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Trepak-5.mp3)
* [Trepak-6.mp3](https://github.com/engine-pi/tetris/raw/main/misc/sounds/Trepak-6.mp3)

https://github.com/niuhuan/gbc-swing/blob/master/src/main/java/gbc/Speaker.java

https://github.com/trekawek/coffee-gb

### Bildschirme (`scenes`)

`CopyrightScene`

![CopyrightScene](https://raw.githubusercontent.com/Josef-Friedrich/tetris/main/misc/graphics/screenshots/CopyrightScreen.png)

`TitleScene`

![TitleScene](https://raw.githubusercontent.com/Josef-Friedrich/tetris/main/misc/graphics/screenshots/TitleScreen.png)

`MainMenuScene`

![MainMenuScene](https://raw.githubusercontent.com/Josef-Friedrich/tetris/main/misc/graphics/screenshots/MainMenuScreen.png)

`LevelSelectScene`

![LevelSelectScene](https://raw.githubusercontent.com/Josef-Friedrich/tetris/main/misc/graphics/screenshots/LevelSelectScreen.png)

`IngameScene`

![IngameScene](https://raw.githubusercontent.com/Josef-Friedrich/tetris/main/misc/graphics/screenshots/IngameScreen.png)

Das Spielfeld ist `8x18` groß. Der linke Rand ist `2` und der rechte
Rand `10` Blöcke breit.

`GameOverScene`

![GameOverScene](https://raw.githubusercontent.com/Josef-Friedrich/tetris/main/misc/graphics/screenshots/GameOverScreen.png)

`RussianDancersScene`

![RussianDancersScene](https://raw.githubusercontent.com/Josef-Friedrich/tetris/main/misc/graphics/screenshots/RussianDancersScreen.gif)

### Emulation

Es gibt eine Vielzahl sogenannter Emulatoren, mit denen der Gameboy
simuliert werden kann.

#### mGBA

Der Emulator
[mGBA](https://mgba.io/downloads.html) (mini Game Boy Advance) läuft auf
allen gängigen Desktop-Betriebssystemen und auf einigen Spielekonsolen.
_mGBA_ ist freie Software und wird unter der [Mozilla Public License
2.0](https://github.com/mgba-emu/mgba/blob/master/LICENSE)
veröffentlicht. Der Quellcode ist über
[Github](https://github.com/mgba-emu/mgba) abrufbar.

Um das Spiel in einem Emulator laufen zu lassen, ist eine sogenanntes
ROM notwendig, z.B. von
[emulatorgames.net](https://www.emulatorgames.net/roms/gameboy/tetris-jue-v11/)
oder aus diesem
[Repository](https://github.com/Josef-Friedrich/tetris/raw/main/misc/Tetris-ROM.gb).

Die Tastenkürzel wichtigsten Tastenkürzel des mGBA sind:

- A: X
- B: Z
- L: A
- R: S
- Start: Enter
- Select: Backspace

### Weiterführende Informationen

#### Allgemeine Informationen über das Spiel

- [englische Wikipedia](<https://en.wikipedia.org/wiki/Tetris_(Game_Boy_video_game)>)
- [harddrop.com (Tetris Wiki created by Tetris fans for Tetris fans)](https://harddrop.com/wiki/Tetris_%28Game_Boy%29)
- [Video Game Music Preservation Foundation](<http://www.vgmpf.com/Wiki/index.php?title=Tetris_(GB)>)

#### Youtube-Videos

- [Playthrough 16 Minuten, Gameboy-Display wurde abgefilmt](https://www.youtube.com/watch?v=BQwohHgrk2s)
- [Longplay 52 Minuten (schwarz-weiß Emulator)](https://www.youtube.com/watch?v=VNbo1AGqKrI)
- [short](https://www.youtube.com/shorts/30vVpKAMu6g)

#### Klone

- [canvas_tetris (javascript)](https://github.com/andyp123/canvas_tetris)
- [js-tetris](https://github.com/az23/js-tetris) (eventuell nicht fertig gestellt)

[^fandom]: https://tetris.fandom.com/wiki/Soft_Drop
[^gimp-green]: Ermittelt mit dem GIMP Color Picker mittels eines Bildschirmfotos des Videos https://www.youtube.com/watch?v=BQwohHgrk2s
[^harddrop]: https://harddrop.com/wiki/Tetris_(Game_Boy)
[^mgba-gray]: mGBA Emulator Settings / Gameboy / Game Boy palette / Grayscale Preset
[^nintendo]: https://www.nintendo.com/de-de/Spiele/Game-Boy/TETRIS--275924.html
[^strategywiki]: https://strategywiki.org/wiki/Tetris/Rotation_systems
[^wikipedia-green]: https://en.wikipedia.org/wiki/List_of_video_game_console_palettes#Game_Boy Original Game Boy Hex / Binary
