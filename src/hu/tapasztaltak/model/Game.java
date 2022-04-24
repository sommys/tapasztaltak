package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;
import hu.tapasztaltak.skeleton.Logger;
import hu.tapasztaltak.skeleton.TestSetup;
import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hu.tapasztaltak.skeleton.Logger.LogType.*;

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
    public void startGame(int fieldNum, int laborNum, int warehouseNum, int shelterNum, int virologistNum) {
        RoundManager rm = RoundManager.getInstance();
        for(int i = 1; i <= fieldNum; i++){
            Field f = new Field();
            TestSetup.addObject(f, "f"+i);
            ProtoLogger.logMessage(String.format("%s created",getIdForObject(f)));
            fields.add(f);
            rm.addSteppable(f);
        }
        for(int i = 1; i <= laborNum; i++){
            Labor l= new Labor();
            TestSetup.addObject(l, "l"+i);
            ProtoLogger.logMessage(String.format("%s created",getIdForObject(l)));
            fields.add(l);
            rm.addSteppable(l);
        }
        for(int i = 1; i <= warehouseNum; i++){
            Warehouse w = new Warehouse();
            TestSetup.addObject(w,"w"+i);
            ProtoLogger.logMessage(String.format("%s created",getIdForObject(w)));
            fields.add(w);
            rm.addSteppable(w);
        }
        for(int i = 1; i <= shelterNum; i++){
            Shelter s = new Shelter();
            TestSetup.addObject(s,"s"+i);
            ProtoLogger.logMessage(String.format("%s created",getIdForObject(s)));
            fields.add(s);
            rm.addSteppable(s);
        }
        for(int i = 1; i <= virologistNum; i++){
            Virologist v = new Virologist();
            TestSetup.addObject(v,"v"+i);
            rm.addSteppable(v);
            rm.addVirologist(v);
            Random r = new Random();
            Field f =fields.get(r.nextInt(fields.size()));
            v.setField(f);
            ProtoLogger.logMessage(String.format("%s created on %s",getIdForObject(v),getIdForObject(f)));
        }
    }

    /**
     * Győzelem esetén nyertest hirdet és leállítja a játékot.
     *
     * @param v a potenciális győztes {@link Virologist}
     */
    public void checkEndGame(Virologist v) {
        if (v.getLearnt().size() == maxAgent) {
            ProtoLogger.logMessage(String.format("%s játékos győzött!",getIdForObject(v)));
            // Todo: Peti, játék leállítása, egyéb teendők
        }
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
    public void addField(Field field) { fields.add(field); }

    /**
     * Törli {@code field}-et a játék mezőinek listájából.
     *
     * @param field a törlendő {@link Field}
     */
    public void removeField(Field field) { fields.remove(field); }

    //endregion
}
