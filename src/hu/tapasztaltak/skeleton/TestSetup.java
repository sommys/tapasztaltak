package hu.tapasztaltak.skeleton;

import hu.tapasztaltak.model.*;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import static hu.tapasztaltak.skeleton.Logger.LogType.COMMENT;
import static hu.tapasztaltak.skeleton.Logger.LogType.QUESTION;

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
     * Megkérdezi a felhasználótól, hogy {@code v} le van bénulva
     * @param v a {@link Virologist}, akire vonatkozik a kérdés
     */
    private static void stunQuestion(Virologist v) {
        Logger.log(null, "Le van bénulva a virológus? (I/N):", QUESTION);
        Scanner sc = new Scanner(System.in);
        String stunDecision = sc.nextLine();
        if(stunDecision.equalsIgnoreCase("I")){
            Stun s = new Stun();
            v.addModifier(s);
            s.effect(v);
        }
    }

    /**
     * A beérkezett szám alapján eldönti, hogy melyik ágenst használjuk.
     * @param agentnum a beérkezett szám
     * @param v a {@link Virologist}, aki használja az ágenst
     * @return visszadja a választott ágenst
     */
    private static void useagenthelp(int agentnum, Virologist v, boolean glvs){
        switch (agentnum){
            case 0:
                Dance dance = new Dance();
                v.addModifier(dance);
                storage.put("d",dance);
                if(glvs){
                    Gloves gloves = new Gloves();
                    v.addDefense(gloves);
                    gloves.tryToBlock(v,v,dance);
                }
                v.useAgent(dance,v);
                storage.remove("d",dance);
                break;
            case 1:
                Protect protect = new Protect();
                v.addDefense(protect);
                storage.put("p",protect);
                if(glvs){
                    Gloves gloves = new Gloves();
                    v.addDefense(gloves);
                    gloves.tryToBlock(v,v,protect);
                }
                v.useAgent(protect,v);
                storage.remove("p",protect);
                break;
            case 2:
                Forget forget = new Forget();
                v.addModifier(forget);
                storage.put("f",forget);
                if(glvs){
                    Gloves gloves = new Gloves();
                    v.addDefense(gloves);
                    gloves.tryToBlock(v,v,forget);
                }
                v.useAgent(forget,v);
                storage.remove("f",forget);
                break;
            case 3:
                Stun stun = new Stun();
                v.addModifier(stun);
                storage.put("s",stun);
                if(glvs){
                    Gloves gloves = new Gloves();
                    v.addDefense(gloves);
                    gloves.tryToBlock(v,v,stun);
                }
                v.useAgent(stun,v);
                storage.remove("f",stun);
                break;
        }
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
        System.out.println("--- Setting up Test Environment for Virologist Moves ---");
        Field f1 = new Field();
        Field f2 = new Field();
        Virologist v = new Virologist();
        storage.put("f1", f1);
        storage.put("f2", f2);
        storage.put("v", v);

        f1.addVirologist(v);
        v.setField(f1);

        System.out.println("--- Setup Test Environment for Virologist Moves DONE---");

        stunQuestion(v);

        v.move(f2);
        storage.remove("v");
        storage.remove("f1");
        storage.remove("f2");
    }

    /**
     * Virologist uses agent on themself init
     * A virológus használja magán az ágenst
     * Létre kell hozni 1 inventoryt és 1 virológust.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus magára keni az ágenst.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void useAgentOnThemself(){
        System.out.println("--- Setting up Test Environment for useAgentOnThemself ---");
        Inventory inv = new Inventory();
        Virologist v = new Virologist();
        v.setInventory(inv);
        storage.put("inv", inv);
        storage.put("v", v);
        System.out.println("--- Setup Test Environment for useAgentOnThemself DONE---");
        stunQuestion(v);
        if(!v.isStunned()){
            Scanner sc = new Scanner(System.in);
            Logger.log(null,"Melyik ágenst szeretnéd használni?[0:Dance, 1: Protect, 2:Forget, 3:Stun]",QUESTION);
            Agent use;
            int agentnum = sc.nextInt();
            if (agentnum > 3 || agentnum < 0) {
                System.out.println("Hibás bemenet");
                storage.clear();
                return;
            }
            switch (agentnum){
                case 0:
                    use = new Dance();
                    break;
                case 1:
                    use = new Protect();
                    break;
                case 2:
                    use = new Forget();
                    break;
                case 3:
                    use = new Stun();
                    break;
                default:
                    use = null;
            }
            v.getInventory().addAgent(use);
            storage.put("a",use);
            Logger.log(null,"Van olyan védőfelszerelés/ágens a virológuson ami levédi az ágenst?[I/N]",QUESTION);
            sc.nextLine();
            String suite = sc.nextLine();
            if(suite.equalsIgnoreCase("N")){
                v.useAgent(use,v);
                storage.clear();
                return;
            }
            else if(suite.equalsIgnoreCase("I")) {
                Logger.log(null, "Mennyi köpeny van a virológuson? 0..3", QUESTION);
                int capenum = sc.nextInt();
                Logger.log(null, "Mennyi védő ágens van a virológuson?0.." + (3 - capenum), QUESTION);
                int protectnum = sc.nextInt();
                Logger.log(null, "Mennyi Kesztyű van a virológuson?0.." + (3 - (capenum + protectnum)), QUESTION);
                int glovesnum = sc.nextInt();
                if ((capenum + protectnum + glovesnum) > 3 || (capenum + protectnum + glovesnum) < 0) {
                    System.out.println("Hibás bemenet");
                    storage.clear();
                    return;
                }
                for (int i = 0; i < capenum; i++) {
                    Cape cape = new Cape();
                    v.addDefense(cape);
                    storage.put("cape" + i, cape);
                }
                for (int i = 0; i < protectnum; i++) {
                    Protect protect = new Protect();
                    v.addDefense(protect);
                    storage.put("protect" + i, protect);
                }
                for (int i = 0; i < glovesnum; i++) {
                    Gloves gloves = new Gloves();
                    v.addDefense(gloves);
                    storage.put("gloves" + i, gloves);
                }
                v.useAgent(use, v);
            }
            else{
                System.out.println("Hibás bemenet!");
                storage.clear();
                return;
            }

        }
        storage.clear();
    }

    /**
     * Virologist uses agent on other virologist init
     * A virológus használja egy másik virológuson az ágenst
     * Létre kell hozni 1 inventoryt és 2 virológust.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus egy másik virológusra keni az ágenst.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void useAgentOnOtherVirologist(){

        System.out.println("--- Setting up Test Environment for useAgentOnOtherVirologist ---");
        Inventory inv = new Inventory();
        Virologist v = new Virologist();
        Virologist v2 = new Virologist();
        Field f1 = new Field();
        Field f2 = new Field();
        storage.put("f1",f1);
        storage.put("f2",f2);
        storage.put("inv", inv);
        storage.put("v", v);
        storage.put("v2", v2);
        f1.addVirologist(v);
        v.setField(f1);
        v.setInventory(inv);
        System.out.println("--- Setup Test Environment for useAgentOnOtherVirologist DONE---");
        stunQuestion(v);
        if(!v.isStunned()){
            Scanner sc = new Scanner(System.in);
            Logger.log(null,"Melyik ágenst szeretnéd használni?[0:Dance, 1: Protect, 2:Forget, 3:Stun]",QUESTION);
            Agent use;
            int agentnum = sc.nextInt();
            if (agentnum > 3 || agentnum < 0) {
                System.out.println("Hibás bemenet");
                storage.clear();
                return;
            }
            switch (agentnum){
                case 0:
                    use = new Dance();
                    break;
                case 1:
                    use = new Protect();
                    break;
                case 2:
                    use = new Forget();
                    break;
                case 3:
                    use = new Stun();
                    break;
                default:
                    use = null;
            }
            v.getInventory().addAgent(use);
            storage.put("a",use);
            Logger.log(null,"Ugyanazon a mezőn van, akire használni akarod az ágenst? (I/N)",QUESTION);
            sc.nextLine();
            String samefield = sc.nextLine();
            if(samefield.equalsIgnoreCase("I")){
                f1.addVirologist(v2);
                v2.setField(f1);
            }
            else if(samefield.equalsIgnoreCase("N")){
                f2.addVirologist(v2);
                v2.setField(f2);
            }
            else{
                System.out.println("Hibás bemenet!");
                storage.clear();
                return;
            }
            Logger.log(null,"Van olyan védőfelszerelés/ágens a virológuson ami levédi az ágenst?[I/N]",QUESTION);
            String suite = sc.nextLine();
            if(suite.equalsIgnoreCase("N")){
                v.useAgent(use,v2);
                storage.clear();
                return;
            }
            else if(suite.equalsIgnoreCase("I")) {
                Logger.log(null, "Mennyi köpeny van a virológuson? 0..3", QUESTION);
                int capenum = sc.nextInt();
                Logger.log(null, "Mennyi védő ágens van a virológuson?0.." + (3 - capenum), QUESTION);
                int protectnum = sc.nextInt();
                Logger.log(null, "Mennyi Kesztyű van a virológuson?0.." + (3 - (capenum + protectnum)), QUESTION);
                int glovesnum = sc.nextInt();
                if ((capenum + protectnum + glovesnum) > 3 || (capenum + protectnum + glovesnum) < 0) {
                    System.out.println("Hibás bemenet");
                    storage.clear();
                    return;
                }
                for (int i = 0; i < capenum; i++) {
                    Cape cape = new Cape();
                    v2.addDefense(cape);
                    storage.put("cape" + i, cape);
                }
                for (int i = 0; i < protectnum; i++) {
                    Protect protect = new Protect();
                    v2.addDefense(protect);
                    storage.put("protect" + i, protect);
                }
                for (int i = 0; i < glovesnum; i++) {
                    Gloves gloves = new Gloves();
                    v2.addDefense(gloves);
                    storage.put("gloves" + i, gloves);
                }
                v.useAgent(use, v2);
            }
            else{
                System.out.println("Hibás bemenet!");
                storage.clear();
                return;
            }

        }
        storage.clear();
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

        storage.put("v", v);
        storage.put("w", w);

        v.setField(w);
        w.addVirologist(v);

        stunQuestion(v);

        if(!v.isStunned()) {
            Scanner sc = new Scanner(System.in);
            Logger.log(null, "Hány zsák van a virológuson? (0..3):", QUESTION);
            int bags = sc.nextInt();
            if (bags > 3 || bags < 0) {
                System.out.println("Hibás bemenet");
                return;
            }
            for (int i = 0; i < bags; i++) {
                Bag b = new Bag();
                b.add(v.getInventory());
                b.activate(v);
            }

            Logger.log(null, "Mennyi hely van még a tárhelyben? (0.." + v.getInventory().getSize() + "):", QUESTION);
            int remaining = sc.nextInt();
            if (remaining < 0 || remaining > v.getInventory().getSize()) {
                System.out.println("Hibás bemenet");
                return;
            }

            for (int i = 0; i < v.getInventory().getSize() - remaining; i++) {
                Nucleotid placeholder = new Nucleotid();
                v.getInventory().addMaterial(placeholder);
            }
        }

        Nucleotid n = new Nucleotid();
        Nucleotid n1 = new Nucleotid();
        Aminoacid a = new Aminoacid();
        Aminoacid a1 = new Aminoacid();

        storage.put("n", n);
        storage.put("n1", n1);
        storage.put("a", a);
        storage.put("a1", a1);

        w.addMaterials(n);
        w.addMaterials(n1);
        w.addMaterials(a);
        w.addMaterials(a1);

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
