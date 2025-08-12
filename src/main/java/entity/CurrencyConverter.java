package entity;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
    private final Map<String, Double> rates;
    private final String baseCurrency;

    public CurrencyConverter(Map<String, Double> rates, String baseCurrency) {
        this.rates = new HashMap<>(rates);
        this.baseCurrency = baseCurrency;
    }

    /**
     * Convert an amount from one currency to another using rates relative to the base currency.
     *
     * @param amount        amount expressed in {@code fromCurrency}
     * @param fromCurrency  source currency (e.g., "USD")
     * @param toCurrency    target currency (e.g., "EUR")
     * @return the amount expressed in toCurrency
     * @throws IllegalArgumentException if either currency is unsupported
     */

    public double convert(double amount, String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }
        double baseAmount = amount;
        if (!fromCurrency.equals(baseCurrency)) {
            Double fromRate = rates.get(fromCurrency);
            if (fromRate == null || fromRate == 0.0) {
                throw new IllegalArgumentException("Unsupported fromCurrency: " + fromCurrency);
            }
            baseAmount = amount / fromRate;
        }

        Double toRate = rates.get(toCurrency);
        if (toRate == null || toRate == 0.0) {
            throw new IllegalArgumentException("Unsupported toCurrency: " + toCurrency);
        }

        return baseAmount * toRate;
    }
}
