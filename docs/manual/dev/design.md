# `design` (Optisches Erscheinungsbild)

## Farbgebung der einfachen geometrischen Figuren

[^blog.interface.com]:
    https://blog.interface.com/de/004-farbenlehre-am-bauhaus
    Die Harmonielehre Ittens besagt, dass sich Farben in einem harmonischen
    Gleichgewicht befinden, wenn das Auge sie zu einer Totalität ergänzen kann und
    wenn die Mischung zweier oder mehrerer Farben ein neutrales Grau ergibt. Itten
    ordnete in seinen Untersuchungen zum Verhältnis zwischen Farbe und Form der
    Farbe Rot das Quadrat, der Farbe Gelb das Dreieck und der Farbe Blau den Kreis
    zu.

Die Färbung der einfachen geometrischen Figuren {{ class('pi.Circle',
'Kreis') }}, {{ class('pi.Rectangle', 'Rechteck') }} und {{
class('pi.Triangle', 'Dreieck') }} folgt der Farbenlehre am
Bauhaus:[^blog.interface.com]

[^blog.interface.com]:
    https://blog.interface.com/de/004-farbenlehre-am-bauhaus
    Die Harmonielehre Ittens besagt, dass sich Farben in einem harmonischen
    Gleichgewicht befinden, wenn das Auge sie zu einer Totalität ergänzen kann und
    wenn die Mischung zweier oder mehrerer Farben ein neutrales Grau ergibt. Itten
    ordnete in seinen Untersuchungen zum Verhältnis zwischen Farbe und Form der
    Farbe Rot das Quadrat, der Farbe Gelb das Dreieck und der Farbe Blau den Kreis
    zu.

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/dev/design/SimpleGeometricActorsDemo.java -->

{{ image('docs/dev/design/SimpleGeometricActorsDemo.png', 'Die einfachen geometrischen Figuren Kreis, Rechteck und Dreieck dargestellt durch die Figuren <code>Circle</code>, <code>Rectangle</code> und <code>Triangle</code>') }}

{{ code('docs/dev/design/SimpleGeometricActorsDemo', 39, 41) }}

### `Circle` (Kreis)

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/Circle.java -->

