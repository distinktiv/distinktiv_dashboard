package com.distinktiv.repository;

import com.distinktiv.domain.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {

    Profile findByName(String name);

}
