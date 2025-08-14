package dataaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class TickerCache {
    private static final String CACHE_FILE = "data/ticker_cache.txt";
    private static final String FH_KEY_FILE = "data/FHkey.txt";
    private static Set<String> validTickers = null;

    private static void loadCache() {
        if (validTickers != null)
            return;

        validTickers = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CACHE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim().toUpperCase();
                if (!line.isEmpty()) {
                    validTickers.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Could not load ticker cache: " + e.getMessage());
            validTickers = new HashSet<>(); // Empty set to avoid null checks
        }
    }

    // Fetch US symbols from Finnhub and persist them to the cache file
    private static void refreshFromFinnhubIfEmpty() {
        if (validTickers != null && !validTickers.isEmpty())
            return;

        try {
            String apiKey = Files.readString(Path.of(FH_KEY_FILE)).trim();
            if (apiKey.isEmpty()) {
                System.err.println("Finnhub API key file is empty: " + FH_KEY_FILE);
                return;
            }

            OkHttpClient client = new OkHttpClient();
            String url = "https://finnhub.io/api/v1/stock/symbol?exchange=US&token=" + apiKey;
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.err.println("Finnhub symbol fetch failed: HTTP " + response.code());
                    return;
                }
                String body = response.body() != null ? response.body().string() : "[]";
                JSONArray arr = new JSONArray(body);

                Set<String> symbols = new HashSet<>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String sym = obj.optString("symbol", "").trim().toUpperCase();
                    if (!sym.isEmpty()) {
                        symbols.add(sym);
                    }
                }

                if (!symbols.isEmpty()) {
                    validTickers.addAll(symbols);

                    // Persist to cache file for future runs
                    Files.createDirectories(Path.of("data"));
                    try (BufferedWriter w = Files.newBufferedWriter(Path.of(CACHE_FILE), StandardCharsets.UTF_8)) {
                        for (String s : validTickers) {
                            w.write(s);
                            w.newLine();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Could not refresh ticker cache from Finnhub: " + e.getMessage());
        }
    }

    public static boolean isValidTicker(String ticker) {
        if (ticker == null || ticker.trim().isEmpty())
            return false;

        loadCache();
        return validTickers.contains(ticker.trim().toUpperCase());
    }

    public static void initialize() {
        loadCache();
        // If the file was empty or missing, hydrate from Finnhub
        if (validTickers.isEmpty()) {
            refreshFromFinnhubIfEmpty();
        }
        System.out.println("Ticker cache loaded with " + (validTickers != null ? validTickers.size() : 0) + " tickers.");
    }
}
