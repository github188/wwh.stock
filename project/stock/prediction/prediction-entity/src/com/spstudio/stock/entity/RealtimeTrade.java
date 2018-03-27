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
@Table(name = "realtime_trade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RealtimeTrade.findAll", query = "SELECT r FROM RealtimeTrade r"),
    @NamedQuery(name = "RealtimeTrade.findById", query = "SELECT r FROM RealtimeTrade r WHERE r.id = :id"),
    @NamedQuery(name = "RealtimeTrade.findByStockPrice", query = "SELECT r FROM RealtimeTrade r WHERE r.stockPrice = :stockPrice"),
    @NamedQuery(name = "RealtimeTrade.findByStockPriceMax", query = "SELECT r FROM RealtimeTrade r WHERE r.stockPriceMax = :stockPriceMax"),
    @NamedQuery(name = "RealtimeTrade.findByStockPriceMin", query = "SELECT r FROM RealtimeTrade r WHERE r.stockPriceMin = :stockPriceMin"),
    @NamedQuery(name = "RealtimeTrade.findByRecordTime", query = "SELECT r FROM RealtimeTrade r WHERE r.recordTime = :recordTime")})
public class RealtimeTrade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock_price")
    private BigDecimal stockPrice;
    @Column(name = "stock_price_max")
    private BigDecimal stockPriceMax;
    @Column(name = "stock_price_min")
    private BigDecimal stockPriceMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "record_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordTime;
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Stock stockId;

    public RealtimeTrade() {
    }

    public RealtimeTrade(Integer id) {
        this.id = id;
    }

    public RealtimeTrade(Integer id, BigDecimal stockPrice, Date recordTime) {
        this.id = id;
        this.stockPrice = stockPrice;
        this.recordTime = recordTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

    public BigDecimal getStockPriceMax() {
        return stockPriceMax;
    }

    public void setStockPriceMax(BigDecimal stockPriceMax) {
        this.stockPriceMax = stockPriceMax;
    }

    public BigDecimal getStockPriceMin() {
        return stockPriceMin;
    }

    public void setStockPriceMin(BigDecimal stockPriceMin) {
        this.stockPriceMin = stockPriceMin;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
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
        if (!(object instanceof RealtimeTrade)) {
            return false;
        }
        RealtimeTrade other = (RealtimeTrade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.spstudio.stock.entity.RealtimeTrade[ id=" + id + " ]";
    }

}
