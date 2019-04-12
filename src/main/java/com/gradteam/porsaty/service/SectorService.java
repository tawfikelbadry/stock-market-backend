package com.gradteam.porsaty.service;

import com.gradteam.porsaty.model.MarketSector;
import com.gradteam.porsaty.repository.SectorRepository;
import com.gradteam.porsaty.repository.projection.SectorIdNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    public List<MarketSector> getAllSectors(){
        return (List<MarketSector>) this.sectorRepository.findAll();
    }

    public MarketSector save(MarketSector sector) {
       return this.sectorRepository.save(sector);
    }

    public MarketSector findOne(int id){
        return this.sectorRepository.findById(id);
    }

    public void deleteSector(int id){
        this.sectorRepository.deleteById(id);
    }

    public List<SectorIdNames> getAllSectorIdAndNames(){
        return this.sectorRepository.findAllSectorNames();

    }
}
