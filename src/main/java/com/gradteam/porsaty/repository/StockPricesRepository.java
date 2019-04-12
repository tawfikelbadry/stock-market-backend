package com.gradteam.porsaty.repository;

import com.gradteam.porsaty.model.StockPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockPricesRepository extends CrudRepository<StockPrice,Long>{


    List<StockPrice> findByStockIdOrderByDateAsc(long id);

}
