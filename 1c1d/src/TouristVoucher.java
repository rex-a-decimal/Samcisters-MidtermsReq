public class TouristVoucher {
    private String tourid;
    private String voucherid;
    private String tvdateredeemed;
    private String tvstatus;

    public TouristVoucher() {}

    public TouristVoucher(String tourid, String voucherid, String tvdateredeemed, String tvstatus) {
        this.tourid = tourid;
        this.voucherid = voucherid;
        this.tvdateredeemed = tvdateredeemed;
        this.tvstatus = tvstatus;
    }

    public String getTourid() {
        return tourid;
    }

    public void setTourid(String tourid) {
        this.tourid = tourid;
    }

    public String getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(String voucherid) {
        this.voucherid = voucherid;
    }

    public String getTvdateredeemed() {
        return tvdateredeemed;
    }

    public void setTvdateredeemed(String tvdateredeemed) {
        this.tvdateredeemed = tvdateredeemed;
    }

    public String getTvstatus() {
        return tvstatus;
    }

    public void setTvstatus(String tvstatus) {
        this.tvstatus = tvstatus;
    }
}
