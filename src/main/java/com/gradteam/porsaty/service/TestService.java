package com.gradteam.porsaty.service;

import com.gradteam.porsaty.model.MarketSector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by tawfik on 3/18/2018.
 */
@Service
public class TestService {

//    @Autowired
//    EntityManagerFactory managerFactory;


//    @PostConstruct
//    public void allSectors(){
//        EntityManager entityManger = this.managerFactory.createEntityManager();
//        List<MarketSector> sectors=  entityManger.createQuery("from MarketSector").getResultList();
//        System.out.println("----------start------------");
//        for (MarketSector sector:sectors) {
//            System.out.println(sector.getName());
//        }
//    }

}
