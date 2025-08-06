package entity;

import java.util.List;

/**
 * The representation of detailed company information from the stock API.
 */
public class CompanyDetails {

    private final String symbol;
    private final String name;
    private final String country;
    private final String currency;
    private final String exchange;
    private final String ipoDate;
    private final double marketCapitalization;
    private final String phone;
    private final double shareOutstanding;
    private final String webUrl;
    private final String logo;
    private final String finnhubIndustry;

    // Financial metrics
    private final double currentPrice;
    private final double peRatio;
    private final double week52High;
    private final double week52Low;
    private final double beta;

    // News feature removed
    private final List<String> peers;

    public CompanyDetails(String symbol, String name, String country, String currency,
            String exchange, String ipoDate, double marketCapitalization,
            String phone, double shareOutstanding, String webUrl,
            String logo, String finnhubIndustry, double currentPrice, double peRatio,
            double week52High, double week52Low, double beta,
            List<String> peers) {
        this.symbol = symbol;
        this.name = name;
        this.country = country;
        this.currency = currency;
        this.exchange = exchange;
        this.ipoDate = ipoDate;
        this.marketCapitalization = marketCapitalization;
        this.phone = phone;
        this.shareOutstanding = shareOutstanding;
        this.webUrl = webUrl;
        this.logo = logo;
        this.finnhubIndustry = finnhubIndustry;
        this.currentPrice = currentPrice;
        this.peRatio = peRatio;
        this.week52High = week52High;
        this.week52Low = week52Low;
        this.beta = beta;
        // News feature removed
        this.peers = peers;
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public String getExchange() {
        return exchange;
    }

    public String getIpoDate() {
        return ipoDate;
    }

    public double getMarketCapitalization() {
        return marketCapitalization;
    }

    public String getPhone() {
        return phone;
    }

    public double getShareOutstanding() {
        return shareOutstanding;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getLogo() {
        return logo;
    }

    public String getFinnhubIndustry() {
        return finnhubIndustry;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getPeRatio() {
        return peRatio;
    }

    public double getWeek52High() {
        return week52High;
    }

    public double getWeek52Low() {
        return week52Low;
    }

    public double getBeta() {
        return beta;
    }

    // News feature removed

    public List<String> getPeers() {
        return peers;
    }
}
