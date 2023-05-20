package io.persona.unit.dao;

import io.persona.UserDao;

import java.util.HashMap;
import java.util.Map;

public class UserSpy extends UserDao {
    //
    private final UserDao userDao;
    private final Map<Integer, Boolean> answers = new HashMap<>();

    public UserSpy(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean delete(Integer id) {
        return answers.getOrDefault(id, userDao.delete(id));
    }
}
