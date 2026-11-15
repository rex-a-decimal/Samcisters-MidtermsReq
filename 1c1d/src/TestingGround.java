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
        } else {
            System.out.print("Name: ");
            String name = scan.nextLine();
            System.out.print("Address: ");
            String addr = scan.nextLine();
            System.out.print("Phone: ");
            int phone = Integer.parseInt(scan.nextLine());
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
        ArrayList<Voucher> list = DataAccess.getVouchers();
        for (Voucher v : list) {
            System.out.println(v.getVoucherid() + " | " + v.getVoucherdesc() + " | pts: " + v.getVoucherpointsreq());
        }
        System.out.print("Voucher ID to claim (0 to cancel): ");
        String vid = scan.nextLine();
        if (vid.equals("0")) return;
        if (DataAccess.claimVoucher(currentTourID, vid)) {
            System.out.println("Voucher claimed!");
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
    static void vendorMenu() {
        while (true) {
            System.out.println("\nVENDOR DASHBOARD");
            System.out.println("[1] Log In");
            System.out.println("[2] View My Products");
            System.out.println("[3] Add Product");
            System.out.println("[4] Update Product");
            System.out.println("[5] Delete Product");
            System.out.println("[6] Archive Product");
            System.out.println("[7] Log Out");
            System.out.print("Select: ");
            String ch = scan.nextLine();

            switch (ch) {
                case "1" -> {
                    System.out.print("Username: ");
                    String u = scan.nextLine();
                    System.out.print("Password: ");
                    String p = scan.nextLine();
                    Vendor v = DataAccess.loginVendor(u, p);
                    if (v != null) {
                        currentVendID = v.getVendid();
                        System.out.println("Welcome " + v.getVendestabname() + "!");
                    } else {
                        System.out.println("Wrong credentials.");
                    }
                }
                case "2" -> {
                    if (currentVendID == null) { System.out.println("Login first."); break; }
                    for (Product p : DataAccess.getProductsByVendor(currentVendID)) {
                        System.out.println(p.getProdid() + " | " + p.getProdname() + " | P" + p.getProdprice() + " | " + p.getProdstatus());
                    }
                }
                case "3" -> {
                    if (currentVendID == null) { System.out.println("Login first."); break; }
                    System.out.print("Product ID: ");
                    String id = scan.nextLine();
                    System.out.print("Name: ");
                    String name = scan.nextLine();
                    System.out.print("Description: ");
                    String desc = scan.nextLine();
                    System.out.print("Price: ");
                    int price = Integer.parseInt(scan.nextLine());
                    System.out.print("Status: ");
                    String status = scan.nextLine();
                    System.out.print("Category: ");
                    String cat = scan.nextLine();

                    System.out.print("Save record to database? (Y/N): ");
                    String confirm = scan.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        Product p = new Product(id, currentVendID, name, desc, price, status, cat, null);
                        if (DataAccess.addProduct(p)) System.out.println("Product added!");
                    } else {
                        System.out.println("Product addition canceled.");
                    }
                }
                case "4" -> {
                    if (currentVendID == null) { System.out.println("Login first."); break; }
                    System.out.print("Product ID: ");
                    String id = scan.nextLine();
                    Product old = DataAccess.getProductById(id);
                        if (old == null || !old.getVendid().equals(currentVendID)) {
                            System.out.println("Not found or not yours.");
                            break;
                        }
                    System.out.print("New name: ");
                    String name = scan.nextLine();
                    if (name.isEmpty()) name = old.getProdname();
                    System.out.print("New price: ");
                    String pr = scan.nextLine();
                    int price = pr.isEmpty() ? old.getProdprice() : Integer.parseInt(pr);
                    System.out.print("New status: ");
                    String status = scan.nextLine();
                    if (status.isEmpty()) status = old.getProdstatus();
                    Product p = new Product(id, currentVendID, name, old.getProddesc(), price, status, old.getProdcategory(), old.getProdimg());
                    if (DataAccess.updateProduct(p)) System.out.println("Updated!");
                }
                case "5" -> {
                    if (currentVendID == null) { System.out.println("Login first."); break; }
                    System.out.print("Product ID to delete: ");
                    String id = scan.nextLine();
                    Product old = DataAccess.getProductById(id);
                    if (old == null || !old.getVendid().equals(currentVendID)) {
                        System.out.println("Not found or not yours.");
                        break;
                    }
                    System.out.print("Are you sure you want to PERMANENTLY DELETE this product? (Y/N): ");
                    String confirm = scan.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        if (DataAccess.deleteProduct(id)) System.out.println("Deleted.");
                    } else {
                        System.out.println("Deletion canceled.");
                    }
                }
                case "6" -> {
                    if (currentVendID == null) { System.out.println("Login first."); break; }
                    System.out.print("Product ID to archive: ");
                    String id = scan.nextLine();
                    System.out.print("Are you sure you want to archive this product? (Y/N): ");
                    String confirm = scan.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        if (DataAccess.archiveProduct(id, currentVendID)) {
                            System.out.println("Archived successfully.");
                        } else {
                            System.out.println("Failed to archive.");
                        }
                    } else {
                        System.out.println("Archival canceled.");
                    }
                }
                case "7" -> {
                    currentVendID = null;
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid.");
            }
        }
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
                    if (currentAdmin == null) { System.out.println("Login first."); break; }
                    for (Tourist t : DataAccess.getAllTourists()) {
                        System.out.println(t.getTourid() + " | " + t.getTourname() + " | " + t.getTouruser());
                    }
                }
                case "3" -> {
                    if (currentAdmin == null) { System.out.println("Login first."); break; }
                    for (Vendor v : DataAccess.getAllVendors()) {
                        System.out.println(v.getVendid() + " | " + v.getVendestabname() + " | " + v.getVendstatus());
                    }
                }
                case "4" -> {
                    if (currentAdmin == null) { System.out.println("Login first."); break; }
                    System.out.print("Vendor ID: ");
                    String id = scan.nextLine();
                    System.out.print("New status: ");
                    String st = scan.nextLine();
                    if (DataAccess.updateVendorStatus(id, st)) System.out.println("Status updated.");
                }
                case "5" -> {
                    if (currentAdmin == null) { System.out.println("Login first."); break; }
                    for (Product p : DataAccess.getProducts()) {
                        System.out.println(p.getProdid() + " | " + p.getProdname() + " | " + p.getProdstatus());
                    }
                }
                case "6" -> {
                    if (currentAdmin == null) { System.out.println("Login first."); break; }
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
