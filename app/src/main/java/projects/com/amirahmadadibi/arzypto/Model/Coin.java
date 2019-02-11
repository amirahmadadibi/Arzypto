package projects.com.amirahmadadibi.arzypto.Model;

public class Coin {
    private String name;
    private String coinSymbol;
    private String farsiName;
    private Double price;
    private Double priceInToman;
    private int coinResourceFileId;
    private boolean priceRaiseFlat;

    public Coin(String name, Double price, boolean priceRaiseFlat, String coinSymbol, String farsiName, int coinResourceFileId, Double priceInToman) {
        this.name = name;
        this.price = price;
        this.priceRaiseFlat = priceRaiseFlat;
        this.coinSymbol = coinSymbol;
        this.farsiName = farsiName;
        this.coinResourceFileId = coinResourceFileId;
        this.priceInToman = priceInToman;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isPriceRaiseFlat() {
        return priceRaiseFlat;
    }

    public void setPriceRaiseFlat(boolean priceRaiseFlat) {
        this.priceRaiseFlat = priceRaiseFlat;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    public String getFarsiName() {
        return farsiName;
    }

    public void setFarsiName(String farsiName) {
        this.farsiName = farsiName;
    }

    public int getCoinResourceFileId() {
        return coinResourceFileId;
    }

    public void setCoinResourceFileId(int coinResourceFileId) {
        this.coinResourceFileId = coinResourceFileId;
    }

    public Double getPriceInToman() {
        return priceInToman;
    }

    public void setPriceInToman(Double priceInToman) {
        this.priceInToman = priceInToman;
    }
}
