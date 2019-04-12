package com.gradteam.porsaty.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class NormalUser extends User{

    @Column(length = 60)
    private String fullName;

    private String SSN;

    private double totalMoney;

    @JsonIgnore
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserStocks> userStocks;

    @JsonIgnore
    @OneToMany(mappedBy = "seller")
    private Set<TradingOperation> sellOperations=new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "buyer")
    private Set<TradingOperation> buyOperations=new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    Set<TradingOrder> orders=new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "Companies_followers",
            joinColumns = {@JoinColumn(name = "normalUserId")}
            ,inverseJoinColumns = {@JoinColumn(name = "companyId")})
    Set<Company> follwingCompanies=new HashSet<>();

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<UserStocks> getUserStocks() {
        return userStocks;
    }

    public void setUserStocks(List<UserStocks> userStocks) {
        this.userStocks = userStocks;
    }

    public Set<TradingOperation> getSellOperations() {
        return sellOperations;
    }

    public void setSellOperations(Set<TradingOperation> sellOperations) {
        this.sellOperations = sellOperations;
    }

    public Set<TradingOperation> getBuyOperations() {
        return buyOperations;
    }

    public void setBuyOperations(Set<TradingOperation> buyOperations) {
        this.buyOperations = buyOperations;
    }

    public Set<TradingOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<TradingOrder> orders) {
        this.orders = orders;
    }

    public Set<Company> getFollwingCompanies() {
        return follwingCompanies;
    }

    public void setFollwingCompanies(Set<Company> follwingCompanies) {
        this.follwingCompanies = follwingCompanies;
    }


}
