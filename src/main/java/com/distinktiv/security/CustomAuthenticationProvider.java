package com.distinktiv.security;

import com.distinktiv.domain.User;
import com.distinktiv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    public final String BAD_CREDENTIALS = "BAD CREDENTIALS";
    public final String ACCOUNT_LOCKED = "ACCOUNT LOCKED";
    public final String ACCOUNT_STILL_LOCKED = "ACCOUNT STILL LOCKED";

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.getUserByEmail(email).orElseThrow(() -> new BadCredentialsException(BAD_CREDENTIALS));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isPasswordsMatch = encoder.matches(password, user.getPassword());

        if (!isPasswordsMatch) {
            userService.updateUserFailedLoginCount(user);
            if (user.isAccountLocked()) {
                throw new LockedException(ACCOUNT_LOCKED);
            } else {
                throw new BadCredentialsException(BAD_CREDENTIALS);
            }
        }

        userService.resetUserFailedLoginCount(user);

        if (user.isAccountLocked()) {
            boolean unlockSuccess = userService.unlockUserAccount(user);

            if (!unlockSuccess) {
                throw new LockedException(ACCOUNT_STILL_LOCKED);
            }
        }

        Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getProfile().getName()));

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
