package cn.hzstk.securities.common.service;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.VarConfig;
import cn.hzstk.securities.common.constants.SystemProperties;
import cn.hzstk.securities.stk.domain.*;
import cn.hzstk.securities.stk.service.*;
import cn.hzstk.securities.util.*;
import cn.hzstk.securities.util.robotutil.RobotManager;
import com.sun.jna.platform.win32.WinDef;
import net.ryian.commons.DateUtils;
import net.ryian.core.SystemConfig;
import net.ryian.core.utils.SpringContextUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.util.*;

@Component
@SuppressWarnings("unchecked")
public class StkCommQtz {
    protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static HqDetailsService hService = SpringContextUtil.getBean(HqDetailsService.class);
    private final static RxDataService rService = SpringContextUtil.getBean(RxDataService.class);
    private final static RxHisService rhService = SpringContextUtil.getBean(RxHisService.class);
    private final static PlateService pService = SpringContextUtil.getBean(PlateService.class);
    private final static PlateStockService psService = SpringContextUtil.getBean(PlateStockService.class);
    private final static SelfSelectService sService = SpringContextUtil.getBean(SelfSelectService.class);
    private final static FundDetailsService fService = SpringContextUtil.getBean(FundDetailsService.class);
    private final static InvestCalendarService iService = SpringContextUtil.getBean(InvestCalendarService.class);
    private final static TigerService tService = SpringContextUtil.getBean(TigerService.class);

    public void execute(String dt) {
        try {
            String name = "沪深Ａ股";
            if (StringUtils.isEmpty(dt)) dt = DateUtils.format(new Date());
            //if (hService.queryCntByDt(dt) > Constant.DATA_COUNT) return;
            getHqFile(name);

            if (!FileUtil.isExistFile(name)) return;
            List<String[]> list = FileUtil.readFile(name);
            String[] val;
            for(int i=1;i<list.size()-1;i++){
                val = list.get(i);
                HqDetails o = setHqData(val,dt);
                hService.saveOrUpdate(o);
            }

            FileUtil.deleteFile(name);
            log.info("行情数据生成");
        } catch (Exception ex) {
            logger.error("[execute]： "+ex.toString());
        }
    }
    private static void getHqFile(String fName) throws Exception {
        if (!runSoft()) return;

        RobotManager.pressKeys(new int[]{KeyEvent.VK_7, KeyEvent.VK_ENTER});
        RobotManager.moveClick(165, 95);
        exportCmd(fName);//10000
    }

    private static void exportCmd(String fName) throws Exception {
        RobotManager.pressKeys(new int[]{KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_ENTER});
        RobotManager.moveClick(588, 390);
        RobotManager.pressKey(KeyEvent.VK_ENTER);
        SwnTimer swnTimer = new SwnTimer();
        swnTimer.isExist(fName);
        RobotManager.delay();
        RobotManager.moveClick(715, 440);
        if (!VarConfig.isRxFlag()) setActive(Constant.GOOGLE_CLASSNAME);
        //RobotManager.pressShitKeys(new int[]{KeyEvent.VK_ALT, KeyEvent.VK_TAB});
    }
    private static HqDetails setHqData(String[] val,String dt) {
        HqDetails o = new HqDetails();
        if (StringUtils.isEmpty(dt)) dt = DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR);
        o.setDt(dt);
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

