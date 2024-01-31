package com.bitscoder.swoppiapp.repository;

import com.bitscoder.swoppiapp.entities.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<BaseUser, String> {

    boolean existsByName(String name);

    boolean existsByEmail(String email);
}
