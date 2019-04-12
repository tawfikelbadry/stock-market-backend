package com.gradteam.porsaty.repository;

import com.gradteam.porsaty.model.NormalUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by tawfik on 3/20/2018.
 */
@Repository
public interface NormalUserRepository extends CrudRepository<NormalUser,Long>{


    NormalUser findByUsername(String username);

    NormalUser findByEmail(String email);



    @Query("select n.image from NormalUser n where n.id=?1")
    byte [] getNormalUserImage(long imageId);

    @Query("select n.image from NormalUser n where n.username=?1")
    byte[] getcurrentNormalUserImage(String username);

    @Query("select n.fullName from NormalUser n where n.username=?1")
    String findFullNameByUsername(String username);

    @Query("select n.totalMoney from NormalUser n where n.username=?1")
    Double findTotalMoneyByUsername(String username);

    @Query("select n.id from NormalUser n where n.username=?1")
    Long findUserIdByUsername(String username);
}
