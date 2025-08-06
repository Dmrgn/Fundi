package use_case.login;

/**
 * The Input Data for the Login Use Case.
 */
public class LoginInputData {

    private final String username;
    private final String password;

    public LoginInputData(String username, String password) {
        this.username = username;
        this.password = password;
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
     * @return Password
     */
    public String getPassword() {
        return password;
    }

}
