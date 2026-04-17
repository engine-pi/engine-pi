## foreach-Schleife

for-each ist eine Art for-Schleife, die du verwendest, wenn du alle Elemente
eines Arrays oder einer Collection verarbeiten musst. Allerdings wird der
Ausdruck for-each in dieser Schleife eigentlich nicht verwendet. Die Syntax
lautet wie folgt:

```java
for (type itVar : array)
{
    // Operations
}
```

Wobei type der Typ der Iterator-Variable ist (der dem Datentyp der Elemente im
Array entspricht!), itVar ihr Name und array ein Array (andere Datenstrukturen
sind auch erlaubt, z.B. eine Collection, wie ArrayList), d. h. das Objekt, auf
dem die Schleife ausgeführt wird. Wie du siehst, wird bei diesem Konstrukt kein
Zähler verwendet: Die Iterator-Variable iteriert einfach über die Elemente des
Arrays oder der Collection. Wenn eine solche Schleife ausgeführt wird, wird der
Iterator-Variable nacheinander der Wert jedes Elements des Arrays oder der
Collection zugewiesen, woraufhin der angegebene Anweisungsblock (oder die
Anweisung) ausgeführt wird.[^codegym]

[^codegym]: https://codegym.cc/de/groups/posts/1011-die-for-each-schleife-in-java
