import java.time.LocalDate;

public class Payment {
    private String payid;
    private int payamount;
    private String paymode;
    private String paystatus;
    private String paydatepaid;

    public Payment() {
        payid = "548-118-222";
        payamount = 578;
        paymode = "card";
        paystatus = "paid";
        paydatepaid = "2025-10-24";
    }

    public Payment(String payid, int payamount, String paymode, String paystatus, String paydatepaid) {
        this.payid = payid;
        this.payamount = payamount;
        this.paymode = paymode;
        this.paystatus = paystatus;
        this.paydatepaid = paydatepaid;
    }
}