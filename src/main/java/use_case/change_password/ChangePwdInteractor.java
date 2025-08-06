package use_case.change_password;

import interface_adapter.main.MainViewModel;
import use_case.login.LoginUserDataAccessInterface;

public class ChangePwdInteractor implements ChangePwdInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final ChangePwdOutputBoundary presenter;
    private final MainViewModel mainViewModel;

    public ChangePwdInteractor(LoginUserDataAccessInterface userDataAccessObject,
                               ChangePwdOutputBoundary presenter, MainViewModel mainViewModel) {
        this.userDataAccessObject = userDataAccessObject;
        this.presenter = presenter;
        this.mainViewModel = mainViewModel;
    }


    @Override
    public void changePassword(ChangePwdInputData inputData) {
        String newPassword = inputData.getNewPassword();
        String username = mainViewModel.getState().getUsername();

        if (newPassword == null || newPassword.isEmpty()) {
            presenter.prepareFailView("Password cannot be empty.");
        } else {
            userDataAccessObject.saveNewPassword(username, newPassword);
            presenter.prepareSuccessView("Password changed successfully.");
        }
    }
}
