package interface_adapter.analysis;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The state for the Analysis View Model
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

    public Map<String, Double> getMajorityTickers() {
        return new LinkedHashMap<>(majorityTickers);
    }

    public void setMajorityTickers(Map<String, Double> majorityTickers) {
        this.majorityTickers = new LinkedHashMap<>(majorityTickers);
    }

    public int getNumTickers() {
        return numTickers;
    }

    public void setNumTickers(int numTickers) {
        this.numTickers = numTickers;
    }

    public double getVolatility() {
        return volatility;
    }

    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }

    public Map<String, Double> getMostVolTickers() {
        return new LinkedHashMap<>(mostVolTickers);
    }

    public void setMostVolTickers(Map<String, Double> mostVolTickers) {
        this.mostVolTickers = new LinkedHashMap<>(mostVolTickers);
    }

    public Map<String, Double> getLeastVolTickers() {
        return new LinkedHashMap<>(leastVolTickers);
    }

    public void setLeastVolTickers(Map<String, Double> leastVolTickers) {
        this.leastVolTickers = new LinkedHashMap<>(leastVolTickers);
    }

    public double getPastReturn() {
        return pastReturn;
    }

    public void setPastReturn(double pastReturn) {
        this.pastReturn = pastReturn;
    }

    public Map<String, Double> getTopReturns() {
        return new LinkedHashMap<>(topReturns);
    }

    public void setTopReturns(Map<String, Double> topReturns) {
        this.topReturns = new LinkedHashMap<>(topReturns);
    }

    public Map<String, Double> getWorstReturns() {
        return new LinkedHashMap<>(worstReturns);
    }

    public void setWorstReturns(Map<String, Double> worstReturns) {
        this.worstReturns = new LinkedHashMap<>(worstReturns);
    }
}

