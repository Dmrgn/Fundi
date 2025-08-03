package use_case.sell;

/**
 * The Input Boundary for the Sell Use Case
 */
public interface SellInputBoundary {
    /**
     * Executes the buy usecase.
     * @param sellInputData the input data.
     */
    void execute(SellInputData sellInputData);
}
