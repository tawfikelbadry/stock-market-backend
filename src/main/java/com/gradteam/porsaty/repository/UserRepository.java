package com.gradteam.porsaty.repository;

import com.gradteam.porsaty.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by tawfik on 4/23/2018.
 */
public interface UserRepository extends CrudRepository<User,Long> {

    User findByUsername(String username);

    User findByEmail(String email);
}
