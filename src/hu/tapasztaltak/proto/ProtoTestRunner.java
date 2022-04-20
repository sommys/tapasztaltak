package hu.tapasztaltak.proto;

import java.util.ArrayList;
import java.util.List;

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

    public static boolean runCommand(String command){
        if(command.equalsIgnoreCase("exit")) return false;
        //todo
        switch(command){
            case "": break;
            case "aa": break;
            default: return false;
        }
        return true;
    }

}
