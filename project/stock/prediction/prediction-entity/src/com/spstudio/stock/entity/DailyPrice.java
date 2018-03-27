/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.stock.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sp
 */
@Entity
@Table(name = "daily_price")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DailyPrice.findAll", query = "SELECT d FROM DailyPrice d"),
    @NamedQuery(name = "DailyPrice.findById", query = "SELECT d FROM DailyPrice d WHERE d.id = :id"),
    @NamedQuery(name = "DailyPrice.findRecentByStockCode", query = "SELECT d FROM DailyPrice d JOIN d.stockId s WHERE s.stockCode = :stockCode ORDER BY d.tradeDate DESC"),
    @NamedQuery(name = "DailyPrice.findByStockCode", query = "SELECT d FROM DailyPrice d JOIN d.stockId s WHERE s.stockCode = :stockCode ORDER BY d.tradeDate DESC"),
    @NamedQuery(name = "DailyPrice.findByTradeDate", query = "SELECT d FROM DailyPrice d WHERE d.tradeDate = :tradeDate"),
    @NamedQuery(name = "DailyPrice.findByMaxPrice", query = "SELECT d FROM DailyPrice d WHERE d.maxPrice = :maxPrice"),
    @NamedQuery(name = "DailyPrice.findByMinPrice", query = "SELECT d FROM DailyPrice d WHERE d.minPrice = :minPrice"),
    @NamedQuery(name = "DailyPrice.findByOpenPrice", query = "SELECT d FROM DailyPrice d WHERE d.openPrice = :openPrice"),
    @NamedQuery(name = "DailyPrice.findByClosePrice", query = "SELECT d FROM DailyPrice d WHERE d.closePrice = :closePrice"),
    @NamedQuery(name = "DailyPrice.findByTradeNum", query = "SELECT d FROM DailyPrice d WHERE d.tradeNum = :tradeNum"),
    @NamedQuery(name = "DailyPrice.findByTradeAmount", query = "SELECT d FROM DailyPrice d WHERE d.tradeAmount = :tradeAmount"),
    @NamedQuery(name = "DailyPrice.findByPredictMaxPrice", query = "SELECT d FROM DailyPrice d WHERE d.predictMaxPrice = :predictMaxPrice"),
    @NamedQuery(name = "DailyPrice.findByPredictMinPrice", query = "SELECT d FROM DailyPrice d WHERE d.predictMinPrice = :predictMinPrice")})
public class DailyPrice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trade_date")
    @Temporal(TemporalType.DATE)
    private Date tradeDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "max_price")
    private BigDecimal maxPrice;
    @Column(name = "min_price")
    private BigDecimal minPrice;
    @Column(name = "open_price")
    private BigDecimal openPrice;
    @Column(name = "close_price")
    private BigDecimal closePrice;
    @Column(name = "trade_num")
    private Integer tradeNum;
    @Column(name = "trade_amount")
    private Integer tradeAmount;
    @Column(name = "predict_max_price")
    private BigDecimal predictMaxPrice;
    @Column(name = "predict_min_price")
    private BigDecimal predictMinPrice;
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Stock stockId;

    public DailyPrice() {
    }

    public DailyPrice(Integer id) {
        this.id = id;
    }

    public DailyPrice(Integer id, Date tradeDate) {
        this.id = id;
        this.tradeDate = tradeDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public Integer getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(Integer tradeNum) {
        this.tradeNum = tradeNum;
    }

    public Integer getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Integer tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public BigDecimal getPredictMaxPrice() {
        return predictMaxPrice;
    }

    public void setPredictMaxPrice(BigDecimal predictMaxPrice) {
        this.predictMaxPrice = predictMaxPrice;
    }

    public BigDecimal getPredictMinPrice() {
        return predictMinPrice;
    }

    public void setPredictMinPrice(BigDecimal predictMinPrice) {
        this.predictMinPrice = predictMinPrice;
    }

    public Stock getStockId() {
        return stockId;
    }

    public void setStockId(Stock stockId) {
        this.stockId = stockId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DailyPrice)) {
            return false;
        }
        DailyPrice other = (DailyPrice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.spstudio.stock.entity.DailyPrice[ id=" + id + " ]";
    }

}
