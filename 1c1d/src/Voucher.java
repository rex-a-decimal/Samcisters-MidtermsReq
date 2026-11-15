public class Voucher {
    private String voucherid;
    private String voucherdesc;
    private double voucherdiscount;
    private int vouchervalue;
    private int voucherpointsreq;
    private String vouchervalidfrom;
    private String vouchervalidto;

    public Voucher(){}

    public Voucher(String voucherid, String voucherdesc, double voucherdiscount, int vouchervalue, int voucherpointsreq, String vouchervalidfrom, String vouchervalidto) {
        this.voucherid = voucherid;
        this.voucherdesc = voucherdesc;
        this.voucherdiscount = voucherdiscount;
        this.vouchervalue = vouchervalue;
        this.voucherpointsreq = voucherpointsreq;
        this.vouchervalidfrom = vouchervalidfrom;
        this.vouchervalidto = vouchervalidto;
    }

    public String getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(String voucherid) {
        this.voucherid = voucherid;
    }

    public String getVoucherdesc() {
        return voucherdesc;
    }

    public void setVoucherdesc(String voucherdesc) {
        this.voucherdesc = voucherdesc;
    }

    public double getVoucherdiscount() {
        return voucherdiscount;
    }

    public void setVoucherdiscount(double voucherdiscount) {
        this.voucherdiscount = voucherdiscount;
    }

    public int getVouchervalue() {
        return vouchervalue;
    }

    public void setVouchervalue(int vouchervalue) {
        this.vouchervalue = vouchervalue;
    }

    public int getVoucherpointsreq() {
        return voucherpointsreq;
    }

    public void setVoucherpointsreq(int voucherpointsreq) {
        this.voucherpointsreq = voucherpointsreq;
    }

    public String getVouchervalidfrom() {
        return vouchervalidfrom;
    }

    public void setVouchervalidfrom(String vouchervalidfrom) {
        this.vouchervalidfrom = vouchervalidfrom;
    }

    public String getVouchervalidto() {
        return vouchervalidto;
    }

    public void setVouchervalidto(String vouchervalidto) {
        this.vouchervalidto = vouchervalidto;
    }
}
