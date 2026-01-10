# `gravity` (Schwerkraft)

<!-- http://127.0.0.1:8000/engine-pi/manual/physics/gravity/ -->
<!-- https://engine-pi.github.io/engine-pi/manual/physics/gravity/ -->

Wikipedia: [Gravitation](https://de.wikipedia.org/wiki/Gravitation)

!!! info

    Die Einheit der Schwerkraft (Gewichtskraft) ist das Newton ($N$), da Schwerkraft
    eine Kraft ist (Masse mal Beschleunigung: $F_G = m \cdot g$). Während die Masse in
    Kilogramm ($kg$) gemessen wird, wird die Stärke der Gravitationsbeschleunigung
    (Ortsfaktor $g$) oft in $\frac{N}{kg}$ oder $\frac{m}{s^{2}}$ (ca. $\frac{N}{kg}$ auf der
    Erde) angegeben. <!-- Früher wurde auch das Kilopond ($kp$) genutzt, aber heute ist
    Newton die Standardeinheit. -->
    [^google:aimode]

[^google:aimode]: https://share.google/aimode/hnAeb5oBR90fVXIgl

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/assets/docs/physics -->

{{ video('docs/physics/GravityDemo.mp4') }}

{{ methods('pi.Scene', ['gravity()', 'gravity(pi.Vector)', 'gravity(double,double)', 'gravity(pi.Vector)']) }}

{{ methods('pi.Layer', ['gravity()', 'gravity(pi.Vector)', 'gravity(double,double)', 'gravity(pi.Vector)']) }}

{{ code('docs/physics/GravityDemo', 32) }}
