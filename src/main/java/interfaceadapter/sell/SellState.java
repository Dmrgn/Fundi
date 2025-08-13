package interfaceadapter.sell;

/**
 * The state for the Sell View Model.
 */
public class SellState {
    private String portfolioId = "";
    private String sellError;
    private double balance;

    public SellState(SellState copy) {
        this.portfolioId = copy.getPortfolioId();
        this.sellError = copy.getSellError();
        this.balance = copy.getBalance();
    }

    public SellState() {

    }

    /**
     * Getter.
     * 
     * @return Portfolio Id
     */
    public String getPortfolioId() {
        return portfolioId;
    }

    /**
     * Setter.
     * 
     * @param portfolioId Value
     */
    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    /**
     * Getter.
     * 
     * @return Sell Error
     */
    public String getSellError() {
        return sellError;
    }

    /**
     * Setter.
     * 
     * @param sellError Value
     */
    public void setSellError(String sellError) {
        this.sellError = sellError;
    }

    /**
     * Getter.
     * 
     * @return Balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Setter.
     * 
     * @param balance Value
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
