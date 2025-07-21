package interface_adapter.create;

/**
 * The State information representing the create page user.
 */
public class CreateState {
    private String username = "";

    public CreateState(CreateState copy) {
        username = copy.username;
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
}