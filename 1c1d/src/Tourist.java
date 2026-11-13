public class Tourist {
    private String tourid;
    private String tourname;
    private String touraddress;
    private int tourcpnum;
    private String touremail;
    private String touruser;
    private String tourpass;
    private String tourloyalty;

    public Tourist(){}

    public Tourist(String tourid, String tourname, String touraddress, int tourcpnum, String touremail, String touruser, String tourpass, String tourloyalty) {
        this.tourid = tourid;
        this.tourname = tourname;
        this.touraddress = touraddress;
        this.tourcpnum = tourcpnum;
        this.touremail = touremail;
        this.touruser = touruser;
        this.tourpass = tourpass;
        this.tourloyalty = tourloyalty;
    }

    public String getTourid() {
        return tourid;
    }

    public void setTourid(String tourid) {
        this.tourid = tourid;
    }

    public String getTourname() {
        return tourname;
    }

    public void setTourname(String tourname) {
        this.tourname = tourname;
    }

    public String getTouraddress() {
        return touraddress;
    }

    public void setTouraddress(String touraddress) {
        this.touraddress = touraddress;
    }

    public int getTourcpnum() {
        return tourcpnum;
    }

    public void setTourcpnum(int tourcpnum) {
        this.tourcpnum = tourcpnum;
    }

    public String getTouremail() {
        return touremail;
    }

    public void setTouremail(String touremail) {
        this.touremail = touremail;
    }

    public String getTouruser() {
        return touruser;
    }

    public void setTouruser(String touruser) {
        this.touruser = touruser;
    }

    public String getTourpass() {
        return tourpass;
    }

    public void setTourpass(String tourpass) {
        this.tourpass = tourpass;
    }

    public String getTourloyalty() {
        return tourloyalty;
    }

    public void setTourloyalty(String tourloyalty) {
        this.tourloyalty = tourloyalty;
    }
}
