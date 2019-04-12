package com.gradteam.porsaty.controller;

import com.gradteam.porsaty.model.Company;
import com.gradteam.porsaty.model.News;
import com.gradteam.porsaty.service.CompanyService;
import com.gradteam.porsaty.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.security.Principal;
import java.util.*;

/**
 * Created by tawfik on 4/27/2018.
 */
@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = "http://localhost:4200")
public class NewsResource {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("")
    public ResponseEntity showNewsOrderd(){
        return ResponseEntity.ok(newsService.getAllNewsOrderd());
    }

    @GetMapping("/size")
    public ResponseEntity getNewsSize(){
        Map<String,Long> response=new HashMap<>();
        response.put("count",this.newsService.getNewsSize());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity showNewsPage(@PathVariable("page") int page,@PathVariable("size") int size){
        return ResponseEntity.ok(this.newsService.getNewsPageWithSize(page,size));
    }

    @GetMapping("/top6-1")
    public ResponseEntity showLatest6NewsOrderd(){
        List<News> newss=  newsService.getLatest6NewsOrderd();
        return ResponseEntity.ok(newss.subList(1,newss.size()));
    }

    @GetMapping("/top1")
    public News getLatestNews(){
        return this.newsService.getLatestOneNews();
    }

    @GetMapping("/{newsId}")
    public ResponseEntity showOneNews(@PathVariable("newsId")long id){
        try {
            return ResponseEntity.ok(this.newsService.getOneNewById(id));
        } catch (Exception e) {
            return new ResponseEntity("this news not available", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/company/{id}")
    public ResponseEntity showCompanyNewsOrderd(@PathVariable long id){
        return ResponseEntity.ok(newsService.getCompanyNewsOrderd(id));
    }




    @PostMapping("/add/{companyId}")
    public ResponseEntity addNew(@PathVariable("companyId")long companyId, @RequestBody News news){
        long compid=this.newsService.addNews(companyId,news);
        if(compid>0){

            return ResponseEntity.created(URI.create("/api/news/"+companyId)).build();
        }
        return ResponseEntity.badRequest().body("news not saved successfully");
    }

    @PostMapping("/add")
    public ResponseEntity addNewForCurrentCompany(Principal principal, @RequestBody News news){
        news.setDate(new Date());
        long compid=this.newsService.addNews(principal.getName(),news);

        if(compid>0){
            Map<String,Long> response=new HashMap();
            response.put("newsId",compid);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body("news not saved successfully");
    }

    @RequestMapping(value="/add/image", method=RequestMethod.POST)
    public ResponseEntity upload(
            @RequestParam("id") Long id,
            HttpServletResponse response, HttpServletRequest request
    ){
        try {
            News news = newsService.getOneNewById(id);
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = multipartRequest.getFileNames();
            MultipartFile multipartFile = multipartRequest.getFile(it.next());
            String fileName = id+".png";


            byte[] bytes = multipartFile.getBytes();
            news.setImage(bytes);
            this.newsService.updateNews(news);

            return new ResponseEntity("Upload Success!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Upload failed!", HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/update")
    public ResponseEntity updateNew(Principal principal,@RequestBody News news){
        News newsTemp=new News();

        try {
           newsTemp= this.newsService.getOneNewById(news.getId());
           newsTemp.setTitle(news.getTitle());
           newsTemp.setBody(news.getBody());
           this.newsService.updateNews(newsTemp);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Long> response=new HashMap();
        response.put("newId",newsTemp.getId());
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/remove/{newsId}")
    public ResponseEntity deleteNews(@PathVariable("newsId")long newsId){
        this.newsService.deleteNews(newsId);
        return ResponseEntity.ok("news with Id "+newsId+" has removed");
    }


    // show image of the News by the news id
    @GetMapping(value = "/image/{newsId}",produces = MediaType.IMAGE_JPEG_VALUE )
    public ResponseEntity showNewsImage(@PathVariable("newsId") long newsId){
        HttpHeaders headers = new HttpHeaders();
        byte[] media = this.newsService.getNewsImageWithId(newsId);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;

    }


    @GetMapping("/current/news")
    public ResponseEntity getCurrentCompanyNews(Principal principal){
        List<News> companyNews= this.newsService.getCurrentCompanyNewsOrderd(principal.getName());
        return ResponseEntity.ok(companyNews);
    }

}
