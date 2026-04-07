# `Group` (Gruppe)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/Group.java -->

Über eine {{ javadoc('pi.actor.Group', 'Gruppe') }} lassen sich mehrere Figuren
gemeinsam verwalten, z.B. gleichzeitig bewegen, einfärben oder einer Szene
hinzufügen. Eine Gruppe ist hilfreich, wenn mehrere Figuren als Einheit
behandelt werden sollen.
{{ javadoc('pi.actor.Group') }} ist keine Unterklasse von {{
javadoc('pi.actor.Actor') }}. Die Klasse {{ javadoc('pi.actor.Group') }} kann jedoch
mehrere {{ javadoc('pi.actor.Actor', 'Figuren') }} enhälten.
Die Klasse implementiert {{ javadoc('java.lang.Iterable') }}.
Dadurch kann eine Gruppe direkt in einer `for`-Schleife verwendet werden.

{{ import_admonition('pi.actor.Group') }}

<!-- file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/group/GroupDemo.java -->

{{ video('docs/main-classes/actor/group/GroupDemo.mp4') }}
/// caption
Aus zwei Kreisen und zwei Rechteck besteht das obige Gesicht.
Alle vier Figuren sind zu einer Gruppe zusammengefasst.
///

## Eine Gruppe erstellen

Beim Erzeugen können bereits beliebig viele Figuren übergeben werden:

```java
Circle leftEye = new Circle(3);
Circle rightEye = new Circle(3);
Rectangle nose = new Rectangle(1, 5);

Group<Actor> group = new Group<>(leftEye, rightEye, nose);
```

## Figuren verwalten

Mit
{{ javadoc('pi.actor.Group#add(T)', 'add(T)') }}
und
{{ javadoc('pi.actor.Group#remove(T)', 'remove(T)') }}
werden Figuren zur Gruppe hinzugefügt oder
aus ihr entfernt. Beide Methoden sind _chainable_, liefern also wieder die
gleiche Gruppeninstanz zurück.

```java
Group<Actor> group = new Group<>();

group.add(new Circle())
    .add(new Rectangle(2, 1));

group.remove(someActor);
```

Zusätzlich stehen
{{ javadoc('pi.actor.Group#size()', 'size()') }}
und
{{ javadoc('pi.actor.Group#isEmpty()', 'isEmpty()') }}
zur Verfügung:

```java
int amount = group.size();
boolean empty = group.isEmpty();
```

## Aktionen auf alle Figuren anwenden

Mit der Methode
{{ javadoc('pi.actor.Group#forEachActor(java.util.function.Consumer)', 'forEachActor(action)') }}
lässt sich dieselbe Aktion auf alle Figuren der Gruppe anwenden.

```java
group.forEachActor(actor -> actor.moveBy(1, 0));
```

Mit der Methode
{{ javadoc('pi.actor.Group#forEach(java.lang.Class,java.util.function.Consumer)', 'forEach(Class&lt;S&gt; clazz, Consumer&lt;? super S&gt; action)') }}
kann nach einem bestimmten Figurtyp gefiltert werden:

```java
group.forEach(Circle.class, circle -> circle.color("blue"));
group.forEach(Rectangle.class, rectangle -> rectangle.color("red"));
```

## Gruppe zur Szene hinzufügen

Mit der Methode
{{ javadoc('pi.actor.Group#addToScene(pi.Scene)', 'addToScene(scene)') }}
werden alle Figuren der Gruppe in einem Schritt zur
angegebenen Szene hinzugefügt.

```java
new Group<>(leftEye, rightEye, nose, mouth).addToScene(this);
```

## Iteration

Da {{ javadoc('pi.actor.Group') }} {{ javadoc('java.lang.Iterable') }} implementiert, kann die Gruppe direkt iteriert werden:

```java
for (Actor actor : group)
{
    System.out.println(actor);
}
```

## Demo

{{ code('demos.docs.main_classes.actor.group.GroupDemo') }}
