package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Körök irányításáért felelős singleton osztály.
 */
public class RoundManager {
    /**
     * Privát konstruktor, a singleton elvárásainak megfelelően.
     */
    private RoundManager() {}

    /**
     * Az egyetlen RoundManager példány.
     */
    private static RoundManager instance = null;

    /**
     * A körben már lépett virológusok száma, alap értéke 0.
     */
    private int movedCounter = 0;
    /**
     * Léptethető példányok listája.
     */
    private List<ISteppable> steppables = new ArrayList<>();
    /**
     * A játékban résztvevő virológusok listája.
     */
    private List<Virologist> virologists = new ArrayList<>();

    /**
     * Hozzáférés a singleton példányhoz.
     *
     * @return a {@link RoundManager} példánya.
     */
    public static RoundManager getInstance() {
        if (instance == null) {
            instance = new RoundManager();
        }
        return instance;
    }

    /**
     * Elindít egy új kört.
     */
    private void newRound() {
        for (var s : steppables) {
            s.step();
        }

        movedCounter = 0;
    }

    /**
     * Növeli a lépett virológusok számát és a kör végén újat indít.
     */
    public void virologistMoved() {
        movedCounter++;
        if (movedCounter == virologists.size()) {
            movedCounter = 0;
            newRound();
        }
    }

    //region GETTEREK és SETTEREK

    /**
     * Visszaadja a körben már lépett virológusok számát.
     *
     * @return a körben már lépett virológusok száma.
     */
    public int getMovedCounter() { return movedCounter; }

    /**
     * Beállítja a körben már lépett virológusok számát.
     *
     * @param movedCounter a körben már lépett virológusok száma.
     */
    public void setMovedCounter(int movedCounter) { this.movedCounter = movedCounter; }

    /**
     * Visszaadja a léptethető példányok listáját.
     *
     * @return a {@link ISteppable} példányok listája.
     */
    public List<ISteppable> getSteppables() { return steppables; }

    /**
     * Beállítja a léptethető példányok listáját.
     *
     * @param steppables a {@link ISteppable} példányok listája.
     */
    public void setSteppables(List<ISteppable> steppables) { this.steppables = steppables; }

    /**
     * Hozzáadja {@code steppable}-t a léptethető példányok listájához.
     *
     * @param steppable a hozzáadandó {@link ISteppable} példány.
     */
    public void addSteppable(ISteppable steppable) { steppables.add(steppable); }

    /**
     * Törli {@code steppable}-t a léptethető példányok listájából.
     *
     * @param steppable a törlendő {@link ISteppable} példány.
     */
    public void removeSteppable(ISteppable steppable) { steppables.remove(steppable); }

    /**
     * Visszaadja a játékban résztvevő virológusok listáját.
     *
     * @return a játékban résztvevő virológusok listája.
     */
    public List<Virologist> getVirologists() { return virologists; }

    /**
     * Beállítja a játékban résztvevő virológusok listáját.
     *
     * @param virologists a játékban résztvevő virológusok listája.
     */
    public void setVirologists(List<Virologist> virologists) { this.virologists = virologists; }

    /**
     * Hozzáadja {@code virologist}-ot a virológusok listájához.
     *
     * @param virologist a hozzáadandó {@link Virologist}
     */
    public void addVirologist(Virologist virologist) { virologists.add(virologist); }

    /**
     * Törli {@code virologist}-ot a virológusok listájából.
     *
     * @param virologist a törlendő {@link Virologist}
     */
    public void removeVirologist(Virologist virologist) { virologists.remove(virologist); }

    //endregion
}
