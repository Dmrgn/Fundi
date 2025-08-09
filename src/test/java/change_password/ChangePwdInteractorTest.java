package change_password;

import entity.User;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.change_password.ChangePwdInputData;
import use_case.change_password.ChangePwdInteractor;
import use_case.change_password.ChangePwdOutputBoundary;
import use_case.login.LoginUserDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChangePwdInteractorTest {

    private FakeUserDao fakeUserDao;
    private FakePresenter fakePresenter;
    private MainViewModel mainViewModel;
    private ChangePwdInteractor interactor;

    @BeforeEach
    void setup() {
        fakeUserDao = new FakeUserDao();
        fakePresenter = new FakePresenter();
        mainViewModel = new MainViewModel();

        MainState state = new MainState();
        state.setUsername("test_user");
        mainViewModel.setState(state);

        interactor = new ChangePwdInteractor(fakeUserDao, fakePresenter, mainViewModel);
    }

    @Test
    void testSuccessfulPasswordChange() {
        ChangePwdInputData input = new ChangePwdInputData("test_user","newpass123");
        interactor.changePassword(input);

        assertTrue(fakeUserDao.saved);
        assertEquals("test_user", fakeUserDao.savedUsername);
        assertEquals("newpass123", fakeUserDao.savedPassword);
        assertEquals("Password changed successfully.", fakePresenter.successMessage);
        assertNull(fakePresenter.failMessage);
    }

    @Test
    void testEmptyPasswordFails() {
        ChangePwdInputData input = new ChangePwdInputData("test_user","");
        interactor.changePassword(input);

        assertFalse(fakeUserDao.saved);
        assertEquals("Password cannot be empty.", fakePresenter.failMessage);
        assertNull(fakePresenter.successMessage);
    }

    @Test
    void testNullPasswordFails() {
        ChangePwdInputData input = new ChangePwdInputData("test_user",null);
        interactor.changePassword(input);

        assertFalse(fakeUserDao.saved);
        assertEquals("Password cannot be empty.", fakePresenter.failMessage);
        assertNull(fakePresenter.successMessage);
    }


    private static class FakeUserDao implements LoginUserDataAccessInterface {
        boolean saved = false;
        String savedUsername = null;
        String savedPassword = null;

        @Override
        public boolean existsByName(String username) {
            return false;
        }

        @Override
        public User get(String username) {
            return null;
        }

        @Override
        public void saveNewPassword(String username, String newPassword) {
            saved = true;
            savedUsername = username;
            savedPassword = newPassword;
        }

        // You can leave the rest unimplemented if not used
    }

    // Fake Presenter
    private static class FakePresenter implements ChangePwdOutputBoundary {
        String successMessage = null;
        String failMessage = null;

        @Override
        public void prepareSuccessView(String message) {
            successMessage = message;
        }

        @Override
        public void prepareFailView(String error) {
            failMessage = error;
        }
    }
}
