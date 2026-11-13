public class Admin {
    private String adminid;
    private String adminname;
    private int admincpnum;
    private String adminemail;
    private String adminuser;
    private String adminpass;

    public Admin() {}

    public Admin(String adminid, String adminname, int admincpnum, String adminemail, String adminuser, String adminpass) {
        this.adminid = adminid;
        this.adminname = adminname;
        this.admincpnum = admincpnum;
        this.adminemail = adminemail;
        this.adminuser = adminuser;
        this.adminpass = adminpass;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public int getAdmincpnum() {
        return admincpnum;
    }

    public void setAdmincpnum(int admincpnum) {
        this.admincpnum = admincpnum;
    }

    public String getAdminemail() {
        return adminemail;
    }

    public void setAdminemail(String adminemail) {
        this.adminemail = adminemail;
    }

    public String getAdminuser() {
        return adminuser;
    }

    public void setAdminuser(String adminuser) {
        this.adminuser = adminuser;
    }

    public String getAdminpass() {
        return adminpass;
    }

    public void setAdminpass(String adminpass) {
        this.adminpass = adminpass;
    }
}