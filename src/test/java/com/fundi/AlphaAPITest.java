package com.fundi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.*;

public class AlphaAPITest {
    public static void main(String[] args) throws IOException {
        File file = new File("data/alpha_key.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String key = reader.readLine().trim();
            System.out.println(key);

            String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey="
                    + key;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                System.out.println(response.body().string());
            }
        }
    }
}
