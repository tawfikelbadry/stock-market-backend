package com.gradteam.porsaty.scheduleTasks;

import com.gradteam.porsaty.model.Stock;
import com.gradteam.porsaty.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by tawfik on 5/5/2018.
 */
@Component
public class SessionOperationScheduling {

    @Autowired
    private StockRepository stockRepository;


    // this method is occured every day at 2 pm
    // and it set the last close price to the price of the stock at this time
   // @Scheduled(cron = "0 0 14 * * ?")
    public void calculateClosedPricesForAllStocksAt2pm (){
        Iterable<Stock> allStocks= stockRepository.findAll();
        for (Stock stock:allStocks) {
            stock.changeClosePriceAfterSessionEnd();
        }
        stockRepository.saveAll(allStocks);

    }

}
