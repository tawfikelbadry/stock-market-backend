package com.gradteam.porsaty.service;

import com.gradteam.porsaty.model.UserStocks;
import com.gradteam.porsaty.repository.CompanyRepository;
import com.gradteam.porsaty.repository.UserStocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tawfik on 3/22/2018.
 */
@Service
public class UserStocksService {

    private UserStocksRepository userStocksRepository;
    private CompanyRepository companyRepository;



    @Autowired
    public UserStocksService(UserStocksRepository userStocksRepository, CompanyRepository companyRepository) {
        this.userStocksRepository = userStocksRepository;
        this.companyRepository = companyRepository;
    }

    public List<UserStocks> getAllUserStocks(){
        return (List<UserStocks>) this.userStocksRepository.findAll();
    }

    // get all users that have a percentage of specific stock and it's percentage
    public ArrayList<Map<String,Object>> getStockUsers(long stockId){

        List<UserStocks> stockUsers = this.userStocksRepository.findByStockId(stockId);
        int totalStockQuantity=this.companyRepository.findByStockId(stockId).getCompanyCurrentTotalStocksNumber();
//        System.out.println(totalStockQuantity);
        ArrayList<Map<String,Object>> entities = new ArrayList<Map<String,Object>>();
        for(UserStocks su:stockUsers){
            Map<String,Object> obj=new HashMap<>();
            obj.put("userId",su.getUser().getId());
            obj.put("userFullName",su.getUser().getFullName());
            obj.put("quantity",su.getQuantity());
            double percentage=((su.getQuantity()+0.0)/totalStockQuantity)*100;
            obj.put("percentage",percentage);

            entities.add(obj);
        }

        return entities;
    }


    public List<Map<String,Object>> getAllUserStocksByUserId(long userId){

        List<Object[]> userStocks = this.userStocksRepository.findUserStocksByUserId(userId);

        List<Map<String,Object>> userStoksInfo=new ArrayList<>();

        for(int i=0;i<userStocks.size();i++){

            Map<String,Object> temp=new HashMap();
            temp.put("quantity",userStocks.get(i)[0]);
            temp.put("stockId",userStocks.get(i)[1]);
            temp.put("stockName",userStocks.get(i)[2]);
            temp.put("latestPrice",userStocks.get(i)[3]);
            temp.put("paidPrice",userStocks.get(i)[4]);

            userStoksInfo.add(temp);
        }

        return userStoksInfo;
    }

    public List<Map<String,Object>> getAllUserStocksByUsername(String username){

        List<Object[]> userStocks = this.userStocksRepository.findUserStocksByUsername(username);

        List<Map<String,Object>> userStoksInfo=new ArrayList<>();

        for(int i=0;i<userStocks.size();i++){

            Map<String,Object> temp=new HashMap();
            temp.put("quantity",userStocks.get(i)[0]);
            temp.put("stockId",userStocks.get(i)[1]);
            temp.put("stockName",userStocks.get(i)[2]);
            temp.put("latestPrice",userStocks.get(i)[3]);
            temp.put("paidPrice",userStocks.get(i)[4]);

            userStoksInfo.add(temp);
        }

        return userStoksInfo;
    }


    public int getQuantityOfStockForUserByUsernameAndStockId(String username,long stockId){
        return this.userStocksRepository.findQuantityUserStocksByUsernameAndStockId(username,stockId);
    }

    public int getQuantityOfStockForUserByUserIdAndStockId(long userId,long stockId){
        return this.userStocksRepository.findQuantityUserStocksByUserIdAndStockId(userId,stockId);
    }


    public UserStocks getUserStocksByUsernameAndStockId(String username,long stockId){
        return this.userStocksRepository.findUserStocksByUsernameAndStockId(username,stockId);
    }

    public UserStocks getUserStocksByUserIdAndStockId(long userId,long stockId){
        return this.userStocksRepository.findUserStocksByUserIdAndStockId(userId,stockId);
    }

    public List<UserStocks> getUserStocksByStockId(long stockId){
        return this.userStocksRepository.findAllByStockId(stockId);
    }


    public void saveUserStock(UserStocks sellerStocks) {
        this.userStocksRepository.save(sellerStocks);
    }
}
