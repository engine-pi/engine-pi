# `config` (Konfiguration)

Die Standardeinstellungen der Engine Pi können mithilfe einer
Konfigurationsdatei names `engine-pi.properties` verändert werden. Dabei handelt
es sich um eine
[Java-Properties-Datei](https://de.wikipedia.org/wiki/Java-Properties-Datei),
also ein sehr einfaches, selbsterklärend Format. Beim Starten eines Projekts
sucht die Engine Pi im Ausführungsorder der Anwendung nach dieser Datei. Falls
keine solche Datei vorhanden ist, erstellt die Engine eine
`engine-pi.properties` mit den Standardwerten.

```properties
game_instantMode=true
graphics_framerate=60
graphics_windowPosition=NONE
graphics_windowHeight=576
graphics_colorScheme=Gnome
graphics_pixelMultiplication=1
graphics_screenRecordingNFrames=2
graphics_windowWidth=768
sound_musicVolume=0.5
sound_soundVolume=0.5
debug_verbose=false
debug_actorCoordinates=false
debug_enabled=false
debug_renderActors=true
coordinatesystem_labelsOnIntersections=false
coordinatesystem_linesNMeter=-1
```

Die Konfigurationen sind in Konfigurationsgruppen, die jeweils ein Präfix haben,
organisiert:

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/config/Configuration.java -->

- `game_`: {{ class('pi.config.GameConfiguration') }}
- `graphics_`: {{ class('pi.config.GraphicsConfiguration') }}
- `sound_`: {{ class('pi.config.SoundConfiguration') }}
- `debug_`: {{ class('pi.config.DebugConfiguration') }}
- `coordinatesystem_`: {{ class('pi.config.CoordinatesystemConfiguration') }}

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/test/java/pi/config/ConfigurationTest.java -->

```java
import static pi.Controller.config;

assertEquals(true, config.game.instantMode());

assertEquals(768, config.graphics.windowWidth());
assertEquals(576, config.graphics.windowHeight());
assertEquals(Direction.NONE, config.graphics.windowPosition());
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

Die Engine Pi bietet die Möglichkeit, eigene Konfigurationsgruppen zu
definieren.[^litiengine:configuration]

{{ code('demos.docs.resources.config.MyCustomConfigurationGroup', from_import=True) }}

{{ code('demos.docs.resources.config.CustomConfigGroupDemo', from_import=True) }}

[^litiengine:configuration]: https://litiengine.com/docs/configuration/
