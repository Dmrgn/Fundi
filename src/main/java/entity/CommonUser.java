package entity;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String id;
    private final String name;
    private final String password;

    public CommonUser(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

}