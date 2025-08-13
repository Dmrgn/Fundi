package usecase.portfolio;

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
    private final double balance;

    public PortfolioOutputData(String username, String portfolioId, String portfolioName, String[] stockNames, int[] stockAmounts, double[] stockPrices, double balance) {
        this.username = username;
        this.portfolioId = portfolioId;
        this.portfolioName = portfolioName;
        this.stockNames = stockNames;
        this.stockAmounts = stockAmounts;
        this.stockPrices = stockPrices;
        this.balance = balance;
    }

    /**
     * Getter.
     * @return Stock Names
     */
    public String[] getStockNames() {
        return stockNames;
    }

    /**
     * Getter.
     * @return Stock Amounts
     */
    public int[] getStockAmounts() {
        return stockAmounts;
    }

    /**
     * Getter.
     * @return Stock Prices
     */
    public double[] getStockPrices() {
        return stockPrices;
    }

    /**
     * Getter.
     * @return Portfolio Id
     */
    public String getPortfolioId() {
        return portfolioId;
    }

    /**
     * Getter.
     * @return Portfolio Name
     */
    public String getPortfolioName() {
        return portfolioName;
    }

    /**
     * Getter.
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter.
     * @return Balance
     */
    public double getBalance() {
        return balance;
    }
}