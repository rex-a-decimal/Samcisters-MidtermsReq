public class Payment {
    private String payid;
    private double payamount;
    private String paymode;
    private String paystatus;
    private String paydatepaid;

    public Payment() {}

    public Payment(String payid, double payamount, String paymode, String paystatus, String paydatepaid) {
        this.payid = payid;
        this.payamount = payamount;
        this.paymode = paymode;
        this.paystatus = paystatus;
        this.paydatepaid = paydatepaid;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public double getPayamount() {
        return payamount;
    }

    public void setPayamount(double payamount) {
        this.payamount = payamount;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public String getPaydatepaid() {
        return paydatepaid;
    }

    public void setPaydatepaid(String paydatepaid) {
        this.paydatepaid = paydatepaid;
    }
}