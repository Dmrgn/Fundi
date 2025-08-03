package interface_adapter.create;

/**
 * The State for the create use case.
 */
public class CreateState {
    private String username = "";
    private String createError;

    public CreateState(CreateState copy) {
        username = copy.username;
        createError = copy.createError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public CreateState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreateError() {
        return createError;
    }

    public void setCreateError(String createError) {
        this.createError = createError;
    }
}