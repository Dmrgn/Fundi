package usecase.change_password;

public class ChangePwdInputData {
    private final String username;
    private final String newPassword;

    public ChangePwdInputData(String username, String newPassword) {
        this.username = username;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getUsername() {
        return username;
    }
}
