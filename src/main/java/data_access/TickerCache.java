package data_access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Cache for ticker symbols from Finnhub API to enable fast ticker validation
 */
public class TickerCache {
    private static final String CACHE_FILE = "data/ticker_cache.txt";
    private static final Set<String> tickerSet = new HashSet<>();
    private static String apiKey;

    /**
     * Initialize the ticker cache by loading from file and starting background
     * refresh
     */
    public static void initialize() {
        try {
            // Read API key
            apiKey = Files.readString(Path.of("data/FHkey.txt")).trim();

            // Try to load from existing cache file first
            try {
                loadFromFile();
                System.out.println("Ticker cache loaded from file with " + tickerSet.size() + " symbols");
            } catch (Exception e) {
                System.out.println("No existing cache file found, will fetch from API in background");
            }

            // Start background refresh - doesn't block startup
            CompletableFuture.runAsync(() -> {
                try {
                    fetchTickersFromAPI();
                    saveToFile();
                    System.out.println(
                            "Ticker cache refreshed in background with " + tickerSet.size() + " symbols from API");
                } catch (Exception e) {
                    System.err.println("Background ticker cache refresh failed: " + e.getMessage());
                }
            });

        } catch (Exception e) {
            System.err.println("Failed to initialize ticker cache: " + e.getMessage());
            // Initialize with empty set as last resort
            tickerSet.clear();
        }
    }

    /**
     * Fetch tickers from Finnhub API for US exchange using simple HTTP
     */
    private static void fetchTickersFromAPI() throws Exception {
        tickerSet.clear();

        // Use your working fetcher approach
        @SuppressWarnings("deprecation")
        URL url = new URL("https://finnhub.io/api/v1/stock/symbol?exchange=US&token=" + apiKey);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String response = reader.lines().reduce("", String::concat);

            // Parse symbols using your working logic
            int index = 0;
            while ((index = response.indexOf("\"symbol\":\"", index)) != -1) {
                index += 10; // Skip past "symbol":"
                int endIndex = response.indexOf("\"", index);
                if (endIndex != -1) {
                    String symbol = response.substring(index, endIndex);

                    // Filter out symbols with periods (ADRs, etc.) and only keep 1-5 letter symbols
                    if (!symbol.contains(".") && symbol.matches("^[A-Z]{1,5}$")) {
                        tickerSet.add(symbol);
                    }
                }
                index = endIndex;
            }
        }
    }

    /**
     * Save ticker set to cache file
     */
    private static void saveToFile() throws IOException {
        Path cacheFilePath = Paths.get(CACHE_FILE);

        // Create data directory if it doesn't exist
        Files.createDirectories(cacheFilePath.getParent());

        // Convert set to string and save
        StringBuilder sb = new StringBuilder();
        for (String ticker : tickerSet) {
            sb.append(ticker).append("\n");
        }

        Files.writeString(cacheFilePath, sb.toString());
    }

    /**
     * Load ticker set from cache file
     */
    private static void loadFromFile() throws IOException {
        Path cacheFilePath = Paths.get(CACHE_FILE);

        if (!Files.exists(cacheFilePath)) {
            throw new IOException("Cache file does not exist: " + CACHE_FILE);
        }

        tickerSet.clear();
        tickerSet.addAll(Files.readAllLines(cacheFilePath));
    }

    /**
     * Check if a ticker symbol is valid (exists in our cache)
     */
    public static boolean isValidTicker(String ticker) {
        return tickerSet.contains(ticker.toUpperCase());
    }

    /**
     * Get the total number of cached tickers
     */
    public static int getCacheSize() {
        return tickerSet.size();
    }

    /**
     * Force refresh the cache from API
     */
    public static void refresh() {
        initialize();
    }
}
