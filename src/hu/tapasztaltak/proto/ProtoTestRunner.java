package hu.tapasztaltak.proto;

import hu.tapasztaltak.model.*;

import java.util.ArrayList;
import java.util.List;

import static hu.tapasztaltak.proto.ProtoLogger.logMessage;
import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;
import static hu.tapasztaltak.proto.ProtoMain.storage;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

public class ProtoTestRunner {
    private static class Test{
        String name;
        String inputFile;
        String outputFile;
        String expectedOutputFile;
        boolean result = false;

        public Test(String name, String inputFile, String outputFile, String expectedOutputFile) {
            this.name = name;
            this.inputFile = inputFile;
            this.outputFile = outputFile;
            this.expectedOutputFile = expectedOutputFile;
        }

        public void run(){
            //todo
            // input megnyitasa
            // soronkent beolvassuk a parancsokat, meghivjuk a runCommand-ot
            // kiirjuk az outputFile-ba az eredmenyt: ProtoLogger.actOutput.toString, majd ProtoLogger.actOutput = new StringBuilder();
            // ellenőrizzük az eredményt okosba
        }
    }

    private static final List<Test> testList = new ArrayList<>();

    public static void init(){
        for(int i = 1; i < ProtoMain.ALL_TEST_IDX; i++){
            testList.add(new Test("t"+i, "t"+i+".txt", "t"+i+"_o.txt", "t"+i+"_eo.txt"));
        }
    }

    public static void runTest(int idx){
        Test toRun = testList.get(idx);
        toRun.run();
        System.out.println(toRun.name + " result: " + (toRun.result ? "SUCCESS" : "FAIL"));
    }

    public static boolean runCommand(String command) throws Exception {
        if(command.equalsIgnoreCase("Exit")) return false;
        String cmd = command.contains(" ") ? command.substring(0, command.indexOf(" ")) : command;
        String[] args = command.contains(" ") ? command.substring(cmd.length()).split(" ") : new String[]{};
        switch(cmd){
            case "Reset": resetState(); break;
            case "SetRandomness": {
                if(args.length != 1) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    ProtoMain.randomness = parseInt(args[0]) == 1;
                    System.out.println("Randomness turned "+(ProtoMain.randomness ? "ON" : "OFF"));
                } catch(NumberFormatException e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "CreateVirologist":{
                if(args.length != 1) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                Field f = (Field)storage.get(args[0]);
                Virologist v = new Virologist();
                v.setField(f);
                storage.put(getIdForObject(v), v);
                logMessage(String.format("%s created on %s", getIdForObject(v), getIdForObject(f)));
                break;
            }
            case "CreateField":{
                if(args.length != 0) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                Field f = new Field();
                storage.put(getIdForObject(f), f);
                logMessage(String.format("%s created", getIdForObject(f)));
                break;
            }
            case "CreateLabor":{
                if(args.length != 2) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Labor l;
                    if (parseBoolean(args[1])) {
                        l = new InfLabor();
                    } else {
                        l = new Labor();
                    }
                    int geneType = parseInt(args[0]);
                    if(geneType < 0 || geneType > 3) throw new Exception();
                    l.setGene(ProtoMain.genes[geneType]);
                    storage.put(getIdForObject(l), l);
                    logMessage(String.format("%s created: genectic code: %s", getIdForObject(l), l.getGene().getAgent().getClass().getSimpleName()));
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
            }
            case "CreateShelter":{
                if(!(args.length == 1 || args.length == 2)) throw new Exception("Hiba történt [hibás paraméter]");
                Suite s;
                try{
                    int suiteType = parseInt(args[0]);
                    switch(suiteType){
                        case 0: s = new Bag();break;
                        case 1: s = new Cape();break;
                        case 2: s = new Gloves();break;
                        case 3: s = new Axe();break;
                        case 4: s = null; break;
                        default: throw new Exception();
                    }
                    Shelter sh = new Shelter();
                    sh.setSuite(s);
                    int rc = s == null ? -1 : 4;
                    if(args.length == 2){
                        rc = parseInt(args[1]);
                        if(rc < -1 || rc > 8) throw new Exception();
                        if(s != null && rc != -1) throw new Exception("Can't set refresh counter for a field with items!");
                        if(s == null && rc == -1) throw new Exception("Can't set refresh counter to -1 for a field without items!");
                    }
                    sh.setRefreshCounter(rc);
                    storage.put(getIdForObject(sh), sh);
                    if(s != null) storage.put(getIdForObject(s), s);
                    logMessage(String.format("%s created with %s", getIdForObject(sh), s == null ? "Nothing" : s.getClass().getSimpleName()));
                } catch(Exception e){
                    if(e.getMessage() != null && e.getMessage().startsWith("Can't set")) logMessage(e.getMessage());
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "CreateWarehouse": {
                if(!(args.length == 2 || args.length == 3)) throw new Exception("Hiba történt [hibás paraméter]");
                Warehouse w = new Warehouse();
                try{
                    int amino = parseInt(args[0]);
                    int ncl = parseInt(args[1]);
                    for(int i = 0; i < amino; i++){
                        Aminoacid a = new Aminoacid();
                        storage.put(getIdForObject(a), a);
                        w.addMaterials(a);
                    }
                    for(int i = 0; i < ncl; i++){
                        Nucleotid n = new Nucleotid();
                        storage.put(getIdForObject(n), n);
                        w.addMaterials(n);
                    }
                    int rc = w.getMaterials().isEmpty() ? 4 : -1;
                    if(args.length == 3){
                        rc = parseInt(args[2]);
                        if(rc < -1 || rc > 8) throw new Exception();
                        if(!w.getMaterials().isEmpty() && rc != -1) throw new Exception("Can't set refresh counter for a field with items!");
                        if(w.getMaterials().isEmpty() && rc == -1) throw new Exception("Can't set refresh counter to -1 for a field without items!");
                    }
                    w.setRefreshCounter(rc);
                    storage.put(getIdForObject(w), w);
                    logMessage(String.format("%s created with %d aminoacid and %d nucleotid", getIdForObject(w), amino, ncl));
                } catch(Exception e){
                    if(e.getMessage() != null && e.getMessage().startsWith("Can't set")) logMessage(e.getMessage());
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "SetNeighbours":{
                if(args.length != 2) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Field f = (Field) storage.get(args[0]);
                    if(f == null) throw new Exception();
                    String[] nFields = args[1].split(",");
                    for(String nId : nFields){
                        Field n = (Field) storage.get(nId);
                        if(n == null) throw new Exception();
                        f.addNeighbour(n);
                    }
                    logMessage(String.format("%s added to %s neighbours", String.join(", ", nFields), getIdForObject(f)));
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "AddMaterial":{
                if(args.length != 3) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try{
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    int matType = parseInt(args[1]);
                    int amount = parseInt(args[2]);
                    if(v.getInventory().getSize()-v.getInventory().getUsedSize() < amount){
                        logMessage(String.format("Not enough space in %s inventory", getIdForObject(v)));
                    }
                    switch(matType){
                        //TODO
                        case 0:{
                            break;
                        }
                        case 1:{
                            break;
                        }
                        default: throw new Exception();
                    }
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            default: throw new Exception("Hiba történt [hibás parancs]");
        }
        return true;
    }

    private static void resetState(){
        storage.clear();
        ProtoMain.ids.clear();
    }

}
