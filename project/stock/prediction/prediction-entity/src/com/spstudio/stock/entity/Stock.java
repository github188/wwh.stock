/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.stock.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sp
 */
@Entity
@Table(name = "stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s"),
    @NamedQuery(name = "Stock.findById", query = "SELECT s FROM Stock s WHERE s.id = :id"),
    @NamedQuery(name = "Stock.findByStockCode", query = "SELECT s FROM Stock s WHERE s.stockCode = :stockCode"),
    @NamedQuery(name = "Stock.findByStockName", query = "SELECT s FROM Stock s WHERE s.stockName = :stockName")})
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "stock_code")
    private String stockCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "stock_name")
    private String stockName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockId", fetch = FetchType.LAZY)
    private Collection<RealtimeTrade> realtimeTradeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockId", fetch = FetchType.LAZY)
    private Collection<UserStockRef> userStockRefCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockId", fetch = FetchType.LAZY)
    private Collection<DailyPrice> dailyPriceCollection;
    @JoinColumn(name = "market_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Market marketId;

    public Stock() {
    }

    public Stock(Integer id) {
        this.id = id;
    }

    public Stock(Integer id, String stockCode, String stockName) {
        this.id = id;
        this.stockCode = stockCode;
        this.stockName = stockName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    @XmlTransient
    public Collection<RealtimeTrade> getRealtimeTradeCollection() {
        return realtimeTradeCollection;
    }

    public void setRealtimeTradeCollection(Collection<RealtimeTrade> realtimeTradeCollection) {
        this.realtimeTradeCollection = realtimeTradeCollection;
    }

    @XmlTransient
    public Collection<UserStockRef> getUserStockRefCollection() {
        return userStockRefCollection;
    }

    public void setUserStockRefCollection(Collection<UserStockRef> userStockRefCollection) {
        this.userStockRefCollection = userStockRefCollection;
    }

    @XmlTransient
    public Collection<DailyPrice> getDailyPriceCollection() {
        return dailyPriceCollection;
    }

    public void setDailyPriceCollection(Collection<DailyPrice> dailyPriceCollection) {
        this.dailyPriceCollection = dailyPriceCollection;
    }

    public Market getMarketId() {
        return marketId;
    }

    public void setMarketId(Market marketId) {
        this.marketId = marketId;
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
        if (!(object instanceof Stock)) {
            return false;
        }
        Stock other = (Stock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.spstudio.stock.entity.Stock[ id=" + id + " ]";
    }

}
