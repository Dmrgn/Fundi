package use_case;

import entity.User;

public interface UserDataAccessInterface {
    boolean existsByName(String identifier);

    void save(User user);

    User get(String username);
}
