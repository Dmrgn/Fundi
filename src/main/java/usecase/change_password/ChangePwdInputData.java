package usecase.change_password;

public class ChangePwdInputData {
    private final String username;
    private final String newPassword;

    public ChangePwdInputData(String username, String newPassword) {
        this.username = username;
        this.newPassword = newPassword;
    }

    /**
     * Gets the new password.
     *
     * @return the new password.
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     *  Gets the username of the user.
     *
     *  @return the username.
     */

    public String getUsername() {
        return username;
    }
}
