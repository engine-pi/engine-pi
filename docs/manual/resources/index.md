# Ressourcenverwaltung[^litiengine]

[^litiengine]: https://litiengine.com/docs/resource-management/

<!-- https://litiengine.com/docs/resource-management/ -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/resources/Resource.java -->
<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/resources/ResourcesContainer.java -->

<!-- The static Resources class is the engine’s entry point for accessing any kind of
resource from within your LITIENGINE project. A resource is any non-executable
data that is deployed with your game. The Resources class provides access to
different types of ResourcesContainers and is used by different (loading)
mechanisms to make resources available during runtime. LITIENGINE supports
various different resource types, including: -->

Die statische Klasse {{ class('pi.Controller') }} ist der Einstiegspunkt der
Engine für den Zugriff auf alle Arten von Ressourcen. Eine Ressource ist jede
nicht ausführbare Datei, die zusammen mit Ihrem Spiel bereitgestellt wird. Die
Klasse {{ class('pi.Controller') }} bietet Zugriff auf verschiedene Arten von {{
class('pi.resources.ResourcesContainer') }} und wird von verschiedenen
(Lade-)Mechanismen genutzt, um Ressourcen zur Laufzeit verfügbar zu machen. Die
Engine Pi unterstützt verschiedene Ressourcentypen, darunter:

- colors
- fonts
- images
- sounds

## Ressourcencontainer

<!-- ResourcesContainer is an abstract implementation for all classes that contain a
certain resource type. Basically, it’s an in-memory cache of the resources and
provides access to manage the resources. -->

{{ class('pi.resources.ResourcesContainer') }} ist eine abstrakte Oberklasse für
alle Klassen, die einen bestimmten Ressourcentyp enthalten. Im Grunde handelt es
sich um einen In-Memory-Cache der Ressourcen, der Zugriff zur Verwaltung der
Ressourcen bietet.

<!-- Internally, the resources are stored in a ConcurrentHashMap where the keys are
String identifiers and the values are your individual resource objects. -->

Intern werden die Ressourcen in einer `ConcurrentHashMap` gespeichert, wobei die
Schlüssel String-Identifikatoren und die Werte Ihre einzelnen Ressourcenobjekte
sind.

<!-- There are various overloads for ResourcesContainer.add(...) and
ResourcesContainer.get(...), allowing you to adjust these operations to your
needs. All the ResourcesContainer‘s contents and its listeneres can be discarded
with ResourcesContainer.clear(). -->

Es gibt verschiedene Überladungen für ResourcesContainer.add(...) und
ResourcesContainer.get(...), mit denen Sie diese Operationen an Ihre Bedürfnisse
anpassen können. Der gesamte Inhalt des ResourcesContainer und seine Listener
können mit ResourcesContainer.clear() gelöscht werden. Listener

## Listeners

<!-- You can register ResourcesContainerListeners with
addContainerListener(ResourcesContainerListener<T> listener) to get notified
whenever a resource was added to or removed from your ResourcesContainer.
Removing listeners works analogically with
removeContainerListener(ResourcesContainerListener<T> listener) -->

Mit der Methode
{{ method('pi.resources.ResourcesContainer', 'addContainerListener(pi.resources.ResourcesContainerListener)', 'addContainerListener(ResourcesContainerListener&lt;T&gt; listener)') }}
der Klasse
{{ class('pi.resources.ResourcesContainer') }}
lassen sich
{{ class('pi.resources.ResourcesContainerListener') }}
registrieren , um
benachrichtigt zu werden, sobald eine Ressource zu einem ResourcesContainer
hinzugefügt oder entfernt wurde. Das Entfernen von Listenern funktioniert
analog mit
{{ method('pi.resources.ResourcesContainer', 'removeContainerListener(pi.resources.ResourcesContainerListener)', 'removeContainerListener(ResourcesContainerListener&lt;T&gt; listener)') }}.
