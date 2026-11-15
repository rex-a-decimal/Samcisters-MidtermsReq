import java.util.ArrayList;
import java.util.Scanner;

public class TestingGround {
    static Scanner scan = new Scanner(System.in);
    static String currentTourID = null;
    static String currentTourName = null;
    static String currentVendID = null;
    static String currentAdmin = null;
    static ArrayList<String[]> cart = new ArrayList<>(); // [prodid, qty]

    public static void main(String[] args) {
        System.out.println("Welcome to CAR Reservation System! Choose who you will be...");
        while (true) {
            System.out.println("\nI am the...");
            System.out.println("[1] ADMIN");
            System.out.println("[2] VENDOR");
            System.out.println("[3] TOURIST");
            System.out.println("[4] EXIT");
            System.out.print("Select your role: ");
            String choice = scan.nextLine();

            switch (choice) {
                case "1" -> adminMenu();
                case "2" -> vendorMenu();
                case "3" -> touristMenu();
                case "4" -> {
                    System.out.println("Thank you for using CARRS!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ==================== TOURIST ====================
    static void touristMenu() {
        while (true) {
            System.out.println("\nTOURIST DASHBOARD");
            System.out.println("[1] Log In/Sign Up");
            System.out.println("[2] Browse Products");
            System.out.println("[3] View Cart & Checkout");
            System.out.println("[4] Claim Voucher");
            System.out.println("[5] Subscription");
            System.out.println("[6] Pre-Order History");
            System.out.println("[7] Log Out");
            System.out.print("Select an option: ");
            String ch = scan.nextLine();

            switch (ch) {
                case "1" -> touristLogin();
                case "2" -> browseProducts();
                case "3" -> checkout();
                case "4" -> claimVoucher();
                case "5" -> System.out.println("Subscription feature coming soon.");
                case "6" -> history();
                case "7" -> {
                    currentTourID = null;
                    currentTourName = null;
                    cart.clear();
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid.");
            }
        }
    }

    static void touristLogin() {
        System.out.println("[1] LOG IN  [2] SIGN UP");
        System.out.print("Choice: ");
        String ch = scan.nextLine();

        if (ch.equals("1")) {
            System.out.print("User: ");
            String u = scan.nextLine();
            System.out.print("Password: ");
            String p = scan.nextLine();
            Tourist t = DataAccess.loginTourist(u, p);
            if (t != null) {
                currentTourID = t.getTourid();
                currentTourName = t.getTourname();
                System.out.println("Welcome, " + currentTourName + "!");
            } else {
                System.out.println("Incorrect credentials.");
            }
        } else if (ch.equals("2")) {
            System.out.print("Name: ");
            String name = scan.nextLine();
            System.out.print("Address: ");
            String addr = scan.nextLine();
            System.out.print("Phone: ");
            String phone = scan.nextLine();
            System.out.print("Email: ");
            String email = scan.nextLine();
            System.out.print("Username: ");
            String user = scan.nextLine();
            System.out.print("Password: ");
            String pass = scan.nextLine();

            Tourist t = DataAccess.signUpTourist(name, addr, phone, email, user, pass);
            if (t != null) {
                currentTourID = t.getTourid();
                currentTourName = t.getTourname();
                System.out.println("Registered! Welcome " + user);
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    static void browseProducts() {
        ArrayList<Product> list = DataAccess.getProducts();
        if (list.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        for (Product p : list) {
            System.out.println(p.getProdid() + " | " + p.getProdname() + " | P" + p.getProdprice() + " | " + p.getProdstatus());
        }
        System.out.print("Enter prodid to add to cart (0 to back): ");
        String id = scan.nextLine();
        if (id.equals("0")) return;
        Product found = DataAccess.getProductById(id);
        if (found != null) {
            System.out.print("Quantity: ");
            String qty = scan.nextLine();
            cart.add(new String[]{id, qty});
            System.out.println("Added to cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    static void checkout() {
        if (currentTourID == null) {
            System.out.println("Please log in first.");
            return;
        }
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        double total = 0;
        System.out.println("YOUR CART");
        for (String[] item : cart) {
            Product p = DataAccess.getProductById(item[0]);
            if (p != null) {
                int q = Integer.parseInt(item[1]);
                System.out.println(p.getProdname() + " x" + q + " = P" + (p.getProdprice() * q));
                total += p.getProdprice() * q;
            }
        }
        System.out.println("TOTAL: P" + total);
        System.out.print("Proceed? (y/n): ");
        if (!scan.nextLine().equalsIgnoreCase("y")) return;

        System.out.print("Pickup date (YYYY-MM-DD): ");
        String pdate = scan.nextLine();
        System.out.print("Pickup time (HH:MM:SS): ");
        String ptime = scan.nextLine();
        System.out.print("Pickup place: ");
        String place = scan.nextLine();
        System.out.print("Payment mode: ");
        String mode = scan.nextLine();

        String payid = "PAY" + (System.currentTimeMillis() % 100000);
        String salesid = "S" + (System.currentTimeMillis() % 100000);

        if (DataAccess.createOrder(payid, total, mode, salesid, currentTourID, pdate, ptime, place, cart)) {
            System.out.println("ORDER PLACED! Sales ID: " + salesid);
            cart.clear();
        }
    }

    static void claimVoucher() {
        if (currentTourID == null) {
            System.out.println("Please log in first.");
            return;
        }

        int points = DataAccess.getTouristPoints(currentTourID);
        ArrayList<Voucher> vouchers = DataAccess.getVouchers();
        ArrayList<Voucher> available = new ArrayList<>();

        System.out.println("\n========== AVAILABLE VOUCHERS ==========");
        System.out.println("Your earned points: " + points);
        System.out.println("----------------------------------------");

        // Show only vouchers the tourist has enough earned points to claim.
        for (Voucher v : vouchers) {
            if (points >= v.getVoucherpointsreq()) {
                available.add(v);
                System.out.println(v.getVoucherdesc() +
                        " | pts: " + v.getVoucherpointsreq());
            }
        }

        if (available.isEmpty()) {
            System.out.println("No vouchers available yet.");
            System.out.println("Earn points from completed purchases to unlock vouchers.");
            return;
        }

        System.out.println("----------------------------------------");
        System.out.print("Enter voucher name to claim (0 to cancel): ");
        String voucherName = scan.nextLine().trim();
        if (voucherName.equals("0")) return;

        Voucher selected = null;
        for (Voucher v : available) {
            if (v.getVoucherdesc().equalsIgnoreCase(voucherName)) {
                selected = v;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Voucher name not found or you do not have enough points.");
            return;
        }

        // Use the ID internally; the tourist only sees and enters the voucher name.
        if (DataAccess.claimVoucher(currentTourID, selected.getVoucherid())) {
            System.out.println("Voucher claimed!");
            System.out.println("Claimed: " + selected.getVoucherdesc());
        } else {
            System.out.println("Voucher could not be claimed.");
        }
    }

    static void history() {
        if (currentTourID == null) {
            System.out.println("Please log in first.");
            return;
        }
        ArrayList<Sales> list = DataAccess.getSalesByTourist(currentTourID);
        if (list.isEmpty()) {
            System.out.println("No previous orders.");
            return;
        }
        for (Sales s : list) {
            System.out.println(s.getSalesid() + " | " + s.getSalesdate() + " | P" + s.getSalestotalamt() + " | " + s.getSalesstatus());
        }
    }

    // ==================== VENDOR ====================
    static String currentStoreName = "";

    static void vendorMenu() {
        if (!vendorLogin()) {
            return; // Return to main menu if login fails
        }

        int choice = 0;
        do {
            clearScreen();
            printLine();
            System.out.println("STORE: " + currentStoreName + " | VENDOR ID: " + currentVendID);
            System.out.println("Product Management Dashboard");
            printLine();
            displayVendorProducts();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
            System.out.println("OPERATIONS MENU:");
            System.out.println("[1] Add Product");
            System.out.println("[2] Archive Product");
            System.out.println("[3] View & Search Sales");
            System.out.println("[4] Update Product Details");
            System.out.println("[5] Change Product Status");
            System.out.println("[6] Logout");
            printLine();
            System.out.print("Select option [1-6]: ");

            String input = scan.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1 -> vendorAddProduct();
                case 2 -> vendorArchiveProduct();
                case 3 -> vendorSearchSales();
                case 4 -> vendorUpdateProduct();
                case 5 -> vendorChangeProductStatus();
                case 6 -> {
                    System.out.println("  Logging out of " + currentStoreName + ". Goodbye!");
                    currentVendID = null;
                    currentStoreName = "";
                }
                default -> {
                    System.out.println("Invalid choice. Press enter to continue.");
                    scan.nextLine();
                }
            }
        } while (choice != 6);
    }

    static boolean vendorLogin() {
        printLine();
        System.out.println("TOURISM MARKETPLACE SYSTEM - VENDOR PORTAL");
        System.out.println("Vendor Login Screen");
        printLine();
        System.out.print("Enter Vendor Username: ");
        String username = scan.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scan.nextLine().trim();
        printLine();

        Vendor v = DataAccess.loginVendor(username, password);
        if (v != null) {
            currentVendID = v.getVendid();
            currentStoreName = v.getVendestabname();
            System.out.println("Authenticated successfully! Welcome, " + currentStoreName + ".");
            System.out.print("Press enter to continue...");
            scan.nextLine();
            return true;
        }

        System.out.println("Invalid username or password.");
        return false;
    }

    static void displayVendorProducts() {
        System.out.println("Prod ID  | Product Name     | Category  | Price        | Status");
        System.out.println();
        boolean found = false;

        for (Product p : DataAccess.getProductsByVendor(currentVendID)) {
            if (!"Archived".equalsIgnoreCase(p.getProdstatus())) {
                found = true;
                System.out.printf("  %-8s | %-16s | %-9s | PHP %-8.2f | %s%n",
                        p.getProdid(),
                        p.getProdname(),
                        p.getProdcategory(),
                        (double) p.getProdprice(),
                        p.getProdstatus());
            }
        }

        if (!found) {
            System.out.println("No products found.");
        }
    }

    static void vendorAddProduct() {
        clearScreen();
        printLine();
        System.out.println("ADD PRODUCT");
        printLine();
        System.out.print("Enter Product ID: ");
        String productID = scan.nextLine().trim();
        System.out.print("Enter Product Name: ");
        String productName = scan.nextLine().trim();
        System.out.print("Enter Product Description: ");
        String description = scan.nextLine().trim();

        int price = 0;
        while (true) {
            System.out.print("Enter Price: ");
            try {
                price = Integer.parseInt(scan.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a whole number.");
            }
        }

        System.out.print("Enter Category: ");
        String category = scan.nextLine().trim();
        System.out.print("Enter Initial Status: ");
        String status = scan.nextLine().trim();

        printLine();
        System.out.print("Save record to database? (Y/N): ");
        String confirm = scan.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y")) {
            Product p = new Product(productID, currentVendID, productName, description, price, status, category, null);
            if (DataAccess.addProduct(p)) {
                System.out.println("  Product '" + productID + "' successfully added under " + currentStoreName + "!");
            } else {
                System.out.println("  Error adding product to database.");
            }
        } else {
            System.out.println("Product addition canceled.");
        }
        System.out.print("Press enter to return...");
        scan.nextLine();
    }

    static void vendorArchiveProduct() {
        clearScreen();
        printLine();
        System.out.println("ARCHIVE PRODUCT");
        printLine();
        System.out.print("Enter Product ID to Archive: ");
        String productID = scan.nextLine().trim();

        Product old = DataAccess.getProductById(productID);
        if (old == null || !old.getVendid().equals(currentVendID)) {
            System.out.println("Product not found or does not belong to your store.");
            System.out.print("Press enter to return...");
            scan.nextLine();
            return;
        }

        System.out.println("Current Product: " + old.getProdname() + " | Status: " + old.getProdstatus());
        printLine();
        System.out.print("Are you sure you want to archive this product? (Y/N): ");
        String confirm = scan.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y")) {
            Product archived = new Product(
                    old.getProdid(), currentVendID, old.getProdname(), old.getProddesc(),
                    old.getProdprice(), "Archived", old.getProdcategory(), old.getProdimg()
            );
            if (DataAccess.updateProduct(archived)) {
                System.out.println("\nProduct " + productID + " status updated to 'Archived'.");
                System.out.println("It will no longer appear in customer search results.");
            } else {
                System.out.println("Failed to archive product.");
            }
        } else {
            System.out.println("Archival canceled.");
        }
        System.out.print("Press enter to return...");
        scan.nextLine();
    }

    static void vendorSearchSales() {
        clearScreen();
        printLine();
        System.out.println("STORE: " + currentStoreName + " | VENDOR ID: " + currentVendID);
        System.out.println("Incoming Sales & Order Tracking & Search");
        printLine();
        System.out.println("Sales ID     | Date       | Product Name   | Qty  | Price        | Subtotal");
        System.out.println();

        String sql = "SELECT s.salesid, s.salesdate, p.prodname, sd.sdqty, sd.sdunitprice, (sd.sdqty * sd.sdunitprice) AS Subtotal " +
                "FROM sales s " +
                "JOIN salesdetails sd ON s.salesid = sd.salesid " +
                "JOIN product p ON sd.prodid = p.prodid " +
                "WHERE p.vendid = ?";

        try (java.sql.Connection conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/tourism_marketplace", "root", "");
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, currentVendID);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.printf("%-12s | %-10s | %-14s | %-4d | PHP %-8.2f | PHP %.2f%n",
                            rs.getString("salesid"),
                            rs.getString("salesdate"),
                            rs.getString("prodname"),
                            rs.getInt("sdqty"),
                            rs.getDouble("sdunitprice"),
                            rs.getDouble("Subtotal"));
                }
                if (!found) {
                    System.out.println("No sales records found.");
                }
            }
        } catch (Exception e) {
            System.out.println("No sales records found or unable to fetch sales data.");
        }

        printLine();
        System.out.println("SEARCH & FILTER OPTIONS:");
        System.out.println("[1] Search by Sales ID");
        System.out.println("[2] Search by Product ID");
        System.out.println("[3] Filter by Date");
        System.out.println("[4] Filter by Price");
        System.out.println("[5] Refresh Full List");
        System.out.println("[6] Return to Main Menu");
        printLine();
        System.out.print("Select option [1-6]: ");
        scan.nextLine();
        System.out.println("Returning to Main Menu...");
    }

    static void vendorUpdateProduct() {
        clearScreen();
        printLine();
        System.out.println("UPDATE PRODUCT DETAILS");
        printLine();
        System.out.print("Enter Product ID to Update: ");
        String productID = scan.nextLine().trim();

        Product old = DataAccess.getProductById(productID);
        if (old == null || !old.getVendid().equals(currentVendID)) {
            System.out.println("Product not found or does not belong to your store.");
            System.out.print("Press enter to return...");
            scan.nextLine();
            return;
        }

        System.out.println("Current Details: " + old.getProdname() + " | " + old.getProdcategory() + " | PHP " + old.getProdprice() + " | " + old.getProdstatus());
        printLine();

        System.out.print("Enter New Product Name (leave blank to keep current): ");
        String newName = scan.nextLine().trim();
        if (newName.isEmpty()) newName = old.getProdname();

        System.out.print("Enter New Description (leave blank to keep current): ");
        String newDesc = scan.nextLine().trim();
        if (newDesc.isEmpty()) newDesc = old.getProddesc();

        System.out.print("Enter New Price (leave blank to keep current): ");
        String priceInput = scan.nextLine().trim();
        int newPrice = priceInput.isEmpty() ? old.getProdprice() : Integer.parseInt(priceInput);

        System.out.print("Enter New Category (leave blank to keep current): ");
        String newCat = scan.nextLine().trim();
        if (newCat.isEmpty()) newCat = old.getProdcategory();

        printLine();
        System.out.print("Save updated details? (Y/N): ");
        String confirm = scan.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y")) {
            Product updated = new Product(productID, currentVendID, newName, newDesc, newPrice, old.getProdstatus(), newCat, old.getProdimg());
            if (DataAccess.updateProduct(updated)) {
                System.out.println("Product '" + productID + "' details successfully updated!");
            } else {
                System.out.println("Error updating product.");
            }
        } else {
            System.out.println("Update canceled.");
        }
        System.out.print("Press enter to return...");
        scan.nextLine();
    }

    static void vendorChangeProductStatus() {
        clearScreen();
        printLine();
        System.out.println("CHANGE PRODUCT STATUS");
        printLine();
        System.out.print("Enter Product ID to modify: ");
        String productID = scan.nextLine().trim();

        Product old = DataAccess.getProductById(productID);
        if (old == null || !old.getVendid().equals(currentVendID)) {
            System.out.println("Product not found or does not belong to your store.");
            System.out.print("Press enter to return...");
            scan.nextLine();
            return;
        }

        System.out.println("Current Product: " + old.getProdname() + " | Current Status: " + old.getProdstatus());
        printLine();
        System.out.println("Select New Status:");
        System.out.println("[1] Available");
        System.out.println("[2] Pre-Order");
        System.out.println("[3] Out-of-Stock");
        printLine();
        System.out.print("Enter choice [1-3]: ");
        String statusChoice = scan.nextLine().trim();

        String statusStr = switch (statusChoice) {
            case "1" -> "Available";
            case "2" -> "Pre-Order";
            case "3" -> "Out-of-Stock";
            default -> old.getProdstatus();
        };

        printLine();
        System.out.print("Confirm status change? (Y/N): ");
        String confirm = scan.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y")) {
            Product updated = new Product(
                    productID, currentVendID, old.getProdname(), old.getProddesc(),
                    old.getProdprice(), statusStr, old.getProdcategory(), old.getProdimg()
            );
            if (DataAccess.updateProduct(updated)) {
                System.out.println("Product " + productID + " status successfully updated to '" + statusStr + "'.");
            } else {
                System.out.println("Error updating status.");
            }
        } else {
            System.out.println("Status change canceled.");
        }
        System.out.print("Press enter to return to Product Management...");
        scan.nextLine();
    }

    static void printLine() {
        System.out.println("=======================================================================================================================");
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // ==================== ADMIN ====================
    static void adminMenu() {
        while (true) {
            System.out.println("\nADMIN DASHBOARD");
            System.out.println("[1] Log In");
            System.out.println("[2] View All Tourists");
            System.out.println("[3] View All Vendors");
            System.out.println("[4] Update Vendor Status");
            System.out.println("[5] View All Products");
            System.out.println("[6] Delete Tourist or Vendor Account");
            System.out.println("[7] Log Out");
            System.out.print("Select: ");
            String ch = scan.nextLine();

            switch (ch) {
                case "1" -> {
                    System.out.print("Username: ");
                    String u = scan.nextLine();
                    System.out.print("Password: ");
                    String p = scan.nextLine();
                    Admin a = DataAccess.loginAdmin(u, p);
                    if (a != null) {
                        currentAdmin = a.getAdminuser();
                        System.out.println("Welcome Admin " + a.getAdminname() + "!");
                    } else {
                        System.out.println("Wrong credentials.");
                    }
                }
                case "2" -> {
                    if (currentAdmin == null) {
                        System.out.println("Login first.");
                        break;
                    }
                    for (Tourist t : DataAccess.getAllTourists()) {
                        System.out.println(t.getTourid() + " | " + t.getTourname() + " | " + t.getTouruser());
                    }
                }
                case "3" -> {
                    if (currentAdmin == null) {
                        System.out.println("Login first.");
                        break;
                    }
                    for (Vendor v : DataAccess.getAllVendors()) {
                        System.out.println(v.getVendid() + " | " + v.getVendestabname() + " | " + v.getVendstatus());
                    }
                }
                case "4" -> {
                    if (currentAdmin == null) {
                        System.out.println("Login first.");
                        break;
                    }
                    System.out.print("Vendor ID: ");
                    String id = scan.nextLine();
                    System.out.print("New status: ");
                    String st = scan.nextLine();
                    if (DataAccess.updateVendorStatus(id, st)) System.out.println("Status updated.");
                }
                case "5" -> {
                    if (currentAdmin == null) {
                        System.out.println("Login first.");
                        break;
                    }
                    for (Product p : DataAccess.getProducts()) {
                        System.out.println(p.getProdid() + " | " + p.getProdname() + " | " + p.getProdstatus());
                    }
                }
                case "6" -> {
                    if (currentAdmin == null) {
                        System.out.println("Login first.");
                        break;
                    }
                    System.out.println("[1] Delete Tourist");
                    System.out.println("[2] Delete Vendor");
                    System.out.print("Select: ");
                    String type = scan.nextLine();

                    switch (type) {
                        case "1" -> {
                            System.out.print("Enter Tourist ID to delete: ");
                            String tid = scan.nextLine();
                            if (DataAccess.deleteTourist(tid)) {
                                System.out.println("Tourist account deleted successfully.");
                            } else {
                                System.out.println("Failed to delete tourist.");
                            }
                        }
                        case "2" -> {
                            System.out.print("Enter Vendor ID to delete: ");
                            String vid = scan.nextLine();
                            if (DataAccess.deleteVendor(vid)) {
                                System.out.println("Vendor deleted successfully.");
                            } else {
                                System.out.println("Failed to delete vendor.");
                            }
                        }
                        default -> System.out.println("Invalid choice.");
                    }
                }
                case "7" -> {
                    currentAdmin = null;
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid.");
            }
        }
    }
}
