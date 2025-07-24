package use_case.buy;

/**
 * Input boundary for buy portfolio functionality
 */
public interface BuyInputBoundary {

    /**
     * Executes the buy usecase.
     * @param buyInputData the input data.
     */
    void execute(BuyInputData buyInputData);
}
