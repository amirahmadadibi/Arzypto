package projects.com.amirahmadadibi.arzypto.Model;

public class Coin {
    private String name;
    private String coinSymbol;
    private String farsiName;
    private Double price;
    private Double priceInToman;
    private int coinResourceFileId;
    private boolean priceRaiseFlag;
    private double marketCapUsd;
    private double volumeUsd24Hr;
    private double changePercent24Hr;

    public Coin(String name, Double price, boolean priceRaiseFlag, String coinSymbol, String farsiName, int coinResourceFileId, Double priceInToman) {
        this.name = name;
        this.price = price;
        this.priceRaiseFlag = priceRaiseFlag;
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

    public double getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(double marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public double getVolumeUsd24Hr() {
        return volumeUsd24Hr;
    }

    public void setVolumeUsd24Hr(double volumeUsd24Hr) {
        this.volumeUsd24Hr = volumeUsd24Hr;
    }

    public double getChangePercent24Hr() {
        return changePercent24Hr;
    }

    public void setChangePercent24Hr(double changePercent24Hr) {
        this.changePercent24Hr = changePercent24Hr;
    }
}
