package io.persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    //
    List<User> users = new ArrayList<>();
    public List<User> getAll() {
        //
        return users;
    }

    public boolean add(User user) {
        //
        return users.add(user);
    }

    public Optional<User> login(String username, String password) {
        //
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username/Password is null");
        }
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
    }
}
