package com.distinktiv.service;

import com.distinktiv.component.UserAccountSettings;
import com.distinktiv.domain.User;
import com.distinktiv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final UserAccountSettings userAccountSettings;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProfileService profileService, UserAccountSettings userAccountSettings, UserAccountSettings userAccountSettings1) {
        this.userRepository = userRepository;
        this.profileService = profileService;
        this.userAccountSettings = userAccountSettings1;
    }
    @Override
    public Optional<Collection<User>> getAllUsers() {
       return Optional.of( (Collection<User>) userRepository.findAll());
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public void updateUserFailedLoginCount(User user) {
        int userFailedLoginCount = user.getFailedLoginCount();
        user.setFailedLoginCount(userFailedLoginCount + 1);
        user.setLastFailedLoginDate(new Date());
        if (!user.isAccountLocked() && user.getFailedLoginCount() > userAccountSettings.getMaxFailedLoginCount()) {
            user.setAccountLocked(true);
        }
        userRepository.save(user);
    }

    @Override
    public void resetUserFailedLoginCount(User user) {
        user.setFailedLoginCount(0);
        userRepository.save(user);
    }

    @Override
    public boolean unlockUserAccount(User user) {
        boolean unlockSuccess = false;
        long lastFailedLoginTime = user.getLastFailedLoginDate().getTime();
        long currentTime = new Date().getTime();
        long timeout = userAccountSettings.getLockTimeOut() * 1000 * 60;
        if ((currentTime - lastFailedLoginTime) > timeout) {
            user.setAccountLocked(false);
            userRepository.save(user);
            unlockSuccess = true;
        }
        return unlockSuccess;
    }
}
