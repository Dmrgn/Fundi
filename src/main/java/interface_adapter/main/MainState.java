package interface_adapter.main;

/**
 * The State information representing the main page user.
 */
public class MainState {
    private String id = "";
    private String username = "";
    private String useCase = "";

    public MainState(MainState copy) {
        id = copy.id;
        username = copy.username;
        useCase = copy.useCase;
    }

    // Because of the previous copy constructor, the default constructor must be
    // explicit.
    public MainState() {

    }

    /**
     * Getter.
     * 
     * @return Id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter.
     * 
     * @param id Value
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter.
     * 
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter.
     * 
     * @param username Value
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter.
     * 
     * @param useCase Value
     */
    public void setUseCase(String useCase) {
        this.useCase = useCase;
    }

    /**
     * Getter.
     * 
     * @return Use Case
     */
    public String getUseCase() {
        return useCase;
    }
}