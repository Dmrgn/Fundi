package entity;

public class UserFactory {
    /**
     * @param name
     * @param password
     * @return
     */
    public User create(String name, String password) {
        return new User(name, password);
    }
}
