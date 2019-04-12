package com.gradteam.porsaty.repository;

import com.gradteam.porsaty.model.security.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tawfik on 4/23/2018.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

    Role findByName(String roleName);
}
