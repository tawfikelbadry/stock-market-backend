package com.gradteam.porsaty.service;

import com.gradteam.porsaty.model.User;
import com.gradteam.porsaty.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by tawfik on 4/23/2018.
 */
@Service
public class UserSecurityService implements UserDetailsService {

    private static final Logger LOG= LoggerFactory.getLogger(UserSecurityService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("username : "+username);
        User user=userRepository.findByUsername(username);
        if(null==user){
            LOG.warn("username {} not found",username);
            throw new UsernameNotFoundException("Username "+username+" not found");
        }else{
            LOG.warn("Welcome {} you have logged in",username);

        }
        return user;
    }

}
