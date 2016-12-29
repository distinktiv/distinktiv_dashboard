package com.distinktiv.service;

import com.distinktiv.domain.CurrentUser;
import com.distinktiv.domain.User;
import com.distinktiv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CurrentUserDetailService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(CurrentUserDetailService.class.getSimpleName());

    private final UserRepository userRepository;

    @Autowired
    public CurrentUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.log(Level.INFO, String.format("Logging user with email [%s]", email));
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email [%s] not found.", email));
        }
        return new CurrentUser(user);
    }
}
