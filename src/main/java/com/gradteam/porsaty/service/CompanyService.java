package com.gradteam.porsaty.service;

import com.gradteam.porsaty.config.SecurityUtility;
import com.gradteam.porsaty.model.Company;
import com.gradteam.porsaty.model.Stock;
import com.gradteam.porsaty.model.security.Role;
import com.gradteam.porsaty.model.security.UserRole;
import com.gradteam.porsaty.repository.CompanyRepository;
import com.gradteam.porsaty.repository.RoleRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CompanyService {

    private static final Logger LOG= LoggerFactory.getLogger(UserSecurityService.class);


    private CompanyRepository companyRepository;
    private RoleRepository roleRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, RoleRepository roleRepository) {
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
    }

    public Company SaveCompany(Company company){
       return this.companyRepository.save(company);
    }



    public long register(Company company){

        /*
        *  return -2 if email used before
        *  return -1 if company name used before
        *  return id if saved succsefully
        *  else return 0
        * */
        if(this.companyRepository.findByUsername(company.getUsername())!=null){
            System.out.println("this username Already used");
            return -3;
        }else if(this.companyRepository.findByEmail(company.getEmail())!=null){
            System.out.println("this Email Has registred Already");
            return -2;
        }else if(this.companyRepository.findByCompanyName(company.getCompanyName())!=null){
            System.out.println("this company name already exist");
            return -1;
        }else {


            company.setPassword(SecurityUtility.passwordEncoder().encode(company.getPassword()));

            // get Company  Role
            Role roleCompany = roleRepository.findByName("ROLE_COMPANY");

            // add Company Role to the new Compant
            Set<UserRole> companyRoleSet=new HashSet<>();
            companyRoleSet.add(new UserRole(company,roleCompany));
            company.setUserRoles(companyRoleSet);

            Stock stock=new Stock();
            stock.setCompany(company);

            company.setStock(stock);

            // create new company

            Company cmpny = this.companyRepository.save(company);

            if (cmpny != null) {
                return cmpny.getId();
            }
        }
        return 0;
    }


    public List<Company> getAllCompanies(){
        return (List<Company>) this.companyRepository.findAll();
    }


    public Company getCompanyById(long id) throws Exception{
        return this.companyRepository.findById(id).get();
    }


    public List<Company> getSectorCompanies(int id){
        return this.companyRepository.findAllByVerifiedStateAndCompanySectorId(true,id);
    }


    public byte[] getCompanyImage(long companyId){
        return this.companyRepository.getCompanyImage(companyId);
    }


    public List<Company> getTop4CompaniesSortedByTradingVolume(){

        return this.companyRepository.findFirst4ByVerifiedStateOrderByTradingVolumeDesc(true);
    }

    public List<Company> getSliceOfCompaniesSortedByTradingVolume(int size){

        return this.companyRepository.findByVerifiedStateOrderByTradingVolumeDesc(true,new PageRequest(0,size));
    }

    public List<Company> getSliceOfCompaniesSortedByTradingVolumeForSector(int sectorId,int size){

        return this.companyRepository.findByCompanySectorIdAndVerifiedStateOrderByTradingVolumeDesc(sectorId,true,new PageRequest(0,size));
    }





    public List<Company> getCompaniesByNameLike(String companyName) {
        return this.companyRepository.findByVerifiedStateAndCompanyNameContaining(true,companyName);
    }

    public Company getCurrentNormalUserInfo(String username) {
        return this.companyRepository.findByUsername(username);
    }

    public List<Company> getAllVerifiedCompanies() {
        return this.companyRepository.findByVerifiedState(true);
    }

    public List<Company> getAllUnVerifiedCompanies() {
        return this.companyRepository.findByVerifiedState(false);
    }

    public Company getCompanyByUsername(String username) {
        return this.companyRepository.findByUsername(username);
    }
}
