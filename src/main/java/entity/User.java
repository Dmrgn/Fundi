package entity;

/**
 * An entity representing a User.
 */
public class User {
    private final String name;
    private final String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Getter.
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter.
     * @return Password
     */
    public String getPassword() {
        return password;
    }

}
