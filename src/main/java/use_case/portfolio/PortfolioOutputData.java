package use_case.portfolio;

/**
 * Output Data for the Portfolio Use Case.
 */
public class PortfolioOutputData {

    private final String username;
    private final String[] stockNames;
    private final int[] stockAmounts;
    private final double[] stockPrices;

    public PortfolioOutputData(String username, String[] stockNames, int[] stockAmounts, double[] stockPrices) {
        this.username = username;
        this.stockNames = stockNames;
        this.stockAmounts = stockAmounts;
        this.stockPrices = stockPrices;
    }

    public String getUsername() {
        return username;
    }

    public String[] getStockNames() {
        return stockNames;
    }

    public int[] getStockAmounts() {
        return stockAmounts;
    }

    public double[] getStockPrices() {
        return stockPrices;
    }
}