/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.stock.sb;

import com.spstudio.stock.entity.DailyPrice;
import com.spstudio.stock.entity.Market;
import com.spstudio.stock.entity.MarketIndex;
import com.spstudio.stock.entity.Stock;
import com.spstudio.util.SPHttpClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * 定时采集数据，默认15:30开始采集
 *
 * @author sp
 */
@Singleton
public class CollectDailyPriceSessionBean implements CollectDailyPriceSessionBeanRemote {

    private static final Logger LOG = Logger.getLogger(CollectDailyPriceSessionBean.class.getName());

    @PersistenceContext(unitName = "prediction-pu")
    private EntityManager em;

    static final String BASE_URL = "http://hq.sinajs.cn/list=";
    final int batchProcessCount = 20;

    @Schedule(dayOfWeek = "Mon-Fri", month = "*", hour = "15", dayOfMonth = "*", year = "*", minute = "30", second = "0", persistent = false)
    @Override
    public void collectDailyPrice() {

        LOG.log(Level.INFO, "begin collect stock price information at {0}", new Date());
        
        Query queryMarkets = em.createNamedQuery("Market.findAll", Market.class);
        List<Market> marketList = queryMarkets.getResultList();
        for (Market market : marketList) {
            Collection<Stock> stockCollection = market.getStockCollection();
            String marketSymbol = market.getId() == 1 ? "sh" : "sz";
            fetchDataFromMarket(marketSymbol, stockCollection);
            LOG.log(Level.INFO, "{0} market fetching is complete", marketSymbol);

            String marketIndexSymbol = market.getId() == 1 ? "s_sh000001" : "s_sz399001";
            fetchMarketIndex(marketIndexSymbol, market);
            LOG.log(Level.INFO, "{0} market index fetching is complete", marketSymbol);
        }
    }

    private void fetchMarketIndex(String symbol, Market market) {
        String requestUrl = BASE_URL + symbol;
        String marketIndexData = null;
        try {
            marketIndexData = new SPHttpClient().getContentFromUrl(requestUrl);
        } catch (Exception ex) {
            Logger.getLogger(CollectDailyPriceSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (marketIndexData != null) {
            // var hq_str_s_sz399001="深证成指,13091.834,-474.437,-3.50,79288734,13798092";
            marketIndexData = marketIndexData.split("\"")[1];
            String[] data = marketIndexData.split(",");
            MarketIndex mi = new MarketIndex();
            mi.setMarketId(market);
            mi.setTradeDate(new Date());
            mi.setPoint((int) Double.parseDouble(data[1]));
            mi.setPrice(new BigDecimal(Double.parseDouble(data[2])));
            mi.setChangeRate(new BigDecimal(Double.parseDouble(data[3])));
            mi.setVolumn(Integer.parseInt(data[4]) / 10000); // 万手
            mi.setTurnover(Integer.parseInt(data[5]) / 10000); // 亿
            em.persist(mi);
        } else {
            LOG.log(Level.SEVERE, "fail to fetch market index info of {0}", symbol);
        }
    }

    private void fetchDataFromMarket(String symbol, Collection<Stock> stockCollection) {
        List<Stock> stockList = new ArrayList<>();
        for (Stock stock : stockCollection) {
            stockList.add(stock);
            if (stockList.size() == batchProcessCount) {
                batchFetchData(symbol, stockList);
                em.flush();
                stockList.clear();
            }
        }
        if (!stockList.isEmpty()) {
            batchFetchData(symbol, stockList);
            em.flush();
        }
    }

    private void batchFetchData(String symbol, List<Stock> stockDataList) {
        String compositeUrl = "";
        for (Stock stock : stockDataList) {
            compositeUrl += ("," + symbol + stock.getStockCode());
        }
        String requestUrl = BASE_URL + compositeUrl.substring(1);
        String stockDataMulti = null;
        try {
            stockDataMulti = new SPHttpClient().getContentFromUrl(requestUrl);
        } catch (Exception ex) {
            Logger.getLogger(CollectDailyPriceSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (stockDataMulti != null) {
            int index = 0;
            List<DailyPrice> dailyPriceList = new ArrayList<>();
            for (String stockData : stockDataMulti.split(";")) {
                Stock stock = stockDataList.get(index++);

                if (!stockData.contains(stock.getStockCode())) {
                    LOG.warning("ERROR: return result does not contain request stock code");
                    continue;
                }
                if (stockData.split("\"").length < 2) {
                    // some stock will return empty data
                    continue;
                }
                String data = stockData.split("\"")[1];
                if (data.split(",").length < 30) {
                    continue;
                }

                String[] trade = data.split(",");

                DailyPrice dp = new DailyPrice();
                dp.setOpenPrice(BigDecimal.valueOf(Double.parseDouble(trade[1])));
                dp.setClosePrice(BigDecimal.valueOf(Double.parseDouble(trade[3])));
                dp.setMaxPrice(BigDecimal.valueOf(Double.parseDouble(trade[4])));
                dp.setMinPrice(BigDecimal.valueOf(Double.parseDouble(trade[5])));
                dp.setTradeNum(Integer.parseInt("" + Long.parseLong(trade[8]) / 10000)); // 单位：百手
                dp.setTradeAmount((int) Math.round(Double.parseDouble(trade[9]) / 10000)); // 单位：万元
                dp.setStockId(stock);
                dp.setTradeDate(new Date());
                em.persist(dp);
            }
        }

    }

}
