package app;

import interface_adapter.change_password.ChangePwdController;
import interface_adapter.change_password.ChangePwdViewModel;
import interface_adapter.main.MainViewModel;
import use_case.change_password.ChangePwdInputBoundary;
import use_case.change_password.ChangePwdInteractor;
import use_case.change_password.ChangePwdOutputBoundary;
import use_case.change_password.ChangePwdPresenter;
import use_case.login.LoginUserDataAccessInterface;

public final class ChangePwdUseCaseFactory {
    private ChangePwdUseCaseFactory() {

    }

    /**
     * Factory for the change password use case.
     * @param changePwdViewModel The Change Pwd View Model
     * @param userDataAccessInterface The DAO
     * @param mainViewModel The Main View Model
     * @return The Change Pwd Controller
     */
    public static ChangePwdController create(ChangePwdViewModel changePwdViewModel,
                                      LoginUserDataAccessInterface userDataAccessInterface,
                                      MainViewModel mainViewModel) {
        ChangePwdOutputBoundary changePwdOutputBoundary = new ChangePwdPresenter(changePwdViewModel);
        ChangePwdInputBoundary changePwdInputBoundary = new ChangePwdInteractor(userDataAccessInterface,
                changePwdOutputBoundary, mainViewModel);
        return new ChangePwdController(changePwdInputBoundary, mainViewModel);
    }
}
