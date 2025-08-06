package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final boolean useCaseFailed;

    public LoginOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Getter.
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter.
     * @return Use Case Failed
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

}
