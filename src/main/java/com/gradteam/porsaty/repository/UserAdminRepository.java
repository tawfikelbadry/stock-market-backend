package com.gradteam.porsaty.repository;

import com.gradteam.porsaty.model.UserAdmin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tawfik on 4/24/2018.
 */
@Repository
public interface UserAdminRepository extends CrudRepository<UserAdmin,Long> {
}
