package usecase.remember_me;

import entity.User;

public interface RememberMeUserDataAccessInterface {
    void setRememberMe(String username, boolean remember);

    User getRememberedUser();
}
