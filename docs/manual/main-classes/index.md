# Die Hauptklassen

{{ drawio('main-classes') }}

Durch die Steuerklasse {{ javadoc('pi.Controller') }} kann eine
{{ javadoc('pi.Scene', 'Szene (Scene)') }} gestartet werden.
Die {{ javadoc('pi.Camera', 'Kamera (Camera)') }} legt fest, welcher Ausschnitt
einer {{ javadoc('pi.Scene', 'Szene') }} im Spielfenster angezeigt werden soll.
Eine  {{ javadoc('pi.Scene', 'Szene') }} besteht aus einer
{{ javadoc('pi.Layer', 'Hauptebene') }} und beliebig vielen weiteren
{{ javadoc('pi.Layer', 'Ebenen') }}.
Die einzelnen {{ javadoc('pi.actor.Actor', 'Figuren (Actor)') }} sind einer
{{ javadoc('pi.Layer', 'Ebene (Layer)') }} zugeordnet.
