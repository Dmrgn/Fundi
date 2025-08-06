package interface_adapter.portfolio;

/**
 * The state for the Portfolio View Model.
 */
public class PortfolioState {
    private String username = "";
    private String portfolioId = "";
    private String portfolioName = "";
    private String[] stockNames = new String[0];
    private int[] stockAmounts = new int[0];
    private double[] stockPrices = new double[0];

    /**
     * Getter.
     * @return Portfolio Id
     */
    public String getPortfolioId() {
        return portfolioId;
    }

    /**
     * Setter.
     * @param portfolioId Value
     */
    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    /**
     * Getter.
     * @return Portfolio Name
     */
    public String getPortfolioName() {
        return portfolioName;
    }

    /**
     * Setter.
     * @param portfolioName Value
     */
    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }


    /**
     * Getter.
     * @return Stock Names
     */
    public String[] getStockNames() {
        return stockNames;
    }

    /**
     * Setter.
     * @param stockNames Value
     */
    public void setStockNames(String[] stockNames) {
        if (stockNames != null) {
            this.stockNames = stockNames.clone();
        }

        else {
            this.stockNames = new String[0];
        }
    }

    /**
     * Getter.
     * @return Stock Amounts
     */
    public int[] getStockAmounts() {
        return stockAmounts;
    }

    /**
     * Setter.
     * @param stockAmounts Value
     */
    public void setStockAmounts(int[] stockAmounts) {
        if (stockAmounts != null) {
            this.stockAmounts = stockAmounts.clone();
        }

        else {
            this.stockAmounts = new int[0];
        }
    }

    /**
     * Getter.
     * @return Stock Prices
     */
    public double[] getStockPrices() {
        return stockPrices;
    }

    /**
     * Setter.
     * @param stockPrices Value
     */
    public void setStockPrices(double[] stockPrices) {
        if (stockPrices != null) {
            this.stockPrices = stockPrices.clone();
        } else {
            this.stockPrices = new double[0];
        }
    }

    /**
     * Getter.
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter.
     * @param username Value
     */
    public void setUsername(String username) {
        this.username = username;
    }
}