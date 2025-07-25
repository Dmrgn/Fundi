package interface_adapter.analysis;

import java.util.HashMap;
import java.util.Map;

public class AnalysisState {
    int numTickers;
    Map<String, Double> majorityTickers;
    double volatility;
    Map<String, Double> mostVolTickers;
    Map<String, Double> leastVolTickers;
    double pastReturn;
    Map<String, Double> topReturns;
    Map<String, Double> worstReturns;

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
        return new HashMap<>(majorityTickers);
    }

    public void setMajorityTickers(Map<String, Double> majorityTickers) {
        this.majorityTickers = new HashMap<>(majorityTickers);
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
        return new HashMap<>(mostVolTickers);
    }

    public void setMostVolTickers(Map<String, Double> mostVolTickers) {
        this.mostVolTickers = new HashMap<>(mostVolTickers);
    }

    public Map<String, Double> getLeastVolTickers() {
        return new HashMap<>(leastVolTickers);
    }

    public void setLeastVolTickers(Map<String, Double> leastVolTickers) {
        this.leastVolTickers = new HashMap<>(leastVolTickers);
    }

    public double getPastReturn() {
        return pastReturn;
    }

    public void setPastReturn(double pastReturn) {
        this.pastReturn = pastReturn;
    }

    public Map<String, Double> getTopReturns() {
        return new HashMap<>(topReturns);
    }

    public void setTopReturns(Map<String, Double> topReturns) {
        this.topReturns = new HashMap<>(topReturns);
    }

    public Map<String, Double> getWorstReturns() {
        return new HashMap<>(worstReturns);
    }

    public void setWorstReturns(Map<String, Double> worstReturns) {
        this.worstReturns = new HashMap<>(worstReturns);
    }
}

