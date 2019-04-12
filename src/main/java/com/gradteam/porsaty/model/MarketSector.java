package com.gradteam.porsaty.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MarketSector implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    private double openPrice;
    private double lastClosedValue;


    // value of المؤشر for the market sector
    @Transient
    private double cuurentValue;

    @Transient
    private double changeValue;

    @Transient
    private double changePercentage;



    @JsonIgnore
    @OneToMany(mappedBy = "companySector",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Set<Company> sector_companies=new HashSet<>();


    @OneToMany(mappedBy = "sector",cascade = CascadeType.ALL)
    Set<SectorPrice> sectorPrices=new HashSet<>();

    public MarketSector() {
    }

    public MarketSector(String name) {
        this.name = name;
    }

    public MarketSector(int id, String name) {
        this.id=id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //لمؤشر = ( مجموع القيم السوقية للأسهم لليوم / مجموع القيم السوقية للاسهم لليوم السابق ) × قيمة المؤشر لليوم السابق //
    public double getCuurentValue() {
        double marketValuesSumToday=0;
        double marketValuesSumYesterday=0;

        for(Company company:this.sector_companies){
            if(company.getStock().getLastClosePrice()==0&&company.getCompanyCurrentTotalStocksNumber()==0){
                continue;
            }
            marketValuesSumToday+=company.getStock().getLatestPrice()*company.getCompanyCurrentTotalStocksNumber();
            marketValuesSumYesterday+=(company.getStock().getLastClosePrice()*company.getCompanyCurrentTotalStocksNumber());
//            System.out.println("today : "+marketValuesSumToday);
//            System.out.println("yesterday : "+marketValuesSumYesterday);

        }

        this.cuurentValue=(marketValuesSumToday/marketValuesSumYesterday)*this.lastClosedValue;
        if(Double.isNaN(this.cuurentValue)){
            return 0;
        }
        return cuurentValue;
    }

    public void setCuurentValue(double cuurentValue) {
        this.cuurentValue = cuurentValue;
    }

    // change value =last close price - current price
    public double getChangeValue() {
        this.changeValue=this.getCuurentValue()-this.lastClosedValue;
        if(Double.isNaN(this.changeValue)){
            return 0;
        }
        return changeValue;
    }

    public void setChangeValue(double changeValue) {
        this.changeValue = changeValue;
    }

    // change percentage = ((currentvalue - last close value)/last close value)*100;
    public double getChangePercentage() {
        changePercentage=((cuurentValue-lastClosedValue)/lastClosedValue)*100;
        if(Double.isNaN(this.changePercentage)){
            return 0;
        }
        return changePercentage;
    }

    public void setChangePercentage(double changePercentage) {
        this.changePercentage = changePercentage;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getLastClosedValue() {
        return lastClosedValue;
    }

    public void setLastClosedValue(double lastClosedValue) {
        this.lastClosedValue = lastClosedValue;
    }

    public Set<Company> getSector_companies() {
        return sector_companies;
    }

    public void setSector_companies(Set<Company> sector_companies) {
        this.sector_companies = sector_companies;
    }


}
