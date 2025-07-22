package data_access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import entity.User;
import entity.UserFactory;
import use_case.UserDataAccessInterface;

public class FileUserDataAccessObject implements UserDataAccessInterface {

    private final String csvFilePath;
    private final Map<String, User> accounts = new HashMap<>();
    private final UserFactory userFactory;

    public FileUserDataAccessObject(String csvFilePath, UserFactory userFactory) throws IOException {
        this.csvFilePath = csvFilePath;
        this.userFactory = userFactory;
        load();
    }

    public void save(User user) {
        accounts.put(user.getName(), user);
        save();
    }

    public User get(String username) {
        return accounts.get(username);
    }

    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

    private void load() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String header = reader.readLine();
            assert header.equals("username,password");

            String row;
            while ((row = reader.readLine()) != null) {
                String[] col = row.split(",");
                String username = String.valueOf(col[0]);
                String password = String.valueOf(col[1]);
                User user = userFactory.create(username, password);
                accounts.put(username, user);
            }
        }
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            writer.write("username,password\n");
            for (User user : accounts.values()) {
                String line = String.format("%s,%s\n", user.getName(), user.getPassword());
                writer.write(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
