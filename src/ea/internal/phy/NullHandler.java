package ea.internal.phy;

import ea.*;
import ea.Physik.Typ;
import ea.internal.util.Logger;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * Created by andonie on 16.02.15.
 */
public class NullHandler extends PhysikHandler {

    /**
     * Die Fixture Definition für das Client-Objekt.
     * Enthält auch die <b>shape</b>-Informationen.
     */
    private final FixtureDef fixtureDef;

    /**
     * Masse als hilfeweise gespeicherte Variable.
     */
    private float masse;

    /**
     * Referenz auf die World, in der sich der Handler befindet.
     */
    private WorldHandler worldHandler;

    public NullHandler(Raum raum) {
        super(raum, Typ.PASSIV, false);
        bodyDef = new BodyDef();

        fixtureDef = new FixtureDef();
        fixtureDef.density = 30f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;

    }

    /**
     * Speichert die Position des Objekts. Ist zu Beginn immer der Ursprung.
     */
    private Punkt position = Punkt.ZENTRUM;

    /**
     * Speichert die aktuelle Rotation in Radians. Ist zu Beginn stets 0.
     */
    private float rotation = 0;

    /**
     * Die Standard Body-Definition. Wird beim Übertrag eines Null-Handlers in einen
     * JB2D-Handler verwendet.
     */
    private final BodyDef bodyDef;

    @Override
    public void verschieben(Vektor v) {
        position = position.verschobeneInstanz(v);
    }

    @Override
    public Punkt mittelpunkt() {
        System.out.println("Nullhandler Mittelpunkt");
        return null;
    }

    @Override
    public boolean beinhaltet(Punkt p) {
        return false;
    }

    @Override
    public Punkt position() {
        return position;
    }

    @Override
    public float rotation() {
        return rotation;
    }

    @Override
    public void rotieren(float radians) {
        rotation += radians;
    }

    @Override
    public void dichteSetzen(float dichte) {
        fixtureDef.density = dichte;
    }

    @Override
    public float dichte() {

        return fixtureDef.density;
    }

    @Override
    public void reibungSetzen(float reibung) {
        fixtureDef.friction = reibung;
    }

    @Override
    public float reibung() {
        return fixtureDef.friction;
    }

    @Override
    public void elastizitaetSetzen(float ela) {
        fixtureDef.restitution = ela;
    }

    @Override
    public float elastizitaet() {
        return fixtureDef.restitution;
    }

    @Override
    public void masseSetzen(float masse) {
        this.masse = masse;
    }

    @Override
    public float masse() {

        return masse;
    }

    @Override
    public void kraftWirken(Vektor kraft) {
        Logger.error("Physik", "Bevor Physik genutzt wird, muss das Objekt (direkt oder indirekt) mit einer Wurzel verbunden " +
                "sein.");
    }

    @Override
    public void drehMomentWirken(float drehmoment) {
        Logger.error("Physik", "Bevor Physik genutzt wird, muss das Objekt (direkt oder indirekt) mit einer Wurzel verbunden " +
                "sein.");
    }

    @Override
    public void drehImpulsWirken(float drehimpuls) {
        Logger.error("Physik", "Bevor Physik genutzt wird, muss das Objekt (direkt oder indirekt) mit einer Wurzel verbunden " +
                "sein.");
    }

    @Override
    public void schwerkraftSetzen(Vektor schwerkraftInN) {
        Logger.error("Physik", "Bevor Physik genutzt wird, muss das Objekt (direkt oder indirekt) mit einer Wurzel verbunden " +
                "sein.");
    }

    @Override
    public void kraftWirken(Vektor kraftInN, Punkt globalerOrt) {
            Logger.error("Physik", "Bevor Physik genutzt wird, muss das Objekt (direkt oder indirekt) mit einer Wurzel verbunden " +
                    "sein.");
    }

    @Override
    public void impulsWirken(Vektor impulsInNS, Punkt globalerOrt) {
            Logger.error("Physik", "Bevor Physik genutzt wird, muss das Objekt (direkt oder indirekt) mit einer Wurzel verbunden " +
                    "sein.");
    }

    @Override
    public void killBody() {
        Logger.warning("Physik/INTERNAL WARNING", "Kill Body wurde an einem Null-Handler aufgerufen.");
    }

    @Override
    public WorldHandler worldHandler() {
        if(worldHandler == null) {
            Logger.error("Physik", "Ein Objekt wurde physikalisch angefragt, bevor es an einer Wurzel war.");
            return null;
        } return worldHandler;
    }

    @Override
    public Body getBody() {
        Logger.warning("Physik/Internal", "getBody()-Ausgabe wurde an Null-Handler aufgegeben.");
        return null;
    }

    @Override
    public void physicalReset() {
        //Nothing to do.
    }

    @Override
    public void geschwindigkeitSetzen(Vektor geschwindigkeitInMProS) {
        Logger.error("Physik", "Bevor Physik genutzt wird, muss das Objekt (direkt oder indirekt) mit einer Wurzel verbunden " +
                "sein.");
    }

    @Override
    public Vektor geschwindigkeit() {
        Logger.error("Physik", "Bevor Physik genutzt wird, muss das Objekt (direkt oder indirekt) mit einer Wurzel verbunden " +
                "sein.");
        return null;
    }

    @Override
    public void rotationBlockiertSetzen(boolean block) {
        Logger.error("Physik", "Bevor Physik genutzt wird, muss das Objekt (direkt oder indirekt) mit einer Wurzel verbunden " +
                "sein.");
    }

    @Override
    public boolean rotationBlockiert() {
        Logger.error("Physik", "Bevor Physik genutzt wird, muss das Objekt (direkt oder indirekt) mit einer Wurzel verbunden " +
                "sein.");
        return false;
    }

    /**
     *
     * @param typ   Der neue Typ.
     * @return
     */
    @Override
    public PhysikHandler typ(Typ typ) {

        //System.out.println("TYPE " + typ + " at " + this);

        if(typ == null) {
            Logger.error("Physik", "Physik-Typ wurde nicht spezifiziert.");
            return this;
        }
        bodyDef.type = typ.convert();

        if(fixtureDef.shape == null) {
            //Das Objekt hat keine Shape (ist Knoten oder nicht an einem Knoten angemeldet)
            return this;
        }


        bodyDef.active = true;
        fixtureDef.isSensor = typ == Typ.PASSIV;// && isSensor;

        bodyDef.position.set(worldHandler.fromVektor(position.alsVektor()));
        bodyDef.gravityScale = typ == Typ.PASSIV ? 0 : 1;

        return new BodyHandler(raum, worldHandler, bodyDef, fixtureDef, physikTyp, isSensor, this);

    }


    @Override
    public void setSensor(boolean isSensor) {
        this.isSensor = isSensor;
    }

    @Override
    public void update(WorldHandler worldHandler) throws IllegalStateException {
        if(worldHandler == null)
            return;

        this.worldHandler = worldHandler;


        if(! (this.raum instanceof Knoten)) worldHandler.blockPPMChanges();
        fixtureDef.shape = raum.berechneShape(worldHandler.getPixelProMeter());

        raum.bodyTypeSetzen(physikTyp);
    }
}