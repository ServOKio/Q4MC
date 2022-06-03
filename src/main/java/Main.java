import net.servokio.q4mc.MainDC;

public class Main {
    public static void main(String[] args) {
        String token = "your_token";

        System.out.println("Initialization...");
        MainDC mainDC = new MainDC(args);
        mainDC.init();
    }
}