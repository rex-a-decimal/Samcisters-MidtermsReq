public class Vendor {
    private String vendid;
    private String vendestabname;
    private String vendownername;
    private String vendaddress;
    private int vendcpnum;
    private String vendemail;

    public Vendor(){}

    public Vendor(String vendid, String vendestabname, String vendownername, String vendaddress, int vendcpnum, String vendemail) {
        this.vendid = vendid;
        this.vendestabname = vendestabname;
        this.vendownername = vendownername;
        this.vendaddress = vendaddress;
        this.vendcpnum = vendcpnum;
        this.vendemail = vendemail;
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

    public int getVendcpnum() {
        return vendcpnum;
    }

    public void setVendcpnum(int vendcpnum) {
        this.vendcpnum = vendcpnum;
    }

    public String getVendemail() {
        return vendemail;
    }

    public void setVendemail(String vendemail) {
        this.vendemail = vendemail;
    }
}
