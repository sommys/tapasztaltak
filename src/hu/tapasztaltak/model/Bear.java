package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;
import hu.tapasztaltak.skeleton.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;
import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * A medvetánc ágenset reprezentáló osztály
 */
public class Bear extends Agent implements SpecialModifier{

    /**
     * Visszaadja az ágens készítéséhez szükséges receptet, azaz hogy milyen anyagok kellenek hozzá
     * @return milyen anyagok ({@link IMaterial}) kellenek a készítéséhez
     */
    @Override
    public List<IMaterial> getRecipe() {
        return Collections.emptyList();
    }

    /**
     * Felkenődik v-re
     * @param v a {@link Virologist}, akire felkenődik
     */
    @Override
    public void spread(Virologist v) {
        boolean originalLoggerSwitch = ProtoLogger.loggingSwitch;
        if(ProtoLogger.loggingSwitch) ProtoLogger.loggingSwitch = false;
        v.addModifier(this);
        if(originalLoggerSwitch) ProtoLogger.loggingSwitch = true;
        ProtoLogger.logMessage(String.format("%s infected with Bear", getIdForObject(v)));
    }

    /**
     * Visszaad egy másolatot az ágensről
     * @return a másolat
     */
    @Override
    public Agent clone() {
        return new Bear();
    }


    /**
     * Kifejti a hatását a paraméterben átadott {@link Virologist}-ra
     * Ha bénult, akkor nem történik semmi.
     * Különben egy random szomszédra mozog, ha az egy raktár, akkor összetöri az ott lévő anyagokat.
     * Az azonos mezőn lévő virológusokat megpróbálja megfertőzni.
     * A játékos nem csinálhat egyebet a Medve ágens hatása alatt, ezért a köre is véget ér.
     * @param v {@link Virologist}, akire kifejti a hatását
     */
    @Override
    public void effect(Virologist v) {
        //ha béna, akkor nem történik semmi
        if(v.isStunned() || v.isMoved()) return;
        //random mozgás
        ProtoLogger.logMessage(String.format("[%s effect started]", getIdForObject(this)));
        Field f = v.getField();
        Field randField = f.getRandomNeighbour();
        f.removeVirologist(v);
        randField.addVirologist(v);
        ProtoLogger.logMessage(String.format("%s moved to %s", getIdForObject(v), getIdForObject(randField)));
        //törés, zúzás
        randField.destroyStuff();
        ProtoLogger.logMessage(String.format("%s destroyed materials on %s", getIdForObject(v), getIdForObject(randField)));
        //fertőzés
        List<Virologist> toInfect = v.getField().getVirologists();
        for(Virologist i : toInfect){
            ProtoLogger.logMessage(String.format("%s tries to infect %s", getIdForObject(v), getIdForObject(i)));
            v.useAgent(new Bear(), i); //köszi Lilla :)
        }
        //cselekvőképtelenség és kör vége
        v.setMoved(true);
        ProtoLogger.logMessage(String.format("%s ended round", getIdForObject(v)));
        v.endRound();
        ProtoLogger.logMessage(String.format("[%s effect ended]", getIdForObject(this)));
    }

    /**
     * Megadja, hogy aktívan hat-e még a módosító
     * @return aktívan hat-e a módosító
     */
    @Override
    public boolean isActive() {
        return true;
    }
}
