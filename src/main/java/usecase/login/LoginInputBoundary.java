package usecase.login;

/**
 * Input boundary for login functionality.
 */
public interface LoginInputBoundary {
    /**
     * Executes the login usecase.
     * 
     * @param loginInputData the input data.
     */
    void execute(LoginInputData loginInputData);

    /**
     * Switch to the Signup View.
     */
    void switchToSignupView();

    /**
     * Execute login with an already-hashed password (for remember me).
     * 
     * @param username       the username
     * @param hashedPassword the already-hashed password
     */
    void executeWithHashedPassword(String username, String hashedPassword);
}