package hu.tapasztaltak.skeleton;

import hu.tapasztaltak.model.*;

import java.util.*;

import static hu.tapasztaltak.skeleton.Logger.LogType.*;

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
            storage.put("s", s);
            v.addModifier(s);
            s.effect(v);
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

        System.out.println("--- Setup Test Environment for Virologist Moves DONE ---");

        stunQuestion(v);

        v.move(f2);
        storage.clear();
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
        System.out.println("--- Setup Test Environment for useAgentOnThemself DONE ---");
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
        System.out.println("--- Setup Test Environment for useAgentOnOtherVirologist DONE ---");
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
    public static void virologistMakesAgent(){
        System.out.println("--- Setting up Test Environment for virologistMakesAgent ---");
        Inventory inv = new Inventory();
        Virologist v = new Virologist();
        Gene g = new Gene();
        Agent a = null;
        storage.put("inv", inv);
        storage.put("v", v);
        storage.put("g", g);
        storage.put("a", a);

        v.setInventory(inv);
        stunQuestion(v);

        if(!v.isStunned()){
            Scanner sc = new Scanner(System.in);
            Logger.log(null,"Melyik ágenst szeretnéd elkészíteni?[0:Dance, 1: Protect, 2:Forget, 3:Stun]",QUESTION);
            int agentnum = sc.nextInt();
            if (agentnum > 3 || agentnum < 0) {
                System.out.println("Hibás bemenet");
                storage.clear();
                return;
            }
            Logger.log(null, "Van elég nyersanyaga a virológusnak, hogy elkészítse az ágenst? (I/N):", QUESTION);
            sc.nextLine();
            String enoughMaterial = sc.nextLine();
            boolean hasEnough = enoughMaterial.equalsIgnoreCase("I");
            switch (agentnum){
                case 0:
                    a = new Dance();
                    storage.put("dance", a);
                    if(hasEnough){
                        Nucleotid invN1 = new Nucleotid();
                        Aminoacid invA1 = new Aminoacid();
                        invA1.add(v.getInventory());
                        invN1.add(v.getInventory());
                        storage.put("invN1", invN1);
                        storage.put("invA1", invA1);
                    }
                    break;
                case 1:
                    a = new Protect();
                    storage.put("protect", a);
                    if(hasEnough){
                        Nucleotid invN1 = new Nucleotid();
                        Nucleotid invN2 = new Nucleotid();
                        Aminoacid invA1 = new Aminoacid();
                        Aminoacid invA2 = new Aminoacid();
                        invN1.add(v.getInventory());
                        invN2.add(v.getInventory());
                        invA1.add(v.getInventory());
                        invA2.add(v.getInventory());
                        storage.put("invN1", invN1);
                        storage.put("invN2", invN2);
                        storage.put("invA1", invA1);
                        storage.put("invA2", invA2);
                    }
                    break;
                case 2:
                    a = new Forget();
                    storage.put("forget", a);
                    if(hasEnough){
                        Nucleotid invN1 = new Nucleotid();
                        Nucleotid invN2 = new Nucleotid();
                        Nucleotid invN3 = new Nucleotid();
                        invN1.add(v.getInventory());
                        invN2.add(v.getInventory());
                        invN3.add(v.getInventory());
                        storage.put("invN1", invN1);
                        storage.put("invN2", invN2);
                        storage.put("invN3", invN3);
                    }
                    break;
                case 3:
                    a = new Stun();
                    storage.put("stun", a);
                    if(hasEnough){
                        Aminoacid invA1 = new Aminoacid();
                        Aminoacid invA2 = new Aminoacid();
                        Aminoacid invA3 = new Aminoacid();
                        invA1.add(v.getInventory());
                        invA2.add(v.getInventory());
                        invA3.add(v.getInventory());
                        storage.put("invA1", invA1);
                        storage.put("invA2", invA2);
                        storage.put("invA3", invA3);
                    }
                    break;
                default:
                    break;
            }
        }
        g.setAgent(a);
        System.out.println("--- Setup Test Environment for virologistMakesAgent DONE ---");
        v.makeAgent(g);

        storage.clear();
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
                storage.clear();
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
                storage.clear();
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

        storage.clear();
    }

    /**
     * Virologist scans labor init
     * A virológus letapogatja a labort
     * Létre kell hozni 1 labort, 1 virológust, 1 játékot és egy genetikai kódot.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus meghívja a tapogat függvényt.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistScansLabor(){
        System.out.println("--- Setting up Test Environment for useAgentOnOtherVirologist ---");
        Labor l = new Labor();
        Virologist v = new Virologist();
        Gene g = new Gene();

        storage.put("v", v);
        storage.put("l", l);
        storage.put("g", g);

        v.setField(l);
        l.addVirologist(v);
        l.setGene(g);

        System.out.println("--- Setup Test Environment for useAgentOnOtherVirologist DONE ---");

        stunQuestion(v);

        if(!v.isStunned()) {
            Scanner sc = new Scanner(System.in);
            Logger.log(null, "Megtanulta már az itt lévő genetikai kódot a virológus? (I/N):", QUESTION);
            String learn = sc.nextLine();
            if (learn.equalsIgnoreCase("I")) {
                storage.clear();
                return;
            } else if (learn.equalsIgnoreCase("N")) {
                v.learn(g);
                v.addLearnt(g);

                Logger.log(null, "Nyert a virológus? (I/N):", QUESTION);
                String win = sc.nextLine();
                if (win.equalsIgnoreCase("I")) {
                    while ( v.getLearnt().size() != Game.getInstance().getMaxAgent() ){
                        v.addLearnt(g);
                    }
                    Game.getInstance().checkEndGame(v);
                    storage.clear();
                    return;
                } else if (win.equalsIgnoreCase("N")) {
                    if( v.getLearnt().size() == Game.getInstance().getMaxAgent()){
                        v.getLearnt().remove(0);
                    }
                    else {
                        while ( v.getLearnt().size() != Game.getInstance().getMaxAgent()-1 ){
                            v.addLearnt(g);
                        }
                    }
                    Game.getInstance().checkEndGame(v);
                    storage.clear();
                    return;
                } else {
                    System.out.println("Hibás bemenet!");
                    storage.clear();
                    return;
                }
            } else {
                System.out.println("Hibás bemenet!");
                storage.clear();
                return;
            }

        }

        v.scanning();

        storage.clear();
    }

    /**
     * Virologist Scans Shelter init
     * A virológus letapogatja az óvóhelyet, esetleg felveszi az ott található felszerelést (ha van)
     * Létre kell hozni 1 óvóhelyet, 1 virológust, opcionálisan 1 felszerelést az óvóhelyre.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistScansShelter(){
        System.out.println("--- Setting up Test Environment for Virologist scans shelter ---");
        Shelter s = new Shelter();
        Virologist v = new Virologist();
        Inventory inv = new Inventory();

        storage.put("v", v);
        storage.put("s", s);
        storage.put("inv", inv);

        v.setInventory(inv);
        v.setField(s);
        s.addVirologist(v);

        stunQuestion(v);
        if(!v.isStunned()){
            Logger.log(null, "Mennyi hely van még a virológusnál? (0..10):", QUESTION);
            Scanner sc = new Scanner(System.in);
            int remaining = sc.nextInt();
            if(remaining<0 || remaining>10){
                System.out.println("Hibás bemenet!");
                storage.clear();
                return;
            }
            for(int i = 0; i < 10-remaining; i++){
                Cape c = new Cape();
                storage.put("invC"+i, c);
                c.add(v.getInventory());
            }
            Logger.log(null, "Milyen felszerelés van az óvóhelyen? [0=Semmi, 1=Bag, 2=Cape, 3=Gloves]", QUESTION);
            int suiteOnShelter = sc.nextInt();
            switch(suiteOnShelter){
                case 0: break;
                case 1:
                    Bag b = new Bag();
                    storage.put("b", b);
                    s.setSuite(b);
                    break;
                case 2:
                    Cape c = new Cape();
                    storage.put("c", c);
                    s.setSuite(c);
                    break;
                case 3:
                    Gloves g = new Gloves();
                    storage.put("g", g);
                    s.setSuite(g);
                    break;
                default:
                    System.out.println("Hibás bemenet!");
                    storage.clear();
                    return;
            }
        }
        System.out.println("--- Setup Test Environment for Virologist scans shelter DONE ---");
        v.scanning();
        storage.clear();
    }

    /**
     * Virologist Steals Material init
     * A virológus ellop egy anyagot.
     * Létre kell hozni 2 virológust, 2 mezőt és 2 inventoryt.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus meghívja a lopás függvényt.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistStealsMaterial(){
        System.out.println("--- Setting up Test Environment for VirologistStealsMaterial ---");
        Virologist v1 = new Virologist();
        Virologist v2 = new Virologist();
        Field f1 = new Field();
        Field f2 = new Field();
        Inventory inv = new Inventory();
        Inventory inv2 = new Inventory();
        v1.setInventory(inv);
        v2.setInventory(inv2);
        v1.setField(f1);
        storage.put("v",v1);
        storage.put("v2",v2);
        storage.put("f",f1);
        storage.put("f2",f2);
        storage.put("inv",inv);
        storage.put("inv2",inv);
        f1.addVirologist(v1);
        System.out.println("--- Setup Test Environment for VirologistStealsMaterial DONE---");
        stunQuestion(v1);
        Scanner sc = new Scanner(System.in);
        if(!v1.isStunned()) {
            Logger.log(null,"Mennyi zsákot visel a rabló virológus? 0..3",QUESTION);
            int bags = sc.nextInt();
            for (int i = 0; i < bags; i++) {
                Bag bag = new Bag();
                storage.put("bag"+i,bag);
                bag.add(v1.getInventory());
                bag.activate(v1);
            }
            Logger.log(null, "Mennyi hely van a virológus inventoryába? 0.."+v1.getInventory().getSize(), QUESTION);
            int vinvcapacity = sc.nextInt();
            if (vinvcapacity < 0 || vinvcapacity > v1.getInventory().getSize()) {
                System.out.println("Hibás bemenet");
                storage.clear();
                return;
            }

            for (int i = 0; i < v1.getInventory().getSize() - vinvcapacity; i++) {
                Nucleotid placeholder = new Nucleotid();
                v1.getInventory().addMaterial(placeholder);
            }
            Logger.log(null, "Ugyanazon a mezőn van, akitől lopni akarsz? (I/N)", QUESTION);
            sc.nextLine();
            String samefield = sc.nextLine();
            if (samefield.equalsIgnoreCase("I")) {
                f1.addVirologist(v2);
                v2.setField(f1);
            } else if (samefield.equalsIgnoreCase("N")) {
                f2.addVirologist(v2);
                v2.setField(f2);
            } else {
                System.out.println("Hibás bemenet!");
                storage.clear();
                return;
            }
            Logger.log(null,"Mennyi zsákot visel a kirabolni kívánt virológusnál?0..3",QUESTION);
            int bagnum = sc.nextInt();
            for (int i = 0; i < bagnum; i++) {
                Bag bag = new Bag();
                storage.put("bag"+i,bag);
                bag.add(v2.getInventory());
                bag.activate(v2);
            }

            Logger.log(null, "Mennyi nukleotid legyen a kirabolni kívánt virológusnál?0.." + v2.getInventory().getSize(), QUESTION);
            int nukleotidnum = sc.nextInt();
            for (int i = 0; i < nukleotidnum; i++) {
                Nucleotid nucleotid = new Nucleotid();
                nucleotid.add(v2.getInventory());
                storage.put("nuc" + i, nucleotid);
            }
            Logger.log(null, "Mennyi aminosav legyen a kirabolni kívánt virológusnál?0.." + (v2.getInventory().getSize()-v2.getInventory().getUsedSize()), QUESTION);
            int aminoacidnum = sc.nextInt();
            for (int i = 0; i < aminoacidnum; i++) {
                Aminoacid aminoacid = new Aminoacid();
                aminoacid.add(v2.getInventory());
                storage.put("amino" + i, aminoacid);
            }
        }
        v1.steal(v2);
        storage.clear();
    }

    /**
     * Virologist Steals Suite init
     * A virológus ellop egy felszerelést.
     * Létre kell hozni 2 virológust, 2 mezőt és 2 inventoryt.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus meghívja a lopás függvényt.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistStealsSuite(){
        System.out.println("--- Setting up Test Environment for VirologistStealsMaterial ---");
        Virologist v1 = new Virologist();
        Virologist v2 = new Virologist();
        Field f1 = new Field();
        Field f2 = new Field();
        Inventory inv = new Inventory();
        Inventory inv2 = new Inventory();
        v1.setInventory(inv);
        v2.setInventory(inv2);
        v1.setField(f1);
        storage.put("v",v1);
        storage.put("v2",v2);
        storage.put("f",f1);
        storage.put("f2",f2);
        storage.put("inv",inv);
        storage.put("inv2",inv);
        f1.addVirologist(v1);
        System.out.println("--- Setup Test Environment for VirologistStealsMaterial DONE---");
        stunQuestion(v1);
        Scanner sc = new Scanner(System.in);
        if(!v1.isStunned()) {
            Logger.log(null, "Mennyi zsákot visel a rabló virológus? 0..3", QUESTION);
            int bags = sc.nextInt();
            for (int i = 0; i < bags; i++) {
                Bag bag = new Bag();
                storage.put("bag" + i, bag);
                bag.add(v1.getInventory());
                bag.activate(v1);
            }
            Logger.log(null, "Mennyi hely van a virológus inventoryába? 0.." + v1.getInventory().getSize(), QUESTION);
            int vinvcapacity = sc.nextInt();
            if (vinvcapacity < 0 || vinvcapacity > v1.getInventory().getSize()) {
                System.out.println("Hibás bemenet");
                storage.clear();
                return;
            }

            for (int i = 0; i < v1.getInventory().getSize() - vinvcapacity; i++) {
                Nucleotid placeholder = new Nucleotid();
                v1.getInventory().addMaterial(placeholder);
            }
            Logger.log(null, "Ugyanazon a mezőn van, akitől lopni akarsz? (I/N)", QUESTION);
            sc.nextLine();
            String samefield = sc.nextLine();
            if (samefield.equalsIgnoreCase("I")) {
                f1.addVirologist(v2);
                v2.setField(f1);
            } else if (samefield.equalsIgnoreCase("N")) {
                f2.addVirologist(v2);
                v2.setField(f2);
            } else {
                System.out.println("Hibás bemenet!");
                storage.clear();
                return;
            }
            Logger.log(null, "Mennyi zsákot visel a kirabolni kívánt virológusnál?0..3", QUESTION);
            int bagnum = sc.nextInt();
            for (int i = 0; i < bagnum; i++) {
                Bag bag = new Bag();
                storage.put("bag" + i, bag);
                bag.add(v2.getInventory());
                bag.activate(v2);
            }
            Logger.log(null,"Mennyi köpeny van a virológusnál?0.."+(v2.getInventory().getSize()-v2.getInventory().getUsedSize()),QUESTION);
            int capenum = sc.nextInt();
            for (int i = 0; i < capenum; i++) {
                Cape cape = new Cape();
                storage.put("cape"+i,cape);
                cape.add(v2.getInventory());
            }
            Logger.log(null,"Mennyi kesztyű van a virológusnál?0.."+(v2.getInventory().getSize()-v2.getInventory().getUsedSize()),QUESTION);
            int glovesnum = sc.nextInt();
            for (int i = 0; i < glovesnum; i++) {
                Gloves gloves = new Gloves();
                storage.put("gloves"+i,gloves);
                gloves.add(v2.getInventory());
            }
            Logger.log(null,"Mennyi zsák van a virológusnál?0.."+(v2.getInventory().getSize()-v2.getInventory().getUsedSize()),QUESTION);
            int bagss = sc.nextInt();
            for (int i = 0; i < capenum; i++) {
                Bag bag = new Bag();
                storage.put("bag"+i,bag);
                bag.add(v2.getInventory());
            }
            v1.steal(v2);
        }
    }

    /**
     * Virologist switches suite init.
     * A virológus lecseréli a kiválasztott felszerelést egy másik kiválasztottra.
     * Létre kell hozni 1 bag-et, 1 cape-t, 1 gloves-t, 1 inventory-t és 1 virológust.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológuson meghívjuk a switchSuite függvényt.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistSwitchesSuite(){
        System.out.println("--- Setting up Test Environment for Virologist switches suite ---");
        Virologist v = new Virologist();
        storage.put("v", v);
        Inventory inv = new Inventory();
        storage.put("inv", inv);
        stunQuestion(v);
        Bag b = new Bag();
        storage.put("b", b);
        Cape c = new Cape();
        storage.put("c", c);
        Gloves g = new Gloves();
        storage.put("g", g);
        List<Suite> suites = new ArrayList<>();
        suites.add(b);
        suites.add(c);
        suites.add(g);
        StringBuilder availableSuites = new StringBuilder("[");
        int i = 1;
        for(Suite s : suites){
            availableSuites.append(String.format("%d=%s, ", i++, s.getClass().getSimpleName()));
        }
        availableSuites.replace(availableSuites.length()-2, availableSuites.length(), "] ");
        Logger.log(null,"Melyik felszerelést szeretnéd lecserélni? "+availableSuites, QUESTION);
        Scanner sc = new Scanner(System.in);
        int fromSuite = sc.nextInt();
        if(fromSuite<1 || fromSuite>3){
            System.out.println("Hibás bemenet!");
            storage.clear();
            return;
        }
        Suite from = suites.remove(fromSuite-1);
        i = 1;

        availableSuites = new StringBuilder("[");
        for(Suite s : suites){
            availableSuites.append(String.format("%d=%s, ", i++, s.getClass().getSimpleName()));
        }
        availableSuites.replace(availableSuites.length()-2, availableSuites.length(), "] ");

        Logger.log(null,"Melyik felszerelésre szeretnéd lecserélni? "+availableSuites, QUESTION);
        int toSuite = sc.nextInt();
        if(toSuite<1 || toSuite>2){
            System.out.println("Hibás bemenet!");
            storage.clear();
            return;
        }
        Suite to = suites.remove(toSuite-1);
        from.add(v.getInventory());
        to.add(v.getInventory());
        System.out.println("--- Setup Test Environment for Virologist switches suite DONE ---");
        v.switchSuite(from, to);
        storage.clear();
    }

    /**
     * Shelter refreshes init.
     * Az óvóhely újra létrehoz felszerelést, ha szükséges.
     * Létre kell hozni 1 óvóhelyet és opcionálisan 1 rajta lévő felszerelést.
     */
    public static void shelterRefresh(){
        System.out.println("--- Setting up Test Environment for Shelter refresh ---");
        Shelter s = new Shelter();
        storage.put("s", s);
        Logger.log(null, "Van lent a mezőn tárgy? (I/N):", QUESTION);
        Scanner sc = new Scanner(System.in);
        String suiteOnShelter = sc.nextLine();
        if(suiteOnShelter.equalsIgnoreCase("I")){
            Bag b = new Bag();
            storage.put("suite", b);
            s.setSuite(b);
        }
        System.out.println("--- Setup Test Environment for Shelter refresh DONE ---");
        s.refresh();
        storage.clear();
    }

    /**
     * Warehouse refreshes init.
     * A raktár újra létrehoz anyagokat, ha szükséges.
     * Létre kell hozni 1 raktárat és opcionálisan pár rajta lévő anyagot.
     */
    public static void warehouseRefresh(){
        System.out.println("--- Setting up Test Environment for Warehouse refresh ---");
        Warehouse w = new Warehouse();
        storage.put("w", w);
        Logger.log(null, "Van lent a mezőn anyag? (I/N):", QUESTION);
        Scanner sc = new Scanner(System.in);
        String materialOnWarehouse = sc.nextLine();
        if(materialOnWarehouse.equalsIgnoreCase("I")){
            Nucleotid n = new Nucleotid();
            storage.put("n", n);
            w.addMaterials(n);
        }
        System.out.println("--- Setup Test Environment for Warehouse refresh DONE ---");
        w.refresh();
        storage.clear();
    }

    public static void playerStartsGame(){
        System.out.println("--- Setting up Test Environment for Player starts game ---");
        Game g = Game.getInstance();
        storage.put("g", g);
        RoundManager rm = RoundManager.getInstance();
        storage.put("rm", rm);
        Scanner sc = new Scanner(System.in);
        Logger.log(null, "Mennyi üres mező legyen? (1...*):", QUESTION);
        int fieldNum = sc.nextInt();
        if(fieldNum<1){
            System.out.println("Hibás bemenet!");
            storage.clear();
            return;
        }
        Logger.log(null, "Mennyi labor legyen? (1...*):", QUESTION);
        int laborNum = sc.nextInt();
        if(laborNum<1){
            System.out.println("Hibás bemenet!");
            storage.clear();
            return;
        }
        Logger.log(null, "Mennyi raktár legyen? (1...*):", QUESTION);
        int warehouseNum = sc.nextInt();
        if(warehouseNum<1){
            System.out.println("Hibás bemenet!");
            storage.clear();
            return;
        }
        Logger.log(null, "Mennyi óvóhely legyen? (1...*):", QUESTION);
        int shelterNum = sc.nextInt();
        if(shelterNum<1){
            System.out.println("Hibás bemenet!");
            storage.clear();
            return;
        }
        Logger.log(null, "Mennyi virológus legyen? (1...*):", QUESTION);
        int virologistNum = sc.nextInt();
        if(virologistNum<1){
            System.out.println("Hibás bemenet!");
            storage.clear();
            return;
        }
        System.out.println("--- Setup Test Environment for Player starts game DONE ---");
        g.startGame(fieldNum, laborNum, warehouseNum, shelterNum, virologistNum);
        storage.clear();
    }

    /**
     * Virologist put on bag init.
     * A virológus felveszi az inventory-jában található bag-et.
     * Létre kell hozni 1 bag-et, egy inventory-t és 1 virológust.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus meghívja a putOnSuite függvényt.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistPutsOnBag() {
        System.out.println("--- Setting up Test Environment for Virologist put on bag ---");
        Virologist v = new Virologist();
        Inventory inv = new Inventory();
        Bag b = new Bag();
        b.add(inv);
        storage.put("v",v);
        storage.put("b",b);

        stunQuestion(v);
        Scanner sc = new Scanner(System.in);
        System.out.print("Hány felszerelést visel már a virológus? (0...3):");
        int activeSuitesDecision = sc.nextInt();
        while(activeSuitesDecision > 3 || activeSuitesDecision < 0){
            System.out.println("Hibás bemenet...");
            System.out.print("Hány felszerelést visel már a virológus? (0...3):");
            activeSuitesDecision = sc.nextInt();
        }
        for(int i = 0; i < activeSuitesDecision; i++){
            Cape c = new Cape();
            c.setActive(true);
            inv.addSuite(c);
        }
        v.setInventory(inv);
        System.out.println("--- Setup Test Environment for Virologist put on bag DONE ---");
        v.putOnSuite(b);
        storage.clear();
    }

    /**
     * Virologist put on Cape init.
     * A virológus felveszi az inventory-jában található cape-et.
     * Létre kell hozni 1 cape-et, 1 inventory-t és 1 virológust.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus meghívja a putOnSuite függvényt.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistPutsOnCape() {
        System.out.println("--- Setting up Test Environment for Virologist put on cape ---");
        Virologist v = new Virologist();
        Inventory inv = new Inventory();
        Cape c = new Cape();
        c.add(inv);
        storage.put("v",v);
        storage.put("c",c);

        stunQuestion(v);
        Scanner sc = new Scanner(System.in);
        System.out.print("Hány felszerelést visel már a virológus? (0...3):");
        int activeSuitesDecision = sc.nextInt();
        while(activeSuitesDecision > 3 || activeSuitesDecision < 0){
            System.out.println("Hibás bemenet...");
            System.out.print("Hány felszerelést visel már a virológus? (0...3):");
            activeSuitesDecision = sc.nextInt();
        }
        for(int i = 0; i < activeSuitesDecision; i++){
            Cape cape = new Cape();
            cape.setActive(true);
            inv.addSuite(cape);
        }
        v.setInventory(inv);
        System.out.println("--- Setup Test Environment for Virologist put on cape DONE ---");
        v.putOnSuite(c);
        storage.clear();
    }

    /**
     * Virologist put on gloves init.
     * A virológus felveszi az inventory-jában található gloves-ot.
     * Létre kell hozni 1 gloves-ot, egy inventory-t és 1 virológust.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus meghívja a putOnSuite függvényt.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistPutsOnGloves() {
        System.out.println("--- Setting up Test Environment for Virologist put on gloves ---");
        Virologist v = new Virologist();
        Inventory inv = new Inventory();
        Gloves g = new Gloves();
        g.add(inv);
        storage.put("v",v);
        storage.put("g",g);

        stunQuestion(v);
        Scanner sc = new Scanner(System.in);
        System.out.print("Hány felszerelést visel már a virológus? (0...3):");
        int activeSuitesDecision = sc.nextInt();
        while(activeSuitesDecision > 3 || activeSuitesDecision < 0){
            System.out.println("Hibás bemenet...");
            System.out.print("Hány felszerelést visel már a virológus? (0...3):");
            activeSuitesDecision = sc.nextInt();
        }
        for(int i = 0; i < activeSuitesDecision; i++){
            Cape c = new Cape();
            c.setActive(true);
            inv.addSuite(c);
        }
        v.setInventory(inv);
        System.out.println("--- Setup Test Environment for Virologist put on gloves DONE ---");
        v.putOnSuite(g);
        storage.clear();
    }

    /**
     * A virológuson dance ágens hatása érvényesül.
     * Ennek következtében egy másik mezőre lép
     * Létre kell hozni: 3 fieldet, 1 virológust, 1 dance ágenst.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus meghívja a step függvényt.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistDances(){
        System.out.println("--- Setting up Test Environment for Virologist dances ---");
        Field f1 = new Field();
        Field f2 = new Field();
        Field f3 = new Field();
        Virologist v = new Virologist();
        SpecialModifier d = new Dance();

        v.setField(f1);
        f1.addVirologist(v);
        f1.addNeighbour(f2);
        f2.addNeighbour(f1);
        f1.addNeighbour(f3);
        f3.addNeighbour(f1);
        v.addModifier(d);

        storage.put("f1",f1);
        storage.put("f2",f2);
        storage.put("f3",f3);
        storage.put("v",v);
        storage.put("d",d);

        stunQuestion(v);
        System.out.println("--- Setup Test Environment for Virologist dances DONE ---");
        v.step();
        storage.clear();
    }

    /**
     * A virológuson forget ágens hatása érvényesül.
     * Ennek következtében egy másik mezőre lép
     * Létre kell hozni: 1 gene-t, 1 virológust, 1 dance ágenst.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus meghívja a step függvényt.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistForgets(){
        System.out.println("--- Setting up Test Environment for Virologist forgets ---");
        Virologist v = new Virologist();
        SpecialModifier f = new Forget();
        Gene g = new Gene();

        v.addLearnt(g);
        v.addModifier(f);
        storage.put("v",v);
        storage.put("f",f);
        storage.put("g",g);
        System.out.println("--- Setup Test Environment for Virologist forgets DONE ---");
        v.step();
        storage.clear();
    }

    /**
     * A virológuson a stun ágens hatása érvényesül.
     * Ennek következtében nem tud lépni, se cselekedni.
     * Létre kell hozni: 1 stun-t, 1 virológust, 1 roundManager-t.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus meghívja a step függvényt.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistIsBeingStunned(){
        System.out.println("--- Setting up Test Environment for Virologist is being stunned ---");
        Virologist v = new Virologist();
        SpecialModifier s = new Stun();
        RoundManager rm = RoundManager.getInstance();

        v.addModifier(s);
        rm.addVirologist(v);
        storage.put("v",v);
        storage.put("s",s);
        storage.put("rm",rm);

        System.out.println("--- Setup Test Environment for Virologist is being stunned DONE ---");
        v.step();
        storage.clear();
    }

    /**
     * A virológus befejezi a körét.
     * Attól függően, hogy az utolsó játékos lépett-e, a roundManager új kört kezd.
     * Létre kell hozni: 3 virológust, 1 roundManagert.
     * A létrehozott objektumokat beletesszük a HashMapbe.
     * A virológus meghívja az endround függvényt.
     * A létrehozott objektumokat kivesszük a HashMapből.
     */
    public static void virologistEndsRoundRoundManagerReacts(){
        System.out.println("--- Setting up Test Environment for Virologist ends round, round manager reacts ---");
        Virologist v = new Virologist();
        Virologist v1 = new Virologist();
        Virologist v2 = new Virologist();
        RoundManager rm = RoundManager.getInstance();

        rm.addVirologist(v);
        rm.addVirologist(v1);
        rm.addVirologist(v2);

        Scanner sc = new Scanner(System.in);
        Logger.log(null,"Ő volt az utolsó virológus a körben? [I/N]",QUESTION);
        String answer = sc.nextLine();

        if(answer.equalsIgnoreCase("I")){
            v1.setMoved(true);
            v2.setMoved(true);
            rm.setMovedCounter(rm.getVirologists().size()-1);
        }
        System.out.println("--- Setup Test Environment for Virologist ends round, round manager reacts DONE ---");

        v.endRound();
        storage.clear();
    }
}
