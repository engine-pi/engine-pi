# `Go to file://` (Links zwischen Textdateien)

In zahlreichen Textdateien des Projekts sind folgende Kommentare zu finden:

```java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/ImageDemo.java
```

Durch die Angabe des Präfixes `file://` in Kombination mit dem absoluten Pfad
kann mithilfe des Editors Visual Studio Code zu dieser Datei gesprungen werden.

So kann beispielsweise die Klasse {{
repo_link('subprojects/engine/src/main/java/pi/Image.java', 'Image.java') }} mit
der Klasse {{
repo_link('subprojects/demos/src/main/java/demos/classes/actor/ImageDemo.java',
'ImageDemo.java') }} aus dem Demo-Subprojekt verlinkt werden.

Diese Links funktionieren nur, wenn das Engine-Pi-Repository in das Verzeichnis
`/data/school/repos/inf/java/engine-pi` geklont wurde.
