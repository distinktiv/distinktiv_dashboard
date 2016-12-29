package com.distinktiv.service;


import com.distinktiv.domain.Profile;
import com.distinktiv.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile getProfileByName(String name) {
        return profileRepository.findByName(name);
    }

    @Override
    public Collection<Profile> getAllProfile() {
        return (Collection<Profile>)profileRepository.findAll();
    }

    @Override
    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}
