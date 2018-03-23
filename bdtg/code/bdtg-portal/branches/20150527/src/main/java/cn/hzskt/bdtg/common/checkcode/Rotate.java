package cn.hzskt.bdtg.common.checkcode;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * @author huangxf
 */
public class Rotate {
    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().add(new RotatePanel());
        jf.setPreferredSize(new Dimension(500, 400));
        jf.pack();
        jf.setVisible(true);
    }
}

class RotatePanel extends JPanel {
    private static int imageL = 150;
    private static int imageH = 40;
    private static int length = 4;
    private static int fontSize = 20;
    public static char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static Random random = new Random();

    private static int rand(double from, double to) {
        return (int) (from + Math.random() * (to - from));
    }

    private static double randDouble(double from, double to) {
        return from + Math.random() * (to - from);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(243, 251, 254));
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        String s = "W";
        Font f2 = new Font("Stencil Four", Font.PLAIN, (int)(20*1.5));
        Color[] colors = {Color.ORANGE, Color.LIGHT_GRAY};

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        g2d.setFont(f2);
        //   平移原点到图形环境的中心
                //   旋转文本
        for (int i = 0; i < 12; i++) {
            g2d.translate(i*30, this.getHeight() / 2);
            g2d.rotate(30 * Math.PI / 180);
            g2d.setPaint(colors[i % 2]);
            g2d.drawString(s, 0, 0);
            g2d.rotate(-30 * Math.PI / 180);
            g2d.translate(-i*30, -this.getHeight() / 2);
        }

        for (int i = 0; i < 10; i++) {
            //杂点颜色
            Color cl = new Color(rand(150, 225),
                    rand(150, 225),
                    rand(150, 225)
            );

            for (int j = 0; j < 5; j++) {
                // 绘杂点
                g2d.setColor(cl);
                g2d.setFont(new Font("Stencisadfasl Four", Font.PLAIN, 11));
                int x = rand(-10, 150);
                int y = rand(-10, 40);
                g2d.drawString(String.valueOf(codeSequence[random.nextInt(32)]), x, y);
            }
        }




        double px = 0;
        double py = 0;

        // 曲线前部分
        double A = randDouble(1, imageH / 2);                  // 振幅
        double b = randDouble(imageH / 4, imageH / 4);   // Y轴方向偏移量
        double f = randDouble(imageH / 4, imageH / 4);   // X轴方向偏移量
        double T = randDouble(imageH, imageL * 2);  // 周期
        double w = (2 * Math.PI) / T;

        double px1 = 0;  // 曲线横坐标起始位置
        double px2 = randDouble(imageL / 2, imageL * 0.8);  // 曲线横坐标结束位置

        g2d.setColor(new Color(rand(1, 120), rand(1, 120), rand(1, 120)));
        for (px = px1; px <= px2; px = px + 0.9) {
            if (w != 0) {
                py = A * Math.sin(w * px + f) + b + imageH / 2;  // y = Asin(ωx+φ) + b
                int i = (int) (fontSize / 5);
                while (i > 0) {
                    // 这里(while)循环画像素点比imagettftext和imagestring用字体大小一次画出（不用这while循环）性能要好很多
                    g2d.drawLine((int) px, (int) (py + i), (int) px, (int) (py + i));
                    i--;
                }
            }
        }

        // 曲线后部分
        A = randDouble(1, imageH / 2);                  // 振幅
        f = rand(imageH / 4, imageH / 4);   // X轴方向偏移量
        T = randDouble(imageH, imageL * 2);  // 周期
        w = (2 * Math.PI) / T;
        b = py - A * Math.sin(w * px + f) - imageH / 2;
        px1 = px2;
        px2 = imageL;

        for (px = px1; px <= px2; px = px + 0.9) {
            if (w != 0) {
                py = A * Math.sin(w * px + f) + b + imageH / 2;  // y = Asin(ωx+φ) + b
                int i = (int) (fontSize / 5);
                while (i > 0) {
                    g2d.drawLine((int) px, (int) (py + i), (int) px, (int) (py + i));
                    i--;
                }
            }
        }







        int codeNX = 0; // 验证码第N个字符的左边距
        int codeNY = (int) (20 * 1.5); // 验证码第N个字符的左边距
        g2d.setFont(new Font("Stencil Four", Font.PLAIN, (int) (20 * 1.5)));

        for (int i = 0; i < 4; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(32)]);
            codeNX += rand(20 * 1.2, 20 * 1.6);

            g2d.translate(codeNX, codeNY);
            double dd = randDouble(-40 * Math.PI / 180, 40 * Math.PI / 180);
            g2d.rotate(dd);
            //g2d.rotate(randDouble(-0.5,0.5));
            g2d.drawString(strRand, 0, 0);
            g2d.rotate(-dd);
            g2d.translate(-codeNX, -codeNY);
        }


    }


}