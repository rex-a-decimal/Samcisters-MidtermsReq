package backend;

public class Product {
    private String prodid;
    private String vendid;
    private String prodname;
    private String proddesc;
    private int prodprice;
    private String prodstatus;
    private String prodcategory;
    private String prodimg;

    public Product() {}

    public Product(String prodid, String vendid, String prodname, String proddesc, int prodprice, String prodstatus, String prodcategory, String prodimg) {
        this.prodid = prodid;
        this.vendid = vendid;
        this.prodname = prodname;
        this.proddesc = proddesc;
        this.prodprice = prodprice;
        this.prodstatus = prodstatus;
        this.prodcategory = prodcategory;
        this.prodimg = prodimg;
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

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    public String getProddesc() {
        return proddesc;
    }

    public void setProddesc(String proddesc) {
        this.proddesc = proddesc;
    }

    public int getProdprice() {
        return prodprice;
    }

    public void setProdprice(int prodprice) {
        this.prodprice = prodprice;
    }

    public String getProdstatus() {
        return prodstatus;
    }

    public void setProdstatus(String prodstatus) {
        this.prodstatus = prodstatus;
    }

    public String getProdcategory() {
        return prodcategory;
    }

    public void setProdcategory(String prodcategory) {
        this.prodcategory = prodcategory;
    }

    public String getProdimg() {
        return prodimg;
    }

    public void setProdimg(String prodimg) {
        this.prodimg = prodimg;
    }
}