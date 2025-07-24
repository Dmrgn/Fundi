package entity;

/**
 * The representation of a user in our program.
 */
public interface User {

    /**
     * Returns the id of the user.
     * 
     * @return the id of the user.
     */
    String getId();

    /**
     * Returns the username of the user.
     * 
     * @return the username of the user.
     */
    String getName();

    /**
     * Returns the password of the user.
     * 
     * @return the password of the user.
     */
    String getPassword();

}
