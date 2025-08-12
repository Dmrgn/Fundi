package interfaceadapter.change_password;

import interfaceadapter.main.MainViewModel;
import usecase.change_password.ChangePwdInputBoundary;
import usecase.change_password.ChangePwdInputData;

public class ChangePwdController {
    private final ChangePwdInputBoundary interactor;
    private final MainViewModel mainViewModel;

    public ChangePwdController(ChangePwdInputBoundary interactor, MainViewModel mainViewModel) {
        this.interactor = interactor;
        this.mainViewModel = mainViewModel;
    }

    /**
     * Executes the Change Password request.
     *
     * @param newPassword the new password entered by the user
     */
    public void execute(String newPassword) {
        String currentUser = mainViewModel.getState().getUsername();
        ChangePwdInputData inputData = new ChangePwdInputData(currentUser, newPassword);
        interactor.changePassword(inputData);
    }
}
