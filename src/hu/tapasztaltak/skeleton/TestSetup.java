package hu.tapasztaltak.skeleton;

import hu.tapasztaltak.model.*;

import java.util.HashMap;

public class TestSetup {
    /**
     * Statikus HashMap az objektumok tárolására
     */
    static HashMap<String, Object> storage = new HashMap<String, Object>();

    /**
     * Egy objektumhoz tartozó azonosítót [nevet] adja meg
     * @param o a keresett objektum
     * @return az objektumhoz tartozó azonosító [név], ha nincs benne a HashMap-ben, akkor {@code null}-t ad vissza
     */
    String getName(Object o){
        for(HashMap.Entry<String, Object> e : storage.entrySet()){
            if(o == e.getValue()){
                return e.getKey();
            }
        }
        return null;
    }

    /**
     * Virologist moves init
     * A virológus mozgását bemutatő függvény
     * Létre kell hozni 2 mezőt és egy virológust.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * Ezek után az f1 mezőhöz hozzáadjuk a v virolőgust.
     * A virológusnak beállítjuk az f1 mezőt.
     * A virológust az f2 mezőre mozgatjuk.
     */
    public void virologistMoves(){
        Field f1 = new Field();
        Field f2 = new Field();
        Virologist v = new Virologist();

        storage.put("f1", f1);
        storage.put("f2", f2);
        storage.put("v", v);

        f1.addVirologists(v);

        //ezek nem kellenek akkor
        // A két mezőt beállítjuk egymás szomszédjának.
        //f1.addNeighbours(f2);
        //f2.addNeighbours(f1);

        v.setField(f1);
        v.move(f2);

        //ezek hová?
        //storage.remove(v);
        //storage.remove(f1);
        //storage.remove(f2);
    }

    /**
     * Virologist uses agent on themself init
     * A virológus használja magán az ágenst
     * Létre kell hozni 1 inventoryt és egy virológust.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     */
    public void useAgentOnThemself(){
        /*
        Gloves g = new Gloves();
        Cape c = new Cape();
        Protect p = new Protect();
        Protect p2 = new Protect();
        Dance d = new Dance();
        Stun s = new Stun();
        Forget f = new Forget();*/

        Inventory inv = new Inventory();
        Virologist v = new Virologist();

        v.setInventory(inv);
        //inv.setVirologist(v); :(

        storage.put("inv", inv);
        storage.put("v", v);
    }
}
