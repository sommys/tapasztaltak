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
        boolean started = false;
        String input = "";
        do{
            Logger.log("q","Válasszon menüpontot:");
            input = sc.nextLine();
            switch (input){
                case "1":
                    if(!started){
                        RoundManager rm = new RoundManager();
                        Game game = new Game();
                        started = true;
                    }
                    else{
                        Logger.log("cm","A program már fut!");
                    }
                    break;
                case "2":
                    TestSetup.virologistMoves();
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
                case "7":
                    break;
                case "8":
                    break;
                case "9":
                    break;
                case "10":
                    break;
                case "11":
                    break;
                case "12":
                    break;
                case "13":
                    break;
                case "14":
                    break;
                case "15":
                    break;
                case "16":
                    break;
                case "17":
                    break;
                case "18":
                    break;
                case "19":
                    break;
                case "20":
                    break;
                case "21":
                    break;
                case "help":
                    Logger.log("cm","A szám beírásával tudsz menüpontot választani!");
                    Logger.log("cm","1: Start Game");
                    Logger.log("cm","2: Virologist move");
                    Logger.log("cm","3: Virologist uses agent on themself ");
                    Logger.log("cm","4: Virologist uses agent on other virologist");
                    Logger.log("cm","5: Virologist makes agent");
                    Logger.log("cm","6: Virologist scans warehouse");
                    Logger.log("cm","7: Virologist scans labor");
                    Logger.log("cm","8: Virologist scans shelter");
                    Logger.log("cm","9: Virologist steals material");
                    Logger.log("cm","10: Virologist steals suite");
                    Logger.log("cm","11: Virologist switches suite");
                    Logger.log("cm","12: Shelter refresh");
                    Logger.log("cm","13: Warehouse refresh");
                    Logger.log("cm","14: Shelter refresh");
                    Logger.log("cm","15: Virologist dances");
                    Logger.log("cm","16: Virologist forgets");
                    Logger.log("cm","17: Virologist is being stunned");
                    Logger.log("cm","18: Virologist puts on bag");
                    Logger.log("cm","19: Virologist puts on cape");
                    Logger.log("cm","20: Virologist puts on gloves");
                    Logger.log("cm","21: Virologist ends round, round manager reacts");
                    break;
                case "endtest":
                    Logger.log("cm","Teszt vége!");
                    break;
                default:
                    Logger.log("cm","A 'help' paranccsal kaphat leírást a program működéséről.");
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
