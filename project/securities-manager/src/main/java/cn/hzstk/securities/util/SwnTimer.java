package cn.hzstk.securities.util;

import cn.hzstk.securities.common.constants.SystemProperties;
import net.ryian.core.SystemConfig;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.io.File;

public class SwnTimer {
    private boolean stop = false;
    private File f = null;
    private long time = 0;
    public void isExist(String fName){
        ActionListener taskPerformer= e -> {
            if (FileUtil.isExistFile(fName)) {
                stop = true;
            }
        };
        stop(taskPerformer);
    }

    public void isModify(){
        ActionListener taskPerformer= e -> {
            if (time != f.lastModified()) {
                stop = true;
            }
        };
        f = new File(SystemConfig.INSTANCE.getValue(SystemProperties.ZS_INSTALL_PATH)+"connect.cfg");
        time = f.lastModified();
        //Timer每一秒轮转一次
        stop(taskPerformer);
    }

    private void stop(ActionListener taskPerformer){
        //Timer每一秒轮转一次
        Timer timer = new Timer(1000, taskPerformer);
        timer.start();
        for (int i = 0; i < 600; i++) {
            if (stop) break;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        timer.stop();
    }
}
