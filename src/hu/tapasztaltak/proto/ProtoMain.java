package hu.tapasztaltak.proto;

import hu.tapasztaltak.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

import static java.lang.Integer.parseInt;

public class ProtoMain {

    public static HashMap<String, Object> storage = new HashMap<>();

    public static HashMap<String, Integer> ids = new HashMap<>();

    public static Gene[] genes = {new Gene(), new Gene(), new Gene(), new Gene()};

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

    /**
     * Menü felépítése, tesztfuttatások hívása
     */
    public static void main(String[] args) {
        genes[0].setAgent(new Dance());
        genes[1].setAgent(new Forget());
        genes[2].setAgent(new Protect());
        genes[3].setAgent(new Stun());
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
                    default: throw new Exception();
                }
            } catch (Exception e) {
                System.err.println("Hiba történt! [hibás menüpont]");
                return;
            }
        } while(input != 7);
    }

    private static void runTests() {
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
            System.err.println("Hiba történt! [hibás menüpont]");
            return;
        }
    }

    private static String currentState(){
        StringBuilder state = new StringBuilder();
        return state.toString();
    }

    private static void showState() {
        System.out.println(currentState());
    }

    private static void exportState() {
        System.out.print("Mi legyen a fájl neve (kiterjesztés nélkül!), ahova exportálja az állapotot?: ");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(currentState());
            fw.close();
        } catch(Exception e){
            System.err.println("Hiba történt! [fájl mentés]");
        }
    }

    private static void importState() {
        System.out.print("Mi a fájl neve (kiterjesztés nélkül!), ahonnan importálja az állapotot?: ");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            if(!line.equals("fields:")) throw new Exception();
            int readProcess = 0;
            while((line = br.readLine()) != null && readProcess == 0){
                if(line.equals("virologists:")) {
                    readProcess = 1;
                    continue;
                }
                line = line.trim();
                Pattern p = Pattern.compile("(i*lab|fld|shlt|wrh)[0-9]+");
                Matcher m = p.matcher(line);
                if(!m.find()) throw new Exception();
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
                        if(s != null && (rc < 4 || rc > 8)) throw new Exception();
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
                        if((aminoNum + nclNum) == 0 && (rc < 4 || rc > 8)) throw new Exception();
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
                storage.put(infos[0], f);
            }
//            while((line = br.readLine()) != null && readProcess == 1){
//
//            }
        } catch(Exception e){
            System.err.println("Hiba történt! [fájl importálás]");
        }
    }

    private static String getIdForObject(Object o){
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

    private static void importCommands() {
    }

    private static void commandsByHand() {
        System.out.println("Kilépni az 'exit' paranccsal lehet (illetve hibás parancsra is kilép!)");
        boolean error = false;
        Scanner sc = new Scanner(System.in);
        String cmd;
        while(ProtoTestRunner.runCommand(cmd = sc.nextLine()));
    }
}
