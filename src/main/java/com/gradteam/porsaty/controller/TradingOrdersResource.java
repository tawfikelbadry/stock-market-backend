package com.gradteam.porsaty.controller;

import com.gradteam.porsaty.model.TradingOrder;
import com.gradteam.porsaty.service.TradingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

/**
 * Created by tawfik on 4/24/2018.
 */
@RestController
@RequestMapping("/api/tradingorders")
public class TradingOrdersResource {

    @Autowired
    private TradingOrderService tradingOrderService;


    @GetMapping("/all")
    public ResponseEntity showAllOrders(){
        return ResponseEntity.ok(tradingOrderService.getAllOrders());
    }


    @GetMapping("/{userId}")
    public ResponseEntity getUserOrderAsc(@PathVariable("userId")long userId){
        return ResponseEntity.ok(this.tradingOrderService.getUserOrdersDesc(userId));
    }


    //////////////////  orders  //////////////////////////

    @GetMapping("/current/orders")
    public ResponseEntity getUserOrderAsc(Principal principal){
        List<Map<String,Object>> response=new ArrayList<>();
        List<TradingOrder> orders= this.tradingOrderService.getCurrentUserOrdersDesc(principal.getName());
        for(TradingOrder order:orders){
            Map<String,Object> o=new HashMap<>();
            o.put("orderId",order.getId());
            o.put("date",order.getDateTime());
            o.put("companyId",order.getStock().getCompany().getId());
            o.put("wantedPrice",order.getWantedPrice());
            o.put("quantity",order.getCount());
            o.put("totalPrice",order.getCount()*order.getWantedPrice());
            o.put("stockName",order.getStock().getCompany().getCompanyName());
            if(order.getType().equals("SELL")){
                o.put("type","بيع");
            }else{
                o.put("type","شراء");
            }
            o.put("isAccepted",order.isAccepted());

            response.add(o);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/current/orders/sell")
    public ResponseEntity getUserSellOrdersAsc(Principal principal){
        List<Map<String,Object>> response=new ArrayList<>();
        List<TradingOrder> orders= this.tradingOrderService.getCurrentUserSellOrdersDesc(principal.getName(),"SELL");
        for(TradingOrder order:orders){
            Map<String,Object> o=new HashMap<>();
            o.put("orderId",order.getId());
            o.put("stockName",order.getStock().getCompany().getCompanyName());
            o.put("date",order.getDateTime());
            o.put("companyId",order.getStock().getCompany().getId());
            o.put("wantedPrice",order.getWantedPrice());
            o.put("totalPrice",order.getCount()*order.getWantedPrice());
            o.put("quantity",order.getCount());
            if(order.getType().equals("SELL")){
                o.put("type","بيع");
            }else{
                o.put("type","شراء");
            }
            o.put("isAccepted",order.isAccepted());

            response.add(o);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/current/orders/buy")
    public ResponseEntity getUserBuyOrdersAsc(Principal principal){
        List<Map<String,Object>> response=new ArrayList<>();
        List<TradingOrder> orders= this.tradingOrderService.getCurrentUserSellOrdersDesc(principal.getName(),"BUY");
        for(TradingOrder order:orders){
            Map<String,Object> o=new HashMap<>();
            o.put("orderId",order.getId());
            o.put("date",order.getDateTime());
            o.put("companyId",order.getStock().getCompany().getId());
            o.put("stockName",order.getStock().getCompany().getCompanyName());
            o.put("wantedPrice",order.getWantedPrice());
            o.put("quantity",order.getCount());
            o.put("totalPrice",order.getCount()*order.getWantedPrice());
            if(order.getType().equals("SELL")){
                o.put("type","بيع");
            }else{
                o.put("type","شراء");
            }
            o.put("isAccepted",order.isAccepted());

            response.add(o);
        }

        return ResponseEntity.ok(response);
    }

    ////////////////  offers  ////////////////
    // current user all offers
    @GetMapping("/current/offers")
    public ResponseEntity getAllOffers(Principal principal){
        List<Map<String,Object>> response=new ArrayList<>();

        List<TradingOrder> offers= this.tradingOrderService.getCurrentUserAllOffers(principal.getName());


        for(TradingOrder order:offers){
            Map<String,Object> o=new HashMap<>();
            o.put("orderId",order.getId());
            o.put("date",order.getDateTime());
            o.put("wantedPrice",order.getWantedPrice());
            o.put("stockName",order.getStock().getCompany().getCompanyName());
            o.put("quantity",order.getCount());
            o.put("companyId",order.getStock().getCompany().getId());
            o.put("totalPrice",order.getCount()*order.getWantedPrice());

            if(order.getType().equals("SELL")){
                o.put("type","بيع");
            }else{
                o.put("type","شراء");
            }
            o.put("isAccepted",order.isAccepted());

            response.add(o);
        }


        return ResponseEntity.ok(response);

    }

    @GetMapping("/current/offers/sell")
    public ResponseEntity getAllSellOffers(Principal principal){
        List<Map<String,Object>> response=new ArrayList<>();

        List<TradingOrder> offers= this.tradingOrderService.getCurrentUserAllSellOffers(principal.getName());


        for(TradingOrder order:offers){
            Map<String,Object> o=new HashMap<>();
            o.put("orderId",order.getId());
            o.put("stockName",order.getStock().getCompany().getCompanyName());
            o.put("wantedPrice",order.getWantedPrice());
            o.put("date",order.getDateTime());
            o.put("companyId",order.getStock().getCompany().getId());
            o.put("quantity",order.getCount());
            o.put("totalPrice",order.getCount()*order.getWantedPrice());
            if(order.getType().equals("SELL")){
                o.put("type","بيع");
            }else{
                o.put("type","شراء");
            }
            o.put("isAccepted",order.isAccepted());

            response.add(o);
        }


        return ResponseEntity.ok(response);

    }

    @GetMapping("/current/offers/buy")
    public ResponseEntity getAllBuyOffers(Principal principal){
        List<Map<String,Object>> response=new ArrayList<>();

        List<TradingOrder> offers= this.tradingOrderService.getCurrentUserAllBuyOffers(principal.getName());


        for(TradingOrder order:offers){
            Map<String,Object> o=new HashMap<>();
            o.put("orderId",order.getId());
            o.put("totalPrice",order.getCount()*order.getWantedPrice());
            o.put("stockName",order.getStock().getCompany().getCompanyName());
            o.put("date",order.getDateTime());
            o.put("companyId",order.getStock().getCompany().getId());
            o.put("wantedPrice",order.getWantedPrice());
            o.put("quantity",order.getCount());
            if(order.getType().equals("SELL")){
                o.put("type","بيع");
            }else{
                o.put("type","شراء");
            }
            o.put("isAccepted",order.isAccepted());

            response.add(o);
        }


        return ResponseEntity.ok(response);

    }


    // types : BUY | SELL
    @PostMapping("order")
    public ResponseEntity addOrder(@RequestBody TradingOrder tradingOrder){
        tradingOrder.setDateTime(new Date());
        return ResponseEntity.ok(this.tradingOrderService.addOrder(tradingOrder));
    }

    @DeleteMapping("cancel-order/{orderId}")
    public ResponseEntity cancel_order(@PathVariable("orderId")long orderId){
        tradingOrderService.cancelOrder(orderId);
        return ResponseEntity.ok("order with id : "+orderId+" has removed");
    }


    //////////////////// accept offers mapping ////////////////////////////
    @PostMapping("/accept/sellorder/{orderId}")
    public ResponseEntity acceptSellOffer(@PathVariable("orderId")long orderId,Principal principal){
        Map<String,Integer> response=new HashMap<>();
        response.put("responseKey",this.tradingOrderService.acceptSellOffer(orderId,principal.getName()));
        return ResponseEntity.ok(response);
    }


    @PostMapping("/accept/buyorder/{orderId}")
    public ResponseEntity acceptBuyOffer(@PathVariable("orderId")long orderId,Principal principal){
        Map<String,Integer> response=new HashMap<>();
        response.put("responseKey",this.tradingOrderService.acceptBuyOffer(orderId,principal.getName()));
        return ResponseEntity.ok(response);
    }







}