Der {{ class('pi.Circle', 'Kreis') }} ist standardmäßig **blau** gefärbt. Die Farbe Blau wirkt für
[Itten](https://de.wikipedia.org/wiki/Johannes_Itten) rund, erweckt ein Gefühl
der Entspanntheit und Bewegung und steht für den „in sich bewegten Geist“, wie
er sich ausdrückt. Der Kreis entspricht der Farbe Blau, da er ein Symbol der
„stetigen Bewegung“ darstelle.

### `Rectangle` (Rechteck)

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/Rectangle.java -->

Das {{ class('pi.Rectangle', 'Rechteck') }} ist standardmäßig **rot**
gefärbt. Die Farbe Rot stellt für
[Itten](https://de.wikipedia.org/wiki/Johannes_Itten) die körperhafte Materie
dar. Sie wirkt statisch und schwer. Er ordnet deshalb der Farbe die statische
Form des Quadrates zu.

### `Triangle` (Dreieck)

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/Triangle.java -->

Das {{ class('pi.Triangle', 'Dreieck') }} ist standardmäßig **gelb**
gefärbt. Gelb steht bei [Itten](https://de.wikipedia.org/wiki/Johannes_Itten)
für den Geist und das Denken. Gelb zeigt sich kämpferisch und aggressiv, besitzt
einen schwerelosen Charakter und diesem Charakter entspricht laut Itten das
Dreieck.[^byk-instruments.com]

[^byk-instruments.com]:
    https://www.byk-instruments.com/de/color-determines-shape
    September 9, 2005 - Source: www.farbimpulse.de - The online magazine for
    color in science and technology

## Farbschema

Standardmäßig verwendet die Engine Pi ein Farbschema nach den Farben der
<a href= "https://developer.gnome.org/hig/reference/palette.html">GNOME
Human Interface Guidelines</a>.

<table><thead>
  <tr>
    <th></th>
    <th>Name</th>
    <th>RGB</th>
    <th>Hexadecimal</th>
  </tr></thead>
<tbody>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTUzLCAxOTMsIDI0MSkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Blue 1' from the GNOME HIG palette"></td>
    <td>Blue 1</td>
    <td>(153, 193, 241)</td>
    <td>#99c1f1</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoOTgsIDE2MCwgMjM0KSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Blue 2' from the GNOME HIG palette"></td>
    <td>Blue 2</td>
    <td>(98, 160, 234)</td>
    <td>#62a0ea</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoNTMsIDEzMiwgMjI4KSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Blue 3' from the GNOME HIG palette"></td>
    <td>Blue 3</td>
    <td>(53, 132, 228)</td>
    <td>#3584e4</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjgsIDExMywgMjE2KSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Blue 4' from the GNOME HIG palette"></td>
    <td>Blue 4</td>
    <td>(28, 113, 216)</td>
    <td>#1c71d8</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjYsIDk1LCAxODApIiAvPgogICAgPC9zdmc+CiAgICA=" alt="Color 'Blue 5' from the GNOME HIG palette"></td>
    <td>Blue 5</td>
    <td>(26, 95, 180)</td>
    <td>#1a5fb4</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTQzLCAyNDAsIDE2NCkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Green 1' from the GNOME HIG palette"></td>
    <td>Green 1</td>
    <td>(143, 240, 164)</td>
    <td>#8ff0a4</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoODcsIDIyNywgMTM3KSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Green 2' from the GNOME HIG palette"></td>
    <td>Green 2</td>
    <td>(87, 227, 137)</td>
    <td>#57e389</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoNTEsIDIwOSwgMTIyKSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Green 3' from the GNOME HIG palette"></td>
    <td>Green 3</td>
    <td>(51, 209, 122)</td>
    <td>#33d17a</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoNDYsIDE5NCwgMTI2KSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Green 4' from the GNOME HIG palette"></td>
    <td>Green 4</td>
    <td>(46, 194, 126)</td>
    <td>#2ec27e</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMzgsIDE2MiwgMTA1KSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Green 5' from the GNOME HIG palette"></td>
    <td>Green 5</td>
    <td>(38, 162, 105)</td>
    <td>#26a269</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjQ5LCAyNDAsIDEwNykiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Yellow 1' from the GNOME HIG palette"></td>
    <td>Yellow 1</td>
    <td>(249, 240, 107)</td>
    <td>#f9f06b</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjQ4LCAyMjgsIDkyKSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Yellow 2' from the GNOME HIG palette"></td>
    <td>Yellow 2</td>
    <td>(248, 228, 92)</td>
    <td>#f8e45c</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjQ2LCAyMTEsIDQ1KSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Yellow 3' from the GNOME HIG palette"></td>
    <td>Yellow 3</td>
    <td>(246, 211, 45)</td>
    <td>#f6d32d</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjQ1LCAxOTQsIDE3KSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Yellow 4' from the GNOME HIG palette"></td>
    <td>Yellow 4</td>
    <td>(245, 194, 17)</td>
    <td>#f5c211</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjI5LCAxNjUsIDEwKSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Yellow 5' from the GNOME HIG palette"></td>
    <td>Yellow 5</td>
    <td>(229, 165, 10)</td>
    <td>#e5a50a</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjU1LCAxOTAsIDExMSkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Orange 1' from the GNOME HIG palette"></td>
    <td>Orange 1</td>
    <td>(255, 190, 111)</td>
    <td>#ffbe6f</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjU1LCAxNjMsIDcyKSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Orange 2' from the GNOME HIG palette"></td>
    <td>Orange 2</td>
    <td>(255, 163, 72)</td>
    <td>#ffa348</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjU1LCAxMjAsIDApIiAvPgogICAgPC9zdmc+CiAgICA=" alt="Color 'Orange 3' from the GNOME HIG palette"></td>
    <td>Orange 3</td>
    <td>(255, 120, 0)</td>
    <td>#ff7800</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjMwLCA5NywgMCkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Orange 4' from the GNOME HIG palette"></td>
    <td>Orange 4</td>
    <td>(230, 97, 0)</td>
    <td>#e66100</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTk4LCA3MCwgMCkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Orange 5' from the GNOME HIG palette"></td>
    <td>Orange 5</td>
    <td>(198, 70, 0)</td>
    <td>#c64600</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjQ2LCA5NywgODEpIiAvPgogICAgPC9zdmc+CiAgICA=" alt="Color 'Red 1' from the GNOME HIG palette"></td>
    <td>Red 1</td>
    <td>(246, 97, 81)</td>
    <td>#f66151</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjM3LCA1MSwgNTkpIiAvPgogICAgPC9zdmc+CiAgICA=" alt="Color 'Red 2' from the GNOME HIG palette"></td>
    <td>Red 2</td>
    <td>(237, 51, 59)</td>
    <td>#ed333b</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjI0LCAyNywgMzYpIiAvPgogICAgPC9zdmc+CiAgICA=" alt="Color 'Red 3' from the GNOME HIG palette"></td>
    <td>Red 3</td>
    <td>(224, 27, 36)</td>
    <td>#e01b24</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTkyLCAyOCwgNDApIiAvPgogICAgPC9zdmc+CiAgICA=" alt="Color 'Red 4' from the GNOME HIG palette"></td>
    <td>Red 4</td>
    <td>(192, 28, 40)</td>
    <td>#c01c28</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTY1LCAyOSwgNDUpIiAvPgogICAgPC9zdmc+CiAgICA=" alt="Color 'Red 5' from the GNOME HIG palette"></td>
    <td>Red 5</td>
    <td>(165, 29, 45)</td>
    <td>#a51d2d</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjIwLCAxMzgsIDIyMSkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Purple 1' from the GNOME HIG palette"></td>
    <td>Purple 1</td>
    <td>(220, 138, 221)</td>
    <td>#dc8add</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTkyLCA5NywgMjAzKSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Purple 2' from the GNOME HIG palette"></td>
    <td>Purple 2</td>
    <td>(192, 97, 203)</td>
    <td>#c061cb</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTQ1LCA2NSwgMTcyKSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Purple 3' from the GNOME HIG palette"></td>
    <td>Purple 3</td>
    <td>(145, 65, 172)</td>
    <td>#9141ac</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTI5LCA2MSwgMTU2KSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Purple 4' from the GNOME HIG palette"></td>
    <td>Purple 4</td>
    <td>(129, 61, 156)</td>
    <td>#813d9c</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoOTcsIDUzLCAxMzEpIiAvPgogICAgPC9zdmc+CiAgICA=" alt="Color 'Purple 5' from the GNOME HIG palette"></td>
    <td>Purple 5</td>
    <td>(97, 53, 131)</td>
    <td>#613583</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjA1LCAxNzEsIDE0MykiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Brown 1' from the GNOME HIG palette"></td>
    <td>Brown 1</td>
    <td>(205, 171, 143)</td>
    <td>#cdab8f</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTgxLCAxMzEsIDkwKSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Brown 2' from the GNOME HIG palette"></td>
    <td>Brown 2</td>
    <td>(181, 131, 90)</td>
    <td>#b5835a</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTUyLCAxMDYsIDY4KSIgLz4KICAgIDwvc3ZnPgogICAg" alt="Color 'Brown 3' from the GNOME HIG palette"></td>
    <td>Brown 3</td>
    <td>(152, 106, 68)</td>
    <td>#986a44</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTM0LCA5NCwgNjApIiAvPgogICAgPC9zdmc+CiAgICA=" alt="Color 'Brown 4' from the GNOME HIG palette"></td>
    <td>Brown 4</td>
    <td>(134, 94, 60)</td>
    <td>#865e3c</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoOTksIDY5LCA0NCkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Brown 5' from the GNOME HIG palette"></td>
    <td>Brown 5</td>
    <td>(99, 69, 44)</td>
    <td>#63452c</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjU1LCAyNTUsIDI1NSkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Light 1' from the GNOME HIG palette"></td>
    <td>Light 1</td>
    <td>(255, 255, 255)</td>
    <td>#ffffff</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjQ2LCAyNDUsIDI0NCkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Light 2' from the GNOME HIG palette"></td>
    <td>Light 2</td>
    <td>(246, 245, 244)</td>
    <td>#f6f5f4</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMjIyLCAyMjEsIDIxOCkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Light 3' from the GNOME HIG palette"></td>
    <td>Light 3</td>
    <td>(222, 221, 218)</td>
    <td>#deddda</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTkyLCAxOTEsIDE4OCkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Light 4' from the GNOME HIG palette"></td>
    <td>Light 4</td>
    <td>(192, 191, 188)</td>
    <td>#c0bfbc</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTU0LCAxNTMsIDE1MCkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Light 5' from the GNOME HIG palette"></td>
    <td>Light 5</td>
    <td>(154, 153, 150)</td>
    <td>#9a9996</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMTE5LCAxMTgsIDEyMykiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Dark 1' from the GNOME HIG palette"></td>
    <td>Dark 1</td>
    <td>(119, 118, 123)</td>
    <td>#77767b</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoOTQsIDkyLCAxMDApIiAvPgogICAgPC9zdmc+CiAgICA=" alt="Color 'Dark 2' from the GNOME HIG palette"></td>
    <td>Dark 2</td>
    <td>(94, 92, 100)</td>
    <td>#5e5c64</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoNjEsIDU2LCA3MCkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Dark 3' from the GNOME HIG palette"></td>
    <td>Dark 3</td>
    <td>(61, 56, 70)</td>
    <td>#3d3846</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMzYsIDMxLCA0OSkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Dark 4' from the GNOME HIG palette"></td>
    <td>Dark 4</td>
    <td>(36, 31, 49)</td>
    <td>#241f31</td>
  </tr>
  <tr>
    <td><img src="data:image/svg+xml;base64,CiAgICA8c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiCiAgICAgICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICAgICAgIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4KCiAgICA8cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJyZ2IoMCwgMCwgMCkiIC8+CiAgICA8L3N2Zz4KICAgIA==" alt="Color 'Dark 5' from the GNOME HIG palette"></td>
    <td>Dark 5</td>
    <td>(0, 0, 0)</td>
    <td>#000000</td>
  </tr>
</tbody></table>

## Schriftart

Die Engine Pi liefert die Schriftart [Cantarell](https://cantarell.gnome.org)
aus.
[Cantarell](https://gitlab.gnome.org/GNOME/gsettings-desktop-schemas/-/blob/8df894aecbf6908c48c3da62434061bfa8dc46ea/schemas/org.gnome.desktop.interface.gschema.xml.in#L108)
war lange Zeit die Standardschrift des Gnome-Projekts.

## Logo

{{ image('logo/logo.svg') }}
