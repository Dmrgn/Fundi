package interfaceadapter;

import java.util.ArrayList;

import interfaceadapter.portfolio.PortfolioState;
import interfaceadapter.portfolio.PortfolioViewModel;

/**
 * The updater for the Portfolio View Model.
 */
public class PortfolioUpdateCommand implements PortfolioCommand {
    private final String ticker;
    private final double price;
    private final int quantity;

    public PortfolioUpdateCommand(String ticker, double price, int quantity) {
        this.ticker = ticker;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Update the Portfolio View Model.
     * 
     * @param portfolioViewModel The view model
     */
    public void execute(PortfolioViewModel portfolioViewModel) {
        PortfolioState portfolioState = portfolioViewModel.getState();
        ArrayList<String> outTickers = new ArrayList<>();
        ArrayList<Double> outPrice = new ArrayList<>();
        ArrayList<Integer> outQuantity = new ArrayList<>();

        String[] tickers = portfolioState.getStockNames();
        double[] prices = portfolioState.getStockPrices();
        int[] amounts = portfolioState.getStockAmounts();

        boolean found = false;
        for (int i = 0; i < tickers.length; i++) {
            outTickers.add(tickers[i]);
            if (ticker.equals(tickers[i])) {
                found = true;
                if (price > 0) {
                    outPrice.add(prices[i] + price * quantity);
                    outQuantity.add(amounts[i] + quantity);
                } else {
                    int newAmount = amounts[i] - quantity;
                    if (newAmount > 0) {
                        outPrice.add(Math.max(Math.abs(price) * newAmount, 0.0));
                        outQuantity.add(newAmount);
                    }
                }
            } else {
                outPrice.add(prices[i]);
                outQuantity.add(amounts[i]);
            }
        }

        if (!found) {
            if (price > 0) { // only add on buys
                outTickers.add(ticker);
                outPrice.add(price * quantity);
                outQuantity.add(quantity);
            }
        }

        portfolioState.setStockNames(outTickers.toArray(new String[0]));
        double[] newPrices = toDoubleArray(outPrice);
        portfolioState.setStockPrices(newPrices);
        int[] newAmounts = toIntArray(outQuantity);
        portfolioState.setStockAmounts(newAmounts);
    }

    private static int[] toIntArray(ArrayList<Integer> outQuantity) {
        int[] newAmounts = new int[outQuantity.size()];
        for (int i = 0; i < outQuantity.size(); i++) {
            newAmounts[i] = outQuantity.get(i);
        }
        return newAmounts;
    }

    private static double[] toDoubleArray(ArrayList<Double> outPrice) {
        double[] newPrices = new double[outPrice.size()];
        for (int i = 0; i < outPrice.size(); i++) {
            newPrices[i] = outPrice.get(i);
        }
        return newPrices;
    }
}
