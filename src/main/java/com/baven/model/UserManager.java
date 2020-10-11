package com.baven.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private final static Map<Integer, User> _userMap = new HashMap<>();

    private UserManager(){}

    public static void addUser(User user){
        if (null != user) {
            _userMap.put(user.getUserId(), user);
        }
    }
    public static void removeUserById(int userId) {
        _userMap.remove(userId);
    }
    public static Collection<User> listUser() {
        return _userMap.values();
    }
}
