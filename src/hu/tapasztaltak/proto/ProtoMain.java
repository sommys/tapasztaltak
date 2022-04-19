package hu.tapasztaltak.proto;

public class ProtoMain {
    /**
     * Menü felépítése, tesztfuttatások hívása
     */
    public static void main(String[] args) {
        ProtoTestRunner.init(); //Tesztek létrehozása a megfelelő paraméterekkel
        System.out.println("Hello PROTO!");
    }
}
