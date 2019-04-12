package com.gradteam.porsaty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by tawfik on 5/1/2018.
 */
@Controller
public class StaticContentController {

    @GetMapping("/company-documents/{companyUserName}")
    public String showCompanyDocument(@PathVariable("companyUserName")String companyUserName){
        return "companies-documents/"+companyUserName+".pdf";
    }
}
