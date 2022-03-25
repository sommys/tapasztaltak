package hu.tapasztaltak.skeleton;

public class Logger {
    static int tabCount = 0;

    public static void log(String message){
        for (int i = 0; i < tabCount; i++)
            System.out.print('\t');
        System.out.println(message);
    }
    public static void increaseTabCount() {
        tabCount++;
    }
    public static void decreaseTabCount() {
        tabCount--;
    }
}
