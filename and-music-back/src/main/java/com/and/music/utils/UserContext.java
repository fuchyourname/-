package com.and.music.utils;

import com.and.music.domain.Users;
import org.springframework.stereotype.Component;

public class UserContext {
    private static final ThreadLocal<Users> userThreadLocal = new ThreadLocal<>();

    public static void setUser(Users user) {
        userThreadLocal.set(user);
    }

    public static Users getUser() {
        return userThreadLocal.get();
    }

    public static void removeUser() {
        userThreadLocal.remove();
    }
}

