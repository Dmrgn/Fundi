package entity;

import java.time.LocalDate;

/**
 * Represents a portfolio's value at a specific point in time.
 */
public class PortfolioValuePoint {
    private final String portfolioId;
    private final String portfolioName;
    private final LocalDate date;
    private final double value;

    public PortfolioValuePoint(String portfolioId, String portfolioName, LocalDate date, double value) {
        this.portfolioId = portfolioId;
        this.portfolioName = portfolioName;
        this.date = date;
        this.value = value;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }
}
