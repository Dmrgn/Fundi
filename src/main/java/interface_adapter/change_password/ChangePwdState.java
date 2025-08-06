package interface_adapter.change_password;

public class ChangePwdState {
    private String newPassword = "";
    private String message = ""; // This will store both success and error messages

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
