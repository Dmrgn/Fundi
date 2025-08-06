package use_case.history;

import java.time.LocalDate;

/**
 * Output Data for the History Use Case.
 */
public class HistoryOutputData {
    private final String[] tickers;
    private final double[] prices;
    private final int[] amounts;
    private final LocalDate[] dates;

    public HistoryOutputData(String[] tickers, double[] prices, int[] amounts, LocalDate[] dates) {
        this.tickers = tickers;
        this.prices = prices;
        this.amounts = amounts;
        this.dates = dates;
    }

    /**
     * Getter.
     * @return Tickers
     */
    public String[] getTickers() {
        return tickers;
    }

    /**
     * Getter.
     * @return Prices
     */
    public double[] getPrices() {
        return prices;
    }

    /**
     * Getter.
     * @return Amounts
     */
    public int[] getAmounts() {
        return amounts;
    }

    /**
     * Getter.
     * @return Dates
     */
    public LocalDate[] getDates() {
        return dates;
    }
}
