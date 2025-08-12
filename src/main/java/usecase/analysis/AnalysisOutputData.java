package usecase.analysis;

import java.util.Map;

/**
 * The Output Data for the Analysis Use Case.
 */
public class AnalysisOutputData {
    private final int numTickers;
    private final Map<String, Double> majorityTickers;
    private final double volatility;
    private final Map<String, Double> mostVolTickers;
    private final Map<String, Double> leastVolTickers;
    private final double pastReturn;
    private final Map<String, Double> topReturns;
    private final Map<String, Double> worstReturns;

    public AnalysisOutputData(int numTickers, Map<String, Double> majorityTickers, double volatility,
                              Map<String, Double> mostVolTickers, Map<String, Double> leastVolTickers,
                              double pastReturn, Map<String, Double> topReturns, Map<String, Double> worstReturns) {
        this.numTickers = numTickers;
        this.majorityTickers = majorityTickers;
        this.volatility = volatility;
        this.mostVolTickers = mostVolTickers;
        this.leastVolTickers = leastVolTickers;
        this.pastReturn = pastReturn;
        this.topReturns = topReturns;
        this.worstReturns = worstReturns;
    }

    /**
     * Getter.
     * @return Num Tickers
     */
    public int getNumTickers() {
        return numTickers;
    }

    /**
     * Getter.
     * @return Majority Tickers
     */
    public Map<String, Double> getMajorityTickers() {
        return majorityTickers;
    }

    /**
     * Getter.
     * @return Volatility
     */
    public double getVolatility() {
        return volatility;
    }

    /**
     * Getter.
     * @return Least Volatile
     */
    public Map<String, Double> getLeastVolTickers() {
        return leastVolTickers;
    }

    /**
     * Getter.
     * @return Most Volatile
     */
    public Map<String, Double> getMostVolTickers() {
        return mostVolTickers;
    }

    /**
     * Getter.
     * @return Past Return
     */
    public double getPastReturn() {
        return pastReturn;
    }

    /**
     * Getter.
     * @return Top Returns
     */
    public Map<String, Double> getTopReturns() {
        return topReturns;
    }

    /**
     * Getter.
     * @return Worst Returns
     */
    public Map<String, Double> getWorstReturns() {
        return worstReturns;
    }
}
