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

    /**
     * Getter.
     * @return Have Recs
     */
    public Map<String, Double> getHaveRecs() {
        return haveRecs;
    }

    /**
     * Getter.
     * @return Not Have Recs
     */
    public Map<String, Double> getNotHaveRecs() {
        return notHaveRecs;
    }

    /**
     * Getter.
     * @return Safe Recs.
     */
    public Map<String, Double> getSafeRecs() {
        return safeRecs;
    }
}
