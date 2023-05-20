package io.persona.unit.dao;

import io.persona.UserDao;

import java.util.HashMap;
import java.util.Map;

public class UserDaoMock extends UserDao {

    private Map<Integer, Boolean> answers = new HashMap<>();

    @Override
    public boolean delete(Integer id) {
        return answers.getOrDefault(id, false);
    }
}
