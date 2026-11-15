import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;


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
    static String currentVendID = null;
    static String currentVendUser = null;
    static String currentAdminUser = null;

    // simple cart: list of [prodid, qty]
    static ArrayList<String[]> cart = new ArrayList<>();

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
                    adminMenu();
                }
                case 2 -> {
                    vendorMenu();
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

    // ==================== TOURIST ====================
    public static void touristMenu() {
        boolean inTouristMenu = true;

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
                    viewCartAndCheckout();
                }
                case "4" -> {
                    claimVoucherMenu();
                }
                case "5" -> {
                    subscriptionMenu();
                }
                case "6" -> {
                    preOrderHistory();
                }
                case "7" -> {
                    System.out.println(YELLOW + "Logging out..." + RESET);
                    currentTourUser = null;
                    currentTourID = null;
                    cart.clear();
                    inTouristMenu = false;
                }
                default -> System.out.println(YELLOW + "Invalid choice. Try again." + RESET);
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
                currentTourUser = newUser.getTourname();
                currentTourID = newUser.getTourid();
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
            System.out.println(BOLD + "AVAILABLE PRODUCTS" + RESET);
            for (Product p : products) {
                System.out.println(p.getProdid() + " | " +
                        p.getProdname() + " | P" +
                        p.getProdprice() + " | " +
                        p.getProdstatus() + " | " + p.getProdcategory());
            }
            System.out.println("\nEnter prodid to add to cart (or 0 to go back): ");
            String pid = scan.nextLine();
            if (!pid.equals("0")) {
                Product found = DataAccess.getProductById(pid);
                if (found != null) {
                    System.out.print("Quantity: ");
                    int qty = Integer.parseInt(scan.nextLine());
                    cart.add(new String[]{pid, String.valueOf(qty)});
                    System.out.println("Added " + qty + " x " + found.getProdname() + " to cart.");
                } else {
                    System.out.println("Product not found.");
                }
            }
        }
    }

    private static void viewCartAndCheckout() {
        if (currentTourID == null) {
            System.out.println("Please log in first.");
            return;
        }
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println(BOLD + "YOUR CART" + RESET);
        double total = 0;
        ArrayList<Product> cartProds = new ArrayList<>();
        ArrayList<Integer> qtys = new ArrayList<>();
        for (String[] item : cart) {
            Product p = DataAccess.getProductById(item[0]);
            if (p != null) {
                int q = Integer.parseInt(item[1]);
                double sub = p.getProdprice() * q;
                total += sub;
                cartProds.add(p);
                qtys.add(q);
                System.out.println(p.getProdname() + " x" + q + " = P" + sub);
            }
        }
        System.out.println("TOTAL: P" + total);

        System.out.print("Proceed to checkout? (y/n): ");
        String ans = scan.nextLine();
        if (!ans.equalsIgnoreCase("y")) return;

        System.out.print("Pickup date (YYYY-MM-DD): ");
        String pdate = scan.nextLine();
        System.out.print("Pickup time (HH:MM:SS): ");
        String ptime = scan.nextLine();
        System.out.print("Pickup place: ");
        String pplace = scan.nextLine();
        System.out.print("Payment mode (cash/card/online): ");
        String mode = scan.nextLine();

        // simple ids
        String payid = "PAY-" + System.currentTimeMillis() % 100000;
        String salesid = "S-" + System.currentTimeMillis() % 100000;

        boolean ok = DataAccess.createPayment(payid, total, mode, "pending", null);
        if (!ok) {
            System.out.println("Payment failed.");
            return;
        }

        Sales s = new Sales(salesid, currentTourID, payid, LocalDate.now().toString(), "Pending",
                total, (int)(total/100), 0, pdate, ptime, pplace);
        ok = DataAccess.createSales(s);
        if (!ok) {
            System.out.println("Sales record failed.");
            return;
        }

        for (int i = 0; i < cartProds.size(); i++) {
            Product p = cartProds.get(i);
            int q = qtys.get(i);
            SalesDetails sd = new SalesDetails(salesid, p.getProdid(), p.getVendid(), q, p.getProdprice(), p.getProdprice()*q);
            DataAccess.createSalesDetail(sd);
        }

        System.out.println(BOLD + "ORDER PLACED!" + RESET);
        System.out.println("Sales ID: " + salesid);
        System.out.println("Total: P" + total);
        System.out.println("Pickup: " + pdate + " " + ptime + " at " + pplace);
        System.out.println("Thank you!");
        cart.clear();
    }

    private static void claimVoucherMenu() {
        if (currentTourID == null) {
            System.out.println("Please log in first.");
            return;
        }
        ArrayList<Voucher> vouchers = DataAccess.getVouchers();
        System.out.println(BOLD + "AVAILABLE VOUCHERS" + RESET);
        for (Voucher v : vouchers) {
            System.out.println(v.getVoucherid() + " | " + v.getVoucherdesc() + " | Points req: " + v.getVoucherpointsreq());
        }
        System.out.print("Enter voucher id to claim (or 0 to cancel): ");
        String vid = scan.nextLine();
        if (vid.equals("0")) return;
        if (DataAccess.claimVoucher(currentTourID, vid)) {
            System.out.println("Voucher claimed successfully!");
        }
    }

    private static void subscriptionMenu() {
        System.out.println(BOLD + "SUBSCRIPTION" + RESET);
        System.out.println("Subscription 1 - Basic (free)");
        System.out.println("Subscription 2 - Silver (points boost)");
        System.out.println("Subscription 3 - Gold (extra discounts)");
        System.out.println("(Feature coming soon - for now just view)");
    }

    private static void preOrderHistory() {
        if (currentTourID == null) {
            System.out.println("Please log in first.");
            return;
        }
        ArrayList<Sales> history = DataAccess.getSalesByTourist(currentTourID);
        if (history.isEmpty()) {
            System.out.println("No previous orders.");
            return;
        }
        System.out.println(BOLD + "PRE-ORDER HISTORY" + RESET);
        for (Sales s : history) {
            System.out.println(s.getSalesid() + " | " + s.getSalesdate() + " | P" + s.getSalestotalamt() + " | " + s.getSalesstatus());
            System.out.println("  Pickup: " + s.getSalespickupdate() + " " + s.getSalespickuptime() + " @ " + s.getSalespickupplace());
        }
    }

    // ==================== VENDOR ====================
    public static void vendorMenu() {
        boolean inVendor = true;
        while (inVendor) {
            System.out.println(BOLD + "VENDOR DASHBOARD" + RESET);
            System.out.println(UNDERLINE + "[1]" + RESET + " Log In");
            System.out.println(UNDERLINE + "[2]" + RESET + " View My Products");
            System.out.println(UNDERLINE + "[3]" + RESET + " Add Product");
            System.out.println(UNDERLINE + "[4]" + RESET + " Update Product");
            System.out.println(UNDERLINE + "[5]" + RESET + " Delete Product");
            System.out.println(UNDERLINE + "[6]" + RESET + " Log Out");
            System.out.print(YELLOW + "Select: " + RESET);
            String ch = scan.nextLine();

            switch (ch) {
                case "1" -> vendorLogin();
                case "2" -> vendorViewProducts();
                case "3" -> vendorAddProduct();
                case "4" -> vendorUpdateProduct();
                case "5" -> vendorDeleteProduct();
                case "6" -> {
                    currentVendID = null;
                    currentVendUser = null;
                    inVendor = false;
                    System.out.println("Logged out.");
                }
                default -> System.out.println("Invalid.");
            }
        }
    }

    private static void vendorLogin() {
        System.out.print("Username: ");
        String u = scan.nextLine();
        System.out.print("Password: ");
        String p = scan.nextLine();
        Vendor v = DataAccess.loginVendor(u, p);
        if (v != null) {
            currentVendID = v.getVendid();
            currentVendUser = v.getVenduser();
            System.out.println("Welcome " + v.getVendestabname() + "!");
        } else {
            System.out.println("Wrong credentials.");
        }
    }

    private static void vendorViewProducts() {
        if (currentVendID == null) {
            System.out.println("Login first.");
            return;
        }
        ArrayList<Product> prods = DataAccess.getProductsByVendor(currentVendID);
        if (prods.isEmpty()) {
            System.out.println("No products yet.");
            return;
        }
        for (Product p : prods) {
            System.out.println(p.getProdid() + " | " + p.getProdname() + " | P" + p.getProdprice() + " | " + p.getProdstatus());
        }
    }

    private static void vendorAddProduct() {
        if (currentVendID == null) {
            System.out.println("Login first.");
            return;
        }
        System.out.print("Product ID: ");
        String id = scan.nextLine();
        System.out.print("Name: ");
        String name = scan.nextLine();
        System.out.print("Description: ");
        String desc = scan.nextLine();
        System.out.print("Price: ");
        int price = Integer.parseInt(scan.nextLine());
        System.out.print("Status (Available/Pre-Order/Out-of-Stock): ");
        String status = scan.nextLine();
        System.out.print("Category: ");
        String cat = scan.nextLine();
        System.out.print("Image URL (or leave blank): ");
        String img = scan.nextLine();
        if (img.isEmpty()) img = null;

        Product p = new Product(id, currentVendID, name, desc, price, status, cat, img);
        if (DataAccess.addProduct(p)) {
            System.out.println("Product added!");
        }
    }

    private static void vendorUpdateProduct() {
        if (currentVendID == null) {
            System.out.println("Login first.");
            return;
        }
        System.out.print("Product ID to update: ");
        String id = scan.nextLine();
        Product old = DataAccess.getProductById(id);
        if (old == null || !old.getVendid().equals(currentVendID)) {
            System.out.println("Product not found or not yours.");
            return;
        }
        System.out.print("New name (" + old.getProdname() + "): ");
        String name = scan.nextLine();
        if (name.isEmpty()) name = old.getProdname();
        System.out.print("New desc: ");
        String desc = scan.nextLine();
        if (desc.isEmpty()) desc = old.getProddesc();
        System.out.print("New price (" + old.getProdprice() + "): ");
        String pr = scan.nextLine();
        int price = pr.isEmpty() ? old.getProdprice() : Integer.parseInt(pr);
        System.out.print("New status (" + old.getProdstatus() + "): ");
        String status = scan.nextLine();
        if (status.isEmpty()) status = old.getProdstatus();
        System.out.print("New category (" + old.getProdcategory() + "): ");
        String cat = scan.nextLine();
        if (cat.isEmpty()) cat = old.getProdcategory();
        System.out.print("New img url: ");
        String img = scan.nextLine();
        if (img.isEmpty()) img = old.getProdimg();

        Product p = new Product(id, currentVendID, name, desc, price, status, cat, img);
        if (DataAccess.updateProduct(p)) {
            System.out.println("Updated!");
        }
    }

    private static void vendorDeleteProduct() {
        if (currentVendID == null) {
            System.out.println("Login first.");
            return;
        }
        System.out.print("Product ID to delete: ");
        String id = scan.nextLine();
        Product old = DataAccess.getProductById(id);
        if (old == null || !old.getVendid().equals(currentVendID)) {
            System.out.println("Not found or not yours.");
            return;
        }
        if (DataAccess.deleteProduct(id)) {
            System.out.println("Deleted.");
        }
    }

    // ==================== ADMIN ====================
    public static void adminMenu() {
        boolean inAdmin = true;
        while (inAdmin) {
            System.out.println(BOLD + "ADMIN DASHBOARD" + RESET);
            System.out.println(UNDERLINE + "[1]" + RESET + " Log In");
            System.out.println(UNDERLINE + "[2]" + RESET + " View All Tourists");
            System.out.println(UNDERLINE + "[3]" + RESET + " View All Vendors");
            System.out.println(UNDERLINE + "[4]" + RESET + " Update Vendor Status");
            System.out.println(UNDERLINE + "[5]" + RESET + " View All Products");
            System.out.println(UNDERLINE + "[6]" + RESET + " Log Out");
            System.out.print(YELLOW + "Select: " + RESET);
            String ch = scan.nextLine();

            switch (ch) {
                case "1" -> adminLogin();
                case "2" -> adminViewTourists();
                case "3" -> adminViewVendors();
                case "4" -> adminUpdateVendorStatus();
                case "5" -> {
                    ArrayList<Product> prods = DataAccess.getProducts();
                    for (Product p : prods) {
                        System.out.println(p.getProdid() + " | " + p.getProdname() + " | " + p.getProdstatus() + " | vend:" + p.getVendid());
                    }
                }
                case "6" -> {
                    currentAdminUser = null;
                    inAdmin = false;
                    System.out.println("Logged out.");
                }
                default -> System.out.println("Invalid.");
            }
        }
    }

    private static void adminLogin() {
        System.out.print("Username: ");
        String u = scan.nextLine();
        System.out.print("Password: ");
        String p = scan.nextLine();
        Admin a = DataAccess.loginAdmin(u, p);
        if (a != null) {
            currentAdminUser = a.getAdminuser();
            System.out.println("Welcome Admin " + a.getAdminname() + "!");
        } else {
            System.out.println("Wrong credentials.");
        }
    }

    private static void adminViewTourists() {
        if (currentAdminUser == null) {
            System.out.println("Login first.");
            return;
        }
        ArrayList<Tourist> list = DataAccess.getAllTourists();
        for (Tourist t : list) {
            System.out.println(t.getTourid() + " | " + t.getTourname() + " | " + t.getTouruser() + " | " + t.getTourloyalty());
        }
    }

    private static void adminViewVendors() {
        if (currentAdminUser == null) {
            System.out.println("Login first.");
            return;
        }
        ArrayList<Vendor> list = DataAccess.getAllVendors();
        for (Vendor v : list) {
            System.out.println(v.getVendid() + " | " + v.getVendestabname() + " | " + v.getVendstatus() + " | " + v.getVenduser());
        }
    }

    private static void adminUpdateVendorStatus() {
        if (currentAdminUser == null) {
            System.out.println("Login first.");
            return;
        }
        System.out.print("Vendor ID: ");
        String id = scan.nextLine();
        System.out.print("New status (active/approved/unreviewed/on hold): ");
        String st = scan.nextLine();
        if (DataAccess.updateVendorStatus(id, st)) {
            System.out.println("Status updated.");
        }
    }
}
