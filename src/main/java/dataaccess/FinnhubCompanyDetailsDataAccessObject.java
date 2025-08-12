package dataaccess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.CompanyDetails;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import usecase.company_details.CompanyDetailsDataAccessInterface;

/**
 * FinnHub-based implementation of CompanyDetailsDataAccessInterface.
 */
public class FinnhubCompanyDetailsDataAccessObject implements CompanyDetailsDataAccessInterface {

    private static final String PROFILE_API_URL = "https://finnhub.io/api/v1/stock/profile2";
    private static final String METRICS_API_URL = "https://finnhub.io/api/v1/stock/metric";
    private static final String PEERS_API_URL = "https://finnhub.io/api/v1/stock/peers";
    private static final String QUOTE_API_URL = "https://finnhub.io/api/v1/quote";
    private static final int NUMBER_FIVE = 5;
    private static final int NUMBER_TEN = 10;
    private final OkHttpClient httpClient;
    private final String apiKey;

    public FinnhubCompanyDetailsDataAccessObject() throws IOException {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(NUMBER_FIVE, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(NUMBER_TEN, java.util.concurrent.TimeUnit.SECONDS)
                .build();
        // Read API key from FHkey.txt
        this.apiKey = Files.readString(Path.of("data/FHkey.txt")).trim();
    }

    @Override
    public CompanyDetails getCompanyDetails(String symbol) throws Exception {
        // Fetch essential data - profile, current price, metrics, and peers
        JSONObject profile = fetchCompanyProfile(symbol);
        double currentPrice = fetchCurrentPrice(symbol);
        JSONObject metrics = fetchBasicFinancials(symbol);
        List<String> peers = fetchCompanyPeers(symbol);

        // Parse profile data
        String name = profile.optString("name", "N/A");
        String country = profile.optString("country", "N/A");
        String currency = profile.optString("currency", "N/A");
        String exchange = profile.optString("exchange", "N/A");
        String ipoDate = profile.optString("ipo", "N/A");
        double marketCap = profile.optDouble("marketCapitalization", 0.0);
        String phone = profile.optString("phone", "N/A");
        double shareOutstanding = profile.optDouble("shareOutstanding", 0.0);
        String webUrl = profile.optString("weburl", "N/A");
        String logo = profile.optString("logo", "");
        String industry = profile.optString("finnhubIndustry", "N/A");

        // Parse metrics data
        JSONObject metricObj = metrics.optJSONObject("metric");
        double peRatio = metricObj != null ? metricObj.optDouble("peBasicExclExtraTTM", 0.0) : 0.0;
        double week52High = metricObj != null ? metricObj.optDouble("52WeekHigh", 0.0) : 0.0;
        double week52Low = metricObj != null ? metricObj.optDouble("52WeekLow", 0.0) : 0.0;
        double beta = metricObj != null ? metricObj.optDouble("beta", 0.0) : 0.0;

        return new CompanyDetails(
                symbol, name, country, currency, exchange, ipoDate, marketCap,
                phone, shareOutstanding, webUrl, logo, industry, currentPrice, peRatio,
                week52High, week52Low, beta, peers);
    }

    private JSONObject fetchCompanyProfile(String symbol) throws Exception {
        String url = String.format("%s?symbol=%s&token=%s", PROFILE_API_URL, symbol, apiKey);
        return makeApiCall(url);
    }

    private JSONObject fetchBasicFinancials(String symbol) throws Exception {
        String url = String.format("%s?symbol=%s&metric=all&token=%s", METRICS_API_URL, symbol, apiKey);
        return makeApiCall(url);
    }

    private double fetchCurrentPrice(String symbol) throws Exception {
        String url = String.format("%s?symbol=%s&token=%s", QUOTE_API_URL, symbol, apiKey);
        JSONObject quote = makeApiCall(url);
        // 'c' is current price in Finnhub API
        return quote.optDouble("c", 0.0);
    }

    private List<String> fetchCompanyPeers(String symbol) throws Exception {
        String url = String.format("%s?symbol=%s&token=%s", PEERS_API_URL, symbol, apiKey);
        JSONArray peersArray = makeApiCallArray(url);

        List<String> peers = new ArrayList<>();
        // Limit to first 5 peers
        int limit = Math.min(NUMBER_FIVE, peersArray.length());
        for (int i = 0; i < limit; i++) {
            peers.add(peersArray.getString(i));
        }

        return peers;
    }

    private JSONObject makeApiCall(String url) throws Exception {
        Request request = new Request.Builder().url(url).build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API request failed: " + response.code());
            }

            String responseBody = response.body().string();
            return new JSONObject(responseBody);
        }
    }

    private JSONArray makeApiCallArray(String url) throws Exception {
        Request request = new Request.Builder().url(url).build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API request failed: " + response.code());
            }

            String responseBody = response.body().string();
            return new JSONArray(responseBody);
        }
    }
}
