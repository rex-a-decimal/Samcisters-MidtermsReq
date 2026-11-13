public class Sales {
    private String salesid;
    private String tourid;
    private String payid;
    private String salesdate;
    private String salesstatus;
    private double salestotalamt;
    private int salespointsearned;
    private double salesdiscountamt;
    private String salespickupdate;
    private String salespickuptime;
    private String salespickupplace;

    public Sales() {}

    public Sales(String salesid, String tourid, String payid, String salesdate, String salesstatus, double salestotalamt, int salespointsearned, double salesdiscountamt, String salespickupdate, String salespickuptime, String salespickupplace) {
        this.salesid = salesid;
        this.tourid = tourid;
        this.payid = payid;
        this.salesdate = salesdate;
        this.salesstatus = salesstatus;
        this.salestotalamt = salestotalamt;
        this.salespointsearned = salespointsearned;
        this.salesdiscountamt = salesdiscountamt;
        this.salespickupdate = salespickupdate;
        this.salespickuptime = salespickuptime;
        this.salespickupplace = salespickupplace;
    }

    public String getSalesid() {

        return salesid;
    }

    public void setSalesid(String salesid) {
        this.salesid = salesid;
    }

    public String getTourid() {
        return tourid;
    }

    public void setTourid(String tourid) {
        this.tourid = tourid;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getSalesdate() {
        return salesdate;
    }

    public void setSalesdate(String salesdate) {
        this.salesdate = salesdate;
    }

    public String getSalesstatus() {
        return salesstatus;
    }

    public void setSalesstatus(String salesstatus) {
        this.salesstatus = salesstatus;
    }

    public double getSalestotalamt() {
        return salestotalamt;
    }

    public void setSalestotalamt(double salestotalamt) {
        this.salestotalamt = salestotalamt;
    }

    public int getSalespointsearned() {
        return salespointsearned;
    }

    public void setSalespointsearned(int salespointsearned) {
        this.salespointsearned = salespointsearned;
    }

    public double getSalesdiscountamt() {
        return salesdiscountamt;
    }

    public void setSalesdiscountamt(double salesdiscountamt) {
        this.salesdiscountamt = salesdiscountamt;
    }

    public String getSalespickupdate() {
        return salespickupdate;
    }

    public void setSalespickupdate(String salespickupdate) {
        this.salespickupdate = salespickupdate;
    }

    public String getSalespickuptime() {
        return salespickuptime;
    }

    public void setSalespickuptime(String salespickuptime) {
        this.salespickuptime = salespickuptime;
    }

    public String getSalespickupplace() {
        return salespickupplace;
    }

    public void setSalespickupplace(String salespickupplace) {
        this.salespickupplace = salespickupplace;
    }
}