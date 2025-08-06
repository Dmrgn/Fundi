package interface_adapter.history;

import java.time.LocalDate;

/**
 * State for the History View Model.
 */
public class HistoryState {
    private String[] tickers;
    private double[] prices;
    private int[] amounts;
    private LocalDate[] dates;

    public HistoryState(HistoryState copy) {
        this.tickers = copy.tickers;
        this.prices = copy.prices;
        this.amounts = copy.amounts;
        this.dates = copy.dates;
    }

    public HistoryState() {

    }

    /**
     * Getter.
     * @return Tickers
     */
    public String[] getTickers() {
        return tickers.clone();
    }

    /**
     * Setter.
     * @param tickers Value
     */
    public void setTickers(String[] tickers) {
        this.tickers = tickers.clone();
    }

    /**
     * Getter.
     * @return Prices
     */
    public double[] getPrices() {
        return prices.clone();
    }

    /**
     * Setter.
     * @param prices Value
     */
    public void setPrices(double[] prices) {
        this.prices = prices.clone();
    }

    /**
     * Getter.
     * @return Amounts
     */
    public int[] getAmounts() {
        return amounts.clone();
    }

    /**
     * Setter.
     * @param amounts Value
     */
    public void setAmounts(int[] amounts) {
        this.amounts = amounts.clone();
    }

    /**
     * Getter.
     * @return Dates
     */
    public LocalDate[] getDates() {
        return dates.clone();
    }

    /**
     * Setter.
     * @param dates Value
     */
    public void setDates(LocalDate[] dates) {
        this.dates = dates.clone();
    }

}
