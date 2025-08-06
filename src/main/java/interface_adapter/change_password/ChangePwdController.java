package interface_adapter.change_password;

import interface_adapter.main.MainViewModel;
import use_case.change_password.ChangePwdInputBoundary;
import use_case.change_password.ChangePwdInputData;

public class ChangePwdController {
    private final ChangePwdInputBoundary interactor;
    private final MainViewModel mainViewModel;

    public ChangePwdController(ChangePwdInputBoundary interactor, MainViewModel mainViewModel) {
        this.interactor = interactor;
        this.mainViewModel = mainViewModel;
    }

    public void execute(String newPassword) {
        String currentUser = mainViewModel.getState().getUsername();
        ChangePwdInputData inputData = new ChangePwdInputData(currentUser, newPassword);
        interactor.changePassword(inputData);
    }
}
