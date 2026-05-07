# Generics

*Generics* (generische Typen, Typparameter) sind Platzhalter in der Programmierung
(z.B. `#!java <T>`), die es ermöglichen, Klassen, Schnittstellen und Methoden
mit verschiedenen Datentypen wiederverwendbar zu machen. Sie erhöhen die
Typsicherheit zur Kompilierzeit, da fehlerhafte Datentypen erkannt werden, bevor
das Programm läuft. *Generics* machen also Klassen und Methoden flexibel, ohne die
Typsicherheit zu verlieren.

Typische Schreibweise:

```java
ArrayList<String> names = new ArrayList<>();
```

Hier bedeutet `<String>`: Diese Liste darf nur Zeichenketten enthalten.

## Warum Generics?

Statt mit dem Datentyp `#!java Object` zu arbeiten und später zu casten, kann
direkt der gewünschte Datentyp direkt verwendet werden. Ohne *Generics* können
leicht Laufzeitfehler entstehen:

```java
ArrayList list = new ArrayList();
list.add("Max");
list.add(42);

String name = (String) list.get(1); // ClassCastException zur Laufzeit
```

Mit *Generics* wird der Fehler früh erkannt:

```java
ArrayList<String> list = new ArrayList<>();
list.add("Max");
// list.add(42); // Compilerfehler

String name = list.get(0); // kein Cast notwendig
```

Vorteile:

- weniger Casts
- Fehler schon beim Kompilieren
- besser lesbarer Code

## Eigene generische Klasse

Du kannst selbst Typparameter definieren, oft mit `T` (Type):

```java
public class Box<T>
{
	private T value;

	public void set(T value)
	{
		this.value = value;
	}

	public T get()
	{
		return value;
	}
}
```

Verwendung:

```java
Box<Integer> punktzahl = new Box<>();
punktzahl.set(100);
Integer wert = punktzahl.get();

Box<String> textBox = new Box<>();
textBox.set("Hallo");
```

## Generische Methoden

Nicht nur Klassen, auch Methoden können generisch sein:

```java
public static <T> void printArray(T[] array)
{
	for (T element : array)
	{
		System.out.println(element);
	}
}
```

Aufruf:

```java
String[] namen = {"Ada", "Linus"};
Integer[] zahlen = {1, 2, 3};

printArray(namen);
printArray(zahlen);
```

## Bounded Type Parameters

Manchmal soll ein Typparameter eingeschränkt sein, z. B. auf Zahlen:

```java
public static <T extends Number> double add(T a, T b)
{
	return a.doubleValue() + b.doubleValue();
}
```

Erlaubt sind dann nur Unterklassen von `Number` (z. B. `Integer`, `Double`).

## Wildcards

Wildcards nutzt man oft bei Collections.

### `? extends Typ`

`? extends Number` bedeutet: unbekannter Typ, der `Number` oder eine
Unterklasse davon ist.

```java
public static double sum(List<? extends Number> zahlen)
{
	double s = 0;
	for (Number n : zahlen)
	{
		s += n.doubleValue();
	}
	return s;
}
```

Du kannst sicher lesen, aber in der Regel nichts Neues hinzufügen.

### `? super Typ`

`? super Integer` bedeutet: unbekannter Typ, der `Integer` oder eine Oberklasse
davon ist.

```java
public static void fillWithZeros(List<? super Integer> liste)
{
	liste.add(0);
	liste.add(0);
}
```

Hier kannst du `Integer` sicher hinzufügen.

Merksatz (PECS):

- Producer: `extends`
- Consumer: `super`

## Wichtige Hinweise

- Generics funktionieren nur mit Referenztypen, nicht mit primitiven Typen.
  Also `List<Integer>` statt `List<int>`.
- Durch Type Erasure sind Typparameter zur Laufzeit weitgehend entfernt.
  Deshalb ist z. B. `new T()` nicht erlaubt.
- Nutze den Diamond-Operator `<>`, wenn der Typ rechts eindeutig ist.

```java
Map<String, Integer> punkte = new HashMap<>();
```

## Kurzfassung

Generics sorgen für sicheren und klaren Java-Code. Du definierst flexibel,
welcher Typ verwendet wird, und bekommst viele Fehler schon beim Kompilieren
statt erst zur Laufzeit.
