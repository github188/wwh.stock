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
@Table(name = "market")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Market.findAll", query = "SELECT m FROM Market m"),
    @NamedQuery(name = "Market.findById", query = "SELECT m FROM Market m WHERE m.id = :id"),
    @NamedQuery(name = "Market.findByMarketName", query = "SELECT m FROM Market m WHERE m.marketName = :marketName")})
public class Market implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "market_name")
    private String marketName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "marketId", fetch = FetchType.LAZY)
    private Collection<MarketIndex> marketIndexCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "marketId", fetch = FetchType.LAZY)
    private Collection<Stock> stockCollection;

    public Market() {
    }

    public Market(Integer id) {
        this.id = id;
    }

    public Market(Integer id, String marketName) {
        this.id = id;
        this.marketName = marketName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    @XmlTransient
    public Collection<MarketIndex> getMarketIndexCollection() {
        return marketIndexCollection;
    }

    public void setMarketIndexCollection(Collection<MarketIndex> marketIndexCollection) {
        this.marketIndexCollection = marketIndexCollection;
    }

    @XmlTransient
    public Collection<Stock> getStockCollection() {
        return stockCollection;
    }

    public void setStockCollection(Collection<Stock> stockCollection) {
        this.stockCollection = stockCollection;
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
        if (!(object instanceof Market)) {
            return false;
        }
        Market other = (Market) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.spstudio.stock.entity.Market[ id=" + id + " ]";
    }

}
