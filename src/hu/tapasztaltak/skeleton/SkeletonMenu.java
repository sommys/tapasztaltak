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
        help();
        String input = "";
        do{
            Logger.log(null,"Válasszon menüpontot:", QUESTION);
            input = sc.nextLine();
            switch (input){
                case "1":
                    TestSetup.virologistMoves();
                    break;
                case "2":
                    TestSetup.useAgentOnThemself();
                    break;
                case "3":
                    TestSetup.useAgentOnOtherVirologist();
                    break;
                case "4":
                    TestSetup.virologistMakesAgent();
                    break;
                case "5":
                    TestSetup.virologistScansWarehouse();
                    break;
                case "6":
                    TestSetup.virologistScansLabor();
                    break;
                case "7":
                    TestSetup.virologistScansShelter();
                    break;
                case "8":
                    TestSetup.virologistStealsMaterial();
                    break;
                case "9":
                    TestSetup.virologistStealsSuite();
                    break;
                case "10":
                    TestSetup.virologistSwitchesSuite();
                    break;
                case "11":
                    TestSetup.shelterRefresh();
                    break;
                case "12":
                    TestSetup.warehouseRefresh();
                    break;
                case "13":
                    TestSetup.playerStartsGame();
                    break;
                case "14":
                    TestSetup.virologistDances();
                    break;
                case "15":
                    TestSetup.virologistForgets();
                    break;
                case "16":
                    TestSetup.virologistIsBeingStunned();
                    break;
                case "17":
                    TestSetup.virologistPutsOnBag();
                    break;
                case "18":
                    TestSetup.virologistPutsOnCape();
                    break;
                case "19":
                    TestSetup.virologistPutsOnGloves();
                    break;
                case "20":
                    TestSetup.virologistEndsRoundRoundManagerReacts();
                    break;
                case "help":
                    help();
                    break;
                case "endtest":
                    Logger.log(null,"Köszi hogy letesztelted! :) @ tapasztaltak", COMMENT);
                    break;
                default:
                    Logger.log(null,"A 'help' paranccsal kaphat leírást a program működéséről.\nAz 'endtest' paranccsal léphet ki a tesztből.", COMMENT);
                    break;
            }
        } while (!input.equals("endtest"));
    }

    /**
     * A menü kiírását végző metódus
     */
    private static void help() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch(Exception ignored){}
        Logger.log(null,"A szám beírásával tud menüpontot választani, az 'endtest' paranccsal pedig kilépni a programból!", COMMENT);
        Logger.log(null,"1: Virologist moves", COMMENT);
        Logger.log(null,"2: Virologist uses agent on themself", COMMENT);
        Logger.log(null,"3: Virologist uses agent on other virologist", COMMENT);
        Logger.log(null,"4: Virologist makes agent", COMMENT);
        Logger.log(null,"5: Virologist scans warehouse", COMMENT);
        Logger.log(null,"6: Virologist scans labor", COMMENT);
        Logger.log(null,"7: Virologist scans shelter", COMMENT);
        Logger.log(null,"8: Virologist steals material", COMMENT);
        Logger.log(null,"9: Virologist steals suite", COMMENT);
        Logger.log(null,"10: Virologist switches suite", COMMENT);
        Logger.log(null,"11: Shelter refresh", COMMENT);
        Logger.log(null,"12: Warehouse refresh", COMMENT);
        Logger.log(null,"13: Player Start Game", COMMENT);
        Logger.log(null,"14: Virologist dances", COMMENT);
        Logger.log(null,"15: Virologist forgets", COMMENT);
        Logger.log(null,"16: Virologist is being stunned", COMMENT);
        Logger.log(null,"17: Virologist puts on bag", COMMENT);
        Logger.log(null,"18: Virologist puts on cape", COMMENT);
        Logger.log(null,"19: Virologist puts on gloves", COMMENT);
        Logger.log(null,"20: Virologist ends round, round manager reacts", COMMENT);
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
