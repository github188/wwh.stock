package cn.hzstk.securities.shop.domain;
import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "shop_service")
public class Service extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //模型编号
    @Column(name = "model_id")
    private Integer modelId;

    //商品类型
    @Column(name = "service_type")
    private Integer serviceType;

    //店铺编号
    @Column(name = "shop_id")
    private Integer shopId;

    //用户编号
    @Column(name = "uid")
    private Integer uid;

    //用户名
    @Column(name = "username")
    private String username;

    //收益率
    @Column(name = "profit_rate")
    private Integer profitRate;

    //行业编号
    @Column(name = "indus_id")
    private Integer indusId;

    //行业父编号
    @Column(name = "indus_pid")
    private Integer indusPid;

    //行业路径
    @Column(name = "indus_path")
    private String indusPath;

    //自定义分类
    @Column(name = "cus_cate_id")
    private Integer cusCateId;

    //商品标题
    @Column(name = "title")
    private String title;

    //商品价格
    @Column(name = "price")
    private Integer price;

    //单价
    @Column(name = "unite_price")
    private String unitePrice;

    //服务时间
    @Column(name = "service_time")
    private Integer serviceTime;

    //服务单位时间（如：/天/时）
    @Column(name = "unit_time")
    private String unitTime;

    //对象名称
    @Column(name = "obj_name")
    private String objName;

    //商品图片
    @Column(name = "pic")
    private String pic;

    //商品封面
    @Column(name = "ad_pic")
    private String adPic;

    //区域范围
    @Column(name = "area_range")
    private String areaRange;

    //商品关键词
    @Column(name = "key_word")
    private String keyWord;

    //商品交付方式outside|inside
    @Column(name = "submit_method")
    private String submitMethod;

    //作品附件
    @Column(name = "file_path")
    private String filePath;

    //作品描述
    @Column(name = "content")
    private String content;

    //作品发布时间
    @Column(name = "on_time")
    private Integer onTime;

    //是否终止
    @Column(name = "is_stop")
    private Integer isStop;

    //销量
    @Column(name = "sale_num")
    private Integer saleNum;

    //关注数
    @Column(name = "focus_num")
    private Integer focusNum;

    //评价数
    @Column(name = "mark_num")
    private Integer markNum;

    //留言数
    @Column(name = "leave_num")
    private Integer leaveNum;

    //浏览
    @Column(name = "views")
    private Integer views;

    //付费项
    @Column(name = "pay_item")
    private String payItem;

    //付费费用
    @Column(name = "att_cash")
    private Integer attCash;

    //刷新时间
    @Column(name = "refresh_time")
    private Integer refreshTime;

    //唯一数
    @Column(name = "unique_num")
    private String uniqueNum;

    //总销量
    @Column(name = "total_sale")
    private Integer totalSale;

    //作品/服务状态
    @Column(name = "service_status")
    private Integer serviceStatus;

    //是否推荐
    @Column(name = "is_top")
    private Integer isTop;

    //地图标记点
    @Column(name = "point")
    private String point;

    //城市
    @Column(name = "city")
    private String city;

    //使用付费项目时间
    @Column(name = "payitem_time")
    private String payitemTime;

    //服务的服务时间
    @Column(name = "exist_time")
    private Integer existTime;

    //允许最大时间
    @Column(name = "confirm_max")
    private Integer confirmMax;

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

    //地区
    @Column(name = "area")
    private Integer area;

    //服务置顶状态
    @Column(name = "goodstop")
    private Integer goodstop;

    //当值为"custom"表示自定义,当值为"forever"表示无期限,当值为数字表示期限为多少天
    @Column(name = "overdue_type")
    private String overdueType;

    public void setModelId(Integer modelId){
        this.modelId = modelId;
    }

    public Integer getModelId(){
        return modelId;
    }

    public void setServiceType(Integer serviceType){
        this.serviceType = serviceType;
    }

    public Integer getServiceType(){
        return serviceType;
    }

    public void setShopId(Integer shopId){
        this.shopId = shopId;
    }

    public Integer getShopId(){
        return shopId;
    }

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

    public void setProfitRate(Integer profitRate){
        this.profitRate = profitRate;
    }

    public Integer getProfitRate(){
        return profitRate;
    }

    public void setIndusId(Integer indusId){
        this.indusId = indusId;
    }

    public Integer getIndusId(){
        return indusId;
    }

    public void setIndusPid(Integer indusPid){
        this.indusPid = indusPid;
    }

    public Integer getIndusPid(){
        return indusPid;
    }

    public void setIndusPath(String indusPath){
        this.indusPath = indusPath;
    }

    public String getIndusPath(){
        return indusPath;
    }

    public void setCusCateId(Integer cusCateId){
        this.cusCateId = cusCateId;
    }

    public Integer getCusCateId(){
        return cusCateId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setPrice(Integer price){
        this.price = price;
    }

    public Integer getPrice(){
        return price;
    }

    public void setUnitePrice(String unitePrice){
        this.unitePrice = unitePrice;
    }

    public String getUnitePrice(){
        return unitePrice;
    }

    public void setServiceTime(Integer serviceTime){
        this.serviceTime = serviceTime;
    }

    public Integer getServiceTime(){
        return serviceTime;
    }

    public void setUnitTime(String unitTime){
        this.unitTime = unitTime;
    }

    public String getUnitTime(){
        return unitTime;
    }

    public void setObjName(String objName){
        this.objName = objName;
    }

    public String getObjName(){
        return objName;
    }

    public void setPic(String pic){
        this.pic = pic;
    }

    public String getPic(){
        return pic;
    }

    public void setAdPic(String adPic){
        this.adPic = adPic;
    }

    public String getAdPic(){
        return adPic;
    }

    public void setAreaRange(String areaRange){
        this.areaRange = areaRange;
    }

    public String getAreaRange(){
        return areaRange;
    }

    public void setKeyWord(String keyWord){
        this.keyWord = keyWord;
    }

    public String getKeyWord(){
        return keyWord;
    }

    public void setSubmitMethod(String submitMethod){
        this.submitMethod = submitMethod;
    }

    public String getSubmitMethod(){
        return submitMethod;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public String getFilePath(){
        return filePath;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public void setOnTime(Integer onTime){
        this.onTime = onTime;
    }

    public Integer getOnTime(){
        return onTime;
    }

    public void setIsStop(Integer isStop){
        this.isStop = isStop;
    }

    public Integer getIsStop(){
        return isStop;
    }

    public void setSaleNum(Integer saleNum){
        this.saleNum = saleNum;
    }

    public Integer getSaleNum(){
        return saleNum;
    }

    public void setFocusNum(Integer focusNum){
        this.focusNum = focusNum;
    }

    public Integer getFocusNum(){
        return focusNum;
    }

    public void setMarkNum(Integer markNum){
        this.markNum = markNum;
    }

    public Integer getMarkNum(){
        return markNum;
    }

    public void setLeaveNum(Integer leaveNum){
        this.leaveNum = leaveNum;
    }

    public Integer getLeaveNum(){
        return leaveNum;
    }

    public void setViews(Integer views){
        this.views = views;
    }

    public Integer getViews(){
        return views;
    }

    public void setPayItem(String payItem){
        this.payItem = payItem;
    }

    public String getPayItem(){
        return payItem;
    }

    public void setAttCash(Integer attCash){
        this.attCash = attCash;
    }

    public Integer getAttCash(){
        return attCash;
    }

    public void setRefreshTime(Integer refreshTime){
        this.refreshTime = refreshTime;
    }

    public Integer getRefreshTime(){
        return refreshTime;
    }

    public void setUniqueNum(String uniqueNum){
        this.uniqueNum = uniqueNum;
    }

    public String getUniqueNum(){
        return uniqueNum;
    }

    public void setTotalSale(Integer totalSale){
        this.totalSale = totalSale;
    }

    public Integer getTotalSale(){
        return totalSale;
    }

    public void setServiceStatus(Integer serviceStatus){
        this.serviceStatus = serviceStatus;
    }

    public Integer getServiceStatus(){
        return serviceStatus;
    }

    public void setIsTop(Integer isTop){
        this.isTop = isTop;
    }

    public Integer getIsTop(){
        return isTop;
    }

    public void setPoint(String point){
        this.point = point;
    }

    public String getPoint(){
        return point;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public void setPayitemTime(String payitemTime){
        this.payitemTime = payitemTime;
    }

    public String getPayitemTime(){
        return payitemTime;
    }

    public void setExistTime(Integer existTime){
        this.existTime = existTime;
    }

    public Integer getExistTime(){
        return existTime;
    }

    public void setConfirmMax(Integer confirmMax){
        this.confirmMax = confirmMax;
    }

    public Integer getConfirmMax(){
        return confirmMax;
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

    public void setArea(Integer area){
        this.area = area;
    }

    public Integer getArea(){
        return area;
    }

    public void setGoodstop(Integer goodstop){
        this.goodstop = goodstop;
    }

    public Integer getGoodstop(){
        return goodstop;
    }

    public void setOverdueType(String overdueType){
        this.overdueType = overdueType;
    }

    public String getOverdueType(){
        return overdueType;
    }
}
