# `instantMode` (Instant-Modus)

Im sogenannten Instant-Modus werden die erzeugten Figuren sofort einer Szene
hinzugefügt und die Szene wird dann umgehend gestartet.

Der Instant-Modus der Engine Pi startet ein Projekt, ohne dass viel Code
geschrieben werden muss.

{{ methods('pi.Controller', ['instantMode(boolean)']) }}

{{ methods('pi.config.GameConfig', ['instantMode()', 'instantMode(boolean)']) }}

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/controller/instant_mode/InstantModeEnabled.java -->

{{ code('docs/main_classes/controller/instant_mode/InstantModeEnabled.java', line=27) }}

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/controller/instant_mode/InstantModeDisabled.java -->

Bei deaktivieren Instant-Modus muss wesentlich mehr Code geschrieben werden, um
das gleiche Ergebnis wie im obigen Code-Beispiel zu erzielen.

{{ code('docs/main_classes/controller/instant_mode/InstantModeDisabled.java', 25) }}

## Instant-Modus deaktivieren

Der Instant-Modus kann deaktiviert werden, indem vor `#!java Controller.start()`
die Methode `#!java Controller.instantMode(false)` aufgerufen wird.

{{ code('docs/main_classes/controller/instant_mode/InstantModeDisabled.java', 32, 36) }}

Manchmal ist es jedoch nicht ausreichend, direkt vor `#!java Controller.start()` den
Instant-Modus mittels `#!java Controller.instantMode(false)` zu deaktiveren.
Möglicherweise kann dann ein statischer Block Abhilfe schaffen:

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/controller/instant_mode/InstantModeDisabledByStaticBlock.java -->

{{ code('docs/main_classes/controller/instant_mode/InstantModeDisabledByStaticBlock.java', 25) }}

Der Instant-Modus lässt sich auch über die Konfigurationsdatei `engine-pi.properties`
deaktivieren:

```properties
game_instantMode=true
```
