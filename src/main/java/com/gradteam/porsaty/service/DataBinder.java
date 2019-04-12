package com.gradteam.porsaty.service;

import com.gradteam.porsaty.config.SecurityUtility;
import com.gradteam.porsaty.model.*;
import com.gradteam.porsaty.model.security.Role;
import com.gradteam.porsaty.model.security.UserRole;
import com.gradteam.porsaty.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DataBinder {

    private RoleRepository roleRepository;

    private CompanyService companyService;
    private SectorService sectorService;
    private StockService stockService;

    private NormalUserService normalUserService;
    private StockPricesRepository stockPricesRepository;



    @Autowired
    public DataBinder(CompanyService companyService, SectorService sectorService, StockService stockService
                      , NormalUserService normalUserService, StockPricesRepository stockPricesRepository
                      ,RoleRepository roleRepository) {
        this.roleRepository=roleRepository;
        this.companyService = companyService;
        this.sectorService = sectorService;
        this.stockService=stockService;
        this.normalUserService = normalUserService;
        this.stockPricesRepository=stockPricesRepository;
    }

//    @PostConstruct
    private void saveBasicData(){
        // add the Roles
        createRoles();
        // add some users
        createSomeNormalUsers();
        // save the sectors
        createSectors();
        // save some companies
        createSomeCompanies();

    }


    /// create Application Rules
    private void createRoles(){
        Role RoleNormal=new Role(1,"ROLE_USER");
        Role RoleCompany=new Role(2,"ROLE_COMPANY");
        Role RoleAdmin=new Role(3,"ROLE_ADMIN");
        this.roleRepository.save(RoleNormal);
        this.roleRepository.save(RoleCompany);
        this.roleRepository.save(RoleAdmin);

    }

    /// create some Normal users
    private void createSomeNormalUsers(){
        NormalUser normalUser1=new NormalUser();
        normalUser1.setId(1);
        normalUser1.setFullName("Tawfik Mahmoud Elbadry");
        normalUser1.setUsername("tawfik123");
        normalUser1.setEmail("tawfeeq.elbadry@gmail.com");
        normalUser1.setPassword(SecurityUtility.passwordEncoder().encode("123000"));
        normalUser1.setSSN("14526987452365");
        this.normalUserService.createNormalUser(normalUser1);


        NormalUser normalUser2=new NormalUser();
        normalUser2.setId(2);
        normalUser2.setFullName("Ibrahim Elfakhrany");
        normalUser2.setUsername("ibrahim8");
        normalUser2.setEmail("ibrahim.elfakhrany@gmail.com");
        normalUser2.setPassword(SecurityUtility.passwordEncoder().encode("74123"));
        normalUser2.setSSN("1654654646546");
        this.normalUserService.createNormalUser(normalUser2);

    }

    // save the sectors
    private void createSectors(){
        MarketSector sector1=new MarketSector(1,"البنوك");
        MarketSector sector2=new MarketSector(2,"الموارد الاساسية");
        MarketSector sector3=new MarketSector(3,"خدمات ومنتجات صناعية وسيارات");
        MarketSector sector4=new MarketSector(4,"خدمات مالية ( باستثناء البنوك )");
        MarketSector sector5=new MarketSector(5,"الاغذية والمشروبات");
        MarketSector sector6=new MarketSector(6,"الرعاية الصحية والادوية");
        MarketSector sector7=new MarketSector(7,"العقارات");
        MarketSector sector8=new MarketSector(8,"الكيماويات");
        MarketSector sector9=new MarketSector(9,"المنتجات المنزلية والشخصية");
        MarketSector sector10=new MarketSector(10,"التشييد ومواد البناء");
        MarketSector sector11=new MarketSector(11,"الاتصالات");
        MarketSector sector12=new MarketSector(12,"السياحة والترفيه");

        sectorService.save(sector1);
        sectorService.save(sector2);
        sectorService.save(sector3);
        sectorService.save(sector4);
        sectorService.save(sector5);
        sectorService.save(sector6);
        sectorService.save(sector7);
        sectorService.save(sector8);
        sectorService.save(sector9);
        sectorService.save(sector10);
        sectorService.save(sector11);
        sectorService.save(sector12);

    }

    // create some companies
    private void createSomeCompanies(){
        
        MarketSector BankSector=sectorService.findOne(1);
        // create company 1
        Company company1=new Company();
        company1.setCompanyName("بنك التعمير والاسكان (HDBK)");
        company1.setCompanyWorkField("يعمل البنك منذ اليوم الأول لإنشائه على المساهمة بدور ملموس في التخفيف من حدة مشكلة الإسكان عن طريق تضييق الفجوة بين العرض والطلب من الوحدات السكنية، وذلك بمختلف مدن الجمهورية من خلال إنشاء مجموعة متكاملة من المشروعات الإسكانية.");
        Date com1StartDate=new Date(79,5,29);
        company1.setCompanyStartDate(com1StartDate);
        company1.setEmail("hdbk@gmail.com");
        company1.setUsername("hdbk123");
        company1.setPassword(SecurityUtility.passwordEncoder().encode("123000"));
        company1.setCompanySector(BankSector);
        company1.setCompanyCapital(1265000000);
        company1.setCompanyCurrentTotalStocksNumber(126500000);
        company1.setCompanyOwnedStocksNuber(965000000);

        // get Company  Role
        Role roleCompany = roleRepository.findByName("ROLE_COMPANY");

        // add Company Role to the new Compant
        Set<UserRole> companyRoleSet=new HashSet<>();
        companyRoleSet.add(new UserRole(company1,roleCompany));
        company1.setUserRoles(companyRoleSet);

        //create stock 1
        Stock stock1=new Stock();
        stock1.setId(1);
        stock1.setNamedValue(10);
        stock1.setMarketValue(6791785000.0);
        stock1.setBookValue(34.82);
        stock1.setBokValueMultiplier(1.55);
        stock1.setGainOfStock(8.74);
        stock1.setMultiplierGainofStock(5.27);
        stock1.setLatestPrice(53.69);
        stock1.setLastClosePrice(53.98);
        stock1.setOpenPrice(53.98);
        stock1.setMaxPrice(54.01);
        stock1.setMinPrice(53.05);
        stock1.setTradingCurrency("دولار امريكي");

        stock1.setCompany(company1);
        // save it
        this.stockService.save(stock1);


        MarketSector Sector2=sectorService.findOne(2);
        // create company 2
        Company company2=new Company();
        company2.setCompanyName("العامة لصناعة الورق (RAKT)");
        company2.setCompanyWorkField("تقوم الشركة بصناعة ورق الكتابة والكرافت والطباعة من المخلفات الزراعية وكذلك إنتاج الكرتون متعدد الطبقات. بالإضافة لإنتاج الشركة للكرتون عالى الجودة لأغراض التعبئة والتغليف.");
        Date com2StartDate=new Date(58,0,1);
        company2.setCompanyStartDate(com2StartDate);
        company2.setEmail("rakt@gmail.com");
        company2.setUsername("rakt123");
        company2.setPassword(SecurityUtility.passwordEncoder().encode("123000"));
        company2.setCompanySector(Sector2);
        company2.setCompanyCapital(150000000);
        company2.setCompanyCurrentTotalStocksNumber(30000000);
        company2.setCompanyOwnedStocksNuber(17000000);


        // add Company Role to the new Compant
        Set<UserRole> company2RoleSet=new HashSet<>();
        company2RoleSet.add(new UserRole(company2,roleCompany));
        company2.setUserRoles(company2RoleSet);

        //create stock 1
        Stock stock2=new Stock();
        stock2.setId(2);
        stock2.setNamedValue(5);
        stock2.setMarketValue(232500000);
        stock2.setBookValue(-5.51);
        stock2.setBokValueMultiplier(-1.54);
        stock2.setGainOfStock(-2.11);
        stock2.setMultiplierGainofStock(-3.74);
        stock2.setLatestPrice(7.75);
        stock2.setLastClosePrice(7.69);
        stock2.setOpenPrice(7.69);
        stock2.setMaxPrice(7.82);
        stock2.setMinPrice(7.65);
        stock2.setTradingCurrency("جنيه مصري");

        stock2.setCompany(company2);

        stockService.save(stock2);

    }






}
