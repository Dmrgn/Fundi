package com.fundi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.*;

public class NewsAPITest {
    public static void main(String[] args) throws IOException {
        File file = new File("data/news_key.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String key = reader.readLine().trim();
            System.out.println(key);

            String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + key;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                System.out.println(response.body().string());
            }
        }
    }
}
