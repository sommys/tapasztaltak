package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * A medvetánc ágenset reprezentáló osztály
 */
public class Bear extends Agent implements SpecialModifier{

    /**
     * Megmondja az ádatott listáról, hogy van-e benne Medvetánc ágens
     * @param m a {@link SpecialModifier} lista amit vizsgálunk
     * @return van-e benne Medvetánc ágens
     */
    public static boolean containsBear(List<SpecialModifier> m){
        for(SpecialModifier sm : m){
            if(sm.getClass() == Bear.class) return true;
        }
        return false;
    }

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
        Logger.log(this, "spread", CALL, v);
        v.addModifier(this);
        Logger.log(this, "", RETURN);
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
     *
     * @param v {@link Virologist}, akire kifejti a hatását
     */
    @Override
    public void effect(Virologist v) {
        //ha béna, akkor nem történik semmi
        if(v.isStunned() || v.isMoved()) return;
        //random mozgás
        Field f = v.getField();
        Field randField = f.getRandomNeighbour();
        f.removeVirologist(v);
        randField.addVirologist(v);
        //törés, zúzás
        randField.destroyStuff();
        //fertőzés
        List<Virologist> toInfect = v.getField().getVirologists();
        for(Virologist i : toInfect){
            if(!i.isBear()){
                v.useAgent(new Bear(), i); //köszi Lilla :)
            }
        }
        //cselekvőképtelenség és kör vége
        v.setMoved(true);
        v.endRound();
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
