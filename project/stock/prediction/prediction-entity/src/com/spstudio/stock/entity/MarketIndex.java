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
@Table(name = "market_index")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarketIndex.findAll", query = "SELECT m FROM MarketIndex m"),
    @NamedQuery(name = "MarketIndex.findRecent", query = "SELECT m FROM MarketIndex m ORDER BY m.tradeDate DESC"),
    @NamedQuery(name = "MarketIndex.findById", query = "SELECT m FROM MarketIndex m WHERE m.id = :id"),
    @NamedQuery(name = "MarketIndex.findByPoint", query = "SELECT m FROM MarketIndex m WHERE m.point = :point"),
    @NamedQuery(name = "MarketIndex.findByPrice", query = "SELECT m FROM MarketIndex m WHERE m.price = :price"),
    @NamedQuery(name = "MarketIndex.findByChangeRate", query = "SELECT m FROM MarketIndex m WHERE m.changeRate = :changeRate"),
    @NamedQuery(name = "MarketIndex.findByVolumn", query = "SELECT m FROM MarketIndex m WHERE m.volumn = :volumn"),
    @NamedQuery(name = "MarketIndex.findByTurnover", query = "SELECT m FROM MarketIndex m WHERE m.turnover = :turnover"),
    @NamedQuery(name = "MarketIndex.findByTradeDate", query = "SELECT m FROM MarketIndex m WHERE m.tradeDate = :tradeDate")})
public class MarketIndex implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "point")
    private int point;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private BigDecimal price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "change_rate")
    private BigDecimal changeRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "volumn")
    private int volumn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "turnover")
    private int turnover;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trade_date")
    @Temporal(TemporalType.DATE)
    private Date tradeDate;
    @JoinColumn(name = "market_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Market marketId;

    public MarketIndex() {
    }

    public MarketIndex(Integer id) {
        this.id = id;
    }

    public MarketIndex(Integer id, int point, BigDecimal price, BigDecimal changeRate, int volumn, int turnover, Date tradeDate) {
        this.id = id;
        this.point = point;
        this.price = price;
        this.changeRate = changeRate;
        this.volumn = volumn;
        this.turnover = turnover;
        this.tradeDate = tradeDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(BigDecimal changeRate) {
        this.changeRate = changeRate;
    }

    public int getVolumn() {
        return volumn;
    }

    public void setVolumn(int volumn) {
        this.volumn = volumn;
    }

    public int getTurnover() {
        return turnover;
    }

    public void setTurnover(int turnover) {
        this.turnover = turnover;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
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
        if (!(object instanceof MarketIndex)) {
            return false;
        }
        MarketIndex other = (MarketIndex) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.spstudio.stock.entity.MarketIndex[ id=" + id + " ]";
    }

}
