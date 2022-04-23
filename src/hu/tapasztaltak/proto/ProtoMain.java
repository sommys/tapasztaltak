package hu.tapasztaltak.proto;

import hu.tapasztaltak.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static hu.tapasztaltak.proto.ProtoTestRunner.runCommand;
import static java.lang.Integer.parseInt;

public class ProtoMain {

    public static HashMap<String, Object> storage = new HashMap<>();

    public static HashMap<String, Integer> ids = new HashMap<>();

    public static Gene[] genes = {new Gene(), new Gene(), new Gene(), new Gene()};

    public static boolean randomness = true;

    public final static String MENU = "1. Parancsok kézi használata\n" +
            "2. Parancsok importálása fájlból\n" +
            "3. Állapot importálása fájlból\n" +
            "4. Állapot exportálása fájlba\n" +
            "5. Aktuális állapot megjelenítése\n" +
            "6. Tesztek futtatása\n" +
            "7. Kilépés";

    public final static String TESTS = "1. Virologist Moves\n" +
            "2. Virologist Uses Agent\n" +
            "3. Agent Effects\n" +
            "4. Virologist Steals\n" +
            "5. Virologist Scans Fields\n" +
            "6. Virologist Activates Suites\n" +
            "7. Virologist Switches Suites\n" +
            "8. Virologist Blocks Agent\n" +
            "9. Virologist Makes Agent\n" +
            "10. Axe use like a viking\n" +
            "11. EndRound\n" +
            "12. Agent older very old being old ye\n" +
            "13. Fields Refresh\n" +
            "14. Összes teszt futtatása";

    public final static int ALL_TEST_IDX = 14;
    private final static int VIROLOGIST_LINES = 7;

