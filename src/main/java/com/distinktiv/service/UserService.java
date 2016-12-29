package com.distinktiv.service;


import com.distinktiv.domain.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserByEmail(String email);
    Optional<Collection<User>> getAllUsers();
    User createUser(User user);
    void resetUserFailedLoginCount(User user);
    void updateUserFailedLoginCount(User user);
    boolean unlockUserAccount(User user);

}
