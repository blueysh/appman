import me.blueysh.appman.Main;

import java.util.Scanner;

public class TestingInterface {
    public static void main(String[] args) {

        System.out.print("Input args: ");
        Scanner scan = new Scanner(System.in);

        Main.main(scan.nextLine().split(" "));
    }
}
