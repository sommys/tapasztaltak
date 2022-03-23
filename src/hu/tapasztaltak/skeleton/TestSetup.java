package hu.tapasztaltak.skeleton;

import hu.tapasztaltak.model.*;

//TODO: DANA
//ADOMM MEGCSINALOM MAGAM
public class TestSetup {
    /**
     * Virologist moves init
     * A virológus mozgását bemutatő függvény
     * Létre kell hozni 2 mezőt és egy virológust.
     * Ezek után az f1 mezőhöz hozzáadjuk a v virolőgust.
     * A két mezőt beállítjuk egymás szomszédjának.
     * A virológust az f2 mezőre mozgatjuk.
     */
    public void virologistMoves(){
        Field f1 = new Field();
        Field f2 = new Field();
        Virologist v = new Virologist();

        f1.addVirologists(v);
        f1.addNeighbours(f2);

        f2.addNeighbours(f1);
        v.move(f2);
    }

    /**
     * Virologist uses agent on themself init
     * A virológus használja magán az ágenst
     */
    public void useAgentOnThemself(){
        Gloves g = new Gloves();
        Cape c = new Cape();
        Protect p = new Protect();
        Protect p2 = new Protect();
        Dance d = new Dance();
        Stun s = new Stun();
        Forget f = new Forget();


    }

}
