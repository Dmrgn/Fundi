package use_case.create;

/**
 * Input boundary for Create Use Case.
 */
public interface CreateInputBoundary {

    /**
     * Executes the Portfolio Use Case.
     * @param createInputData the input data.
     */
    void execute(CreateInputData createInputData);
}
