package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;
import hu.tapasztaltak.skeleton.TestSetup;

import java.util.ArrayList;
import java.util.List;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * A játékot reprezentáló singleton osztály.
 */
public class Game {
    /**
     * Privát konstruktor, a singleton elvárásainak megfelelően.
     */
    private Game() {}
    /**
     * Az egyetlen Game példány.
     */
    private static Game instance = null;
    /**
     * Győzelemhez szükséges genetikai kódok száma.
     */
    final int maxAgent = 4;
    /**
     * A játék mezőinek listája.
     */
    private List<Field> fields = new ArrayList<>();

    /**
     * Hozzáférés a singleton példányhoz.
     *
     * @return a {@link Game} példánya.
     */
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * Elindítja a játékot, létrehozza a pályát és a további szereplőket.
     */
    public void startGame() {
        // Todo: Peti majd elesben?
    }

    /**
     * Győzelem esetén nyertest hirdet és leállítja a játékot.
     *
     * @param v a potenciális győztes {@link Virologist}
     */
    public void checkEndGame(Virologist v) {
        Logger.log(this, "checkEndGame", CALL, v);
        if (v.getLearnt().size() == maxAgent) {
            System.out.printf("%s játékos győzött!\n", TestSetup.getName(v));
            // Todo: Peti, játék leállítása, egyéb teendők
        }
        Logger.log(this, "", RETURN);
    }

    //region GETTEREK és SETTEREK

    /**
     * Visszaadja a játékban lévő különböző ágensek számát.
     *
     * @return a játékban gyűjtehtő különböző ágensek száma.
     */
    public int getMaxAgent(){ return maxAgent; }

    /**
     * Visszaadja a játék mezőinek listáját.
     *
     * @return a játék {@link Field} listája.
     */
    public List<Field> getFields() { return fields; }

    /**
     * Beállítja a játék mezőinek listáját.
     *
     * @param fields a játék {@link Field} listája.
     */
    public void setFields(List<Field> fields) { this.fields = fields; }

    /**
     * Hozzáadja {@code field}-et a játék mezőinek listájához.
     *
     * @param field a hozzáadandó {@link Field}
     */
    public void addFields(Field field) { fields.add(field); }

    /**
     * Törli {@code field}-et a játék mezőinek listájából.
     *
     * @param field a törlendő {@link Field}
     */
    public void removeFields(Field field) { fields.remove(field); }

    //endregion
}
