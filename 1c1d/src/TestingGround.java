import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class TestingGround {
    static Scanner scan = new Scanner(System.in);
    static final String RESET = "\u001B[0m";
    static final String YELLOW = "\u001B[33m";
    static final String BLUE = "\u001B[34m";
    static final String BOLD = "\u001B[1m";
    static final String ITALIC = "\u001B[3m";
    static final String UNDERLINE = "\u001B[4m";


    public static void main(String[] args) {
        boolean active = true;
        System.out.printf(BOLD + YELLOW + "Welcome to CAR Reservation System! Choose who you will be...\n" + RESET);

        do {
            System.out.println("I am the...");
            System.out.println(UNDERLINE + "[1]" + RESET + " ADMIN");
            System.out.println(UNDERLINE + "[2]" + RESET + " VENDOR");
            System.out.println(UNDERLINE + "[3]" + RESET + " TOURIST");
            System.out.println(UNDERLINE + "[4]" + RESET + " EXIT");
            System.out.print(YELLOW + "Select your role: " + RESET);
            int person = Integer.parseInt(scan.nextLine());
            switch (person) {
                case 1 -> {
                    //adminMenu();
                }
                case 2 -> {
                    //vendorMenu()
                }
                case 3 -> {
                    touristMenu();
                }

                case 4 -> {
                    System.out.print(BOLD + ITALIC + "Thank you for using CARRS!");
                    System.exit(0);
                }
            }

        } while (active);

        ArrayList<Product> products = DataAccess.getProducts();

        if (products.isEmpty()) {
            System.out.println("No products found. Check your database.");
        } else {
            for (Product p : products) {
                System.out.println(p.getProdid() + " | " +
                        p.getProdname() + " | ₱" +
                        p.getProdprice() + " | " +
                        p.getProdstatus());
            }
        }
    }

    public static void touristMenu() {
        boolean inTouristMenu = true;

        while (inTouristMenu) {
            System.out.println(BOLD + "TOURIST DASHBOARD" + RESET);
            System.out.println(UNDERLINE + "[1]" + RESET + " Browse Products");
            System.out.println(UNDERLINE + "[2]" + RESET + " View My Order History");
            System.out.println(UNDERLINE + "[3]" + RESET + " Cancel an Order");
            System.out.println(UNDERLINE + "[4]" + RESET + " Log Out");
            System.out.print(YELLOW + "Select an option: " + RESET);

            String choice = scan.nextLine();

            switch (choice) {
                case "1":
                    System.out.println(BLUE + "Opening product catalog..." + RESET);
                    break;
                case "2":
                    System.out.println(BLUE + "Loading your order history..." + RESET);
                    break;
                case "3":
                    System.out.println(BLUE + "Opening cancellation menu..." + RESET);
                    break;
                case "4":
                    System.out.println(YELLOW + "Logging out..." + RESET);
                    inTouristMenu = false;
                    break;
                default:
                    System.out.println(YELLOW + "Invalid choice. Try again." + RESET);
            }
        }
    }
}