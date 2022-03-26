package hu.tapasztaltak.skeleton;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Logger {
    static int tabCount = 0;

    /**
     * Logolás típusaira enum
     * Lehetséges értékek:
     * <ul>
     *     <li>{@code COMMENT} - komment</li>
     *     <li>{@code HÍVÁS} - hívás</li>
     *     <li>{@code VISSZATÉRÉS} - komment</li>
     *     <li>{@code QUESTION} - kérdés</li>
     * </ul>
     */
    public enum LogType{
        /**
         * Komment
         */
        COMMENT,
        /**
         * Hívás
         */
        CALL,
        /**
         * Visszatérés
         */
        RETURN,
        /**
         * Kérdés
         */
        QUESTION
    }

    /**
     * Logolást végző metódus
     * @param called az osztály, akin hívták a metódust, vagy {@code null}, ha nem hívásról van szó
     * @param methodNameOrMsg a metódosu neve, amit hívtak vagy üzenet
     * @param type a log típusa (lásd: {@link LogType})
     * @param args a függvényhívás paraméterei, amennyiben voltak
     */
    public static void log(Object called, String methodNameOrMsg, LogType type, Object ... args) {
        if(type == LogType.CALL) tabCount++;

        String tabs = "";
        for (int i = 0; i < tabCount; i++) tabs+='\t';

        switch (type) {
            case QUESTION:
                System.out.print("? "+tabs+methodNameOrMsg);
                break;
            case CALL:
                StringBuilder callString = new StringBuilder("["+TestSetup.getName(called)+":"+called.getClass().getSimpleName()+"]");
                callString.append("."+methodNameOrMsg);
                String params = Arrays.stream(args).map(param -> TestSetup.getName(param)).collect(Collectors.joining(",", "(", ")"));
                callString.append(params);
                System.out.println("> "+tabs+callString);
                break;
            case RETURN:
                tabCount--;
                if(!methodNameOrMsg.isBlank())
                    System.out.println("< "+tabs+methodNameOrMsg);
                break;
            case COMMENT:
                System.out.println("- "+tabs+methodNameOrMsg);
                break;
        }
    }
    public static void increaseTabCount() {tabCount++;}
    public static void decreaseTabCount() {tabCount--;}
}
