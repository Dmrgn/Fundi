package use_case.main;

/**
 * Output Data for the Signup Use Case.
 */
public class MainOutputData {

    private final String username;

    private final boolean useCaseFailed;

    public MainOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}