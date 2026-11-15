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

    static String currentTourUser = null;
    static String currentTourID = null;

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


    }

    public static void touristMenu() {
        boolean inTouristMenu = true;

        while (inTouristMenu) {
            while (inTouristMenu) {
                System.out.println(BOLD + "TOURIST DASHBOARD" + RESET);
                System.out.println(UNDERLINE + "[1]" + RESET + " Log In/Sign Up");
                System.out.println(UNDERLINE + "[2]" + RESET + " Browse Products");
                System.out.println(UNDERLINE + "[3]" + RESET + " View Cart & Checkout");
                System.out.println(UNDERLINE + "[4]" + RESET + " Claim Voucher");
                System.out.println(UNDERLINE + "[5]" + RESET + " Subscription");
                System.out.println(UNDERLINE + "[6]" + RESET + " Pre-Order History");
                System.out.println(UNDERLINE + "[7]" + RESET + " Log Out");
                System.out.print(YELLOW + "Select an option: " + RESET);

                String choice = scan.nextLine();

                switch (choice) {
                    case "1" -> {
                        logInMenu();
                    }
                    case "2" -> {
                        browseProducts();
                    }
                    case "3" -> {
                        // CART (review items)
                        // -> PAYMENT (confirm and pay)
                        // -> ORDER RECEIPT (display confirmation)
                    }
                    case "4" -> {
                        // VOUCHER CLAIM
                    }
                    case "5" -> {
                        // SUBSCRIPTION
                    }
                    case "6" -> {
                        // PRE-ORDER HISTORY
                    }
                    case "7" -> {
                        System.out.println(YELLOW + "Logging out..." + RESET);
                        inTouristMenu = false;
                    }
                    default -> System.out.println(YELLOW + "Invalid choice. Try again." + RESET);
                }
            }
        }
    }


    private static void logInMenu() {
        System.out.println("LOG IN or SIGN UP");
        System.out.println("[1] LOG IN\n[2] SIGN UP");
        System.out.print("Choice: ");
        int choice = Integer.parseInt(scan.nextLine());

        if (choice == 1) {
            System.out.println("Please enter your username and password.");
            boolean passMatch = false;
            while (!passMatch) {
                System.out.print("User: ");
                String name = scan.nextLine();
                System.out.print("Password: ");
                String pass = scan.nextLine();
                //DAO for user log-in
                Tourist currentUser = DataAccess.login(name, pass);
                if (currentUser != null) {
                    currentTourUser = currentUser.getTourname();
                    currentTourID = currentUser.getTourid();
                    System.out.println("Welcome, " + currentTourUser + "!");
                    passMatch = true;
                } else {
                    System.out.println("Incorrect credentials. Please try again.");
                }
            }
        } else {
            System.out.println("Please enter the fields below: ");
            System.out.print("Name: ");
            String name = scan.nextLine();
            System.out.print("Address: ");
            String address = scan.nextLine();
            System.out.print("Phone Number: ");
            int phoneNumber = Integer.parseInt(scan.nextLine());
            System.out.print("E-mail Address: ");
            String email = scan.nextLine();
            System.out.print("Username: ");
            String user = scan.nextLine();
            System.out.print("Password: ");
            String password = scan.nextLine();

            Tourist newUser = DataAccess.signUp(name, address, phoneNumber, email, user, password);
            if (newUser != null) {
                System.out.println("Registration successful! Welcome " + user + "!");
            } else {
                System.out.println("Sorry, an error occurred. Please try registering again.");
            }
        }
    }

    private static void browseProducts() {
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
}
