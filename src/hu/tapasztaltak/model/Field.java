package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * A játék alapmezőit reprezentáló osztály.
 */
public class Field implements ISteppable {
    /**
     * A mezőre létrejövő tárgyak megjelenéséig hátralévő körök száma.
     */
    protected int refreshCounter = -1;
    /**
     * A mezővel szomszédos mezők listája.
     */
    private List<Field> neighbours = new ArrayList<>();
    /**
     * A mezőn tartózkodó virológusok listája.
     */
    private List<Virologist> virologists = new ArrayList<>();

    /**
     * A {@code v} virológusnak adja, a mezőn található dolgo[ka]t.
     *
     * @param v a {@link Virologist}, aki a dolgo[ka]t kapja.
     */
    public void getItem(Virologist v) {}

    /**
     * Újratölti a mezőre jellemző dolgo[ka]t.
     */
    public void refresh() {}

    /**
     * Refreshcountert csökkenti és ha nulla, akkor a refresh függvényt meghívja.
     */
    public void step() {
        // Todo: Peti, leírás szerint csak ha szükséges, akkor csökkentünk, de ez mit akar pontosan jelenteni?
        if (refreshCounter > 0) {
            refreshCounter--;
        } else {
            // Todo: Peti, hibakezelés
            System.out.println("HIBA: Nem várt működés a futás során");
        }

        if (refreshCounter == 0) {
            refresh();
            // Todo: Peti, Itt nekem kéne a refresh countert random 4-8 közé állítani?
        }
    }

    /**
     * Megállapítja {@code f} mezőről, hogy szomszédos-e.
     *
     * @param f a {@link Field}, ami a kérdéses szomszéd
     * @return szomszédos-e a mező
     */
    public boolean isNeighbour(Field f) {
        Logger.log(this, "isNeighbour", CALL, f);
        Logger.log(this, "neighbour="+neighbours.contains(f), RETURN);
        return neighbours.contains(f);
    }

    /**
     * Visszatér egy random szomszédos mezővel.
     *
     * @return random szomszédos {@link Field}
     */
    public Field getRandomNeighbour() {
        Random random = new Random();
        return neighbours.get(random.nextInt(neighbours.size()));
    }

    //region GETTEREK ÉS SETTEREK

    /**
     * Visszaadja, hogy hány kör van még hátra a mező frissítéséig.
     *
     * @return frissítésig hátralévő körök száma
     */
    public int getRefreshCounter() {
        return refreshCounter;
    }

    /**
     * Beállítja, hogy hány kör van még hátra a mező frissítéséig.
     *
     * @param refreshCounter frissítésig hátralévő körök száma
     */
    public void setRefreshCounter(int refreshCounter) {
        this.refreshCounter = refreshCounter;
    }

    /**
     * Visszaadja a szomszédos mezők listáját.
     *
     * @return a szomszédos {@link Field} lista
     */
    public List<Field> getNeighbours() {
        return neighbours;
    }

    /**
     * Beállítja a szomszédos mezők listáját.
     *
     * @param neighbours a szomszédos {@link Field} lista
     */
    public void setNeighbours(List<Field> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Hozzáadja a szomszédos mezőkhöz {@code neighbour}-t.
     *
     * @param neighbour a hozzáadandó {@link Field}
     */
    public void addNeighbour(Field neighbour) {
        this.neighbours.add(neighbour);
    }

    /**
     * Törli {@code neighbour}-t a szomszédos mezők közül
     *
     * @param neighbour a törlendő {@link Field}
     */
    public void removeNeighbour(Field neighbour) {
        this.neighbours.remove(neighbour);
    }

    /**
     * Visszaadja a mezőn tartózkodó virológusok listáját.
     *
     * @return a mezőn tartózkodó {@link Virologist} lista
     */
    public List<Virologist> getVirologists() {
        return virologists;
    }

    /**
     * Beállítja a mezőn tartózkodó virológusok listáját.
     *
     * @param virologists a mezőn tartózkodó {@link Virologist} lista
     */
    public void setVirologists(List<Virologist> virologists) {
        this.virologists = virologists;
    }

    /**
     * Hozzáadja a mezőn tartózkodó virológusokhoz {@code virologist}-ot.
     *
     * @param virologist a hozzáadandó {@link Virologist}
     */
    public void addVirologist(Virologist virologist) {
        Logger.log(this, "addVirologist", CALL, virologist);
        this.virologists.add(virologist);
        Logger.log(this, "", RETURN);
    }

    /**
     * Törli {@code virologist}-ot a mezőn tartózkodó virológusok közül.
     *
     * @param virologist a törlendő {@link Virologist}
     */
    public void removeVirologist(Virologist virologist) {
        Logger.log(this, "removeVirologist", CALL, virologist);
        this.virologists.remove(virologist);
        Logger.log(this, "", RETURN);
    }

    //endregion
}
