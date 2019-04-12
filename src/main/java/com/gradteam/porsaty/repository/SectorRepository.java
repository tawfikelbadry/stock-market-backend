package com.gradteam.porsaty.repository;

import com.gradteam.porsaty.model.MarketSector;
import com.gradteam.porsaty.repository.projection.SectorIdNames;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SectorRepository extends CrudRepository<MarketSector,Integer>{

    MarketSector findById(int id);

    @Query("select s from MarketSector s")
    List<SectorIdNames> findAllSectorNames();

}
