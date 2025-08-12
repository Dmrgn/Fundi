package interfaceadapter.news;

import interfaceadapter.ViewModel;

public class NewsViewModel extends ViewModel<NewsState> {
    public NewsViewModel() {
        super("news");
        setState(new NewsState());
    }
}
