package com.gradteam.porsaty.repository;

import com.gradteam.porsaty.model.Stock;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StockRepository extends CrudRepository<Stock,Long> {

    List<Stock> findAllByCompanyCompanySectorId(int id);


    @Query("select s.company.companyName from Stock s where s.id=?1")
    String findStockNameByStockId(long stockId);

    @Query("select s.latestPrice from Stock s where s.id=?1")
    Double findStockPriceById(long stockId);
}
