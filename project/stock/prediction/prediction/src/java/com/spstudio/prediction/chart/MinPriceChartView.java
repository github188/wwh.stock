/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.prediction.chart;

import com.spstudio.prediction.bb.StockQueryMB;
import com.spstudio.stock.sb.StockQuerySessionBeanRemote;
import java.util.Collections;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author sp
 */
@Model
public class MinPriceChartView extends ChartViewBase {

    @EJB
    private StockQuerySessionBeanRemote stockQuerySessionBean;

    @Inject
    StockQueryMB mb;

    @PostConstruct
    public void construct() {
        list = stockQuerySessionBean.queryRecentDailyPriceList(mb.getStockCode(), 20);
        if (!list.isEmpty()) {
            list.stream().forEach((dp) -> {
                if (dp.getMinPrice().doubleValue() >= highBoundary) {
                    highBoundary = dp.getMinPrice().doubleValue();
                }
                if (dp.getMinPrice().doubleValue() < lowBoundary) {
                    lowBoundary = dp.getMinPrice().doubleValue();
                }
            });
            highBoundary = highBoundary * 1.5;
            lowBoundary = lowBoundary * 0.5;
        }

        if (list.isEmpty()) {
            list = Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void fillSeriesData(ChartSeries actualSeries, ChartSeries predictSeries) {
        list.stream().forEach((dp) -> {
            actualSeries.set(sdf.format(dp.getTradeDate()), dp.getMinPrice() == null ? 0 : dp.getMinPrice());
            predictSeries.set(sdf.format(dp.getTradeDate()), dp.getPredictMinPrice() == null ? dp.getMinPrice() : dp.getPredictMinPrice());
        });
    }

}
