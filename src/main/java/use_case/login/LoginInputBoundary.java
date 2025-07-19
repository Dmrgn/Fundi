package use_case.login;

/**
 * Input boundary for login functionality
 */
public interface LoginInputBoundary {

    /**
     * Executes the login usecase.
     * @param loginInputData the input data.
     */
    void execute(LoginInputData loginInputData);
}