    public String executeRx(String stockCode, String startCode) {
        try {
            String dt = hService.selectMinDt();
            if (!StringUtils.isEmpty(stockCode) && StringUtils.isNumeric(stockCode)) {
                if ("0".equals(stockCode)) {
                    VarConfig.setRxFlag(true);
                    if (StringUtils.isEmpty(startCode)) startCode = "0";
                    List<String> list = rService.getStockInfo(startCode);
                    for (String code : list) {
                        if (getRxSingle(code, dt)) return code;
                    }
                } else {
                    getRxSingle(stockCode, dt);
                }
            } else {
                VarConfig.setRxFlag(true);
                Map<String, String> paramMap = new HashMap<>();
                List<SelfSelect> list = sService.query(paramMap);
                for (SelfSelect self : list) {
                    if (getRxSingle(self.getStockCode(), dt)) return self.getStockCode();
                }
            }
            VarConfig.setRxFlag(false);

            log.info("日线数据生成");
        } catch (Exception ex) {
            logger.error("[executeRx]： "+ex.toString());
        }
        return null;
    }
    private static boolean getRxSingle(String stockCode, String dt) throws Exception {
        String name = stockCode + Constant.FILE_SUFFIX_TXT;
        getRxFile(stockCode, name);

        if (!FileUtil.isExistFile(name)) return true;
        List<String[]> list = FileUtil.readFile(name);
        if (list.size() == 0) return true;
        String[] val;
        String stockName = (list.get(0))[0].trim().split(" ")[0];
        for(int i=2;i<list.size()-1;i++){
            val = list.get(i);
            if (DateUtils.format(DateUtils.stringToUtilDate(val[0])).compareTo(dt) < 0) {
                RxHis o = setRxHis(val, stockCode, stockName);
                rhService.saveOrUpdate(o);
            } else {
                RxData o = setRxData(val, stockCode, stockName);
                rService.saveOrUpdate(o);
            }
        }
        FileUtil.deleteFile(name);
        return false;
    }
    private static void getRxFile(String stockCode,String fName) throws Exception {
        if (!runSoft()) return;

        String val;
        for(int i = 0;i<stockCode.length();i++){
            val = stockCode.substring(i, i + 1);
            RobotManager.pressKey((int) (val.charAt(0)));
        }

        RobotManager.pressKey(KeyEvent.VK_ENTER);
        exportCmd(fName);//1000
    }

