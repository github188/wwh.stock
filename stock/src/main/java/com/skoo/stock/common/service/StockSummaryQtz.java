package com.skoo.stock.common.service;

import com.skoo.commons.DateUtils;
import com.skoo.commons.StringUtils;
import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.hsa.domain.IndexData;
import com.skoo.stock.hsa.service.IndexDataService;
import com.skoo.stock.util.FileUtil;
import com.skoo.stock.util.StockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@SuppressWarnings("unchecked")
public class StockSummaryQtz {
    private static final Logger LOG = LoggerFactory.getLogger("mytest");

    protected final static IndexDataService iService = SpringContextUtil.getBean(IndexDataService.class);

    public void execute() {

        try {
            String[] code = {"sha","sza","small","gem"};
            for (int i = 0; i < code.length; i++) {
                index_sum(code[i]);
            }

            boolean loopFlag = true;
            String sAddr;
            do {
                List<String> listName = FileUtil.readStockFile();
                if (listName.size() == 0) {
                    loopFlag = false;
                } else {
                    for (int k = 0; k < listName.size(); k++) {
                        sAddr = listName.get(k);
                        if (sAddr.indexOf("<") < 0) continue;
                        sAddr = sAddr.split("<")[1].replace(">","");
                        if (sAddr.indexOf("stock/")<0) continue;
                        sAddr = sAddr.split("stock/")[1].split(".")[0];
                        index_sum(sAddr);
                    }
                }
            } while (loopFlag);
        } catch (Exception ex) {
            LOG.error("[StockSummaryQtz]："+ex.getMessage());
        }
    }

    private static void index_sum(String code) {
        String[] val = StockUtil.getStockSum(code);
        if (val == null) return;
        IndexData o = setInexSum(val);
        iService.saveOrUpdate(o);
    }

    private static IndexData setInexSum(String[] val) {
        IndexData o = new IndexData();
        o.setCode(val[0]);
        o.setName(val[8]);
        o.setDt(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR));
        o.setStartPrice(StockUtil.cvtDouble(val[5]));
        o.setEndPrice(StockUtil.cvtDouble(val[4]));
        o.setClosingPrice(StockUtil.cvtDouble(val[1]));
        o.setUdAmount(StockUtil.cvtDouble(val[2]));
        o.setUdWidth(val[3]);
        o.setHighestPrice(StockUtil.cvtDouble(val[6]));
        o.setLowestPrice(StockUtil.cvtDouble(val[7]));
        o.setVolume(StockUtil.cvtDouble(val[12]));
        o.setTurnVolume(StockUtil.cvtDouble(val[15]));
        o.setRiseCnt(val[10]);
        o.setFlatCnt(val[13]);
        o.setFallCnt(val[16]);
        return o;
    }
}
