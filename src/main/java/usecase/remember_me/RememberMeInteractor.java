package usecase.remember_me;

import entity.User;
import usecase.login.LoginInputBoundary;
import usecase.login.LoginInputData;

public class RememberMeInteractor {
    private final RememberMeUserDataAccessInterface rememberDao;
    private final LoginInputBoundary loginInteractor;

    public RememberMeInteractor(RememberMeUserDataAccessInterface rememberDao, LoginInputBoundary loginInteractor) {
        this.rememberDao = rememberDao;
        this.loginInteractor = loginInteractor;
    }

    public void checkRememberMe() {
        User remembered = rememberDao.getRememberedUser();
        if (remembered != null) {
            loginInteractor.execute(new LoginInputData(remembered.getName(), remembered.getPassword()));
        }
    }

    public void saveRememberMe(String username, boolean rememberMe) {
        rememberDao.setRememberMe(username, rememberMe);
    }
}