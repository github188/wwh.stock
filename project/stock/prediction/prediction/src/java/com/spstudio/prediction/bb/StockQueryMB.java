/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.prediction.bb;

import com.spstudio.exception.PrivilegeNeedException;
import com.spstudio.exception.StockNotFoundException;
import com.spstudio.stock.entity.DailyPrice;
import com.spstudio.stock.sb.StockQuerySessionBeanRemote;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author sp
 */
@Named(value = "stockQueryMB")
@RequestScoped
public class StockQueryMB {

    @Inject
    Principal principal;

    @Inject
    RecentQueryMB recentQueryMB;

    @EJB
    private StockQuerySessionBeanRemote stockQuerySessionBean;

    private String stockCode = "600000";
    private DailyPrice predictedStock;

    private static final Logger LOG = Logger.getLogger(StockQueryMB.class.getName());

    /**
     * Creates a new instance of StockQueryMB
     */
    public StockQueryMB() {
    }

    public void queryPredictedValue() {
        queryPredictedValue(stockCode);
    }

    public void queryPredictedValue(String stockCode) {
        try {
            predictedStock = stockQuerySessionBean.queryStock(stockCode);
            recentQueryMB.addQueriedStock(predictedStock.getStockId());
        } catch (PrivilegeNeedException ex) {
            LOG.log(Level.WARNING, "{0} encounter exception: PrivilegeNeedException", principal.getName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "查询未能完成，请检查是否用完每日免费查询次数，或升级用户等级", ""));
        } catch (StockNotFoundException ex) {
            LOG.log(Level.WARNING, "{0} encounter exception: StockNotFoundException", principal.getName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "查询未能完成，请检查您的股票代码，目前系统仅支持深市和沪市暂不支持创业板", ""));
        }
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public DailyPrice getPredictedStock() {
        return predictedStock;
    }

    public void setPredictedStock(DailyPrice predictedStock) {
        this.predictedStock = predictedStock;
    }

}
