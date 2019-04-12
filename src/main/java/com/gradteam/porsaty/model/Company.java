package com.gradteam.porsaty.model;


import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Company extends User  implements Serializable {


    @Column(nullable = false,unique = true)
    @NotNull
    private String companyName;

    @JsonIgnore
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    private int companyCurrentTotalStocksNumber;
    private int companyOwnedStocksNuber;
    private long companyCapital;

    @Column(columnDefinition = "TEXT")
    private String companyWorkField;

    @Temporal(TemporalType.DATE)
    private Date companyStartDate;

    private String companyFinancialYearStart;

    private long tradingVolume;

    private double tradingValue;

    private boolean verifiedState=false;

    @ManyToOne(fetch = FetchType.EAGER)
    private MarketSector companySector;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "company")
    private Stock stock;

    @OneToOne(mappedBy = "company",fetch = FetchType.LAZY)
    @JsonIgnore
    private CompanyReview companyReview;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "Companies_followers",
            joinColumns = {@JoinColumn(name = "companyId")}
            ,inverseJoinColumns = {@JoinColumn(name = "normalUserId")})
    private Set<NormalUser> follwers=new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private Set<News> newsSet=new HashSet<>();

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyCurrentTotalStocksNumber() {
        return companyCurrentTotalStocksNumber;
    }

    public void setCompanyCurrentTotalStocksNumber(int companyCurrentTotalStocksNumber) {
        this.companyCurrentTotalStocksNumber = companyCurrentTotalStocksNumber;
    }

    public int getCompanyOwnedStocksNuber() {
        return companyOwnedStocksNuber;
    }

    public void setCompanyOwnedStocksNuber(int companyOwnedStocksNuber) {
        this.companyOwnedStocksNuber = companyOwnedStocksNuber;
    }

    public long getCompanyCapital() {
        return companyCapital;
    }

    public void setCompanyCapital(long companyCapital) {
        this.companyCapital = companyCapital;
    }

    public String getCompanyWorkField() {
        return companyWorkField;
    }

    public void setCompanyWorkField(String companyWorkField) {
        this.companyWorkField = companyWorkField;
    }

    public Date getCompanyStartDate() {
        return companyStartDate;
    }

    public void setCompanyStartDate(Date companyStartDate) {
        this.companyStartDate = companyStartDate;
    }

    public String getCompanyFinancialYearStart() {
        return companyFinancialYearStart;
    }

    public void setCompanyFinancialYearStart(String companyFinancialYearStart) {
        this.companyFinancialYearStart = companyFinancialYearStart;
    }

    public long getTradingVolume() {
        return tradingVolume;
    }

    public void setTradingVolume(long tradingVolume) {
        this.tradingVolume = tradingVolume;
    }

    public double getTradingValue() {
        return tradingValue;
    }

    public void setTradingValue(double tradingValue) {
        this.tradingValue = tradingValue;
    }

    public boolean isVerifiedState() {
        return verifiedState;
    }

    public void setVerifiedState(boolean verifiedState) {
        this.verifiedState = verifiedState;
    }

    public MarketSector getCompanySector() {
        return companySector;
    }

    public void setCompanySector(MarketSector companySector) {
        this.companySector = companySector;
    }

    public CompanyReview getCompanyReview() {
        return companyReview;
    }

    public void setCompanyReview(CompanyReview companyReview) {
        this.companyReview = companyReview;
    }

    public Set<NormalUser> getFollwers() {
        return follwers;
    }

    public void setFollwers(Set<NormalUser> follwers) {
        this.follwers = follwers;
    }

    public Set<News> getNewsSet() {
        return newsSet;
    }

    public void setNewsSet(Set<News> newsSet) {
        this.newsSet = newsSet;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Company{" +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
