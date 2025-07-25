package use_case.login;

import entity.User;
import use_case.UserDataAccessInterface;

/**
 * DAO for the Login Use Case.
 */
public interface LoginUserDataAccessInterface extends UserDataAccessInterface {

    /**
     * Checks if the given username exists.
     * 
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Returns the user with the given username.
     * 
     * @param username the username to look up
     * @return the user with the given username
     */
    User get(String username);
}