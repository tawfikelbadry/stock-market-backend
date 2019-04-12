package com.gradteam.porsaty.controller;

import com.gradteam.porsaty.model.News;
import com.gradteam.porsaty.model.NormalUser;
import com.gradteam.porsaty.service.NewsService;
import com.gradteam.porsaty.service.NormalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.*;

/**
 * Created by tawfik on 3/20/2018.
 */
@RestController
@RequestMapping("/api/normal-users")
public class NormalUserResource {

    @Autowired
    private NormalUserService normalUserService;

    @Autowired
    private NewsService newsService;

    @GetMapping("")
    public List<NormalUser> getAllUsers(){
        return this.normalUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<NormalUser> getUser(@PathVariable("id") long id){
        return this.normalUserService.getUser(id);
    }


    //return -1 if username not available
    //return -2 if email not available
    // return id of new user if registerd
    @PostMapping("/register")
    public ResponseEntity saveUser(@RequestBody NormalUser user, HttpServletRequest request){
        Map<String,Long> response=new HashMap<>();
        response.put("response",this.normalUserService.createNormalUser(user));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add/image")
    public ResponseEntity saveUserImage(@RequestParam("id") Long id,
                                        HttpServletResponse response, HttpServletRequest request
    ){
        try {
            NormalUser user = normalUserService.getUser(id).get();
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = multipartRequest.getFileNames();
            MultipartFile multipartFile = multipartRequest.getFile(it.next());


            byte[] bytes = multipartFile.getBytes();
            user.setImage(bytes);
            this.normalUserService.saveUser(user);
            return new ResponseEntity("Upload Success!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Upload failed!", HttpStatus.BAD_REQUEST);
        }
    }


    // show image of the Normal user by the normal user id
    @GetMapping(value = "/image/{userId}",produces = MediaType.IMAGE_JPEG_VALUE )
    public ResponseEntity showNormalUserImage(@PathVariable("userId") long userId){
        HttpHeaders headers = new HttpHeaders();
        byte[] media = this.normalUserService.getNormalUserImage(userId);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;

    }

    @GetMapping(value = "/image/current",produces = MediaType.IMAGE_JPEG_VALUE )
    public ResponseEntity showCurrentUserImage(Principal principal){
        HttpHeaders headers = new HttpHeaders();
        byte[] media = this.normalUserService.getNormalUserImage(principal.getName());
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;

    }

    @GetMapping("/fullname")
    public ResponseEntity getCurrentNormalUserName(Principal principal){
        Map<String,String> response=new HashMap<>();
        response.put("name",this.normalUserService.getCurrentUserName(principal.getName()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/totalMoney")
    public ResponseEntity getCurrentUserTotalMoney(Principal principal){
        Map<String,Double> response=new HashMap<>();
        response.put("totalMoney",this.normalUserService.getCurrentUserTotalMoney(principal.getName()));
        return ResponseEntity.ok(response);

    }

    @GetMapping("/current/UserId")
    public ResponseEntity getUserIdByUserName(Principal principal){
        Map<String,Long> userId=new HashMap<>();
        userId.put("CurrentuserId",this.normalUserService.getCurrentUserId(principal.getName()));
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/following/current-user")
    public ResponseEntity getFollowingCompaniesByUserId(Principal principal){
        return ResponseEntity.ok(this.normalUserService.getUserFollowingCompanies(principal.getName()));
    }

    @PostMapping("/follow/{companyId}")
    public ResponseEntity followCompany(Principal principal,@PathVariable("companyId") long companyId){
        int result=this.normalUserService.followCompany(principal.getName(),companyId);
        Map<String,Integer> response=new HashMap<>();
        response.put("result",result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/unfollow/{companyId}")
    public ResponseEntity unfollowCompany(Principal principal,@PathVariable("companyId") long companyId){
        int result=this.normalUserService.unfollowCompany(principal.getName(),companyId);
        Map<String,Integer> response=new HashMap<>();
        response.put("result",result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-follow/{companyId}")
    public ResponseEntity checkIfCurrentUserFollowCompany(Principal principal,@PathVariable("companyId")long companyId){
       Map<String,Boolean> isFollowing=new HashMap<>();
       isFollowing.put("isFollow",this.normalUserService.checkCompanyFollowing(principal.getName(),companyId));
       return ResponseEntity.ok(isFollowing);
    }


    @GetMapping("/current/following-news")
    public ResponseEntity getCurrentUserFollowingCompaniesNews(Principal principal){
        List<News> fnews= this.newsService.getFollowingCompaniesNews(principal.getName());
        return ResponseEntity.ok(fnews);
    }


}
