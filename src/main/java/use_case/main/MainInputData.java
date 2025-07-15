package use_case.main;

/**
 * The Input Data for the Signup Use Case.
 */
public class MainInputData {

    private final String username;

    public MainInputData(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }

}