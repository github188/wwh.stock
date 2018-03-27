/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.stock.sb;

import com.spstudio.stock.entity.MarketIndex;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author sp
 */
@Stateless
public class TestSessionBean implements TestSessionBeanRemote {

    @PersistenceContext(unitName = "prediction-pu")
    private EntityManager em;

    @Override
    public void test() {
        TypedQuery<MarketIndex> miQuery = em.createNamedQuery("MarketIndex.findRecent", MarketIndex.class);
        miQuery.setMaxResults(30);
        List<MarketIndex> miList = miQuery.getResultList();
        LOG.log(Level.INFO, "size: {0}", miList.size());
    }
    private static final Logger LOG = Logger.getLogger(TestSessionBean.class.getName());

}
