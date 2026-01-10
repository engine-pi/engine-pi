# `MouseClickListener` (Mauseingaben)[^engine-alpha-wiki:user-input-mouse-click-listener]

[^engine-alpha-wiki:user-input-mouse-click-listener]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/User_Input#MouseClickListener

{{ image('docs/input/PaintingCirclesDemo.gif') }}
/// caption
Auf Mausklick reagieren: Kreise malen
///

Das [folgende
Beispiel](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/input/mouse/PaintingCirclesDemo.java)
malt bei jedem Knopfdruck einen Kreis.[^mausklick-kreise-malen]

[^mausklick-kreise-malen]: https://engine-alpha.org/wiki/v4.x/User_Input#Auf_Mausklick_reagieren:_Kreise_malen

{{ demo('tutorials/user_input/mouse/PaintingCirclesDemo', 'a010897d03ba56fa142466a40f00a7e6f12a71d7', 'L23-L54') }}

```java
public class PaintingCirclesDemo extends Scene implements MouseClickListener
{
    public PaintingCirclesDemo()
    {
        addMouseClickListener(this);
    }

    private void paintCircleAt(double mX, double mY, double diameter)
    {
        Circle circle = new Circle(diameter);
        circle.setCenter(mX, mY);
        add(circle);
    }

    @Override
    public void onMouseDown(Vector position, MouseButton mouseButton)
    {
        paintCircleAt(position.getX(), position.getY(), 1);
    }
}
```

## Schnittstelle `MouseClickListener`

Das Interface
{{ class('pi.event.MouseClickListener') }}
ermöglicht das Reagieren auf Mausklicks des Nutzers. Ebenso ermöglicht es das
Reagieren auf Loslassen der Maus.

Bei einem Mausklick (egal ob linke, rechte, oder sonstige Maustaste) wird ein
Kreis an der Position des Klicks erstellt:

```java
@Override
public void onMouseDown(Vector position, MouseButton mouseButton)
{
    paintCircleAt(position.getX(), position.getY(), 1);
}
```

## `Vector`

Statt zwei `double`-Parametern für die X/Y-Koordinaten des Klicks, nutzt die
Engine hier die interne Klasse
{{ class('pi.Vector') }}.
Die Klasse `Vector` wird in der Engine durchgehend verwendet und ist essentiell
für die Arbeit mit der Engine.[^engine-alpha-wiki:vector]

[^engine-alpha-wiki:vector]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/User_Input#Vector

{{ image('docs/input/PaintingCirclesAdvancedDemo.gif') }}
/// caption
Ein besseres Kreismalen: Auswählbare Größe und Farbe über ein kleines Menü
///

{{ demo('tutorials/user_input/mouse/PaintingCirclesAdvancedDemo') }}
