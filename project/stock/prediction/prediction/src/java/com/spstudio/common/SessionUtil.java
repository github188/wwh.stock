/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.common;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sp
 */
@Model
public class SessionUtil {

    @Inject
    HttpSession session;

    public void logout() {
        session.invalidate();
    }
}
