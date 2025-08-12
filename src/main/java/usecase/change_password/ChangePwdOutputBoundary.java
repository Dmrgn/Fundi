package usecase.change_password;

public interface ChangePwdOutputBoundary {

    /**
     * Prepares the success state for the view.
     *
     * @param message the user-facing success message
     */
    void prepareSuccessView(String message);

    /**
     * Prepares the success state for the view.
     *
     * @param error the user-facing error message
     */
    void prepareFailView(String error);
}
