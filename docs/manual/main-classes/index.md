# Die Hauptklassen

{{ drawio('main-classes') }}

Durch die Steuerklasse {{ class('pi.Controller') }} kann eine
{{ class('pi.Scene', 'Szene (Scene)') }} gestartet werden.
Die {{ class('pi.Camera', 'Kamera (Camera)') }} legt fest, welcher Ausschnitt
einer {{ class('pi.Scene', 'Szene') }} im Spielfenster angezeigt werden soll.
Eine  {{ class('pi.Scene', 'Szene') }} besteht aus einer
{{ class('pi.Layer', 'Hauptebene') }} und beliebig vielen weiteren
{{ class('pi.Layer', 'Ebenen') }}.
Die einzelnen {{ class('pi.actor.Actor', 'Figuren (Actor)') }} sind einer
{{ class('pi.Layer', 'Ebene (Layer)') }} zugeordnet.
