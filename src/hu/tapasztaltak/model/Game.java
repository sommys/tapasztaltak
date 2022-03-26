package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.TestSetup;

import java.util.ArrayList;
import java.util.List;

/**
 * A játékot reprezentáló singleton osztály.
 */
public class Game {
    /**
     * Győzelemhez szükséges genetikai kódok száma.
     */
    public static int maxAgent = 4;
    /**
     * A játék mezőinek listája.
     */
    private List<Field> fields = new ArrayList<>();

    /**
     * Elindítja a játékot, létrehozza a pályát és a további szereplőket.
     */
    public void startGame() {
        // Todo: Peti
    }

    /**
     * Győzelem esetén nyertest hirdet és leállítja a játékot.
     *
     * @param v a potenciális győztes {@link Virologist}
     */
    public void checkEndGame(Virologist v) {
        if (v.getLearnt().size() == maxAgent) {
            System.out.printf("%s játékos győzött!\n", TestSetup.getName(v));
            // Todo: Peti, játék leállítása, egyéb teendők
        }
    }

    //region GETTEREK és SETTEREK

    // Todo: Peti, maxagent maradjon public static? Mert akkor fölösleges a getter/setter

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
