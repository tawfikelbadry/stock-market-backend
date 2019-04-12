package com.gradteam.porsaty.repository;

import com.gradteam.porsaty.model.TradingOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by tawfik on 4/24/2018.
 */
@Repository
public interface TradingOrderRepository extends CrudRepository<TradingOrder,Long> {

    List<TradingOrder> findByUserIdOrderByDateTimeDesc(long userId);

    List<TradingOrder> findByUserUsernameOrderByDateTimeDesc(String username);

    List<TradingOrder> findByUserUsernameAndTypeOrderByDateTimeDesc(String username, String type);

    List<TradingOrder> findByUserUsernameNotOrderByDateTimeDesc(String username);

    List<TradingOrder> findByUserUsernameNotAndTypeOrderByDateTimeDesc(String username,String type);

}
