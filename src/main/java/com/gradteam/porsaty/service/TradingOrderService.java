package com.gradteam.porsaty.service;

import com.gradteam.porsaty.model.*;
import com.gradteam.porsaty.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * Created by tawfik on 4/24/2018.
 */
@Service
public class TradingOrderService {

    @Autowired
    private TradingOrderRepository tradingOrderRepository;
    @Autowired
    private UserStocksService userStocksService;
    @Autowired
    private NormalUserRepository normalUserRepository;
    @Autowired
    private TradingOperationRepository tradingOperationRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockPricesRepository stockPricesRepository;
    @Autowired
    private SectorPriceRepository sectorPriceRepository;
    @Autowired
    private CompanyRepository companyRepository;



    public Iterable<TradingOrder> getAllOrders(){
        return  this.tradingOrderRepository.findAll();
    }

    public TradingOrder addOrder(TradingOrder tradingOrder){
        return this.tradingOrderRepository.save(tradingOrder);
    }


    public List<TradingOrder> getUserOrdersDesc(long userId) {
        return this.tradingOrderRepository.findByUserIdOrderByDateTimeDesc(userId);
    }

    // here the logged user accept the offer to sell some of his stocks to another trader
    // return 1 if done successfully
    // return 0 if current user not have enough stocks to trade
    public int acceptSellOffer(long orderId,String currentUsername){
       TradingOrder order= this.tradingOrderRepository.findById(orderId).get();
       System.out.println(order.getUser().getFullName()+" want to buy "+order.getCount()+" of "+order.getStock());
       if(userStocksService.getQuantityOfStockForUserByUsernameAndStockId(currentUsername,order.getStock().getId())>=order.getCount()){



           // save the operation to trading operation
           TradingOperation tradingOperation=new TradingOperation();
           tradingOperation.setBuyer(order.getUser());
           tradingOperation.setDate(new Date());
           tradingOperation.setPrice(order.getWantedPrice());
           tradingOperation.setSeller(normalUserRepository.findByUsername(currentUsername));
           tradingOperation.setQuantity(order.getCount());
           tradingOperation.setStock(order.getStock());
           this.tradingOperationRepository.save(tradingOperation);

           // update the buyer money value and the seller money
           double operationTotalMoney=order.getCount()*order.getWantedPrice();
           NormalUser buyer= order.getUser();
           double buyerNextValue= buyer.getTotalMoney()- operationTotalMoney;
           buyer.setTotalMoney(buyerNextValue);
           normalUserRepository.save(buyer);

           NormalUser seller=this.normalUserRepository.findByUsername(currentUsername);
           double sellerNextValue= seller.getTotalMoney()+ operationTotalMoney;
           seller.setTotalMoney(sellerNextValue);

           /*  add the stocks to the user in userstocks table (update seller and buyer) */
           UserStocks sellerStocks,buyerStocks;
           // substract from seller the number of selled stocks
           sellerStocks=this.userStocksService.getUserStocksByUsernameAndStockId(currentUsername,order.getStock().getId());
           sellerStocks.setQuantity(sellerStocks.getQuantity()-order.getCount());


           if(null!=this.userStocksService.getUserStocksByUserIdAndStockId(order.getUser().getId(),order.getStock().getId())){
               buyerStocks=this.userStocksService.getUserStocksByUserIdAndStockId(order.getUser().getId(),order.getStock().getId());
               buyerStocks.setPaidPrice(order.getWantedPrice());
               buyerStocks.setQuantity(buyerStocks.getQuantity()+order.getCount());
           }else{
               buyerStocks=new UserStocks();
               buyerStocks.setQuantity(order.getCount());
               buyerStocks.setPaidPrice(order.getWantedPrice());
               buyerStocks.setStock(order.getStock());
               buyerStocks.setUser(order.getUser());
           }

           this.userStocksService.saveUserStock(sellerStocks);
           this.userStocksService.saveUserStock(buyerStocks);

           // update trading volume and value for this stock
           Company tempCompany= order.getStock().getCompany();
           tempCompany.setTradingVolume(tempCompany.getTradingVolume()+order.getCount());
           tempCompany.setTradingValue(tempCompany.getTradingValue()+(order.getCount()*order.getWantedPrice()));
           this.companyRepository.save(tempCompany);

           // set the new value of stock ( set latest price ,market value,max price,min price)

           List<UserStocks> stockquantitesWithPrices= this.userStocksService.getUserStocksByStockId(order.getStock().getId());

           double newLatestPrice=0;
           for(UserStocks s:stockquantitesWithPrices){
               newLatestPrice+=(s.getPaidPrice()*s.getQuantity());
           }
           int totalStockNumber=order.getStock().getCompany().getCompanyCurrentTotalStocksNumber();
           newLatestPrice=newLatestPrice/totalStockNumber;

           Stock stock=order.getStock();
           stock.setLatestPrice(newLatestPrice);

           if(stock.getMaxPrice()<stock.getLatestPrice()){
               stock.setMaxPrice(stock.getLatestPrice());
           }

           if(stock.getMinPrice()>stock.getLatestPrice()){
               stock.setMinPrice(stock.getLatestPrice());
           }

           stock.setMarketValue(stock.getLatestPrice()*stock.getCompany().getCompanyCurrentTotalStocksNumber());

           this.stockRepository.save(stock);

           // add the new price to stockprices table
            StockPrice stockPrice=new StockPrice();
            stockPrice.setStock(order.getStock());
            stockPrice.setDate(new Date());
            stockPrice.setLatestPrice(newLatestPrice);
            stockPrice.setLastClosePrice(stock.getLastClosePrice());
            stockPrice.setOpenPrice(stock.getOpenPrice());

            stockPricesRepository.save(stockPrice);

            // save the new market sector value to the sector price table
            SectorPrice sectorPrice=new SectorPrice();
            sectorPrice.setDate(new Date());
            sectorPrice.setSector(order.getStock().getCompany().getCompanySector());
            sectorPrice.setPrice(order.getStock().getCompany().getCompanySector().getCuurentValue());
            this.sectorPriceRepository.save(sectorPrice);


           // set order to be accepted
           System.out.println("user have more than or equal to requested");
           order.setAccepted(true);
           tradingOrderRepository.save(order);

           return 1;

       }
       // return 0 if current user not have enough stocks to trade
       return 0;


    }




