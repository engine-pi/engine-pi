# Konfiguration

Die Standardeinstellungen der Engine Pi können durch eine Konfigurationsdatei
mit dem Namen `engine-pi.properties` verändert werden. Bei
`engine-pi.properties` handelt es sich um eine
[Java-Properties-Datei](https://de.wikipedia.org/wiki/Java-Properties-Datei),
also um eine sehr einfaches Format, das selbsterklärend ist. Die Engine Pi sucht
beim Starten eines Projekts im Ausführungsorder der Anwendung nach dieser Datei.
Wenn keine derartige Datei vorhanden ist, erstellt die Engine eine
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

Die Konfigurationen sind in Konfigurationsgruppen mit jeweils einem Präfix
organisiert.

## Benutzerdefinierte Konfigurationsgruppen

Die Engine Pi bietet die Möglichkeit, eigene Konfigurationsgruppen zu
definieren, die für ein bestimmtes Projekt Konfigurationen
enthalten.[^litiengine:configuration]

{{ code('demos.docs.resources.config.MyCustomConfigurationGroup') }}

{{ code('demos.docs.resources.config.CustomConfigGroupDemo') }}

[^litiengine:configuration]: https://litiengine.com/docs/configuration/
