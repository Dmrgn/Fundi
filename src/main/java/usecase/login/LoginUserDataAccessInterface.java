package usecase.login;

import entity.User;

/**
 * DAO for the Login Use Case.
 */
public interface LoginUserDataAccessInterface {

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

    void saveNewPassword(String username, String newPassword);

    String getUserSetting(int userId, String key);

    void saveUserSetting(int userId, String key, String value);

    /**
     * Authenticate user with already-hashed password (for remember me).
     * 
     * @param username       the username
     * @param hashedPassword the already-hashed password
     * @return true if authentication succeeds
     */
    boolean authenticateWithHash(String username, String hashedPassword);
}
