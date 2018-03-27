package com.skoo.stock.util.robotutil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 13-9-26
 * Time: 上午10:03
 */
public class RobotTest
{
    public static void main(String[] args) throws AWTException,
            InterruptedException, IOException
    {

        Robot robot = new Robot();
        //设置Robot产生一个动作后的休眠时间,否则执行过快
        robot.setAutoDelay(1000);

        //获取屏幕分辨率
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(d);
        Rectangle screenRect = new Rectangle(d);
        //截图
        BufferedImage bufferedImage =
                robot.createScreenCapture(screenRect);
        //保存截图
        File file = new File("screenRect.png");
        ImageIO.write(bufferedImage, "png", file);

        //移动鼠标
        robot.mouseMove(500, 500);

        //点击鼠标
        //鼠标左键
        System.out.println("单击");
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        //鼠标右键
        System.out.println("右击");
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);

        //按下ESC，退出右键状态
        System.out.println("按下ESC");
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
        //滚动鼠标滚轴
        System.out.println("滚轴");
        robot.mouseWheel(5);

        //按下Alt+TAB键
        robot.keyPress(KeyEvent.VK_ALT);
        for(int i=1;i<=2;i++)
        {
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
        }
        robot.keyRelease(KeyEvent.VK_ALT);

    }
}