    private static RxData setRxData(String[] val,String code,String name) {
        RxData o = new RxData();
        o.setDt(DateUtils.format(DateUtils.stringToUtilDate(val[0])));
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

    private static RxHis setRxHis(String[] val,String code,String name) {
        RxHis o = new RxHis();
        o.setDt(DateUtils.format(DateUtils.stringToUtilDate(val[0])));
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

    public void executeBk(String dt) {
        try {
            if (StringUtils.isEmpty(dt)) dt = DateUtils.format(new Date());
            if (pService.queryCntByDt(dt) > 0) return;
            String name = "板块指数";
            getBkFile(name);

            if (!FileUtil.isExistFile(name)) return;
            List<String[]> list = FileUtil.readFile(name);
            String[] val;
            for(int i=1;i<list.size()-1;i++){
                val = list.get(i);
                Plate o = setPlate(val, dt);
                pService.saveOrUpdate(o);
            }

            FileUtil.deleteFile(name);
            log.info("板块数据生成");
        } catch (Exception ex) {
            logger.error("[execute]： "+ex.toString());
        }
    }

    private static void getBkFile(String fName) throws Exception {
        if (!runSoft()) return;

        RobotManager.pressKeys(new int[]{KeyEvent.VK_7, KeyEvent.VK_ENTER});
        RobotManager.moveClick(747, 46);
        RobotManager.moveClick(170, 116);
        exportCmd(fName);//5000
    }
    private static Plate setPlate(String[] val, String dt) {
        Plate o = new Plate();
        if (StringUtils.isEmpty(dt)) dt = DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR);
        o.setDt(dt);
        o.setPlateCode(val[0]);
        o.setPlateName(val[1]);
        o.setChangeWidth(val[2]);
        o.setPrice(val[3]);
        o.setChangeAmount(val[4]);
        o.setChangeRate(val[5]);
        o.setVolumeRatio(val[6]);
        o.setChangeNum(val[7]);
        o.setEvenUp(val[8]);
        o.setThreeChangeWidth(val[9]);
        o.setTurnOver(val[10]);
        //o.setAmount(val[16]);
        return o;
    }

    public void executeBs(String plateCode) {
        try {
            Map<String, String> paramMap = new HashMap<>();
            if (StringUtils.isEmpty(plateCode)) {
                List<Plate> list = pService.queryPlate();
                for (Plate p : list) {
                    paramMap.put("plateCode", p.getPlateCode());
                    List<PlateStock> listStock = psService.query(paramMap);
                    if(listStock != null && listStock.size() > 0) continue;
                    if (p.getPlateCode().startsWith("8808") || p.getPlateCode().startsWith("8802")) continue;
                    if (getBsSingle(p)) return;
                }
            } else {
                paramMap.put("plateCode", plateCode);
                List<Plate> list = pService.query(paramMap);
                if(list != null && list.size() > 0) {
                    getBsSingle(list.get(0));
                }
            }

            log.info("板块详细信息生成");
        } catch (Exception ex) {
            logger.error("[executeBs]： "+ex.toString());
        }
    }
    private static boolean getBsSingle(Plate p) throws Exception {
        String name = p.getPlateName();
        getBsFile(p.getPlateName(), name);

        if (!FileUtil.isExistFile(name)) return true;
        List<String[]> list = FileUtil.readFile(name);
        if (list.size() == 0) return true;
        String[] val;
        for(int i=1;i<list.size()-1;i++){
            val = list.get(i);
            PlateStock o = new PlateStock();
            o.setPlateCode(p.getPlateCode());
            o.setStockCode(val[0]);

            psService.saveOrUpdate(o);
        }
        FileUtil.deleteFile(name);
        return false;
    }

    private static void getBsFile(String plateName,String fName) throws Exception {
        if (!runSoft()) return;

        GB2Alpha obj = new GB2Alpha();
        plateName = obj.String2Alpha(plateName);
        String val;
        for(int i = 0;i<plateName.length();i++){
            val = plateName.substring(i, i + 1);
            RobotManager.pressKey((int) (val.charAt(0)));
        }

        RobotManager.pressKey(KeyEvent.VK_ENTER);
        exportCmd(fName);//1000
    }

    public String executeZx() {
        try {
            String name = "自选股.EBK";
            getZxFile(name);

            List<String[]> list = FileUtil.readFile(name);
            for (String[] aList : list) {
                SelfSelect o = new SelfSelect();
                o.setStockCode(aList[0].substring(1));
                sService.saveOrUpdate(o);
            }

            FileUtil.deleteFile(name);
            log.info("自选数据生成");
        } catch (Exception ex) {
            logger.error("[executeZx]： "+ex.toString());
        }
        return null;
    }
    private static void getZxFile(String fName) throws Exception {
        if (!runSoft()) return;

        RobotManager.moveClick(231, 94);
        RobotManager.moveClick(716, 142);
        RobotManager.moveClick(361, 117);
        RobotManager.moveClick(939, 650);
        RobotManager.moveClick(842, 512);
        if (FileUtil.isExistFile(fName)) RobotManager.moveClick(708, 400);
        RobotManager.delay();
        RobotManager.moveClick(688, 438);
        RobotManager.moveClick(947, 695);
        RobotManager.pressShitKeys(new int[]{KeyEvent.VK_ALT, KeyEvent.VK_TAB});
    }

    public void executeZj(String dt) {
        try {
            String name = "沪深Ａ股";
            if (StringUtils.isEmpty(dt)) dt = DateUtils.format(new Date());
            if (fService.queryCntByDt(dt) > Constant.DATA_COUNT) return;

            getZjFile(name);

            if (!FileUtil.isExistFile(name)) return;
            List<String[]> list = FileUtil.readFile(name);
            String[] val;
            for(int i=1;i<list.size()-1;i++){
                val = list.get(i);
                FundDetails o = setFundDetails(val, dt);
                fService.saveOrUpdate(o);
            }

            FileUtil.deleteFile(name);
            log.info("资金流入数据生成");
        } catch (Exception ex) {
            logger.error("[executeZx]： "+ex.toString());
        }
    }

    private static void getZjFile(String fName) throws Exception {
        if (!runSoft()) return;

        RobotManager.pressKeys(new int[]{KeyEvent.VK_7, KeyEvent.VK_ENTER});
        RobotManager.moveClick(227, 95);
        exportCmd(fName);//5000
    }

    private static FundDetails setFundDetails(String[] val, String dt) {
        FundDetails o = new FundDetails();
        if (StringUtils.isEmpty(dt)) dt = DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR);
        o.setDt(dt);
        o.setStockCode(val[0]);
        o.setStockName(val[1]);
        o.setChangeWidth(val[2]);
        o.setPrice(val[3]);
        o.setTurnOver(val[4]);
        o.setNetRate(val[5]);
        o.setNetAmount(val[6]);
        o.setRelativeFlow(val[7]);
        o.setLargeAmount(val[8]);
        o.setLargeFlow(val[9]);
        o.setChangeWidth5(val[10]);
        o.setTurnOver5(val[11]);
        o.setNetAmount5(val[12]);
        o.setRelativeFlow5(val[13]);
        o.setLargeAmount5(val[14]);
        o.setLargeFlow5(val[15]);
        return o;
    }