    /**
     * Menü felépítése, tesztfuttatások hívása
     */
    public static void main(String[] args) {
        geneInit();
        ProtoTestRunner.init(); //Tesztek létrehozása a megfelelő paraméterekkel
        int input;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(MENU);
            System.out.print("Válasszon menüpontot: ");
            try {
                input = sc.nextInt();
                switch(input){
                    case 1: commandsByHand();break;
                    case 2: importCommands();break;
                    case 3: importState();break;
                    case 4: exportState();break;
                    case 5: showState();break;
                    case 6: runTests();break;
                    case 7: goodbye();break;
                    default: throw new Exception("Hiba történt! [hibás menüpont]");
                }
            } catch (Exception e) {
                if(e.getMessage() != null) System.err.println(e.getMessage());
                return;
            }
        } while(input != 7);
    }

    private static void goodbye() {
        System.out.println("Köszi, hogy letesztelted! :) @ tapasztaltak");
    }

    private static void geneInit() {
        genes[0].setAgent(new Dance());
        genes[1].setAgent(new Forget());
        genes[2].setAgent(new Protect());
        genes[3].setAgent(new Stun());
        storage.put(getIdForObject(genes[0]), genes[0]);
        storage.put(getIdForObject(genes[1]), genes[1]);
        storage.put(getIdForObject(genes[2]), genes[2]);
        storage.put(getIdForObject(genes[3]), genes[3]);
    }

    private static void runTests() throws Exception {
        System.out.println(TESTS);
        System.out.print("Válasszon menüpontot: ");
        int input;
        Scanner sc = new Scanner(System.in);
        try {
            input = sc.nextInt();
            if(input < 0 || input > ALL_TEST_IDX) throw new Exception();
            if(input == ALL_TEST_IDX){
                for(int i = 0; i < ALL_TEST_IDX; i++){
                    ProtoTestRunner.runTest(input);
                }
            } else {
                ProtoTestRunner.runTest(input);
            }
        } catch (Exception e) {
            System.err.println("Hiba történt! [hibás teszt menüpont]");
            throw new Exception();
        }
    }

    public static int getGeneId(Gene g){
        switch(g.getAgent().getClass().getSimpleName()){
            case "Dance": return 0;
            case "Forget": return 1;
            case "Protect": return 2;
            case "Stun": return 3;
            default: return -1;
        }
    }

    public static int getSuiteId(Suite s){
        if(s == null) return 4;
        switch(s.getClass().getSimpleName()){
            case "Bag": return 0;
            case "Cape": return 1;
            case "Gloves": return 2;
            case "Axe": return 3;
            default: return -1;
        }
    }

    private static String currentState(){
        StringBuilder state = new StringBuilder();
        state.append("fields:\n");
        List<Object> fields = storage.values().stream().filter(it -> it instanceof Field).collect(Collectors.toList());
        for(Object f : fields){
            state.append("\t").append(f.toString()).append("\n");
        }
        state.append("virologists:\n");
        for(Object v : storage.values().stream().filter(it -> it instanceof Virologist).collect(Collectors.toList())){
            state.append("\t").append(v.toString()).append("\n");
        }
        state.append("connections:\n");
        for(Object f : fields){
            Field actField = (Field)f;
            String n = getIdForObject(actField) + ": " + actField.getNeighbours().stream().map(ProtoMain::getIdForObject).collect(Collectors.joining(" "));
            state.append("\t").append(n).append("\n");
        }
        return state.toString();
    }

    private static void showState() {
        System.out.println(currentState());
    }

    private static void exportState() throws Exception {
        System.out.print("Mi legyen a fájl neve (kiterjesztés nélkül!), ahova exportálja az állapotot?: ");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(currentState());
            fw.close();
        } catch(Exception e){
            System.err.println("Hiba történt! [állapot exportálás]");
            throw new Exception();
        }
    }

    private static void importState() throws Exception {
        ProtoLogger.loggingSwitch = false;
        System.out.print("Mi a fájl neve (kiterjesztés nélkül!), ahonnan importálja az állapotot?: ");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName+".txt"));
            String line = br.readLine();
            if(!line.equals("fields:")) throw new Exception();
            while((line = br.readLine()) != null){
                if(line.equals("virologists:")) break;
                line = line.trim();
                Pattern p = Pattern.compile("(i*lab|fld|shlt|wrh)[0-9]+");
                if(!p.matcher(line).find()) throw new Exception();
                Field f;
                String[] infos = line.split(" ");
                if(line.startsWith("lab")){
                    if(infos.length != 2) throw new Exception();
                    f = new Labor();
                    ((Labor)f).setGene(genes[parseInt(infos[1])]);
                } else if(line.startsWith("ilab")){
                    if(infos.length != 2) throw new Exception();
                    f = new InfLabor();
                    ((InfLabor)f).setGene(genes[parseInt(infos[1])]);
                } else if(line.startsWith("shlt")){
                    if(infos.length < 2 || infos.length > 3) throw new Exception();
                    f = new Shelter();
                    Suite s = null;
                    switch(parseInt(infos[1])){
                        case 0:{ s = new Bag(); break;}
                        case 1:{ s = new Cape(); break;}
                        case 2:{ s = new Gloves(); break;}
                        case 3:{ s = new Axe(); break;}
                        case 4:break;
                        default: throw new Exception();
                    }
                    if(infos.length == 3){
                        int rc = parseInt(infos[3]);
                        if(s == null && rc == -1) throw new Exception();
                        if(s != null && (rc > 8)) throw new Exception();
                        f.setRefreshCounter(rc);
                    } else {
                        if(s == null) f.setRefreshCounter(4);
                        else f.setRefreshCounter(-1);
                    }
                    if(s != null){
                        storage.put(getIdForObject(s), s);
                    }
                    ((Shelter)f).setSuite(s);
                } else if(line.startsWith("wrh")){
                    if(infos.length < 3 || infos.length > 4) throw new Exception();
                    f = new Warehouse();
                    int aminoNum = parseInt(infos[1]);
                    int nclNum = parseInt(infos[2]);
                    if(aminoNum < 0 || nclNum < 0) throw new Exception();
                    for(int i = 0; i < nclNum; i++){
                        Nucleotid n = new Nucleotid();
                        ((Warehouse)f).addMaterials(n);
                        storage.put(getIdForObject(n), n);
                    }
                    for(int i = 0; i < aminoNum; i++){
                        Aminoacid a = new Aminoacid();
                        ((Warehouse)f).addMaterials(a);
                        storage.put(getIdForObject(a), a);
                    }
                    if(infos.length == 4){
                        int rc = parseInt(infos[3]);
                        if((aminoNum + nclNum) == 0 && (rc > 8)) throw new Exception();
                        if((aminoNum + nclNum) > 0 && rc != -1) throw new Exception();
                        f.setRefreshCounter(rc);
                    } else {
                        if((aminoNum + nclNum) == 0 ) f.setRefreshCounter(4);
                        if((aminoNum + nclNum) > 0) f.setRefreshCounter(-1);
                    }
                } else {
                    if(infos.length != 1) throw new Exception();
                    f = new Field();
                }
                String fId = getIdForObject(f);
                if(!fId.equals(infos[0])) throw new Exception();
                storage.put(infos[0], f);
            }
            int virLines = 0;
            Pattern virPattern = Pattern.compile("vir[0-9]+");
            while((line = br.readLine()) != null){
                if(line.equals("connections:")) break;
                String readVirId = line.trim();
                Virologist v = new Virologist();
                String virId = getIdForObject(v);
                if(!virId.equals(readVirId)) throw new Exception();
                storage.put(virId, v);
                for(int i = 0; i < VIROLOGIST_LINES; i++){
                    line = br.readLine();
                    if(line == null || virPattern.matcher(line).find()) throw new Exception();
                    line = line.trim();
                    if(line.equals("-")) continue;
                    switch(i){
                        case 0:{
                            Field f = (Field)storage.get(line);
                            if(f == null) throw new Exception();
                            v.setField(f);
                            break;
                        }
                        case 1:{
                            String[] suites = line.split(" ");
                            if(suites.length > 3) throw new Exception();
                            for(String sId : suites){
                                if(sId.startsWith("bag")){
                                    Bag b = new Bag();
                                    if(!sId.equals(getIdForObject(b))) throw new Exception();
                                    storage.put(sId, b);
                                    v.getInventory().addSuite(b);
                                    b.activate(v);
                                } else if(sId.startsWith("cape")){
                                    Cape c = new Cape();
                                    if(!sId.equals(getIdForObject(c))) throw new Exception();
                                    storage.put(sId, c);
                                    v.getInventory().addSuite(c);
                                    c.activate(v);
                                } else if(sId.startsWith("glv")){
                                    Gloves g = new Gloves();
                                    int useCount = getExtraInfo(sId);
                                    if(useCount < 0 || useCount > 3) throw new Exception();
                                    String gId = sId.substring(0, sId.indexOf("["));
                                    if(!gId.equals(getIdForObject(g))) throw new Exception();
                                    g.setUseCount(useCount);
                                    storage.put(gId, g);
                                    v.getInventory().addSuite(g);
                                    g.activate(v);
                                } else if(sId.startsWith("axe")){
                                    Axe a = new Axe();
                                    int used = getExtraInfo(sId);
                                    if(used < 0 || used > 1) throw new Exception();
                                    String aId = sId.substring(0, sId.indexOf("["));
                                    if(!aId.equals(getIdForObject(a))) throw new Exception();
                                    a.setUsed(used == 1);
                                    storage.put(aId, a);
                                    v.getInventory().addSuite(a);
                                    a.activate(v);
                                } else {
                                    throw new Exception();
                                }
                            }
                            break;
                        }
                        case 2:{
                            String[] suites = line.split(" ");
                            if(suites.length > v.getInventory().getSize() - v.getInventory().getUsedSize()) throw new Exception();
                            for(String sId : suites) {
                                if (sId.startsWith("bag")) {
                                    Bag b = new Bag();
                                    if (!sId.equals(getIdForObject(b))) throw new Exception();
                                    storage.put(sId, b);
                                    v.getInventory().addSuite(b);
                                } else if (sId.startsWith("cape")) {
                                    Cape c = new Cape();
                                    if (!sId.equals(getIdForObject(c))) throw new Exception();
                                    storage.put(sId, c);
                                    v.getInventory().addSuite(c);
                                } else if (sId.startsWith("glv")) {
                                    Gloves g = new Gloves();
                                    int useCount = getExtraInfo(sId);
                                    if (useCount < 0 || useCount > 3) throw new Exception();
                                    String gId = sId.substring(0, sId.indexOf("["));
                                    if (!gId.equals(getIdForObject(g))) throw new Exception();
                                    g.setUseCount(useCount);
                                    storage.put(gId, g);
                                    v.getInventory().addSuite(g);
                                } else if (sId.startsWith("axe")) {
                                    Axe a = new Axe();
                                    int used = getExtraInfo(sId);
                                    if (used < 0 || used > 1) throw new Exception();
                                    String aId = sId.substring(0, sId.indexOf("["));
                                    if (!aId.equals(getIdForObject(a))) throw new Exception();
                                    a.setUsed(used == 1);
                                    storage.put(aId, a);
                                    v.getInventory().addSuite(a);
                                } else {
                                    throw new Exception();
                                }
                            }
                            break;
                        }
                        case 3:{
                            String[] materials = line.split(" ");
                            if(materials.length > v.getInventory().getSize() - v.getInventory().getUsedSize()) throw new Exception();
                            for(String m : materials){
                                if(m.startsWith("amin")){
                                    Aminoacid a = new Aminoacid();
                                    String aId = getIdForObject(a);
                                    if(storage.containsKey(m)){
                                        //a bennelévőd kicseréljük a generáltra
                                        storage.put(aId, storage.get(m));
                                        //a mostanit kicseréljük a bentlévőre
                                        storage.put(m, a);
                                    }
                                } else if (m.startsWith("ncl")){
                                    Nucleotid n = new Nucleotid();
                                    String nId = getIdForObject(n);
                                    if(storage.containsKey(m)){
                                        //a bennelévőd kicseréljük a generáltra
                                        storage.put(nId, storage.get(m));
                                        //a mostanit kicseréljük a bentlévőre
                                        storage.put(m, n);
                                    }
                                } else {
                                    throw new Exception();
                                }
                            }
                            break;
                        }
                        case 4:{
                            String[] useableAgents = line.split(" ");
                            for(String ua : useableAgents){
                                Agent a;
                                if(ua.startsWith("dnc")){
                                    a = new Dance();
                                } else if(ua.startsWith("stun")){
                                    a = new Stun();
                                } else if(ua.startsWith("prot")){
                                    a = new Protect();
                                } else if(ua.startsWith("frgt")){
                                    a = new Forget();
                                } else {
                                    throw new Exception();
                                }
                                int timeLeft = getExtraInfo(ua);
                                if (timeLeft < 0) throw new Exception();
                                a.setTimeLeft(timeLeft);
                                storage.put(getIdForObject(a), a);
                                v.getInventory().addAgent(a);
                            }
                            break;
                        }
                        case 5:{
                            String[] effectingAgents = line.split(" ");
                            for(String ua : effectingAgents){
                                Agent a;
                                if(ua.startsWith("bear")){
                                    a = new Bear();
                                } else if(ua.startsWith("dnc")){
                                    a = new Dance();
                                } else if(ua.startsWith("stun")){
                                    a = new Stun();
                                } else if(ua.startsWith("prot")){
                                    a = new Protect();
                                } else if(ua.startsWith("frgt")){
                                    a = new Forget();
                                } else {
                                    throw new Exception();
                                }
                                int timeLeft = getExtraInfo(ua);
                                if ("Bear".equals(a.getClass().getSimpleName())) {
                                    if (timeLeft != -1) throw new Exception();
                                } else {
                                    if (timeLeft < 0) throw new Exception();
                                }
                                a.setTimeLeft(timeLeft);
                                storage.put(getIdForObject(a), a);
                                a.spread(v);
                                ProtoLogger.loggingSwitch = false;
                            }
                            break;
                        }
                        case 6:{
                            String[] genes = line.split(" ");
                            for(String g : genes){
                                String genId = g.substring(0, g.indexOf("["));
                                int agentId = getExtraInfo(g);
                                Gene gene = (Gene)storage.get(genId);
                                switch(agentId){
                                    case 0: if(!(gene.getAgent().getClass().getSimpleName().equals("Dance"))) throw new Exception();break;
                                    case 1: if(!(gene.getAgent().getClass().getSimpleName().equals("Forget"))) throw new Exception();break;
                                    case 2: if(!(gene.getAgent().getClass().getSimpleName().equals("Protect"))) throw new Exception();break;
                                    case 3: if(!(gene.getAgent().getClass().getSimpleName().equals("Stun"))) throw new Exception();break;
                                    default: throw new Exception();
                                }
                            }
                            break;
                        }
                        default:{
                            throw new Exception();
                        }
                    }
                }
            }
            Pattern fldConnectionsPattern = Pattern.compile("(i*lab|fld|shlt|wrh)[0-9]+:");
            while((line = br.readLine()) != null){
                line = line.trim();
                if(!fldConnectionsPattern.matcher(line).find()) throw new Exception();
                String fieldId = line.substring(0, line.indexOf(":"));
                Field f = (Field) storage.get(fieldId);
                if(f == null) throw new Exception();
                line = line.substring(line.indexOf(" ")).trim();
                String connections[] = line.split(" ");
                for(String n : connections){
                    if(!storage.containsKey(n)) throw new Exception();
                    Field neighbour = (Field) storage.get(n);
                    f.addNeighbour(neighbour);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            System.err.println("Hiba történt! [állapot importálás]");
            throw new Exception();
        } finally {
            ProtoLogger.loggingSwitch = true;
        }
    }

    private static int getExtraInfo(String line) throws Exception{
        int lastIdxOfId = line.indexOf("[");
        if (lastIdxOfId == -1) throw new Exception();
        int lastIdxOfTimeLeft = line.indexOf("]");
        if (lastIdxOfTimeLeft == -1) throw new Exception();
        int extraInfo = parseInt(line.substring(lastIdxOfId + 1, lastIdxOfTimeLeft));
        return extraInfo;
    }

    public static String getIdForObject(Object o){
        if(storage.containsValue(o)){
            for(Map.Entry<String, Object> e : storage.entrySet()){
                if(e.getValue().equals(o)) return e.getKey();
            }
        }
        String className = o.getClass().getSimpleName();
        if(!ids.containsKey(className)){
            ids.put(className, 1);
        } else {
            ids.put(className, ids.get(className)+1);
        }
        String identifier = "";
        switch(className){
            case "Aminoacid": identifier = "amin";break;
            case "Axe": identifier = "axe";break;
            case "Bag": identifier = "bag";break;
            case "Bear": identifier = "bear";break;
            case "Cape": identifier = "cape";break;
            case "Dance": identifier = "dnc";break;
            case "Field": identifier = "fld";break;
            case "Forget": identifier = "frgt";break;
            case "InfLabor": identifier = "ilab";break;
            case "Gene": identifier = "gene";break;
            case "Gloves": identifier = "glv";break;
            case "Labor": identifier = "lab";break;
            case "Nucleotid": identifier = "ncl";break;
            case "Protect": identifier = "prot";break;
            case "Shelter": identifier = "shlt";break;
            case "Stun": identifier = "stun";break;
            case "Virologist": identifier = "vir";break;
            case "Warehouse": identifier = "wrh";break;
        }
        return identifier+ids.get(className);
    }

    private static void importCommands() throws Exception {
        System.out.print("Mi a fájl neve (kiterjesztés nélkül!), ahonnan importálja az parancsokat?: ");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName+".txt"));
            String line;
            while((line = br.readLine()) != null){
                if(!runCommand(line.trim())) throw new Exception();
            }
        } catch(Exception e){
            e.printStackTrace();
            System.err.println("Hiba történt! [parancsok importálása]");
            throw new Exception();
        }
    }

    private static void commandsByHand() throws Exception {
        System.out.println("Kilépni az 'Exit' paranccsal lehet (illetve hibás parancsra is kilép!)");
        boolean error = false;
        Scanner sc = new Scanner(System.in);
        String cmd;
        while(true){
            cmd = sc.nextLine().trim();
            try{
                if(!runCommand(cmd)) return;
            } catch(Exception e){
                throw new Exception();
            }
        }
    }
}
