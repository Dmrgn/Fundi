package usecase.change_password;

public interface ChangePwdOutputBoundary {
    void prepareSuccessView(String message);
    void prepareFailView(String error);
}
