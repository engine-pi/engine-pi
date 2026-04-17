# Lambda-Ausdrücken

Mit Lambda-Ausdrücken kann man sich viel Schreibarbeit sparen. Klassen, die eine
sogenannten Funktionale Schnittstelle (Functional Interface) implementieren,
d. h. ein Interface mit genau einer abstrakten Methoden, können auch als
Lambda-Ausdruck formuliert werden.

Klasse, die das Interface/Schnittstelle `Runnable` implementiert.

```java
class MyRunnable implements Runnable
{
    public void run()
    {
        startTitleScene();
    }
}

delay(3, new MyRunnable());
```

Als anonyme Klasse

```java
delay(3, new Runnable()
{
    public void run()
    {
        startTitleScene();
    }
});
```

Als Lambda-Ausdruck (Name stammt vom [Lambda-Kalkül](https://de.wikipedia.org/wiki/Lambda-Kalk%C3%BCl) ab)

```java
delay(3, () -> startTitleScene());
```
