package com.ijustyce.fastandroiddev3.baseLib.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * Created by yangchun on 2017/4/26.
 */

public class CpuUtils {

    public static int getCoreNumber() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                if(Pattern.matches("cpu([0-9])+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch(Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}
