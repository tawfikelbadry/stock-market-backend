package com.gradteam.porsaty.repository;

import com.gradteam.porsaty.model.TradingOperation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tawfik on 4/24/2018.
 */
@Repository
public interface TradingOperationRepository extends CrudRepository<TradingOperation,Long> {

    List<TradingOperation> findByBuyerIdOrderByDateDesc(long userId);

    List<TradingOperation> findByBuyerUsernameOrSellerUsernameOrderByDateDesc(String username,String username2);


    List<TradingOperation> findByStockIdOrderByDateDesc(long stockId);
}
