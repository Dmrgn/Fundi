package use_case.recommend;

import java.util.List;

public class RecommendOutputData {
    List<String> recommendations;

    public RecommendOutputData(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }
}
