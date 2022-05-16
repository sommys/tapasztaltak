package hu.tapasztaltak.model;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        v.addModifier(this);
        Game.getInstance().checkEndGame(v);
        Game.objectViewHashMap.get(v).update();
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
        Field f = v.getField();
        Field randField = f.getRandomNeighbour();
        if(randField != f){
            f.removeVirologist(v);
            randField.addVirologist(v);
            v.setField(randField);
        }
        //törés, zúzás
        randField.destroyStuff();
        //fertőzés
        List<Virologist> toInfect = v.getField().getVirologists().stream().filter(it -> it != v).collect(Collectors.toList());
        for(Virologist i : toInfect){
            v.spreadInitiation(new Bear(), i); //köszi Lilla :)
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
