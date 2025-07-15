package interface_adapter.main;

/**
 * The state for the Signup View Model.
 */
public class MainState {
    private String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "MainState{"
                + "username='" + username + '\''
                + '}';
    }
}