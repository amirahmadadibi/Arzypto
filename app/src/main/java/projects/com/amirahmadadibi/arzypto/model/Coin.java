package projects.com.amirahmadadibi.arzypto.model;

public class Coin {
    private String idName;
    private String coinSymbol;
    private String farsiName;
    private Double price;
    private Double priceInToman;
    private int coinResourceFileId;
    private boolean priceRaiseFlag;
    private Double priceGrowthPersentageInPastWeek;
    public Coin(String idName, Double price, boolean priceRaiseFlag, String coinSymbol, String farsiName, int coinResourceFileId, Double priceInToman) {
        this.idName = idName;
        this.price = price;
        this.priceRaiseFlag = priceRaiseFlag;
        this.coinSymbol = coinSymbol;
        this.farsiName = farsiName;
        this.coinResourceFileId = coinResourceFileId;
        this.priceInToman = priceInToman;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isPriceRaiseFlag() {
        return priceRaiseFlag;
    }

    public void setPriceRaiseFlag(boolean priceRaiseFlag) {
        this.priceRaiseFlag = priceRaiseFlag;
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

    public Double getPriceGrowthPersentageInPastWeek() {
        return priceGrowthPersentageInPastWeek;
    }

    public void setPriceGrowthPersentageInPastWeek(Double priceGrowthPersentageInPastWeek) {
        this.priceGrowthPersentageInPastWeek = priceGrowthPersentageInPastWeek;
    }
}
