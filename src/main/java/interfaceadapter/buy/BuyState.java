package interfaceadapter.buy;

/**
 * The state for the Buy View Model.
 */
public class BuyState {
    private String portfolioId = "";
    private String buyError;
    private double balance;

    public BuyState(BuyState copy) {
        this.portfolioId = copy.getPortfolioId();
        this.buyError = copy.getBuyError();
        this.balance = copy.getBalance();
    }

    public BuyState() {

    }

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
     * @return Buy Error
     */
    public String getBuyError() {
        return buyError;
    }

    /**
     * Setter.
     * @param buyError Value
     */
    public void setBuyError(String buyError) {
        this.buyError = buyError;
    }

    /**
     * Getter.
     * @return Balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Setter.
     * @param balance Value
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