    private static boolean runSoft() throws Exception {
        String processName = SystemConfig.INSTANCE.getValue(SystemProperties.ZS_MAIN_PROGRAM);
        if (!FileUtil.isRunning(processName)) {
            Runtime.getRuntime().exec("cmd /c " + SystemConfig.INSTANCE.getValue(SystemProperties.ZS_INSTALL_PATH) + processName);
            VarConfig.setInvestOpen(false);
        }
        return setActive(Constant.ZS_CLASSNAME);
    }

    private static boolean setActive(String className) {
        TryWithHWND.User32 user32 = TryWithHWND.User32.INSTANCE;

        boolean bFlag = true;
        WinDef.HWND hwnd = user32.FindWindowA(className, null);
        if (hwnd == null && Constant.ZS_CLASSNAME.equals(className)) {
            bFlag = false;
            hwnd = user32.FindWindowA("#32770", null);
            if (hwnd == null) return false;
            hwnd = user32.FindWindowExA(null, hwnd, null, null);
        }
        if (hwnd == null) return bFlag;

        user32.SetForegroundWindow(hwnd);
        RobotManager.delay(Constant.SECOND);
        if (!bFlag) {
            RobotManager.delay(10 * Constant.SECOND);
            RobotManager.moveClick(675, 250);
            SwnTimer swnTimer = new SwnTimer();
            swnTimer.isModify();
            RobotManager.delay(10 * Constant.SECOND);
            RobotManager.pressKey(KeyEvent.VK_ENTER);
        }
        return true;
    }

    public void executeInvest(String dt) {
        try {
            String name = "投资日历" + Constant.FILE_SUFFIX_XLS;
            if (StringUtils.isEmpty(dt)) {
                dt = DateUtils.format(new Date());
                dt = DateUtil.getDayExceptWeekend(DateUtil.getNextDay(dt));
                for (int i = 0; i < 5; i++) {
                    if (i > 0) dt = DateUtils.format(DateUtils.nextDay(DateUtils.stringToUtilDate(dt)));
                    execInvestDt(name, dt);
                }
            } else {
                dt = DateUtil.getDayExceptWeekend(DateUtil.getNextDay(dt));
                execInvestDt(name, dt);
            }

            log.info("投资日历数据生成");
        } catch (Exception ex) {
            logger.error("[executeInvest]： "+ex.toString());
        }
    }

    private static void execInvestDt(String name, String dt) throws Exception {
        if (iService.queryCntByDt(dt) > 0) return;

        getInvestFile(name, dt);

        if (!FileUtil.isExistFile(name)) return;
        for (int i = 0; i < 2; i++) {
            List<String[]> list = ExcelUtil.readExcelSheet(name, i);
            for (String[] val : list) {
                InvestCalendar o = setInvestCalendar(val, i);
                if (o != null) iService.saveOrUpdate(o);
            }
        }
    }

    private static void getInvestFile(String fName, String dt) throws Exception {
        if (!runSoft()) return;

        RobotManager.moveClick(1074, 47);
        if (VarConfig.isInvestOpen()) {
            RobotManager.delay();
        } else {
            VarConfig.setInvestOpen(true);
            RobotManager.delay(20000);
        }
        RobotManager.moveClick(198, 132);
        RobotManager.delay();
        Calendar cal = DateUtil.formatYYYYMMDD(dt);
        int x = cal.get(Calendar.DAY_OF_WEEK);
        int y = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        if (x >= 2 && x < cal.get(Calendar.DAY_OF_WEEK)) y += 1;
        int m = cal.get(Calendar.MONTH) + 1;
        if (Integer.valueOf(DateUtils.format(new Date(), "MM")) >= m)
            RobotManager.moveClick(Constant.CALENDAR_START_X + (x - 2) * Constant.CALENDAR_INCREMENT_X, Constant.CALENDAR_START_Y + (y - 1) * Constant.CALENDAR_INCREMENT_Y);
        else
            RobotManager.moveClick(Constant.CALENDAR_START_X + (x - 2) * Constant.CALENDAR_INCREMENT_X, Constant.CALENDAR_START_Y + Constant.CALENDAR_INCREMENT_MON + (y - 1) * Constant.CALENDAR_INCREMENT_Y);
        RobotManager.delay();
        exportXls(fName);
    }

