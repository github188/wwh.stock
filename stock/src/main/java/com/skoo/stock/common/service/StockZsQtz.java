package com.skoo.stock.common.service;

import com.skoo.commons.DateUtils;
import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.common.Constant;
import com.skoo.stock.util.FileUtil;
import com.skoo.stock.util.robotutil.RobotManager;
import com.skoo.stock.zs.domain.HqDetails;
import com.skoo.stock.zs.domain.RxData;
import com.skoo.stock.zs.service.HqDetailsService;
import com.skoo.stock.zs.service.RxDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@SuppressWarnings("unchecked")
public class StockZsQtz {
    private static final Logger LOG = LoggerFactory.getLogger(Constant.LOGFILE);

    protected final static HqDetailsService hService = SpringContextUtil.getBean(HqDetailsService.class);
    protected final static RxDataService rService = SpringContextUtil.getBean(RxDataService.class);

    public void execute() {

        try {
            getHqFile();

            String tempPath = "E:\\zszq\\zd_zszq\\T0002\\export\\沪深Ａ股";
            SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
            String dt = formater.format(new Date());
            tempPath += dt + ".txt";
            List<String[]> list = FileUtil.readFile(tempPath);
            String[] val;
            for(int i=1;i<list.size()-1;i++){
                val = (String[])list.get(i);
                HqDetails o = setHqData(val);
                rService.saveOrUpdate(o);
            }

            LOG.info("调度完成");
        } catch (Exception ex) {
            LOG.error("[StockZsQtz]： "+ex.getCause());
        }
    }

    public void executeRx() {

        try {
            //getHqFile();

            String code = "002424";
            String tempPath = "E:\\zszq\\zd_zszq\\T0002\\export\\";
            tempPath += code + ".txt";
            List<String[]> list = FileUtil.readFile(tempPath);
            String[] val;
            String name = ((String[])list.get(0))[0].trim().split(" ")[0];
            for(int i=2;i<list.size()-1;i++){
                val = (String[])list.get(i);
                RxData o = setRxData(val,code,name);
                hService.saveOrUpdate(o);
            }

            LOG.info("调度完成");
        } catch (Exception ex) {
            LOG.error("[StockZsQtz]： "+ex.getCause());
        }
    }

    public static void getHqFile() throws Exception {
        RobotManager robot = new RobotManager();
        Runtime.getRuntime().exec("E:\\zszq\\zd_zszq\\TdxW.exe");
        robot.moveClick(675, 250);
        robot.pressKey(KeyEvent.VK_ENTER);
        robot.delay(30000);
        robot.pressKeys(new int[]{KeyEvent.VK_7, KeyEvent.VK_ENTER});
        robot.pressKeys(new int[]{KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_ENTER});
        robot.moveClick(588, 390);
        robot.pressKey(KeyEvent.VK_ENTER);
        robot.delay(30000);
        robot.moveClick(715, 440);
    }

    private static HqDetails setHqData(String[] val) {
        HqDetails o = new HqDetails();
        o.setDt(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR));
        o.setStockCode(val[0]);
        o.setStockName(val[1]);
        o.setChangeWidth(val[2]);
        o.setPrice(val[3]);
        o.setChangeAmount(val[4]);
        o.setBuyPrice(val[5]);
        o.setSalePrice(val[6]);
        o.setTotalVolume(val[7]);
        o.setVolume(val[8]);
        o.setChangeRate(val[9]);
        o.setTurnOver(val[10]);
        o.setCurOpen(val[11]);
        o.setHigh(val[12]);
        o.setLow(val[13]);
        o.setPreClose(val[14]);
        o.setPe(val[15]);
        o.setAmount(val[16]);
        o.setVolumeRatio(val[17]);
        return o;
    }

    private static RxData setRxData(String[] val,String code,String name) {
        RxData o = new RxData();
        o.setDt(val[0]);
        o.setStockCode(code);
        o.setStockName(name);
        o.setCurOpen(val[1]);
        o.setHigh(val[2]);
        o.setLow(val[3]);
        o.setCurClose(val[4]);
        o.setVolume(val[5]);
        o.setMa1(val[6]);
        o.setMa2(val[7]);
        o.setMa3(val[8]);
        o.setMa4(val[9]);
        o.setMavol1(val[13]);
        o.setMavol2(val[14]);
        o.setK(val[15]);
        o.setD(val[16]);
        o.setJ(val[17]);
        return o;
    }
}
