package com.gradteam.porsaty.repository;

import com.gradteam.porsaty.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends CrudRepository<Company,Long>{

    List<Company> findAllByVerifiedStateAndCompanySectorId(boolean state,int id);

    List<Company> findByVerifiedState(boolean state);

    Company findByCompanyName(String companyName);

    Company findByEmail(String email);

    Company findByUsername(String username);

    Company findByStockId(long stockId);


    @Query("select c.image from Company c where c.id=?1")
    byte [] getCompanyImage(long imageId);


    List<Company> findFirst4ByVerifiedStateOrderByTradingVolumeDesc(boolean state);

    List<Company> findByVerifiedStateOrderByTradingVolumeDesc(boolean state,Pageable pageable);

    List<Company> findByCompanySectorIdAndVerifiedStateOrderByTradingVolumeDesc(int sector_id,boolean state,Pageable pageable);


    List<Company> findByVerifiedStateAndCompanyNameContaining(boolean state,String companyName);

    List<Company> findByFollwersUsername(String username);
}
