package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String id;
    private final String username;
    private final boolean useCaseFailed;

    public LoginOutputData(String id, String username, boolean useCaseFailed) {
        this.id = id;
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

}
