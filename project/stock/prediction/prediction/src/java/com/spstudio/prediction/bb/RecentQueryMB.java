/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.prediction.bb;

import com.spstudio.stock.entity.Stock;
import com.spstudio.stock.sb.StockQuerySessionBeanRemote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author sp
 */
@Named(value = "recentQueryMB")
@SessionScoped
public class RecentQueryMB implements Serializable {

    @EJB
    private StockQuerySessionBeanRemote stockQuerySessionBean;

    private List<Stock> stockList;

    /**
     * Creates a new instance of RecentQueryMB
     */
    public RecentQueryMB() {
    }

    @PostConstruct
    public void postConstruct() {
        stockList = stockQuerySessionBean.retrieveRecentQueriedStock();
        if (stockList == null) {
            stockList = new ArrayList<>();
        }
    }

    public void addQueriedStock(Stock stock) {
        stockList.add(0, stock);
        while (stockList.size() > 6) {
            stockList.remove(stockList.size() - 1);
        }
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

}
