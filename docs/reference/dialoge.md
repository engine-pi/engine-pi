# Dialog-Fenster

https://engine-alpha.org/wiki/Tutorials/Dialoge

Ziel dieses Tutorials

Nachdem du dieses Tutorial durchgearbeitet hast, kannst du die verschiedenen Dialog-Fenster einsetzen, die dir die Engine Alpha für die Interaktion mit dem Spieler zur Verfügung stellt. Beispielsweise kannst du es dem Spieler ermöglichen seinen Namen einzugeben.

tutorial nachrichtSchicken.png

tutorial eingabeFordern.png

tutorial frage.png
Überblick der Dialoge

Die Engine Alpha stellt dir in der Klasse Game vier Methoden für die Kommunikation mit dem Spieler zur Verfügung:
Methode 	Erklärung

 public void nachrichtSchicken(String nachricht)

	erzeugt ein modales (im Vordergrund stehendes) Fenster mit einer Nachricht. Der Spieler muss diese Nachricht durch einen Linksklick auf "Ok" bestätigen.

 public String eingabeFordern(String nachricht)

	erzeugt ein modales (im Vordergrund stehendes) Fenster mit einem erklärenden Text und einem Eingabe-Feld in das der Spieler hinein schreiben kann.

 public boolean frage(String frage)

	erzeugt ein modales (im Vordergrund stehendes) Fenster mit einer Frage, die mit JA oder NEIN per Linksklick beantwortet werden muss.


Achtung!
Alle Dialog-Fenster drängen sich kompromisslos in den Vordergrund und die entsprechende Methode endet erst dann, wenn das Fenster wieder geschlossen wird. Deshalb solltest du vor dem Aufruf dieser Methoden das Spiel unbedingt anhalten!
