package interfaceadapter.analysis;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The state for the Analysis View Model.
 */
public class AnalysisState {
    private int numTickers;
    private Map<String, Double> majorityTickers;
    private double volatility;
    private Map<String, Double> mostVolTickers;
    private Map<String, Double> leastVolTickers;
    private double pastReturn;
    private Map<String, Double> topReturns;
    private Map<String, Double> worstReturns;

    public AnalysisState(AnalysisState copy) {
        this.numTickers = copy.numTickers;
        this.majorityTickers = copy.majorityTickers;
        this.volatility = copy.volatility;
        this.mostVolTickers = copy.mostVolTickers;
        this.leastVolTickers = copy.leastVolTickers;
        this.pastReturn = copy.pastReturn;
        this.topReturns = copy.topReturns;
        this.worstReturns = copy.worstReturns;
    }

    public AnalysisState() {

    }

    /**
     * Getter.
     * @return Majority Tickers
     */
    public Map<String, Double> getMajorityTickers() {
        return new LinkedHashMap<>(majorityTickers);
    }

    /**
     * Setter.
     * @param majorityTickers Value
     */
    public void setMajorityTickers(Map<String, Double> majorityTickers) {
        this.majorityTickers = new LinkedHashMap<>(majorityTickers);
    }

    /**
     * Getter.
     * @return Num Tickers
     */
    public int getNumTickers() {
        return numTickers;
    }

    /**
     * Setter.
     * @param numTickers Value
     */
    public void setNumTickers(int numTickers) {
        this.numTickers = numTickers;
    }

    /**
     * Getter.
     * @return Volatility
     */
    public double getVolatility() {
        return volatility;
    }

    /**
     * Setter.
     * @param volatility Value
     */
    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }

    /**
     * Getter.
     * @return Most Volatile
     */
    public Map<String, Double> getMostVolTickers() {
        return new LinkedHashMap<>(mostVolTickers);
    }

    /**
     * Setter.
     * @param mostVolTickers Value
     */
    public void setMostVolTickers(Map<String, Double> mostVolTickers) {
        this.mostVolTickers = new LinkedHashMap<>(mostVolTickers);
    }

    /**
     * Getter.
     * @return Least Volatile
     */
    public Map<String, Double> getLeastVolTickers() {
        return new LinkedHashMap<>(leastVolTickers);
    }

    /**
     * Setter.
     * @param leastVolTickers Value
     */
    public void setLeastVolTickers(Map<String, Double> leastVolTickers) {
        this.leastVolTickers = new LinkedHashMap<>(leastVolTickers);
    }

    /**
     * Getter.
     * @return Past Return
     */
    public double getPastReturn() {
        return pastReturn;
    }

    /**
     * Setter.
     * @param pastReturn Value
     */
    public void setPastReturn(double pastReturn) {
        this.pastReturn = pastReturn;
    }

    /**
     * Getter.
     * @return Top Returns
     */
    public Map<String, Double> getTopReturns() {
        return new LinkedHashMap<>(topReturns);
    }

    /**
     * Setter.
     * @param topReturns Value
     */
    public void setTopReturns(Map<String, Double> topReturns) {
        this.topReturns = new LinkedHashMap<>(topReturns);
    }

    /**
     * Getter.
     * @return Worst Returns
     */
    public Map<String, Double> getWorstReturns() {
        return new LinkedHashMap<>(worstReturns);
    }

    /**
     * Setter.
     * @param worstReturns Value
     */
    public void setWorstReturns(Map<String, Double> worstReturns) {
        this.worstReturns = new LinkedHashMap<>(worstReturns);
    }
}

