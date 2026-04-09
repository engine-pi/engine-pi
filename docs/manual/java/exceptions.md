# `Exceptions` (Ausnahmen)

Eine [Exception](https://de.wikipedia.org/wiki/Ausnahmebehandlung) ist ein
Laufzeitfehler, der den normalen Programmablauf unterbrechen kann. Statt das
Programm sofort abstürzen zu lassen, kann Java solche Fehler als Objekte
behandeln und gezielt abfangen.

Typische Beispiele:

- Datei nicht gefunden
- Division durch `0`
- Zugriff auf ein ungültiges Array-Element
- `null`-Wert wird wie ein Objekt verwendet

## `try`, `catch`, `finally`

Fehleranfälliger Code steht in einem `try`-Block. Tritt eine Exception auf,
wird der passende `catch`-Block ausgeführt.

```java
try
{
    int[] numbers = { 1, 2, 3 };
    System.out.println(numbers[5]);
}
catch (ArrayIndexOutOfBoundsException e)
{
    System.out.println("Ungültiger Index: " + e.getMessage());
}
```

Der optionale `finally`-Block wird immer ausgeführt, auch bei Fehlern. Er wird
oft für Aufräumarbeiten genutzt (z. B. Streams schließen).

```java
try
{
    // arbeite mit Ressource
}
catch (Exception e)
{
    // reagiere auf Fehler
}
finally
{
    // wird immer ausgeführt
}
```

## Checked und Unchecked Exceptions

Java unterscheidet zwei wichtige Gruppen:

### Checked Exceptions

Diese müssen behandelt oder in der Methodensignatur deklariert werden.[^wikipedia]

Beispiel: `IOException`

```java
import java.io.IOException;

public void loadFile(String pfad) throws IOException
{
    // ...
}
```

[^wikipedia]:
    https://de.wikipedia.org/wiki/Ausnahmebehandlung#Checked_Exceptions

    **Checked Exceptions**

    In der Programmiersprache Java gibt es als Weiterentwicklung der Ausnahme
    die „Checked Exception“ (dt. etwa: überprüfte Ausnahme). Das ist eine
    Ausnahme, bei der der Compiler prüft, ob alle Stellen, wo sie auftreten
    kann, durch Code zum Abfangen der Ausnahme abgedeckt sind. Der Code zum
    Abfangen kann dabei innerhalb derselben Methode stehen, in der die Ausnahme
    auftreten kann, oder auch in aufrufenden Methoden. In letzterem Fall muss
    der Programmierer die Ausnahmen in der Methodensignatur deklarieren.

    Die zugrunde liegende Idee beim Entwurf von Java war, dass Ausnahmen, auf
    die der Anwendungscode sinnvoll reagieren kann, als Checked Exception
    ausgeführt werden. Durch den Zwang zur Behandlung der Ausnahme sollte
    robuster Code erreicht werden und fehlende Fehlerbehandlungen bereits vom
    Compiler entdeckt werden. Es gibt aber weiterhin Ausnahmen, die keine
    Checked Exceptions sind. Als Konvention gilt dabei, solche Fehler als
    Checked Exception zu realisieren, bei denen man vom Aufrufer erwartet, dass
    er auf ihn reagieren und einen geregelten Programmablauf wiederherstellen
    kann. Darunter fallen beispielsweise Netzwerk-, Datenbank- oder sonstige
    E/A-Fehler. So kann das Öffnen einer Datei aus verschiedenen Gründen
    fehlschlagen (keine Rechte, Datei nicht vorhanden), der Aufbau einer
    Netzwerkverbindung kann aus vom Programm nicht beeinflussbaren Gründen
    fehlschlagen. Nicht-Checked-Exceptions sind zum Melden verschiedener Arten
    von Programmfehlern vorgesehen (zum Beispiel Indexfehler bei
    Array-Indizierung). Es wird davon abgeraten, die Anwendung in solchen Fällen
    versuchen zu lassen, einen geregelten Programmablauf wiederherzustellen.
    Die Klassen der Java-Plattform selber halten sich weitgehend an diese
    Konvention.

    Kritiker führen gegen die Checked Exceptions an, dass sie die Lesbarkeit des
    Quellcodes verschlechtern würden und dass sie viele Programmierer, weil sie
    in dieser Funktionalität keinen dem Aufwand entsprechenden Nutzen erkennen,
    zu Ausweichkonstrukten verleiten, die dem Compiler genügen, aber kaum Fehler
    behandeln. Ein anderer Einwand ist, dass aufgrund der Deklaration der
    Exceptions in den Methodensignaturen allgemein verwendbare Hilfsklassen oder
    Interfaces, insbesondere als Teil von Entwurfsmustern, oft nicht sinnvoll
    operabel sind mit Klassen, die Checked Exceptions verwenden. Als
    Ausweichlösung werden getunnelte Checked Exceptions vorgeschlagen, die aber
    den Nutzen der Checked Exception aufheben. Darüber hinaus stehen Checked
    Exceptions als Teil der Methodensignatur der Erweiterbarkeit von
    Schnittstellen im Wege.

    Neuere Fachliteratur sowie Diskussionen während der Entstehung von
    Programmiersprachen neueren Datums tendieren dazu, Checked Exceptions
    abzulehnen.

### Unchecked Exceptions

Diese treten typischerweise durch Programmierfehler auf und müssen nicht
zwingend deklariert werden.[^oracle]

Beispiele:

- `NullPointerException`
- `IllegalArgumentException`
- `ArithmeticException`

## Hierarchie der Java-Exceptions

Die folgende Aufstellung zeigt die wichtigsten Vererbungsbeziehungen in
vereinfachter Form:

- `Throwable`
  - `Error` (schwerwiegende Laufzeitprobleme, i. d. R. nicht abfangen)
    - `OutOfMemoryError`
    - `StackOverflowError`
  - `Exception`
    - Checked Exceptions
      - `IOException`
        - `FileNotFoundException`
        - `EOFException`
      - `SQLException`
      - `ClassNotFoundException`
      - `InterruptedException`
    - `RuntimeException` (Unchecked Exceptions)
      - `NullPointerException`
      - `IllegalArgumentException`
        - `NumberFormatException`
      - `IllegalStateException`
      - `IndexOutOfBoundsException`
        - `ArrayIndexOutOfBoundsException`
        - `StringIndexOutOfBoundsException`
      - `ArithmeticException`

Auf
[programming.guide](https://programming.guide/java/list-of-java-exceptions.html)
findet sich eine ausführliche Auflistung der Java Exceptions.

## Unterschied zwischen `throw` und `throws`

- `throw` wirft eine konkrete Exception.
- `throws` kündigt in der Methodensignatur an, dass eine Methode eine
  Exception weitergeben kann.

```java
public int divide(int a, int b)
{
    if (b == 0)
    {
        throw new IllegalArgumentException("Division durch 0 ist nicht erlaubt");
    }
    return a / b;
}
```

## Eigene Exceptions erstellen

Eigene _Exceptions_ können erstellt werden, indem von einer der vielen vorhanden
_Exception_-Klasse eine Unterklasse erstellt wird. Eigene _Unchecked Exceptions_
müssen von `RuntimeException` oder `Error` erben.

```java
public class InvalidLevelException extends Exception
{
    public InvalidLevelException(String message)
    {
        super(message);
    }
}
```

Verwendung:

```java
public void setLevel(int level) throws InvalidLevelException
{
    if (level < 1)
    {
        throw new InvalidLevelException("Level muss >= 1 sein");
    }
}
```

[^oracle]:
    https://docs.oracle.com/javase/tutorial/essential/exceptions/runtime.html

    **Unchecked Exceptions — The Controversy**

    Because the Java programming language does not require methods to catch or
    to specify unchecked exceptions (`RuntimeException`, `Error`, and their
    subclasses), programmers may be tempted to write code that throws only
    unchecked exceptions or to make all their exception subclasses inherit from
    `RuntimeException`. Both of these shortcuts allow programmers to write code
    without bothering with compiler errors and without bothering to specify or
    to catch any exceptions. Although this may seem convenient to the
    programmer, it sidesteps the intent of the catch or specify requirement and
    can cause problems for others using your classes.

    Why did the designers decide to force a method to specify all uncaught
    checked exceptions that can be thrown within its scope? Any Exception that
    can be thrown by a method is part of the method's public programming
    interface. Those who call a method must know about the exceptions that a
    method can throw so that they can decide what to do about them. These
    exceptions are as much a part of that method's programming interface as its
    parameters and return value.

    The next question might be: "If it's so good to document a method's API,
    including the exceptions it can throw, why not specify runtime exceptions
    too?" Runtime exceptions represent problems that are the result of a
    programming problem, and as such, the API client code cannot reasonably be
    expected to recover from them or to handle them in any way. Such problems
    include arithmetic exceptions, such as dividing by zero; pointer exceptions,
    such as trying to access an object through a `null` reference; and indexing
    exceptions, such as attempting to access an array element through an index
    that is too large or too small.

    Runtime exceptions can occur anywhere in a program, and in a typical one
    they can be very numerous. Having to add runtime exceptions in every method
    declaration would reduce a program's clarity. Thus, the compiler does not
    require that you catch or specify runtime exceptions (although you can).

    One case where it is common practice to throw a `RuntimeException` is when the
    user calls a method incorrectly. For example, a method can check if one of
    its arguments is incorrectly `null`. If an argument is `null`, the method might
    throw a `NullPointerException`, which is an unchecked exception.

    Generally speaking, do not throw a `RuntimeException` or create a subclass of
    `RuntimeException` simply because you don't want to be bothered with
    specifying the exceptions your methods can throw.

    Here's the bottom line guideline: If a client can reasonably be expected to
    recover from an exception, make it a checked exception. If a client cannot
    do anything to recover from the exception, make it an unchecked exception.
