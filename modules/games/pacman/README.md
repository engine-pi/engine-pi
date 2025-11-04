# Pacman-Klon auf der Engine Pi

Pac-Man ist ein Arcade- und Videospiel, welches
erstmals am 22. Mai 1980 von Namco in Japan als „Puck Man“ für Arcade-Automaten
veröffentlicht wurde.[^wikipedia]

Der Name leitet sich von der japanischen Lautmalerei paku paku für „wiederholt
den Mund öffnen und schließen“ und der Puck-förmigen Gestalt der Hauptfigur her;
zudem bedeutet puck auf Englisch auch „Kobold“. Es wurde vom Spieledesigner Tōru
Iwatani entworfen. Am 26. Oktober 1980 erschien das Spiel von Midway lizenziert
als Pac-Man in den USA. Der Name wurde geändert, da man befürchtete, dass die
Aufschrift „Puck Man“ auf dem Automaten in „Fuck Man“ abgewandelt werden könnte.
Spielerisch blieb es unverändert, außer dass die vier Geister im Spiel andere
Namen erhielten.[^wikipedia]

## Emulation

Das Original kann heute mit einem Emulator (z.
B. MAME) gespielt werden, wobei die eigentlichen Spieldateien (ROMs) bei
universellen Emulatoren nicht inbegriffen sind.[^wikipedia]

```sh
sudo apt install mame
```

## Originale Arcade-Automaten-Hardware

Steuerung Joystick (4 Richtungen)
Gehäuse Standard und Cocktail
Arcade-System CPU: Zilog Z80 getaktet mit 3,072 MHz
Monitor Raster,
Auflösung: 224 × 288 (Vertikal), 16 Farben[^wikipedia]

## Abmessungen

Schrift 8x8 Geister 16x16

224 × 288 = (28 * 8) x (36 * 8)

## Farben

[^almightyguru]
[^aarongiles]

| Index | Color                | Converted RGB[^almightyguru] | AllAssetsPalettes | Uses                                                        |
| ----- | -------------------- | ---------------------------- | ----------------- | ----------------------------------------------------------- |
| 0     | Black (schwarz)      | 0, 0, 0                      | 0, 0, 0           | Background                                                  |
| 1     | Red (rot)            | 252, 0, 0                    | 255, 0, 0         | Blinky, cherry, strawberry, apple, Galaxian ship            |
| 2     | Brown (braun)        | 216, 144, 85                 | 222, 151, 81      | Orange top, cherry stem                                     |
| 3     | Pink (rosa)          | 252, 180, 255                | 255, 183, 255     | Pinky, ghost house door                                     |
| 5     | Cyan (blaugrün)      | 0, 252, 255                  | 0, 255, 255       | Inky, player text                                           |
| 6     | Blue (blau)          | 72, 180, 255                 | 71, 183, 255      | Key top, bell bottom                                        |
| 7     | Orange (orange)      | 252, 180, 85                 | 255, 183, 81      | Clyde, orange                                               |
| 9     | Yellow (gelb)        | 252, 252, 0                  | 255, 255, 0       | Pac-Man, ready!, bell, Galaxian ship                        |
| B     | Indigo (indigo)      | 36, 36, 255                  | 33, 33, 255       | Maze walls, flashing ghosts, ghost pupils, Galaxian ship    |
| C     | Green (grün)         | 0, 252, 0                    | 0, 255, 255       | Melon, strawberry top, orange leaf                          |
| D     | Teal (türkis)        | 72, 180, 170                 | 71, 183, 174      | Melon wrinkles and stem                                     |
| E     | Salmon (lachsfarben) | 252, 180, 170                | 255, 183, 174     | Dots, ghost body, ghost face when flashing                  |
| F     | White (weiß)         | 252, 252, 255                | 222, 222, 255     | Flashing ghosts, scores, text, ghost eyes, fruit highlights |

[^almightyguru]: http://www.thealmightyguru.com/Wiki/index.php?title=Pac-Man#Color_Palette
[^aarongiles]: https://aarongiles.com/mamemem/part3.html

## Links

- [Pacman Emulator (dotnet)](https://github.com/Justin-Credible/pac-man-emulator)
- [Longplay Arcade Game (1980 Namco (Midway License for US release)](https://www.youtube.com/watch?v=dScq4P5gn4A)

[^wikipedia]: https://de.wikipedia.org/wiki/Pac-Man
