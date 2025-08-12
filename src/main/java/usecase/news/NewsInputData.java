package usecase.news;

public class NewsInputData {
    private final String input; // Can be a username or a search query
    private final boolean isSearch;

    public NewsInputData(String input, boolean isSearch) {
        this.input = input;
        this.isSearch = isSearch;
    }

    public String getInput() {
        return input;
    }

    public boolean isSearch() {
        return isSearch;
    }
}
