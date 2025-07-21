package entity;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements UserFactory {

    @Override
    public User create(String id, String name, String password) {
        return new CommonUser(id, name, password);
    }
}