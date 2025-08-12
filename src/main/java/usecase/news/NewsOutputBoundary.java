package usecase.news;

public interface NewsOutputBoundary {
    /**
     * Prepares the view with news data
     * @param newsOutputData the news data to be presented
     */
    void prepareView(NewsOutputData newsOutputData);
}