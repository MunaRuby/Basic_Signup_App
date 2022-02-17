package com.nwabundo.mkobo.repository;


import com.nwabundo.mkobo.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);

    Boolean existsByEmail(String email);

    UserModel findUserModelByUsername(String username);

}
