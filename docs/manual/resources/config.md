# `config` (Konfiguration)

{{ static_import_admonition('config') }}

Die Standardeinstellungen der Engine Pi können mithilfe einer
Konfigurationsdatei names `engine-pi.properties` verändert werden. Dabei handelt
es sich um eine
[Java-Properties-Datei](https://de.wikipedia.org/wiki/Java-Properties-Datei),
also ein sehr einfaches, selbsterklärend Format. Beim Starten eines Projekts
sucht die Engine Pi im Ausführungsordner der Anwendung nach dieser Datei. Falls
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
debug_renderActors=true
debug_actorCoordinates=false
debug_renderAABBs=false
debug_useANSIcolors=false

coordinatesystem_linesNMeter=-1
coordinatesystem_labelsOnIntersections=false
```

Die einzelnen Einstellmöglichkeiten sind in Konfigurationsgruppen, die jeweils
ein Präfix haben, organisiert:

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/config/Configuration.java -->

- `game_`: {{ javadoc('pi.config.GameConfig') }}
- `graphics_`: {{ javadoc('pi.config.GraphicsConfig') }}
- `sound_`: {{ javadoc('pi.config.SoundConfig') }}
- `debug_`: {{ javadoc('pi.config.DebugConfig') }}
- `coordinatesystem_`: {{ javadoc('pi.config.CoordinatesystemConfig') }}

Das statische Attribut {{ javadoc('pi.Controller#config', 'Controller.config')
}} der Klasse {{ javadoc('pi.Controller') }} bietet Zugriff auf alle
Einstellmöglichkeiten.

```java
import pi.Controller;
Controller.config.graphics.windowWidth()
```

Das `config`-Objekt kann auch über einen statischen Import eingebunden werden:
`#!java import static pi.Controller.config;`. Statt `#!java Controller.config`
kann dann etwas kürzer `#!java config` geschrieben werden.

```java
import static pi.Controller.config;
config.graphics.windowWidth()
```

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/test/java/pi/config/ConfigurationTest.java -->

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

assertFalse(config.debug.enabled());
assertTrue(config.debug.renderActors());
assertFalse(config.debug.actorCoordinates());
assertFalse(config.debug.renderAABBs());
assertFalse(config.debug.useANSIcolors());

assertEquals(-1, config.coordinatesystem.linesNMeter());
assertFalse(config.coordinatesystem.labelsOnIntersections());
```

## Benutzerdefinierte Konfigurationsgruppen

Die Engine Pi bietet die Möglichkeit, auch eigene Konfigurationsgruppen zu
definieren und dem {{ javadoc('pi.Controller#config') }}-Objekt
hinzuzufügen.[^litiengine:configuration]

Im folgenden Beispiel definiert die Klasse {{
javadoc('demos.docs.resources.config.MyConfigGroup') }} eine Einstellmöglichkeit
in Form eines Attributs mit dem Namen `myInt`. Die dazugehörenden Getter- und
Setter-Methoden haben - wie in der Engine Pi üblich - kein `get`- bzw.
`set`-Präfix:

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/resources/config/MyConfigGroup.java -->

```java
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.config.ConfigGroup;
import pi.config.ConfigGroupInfo;

@ConfigGroupInfo(prefix = "custom_")
public class MyConfigGroup extends ConfigGroup
{
    private int myInt = 23;

    @Getter
    public int myInt()
    {
        return myInt;
    }

    @Setter
    public void myInt(int myInt)
    {
        set("myInt", myInt);
    }
}
```

Im Setter sollte die Methode {{
javadoc('pi.config.ConfigGroup#set(java.lang.String,T)', 'set(String,T)') }}
(`#!java set("myInt", myInt);`) anstatt des Zuweisungsoperator (`#!java
this.myInt = myInt;`) verwendet werden, damit der {{
javadoc('pi.config.ConfigurationChangedListener') }} benachrichtigt werden kann.

Die Annotationen `#!java @Getter` und `#!java @Setter` sind nicht zwingend
notwendig, die Annotation `#!java @ConfigGroupInfo` jedoch schon. Die Annotation
`@ConfigGroupInfo(prefix = "custom_")` bewirkt, dass das Attribut `#!java myInt` als
`custom_myInt` in die Properties-Datei geschrieben wird.

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/resources/config/CustomConfigGroupDemo.java -->

Benutzerdefinierte Konfigurationsgruppen müssen mit Hilfe der Methode {{
javadoc('pi.config.ConfigLoader#add(pi.config.ConfigGroup...)',
'add(ConfigGroup...)') }} erst zum statischen Singleton-config-Objekt
hinzugefügt werden.

```java
import static pi.Controller.config;

import pi.Scene;

public class CustomConfigGroupDemo extends Scene
{
    static
    {
        MyConfigGroup custom = new MyConfigGroup();
        config.add(custom);
    }
}
```

Dann kann die Konfigurationsgruppe über die Methoden
{{ javadoc('pi.config.ConfigLoader#getGroup(java.lang.Class)', 'getGroup(Class)') }}

```java
MyConfigGroup custom = config.getGroup(MyConfigGroup.class);
System.out.println(custom.myInt());
```

oder {{ javadoc('pi.config.ConfigLoader#getGroup(java.lang.String)', 'getGroup(String)') }}

```java
MyConfigGroup custom = (MyConfigGroup) config.getGroup("custom_");
System.out.println(custom.myInt());
```

wieder abgerufen werden.

Damit die benutzerdefinierte Konfigurationsgruppen in die
`engine-pi.properties`-Datei geschrieben werden, muss {{
javadoc('pi.config.ConfigLoader#save()') }} aufgerufen werden.

```java
import static pi.Controller.config;
config.save();
```

[^litiengine:configuration]: https://litiengine.com/docs/configuration/
