package use_case.buy;

/**
 * Input boundary for Buy Use Case.
 */
public interface BuyInputBoundary {

    /**
     * Executes the Buy Usecase.
     * @param buyInputData the input data.
     */
    void execute(BuyInputData buyInputData);
}
