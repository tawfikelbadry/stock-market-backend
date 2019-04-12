package com.gradteam.porsaty.repository;

import com.gradteam.porsaty.model.SectorPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorPriceRepository extends CrudRepository<SectorPrice,Long> {


    List<SectorPrice> findBySectorIdOrderByDateAsc(int sectorId);
}
