package hu.tapasztaltak.skeleton;

import hu.tapasztaltak.model.*;

import java.util.HashMap;

public class TestSetup {
    /**
     * Statikus HashMap az objektumok tárolására
     */
    public static HashMap<String, Object> storage = new HashMap<>();

    /**
     * Egy objektumhoz tartozó azonosítót [nevet] adja meg
     * @param o a keresett objektum
     * @return az objektumhoz tartozó azonosító [név], ha nincs benne a HashMap-ben, akkor {@code null}-t ad vissza
     */
    public static String getName(Object o){
        for(HashMap.Entry<String, Object> e : storage.entrySet()){
            if(o == e.getValue()){
                return e.getKey();
            }
        }
        return null;
    }

    /**
     * Hozzáadja a HashMaphez a paraméterben megadott objektumot
     * @param o az objektum
     * @param name az objektum neve
     */
    public static void addObject(Object o, String name) {
        storage.put(name, o);
    }

    /**
     * Eltávolítja a HashMapből a paraméterben megadott objektumot
     * @param o az objektum
     */
    public static void removeObject(Object o) {
        storage.remove(getName(o));
    }

    /**
     * Eltávolítja a HashMapből a paraméterben megadott objektumot úgy, hogy előtte megkeresi azt a neve alapján
     * @param name az objektum neve
     */
    public static void removeObject(String name) {
        storage.remove(name);
    }

    /**
     * Virologist moves init
     * A virológus mozgását bemutatő függvény
     * Létre kell hozni 2 mezőt és egy virológust.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * Ezek után az f1 mezőhöz hozzáadjuk a v virolőgust.
     * A virológust az f2 mezőre mozgatjuk.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistMoves(){
        Field f1 = new Field();
        Field f2 = new Field();
        Virologist v = new Virologist();
        storage.put("f1", f1);
        storage.put("f2", f2);
        storage.put("v", v);
        f1.addVirologist(v);
        Logger.log("itt kéne hogy mi történt");

        v.setField(f1);
        v.move(f2);

        storage.remove(v);
        storage.remove(f1);
        storage.remove(f2);
    }

    /**
     * Virologist uses agent on themself init
     * A virológus használja magán az ágenst
     * Létre kell hozni 1 inventoryt és 1 virológust.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus magára keni az ágenst.
     * A létrehozott objektumokat kivesszük a HashMapből.
     * @param a Ágens, amit felkennek
     */
    public static void useAgentOnThemself(boolean stunned, Agent a){
        Inventory inv = new Inventory();
        Virologist v = new Virologist();

        v.setStunned(stunned);
        v.setInventory(inv);

        storage.put("inv", inv);
        storage.put("v", v);

        v.useAgent(a, v);

        storage.remove("inv", inv);
        storage.remove("v", v);
    }

    /**
     * Virologist uses agent on other virologist init
     * A virológus használja egy másik virológuson az ágenst
     * Létre kell hozni 1 inventoryt és 2 virológust.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus egy másik virológusra keni az ágenst.
     * A létrehozott objektumokat kivesszük a HashMapből.
     * @param a Ágens, amit felkennek
     */
    public static void useAgentOnOtherVirologist(Agent a){
        Inventory inv = new Inventory();
        Virologist v1 = new Virologist();
        Virologist v2 = new Virologist();

        v1.setInventory(inv);

        storage.put("inv", inv);
        storage.put("v1", v1);
        storage.put("v2", v2);

        v1.useAgent(a, v2);

        storage.remove("inv", inv);
        storage.remove("v1", v1);
        storage.remove("v2", v2);
    }

    /**
     * Virologist makes agent init
     * A virológus elkészít egy ágenst
     * Létre kell hozni 1 inventoryt, 1 virológust és egy genetikai kódot.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus létrehozza az ágenst.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistMakesAgent(Agent a){
        Inventory inv = new Inventory();
        Virologist v = new Virologist();
        Gene g = new Gene();

        g.setAgent(a);
        v.setInventory(inv);

        storage.put("inv", inv);
        storage.put("v", v);
        storage.put("g", g);

        v.makeAgent(g);

        storage.remove("inv", inv);
        storage.remove("v", v);
        storage.remove("g", g);
    }

    /**
     * Virologist scans warehouse init
     * A virológus letapogatja a raktárat
     * Létre kell hozni 1 raktárat és 1 virológust.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus meghívja a tapogat függvényt.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistScansWarehouse(){
        Warehouse w = new Warehouse();
        Virologist v = new Virologist();

        v.setField(w);
        w.addVirologist(v);

        storage.put("v", v);
        storage.put("w", w);

        v.scanning();

        storage.remove("v", v);
        storage.remove("w", w);
    }

    /**
     * Virologist scans labor init
     * A virológus letapogatja a labort
     * Létre kell hozni 1 labort, 1 virológust, 1 játékot és egy genetikai kódot.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     *
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistScansLabor(){
        Labor l = new Labor();
        Virologist v = new Virologist();
        Gene g = new Gene();

        v.setField(l);
        l.addVirologist(v);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!l.setGene(g);

        storage.put("v", v);
        storage.put("l", l);
        storage.put("g", g);

        v.scanning();

        storage.remove("v", v);
        storage.remove("l", l);
        storage.remove("g", g);
    }

    /**
     *
     *
     * Létre kell hozni
     * A létrehozott objektumokat beletesszük a HashMapbe.
     *
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
}