    private static void exportXls(String fName) throws Exception {
        FileUtil.deleteFile(fName);

        RobotManager.moveClick(360, 155);
        RobotManager.rightClick();
        RobotManager.moveClick(416, 190);
        //RobotManager.moveClick(489, 295);
        RobotManager.moveClick(843, 488);
        SwnTimer swnTimer = new SwnTimer();
        swnTimer.isExist(fName);
        RobotManager.delay();
        //RobotManager.moveClick(715, 440);
        //closeWin(Constant.ZS_CLASSNAME);
        //closeWin(Constant.EXCEL_CLASSNAME);
        setActive(Constant.GOOGLE_CLASSNAME);
        //RobotManager.pressShitKeys(new int[]{KeyEvent.VK_ALT, KeyEvent.VK_TAB});
    }

    private static InvestCalendar setInvestCalendar(String[] val, int no) {
        if (no == 0 && val[3].contains("除息日")) return null;
        if (no == 1 && val[3].contains("登记日")) return null;
        InvestCalendar o = new InvestCalendar();
        o.setDt(val[0]);
        o.setStockCode(DigitFormat.formatNumber(new Double(val[1]), Constant.STOCK_TEMPLATE));
        o.setStockName(val[2]);
        o.setInvestType(val[3]);
        o.setMemo(val[4]);
        return o;
    }

    public void executeTiger(String dt) {
        try {
            String name = "龙虎榜" + Constant.FILE_SUFFIX_XLS;
            //String lastDay = DateUtil.getLastDay(DateUtils.format(new Date()));
            String lastDay = DateUtil.getLastDay(DateUtil.getPreDay(DateUtils.format(new Date())));
            if (StringUtils.isEmpty(dt)) {
                for (int i = 0; i < 5; i++) {
                    if (i > 0) dt = DateUtil.getLastDay(DateUtil.getPreDay(lastDay));
                    execTigerDt(name, dt, i);
                }
            } else {
                for (int i = 0; i < 5; i++) {
                    if (i > 0) lastDay = DateUtil.getLastDay(DateUtil.getPreDay(lastDay));
                    if (lastDay.equals(dt)) {
                        execTigerDt(name, dt, i);
                        break;
                    }
                }
            }

            log.info("龙虎榜数据生成");
        } catch (Exception ex) {
            logger.error("[executeInvest]： "+ex.toString());
        }
    }

    private static void execTigerDt(String name, String dt, int index) throws Exception {
        if (tService.queryCntByDt(dt) > 0) return;

        getTigerFile(name, index);

        if (!FileUtil.isExistFile(name)) return;
        for (int i = 0; i <= 8; i++) {
            if (Math.floorMod(i,2) == 1) continue;
            List<String[]> list = ExcelUtil.readExcelSheet(name, i);
            String[] val;
            for(int j=1;j<list.size();j++){
                val = list.get(j);
                Tiger o = setTiger(val, dt);
                if (o != null) tService.saveOrUpdate(o);
            }
        }
    }

    private static void getTigerFile(String fName, int index) throws Exception {
        if (!runSoft()) return;

        RobotManager.moveClick(1074, 47);
        if (VarConfig.isInvestOpen()) {
            RobotManager.delay();
        } else {
            VarConfig.setInvestOpen(true);
            RobotManager.delay(20000);
        }
        RobotManager.moveClick(204, 158);
        RobotManager.delay();
        RobotManager.moveClick(425 + index * 74, 102);
        RobotManager.delay();
        exportXls(fName);
    }

    private static Tiger setTiger(String[] val, String dt) {
        Tiger o = new Tiger();
        if (StringUtils.isEmpty(dt)) dt = DateUtils.format(new Date(), DateUtils.DATE_FORMAT_STR);
        o.setDt(dt);
        o.setStockCode(DigitFormat.formatNumber(new Double(val[0]), Constant.STOCK_TEMPLATE));
        o.setStockName(val[1]);
        o.setPrice(val[2]);
        return o;
    }

}
