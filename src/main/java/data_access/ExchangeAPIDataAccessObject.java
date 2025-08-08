package data_access;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

import entity.CurrencyConverter;

public class ExchangeAPIDataAccessObject {

    public List<String> getSupportedCurrencies() {
        List<String> currencies = new ArrayList<>();

        try {
            String apiKey = Files.readString(Path.of("data/ExchangeRateAPI_key")).trim();
            URL url = new URL("https://v6.exchangerate-api.com/v6/" + apiKey + "/codes");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }

            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject json = new JSONObject(inline.toString());
            JSONArray supportedCodes = json.getJSONArray("supported_codes");

            for (int i = 0; i < supportedCodes.length(); i++) {
                JSONArray codePair = supportedCodes.getJSONArray(i);
                currencies.add(codePair.getString(0));
            }

        } catch (IOException e) {
            System.out.println("Failed to fetch currencies: " + e.getMessage());
        }

        return currencies;
    }
    public CurrencyConverter getConverter(String baseCurrency) {
        try {

            String apiKey = Files.readString(Path.of("data/ExchangeRateAPI_key")).trim();

            URL url = new URL("https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }

            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject json = new JSONObject(inline.toString());
            JSONObject rates = json.getJSONObject("conversion_rates");

            Map<String, Double> rateMap = new HashMap<>();
            for (String currency : rates.keySet()) {
                rateMap.put(currency, rates.getDouble(currency));
            }

            return new CurrencyConverter(rateMap, baseCurrency);

        } catch (IOException e) {
            System.out.println("Failed to fetch conversion rates: " + e.getMessage());
            return null;
        }
    }
}