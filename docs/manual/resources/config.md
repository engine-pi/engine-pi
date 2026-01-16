# `config` (Konfiguration)

Die Standardeinstellungen der Engine Pi können mithilfe einer
Konfigurationsdatei names `engine-pi.properties` verändert werden. Dabei handelt
es sich um eine
[Java-Properties-Datei](https://de.wikipedia.org/wiki/Java-Properties-Datei),
also ein sehr einfaches, selbsterklärend Format. Beim Starten eines Projekts
sucht die Engine Pi im Ausführungsorder der Anwendung nach dieser Datei. Falls
keine solche Datei vorhanden ist, erstellt die Engine eine
`engine-pi.properties` mit den Standardwerten:

```properties
game_instantMode=true

graphics_framerate=60
graphics_windowWidth=768
graphics_windowHeight=576
graphics_windowPosition=NONE
graphics_colorScheme=Gnome
graphics_pixelMultiplication=1
graphics_screenRecordingNFrames=2

sound_soundVolume=0.5
sound_musicVolume=0.5

debug_enabled=false
debug_verbose=false
debug_renderActors=true
debug_actorCoordinates=false

coordinatesystem_linesNMeter=-1
coordinatesystem_labelsOnIntersections=false
```

Die einzelnen Einstellmöglichkeiten sind in Konfigurationsgruppen, die jeweils ein Präfix haben,
organisiert:

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/config/Configuration.java -->

- `game_`: {{ class('pi.config.GameConfig') }}
- `graphics_`: {{ class('pi.config.GraphicsConfig') }}
- `sound_`: {{ class('pi.config.SoundConfig') }}
- `debug_`: {{ class('pi.config.DebugConfig') }}
- `coordinatesystem_`: {{ class('pi.config.CoordinatesystemConfig') }}

Das statische Attribut {{ method('pi.Controller', 'config', 'Controller.config')
}} der Klasse {{ class('pi.Controller') }} bietet Zugriff alle
Einstellmöglichkeiten.

```java
import pi.Controller;
// Controller.config.graphics.windowWidth()
```

Das `config`-Objekt kann auch über einen statischen Import eingebunden werden:
`#!java import static pi.Controller.config;`. Statt `#!java Controller.config`
kann dann etwas kürzer `#!java config` geschrieben werden.

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/test/java/pi/config/ConfigurationTest.java -->

Das folgende Codebeispiel testet alle Einstellmöglichkeit mit den Standardwerten:

```java
import static pi.Controller.config;

assertEquals(true, config.game.instantMode());

assertEquals(768, config.graphics.windowWidth());
assertEquals(576, config.graphics.windowHeight());
assertEquals(Direction.NONE, config.graphics.windowPosition());
assertEquals(32.0, config.graphics.pixelPerMeter());
assertEquals(0.05, config.graphics.zoomChange());
assertEquals(60, config.graphics.framerate());
assertEquals("Gnome", config.graphics.colorScheme());
assertEquals(1, config.graphics.pixelMultiplication());
assertEquals(2, config.graphics.screenRecordingNFrames());

assertEquals(0.5, config.sound.soundVolume());
assertEquals(0.5, config.sound.musicVolume());

assertEquals(false, config.debug.enabled());
assertEquals(false, config.debug.verbose());
assertEquals(true, config.debug.renderActors());
assertEquals(false, config.debug.actorCoordinates());

assertEquals(-1, config.coordinatesystem.linesNMeter());
assertEquals(false, config.coordinatesystem.labelsOnIntersections());
```

## Benutzerdefinierte Konfigurationsgruppen

Die Engine Pi bietet die Möglichkeit, auch eigene Konfigurationsgruppen zu
definieren und dem {{ method('pi.Controller', 'config') }}-Objekt
hinzuzufügen.[^litiengine:configuration] Die Klasse {{
class('demos.docs.resources.config.MyConfigGroup') }} definierte eine
Einstellmöglichkeit in Form eines Attributs mit dem Namen `myInt`. Die
dazugehörenden Getter- und Setter-Methoden haben - wie in der Engine Pi üblich -
kein `get`- bzw. `set`-Präfix:

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/resources/config/MyConfigGroup.java -->

{{ code('demos.docs.resources.config.MyConfigGroup', from_import=True) }}

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/resources/config/CustomConfigGroupDemo.java -->

{{ code('demos.docs.resources.config.CustomConfigGroupDemo', from_import=True) }}

[^litiengine:configuration]: https://litiengine.com/docs/configuration/
