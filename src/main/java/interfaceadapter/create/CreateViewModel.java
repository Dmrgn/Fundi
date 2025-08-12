package interfaceadapter.create;

import interfaceadapter.ViewModel;

/**
 * The View Model for the Create View.
 */
public class CreateViewModel extends ViewModel<CreateState> {

    public CreateViewModel() {
        super("create");
        setState(new CreateState());
    }

}