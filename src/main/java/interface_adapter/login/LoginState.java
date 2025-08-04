package interface_adapter.login;

/**
 * The state for the Login View Model.
 */
public class LoginState {
    private String username = "";
    private String usernameError;
    private String password = "";
    private String passwordError;

    public LoginState(LoginState copy) {
        username = copy.username;
        usernameError = copy.usernameError;
        password = copy.password;
        passwordError = copy.passwordError;
    }

    public LoginState() {
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
     * @return Username Error
     */
    public String getUsernameError() {
        return usernameError;
    }

    /**
     * Getter.
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter.
     * @return Password Error
     */
    public String getPasswordError() {
        return passwordError;
    }

    /**
     * Setter.
     * @param username Value
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter.
     * @param usernameError Value
     */
    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    /**
     * Setter.
     * @param password Value
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter.
     * @param passwordError Value
     */
    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }
}
