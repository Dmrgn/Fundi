package entity;

import dataaccess.ExchangeAPIDataAccessObject;

public class PreferredCurrencyManager {

    private static String preferredCurrency = "USD";
    private static CurrencyConverter converter;

    public static void setPreferredCurrency(String newCurrency, CurrencyConverter newConverter) {
        preferredCurrency = newCurrency;
        converter = newConverter;
    }

    public static String getPreferredCurrency() {
        return preferredCurrency;
    }

    public static CurrencyConverter getConverter() {
        if (converter == null) {
            final ExchangeAPIDataAccessObject exchangeAPI = new ExchangeAPIDataAccessObject();
            converter = exchangeAPI.getConverter(preferredCurrency);
        }
        return converter;
    }
}
