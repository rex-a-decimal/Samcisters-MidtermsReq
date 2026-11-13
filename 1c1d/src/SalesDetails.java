public class SalesDetails {
    private String salesid;
    private String prodid;
    private String vendid;
    private int sdqty;
    private double sdunitprice;
    private double sdsubtotal;

    public SalesDetails(){}

    public SalesDetails(String salesid, String prodid, String vendid, int sdqty, double sdunitprice, double sdsubtotal) {
        this.salesid = salesid;
        this.prodid = prodid;
        this.vendid = vendid;
        this.sdqty = sdqty;
        this.sdunitprice = sdunitprice;
        this.sdsubtotal = sdsubtotal;
    }

    public String getSalesid() {
        return salesid;
    }

    public void setSalesid(String salesid) {
        this.salesid = salesid;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getVendid() {
        return vendid;
    }

    public void setVendid(String vendid) {
        this.vendid = vendid;
    }

    public int getSdqty() {
        return sdqty;
    }

    public void setSdqty(int sdqty) {
        this.sdqty = sdqty;
    }

    public double getSdunitprice() {
        return sdunitprice;
    }

    public void setSdunitprice(double sdunitprice) {
        this.sdunitprice = sdunitprice;
    }

    public double getSdsubtotal() {
        return sdsubtotal;
    }

    public void setSdsubtotal(double sdsubtotal) {
        this.sdsubtotal = sdsubtotal;
    }
}
