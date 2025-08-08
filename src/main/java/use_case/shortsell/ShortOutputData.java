package use_case.shortsell;

public class ShortOutputData {
    private final String ticker;
    private final double price;
    private final int amount;

    public ShortOutputData(String ticker, double price, int amount) {
        this.ticker = ticker;
        this.price = price;
        this.amount = amount;
    }

    public String getTicker() { return ticker; }
    public double getPrice() { return price; }
    public int getAmount() { return amount; }
}
