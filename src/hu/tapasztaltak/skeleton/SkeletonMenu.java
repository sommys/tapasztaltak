package hu.tapasztaltak.skeleton;
import java.util.Scanner;

import static hu.tapasztaltak.skeleton.Logger.LogType.COMMENT;
import static hu.tapasztaltak.skeleton.Logger.LogType.QUESTION;

/**
 * A menü kezeléséért felelős osztály
 */
public class SkeletonMenu {
    /**
     * Az alkalmazást futtató metódus
     * @param args parancssori argumentumok
     */
    public static void skeletonMain(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        help();
        String input;
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
                    TestSetup.virologistSteals();
                    break;
                case "9":
                    TestSetup.virologistSwitchesSuite();
                    break;
                case "10":
                    TestSetup.shelterRefresh();
                    break;
                case "11":
                    TestSetup.warehouseRefresh();
                    break;
                case "12":
                    TestSetup.playerStartsGame();
                    break;
                case "13":
                    TestSetup.virologistDances();
                    break;
                case "14":
                    TestSetup.virologistForgets();
                    break;
                case "15":
                    TestSetup.virologistIsBeingStunned();
                    break;
                case "16":
                    TestSetup.virologistPutsOnBag();
                    break;
                case "17":
                    TestSetup.virologistPutsOnCape();
                    break;
                case "18":
                    TestSetup.virologistPutsOnGloves();
                    break;
                case "19":
                    TestSetup.virologistEndsRoundRoundManagerReacts();
                    break;
                case "help":
                    help();
                    break;
                case "endtest":
                    Logger.log(null,"Köszi, hogy letesztelted! :) @ tapasztaltak", COMMENT);
                    break;
                default:
                    Logger.log(null,"A 'help' paranccsal kaphat leírást a program működéséről.\nAz 'endtest' paranccsal léphet ki a tesztből.", COMMENT);
                    break;
            }
        } while (!input.equals("endtest"));
    }

    /**
     * A menü kiírását végző metódus
     * Törli a konzolt és kiírja a lehetséges teszteseteket.
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
        Logger.log(null,"8: Virologist steals", COMMENT);
        Logger.log(null,"9: Virologist switches suite", COMMENT);
        Logger.log(null,"10: Shelter refresh", COMMENT);
        Logger.log(null,"11: Warehouse refresh", COMMENT);
        Logger.log(null,"12: Player starts game", COMMENT);
        Logger.log(null,"13: Virologist dances", COMMENT);
        Logger.log(null,"14: Virologist forgets", COMMENT);
        Logger.log(null,"15: Virologist is being stunned", COMMENT);
        Logger.log(null,"16: Virologist puts on bag", COMMENT);
        Logger.log(null,"17: Virologist puts on cape", COMMENT);
        Logger.log(null,"18: Virologist puts on gloves", COMMENT);
        Logger.log(null,"19: Virologist ends round, round manager reacts", COMMENT);
    }
}
