/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.prediction.bb;

import com.spstudio.stock.sb.PredictionSessionBeanRemote;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;

/**
 *
 * @author sp
 */
@Model
public class TestController {
    @EJB
    private PredictionSessionBeanRemote predictionTimerSessionBean;

    public void call() {
        LOG.info("call();");
        predictionTimerSessionBean.executePredictProcess();
    }
    private static final Logger LOG = Logger.getLogger(TestController.class.getName());
}
