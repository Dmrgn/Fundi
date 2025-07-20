package interface_adapter.portfolio;

/**
 * The state for the Portfolio View Model.
 */
public class PortfolioState {
    private String portfolioName = "";
    private String portfolioId = "";
    private String[] stockNames = new String[0];
    private int[] stockAmounts = new int[0];
    private double[] stockPrices = new double[0];

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String[] getStockNames() {
        return stockNames;
    }

    public void setStockNames(String[] stockNames) {
        this.stockNames = stockNames;
    }

    public int[] getStockAmounts() {
        return stockAmounts;
    }

    public void setStockAmounts(int[] stockAmounts) {
        this.stockAmounts = stockAmounts;
    }

    public double[] getStockPrices() {
        return stockPrices;
    }

    public void setStockPrices(double[] stockPrices) {
        this.stockPrices = stockPrices;
    }
}