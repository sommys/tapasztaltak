package hu.tapasztaltak.proto;

import java.util.Scanner;

public class ProtoLogger {
    public static boolean loggingSwitch = true;
    public static StringBuilder actOutput = new StringBuilder();
    public static void logMessage(String msg){
        if(!loggingSwitch) return;
        System.out.println(msg);
        actOutput.append(msg).append("\n");
    }

    public static int logQuestion(String q, boolean yesNo) throws Exception {
        if(!loggingSwitch) return -1;
        System.out.print(q);
        Scanner sc = new Scanner(System.in);
        int choice;
        if(yesNo){
            String decision = sc.nextLine();
            if(decision.equalsIgnoreCase("I")){
                choice = 1;
            } else if(decision.equalsIgnoreCase("N")){
                choice = 0;
            } else {
                throw new Exception("Vmi frappáns haha");
            }
            actOutput.append(q).append(choice == 1 ? "Y" : "N").append("\n");
        } else {
            choice = sc.nextInt();
            actOutput.append(q).append(choice).append("\n");
        }
        return choice;
    }
}
