/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.stock.sb;

import com.spstudio.stock.entity.Users;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sp
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> implements com.spstudio.stock.sb.UsersFacadeRemote {
    @PersistenceContext(unitName = "prediction-pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }

    @Override
    public Users findByLoginId(String loginId) {
        Query query = getEntityManager().createNamedQuery("Users.findByLoginId", Users.class);
        query.setParameter("loginId", loginId);
        Users user = (Users) query.getSingleResult();
        return user;
    }

}
