package usecase.change_password;

public interface ChangePwdInputBoundary {
    /**
     * Requests a password change.
     *
     * @param inputData the necessary fields like username and new password
     */
    void changePassword(ChangePwdInputData inputData);
}
