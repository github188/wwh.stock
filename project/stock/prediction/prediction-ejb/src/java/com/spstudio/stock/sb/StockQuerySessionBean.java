/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.stock.sb;

import com.spstudio.exception.PrivilegeNeedException;
import com.spstudio.exception.StockNotFoundException;
import com.spstudio.stock.entity.DailyPrice;
import com.spstudio.stock.entity.Stock;
import com.spstudio.stock.entity.UserStockRef;
import com.spstudio.stock.entity.Users;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author sp
 */
@Stateless
public class StockQuerySessionBean implements StockQuerySessionBeanRemote {

    @EJB
    private UsersFacadeRemote usersFacade;

    private static final Logger LOG = Logger.getLogger(StockQuerySessionBean.class.getName());

    @Inject
    Principal principal;

    @PersistenceContext(unitName = "prediction-pu")
    private EntityManager em;

    Users user;
    boolean permitStockQuery = false;

    @Override
    public DailyPrice queryStock(final String stockCode) throws PrivilegeNeedException, StockNotFoundException {

        if ("ANONYMOUS".equals(principal.getName())) {
            throw new PrivilegeNeedException();
        }

        user = usersFacade.findByLoginId(principal.getName());
        checkUserQueryPrivilege();

        if (!permitStockQuery) {
            throw new PrivilegeNeedException();
        }

        LOG.log(Level.INFO, "{0} with {1} query stock {2}", new Object[]{principal.getName(), user.getGroupsId().getGroupName(), stockCode});
        TypedQuery<DailyPrice> query = em.createNamedQuery("DailyPrice.findByStockCode", DailyPrice.class);
        query.setParameter("stockCode", stockCode);
        query.setMaxResults(1);
        DailyPrice dp = null;
        try {
            dp = query.getSingleResult();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "{0} with {1} query stock {2} with exception: {3}", new Object[]{principal.getName(), user.getGroupsId().getGroupName(), stockCode, e.getMessage()});
        }
        if (dp == null) {
            throw new StockNotFoundException();
        }
        if (dp.getPredictMaxPrice() != null && dp.getPredictMinPrice() != null
                && dp.getPredictMaxPrice().doubleValue() != 0 && dp.getPredictMinPrice().doubleValue() != 0) {
            UserStockRef ref = new UserStockRef();
            ref.setUserId(user);
            ref.setStockId(dp.getStockId());
            ref.setQueryDate(new java.util.Date());
            em.persist(ref);
        }
        return dp;

    }

    private void checkUserQueryPrivilege() {
        if (user.getGroupsId().getGroupName().equals("admin-group")
                || user.getGroupsId().getGroupName().equals("advanced-group")) {
            permitStockQuery = true;
            return;
        }

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Query query = em.createNativeQuery("SELECT ref.id FROM user_stock_ref ref where ref.user_id = ? and query_date between ? and ?");
        query.setParameter(1, user.getId());
        query.setParameter(2, c.getTime());
        c.add(Calendar.DAY_OF_YEAR, 1);
        query.setParameter(3, c.getTime());
        List<?> list = query.getResultList();

        switch (user.getGroupsId().getGroupName()) {
            case "free-group":
                if (list == null || list.isEmpty()) {
                    permitStockQuery = true;
                }
                break;
            case "standard-group":
                if (list == null || list.size() < 5) {
                    permitStockQuery = true;
                }
                break;
        }
    }

    @Override
    public List<Stock> retrieveRecentQueriedStock() {
        TypedQuery<UserStockRef> query = em.createNamedQuery("UserStockRef.findRecentById", UserStockRef.class);
        query.setParameter("userId", usersFacade.findByLoginId(principal.getName()));
        query.setMaxResults(6);
        List<UserStockRef> list = query.getResultList();
        List<Stock> listStock = new ArrayList<>();
        if (!list.isEmpty()) {
            for (UserStockRef r : list) {
                listStock.add(r.getStockId());
            }
        }
        return listStock;
    }

    @Override
    public List<DailyPrice> queryRecentDailyPriceList(final String stockCode, int maxQuerySize) {
        TypedQuery<DailyPrice> query = em.createNamedQuery("DailyPrice.findRecentByStockCode", DailyPrice.class);
        query.setParameter("stockCode", stockCode);
        query.setMaxResults(maxQuerySize);
        List<DailyPrice> list = query.getResultList();
        em.clear();
        if (!list.isEmpty()) {
            Collections.reverse(list);
            // adjust actual price and predict price
            for (int i = list.size() - 1; i > 0; i--) {
                list.get(i).setPredictMaxPrice(list.get(i - 1).getPredictMaxPrice());
                list.get(i).setPredictMinPrice(list.get(i - 1).getPredictMinPrice());
            }
            list.remove(0);
        }
        return list;
    }

}
