package use_case.signup;

import entity.User;
import use_case.UserDataAccessInterface;

/**
 * DAO for the Signup Use Case.
 */
public interface SignupUserDataAccessInterface extends UserDataAccessInterface {

    /**
     * Checks if the given username exists.
     * 
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Saves the user.
     * 
     * @param user the user to save
     */
    void save(User user);

    /**
     * Returns the id of the given username
     * 
     * @param username The username
     * @return The id of the username
     */
    String getId(String username);
}