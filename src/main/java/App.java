import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Uno!");
        System.out.println("How many players?");
        int numPlayers=scanner.nextInt();
        Game g = new Game(numPlayers);
        g.Play(g);


    }
}
