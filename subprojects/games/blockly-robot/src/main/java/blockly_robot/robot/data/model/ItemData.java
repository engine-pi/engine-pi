package blockly_robot.robot.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import blockly_robot.robot.logic.navigation.Compass;

/**
 * Die Daten eines Gegenstands (Item).
 *
 * <p>
 * In der JSON-Datei sind die Daten beispielsweise so repräsentiert:
 *
 * <pre>{@code
 *   {
 *     "row": 2,
 *     "col": 1,
 *     "dir": 0,
 *     "type": "robot"
 *   }
 * }</pre>
 */
@JsonIgnoreProperties(value = {})
public class ItemData implements Cloneable
{
    /**
     * Die Nummer (ID) des Gegenstands.
     */
    public int num;

    /**
     * Die Zeilennummer, in der der Gegenstand plaziert ist.
     */
    public int row;

    /**
     * Die Spaltennummer, in der der Gegenstand plaziert ist.
     */
    public int col;

    /**
     * <code>0</code> steht für Osten, <code>1</code> steht für Süden,
     * <code>2</code> steht für Westen, <code>3</code> steht für Norden.
     *
     * @see blockly_robot.robot.logic.navigation.Compass
     */
    public int dir;

    /**
     * Hat meistens den Wert <code>robot</code>. Ein eindeutiger Name, der den
     * Gegenstand identifiziert. Zum Beispiel: <code>candle</code>
     */
    public String type;

    /**
     * Relativer Pfad zu <code>src/main/resources/images</code>. Zum Beispiel:
     * <code>candle/candle.png</code>
     */
    public String img;

    /**
     * Größe in Pixel.
     */
    public int side;

    /**
     *
     */
    public int offsetX;

    /**
     *
     */
    public int offsetY;

    /**
     *
     */
    public int zOrder;

    /**
     *
     */
    public String color;

    /**
     * Gibt an, ob der Gegenstand eine Kiste darstellt.
     */
    public boolean isContainer;

    /**
     * Gibt an, ob der Gegenstand einen Ausgang darstellt.
     */
    public boolean isExit;

    /**
     * Gibt an, ob der Gegenstand einen Laser darstellt.
     */
    public boolean isLaser;

    /**
     * Gibt an, ob der Gegenstand ein Hindernis darstellt.
     */
    public boolean isObstacle;

    public boolean isPaint;

    /**
     * Gibt an, ob der Gegenstand einen Wurfkörper darstellt.
     */
    public boolean isProjectile;

    /**
     * Gibt an, ob der Gegenstand eine Kiste darstellt.
     */
    public boolean isPushable;

    /**
     * Gibt an, ob der Gegenstand einen Roboter darstellt.
     */
    public boolean isRobot;

    /**
     * Gibt an, ob der Gegenstand eingesammelt werden kann.
     */
    public boolean isWithdrawable;

    public boolean autoWithdraw;

    /**
     * Gibt an, ob der Gegenstand eine Tafel darstellt.
     */
    public boolean isWritable;

    public int nbStates;

    /**
     * Found in contextParams.json
     */
    public boolean forwardsLeft;

    /**
     * Found in contextParams.json
     */
    public boolean forwardsRight;

    /**
     * Found in contextParams.json
     */
    public boolean forwardsTop;

    /**
     * Found in contextParams.json
     */
    public boolean forwardsBottom;

    /**
     * Found in contextParams.json
     */
    public boolean isRound;

    /**
     * Found in contextParams.json
     */
    public boolean isQuadrille;

    /**
     * Found in contextParams.json
     */
    public boolean isSquare;

    /**
     * Found in contextParams.json
     */
    public boolean isTriangle;

    /**
     * Found in contextParams.json
     */
    public boolean isStriped;

    /**
     * Found in contextParams.json
     */
    public boolean isDotted;

    /**
     * Found in contextParams.json
     */
    public boolean isCross;

    /**
     * Found in contextParams.json
     */
    public boolean isStar;

    /**
     * Found in contextParams.json
     */
    public boolean canBeOutside;

    /**
     * Found in contextParams.json
     */
    public String fontColor;

    /**
     * Found in contextParams.json
     */
    public boolean fontBold;

    /**
     * Found in contextParams.json
     */
    public boolean isGreen;

    /**
     * Found in contextParams.json
     */
    public boolean isOpaque;

    /**
     * Found in contextParams.json
     */
    public String[] states;

    /**
     * Found in contextParams.json
     */
    public boolean isLight;

    /**
     * Found in contextParams.json
     */
    public int state;

    /**
     * Found in contextParams.json
     */
    public boolean isMirror;

    /**
     * Found in contextParams.json
     */
    public boolean isFake;

    /**
     * Found in contextParams.json
     */
    public boolean isWire;

    /**
     * Found in contextParams.json
     */
    public int plugType;

    public int containerSize;

    public Compass getCompassDirection()
    {
        return Compass.fromNumber(dir);
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
