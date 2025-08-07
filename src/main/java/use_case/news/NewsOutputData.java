package use_case.news;

public class NewsOutputData {
    private final String[][] newsItems; // [title, summary, url]

    public NewsOutputData(String[][] newsItems) {
        this.newsItems = newsItems;
    }

    public String[][] getNewsItems() {
        return newsItems;
    }  
}
