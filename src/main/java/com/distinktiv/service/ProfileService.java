package com.distinktiv.service;


import com.distinktiv.domain.Profile;

import java.util.Collection;

public interface ProfileService {

    Profile getProfileByName(String name);
    Collection<Profile> getAllProfile();
    Profile createProfile(Profile profile);

}
