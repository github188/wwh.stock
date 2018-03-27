package com.skoo.stock.common.checkcode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

/**
 * PHP 移植到java by xwh
 */
public class CheckCode {
    private static final Log logger = LogFactory.getLog(CheckCode.class);
    public static Color bgColor = new Color(243, 251, 254);  // 背景
    public static Color foreColor = new Color(243, 251, 254);  // 前景
    public static char[] codeSet = {'3', '4', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'T', 'U', 'V', 'W', 'X', 'Y'};
    private static int imageL = 0;
    private static int imageH = 0;
    private static int length = 4;
    private static int fontSize = 20;
    private static Random random = new Random();
    private static Font[] fonts = new Font[3];
    private static BufferedImage buffImg;
    private static CheckCode checkCode = new CheckCode();
    private static boolean useNoise = true;
    private static boolean useCurve = true;

    static {
        try {
            // 初始化画板
            initParam();

            // 载入字体
            File fileDir = new File(checkCode.getClass().getResource("/font/").getFile());
            File[] tempList = fileDir.listFiles();
            if (tempList != null && tempList.length > 0) {
                fonts = new Font[tempList.length];
                for (int i = 0; i < tempList.length; i++) {
                    if (tempList[i].isFile()) {
                        File file = tempList[i];
                        FileInputStream fi = new FileInputStream(file);
                        BufferedInputStream fb = new BufferedInputStream(fi);
                        fonts[i] = Font.createFont(Font.TRUETYPE_FONT, fb);
                    }
                }
            }
        } catch (Exception ex) {
            fonts = null;
            logger.debug("文件夹内字体载入失败！");
        }
    }

    /**
     * 验证码字符串
     */
    private String checkCodeStr;

    /**
     * 生成新的验证码
     */
    public static CheckCode fresh() {
        checkCode.setCheckCodeStr(createRandomCode());
        return checkCode;
    }

    /**
     * 随机产生验证码
     */
    private static String createRandomCode() {
        Graphics2D graphics = graphicsInit();
        if (useNoise) {
            writeNoise(graphics);
        }

        foreColor = new Color(rand(1, 120), rand(1, 120), rand(1, 120));
        graphics.setColor(foreColor);
        graphics.setFont(getFont());

        if (useCurve) {
            writeCurve(graphics);
        }
        return paintCode(graphics);
    }

    /**
     * 产生随机字体
     *
     * @return 字体
     */
    private static Font getFont() {
        Font font;

        try {
            if (fonts != null && fonts.length > 0) {
                font = fonts[random.nextInt(fonts.length)];
                font = font.deriveFont(Font.PLAIN, (int) (fontSize * 1.5));
            } else {
                font = new Font("Airbus Special", Font.PLAIN, (int) (fontSize * 1.5));
            }
        } catch (Exception ex) {
            font = new Font("Airbus Special", Font.PLAIN, (int) (fontSize * 1.5));
        }

        return font;
    }

    private static String paintCode(Graphics2D graphics) {
        StringBuilder randomCode = new StringBuilder();
        // 绘验证码
        int codeNX = 0; // 验证码第N个字符的左边距
        int codeNY = (int) (fontSize * 1.5); // 验证码第N个字符的左边距
        for (int i = 0; i < length; i++) {
            String strRand = String.valueOf(codeSet[random.nextInt(codeSet.length)]);
            randomCode.append(strRand);
            codeNX += rand(fontSize * 1.2, fontSize * 1.6);

            graphics.translate(codeNX, codeNY);
            double dd = randDouble(-40 * Math.PI / 180, 40 * Math.PI / 180); //左右倾斜角度
            graphics.rotate(dd);
            graphics.drawString(strRand, 0, 0);
            graphics.rotate(-dd);
            graphics.translate(-codeNX, -codeNY);
        }

        return randomCode.toString();
    }

    /**
     * 绘制类初始化
     *
     * @return graphics
     */
    private static Graphics2D graphicsInit() {
        Graphics2D graphics = buffImg.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        graphics.setColor(bgColor);
        graphics.fillRect(0, 0, imageL, imageH);

        return graphics;
    }

