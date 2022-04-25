package hu.tapasztaltak.proto;

import hu.tapasztaltak.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hu.tapasztaltak.proto.ProtoLogger.*;
import static hu.tapasztaltak.proto.ProtoMain.*;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

public class ProtoTestRunner {
    public static class Test{
        public String name;
        String inputFile;
        String outputFile;
        String expectedOutputFile;
        public boolean result = false;

        public Test(String name, String inputFile, String outputFile, String expectedOutputFile) {
            this.name = name;
            this.inputFile = "testResources\\input\\"+inputFile;
            this.outputFile = "testResources\\output\\"+outputFile;
            this.expectedOutputFile = "testResources\\expected\\"+expectedOutputFile;
        }

        public void run(){
            // todo
            //  input megnyitasa
            //  soronkent beolvassuk a parancsokat, meghivjuk a runCommand-ot
            //  kiirjuk az outputFile-ba az eredmenyt: ProtoLogger.actOutput.toString, majd ProtoLogger.actOutput = new StringBuilder();
            //  ellenőrizzük az eredményt okosba
            try {
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                String line;
                while((line = br.readLine()) != null){
                    if(!runCommand(line.trim())){
                        System.out.println(line);
                        throw new Exception();
                    }
                }
                FileWriter fw = new FileWriter(outputFile);
                fw.write(actOutput.toString().trim());
                fw.close();
                br = new BufferedReader(new FileReader(expectedOutputFile));
                String[] actLines = actOutput.toString().trim().split("\n");
                int i = 0;
                while((line = br.readLine()) != null){
                    if(i < actLines.length){
                        if(line.contains("*")){
                            if(line.contains("protected") && actLines[i].contains("protected")){
                                i++;
                                continue;
                            } else if(line.contains("didn't protect") && actLines[i].contains("didn't protect")){
                                i++;
                            } else if(line.contains("RANDOM FIELD FROM")){
                                String fld = line.substring(line.indexOf("FROM ")+5, line.indexOf(" NEIGHBOURS"));
                                Field f = (Field)storage.get(fld.toLowerCase());
                                if(f != null){
                                    String actFld = actLines[i].substring(actLines[i].indexOf("to")+3);
                                    Field n = (Field)storage.get(actFld);
                                    if(n == null || !f.getNeighbours().contains(n)){
                                        result = false;
                                        actOutput = new StringBuilder();
                                        return;
                                    }
                                }
                            } else if(line.contains("something")){
                                if(!(actLines[i].contains("ncl")||actLines[i].contains("amin")||actLines[i].contains("glv")||actLines[i].contains("cape")||actLines[i].contains("axe")||actLines[i].contains("bag"))){
                                    result = false;
                                    actOutput = new StringBuilder();
                                    return;
                                }
                            }
                            i++;
                        } else if(!line.equals(actLines[i++])){
                            result = false;
                            actOutput = new StringBuilder();
                            return;
                        }
                    } else {
                        result = false;
                        actOutput = new StringBuilder();
                        return;
                    }
                }
                result = true;
                actOutput = new StringBuilder();
            } catch(Exception e){
                e.printStackTrace();
                System.err.println("Hiba történt! ["+name+" teszt futtatása]");
                actOutput = new StringBuilder();
            }
        }
    }

    public static final List<Test> testList = new ArrayList<>();

    public static void init(){
        for(int i = 1; i < ProtoMain.ALL_TEST_IDX; i++){
            testList.add(new Test("t"+i, "t"+i+".txt", "t"+i+"_o.txt", "t"+i+"_eo.txt"));
        }
    }

    public static void runTest(int idx){
        Test toRun = testList.get(idx-1);
        toRun.run();
        resetState();
        System.out.println(toRun.name + " result: " + (toRun.result ? "SUCCESS" : "FAIL"));
    }

