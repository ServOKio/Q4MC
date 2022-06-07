import net.servokio.q4mc.MainDC;

public class Main {
    private static String token;

    public static void main(String[] args) {
        System.out.println("Initialization...");
        MainDC mainDC = new MainDC(args);
        mainDC.init();
    }
}