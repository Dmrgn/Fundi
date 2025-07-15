package use_case.main;

/**
 * Input Boundary for actions which are related to signing up.
 */
public interface MainInputBoundary {

    /**
     * Executes the main use case.
     * 
     * @param mainInputData the input data
     */
    void execute(MainInputData mainInputData);

}