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

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }


    public String[] getStockNames() {
        return stockNames;
    }

    public void setStockNames(String[] stockNames) {
        if (stockNames != null) {
            this.stockNames = stockNames.clone();
        } else {
            this.stockNames = new String[0];
        }
    }

    public int[] getStockAmounts() {
        return stockAmounts;
    }

    public void setStockAmounts(int[] stockAmounts) {
        if (stockAmounts != null) {
            this.stockAmounts = stockAmounts.clone();
        } else {
            this.stockAmounts = new int[0];
        }
    }

    public double[] getStockPrices() {
        return stockPrices;
    }

    public void setStockPrices(double[] stockPrices) {
        if (stockPrices != null) {
            this.stockPrices = stockPrices.clone();
        } else {
            this.stockPrices = new double[0];
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}