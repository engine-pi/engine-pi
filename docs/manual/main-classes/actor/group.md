# `Group` (Gruppe)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/Group.java -->


Über eine  {{ class('pi.actor.Group', 'Gruppe') }} lassen sich mehrere Figure gemeinsam verwalten, z.B.
gleichzeitig bewegen oder einer Szene hinzufügen. Die Gruppe implementiert
`Iterable`, sodass sie direkt in einer `for`-Schleife verwendet
werden kann.

Die Klasse {{ class('pi.actor.Group') }} ist im
Paket {{ package('pi.actor') }} enthalten und kann über die Anweisung
`#!java import pi.actor.Group;`
importiert werden.

<!-- file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/group/GroupDemo.java -->

{{ video('docs/main-classes/actor/group/GroupDemo.mp4') }}

{{ code('demos.docs.main_classes.actor.group.GroupDemo') }}
