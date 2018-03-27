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
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author sp
 */
@Remote
public interface StockQuerySessionBeanRemote {

    public DailyPrice queryStock(final String stockCode) throws PrivilegeNeedException, StockNotFoundException;
    public List<DailyPrice> queryRecentDailyPriceList(final String stockCode, int maxQuerySize);
    public List<Stock> retrieveRecentQueriedStock();
}
