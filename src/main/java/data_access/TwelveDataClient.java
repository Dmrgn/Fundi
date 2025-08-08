package data_access;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TwelveDataClient {
    private final OkHttpClient http = new OkHttpClient();
    private String apiKey;

    private String sanitize(String s) {
        if (s == null)
            return "";
        // Remove BOM and whitespace
        s = s.replace("\uFEFF", "").replace("\u200B", "");
        return s.trim();
    }

    public synchronized String getApiKey() {
        if (apiKey != null)
            return apiKey;

        // 1) Try env vars
        String env = System.getenv("TWELVEDATA_API_KEY");
        if (env == null || env.isBlank())
            env = System.getenv("TD_API_KEY");
        if (env != null && !env.isBlank()) {
            apiKey = sanitize(env);
            System.out.println("DEBUG TD: loaded API key from ENV, length=" + apiKey.length());
            return apiKey;
        }

        // 2) Try common file names
        String[] candidates = new String[] {
                "data/TD_key.txt",
                "data/twelvedata_key.txt",
                "data/TwelveData_key.txt"
        };
        for (String p : candidates) {
            try {
                Path keyPath = Path.of(p);
                if (Files.exists(keyPath)) {
                    String raw = Files.readString(keyPath);
                    apiKey = sanitize(raw);
                    System.out.println("DEBUG TD: loaded API key from file " + keyPath.toAbsolutePath() + ", length="
                            + apiKey.length());
                    if (!apiKey.isBlank())
                        return apiKey;
                }
            } catch (IOException ignored) {
            }
        }

        apiKey = "";
        return apiKey;
    }

    public String fetchTimeSeriesJson(String symbol, String interval, int outputsize) throws IOException {
        String key = getApiKey();
        if (key == null || key.isBlank()) {
            throw new IOException("Missing TwelveData API key");
        }
        String encSym = URLEncoder.encode(symbol, StandardCharsets.UTF_8);
        String encInt = URLEncoder.encode(interval, StandardCharsets.UTF_8);
        String encKey = URLEncoder.encode(key, StandardCharsets.UTF_8);

        String urlApikey = String.format(
                "https://api.twelvedata.com/time_series?symbol=%s&interval=%s&outputsize=%d&apikey=%s",
                encSym, encInt, outputsize, encKey);
        String urlApiUnderscore = String.format(
                "https://api.twelvedata.com/time_series?symbol=%s&interval=%s&outputsize=%d&api_key=%s",
                encSym, encInt, outputsize, encKey);

        String body1 = doGet(urlApikey);
        if (bodyIndicatesKeyOk(body1))
            return body1;

        String body2 = doGet(urlApiUnderscore);
        return body2;
    }

    private String doGet(String url) throws IOException {
        Request req = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .get()
                .build();
        try (Response resp = http.newCall(req).execute()) {
            String body = resp.body() != null ? resp.body().string() : "";
            System.out.println("DEBUG TD: GET " + url.replaceAll("(api_key=|apikey=)[^&]+", "$1***") +
                    " -> code=" + resp.code());
            if (!resp.isSuccessful() && (body == null || body.isBlank())) {
                throw new IOException("HTTP " + resp.code());
            }
            return body;
        }
    }

    private boolean bodyIndicatesKeyOk(String body) {
        if (body == null || body.isBlank())
            return false;
        String lc = body.toLowerCase();
        if (lc.contains("api key parameter is incorrect") || lc.contains("invalid api key")
                || lc.contains("not specified")) {
            return false;
        }
        return lc.contains("\"values\"") || lc.contains("\"meta\"");
    }
}
