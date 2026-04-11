# Graphics2D-API

Die Engine-Pi nutzt intensiv die {{ javadoc('java.desktop:java.awt.Graphics2D') }}-API.


Um sich mit dieser API vertraut zu machen, gibt es im Demo {{ javadoc('demos')
}}-Subprojekt die Klasse {{ javadoc('demos.graphics2d.Graphics2DComponent') }}.
Dieser Klasse stellt eine {{ javadoc('java.desktop:java.awt.Graphics2D') }}-Instanz zur Verfügung und öffnet ein
Fenster über die Methode {{ javadoc('demos.graphics2d.Graphics2DComponent#open()') }}.

{{ code('demos.graphics2d.Graphics2DComponent') }}

<!-- Die Klasse `java.awt.Graphics2D` ist die zentrale Zeichen-API von Java2D.
Sie erweitert `Graphics` um:

- praezise 2D-Geometrie (Linien, Kurven, Flaechen)
- Transformationen (verschieben, drehen, skalieren)
- Zeichenattribute (Farben, Linienstaerke, Transparenz, Fonts)
- hochwertiges Rendering ueber Hint-Optionen (Antialiasing etc.)

## Grundprinzip

Ein `Graphics2D`-Objekt repraesentiert den aktuellen Zeichenkontext.
Typisch bekommst du es in Swing in `paintComponent(Graphics g)`:

```java
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g.create();
    try {
        // hier zeichnen
    } finally {
        g2.dispose();
    }
}
```

Warum `create()` und `dispose()`?

- `create()` erzeugt eine Kopie des Kontexts
- Aenderungen an Farbe, Transform, Stroke usw. bleiben lokal
- `dispose()` gibt Ressourcen sauber frei

## Wichtige Methoden

### Formen zeichnen

- `draw(Shape s)`: Umriss zeichnen
- `fill(Shape s)`: Flaeche fuellen
- `drawLine(...)`, `drawRect(...)`, `fillRect(...)`: einfache Primitive

Typische `Shape`-Klassen:

- `Line2D`
- `Rectangle2D`
- `Ellipse2D`
- `RoundRectangle2D`
- `Path2D`

### Farben und Transparenz

- `setColor(Color c)`: aktuelle Farbe setzen
- `setPaint(Paint p)`: allgemeiner als `setColor`, z. B. Gradient
- `setComposite(Composite c)`: Alpha/Blend-Verhalten, meist `AlphaComposite`

Beispiel:

```java
g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
```

### Linien und Kanten

- `setStroke(Stroke s)`: Linienstil setzen, z. B. `BasicStroke`

Beispiel:

```java
g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
```

### Text

- `setFont(Font f)`
- `drawString(String str, float x, float y)`
- fuer exakte Textmetrik: `FontMetrics`

### Transformationen

- `translate(tx, ty)`
- `rotate(theta)`
- `scale(sx, sy)`
- `shear(shx, shy)`
- `setTransform(AffineTransform tx)`
- `transform(AffineTransform tx)`

Wichtig: Transformationen wirken kumulativ und in Reihenfolge.

### Clipping

- `setClip(Shape clip)`
- `clip(Shape s)`

Nur der Clip-Bereich wird gezeichnet.

## Rendering-Hints

Rendering-Hints steuern Qualitaet und Performance:

```java
g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
```

Nutzung:

- UI, Demo, Export: eher Qualitaet
- Echtzeit/Animation mit vielen Objekten: ggf. auf Performance optimieren

## Beispiel: einfache Szene

```java
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Graphics2DDemo extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            // Hintergrund
            g2.setColor(new Color(245, 247, 250));
            g2.fillRect(0, 0, getWidth(), getHeight());

            // Dicke blaue Linie
            g2.setColor(new Color(28, 113, 216));
            g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(new Line2D.Double(40, 40, 260, 90));

            // Halbtransparenter Kreis
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            g2.setColor(new Color(220, 20, 60));
            g2.fill(new Ellipse2D.Double(80, 80, 120, 120));

            // Rotation um Mittelpunkt
            AffineTransform old = g2.getTransform();
            g2.translate(260, 180);
            g2.rotate(Math.toRadians(20));
            g2.setComposite(AlphaComposite.SrcOver);
            g2.setColor(new Color(46, 194, 126));
            g2.fill(new Rectangle2D.Double(-50, -20, 100, 40));
            g2.setTransform(old);

            // Text
            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("SansSerif", Font.BOLD, 18));
            g2.drawString("Graphics2D Demo", 40, 250);
        } finally {
            g2.dispose();
        }
    }
}
```

## Best Practices

- In Swing nur in `paintComponent(...)` zeichnen, nicht in Event-Handlern.
- Immer `super.paintComponent(g)` aufrufen.
- Fuers lokale Zeichnen `Graphics2D g2 = (Graphics2D) g.create()` nutzen.
- Komplexe Geometrie als `Shape` modellieren und dann `draw(...)`/`fill(...)` verwenden.
- Bei Animationen moeglichst Objekte wiederverwenden, um Garbage zu reduzieren.
- Beachten: Swing ist nicht thread-safe. UI-Aktionen laufen auf dem EDT.

## Kurzreferenz

- Geometrie: `Shape`, `Path2D`, `Area`
- Stil: `Color`, `Paint`, `Stroke`, `Composite`
- Koordinatensystem: `AffineTransform`
- Qualitaet: `RenderingHints`
- Text: `Font`, `FontMetrics`
- Bilddaten: `BufferedImage`, `drawImage(...)`

Die offizielle API-Doku ist die beste Quelle fuer Detailverhalten einzelner Methoden und Randfaelle:

- https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Graphics2D.html -->
