package projects.com.amirahmadadibi.arzypto.Model;

public class Coin {
    private String name;
    private Double price;
    private boolean priceRaiseFlat;
    public Coin(String name, Double price, boolean priceRaiseFlat) {
        this.name = name;
        this.price = price;
        this.priceRaiseFlat = priceRaiseFlat;
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
}
