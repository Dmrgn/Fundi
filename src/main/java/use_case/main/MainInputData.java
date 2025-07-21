package use_case.main;

/**
 * The Input Data for the Main Use Case.
 */
public class MainInputData {

    private final String username;
    private final String useCase;

    public MainInputData(String username, String useCase) {
        this.username = username;
        this.useCase = useCase;
    }

    public String getUsername() {
        return username;
    }
    public String getUseCase() {
        return useCase;
    }

}