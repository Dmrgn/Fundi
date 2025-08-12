package usecase.change_password;

import interfaceadapter.change_password.ChangePwdState;
import interfaceadapter.change_password.ChangePwdViewModel;

public class ChangePwdPresenter implements ChangePwdOutputBoundary {
    private final ChangePwdViewModel viewModel;

    public ChangePwdPresenter(ChangePwdViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(String message) {
        ChangePwdState state = viewModel.getState();
        state.setMessage(message);
        viewModel.setState(state);
    }

    @Override
    public void prepareFailView(String error) {
        ChangePwdState state = viewModel.getState();
        state.setMessage(error);
        viewModel.setState(state);
    }
}
