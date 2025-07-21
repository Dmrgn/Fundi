package interface_adapter.main;

/**
 * The State information representing the main page user.
 */
public class MainState {
    private String username = "";
    private String useCase = "";

    public MainState(MainState copy) {
        username = copy.username;
        useCase = copy.useCase;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public MainState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUseCase(String useCase) {
        this.useCase = useCase;
    }

    public String getUseCase() {
        return useCase;
    }
}