    /**
     * 绘制干扰杂点
     */
    private static void writeNoise(Graphics2D graphics) {
        /**
         * 画杂点
         * 往图片上写不同颜色的字母或数字
         */
        int out = fontSize / length + 2;
        for (int i = 0; i < out; i++) {
            //杂点颜色
            Color cl = new Color(rand(150, 225),
                    rand(150, 225),
                    rand(150, 225)
            );

            for (int j = 0; j < 5; j++) {
                // 绘杂点
                graphics.setColor(cl);
                graphics.setFont(new Font("Verdana", Font.BOLD, 12));
                int x = rand(-10, imageL);
                int y = rand(-10, imageH);
                graphics.drawString(String.valueOf(codeSet[random.nextInt(codeSet.length)]), x, y);
            }
        }
    }

    /**
     * 绘制干扰线（实线）绘点X坐标不加1就可以变为实曲线
     */
    protected static void writeCurve(Graphics2D graphics) {
        double px;
        double py = 0;

        // 曲线前部分
        double A = randDouble(1, imageH / 2);                   // 振幅
        double b = randDouble(imageH / 4, imageH / 4);          // Y轴方向偏移量
        double f = randDouble(imageH / 4, imageH / 4);          // X轴方向偏移量
        double T = randDouble(imageH, imageL * 2);              // 周期
        double w = (2 * Math.PI) / T;

        double px1 = 0;                                         // 曲线横坐标起始位置
        double px2 = randDouble(imageL / 2, imageL * 0.8);      // 曲线横坐标结束位置

        graphics.setColor(foreColor);
        for (px = px1; px <= px2; px = px + 0.9) {
            if (w != 0) {
                py = A * Math.sin(w * px + f) + b + imageH / 2;  // y = Asin(ωx+φ) + b
                int i = fontSize / 5;
                while (i > 0) {
                    // 这里(while)循环画像素点比imagettftext和imagestring用字体大小一次画出
                    // （不用这while循环）性能要好很多
                    int x = (int) (px + i);
                    int y = (int) (py + i);
                    graphics.drawLine(x,y,x,y);
                    i--;
                }
            }
        }

        // 曲线后部分
        A = randDouble(1, imageH / 2);                  // 振幅
        f = rand(imageH / 4, imageH / 4);               // X轴方向偏移量
        T = randDouble(imageH, imageL * 2);             // 周期
        w = (2 * Math.PI) / T;
        b = py - A * Math.sin(w * px + f) - imageH / 2;
        px1 = px2;
        px2 = imageL;

        for (px = px1; px <= px2; px = px + 0.9) {
            if (w != 0) {
                py = A * Math.sin(w * px + f) + b + imageH / 2;  // y = Asin(ωx+φ) + b
                int i = fontSize / 5;
                while (i > 0) {
                    int x = (int) (px + i);
                    int y = (int) (py + i);
                    graphics.drawLine(x,y,x,y);
                    i--;
                }
            }
        }
    }


    private static int rand(double from, double to) {
        return (int) (from + Math.random() * (to - from));
    }

    private static double randDouble(double from, double to) {
        return from + Math.random() * (to - from);
    }

    /**
     * 初始化画板
     */
    private static void initParam() {
        // 图片宽(px)
        imageL = (int) (length * fontSize * 1.5 + fontSize * 1.5);
        // 图片高(px)
        imageH = fontSize * 2;
        buffImg = new BufferedImage(imageL, imageH, BufferedImage.TYPE_INT_RGB);
    }

    public BufferedImage getBuffImg() {
        return buffImg;
    }

    public String getCheckCodeStr() {
        return checkCodeStr;
    }

    public void setCheckCodeStr(String checkCodeStr) {
        this.checkCodeStr = checkCodeStr;
    }

    public static int getLength() {
        return length;
    }

    public static void setLength(int length) {
        CheckCode.length = length;
        initParam();
    }

    public static int getFontSize() {
        return fontSize;
    }

    public static void setFontSize(int fontSize) {
        CheckCode.fontSize = fontSize;
        initParam();
    }

    public static boolean isUseCurve() {
        return useCurve;
    }

    public static void setUseCurve(boolean useCurve) {
        CheckCode.useCurve = useCurve;
    }

    public static boolean isUseNoise() {
        return useNoise;
    }

    public static void setUseNoise(boolean useNoise) {
        CheckCode.useNoise = useNoise;
    }
}