/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.stock.sb;

import com.spstudio.stock.encog.BoundaryPredictCore;
import com.spstudio.stock.entity.DailyPrice;
import com.spstudio.stock.entity.MarketIndex;
import com.spstudio.stock.entity.Stock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * 定时运行预测处理，默认15:45运行
 * @author sp
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class PredictionTimerSessionBean implements PredictionSessionBeanRemote {

    @PersistenceContext(unitName = "prediction-pu")
    private EntityManager em;

    @Inject
    UserTransaction ut;

    private static final int ELEMENT_SIZE = 20;
    private static final Logger LOG = Logger.getLogger(PredictionTimerSessionBean.class.getName());

    @Schedule(dayOfWeek = "Mon-Fri", month = "*", hour = "15", dayOfMonth = "*", year = "*", minute = "45", second = "0", persistent = false)
    @Override
    public void executePredictProcess() {
        LOG.info("prediction process is running");
        List<DailyPrice> persistDpList = calculatePredictStocks();
        try {
            ut.setTransactionTimeout(10);
            ut.begin();
            persistDailyPriceList(persistDpList);
            ut.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                Logger.getLogger(PredictionTimerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        LOG.info("prediction process is finished");
    }

    private void persistDailyPriceList(List<DailyPrice> persistDpList) {
        for (DailyPrice dp : persistDpList) {
            em.merge(dp);
        }
        em.flush();
    }

    private List<DailyPrice> calculatePredictStocks() {

        TypedQuery<MarketIndex> miQuery = em.createNamedQuery("MarketIndex.findRecent", MarketIndex.class);
        miQuery.setMaxResults(ELEMENT_SIZE * 2);
        List<MarketIndex> miList = miQuery.getResultList();
        TypedQuery<Stock> query = em.createNamedQuery("Stock.findAll", Stock.class);
        List<Stock> stockList = query.getResultList();

        List<DailyPrice> persistDailyPriceList = new ArrayList<>();

        List<MarketIndex> szList = new ArrayList<>();
        List<MarketIndex> shList = new ArrayList<>();
        for (MarketIndex mi : miList) {
            switch (mi.getMarketId().getId()) {
                case 1:
                    shList.add(mi);
                    break;
                case 2:
                    szList.add(mi);
                    break;
            }
        }
        Collections.reverse(shList);
        Collections.reverse(szList);

        for (Stock stock : stockList) {
            LOG.log(Level.INFO, "prcess stock code: {0} with id: {1}", new Object[]{stock.getStockCode(), stock.getId()});
            TypedQuery<DailyPrice> dpQuery = em.createNamedQuery("DailyPrice.findRecentByStockCode", DailyPrice.class);
            dpQuery.setParameter("stockCode", stock.getStockCode());
            dpQuery.setMaxResults(ELEMENT_SIZE);
            List<DailyPrice> dpList = dpQuery.getResultList();
            Collections.reverse(dpList);

            DailyPrice lastDailyPrice = null;
            switch (stock.getMarketId().getId()) {
                case 1:
                    if (dpList.size() != shList.size()) {
                        LOG.log(Level.WARNING, "different list size got, recent dailyprice list size is {0} but sh market index list count is {1}, ignored", new Object[]{dpList.size(), shList.size()});
                        continue;
                    }
                    lastDailyPrice = new BoundaryPredictCore().predict(dpList, shList);
                    break;
                case 2:
                    if (dpList.size() != szList.size()) {
                        LOG.log(Level.WARNING, "different list size got, recent dailyprice list size is {0} but sz market index list count is {1}, ignored", new Object[]{dpList.size(), szList.size()});
                        continue;
                    }
                    lastDailyPrice = new BoundaryPredictCore().predict(dpList, szList);
                    break;
            }
            if (lastDailyPrice != null) {
                LOG.log(Level.INFO, "predicted max price is {0}", lastDailyPrice.getPredictMaxPrice());
                LOG.log(Level.INFO, "predicted min price is {0}", lastDailyPrice.getPredictMinPrice());
                if (lastDailyPrice.getPredictMaxPrice().doubleValue() > 1000
                        || lastDailyPrice.getPredictMinPrice().doubleValue() > 1000) {
                    LOG.warning("can't calculate value, skipped");
                    continue;
                }
                dpList.get(dpList.size() - 1).setPredictMaxPrice(lastDailyPrice.getPredictMaxPrice());
                dpList.get(dpList.size() - 1).setPredictMinPrice(lastDailyPrice.getPredictMinPrice());

                persistDailyPriceList.add(dpList.get(dpList.size() - 1));
            }
        }
        return persistDailyPriceList;
    }
}
