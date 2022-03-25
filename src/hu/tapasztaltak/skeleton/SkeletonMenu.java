package hu.tapasztaltak.skeleton;

import java.util.Scanner;

//todo: CSIMA
public class SkeletonMenu {
    //ezt itthagyom, jol johet majd a useAgent-hez :D
    //region useAgenthelp
    {
        System.out.print("Le van bénulva a virológus? (I/N):");
        Scanner sc = new Scanner(System.in);
        String stunDecision = sc.nextLine();
        boolean stunned = false;
        if(stunDecision.equalsIgnoreCase("I")){
            stunned = true;
        }
        System.out.print("Melyik ágenst szeretnéd használni? [1=Dance, 2=Forget, 3=Protect, 4=Stun]:");
        int agentChoice = sc.nextInt();
        while(agentChoice < 1 || agentChoice > 4){
            System.out.println("Hibás bemenet...");
            System.out.print("Melyik ágenst szeretnéd használni? [1=Dance, 2=Forget, 3=Protect, 4=Stun]:");
            agentChoice = sc.nextInt();
        }
        switch (agentChoice){
            case 1: {

            }
            case 2: {

            }
            case 3: {

            }
            default: {

            }
        }
    }
    //endregion
}
