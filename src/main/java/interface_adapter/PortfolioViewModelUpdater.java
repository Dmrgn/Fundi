package interface_adapter;

import interface_adapter.portfolio.PortfolioState;
import interface_adapter.portfolio.PortfolioViewModel;

import java.util.ArrayList;

public class PortfolioViewModelUpdater {
    public void update(PortfolioViewModel portfolioViewModel, String ticker, double price, int quantity) {
        PortfolioState portfolioState = portfolioViewModel.getState();
        ArrayList<String> outTickers = new ArrayList<>();
        ArrayList<Double> outPrice = new ArrayList<>();
        ArrayList<Integer> outQuantity = new ArrayList<>();

        String[] tickers = portfolioState.getStockNames();
        double[] prices = portfolioState.getStockPrices();
        int[] amounts = portfolioState.getStockAmounts();

        boolean found = false;
        for (int i = 0; i < tickers.length; i++) {
            if (ticker.equals(tickers[i])) {
                found = true;
                if (price > 0) {
                    outTickers.add(tickers[i]);
                    outPrice.add(prices[i] + price * quantity);
                    outQuantity.add(amounts[i] + quantity);
                } else {
                    int newAmount = amounts[i] - quantity;
                    if (newAmount > 0) {
                        outTickers.add(tickers[i]);
                        outPrice.add(Math.max(price * newAmount, 0.0));
                        outQuantity.add(newAmount);
                    }
                }
            } else {
                outTickers.add(tickers[i]);
                outPrice.add(prices[i] * amounts[i]);
                outQuantity.add(amounts[i]);
            }
        }

        if (!found) {
            outTickers.add(ticker);
            outPrice.add(price);
            outQuantity.add(quantity);
        }
        portfolioState.setStockNames(outTickers.toArray(new String[0]));
        double[] newPrices = new double[outPrice.size()];
        for (int i = 0; i < outPrice.size(); i++) {
            newPrices[i] = outPrice.get(i);
        }
        portfolioState.setStockPrices(newPrices);

        int[] newAmounts = new int[outQuantity.size()];
        for (int i = 0; i < outQuantity.size(); i++) {
            newAmounts[i] = outQuantity.get(i);
        }
        portfolioState.setStockAmounts(newAmounts);

    }
}
