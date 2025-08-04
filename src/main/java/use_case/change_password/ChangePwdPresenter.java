package use_case.change_password;

import interface_adapter.change_password.ChangePwdState;
import interface_adapter.change_password.ChangePwdViewModel;

public class ChangePwdPresenter implements ChangePwdOutputBoundary {
    private final ChangePwdViewModel viewModel;

    public ChangePwdPresenter(ChangePwdViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(String message) {
        ChangePwdState state = viewModel.getState();
        state.setMessage(message); // Single message field handles success
        viewModel.setState(state); // This also triggers firePropertyChange
    }

    @Override
    public void prepareFailView(String error) {
        ChangePwdState state = viewModel.getState();
        state.setMessage(error); // Same message field for error
        viewModel.setState(state); // Notify observers
    }
}
