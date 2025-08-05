package use_case.change_password;

public interface ChangePwdOutputBoundary {
    void prepareSuccessView(String message);
    void prepareFailView(String error);
}
