package com.gradteam.porsaty.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class UserAdmin extends User{


    private String phone;
    private String fullName;

    @OneToMany(mappedBy = "userAdmin")
    Set<CompanyReview> companyReviewList=new HashSet<>();



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<CompanyReview> getCompanyReviewList() {
        return companyReviewList;
    }

    public void setCompanyReviewList(Set<CompanyReview> companyReviewList) {
        this.companyReviewList = companyReviewList;
    }
}
