package com.gradteam.porsaty.service;

import com.gradteam.porsaty.config.SecurityUtility;
import com.gradteam.porsaty.model.Company;
import com.gradteam.porsaty.model.NormalUser;
import com.gradteam.porsaty.model.security.Role;
import com.gradteam.porsaty.model.security.UserRole;
import com.gradteam.porsaty.repository.CompanyRepository;
import com.gradteam.porsaty.repository.NormalUserRepository;
import com.gradteam.porsaty.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by tawfik on 3/20/2018.
 */
@Service
public class NormalUserService {

    private static final Logger LOG= LoggerFactory.getLogger(UserSecurityService.class);


    private CompanyRepository companyRepository;
    private NormalUserRepository normalUserRepository;
    private RoleRepository roleRepository;

    @Autowired
    public NormalUserService(NormalUserRepository normalUserRepository, RoleRepository roleRepository,CompanyRepository companyRepository) {
        this.normalUserRepository = normalUserRepository;
        this.roleRepository = roleRepository;
        this.companyRepository=companyRepository;
    }

    public List<NormalUser> getAllUsers(){
        return (List<NormalUser>)this.normalUserRepository.findAll();
    }

    public Optional<NormalUser> getUser(long id){
        return this.normalUserRepository.findById(id);
    }

    public NormalUser saveUser(NormalUser user){
        return this.normalUserRepository.save(user);
    }

    //return -1 if username not available
    //return -2 if email not available
    // Register new Normal User
    public long createNormalUser(NormalUser user){

        NormalUser localUser=normalUserRepository.findByUsername(user.getUsername());
        if(null!=localUser){
            LOG.warn("this username {} already exist , no thing happen",user.getUsername());
            return -1;
        }else if(null!=normalUserRepository.findByEmail(user.getEmail())){
            LOG.warn("this Email {} already exist , no thing happen",user.getEmail());
            return -2;
        }else {

            // encode the password
            user.setPassword(SecurityUtility.passwordEncoder().encode(user.getPassword()));
            // get Normal user Role
            Role roleUser = roleRepository.findByName("ROLE_USER");

            // add Normal user Role to the new User
            Set<UserRole> userRoleSet=new HashSet<>();
            userRoleSet.add(new UserRole(user,roleUser));
            user.setUserRoles(userRoleSet);

            // save the user
            localUser=this.normalUserRepository.save(user);

        }

        return localUser.getId();
    }


    public byte[] getNormalUserImage(long normalUserId){
        return this.normalUserRepository.getNormalUserImage(normalUserId);
    }

    public byte[] getNormalUserImage(String username){
        return this.normalUserRepository.getcurrentNormalUserImage(username);
    }

    public String getCurrentUserName(String username) {
        return this.normalUserRepository.findFullNameByUsername(username);
    }

    public Double getCurrentUserTotalMoney(String username) {
        return this.normalUserRepository.findTotalMoneyByUsername(username);
    }

    public Long getCurrentUserId(String username) {
        return this.normalUserRepository.findUserIdByUsername(username);
    }

    public List<Company> getUserFollowingCompanies(String username) {
        return this.companyRepository.findByFollwersUsername(username);
    }

    // return 1 if follow successufully
    // return 0 if some error happend
    public int followCompany(String username, long companyId) {
        try{
           NormalUser loggedUserr= this.normalUserRepository.findByUsername(username);
           Company company=companyRepository.findById(companyId).get();
            loggedUserr.getFollwingCompanies().add(company);

           normalUserRepository.save(loggedUserr);
           return 1;
        }catch (Exception e){
            return 0;
        }

    }

    public int unfollowCompany(String username, long companyId) {
        try{
            NormalUser loggeduser= this.normalUserRepository.findByUsername(username);
            Company company=companyRepository.findById(companyId).get();
            loggeduser.getFollwingCompanies().remove(company);

            normalUserRepository.save(loggeduser);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public boolean checkCompanyFollowing(String username, long companyId) {
        NormalUser user= this.normalUserRepository.findByUsername(username);
        Set<Company> followingCompanies= user.getFollwingCompanies();
        for(Company c:followingCompanies){
            if(c.getId()==companyId){
                return true;
            }
        }
        return false;

    }
}
