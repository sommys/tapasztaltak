package hu.tapasztaltak.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static hu.tapasztaltak.proto.ProtoLogger.logMessage;
import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;

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
    protected List<Field> neighbours = new ArrayList<>();
    /**
     * A mezőn tartózkodó virológusok listája.
     */
    protected List<Virologist> virologists = new ArrayList<>();

    /**
     * A {@code v} virológusnak adja, a mezőn található dolgo[ka]t.
     *
     * @param v a {@link Virologist}, aki a dolgo[ka]t kapja.
     */
    public void getItem(Virologist v) throws Exception {
        String vList = virologists.size() == 1 ? "-" : virologists.stream().filter(it -> it != v).map(it -> getIdForObject(it)).collect(Collectors.joining(", "));
        logMessage(String.format("%s scanned %s -> virologists: %s", getIdForObject(v), getIdForObject(this), vList));
    }

    /**
     * Újratölti a mezőre jellemző dolgo[ka]t.
     */
    public void refresh() {}

    /**
     * Refreshcountert csökkenti és ha nulla, akkor a refresh függvényt meghívja.
     */
    public void step() {
        if (refreshCounter > 0) {
            refreshCounter--;
        }

        if (refreshCounter == 0) {
            refresh();
            refreshCounter = -1;
        }
    }

    /**
     * A medve hívja, és szétzúzza a mezőn található anyagokat, amennyiben vannak.
     */
    public void destroyStuff(){ }

    /**
     * Megállapítja {@code f} mezőről, hogy szomszédos-e.
     *
     * @param f a {@link Field}, ami a kérdéses szomszéd
     * @return szomszédos-e a mező
     */
    public boolean isNeighbour(Field f) {
        return neighbours.contains(f);
    }

    /**
     * Visszatér egy random szomszédos mezővel.
     *
     * @return random szomszédos {@link Field}
     */
    public Field getRandomNeighbour() {
        return neighbours.isEmpty() ? this : neighbours.get(new Random().nextInt(neighbours.size()));
    }

    /**
     * A {@link Virologist} választ egyet a vele egy mezőn tartózkodó virológusok közül.
     * @param v a választó {@link Virologist}
     * @return kiválasztott {@link Virologist}
     */
    public Virologist chooseVirologist(Virologist v) {
        List<Virologist> choosable = virologists.stream().filter(vir -> vir != v).collect(Collectors.toList());

        Random r = new Random();
        Virologist chosen = choosable.get(r.nextInt(choosable.size()));
        return chosen;
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
        this.virologists.add(virologist);
    }

    /**
     * Törli {@code virologist}-ot a mezőn tartózkodó virológusok közül.
     *
     * @param virologist a törlendő {@link Virologist}
     */
    public void removeVirologist(Virologist virologist) {
        this.virologists.remove(virologist);
    }

    @Override
    public String toString() {
        return getIdForObject(this);
    }

    //endregion
}
