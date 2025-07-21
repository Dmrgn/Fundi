package entity;

/**
 * Factory for creating users.
 */
public interface UserFactory {
    /**
     * Creates a new User.
     * @param id the id of the user
     * @param name the name of the new user
     * @param password the password of the new user
     * @return the new user
     */
    User create(String id, String name, String password);

}