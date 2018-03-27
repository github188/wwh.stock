/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.common;

import com.spstudio.stock.entity.Users;
import com.spstudio.stock.sb.UsersFacadeRemote;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author sp
 */
@Model
public class LoginStatus {

    @EJB
    private UsersFacadeRemote usersFacade;

    @Inject
    private Principal principal;

    @Inject
    Users user;

    private boolean loggedIn;

    public boolean isLoggedIn() {
        if (principal != null && !"ANONYMOUS".equalsIgnoreCase(principal.getName())) {
            setLoggedIn(true);
            if (user.getId() == null) {
                loadUser();
            }
        } else {
            setLoggedIn(false);
        }
        return loggedIn;
    }

    private void loadUser() {
        Users loggedInUser = usersFacade.findByLoginId(principal.getName());
        try {
            PropertyUtils.copyProperties(user, loggedInUser);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.severe(e.toString());
        }
    }
    private static final Logger LOG = Logger.getLogger(LoginStatus.class.getName());

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
