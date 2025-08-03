package entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinancialCalculatorTest {
    @Test
    void testExpectedReturn() {
        double[] prices = {100, 105, 110};
        double expected = ((110 - 100) / 100.0) * 100;
        ArrayList<StockData> stockData = new ArrayList<>();
        for (double price : prices) {
            StockData tempData = new StockData("blah", LocalDate.now(), price);
            stockData.add(tempData);
        }
        assertEquals(expected, FinancialCalculator.computeReturn(stockData), 0.0001);
    }

    @Test
    void testVolatility() {
        Double[] prices = {100.0, 102.0, 104.0, 106.0, 108.0};
        double result = FinancialCalculator.computeVolatility(List.of(prices));
        assertTrue(result >= 0, "Volatility should be non-negative");
    }

    @Test
    void testSharpeRatio() {
        Double[] prices = {100.0, 102.0, 104.0, 106.0, 108.0};
        double result = FinancialCalculator.sharpeRatio(List.of(prices));
        assertTrue(result >= 0, "Sharpe Ratio should be non-negative");
    }
}
