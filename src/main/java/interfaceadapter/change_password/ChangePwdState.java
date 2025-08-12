package interfaceadapter.change_password;

public class ChangePwdState {
    private String newPassword = "";
    private String message = "";

    /**
     * Gets the new password.
     *
     * @return  the new password
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Updates the new-password text shown in the UI.
     *
     * @param newPassword the text to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Getter for the message.
     *
     * @return  the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Updates the user-facing message (e.g., success or error).
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
