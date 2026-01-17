# `BodyType` (Verhalten von Figuren in der physikalischen Simulation)

<!-- http://127.0.0.1:8000/engine-pi/manual/physics/body-type/ -->
<!-- https://engine-pi.github.io/engine-pi/manual/physics/body-type/ -->

## Vergleich[^engine-alpha-wiki:body-types]

|                                                                             | STATIC | DYNAMIC | KINEMATIC | SENSOR | PARTICLE |
| --------------------------------------------------------------------------- | ------ | ------- | --------- | ------ | -------- |
| Kann mit Actor.anchor() bewegt werden                                       | ✔      | ✔       | ✔         | ✔      | ✔        |
| Beeinflusst von Schwerkraft                                                 |        | ✔       |           | ✔      | ✔        |
| Kann Impulse erhalten                                                       |        | ✔       |           | ✔      | ✔        |
| Kann eine Geschwindigkeit haben                                             |        | ✔       | ✔         | ✔      | ✔        |
| Hat physikalische Eigenschaften wie Masse/Dichte oder Reibungskoeffizienten |        | ✔       |           | ✔      | ✔        |

[^engine-alpha-wiki:body-types]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/4.x/Body_Types

{{ class('pi.physics.BodyType') }}

# Kinetisch

<!-- Lastly, let's see what a 'kinematic' body is all about. As we have seen so far,
dynamic bodies move and static bodies don't. When a static body and a dynamic
body collide, the static body always 'wins' and holds its ground, and the
dynamic body will retreat as necessary so that the two are not overlapping. A
kinematic body is very similar to a static body in that when it collides with a
dynamic body it always holds its ground and forces the dynamic body to retreat
out of the way. The difference is that a kinematic body can move. -->

Ein dynamischer Körper kann sich bewegen, statische Körper hingegen nicht. Wenn
ein statischer Körper und ein dynamischer Körper kollidieren, „gewinnt” immer
der statische Körper und bleibt an Ort und Stelle, während der dynamische Körper
zurückprallt, sodass sich die beiden Körper nicht überlappen. Ein kinematischer
Körper ähnelt einem statischen Körper insofern, als er bei einer Kollision mit
einem dynamischen Körper immer seine Position behält und den dynamischen Körper
zwingt, aus dem Weg zu gehen. Der Hauptunterschied zum statischen Körper ist,
dass sich ein kinematischer Körper bewegen kann.[^iforce2d]

[^iforce2d]: https://www.iforce2d.net/b2dtut/bodies
