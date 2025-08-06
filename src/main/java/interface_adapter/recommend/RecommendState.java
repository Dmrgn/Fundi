package interface_adapter.recommend;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The state for the Recommend View Model.
 */
public class RecommendState {
    private Map<String, Double> haveRecs;
    private Map<String, Double> notHaveRecs;
    private Map<String, Double> safeRecs;

    public RecommendState(RecommendState clone) {
        this.haveRecs = new LinkedHashMap<>(clone.haveRecs);
        this.notHaveRecs = new LinkedHashMap<>(clone.notHaveRecs);
        this.safeRecs = new LinkedHashMap<>(clone.safeRecs);
    }

    public RecommendState() {

    }

    /**
     * Getter.
     * @return Have Recs
     */
    public Map<String, Double> getHaveRecs() {
        return new LinkedHashMap<>(haveRecs);
    }

    /**
     * Setter.
     * @param haveRecs Value
     */
    public void setHaveRecs(Map<String, Double> haveRecs) {
        this.haveRecs = new LinkedHashMap<>(haveRecs);
    }

    /**
     * Getter.
     * @return Not Have Recs
     */
    public Map<String, Double> getNotHaveRecs() {
        return new LinkedHashMap<>(notHaveRecs);
    }

    /**
     * Setter.
     * @param notHaveRecs Value
     */
    public void setNotHaveRecs(Map<String, Double> notHaveRecs) {
        this.notHaveRecs = new LinkedHashMap<>(notHaveRecs);
    }

    /**
     * Getter.
     * @return Safe Recs
     */
    public Map<String, Double> getSafeRecs() {
        return new LinkedHashMap<>(safeRecs);
    }

    /**
     * Setter.
     * @param safeRecs Value
     */
    public void setSafeRecs(Map<String, Double> safeRecs) {
        this.safeRecs = new LinkedHashMap<>(safeRecs);
    }
}
