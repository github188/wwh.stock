package cn.hzstk.securities.shop.domain;
import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "shop_shop")
public class Shop extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //用户编号
    @Column(name = "uid")
    private Integer uid;

    //用户名
    @Column(name = "username")
    private String username;

    //店铺类型
    @Column(name = "shop_type")
    private Integer shopType;

    //店铺名称
    @Column(name = "shop_name")
    private String shopName;

    //店铺经营范围
    @Column(name = "service_range")
    private String serviceRange;

    //店铺描述
    @Column(name = "shop_desc")
    private String shopDesc;

    //工作职务
    @Column(name = "work")
    private String work;

    //工作年限
    @Column(name = "work_year")
    private Integer workYear;

    //店铺SEO关键词
    @Column(name = "keyword")
    private String keyword;

    //店铺背景图
    @Column(name = "shop_background")
    private String shopBackground;

    //店铺LOGO
    @Column(name = "logo")
    private String logo;

    //店铺导航条banner
    @Column(name = "banner")
    private String banner;

    //店铺简介
    @Column(name = "shop_slogans")
    private String shopSlogans;

    //店铺皮肤
    @Column(name = "shop_skin")
    private String shopSkin;

    //背景样式
    @Column(name = "shop_backstyle")
    private String shopBackstyle;

    //店铺字体
    @Column(name = "shop_font")
    private String shopFont;

    //是否激活店铺
    @Column(name = "shop_active")
    private String shopActive;

    //是否关闭
    @Column(name = "is_close")
    private Integer isClose;

    //浏览次数
    @Column(name = "views")
    private Integer views;

    //关注次数
    @Column(name = "focus_num")
    private Integer focusNum;

    //开通时间
    @Column(name = "on_time")
    private Integer onTime;

    //主页banner
    @Column(name = "homebanner")
    private String homebanner;

    //销量
    @Column(name = "on_sale")
    private Integer onSale;

    //店铺状态
    @Column(name = "shop_status")
    private Integer shopStatus;

    //店铺域名
    @Column(name = "domain")
    private String domain;

    //SEO标题
    @Column(name = "seo_title")
    private String seoTitle;

    //SEO关键字
    @Column(name = "seo_keyword")
    private String seoKeyword;

    //SEO描述
    @Column(name = "seo_desc")
    private String seoDesc;

    //省份
    @Column(name = "province")
    private Integer province;

    //城市
    @Column(name = "city")
    private Integer city;

    //地区
    @Column(name = "area")
    private Integer area;

    //详细地址
    @Column(name = "address")
    private String address;

    //坐标
    @Column(name = "coordinate")
    private String coordinate;

    public void setUid(Integer uid){
        this.uid = uid;
    }

    public Integer getUid(){
        return uid;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setShopType(Integer shopType){
        this.shopType = shopType;
    }

    public Integer getShopType(){
        return shopType;
    }

    public void setShopName(String shopName){
        this.shopName = shopName;
    }

    public String getShopName(){
        return shopName;
    }

    public void setServiceRange(String serviceRange){
        this.serviceRange = serviceRange;
    }

    public String getServiceRange(){
        return serviceRange;
    }

    public void setShopDesc(String shopDesc){
        this.shopDesc = shopDesc;
    }

    public String getShopDesc(){
        return shopDesc;
    }

    public void setWork(String work){
        this.work = work;
    }

    public String getWork(){
        return work;
    }

    public void setWorkYear(Integer workYear){
        this.workYear = workYear;
    }

    public Integer getWorkYear(){
        return workYear;
    }

    public void setKeyword(String keyword){
        this.keyword = keyword;
    }

    public String getKeyword(){
        return keyword;
    }

    public void setShopBackground(String shopBackground){
        this.shopBackground = shopBackground;
    }

    public String getShopBackground(){
        return shopBackground;
    }

    public void setLogo(String logo){
        this.logo = logo;
    }

    public String getLogo(){
        return logo;
    }

    public void setBanner(String banner){
        this.banner = banner;
    }

    public String getBanner(){
        return banner;
    }

    public void setShopSlogans(String shopSlogans){
        this.shopSlogans = shopSlogans;
    }

    public String getShopSlogans(){
        return shopSlogans;
    }

    public void setShopSkin(String shopSkin){
        this.shopSkin = shopSkin;
    }

    public String getShopSkin(){
        return shopSkin;
    }

    public void setShopBackstyle(String shopBackstyle){
        this.shopBackstyle = shopBackstyle;
    }

    public String getShopBackstyle(){
        return shopBackstyle;
    }

    public void setShopFont(String shopFont){
        this.shopFont = shopFont;
    }

    public String getShopFont(){
        return shopFont;
    }

    public void setShopActive(String shopActive){
        this.shopActive = shopActive;
    }

    public String getShopActive(){
        return shopActive;
    }

    public void setIsClose(Integer isClose){
        this.isClose = isClose;
    }

    public Integer getIsClose(){
        return isClose;
    }

    public void setViews(Integer views){
        this.views = views;
    }

    public Integer getViews(){
        return views;
    }

    public void setFocusNum(Integer focusNum){
        this.focusNum = focusNum;
    }

    public Integer getFocusNum(){
        return focusNum;
    }

    public void setOnTime(Integer onTime){
        this.onTime = onTime;
    }

    public Integer getOnTime(){
        return onTime;
    }

    public void setHomebanner(String homebanner){
        this.homebanner = homebanner;
    }

    public String getHomebanner(){
        return homebanner;
    }

    public void setOnSale(Integer onSale){
        this.onSale = onSale;
    }

    public Integer getOnSale(){
        return onSale;
    }

    public void setShopStatus(Integer shopStatus){
        this.shopStatus = shopStatus;
    }

    public Integer getShopStatus(){
        return shopStatus;
    }

    public void setDomain(String domain){
        this.domain = domain;
    }

    public String getDomain(){
        return domain;
    }

    public void setSeoTitle(String seoTitle){
        this.seoTitle = seoTitle;
    }

    public String getSeoTitle(){
        return seoTitle;
    }

    public void setSeoKeyword(String seoKeyword){
        this.seoKeyword = seoKeyword;
    }

    public String getSeoKeyword(){
        return seoKeyword;
    }

    public void setSeoDesc(String seoDesc){
        this.seoDesc = seoDesc;
    }

    public String getSeoDesc(){
        return seoDesc;
    }

    public void setProvince(Integer province){
        this.province = province;
    }

    public Integer getProvince(){
        return province;
    }

    public void setCity(Integer city){
        this.city = city;
    }

    public Integer getCity(){
        return city;
    }

    public void setArea(Integer area){
        this.area = area;
    }

    public Integer getArea(){
        return area;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setCoordinate(String coordinate){
        this.coordinate = coordinate;
    }

    public String getCoordinate(){
        return coordinate;
    }
}
