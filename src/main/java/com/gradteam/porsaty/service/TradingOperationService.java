package com.gradteam.porsaty.service;

import com.gradteam.porsaty.model.TradingOperation;
import com.gradteam.porsaty.repository.NormalUserRepository;
import com.gradteam.porsaty.repository.TradingOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tawfik on 5/1/2018.
 */
@Service
public class TradingOperationService {

    @Autowired
    TradingOperationRepository tradingOperationRepository;



    public List<TradingOperation> getUserTradingOperationsDesc(long userId){
        return this.tradingOperationRepository.findByBuyerIdOrderByDateDesc(userId);
    }

    public List<Map<String,Object>>  getCurrentUserTradingOperationsDesc(String username){

        List<TradingOperation> operations= this.tradingOperationRepository.findByBuyerUsernameOrSellerUsernameOrderByDateDesc(username,username);

        List<Map<String,Object>> response=new ArrayList<>();
        for(TradingOperation tr:operations){
            Map<String,Object> map=new HashMap<>();
            map.put("quantity",tr.getQuantity());
            map.put("date",tr.getDate());
            map.put("sector",tr.getStock().getCompany().getCompanySector().getName());
            map.put("price",tr.getPrice());
            map.put("stockName",tr.getStock().getCompany().getCompanyName());
            map.put("stockId",tr.getStock().getId());
            if(tr.getBuyer().getUsername().equals(username)){
                map.put("type","شراء");
                map.put("trader",tr.getSeller().getFullName());
            }else{
                map.put("type","بيع");
                map.put("trader",tr.getBuyer().getFullName());
            }

            response.add(map);
        }


        return response;
    }


    public List<TradingOperation> getStockTradingOperationsDesc(long stockId) {
        return this.tradingOperationRepository.findByStockIdOrderByDateDesc(stockId);
    }


}
