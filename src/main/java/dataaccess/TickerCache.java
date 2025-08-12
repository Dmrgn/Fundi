package dataaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TickerCache {
    private static final String CACHE_FILE = "data/ticker_cache.txt";
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

    public static boolean isValidTicker(String ticker) {
        if (ticker == null || ticker.trim().isEmpty())
            return false;

        loadCache();
        return validTickers.contains(ticker.trim().toUpperCase());
    }

    public static void initialize() {
        loadCache();
        System.out
                .println("Ticker cache loaded with " + (validTickers != null ? validTickers.size() : 0) + " tickers.");
    }
}