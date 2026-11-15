public class Vendor {
    private String vendid;
    private String vendestabname;
    private String vendownername;
    private String vendaddress;
    private String vendcpnum;
    private String vendemail;
    private String vendstatus;
    private String vendpass;
    private String venduser;

    public Vendor() {}

    public Vendor(String vendid, String vendestabname, String vendownername,
                  String vendaddress, String vendcpnum, String vendemail,
                  String vendstatus, String vendpass, String venduser) {
        this.vendid = vendid;
        this.vendestabname = vendestabname;
        this.vendownername = vendownername;
        this.vendaddress = vendaddress;
        this.vendcpnum = vendcpnum;
        this.vendemail = vendemail;
        this.vendstatus = vendstatus;
        this.vendpass = vendpass;
        this.venduser = venduser;
    }

    public String getVendid() {
        return vendid;
    }

    public void setVendid(String vendid) {
        this.vendid = vendid;
    }

    public String getVendestabname() {
        return vendestabname;
    }

    public void setVendestabname(String vendestabname) {
        this.vendestabname = vendestabname;
    }

    public String getVendownername() {
        return vendownername;
    }

    public void setVendownername(String vendownername) {
        this.vendownername = vendownername;
    }

    public String getVendaddress() {
        return vendaddress;
    }

    public void setVendaddress(String vendaddress) {
        this.vendaddress = vendaddress;
    }

    public String getVendcpnum() {
        return vendcpnum;
    }

    public void setVendcpnum(String vendcpnum) {
        this.vendcpnum = vendcpnum;
    }

    public String getVendemail() {
        return vendemail;
    }

    public void setVendemail(String vendemail) {
        this.vendemail = vendemail;
    }

    public String getVendstatus() {
        return vendstatus;
    }

    public void setVendstatus(String vendstatus) {
        this.vendstatus = vendstatus;
    }

    public String getVendpass() {
        return vendpass;
    }

    public void setVendpass(String vendpass) {
        this.vendpass = vendpass;
    }

    public String getVenduser() {
        return venduser;
    }

    public void setVenduser(String venduser) {
        this.venduser = venduser;
    }
}
