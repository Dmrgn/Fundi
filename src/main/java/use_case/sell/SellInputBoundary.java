package use_case.sell;

public interface SellInputBoundary {
    /**
     * Executes the buy usecase.
     * @param sellInputData the input data.
     */
    void execute(SellInputData sellInputData);
}
