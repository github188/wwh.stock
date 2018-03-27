/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.stock.entity;

import java.io.Serializable;
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
@Table(name = "user_stock_ref")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserStockRef.findAll", query = "SELECT u FROM UserStockRef u"),
    @NamedQuery(name = "UserStockRef.findById", query = "SELECT u FROM UserStockRef u WHERE u.id = :id"),
    @NamedQuery(name = "UserStockRef.findRecentById", query = "SELECT u FROM UserStockRef u WHERE u.userId = :userId ORDER BY u.queryDate DESC"),
    @NamedQuery(name = "UserStockRef.findByQueryDate", query = "SELECT u FROM UserStockRef u WHERE u.queryDate = :queryDate")})
public class UserStockRef implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "query_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date queryDate;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users userId;
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Stock stockId;

    public UserStockRef() {
    }

    public UserStockRef(Integer id) {
        this.id = id;
    }

    public UserStockRef(Integer id, Date queryDate) {
        this.id = id;
        this.queryDate = queryDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
        if (!(object instanceof UserStockRef)) {
            return false;
        }
        UserStockRef other = (UserStockRef) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.spstudio.stock.entity.UserStockRef[ id=" + id + " ]";
    }

}
