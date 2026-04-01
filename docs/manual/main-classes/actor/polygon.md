# `Polygon` (Polygon)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/Polygon.java -->

{{ class('pi.actor.Polygon') }}

## Ein Polygon in der Physik-Simulation

{{ video('docs/main-classes/actor/polygon/PolygonPhysicsDemo.mp4') }}

Wie alle anderen Figuren kann auch die {{ class('pi.actor.Polygon') }}-Figur in
einer Physik-Simulation verwendet werden. Das folgende Beispiel lässt das
Polygon auf einer Ebene abprallen. Damit das Polygon aus dem Spielfenster fliegt,
wird seine Fallrichtung mit einem nach rechts gerichteten Impuls
(`#!java new Vector(200, 0)`) beeinflusst.

{{ code('demos.docs.main_classes.actor.polygon.PolygonPhysicsDemo', 39, 54) }}
