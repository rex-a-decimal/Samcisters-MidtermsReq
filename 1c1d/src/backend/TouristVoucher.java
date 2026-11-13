package backend;

public class TouristVoucher {
    private int tourid;
    private String voucherid;
    private String tvdateredeemed;
    private String tvstatus;

    public TouristVoucher() {}

    public TouristVoucher(int tourid, String voucherid, String tvdateredeemed, String tvstatus) {
        this.tourid = tourid;
        this.voucherid = voucherid;
        this.tvdateredeemed = tvdateredeemed;
        this.tvstatus = tvstatus;
    }

    public int getTourid() {
        return tourid;
    }

    public void setTourid(int tourid) {
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
