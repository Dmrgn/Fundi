package use_case.news;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.portfolio.PortfolioTransactionDataAccessInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NewsInteractor implements NewsInputBoundary {
    private final NewsOutputBoundary newsPresenter;
    private final PortfolioTransactionDataAccessInterface portfolioDataAccess;

    // temporary placeholder
    private final String[] DEFAULT_COMPANIES = {"Google", "Apple", "Microsoft", "Nvidia", "OpenAI"};

    public NewsInteractor(NewsOutputBoundary newsPresenter,
                         PortfolioTransactionDataAccessInterface portfolioDataAccess) {
        this.newsPresenter = newsPresenter;
        this.portfolioDataAccess = portfolioDataAccess;
    }

    @Override
    public void execute(NewsInputData inputData) {
        try {
            // Get API key
            File file = new File("data/news_key.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String apiKey = reader.readLine().trim();

            List<String[]> allNews = new ArrayList<>();
            OkHttpClient client = new OkHttpClient();
              for (String company : DEFAULT_COMPANIES) {
                String url = String.format(
                    "https://newsapi.org/v2/everything?q=%s&apiKey=%s&pageSize=3&language=en&sortBy=publishedAt",
                    company, apiKey
                );

                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                String jsonData = response.body().string();

                JSONObject json = new JSONObject(jsonData);
                JSONArray articles = json.getJSONArray("articles");

                // Add company name as header before its news
                allNews.add(new String[]{
                    "Latest News for " + company,
                    "-----------------------------------"
                });

                for (int i = 0; i < articles.length(); i++) {
                    JSONObject article = articles.getJSONObject(i);
                    String[] newsItem = new String[]{
                        article.getString("title"),
                        article.getString("description")
                    };
                    allNews.add(newsItem);
                }

                // Add spacing between companies
                allNews.add(new String[]{"", ""});
            }
            
            // Convert to array and present
            String[][] newsArray = allNews.toArray(new String[0][]);
            NewsOutputData outputData = new NewsOutputData(newsArray);
            newsPresenter.prepareView(outputData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}