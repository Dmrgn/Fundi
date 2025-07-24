package interface_adapter.news;

public class NewsState {
    private String[][] newsItems = new String[0][0];

    public String[][] getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(String[][] newsItems) {
        this.newsItems = newsItems;
    }
}
