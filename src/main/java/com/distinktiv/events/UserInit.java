package com.distinktiv.events;


import com.distinktiv.domain.Profile;
import com.distinktiv.domain.User;
import com.distinktiv.service.ProfileService;
import com.distinktiv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class UserInit implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = Logger.getLogger(UserInit.class.getSimpleName());
    public static final String ADMIN_PROFILE = "admin";
    public static final String USER_PROFILE = "user";

    private final UserService userService;
    private final ProfileService profileService;
    private final SecurityProperties securityProperties;

    @Autowired
    public UserInit(UserService userService,ProfileService profileService, SecurityProperties securityProperties) {
        this.userService = userService;
        this.profileService = profileService;
        this.securityProperties = securityProperties;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (CollectionUtils.isEmpty(profileService.getAllProfile())) {
            LOGGER.log(Level.INFO, "No profiles in storage, creating the admin one.");
            createProfiles();

            // see application-[env].properties - security.user
            User user = new User(securityProperties.getUser().getName(), new BCryptPasswordEncoder().encode(securityProperties.getUser().getPassword()), true, profileService.getProfileByName(ADMIN_PROFILE));
            System.out.println("CREATING USER ********** " + user.getEmail());
            userService.createUser(user);
            LOGGER.log(Level.INFO, "User created: " + user.toString());
        }
    }

    private void createProfiles() {
        Profile profile1 = new Profile(ADMIN_PROFILE);
        Profile profile2 = new Profile(USER_PROFILE);

        profileService.createProfile(profile1);
        profileService.createProfile(profile2);

        LOGGER.log(Level.INFO, "Profile created: " + profile1.toString());
        LOGGER.log(Level.INFO, "Profile created: " + profile2.toString());
    }

}
