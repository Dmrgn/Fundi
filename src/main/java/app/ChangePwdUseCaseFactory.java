package app;

import interfaceadapter.change_password.ChangePwdController;
import interfaceadapter.change_password.ChangePwdViewModel;
import interfaceadapter.main.MainViewModel;
import usecase.change_password.ChangePwdInputBoundary;
import usecase.change_password.ChangePwdInteractor;
import usecase.change_password.ChangePwdOutputBoundary;
import usecase.change_password.ChangePwdPresenter;
import usecase.login.LoginUserDataAccessInterface;

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
