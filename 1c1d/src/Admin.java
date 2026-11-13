public class Admin {
    private int adminid;
    private String adminname;
    private int admincpnum;
    private String adminemail;
    private String adminuser;
    private String adminpass;

    public Admin() {
        adminid = 215;
        adminname = "Tony Starky";
        admincpnum = 915181960;
        adminemail = "tnystrk.ironman@gmail.com";
        adminuser = "tsimironaman";
        adminpass = "Point-Break";
    }

    public Admin(int adminid, String adminname, int admincpnum, String adminemail, String adminuser, String adminpass) {
        this.adminid = adminid;
        this.adminname = adminname;
        this.admincpnum = admincpnum;
        this.adminemail = adminemail;
        this.adminuser = adminuser;
        this.adminpass = adminpass;
    }
}
