package use_case.news;

public class NewsOutputData {
    private final String[][] newsItems;

    public NewsOutputData(String[][] newsItems) {
        this.newsItems = newsItems;
    }

    public String[][] getNewsItems() {
        return newsItems;
    }  
}
