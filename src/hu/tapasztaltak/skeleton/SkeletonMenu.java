package hu.tapasztaltak.skeleton;

import hu.tapasztaltak.model.Game;
import hu.tapasztaltak.model.RoundManager;
import java.util.Scanner;

import static hu.tapasztaltak.skeleton.Logger.LogType.COMMENT;
import static hu.tapasztaltak.skeleton.Logger.LogType.QUESTION;

//todo: CSIMMA
public class SkeletonMenu {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        boolean started = false;
        String input = "";
        do{
            Logger.log(null,"Válasszon menüpontot:", QUESTION);
            input = sc.nextLine();
            switch (input){
                case "1":
                    if(!started){
                        RoundManager rm = new RoundManager();
                        Game game = new Game();
                        started = true;
                    }
                    else{
                        Logger.log(null, "A program már fut!", COMMENT);
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
                    Logger.log(null,"A szám beírásával tudsz menüpontot választani!", COMMENT);
                    Logger.log(null,"1: Start Game", COMMENT);
                    Logger.log(null,"2: Virologist move", COMMENT);
                    Logger.log(null,"3: Virologist uses agent on themself ", COMMENT);
                    Logger.log(null,"4: Virologist uses agent on other virologist", COMMENT);
                    Logger.log(null,"5: Virologist makes agent", COMMENT);
                    Logger.log(null,"6: Virologist scans warehouse", COMMENT);
                    Logger.log(null,"7: Virologist scans labor", COMMENT);
                    Logger.log(null,"8: Virologist scans shelter", COMMENT);
                    Logger.log(null,"9: Virologist steals material", COMMENT);
                    Logger.log(null,"10: Virologist steals suite", COMMENT);
                    Logger.log(null,"11: Virologist switches suite", COMMENT);
                    Logger.log(null,"12: Shelter refresh", COMMENT);
                    Logger.log(null,"13: Warehouse refresh", COMMENT);
                    Logger.log(null,"14: Shelter refresh", COMMENT);
                    Logger.log(null,"15: Virologist dances", COMMENT);
                    Logger.log(null,"16: Virologist forgets", COMMENT);
                    Logger.log(null,"17: Virologist is being stunned", COMMENT);
                    Logger.log(null,"18: Virologist puts on bag", COMMENT);
                    Logger.log(null,"19: Virologist puts on cape", COMMENT);
                    Logger.log(null,"20: Virologist puts on gloves", COMMENT);
                    Logger.log(null,"21: Virologist ends round, round manager reacts", COMMENT);
                    break;
                case "endtest":
                    Logger.log(null,"Teszt vége!", COMMENT);
                    break;
                default:
                    Logger.log(null,"A 'help' paranccsal kaphat leírást a program működéséről.", COMMENT);
                    break;
            }
        } while (!input.equals("endtest"));
    }

    //ezt itthagyom, jol johet majd a useAgent-hez :D
    //region useAgenthelp
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
    //endregion

}
