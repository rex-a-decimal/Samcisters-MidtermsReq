package backend;

public class Voucher {
    private int voucherid;
    private String voucherdesc;
    private int voucherdiscount;
    private int vouchervalue;
    private int voucherpointsreq;
    private String vouchervalidfrom;
    private String vouchervalidto;

    public Voucher(){}

    public Voucher(int voucherid, String voucherdesc, int voucherdiscount, int vouchervalue, int voucherpointsreq, String vouchervalidfrom, String vouchervalidto) {
        this.voucherid = voucherid;
        this.voucherdesc = voucherdesc;
        this.voucherdiscount = voucherdiscount;
        this.vouchervalue = vouchervalue;
        this.voucherpointsreq = voucherpointsreq;
        this.vouchervalidfrom = vouchervalidfrom;
        this.vouchervalidto = vouchervalidto;
    }

    public int getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(int voucherid) {
        this.voucherid = voucherid;
    }

    public String getVoucherdesc() {
        return voucherdesc;
    }

    public void setVoucherdesc(String voucherdesc) {
        this.voucherdesc = voucherdesc;
    }

    public int getVoucherdiscount() {
        return voucherdiscount;
    }

    public void setVoucherdiscount(int voucherdiscount) {
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
