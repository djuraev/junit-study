package io.persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    //
    List<User> users = new ArrayList<>();
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean delete(Integer id) {
        //
        return userDao.delete(id);
    }

    public List<User> getAll() {
        //
        return users;
    }

    public boolean add(User... user) {
        //
        return users.addAll(List.of(user));
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
