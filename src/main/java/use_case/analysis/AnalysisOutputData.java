package use_case.analysis;

import java.util.Map;

/**
 * The Output Data for the Analysis Use Case
 */
public class AnalysisOutputData {
    int numTickers;
    Map<String, Double> majorityTickers;
    double volatility;
    Map<String, Double> mostVolTickers;
    Map<String, Double> leastVolTickers;
    double pastReturn;
    Map<String, Double> topReturns;
    Map<String, Double> worstReturns;

    public AnalysisOutputData(int numTickers, Map<String, Double> majorityTickers, double volatility, Map<String, Double> mostVolTickers, Map<String, Double> leastVolTickers, double pastReturn, Map<String, Double> topReturns, Map<String, Double> worstReturns) {
        this.numTickers = numTickers;
        this.majorityTickers = majorityTickers;
        this.volatility = volatility;
        this.mostVolTickers = mostVolTickers;
        this.leastVolTickers = leastVolTickers;
        this.pastReturn = pastReturn;
        this.topReturns = topReturns;
        this.worstReturns = worstReturns;
    }

    public int getNumTickers() {
        return numTickers;
    }

    public Map<String, Double> getMajorityTickers() {
        return majorityTickers;
    }

    public double getVolatility() {
        return volatility;
    }

    public Map<String, Double> getLeastVolTickers() {
        return leastVolTickers;
    }

    public Map<String, Double> getMostVolTickers() {
        return mostVolTickers;
    }

    public double getPastReturn() {
        return pastReturn;
    }

    public Map<String, Double> getTopReturns() {
        return topReturns;
    }

    public Map<String, Double> getWorstReturns() {
        return worstReturns;
    }
}
