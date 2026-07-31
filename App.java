import java.util.Scanner;

public class App {
    public static Scanner sc;

    private boolean isRunning = false;

    public void start() {
        init();

        while (this.isRunning) {
            
        }

        close();
    }

    public void init() {
        try {
            sc = new Scanner(System.in);
            this.isRunning = true;
        } catch (Exception e) {
            System.out.println("Something went wrong. Try again");
        }
    }

    public void close() {
        sc.close();
    }
}