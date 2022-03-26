package hu.tapasztaltak.skeleton;

public class Logger {
    static int tabCount = 0;
    public static void log(String type,String message) {
        boolean wrong = false;
        switch (type) {
            case "q":
                System.out.print("?");
                for (int i = 0; i < tabCount; i++)
                    System.out.print('\t');
                System.out.print(message);
                wrong = true;
                break;
            case "c":
                System.out.print(">");
                break;
            case "r":
                System.out.print("<");
                break;
            case "cm":
                System.out.print("-");
                break;
            default:
                System.out.println("Somethings wrong, i can feel it.");
                wrong = true;
                break;
        }
        if (!wrong) {
            for (int i = 0; i < tabCount; i++)
                System.out.print('\t');
            System.out.println(message);
        }
        wrong = false;
    }
    public static void increaseTabCount() {tabCount++;}
    public static void decreaseTabCount() {tabCount--;}
}
