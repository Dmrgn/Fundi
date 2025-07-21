package use_case.create;

/**
 * The Input Data for the create portfolio Use Case.
 */
public class CreateInputData {

    private final String username;

    public CreateInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
