package interfaceadapter.create;

/**
 * The State for the create use case.
 */
public class CreateState {
    private String username = "";
    private String createError;

    public CreateState(CreateState copy) {
        username = copy.username;
        createError = copy.createError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public CreateState() {

    }

    /**
     * Getter.
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter.
     * @param username Value
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter.
     * @return Create Error
     */
    public String getCreateError() {
        return createError;
    }

    /**
     * Setter.
     * @param createError Value
     */
    public void setCreateError(String createError) {
        this.createError = createError;
    }
}
