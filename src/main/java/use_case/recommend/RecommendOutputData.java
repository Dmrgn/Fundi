package use_case.recommend;

import java.util.Map;

/**
 * Recommend Output Data
 */
public class RecommendOutputData {
    private final Map<String, Double> haveRecs;
    private final Map<String, Double> notHaveRecs;
    private final Map<String, Double> safeRecs;


    public RecommendOutputData(Map<String, Double> haveRecs, Map<String, Double> notHaveRecs, Map<String, Double> safeRecs) {
        this.haveRecs = haveRecs;
        this.notHaveRecs = notHaveRecs;
        this.safeRecs = safeRecs;
    }

    public Map<String, Double> getHaveRecs() {
        return haveRecs;
    }

    public Map<String, Double> getNotHaveRecs() {
        return notHaveRecs;
    }

    public Map<String, Double> getSafeRecs() {
        return safeRecs;
    }
}