    public static boolean runCommand(String command) throws Exception {
        if(command.equalsIgnoreCase("Exit")) return false;
        String cmd = command.contains(" ") ? command.substring(0, command.indexOf(" ")) : command;
        String[] args = command.contains(" ") ? command.substring(cmd.length()+1).split(" ") : new String[]{};
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
                f.addVirologist(v);
                RoundManager.getInstance().addVirologist(v);
                storage.put(getIdForObject(v), v);
                logMessage(String.format("%s created on %s", getIdForObject(v), getIdForObject(f)));
                break;
            }
            case "CreateField":{
                if(args.length != 0) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                Field f = new Field();
                storage.put(getIdForObject(f), f);
                RoundManager.getInstance().addSteppable(f);
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
                    l.setGene(genes[geneType]);
                    storage.put(getIdForObject(l), l);
                    RoundManager.getInstance().addSteppable(l);
                    logMessage(String.format("%s created: genetic code: %s", getIdForObject(l), l.getGene().getAgent().getClass().getSimpleName()));
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "CreateShelter":{
                if(!(args.length == 1 || args.length == 2)) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
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
                    RoundManager.getInstance().addSteppable(sh);
                    if(s != null) storage.put(getIdForObject(s), s);
                    logMessage(String.format("%s created with %s", getIdForObject(sh), s == null ? "Nothing" : s.getClass().getSimpleName()));
                } catch(Exception e){
                    if(e.getMessage() != null && e.getMessage().startsWith("Can't set")) logMessage(e.getMessage());
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "CreateWarehouse": {
                if(!(args.length == 2 || args.length == 3)) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
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
                        if(!w.getMaterials().isEmpty() && rc != -1){
                            logMessage("Can't set refresh counter for a field with items!");
                            break;
                        }
                        if(w.getMaterials().isEmpty() && rc == -1){
                            logMessage("Can't set refresh counter to -1 for a field without items!");
                            break;
                        }
                    }
                    w.setRefreshCounter(rc);
                    storage.put(getIdForObject(w), w);
                    RoundManager.getInstance().addSteppable(w);
                    logMessage(String.format("%s created with %d aminoacid and %d nucleotid", getIdForObject(w), amino, ncl));
                } catch(Exception e){
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
                        break;
                    }
                    if(!(matType == 0 || matType == 1)) throw new Exception();
                    for(int i = 0; i < amount; i++){
                        IMaterial m = matType == 0 ? new Aminoacid() : new Nucleotid();
                        storage.put(getIdForObject(m), m);
                        v.getInventory().addMaterial(m);
                    }
                    logMessage(String.format("%d %s added for %s inventory %d spaces left", amount, matType == 0 ? "aminoacid" : "nucleotid", getIdForObject(v), v.getInventory().getSize()-v.getInventory().getUsedSize()));
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "AddSuite":{
                if(!(args.length == 3 || args.length == 4)) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    int suiteType = parseInt(args[1]);
                    if(suiteType < 0 || suiteType > 3) throw new Exception();
                    int amount = parseInt(args[2]);
                    if(v.getInventory().getSize()-v.getInventory().getUsedSize() < amount){
                        logMessage(String.format("Not enough space in %s inventory", getIdForObject(v)));
                        break;
                    }
                    for(int i = 0; i < amount; i++){
                        Suite s;
                        String plusInfo = " ";
                        switch(suiteType){
                            case 0:{
                                s = new Bag();
                                if(args.length == 4){
                                    int bagSize = parseInt(args[3]);
                                    if(bagSize < 0) throw new Exception();
                                    ((Bag)s).setSize(bagSize);
                                }
                                plusInfo = " with "+((Bag)s).getSize()+" size ";
                                break;
                            }
                            case 1:{
                                s = new Cape();
                                if(args.length == 4) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                                break;
                            }
                            case 2:{
                                s = new Gloves();
                                if(args.length == 4){
                                    int useCount = parseInt(args[3]);
                                    if(useCount < 1 || useCount > 3) throw new Exception();
                                    ((Gloves)s).setUseCount(useCount);
                                }
                                plusInfo = " with "+((Gloves)s).getUseCount()+" uses left ";
                                break;
                            }
                            case 3:{
                                s = new Axe();
                                if(args.length == 4){
                                    int used = parseInt(args[3]);
                                    if(used < 0 || used > 1) throw new Exception();
                                    ((Axe)s).setUsed(used == 1);
                                }
                                plusInfo = " ["+(((Axe)s).isUsed()?"":"not ")+"used] ";
                                break;
                            }
                            default: throw new Exception();
                        }
                        storage.put(getIdForObject(s), s);
                        s.add(v.getInventory());
                        logMessage(String.format("%s%sadded for %s inventory %d spaces left", getIdForObject(s), plusInfo, getIdForObject(v), v.getInventory().getSize() - v.getInventory().getUsedSize()));
                    }
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "LearnCode":{
                if(args.length != 2) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    int geneId = parseInt(args[1]);
                    if(geneId < 0 || geneId > 3) throw new Exception();
                    v.learn(genes[geneId]);
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "AddUAgent":{
                if(args.length != 3) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    int agentId = parseInt(args[1]);
                    int timeLeft = parseInt(args[2]);
                    if(timeLeft < 1 || timeLeft > 3) throw new Exception();
                    Agent a;
                    switch(agentId){
                        case 0: a = new Dance();break;
                        case 1: a = new Forget();break;
                        case 2: a = new Protect();break;
                        case 3: a = new Stun();break;
                        default: throw new Exception();
                    }
                    a.setTimeLeft(timeLeft);
                    storage.put(getIdForObject(a), a);
                    RoundManager.getInstance().addSteppable(a);
                    v.getInventory().addAgent(a);
                    logMessage(String.format("%s [usable for %d rounds] added for %s", getIdForObject(a), a.getTimeLeft(), getIdForObject(v)));
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "AddEAgent":{
                if(args.length != 3) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    int agentId = parseInt(args[1]);
                    int timeLeft = parseInt(args[2]);
                    if(timeLeft == 0 || timeLeft < -1) throw new Exception();
                    Agent a;
                    switch(agentId){
                        case 0: a = new Dance();break;
                        case 1: a = new Forget();break;
                        case 2: a = new Protect();break;
                        case 3: a = new Stun();break;
                        case 4: {
                            a = new Bear();
                            loggingSwitch = false;
                            break;
                        }
                        default: throw new Exception();
                    }
                    a.setTimeLeft(timeLeft);
                    storage.put(getIdForObject(a), a);
                    RoundManager.getInstance().addSteppable(a);
                    a.spread(v);
                    loggingSwitch=true;
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "ActivateSuite":{
                if(args.length != 2) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    Suite s = (Suite) storage.get(args[1]);
                    if(s == null) throw new Exception();
                    if(v.getInventory().getSuites().stream().filter(Suite::isActive).count() == 3){
                        logMessage(String.format("%s is already wearing 3 suites", getIdForObject(v)));
                        break;
                    }
                    if(!v.getInventory().getSuites().contains(s)){
                        logMessage(String.format("%s doesn't own %s", getIdForObject(v), getIdForObject(s)));
                        break;
                    }
                    s.activate(v);
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "DeactivateSuite":{
                if(args.length != 2) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    Suite s = (Suite) storage.get(args[1]);
                    if(s == null) throw new Exception();
                    if(!v.getInventory().getSuites().contains(s)){
                        logMessage(String.format("%s doesn't own %s", getIdForObject(v), getIdForObject(s)));
                        break;
                    }
                    if(!s.isActive()){
                        logMessage(String.format("%s is not wearing %s", getIdForObject(v), getIdForObject(s)));
                        break;
                    }
                    s.deactivate(v);
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "Move":{
                if(args.length != 2) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    Field f = (Field) storage.get(args[1]);
                    if(f == null) throw new Exception();
                    v.move(f);
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "Scan":{
                if(args.length != 1) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    v.scanning();
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "MakeAgent":{
                if(args.length != 2) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    int geneId = parseInt(args[1]);
                    if(geneId < 0 || geneId > 3) throw new Exception();
                    v.makeAgent(genes[geneId]);
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "UseAgent":{
                if(args.length != 3) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    Agent a = (Agent) storage.get(args[1]);
                    if(a == null || !v.getInventory().getAgents().contains(a)){
                        logMessage(String.format("%s doesn't have %s", getIdForObject(v), args[1]));
                        break;
                    }
                    Virologist victim = (Virologist) storage.get(args[2]);
                    if(victim == null) throw new Exception();
                    v.useAgent(a, victim);
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "Steal":{
                if(args.length != 2) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    Virologist victim = (Virologist) storage.get(args[1]);
                    if(victim == null) throw new Exception();
                    v.steal(victim);
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "EndRound":{
                if(args.length != 1) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    v.endRound();
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "ActivateEffects":{
                if(args.length != 1) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    for(SpecialModifier sm : v.getModifiers()){
                        sm.effect(v);
                    }
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "SwitchSuite":{
                if(args.length != 1) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    List<Suite> worn = v.getInventory().getSuites().stream().filter(Suite::isActive).collect(Collectors.toList());
                    List<Suite> stored = v.getInventory().getSuites().stream().filter(it -> !it.isActive()).collect(Collectors.toList());
                    logMessage(String.format("%s is wearing the following suites:", getIdForObject(v)));
                    for(int i = 0; i < worn.size(); i++){
                        logMessage(String.format("%d. %s", i+1, getIdForObject(worn.get(i))));
                    }
                    int fromIdx = logQuestion("Pick a suite's index you want to switch from: ", false) - 1;
                    while(fromIdx < 0 || fromIdx >= worn.size()){
                        fromIdx = logQuestion(String.format("No such item in %s's inventory, pick again: ", getIdForObject(v)), false) - 1;
                    }
                    logMessage(String.format("%s has the following non-worn suites:", getIdForObject(v)));
                    for(int i = 0; i < stored.size(); i++){
                        logMessage(String.format("%d. %s", i+1, getIdForObject(stored.get(i))));
                    }
                    int toIdx = logQuestion("Pick a suite's index you want to switch to: ", false) - 1;
                    while(toIdx < 0 || toIdx >= stored.size()){
                        toIdx = logQuestion(String.format("No such item in %s's inventory, pick again: ", getIdForObject(v)), false) - 1;
                    }
                    v.switchSuite(worn.get(fromIdx), stored.get(toIdx));
                } catch(Exception e){
                    throw new Exception("Hiba történt [hibás paraméter]");
                }
                break;
            }
            case "UseAxe":{
                if(args.length != 3) throw new Exception("Hiba történt [túl sok/kevés paraméter]");
                try {
                    Virologist v = (Virologist) storage.get(args[0]);
                    if(v == null) throw new Exception();
                    Axe a = (Axe) storage.get(args[1]);
                    if(a == null) throw new Exception();
                    Virologist victim = (Virologist) storage.get(args[2]);
                    if(victim == null) throw new Exception();
                    v.attack(a, victim);
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
        ProtoMain.geneInit();
        RoundManager.getInstance().getSteppables().clear();
        RoundManager.getInstance().getVirologists().clear();
        RoundManager.getInstance().setMovedCounter(0);
    }

}
