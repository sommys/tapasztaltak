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
            //input megnyitasa
            //soronkent beolvassuk a parancsokat, meghivjuk a runCommand-ot
            //kiirjuk az outputFile-ba az eredmenyt: ProtoLogger.actOutput.toString, majd ProtoLogger.actOutput = new StringBuilder();
            //ellenőrizzük az eredményt okosba
        }
    }

    private static final List<Test> testList = new ArrayList<>();

    public static void init(){
        Test t1 = new Test("t1", "t1.txt", "t1_o.txt", "t1_eo.txt");
        Test t2 = new Test("t2", "t2.txt", "t2_o.txt", "t2_eo.txt");
        Test t3 = new Test("t3", "t3.txt", "t3_o.txt", "t3_eo.txt");
        Test t4 = new Test("t4", "t4.txt", "t4_o.txt", "t4_eo.txt");
        Test t5 = new Test("t5", "t5.txt", "t5_o.txt", "t5_eo.txt");
        Test t6 = new Test("t6", "t6.txt", "t6_o.txt", "t6_eo.txt");
        Test t7 = new Test("t7", "t7.txt", "t7_o.txt", "t7_eo.txt");
        Test t8 = new Test("t8", "t8.txt", "t8_o.txt", "t8_eo.txt");
        Test t9 = new Test("t9", "t9.txt", "t9_o.txt", "t9_eo.txt");
        Test t10 = new Test("t10", "t10.txt", "t10_o.txt", "t10_eo.txt");
        Test t11 = new Test("t11", "t11.txt", "t11_o.txt", "t11_eo.txt");
        Test t12 = new Test("t12", "t12.txt", "t12_o.txt", "t12_eo.txt");
        Test t13 = new Test("t13", "t13.txt", "t13_o.txt", "t13_eo.txt");
        testList.add(t1);
        testList.add(t2);
        testList.add(t3);
        testList.add(t4);
        testList.add(t5);
        testList.add(t6);
        testList.add(t7);
        testList.add(t8);
        testList.add(t9);
        testList.add(t10);
        testList.add(t11);
        testList.add(t12);
        testList.add(t13);
    }

    public static void runTest(int idx){
        Test toRun = testList.get(idx);
        toRun.run();
        System.out.println(toRun.name + " result: " + (toRun.result ? "SUCCESS" : "FAIL"));
    }

    public static void runCommand(String command){

    }

}
