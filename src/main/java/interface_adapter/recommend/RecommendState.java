package interface_adapter.recommend;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The state for the Recommend View Model
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

    public Map<String, Double> getHaveRecs() {
        return new LinkedHashMap<>(haveRecs);
    }

    public void setHaveRecs(Map<String, Double> haveRecs) {
        this.haveRecs = new LinkedHashMap<>(haveRecs);
    }

    public Map<String, Double> getNotHaveRecs() {
        return new LinkedHashMap<>(notHaveRecs);
    }

    public void setNotHaveRecs(Map<String, Double> notHaveRecs) {
        this.notHaveRecs = new LinkedHashMap<>(notHaveRecs);
    }

    public Map<String, Double> getSafeRecs() {
        return new LinkedHashMap<>(safeRecs);
    }

    public void setSafeRecs(Map<String, Double> safeRecs) {
        this.safeRecs = new LinkedHashMap<>(safeRecs);
    }
}
