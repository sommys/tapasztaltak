package hu.tapasztaltak.skeleton;

import hu.tapasztaltak.model.Game;
import hu.tapasztaltak.model.RoundManager;

import javax.swing.*;
import java.util.Scanner;

//todo: CSIMMA
public class SkeletonMenu {
    //ezt itthagyom, jol johet majd a useAgent-hez :D
    //region useAgenthelp

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String input = "";
        do{
            input = sc.nextLine();
            switch (input){
                case "1":
                    RoundManager rm = new RoundManager();
                    Game game = new Game();
                    Logger.log("Le van bénulva a virológus? (I/N)");
                    input = sc.nextLine();
                    TestSetup.virologistMoves();
                    break;
                case "help":

                    Logger.log("help:");
                    break;
                case "endtest":
                    Logger.log("Teszt vége!");
                    break;
                default:
                    Logger.log("A 'help' paranccsal kaphat leírást a program működéséről.");
                    break;
            }
        }while (!input.equals("endtest"));
        /*System.out.println("a");
        System.out.print("Le van bénulva a virológus? (I/N):");

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
        }*/
    }


    //endregion
}
