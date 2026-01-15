# `instantMode` (Instant-Modus)

Im sogenannten Instant-Modus werden die erzeugten Figuren sofort einer Szene
hinzugefügt und die Szene wird dann umgehend gestartet.

Der Instant-Modus der Engine Pi startet ein Projekt, ohne dass viel Code
geschrieben werden muss.

{{ methods('pi.Controller', ['instantMode(boolean)']) }}

{{ methods('pi.config.GameConfiguration', ['instantMode()', 'instantMode(boolean)']) }}

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/controller/instant_mode/InstantModeEnabled.java -->

{{ code('docs/main_classes/controller/instant_mode/InstantModeEnabled.java', line=27) }}

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/controller/instant_mode/InstantModeDisabled.java -->

Bei deaktivieren Instant-Modus muss wesentlich mehr Code geschrieben werden, um
das gleiche Ergebnis wie im obigen Code-Beispiel zu erzielen.

{{ code('docs/main_classes/controller/instant_mode/InstantModeDisabled.java', 25) }}

Manchmal ist es nicht ausreichend, direkt vor `Controller.start()` den
Instant-Modus mittels `Controller.instantMode(false)` zu deaktiveren.

Möglicherweise kann ein statischer Block Abhilfe schaffen:

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/controller/instant_mode/InstantModeDisabledByStaticBlock.java -->

{{ code('docs/main_classes/controller/instant_mode/InstantModeDisabledByStaticBlock.java', 25) }}

Oder der Instant-Modus wird über die Konfigurationsdatei `engine-pi.properties`
deaktiviert:

```properties
game_instantMode=true
```
