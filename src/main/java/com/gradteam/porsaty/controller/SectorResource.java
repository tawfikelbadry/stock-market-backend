package com.gradteam.porsaty.controller;

import com.gradteam.porsaty.model.Company;
import com.gradteam.porsaty.model.MarketSector;
import com.gradteam.porsaty.model.SectorPrice;
import com.gradteam.porsaty.model.Stock;
import com.gradteam.porsaty.repository.SectorPriceRepository;
import com.gradteam.porsaty.repository.projection.SectorIdNames;
import com.gradteam.porsaty.service.CompanyService;
import com.gradteam.porsaty.service.SectorService;
import com.gradteam.porsaty.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by tawfik on 3/19/2018.
 */

@RestController
@RequestMapping("/api/sectors")
@CrossOrigin(origins = "http://localhost:4200")
public class SectorResource {

    private SectorService sectorService;
    private CompanyService companyService;
    private StockService stockService;
    private SectorPriceRepository sectorPriceRepository;

    @Autowired
    public SectorResource(SectorService sectorService, CompanyService companyService, StockService stockService,
                          SectorPriceRepository sectorPriceRepository) {
        this.sectorService = sectorService;
        this.companyService = companyService;
        this.stockService = stockService;
        this.sectorPriceRepository=sectorPriceRepository;
    }

    @GetMapping("")
    public List<MarketSector> Hello(){
        return this.sectorService.getAllSectors();
    }

    @GetMapping("/{id}")
    public MarketSector getMarketSector(@PathVariable("id") int id){
        return this.sectorService.findOne(id);
    }

    @GetMapping("/{id}/companies")
    public List<Company> getSectorCompanies(@PathVariable("id") int id){
        return this.companyService.getSectorCompanies(id);
    }

    @GetMapping("/{id}/stocks")
    public List<Stock> getAllSectorStocks(@PathVariable("id") int id){
        return this.stockService.getSectorStocks(id);
    }

    @PostMapping("/save")
    public MarketSector saveSector(@RequestBody MarketSector sector){
        return this.sectorService.save(sector);
    }


    @DeleteMapping("/{id}/remove")
    public int removeSector(@PathVariable("id")int id){
        this.sectorService.deleteSector(id);
        return id;
    }

    @GetMapping("/sectorIdAndNames")
    public List<SectorIdNames> getAllSectorIdsAndNames(){
        return this.sectorService.getAllSectorIdAndNames();
    }

    @GetMapping("/sector-prices/{sectorId}")
    public List<SectorPrice> getSectorPrices(@PathVariable("sectorId") int sectorId){
        return this.sectorPriceRepository.findBySectorIdOrderByDateAsc(sectorId);

    }
}
