package com.gradteam.porsaty.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Stock {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double namedValue;
    private double marketValue;

    private double bookValue;
    private double bokValueMultiplier;

    private double gainOfStock;
    private double multiplierGainofStock;

    private String TradingCurrency;

    private double latestPrice;
    private double openPrice;
    private double lastClosePrice;
    private double maxPrice;
    private double minPrice;

    // get the change value for stock
    @Transient
    private double changeValue;

    // get the change percentage for stock
    @Transient
    private double changePercentage;




    @JsonIgnore
    @OneToMany(mappedBy = "stock",fetch = FetchType.LAZY)
    List<UserStocks> stockUsers;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Company company;

    @JsonIgnore
    @OneToMany(mappedBy = "stock",fetch = FetchType.LAZY)
    private List<StockPrice> stockPricesList;

    @JsonIgnore
    @OneToMany(mappedBy = "stock",fetch = FetchType.LAZY)
    List<TradingOperation> stockOperations;


    @OneToMany(mappedBy = "stock")
    @JsonIgnore
    List<TradingOrder> orders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getNamedValue() {
        return namedValue;
    }

    public void setNamedValue(double namedValue) {
        this.namedValue = namedValue;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public double getBookValue() {
        return bookValue;
    }

    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    public double getBokValueMultiplier() {
        return bokValueMultiplier;
    }

    public void setBokValueMultiplier(double bokValueMultiplier) {
        this.bokValueMultiplier = bokValueMultiplier;
    }

    public double getGainOfStock() {
        return gainOfStock;
    }

    public void setGainOfStock(double gainOfStock) {
        this.gainOfStock = gainOfStock;
    }

    public double getMultiplierGainofStock() {
        return multiplierGainofStock;
    }

    public void setMultiplierGainofStock(double multiplierGainofStock) {
        this.multiplierGainofStock = multiplierGainofStock;
    }

    public String getTradingCurrency() {
        return TradingCurrency;
    }

    public void setTradingCurrency(String tradingCurrency) {
        TradingCurrency = tradingCurrency;
    }

    public double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getLastClosePrice() {
        return lastClosePrice;
    }

    public void setLastClosePrice(double lastClosePrice) {
        this.lastClosePrice = lastClosePrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<StockPrice> getStockPricesList() {
        return stockPricesList;
    }

    public void setStockPricesList(List<StockPrice> stockPricesList) {
        this.stockPricesList = stockPricesList;
    }

    public List<UserStocks> getStockUsers() {
        return stockUsers;
    }

    public void setStockUsers(List<UserStocks> stockUsers) {
        this.stockUsers = stockUsers;
    }

    public List<TradingOperation> getStockOperations() {
        return stockOperations;
    }

    public void setStockOperations(List<TradingOperation> stockOperations) {
        this.stockOperations = stockOperations;
    }

    public List<TradingOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<TradingOrder> orders) {
        this.orders = orders;
    }

    // change value = latest price - last close price
    public double getChangeValue() {
        this.changeValue=latestPrice-lastClosePrice;
        return changeValue;
    }

    public void setChangeValue(double changeValue) {
        this.changeValue = changeValue;
    }
    // change percentage = ((latest price - last close price)/last close price)*100
    public double getChangePercentage() {
        this.changePercentage=((latestPrice-lastClosePrice)/lastClosePrice)*100;
        if(Double.isNaN(changePercentage)){
            return 0;
        }
        return changePercentage;
    }

    public void setChangePercentage(double changePercentage) {
        this.changePercentage = changePercentage;
    }


    ///// custom methods
    public void changeClosePriceAfterSessionEnd(){
        this.lastClosePrice=this.latestPrice;
    }
}
