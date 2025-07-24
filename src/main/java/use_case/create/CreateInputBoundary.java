package use_case.create;

/**
 * Input boundary for create portfolio functionality
 */
public interface CreateInputBoundary {

    /**
     * Executes the login usecase.
     * @param createInputData the input data.
     */
    void execute(CreateInputData createInputData);
}
