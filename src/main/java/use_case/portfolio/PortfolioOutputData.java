package use_case.portfolio;

/**
 * Output Data for the Portfolio Use Case.
 */
public class PortfolioOutputData {

    private final String username;
    private final String portfolioId;
    private final String portfolioName;
    private final String[] stockNames;
    private final int[] stockAmounts;
    private final double[] stockPrices;

    public PortfolioOutputData(String username, String portfolioId, String portfolioName, String[] stockNames, int[] stockAmounts, double[] stockPrices) {
        this.username = username;
        this.portfolioId = portfolioId;
        this.portfolioName = portfolioName;
        this.stockNames = stockNames;
        this.stockAmounts = stockAmounts;
        this.stockPrices = stockPrices;
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

    public String getPortfolioId() {
        return portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public String getUsername() {
        return username;
    }
}