    // here the logged user accept the offer to buy some of another trader stocks
    // sell order from another user and you can buy this stocks from him
    // return 1 if done successfully
    // return 0 if current user not have enough money to trade
    public int acceptBuyOffer(long orderId,String currentUsername){

        TradingOrder order= this.tradingOrderRepository.findById(orderId).get();
        System.out.println(order.getUser().getFullName()+" want to sell "+order.getCount()+" of "+order.getStock());
        // check if the logged user has enough money to buy
        if(this.normalUserRepository.findTotalMoneyByUsername(currentUsername)>=(order.getCount()*order.getWantedPrice())){



            // save the operation to trading operation
            TradingOperation tradingOperation=new TradingOperation();
            tradingOperation.setDate(new Date());
            tradingOperation.setBuyer(normalUserRepository.findByUsername(currentUsername));
            tradingOperation.setPrice(order.getWantedPrice());
            tradingOperation.setSeller(order.getUser());
            tradingOperation.setQuantity(order.getCount());
            tradingOperation.setStock(order.getStock());
            this.tradingOperationRepository.save(tradingOperation);

            // update the buyer money value and the seller money
            double operationTotalMoney=order.getCount()*order.getWantedPrice();
            NormalUser seller= order.getUser();
            double sellerNextValue= seller.getTotalMoney()+operationTotalMoney;
            seller.setTotalMoney(sellerNextValue);
            normalUserRepository.save(seller);

            NormalUser buyer=this.normalUserRepository.findByUsername(currentUsername);
            double buyerNextValue= buyer.getTotalMoney() - operationTotalMoney;
            buyer.setTotalMoney(buyerNextValue);

            /*  add the stocks to the user in userstocks table (update seller and buyer) */
            UserStocks sellerStocks,buyerStocks;
            // substract from seller the number of selled stocks
            sellerStocks=this.userStocksService.getUserStocksByUserIdAndStockId(order.getUser().getId(),order.getStock().getId());
            sellerStocks.setQuantity(sellerStocks.getQuantity()-order.getCount());

            // add to buyer the number of buyed stocks
            if(null!=this.userStocksService.getUserStocksByUsernameAndStockId(currentUsername,order.getStock().getId())){
                buyerStocks=this.userStocksService.getUserStocksByUsernameAndStockId(currentUsername,order.getStock().getId());
                buyerStocks.setPaidPrice(order.getWantedPrice());
                buyerStocks.setQuantity(buyerStocks.getQuantity()+order.getCount());
            }else{
                buyerStocks=new UserStocks();
                buyerStocks.setQuantity(order.getCount());
                buyerStocks.setPaidPrice(order.getWantedPrice());
                buyerStocks.setStock(order.getStock());
                buyerStocks.setUser(this.normalUserRepository.findByUsername(currentUsername));
            }

            this.userStocksService.saveUserStock(sellerStocks);
            this.userStocksService.saveUserStock(buyerStocks);

            // update trading volume and value for this stock
            Company tempCompany= order.getStock().getCompany();
            tempCompany.setTradingVolume(tempCompany.getTradingVolume()+order.getCount());
            tempCompany.setTradingValue(tempCompany.getTradingValue()+(order.getCount()*order.getWantedPrice()));
            this.companyRepository.save(tempCompany);

            // set the new value of stock ( set latest price ,market value,max price,min price)

            List<UserStocks> stockquantitesWithPrices= this.userStocksService.getUserStocksByStockId(order.getStock().getId());

            double newLatestPrice=0;
            for(UserStocks s:stockquantitesWithPrices){
                newLatestPrice+=(s.getPaidPrice()*s.getQuantity());
            }
            int totalStockNumber=order.getStock().getCompany().getCompanyCurrentTotalStocksNumber();
            newLatestPrice=newLatestPrice/totalStockNumber;

            Stock stock=order.getStock();
            stock.setLatestPrice(newLatestPrice);

            if(stock.getMaxPrice()<stock.getLatestPrice()){
                stock.setMaxPrice(stock.getLatestPrice());
            }

            if(stock.getMinPrice()>stock.getLatestPrice()){
                stock.setMinPrice(stock.getLatestPrice());
            }

            stock.setMarketValue(stock.getLatestPrice()*stock.getCompany().getCompanyCurrentTotalStocksNumber());

            this.stockRepository.save(stock);

            // add the new price to stockprices table
            StockPrice stockPrice=new StockPrice();
            stockPrice.setStock(order.getStock());
            stockPrice.setDate(new Date());
            stockPrice.setLatestPrice(newLatestPrice);
            stockPrice.setLastClosePrice(stock.getLastClosePrice());
            stockPrice.setOpenPrice(stock.getOpenPrice());

            stockPricesRepository.save(stockPrice);

            // save the new market sector value to the sector price table
            SectorPrice sectorPrice=new SectorPrice();
            sectorPrice.setDate(new Date());
            sectorPrice.setSector(order.getStock().getCompany().getCompanySector());
            sectorPrice.setPrice(order.getStock().getCompany().getCompanySector().getCuurentValue());
            this.sectorPriceRepository.save(sectorPrice);


            // set order to be accepted
            System.out.println("user have more than or equal to requested");
            order.setAccepted(true);
            tradingOrderRepository.save(order);

            return 1;

        }
        // return 0 if current user not have enough stocks to trade
        return 0;


    }


    public List<TradingOrder> getCurrentUserOrdersDesc(String username) {
        return this.tradingOrderRepository.findByUserUsernameOrderByDateTimeDesc(username);
    }

    public List<TradingOrder> getCurrentUserSellOrdersDesc(String username,String type) {
        return this.tradingOrderRepository.findByUserUsernameAndTypeOrderByDateTimeDesc(username,type);
    }

    public List<TradingOrder> getCurrentUserAllOffers(String username) {
        return this.tradingOrderRepository.findByUserUsernameNotOrderByDateTimeDesc(username);
    }

    public List<TradingOrder> getCurrentUserAllSellOffers(String username) {
        return this.tradingOrderRepository.findByUserUsernameNotAndTypeOrderByDateTimeDesc(username,"SELL");
    }

    public List<TradingOrder> getCurrentUserAllBuyOffers(String username) {
        return this.tradingOrderRepository.findByUserUsernameNotAndTypeOrderByDateTimeDesc(username,"Buy");
    }

    public void cancelOrder(long orderId) {
         this.tradingOrderRepository.deleteById(orderId);
    }
}
