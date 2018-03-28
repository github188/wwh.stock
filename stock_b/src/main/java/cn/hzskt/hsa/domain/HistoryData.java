package cn.hzskt.hsa.domain;
import com.zjhcsoft.smartcity.magic.orm.domain.BaseEntity;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class HistoryData extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 代码**/
	private String code;
			
	/** 日期**/
	private String dt;
			
	/** 收盘价(元)**/
	private Double closingPrice;
			
	/** 涨跌幅(%)**/
	private String udWidth;
			
	/** 换手率(%)**/
	private String turnoverRate;
			
	/** 成交量(万股)**/
	private Double volume;
			
	/** 成交额(亿)**/
	private Double turnVolume;
			
	/** 流入资金(亿)**/
	private Double inflowFund;
			
	/** 买卖差额(万元)**/
	private Double tradeBalance;
			
	/** 资金差(亿)**/
	private Double fundDiff;
			
	/** 净流入率(%)**/
	private String netInflowRate;
			
	/** 顺序**/
	private Integer orderBy;

	/** 最高价**/
	private Double highestPrice;

	/** 最低价**/
	private Double lowestPrice;

	/** 停牌天数**/
	private Integer suspensionDays;

	/** 停牌起始日期**/
	private String startDate;

	/** 停牌结束日期**/
	private String endDate;

	public Double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(Double highestPrice) {
		this.highestPrice = highestPrice;
	}

	public Double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(Double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public Integer getSuspensionDays() {
		return suspensionDays;
	}

	public void setSuspensionDays(Integer suspensionDays) {
		this.suspensionDays = suspensionDays;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setCode(String code){
		this.code = code;
	} 
	
	public String getCode(){
		return code;
	} 
			
	public void setDt(String dt){
		this.dt = dt;
	} 
	
	public String getDt(){
		return dt;
	} 
			
	public void setClosingPrice(Double closingPrice){
		this.closingPrice = closingPrice;
	} 
	
	public Double getClosingPrice(){
		return closingPrice;
	} 
			
	public void setUdWidth(String udWidth){
		this.udWidth = udWidth;
	} 
	
	public String getUdWidth(){
		return udWidth;
	} 
			
	public void setTurnoverRate(String turnoverRate){
		this.turnoverRate = turnoverRate;
	} 
	
	public String getTurnoverRate(){
		return turnoverRate;
	} 
			
	public void setVolume(Double volume){
		this.volume = volume;
	} 
	
	public Double getVolume(){
		return volume;
	} 
			
	public void setTurnVolume(Double turnVolume){
		this.turnVolume = turnVolume;
	} 
	
	public Double getTurnVolume(){
		return turnVolume;
	} 
			
	public void setInflowFund(Double inflowFund){
		this.inflowFund = inflowFund;
	} 
	
	public Double getInflowFund(){
		return inflowFund;
	} 
			
	public void setTradeBalance(Double tradeBalance){
		this.tradeBalance = tradeBalance;
	} 
	
	public Double getTradeBalance(){
		return tradeBalance;
	} 
			
	public void setFundDiff(Double fundDiff){
		this.fundDiff = fundDiff;
	} 
	
	public Double getFundDiff(){
		return fundDiff;
	} 
			
	public void setNetInflowRate(String netInflowRate){
		this.netInflowRate = netInflowRate;
	} 
	
	public String getNetInflowRate(){
		return netInflowRate;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
