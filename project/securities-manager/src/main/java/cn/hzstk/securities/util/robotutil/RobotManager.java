package cn.hzstk.securities.util.robotutil;

import cn.hzstk.securities.common.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RobotManager {
    private static Dimension dim;
    private static Robot robot;
    static {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        //robot = new RobotUtil();
        new Thread(){
            public void run(){
                robot.delay(2000);
                //回车
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            }
        }.start();
        JOptionPane.showMessageDialog(null,"以下程序自动执行,包括本对话框,请不必进行人为干预.\n如果不能正常执行程序,请先关闭输入法");

        //设置开始菜单的大概位置
        int x = 40;
        int y = dim.height-10;

        //鼠标移动到开始菜单,
        robot.mouseMove(x,y);
        robot.delay(500);

        //单击三次开始菜单
        for(int i=0; i<3; i++)
            pressMouse(InputEvent.BUTTON1_MASK);
        robot.delay(1000);


        //运行CMD命令  r  cmd enter
        int[] ks = {KeyEvent.VK_R,KeyEvent.VK_C,KeyEvent.VK_M,KeyEvent.VK_D,KeyEvent.VK_ENTER,};
        pressKeys(ks);
        robot.mouseMove(400,400);
        robot.delay(500);
        //运行DIR命令  dir enter
        ks = new int[]{KeyEvent.VK_D,KeyEvent.VK_I,KeyEvent.VK_R,KeyEvent.VK_ENTER};
        pressKeys(ks);
        robot.delay(1000);
        //运行CLS命令 cls enter
        ks = new int[]{KeyEvent.VK_C,KeyEvent.VK_L,KeyEvent.VK_S,KeyEvent.VK_ENTER};
        pressKeys(ks);
        robot.delay(1000);
        //运行EXIT命令 exit enter
        ks = new int[]{KeyEvent.VK_E,KeyEvent.VK_X,KeyEvent.VK_I,KeyEvent.VK_T,KeyEvent.VK_ENTER};
        pressKeys(ks);
        robot.delay(1000);

        //右键测试
        x=Toolkit.getDefaultToolkit().getScreenSize().width-10;
        robot.mouseMove(x, y);
        //如果是双键鼠标,请改用InputEvent.BUTTON2_MASK试试,我没有这种鼠标
        pressMouse(InputEvent.BUTTON3_MASK);
        //显示日期调整对话框 a
        pressKeys(new int[]{KeyEvent.VK_A});
        robot.delay(2000);
        pressKeys(new int[]{KeyEvent.VK_ESCAPE});
        new Thread(){
            public void run(){
                robot.delay(1000);
                //回车
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            }
        }.start();
        JOptionPane.showMessageDialog(null,"演示完毕!");
    }
    //鼠标单击,要双击就连续调用
    public static void pressMouse(int m){
        robot.mousePress(m);
        robot.delay(10);
        robot.mouseRelease(m);
        robot.delay(500);
    }
    public static void mouseMove(int x, int y){
        robot.mouseMove(x, y);
        robot.delay(10);
    }
    //键盘输入
    public static void pressKeys(int[] ks){
        for (int k : ks) {
            robot.keyPress(k);
            robot.delay(10);
            robot.keyRelease(k);
            robot.delay(Constant.SECOND);
        }
    }
    public static void pressKey(int keyvalue) {
        robot.keyPress(keyvalue);
        robot.delay(10);
        robot.keyRelease(keyvalue);
        robot.delay(Constant.SECOND);
    }
    public static void pressShitKeys(int[] ks) {
        for (int k : ks) {
            robot.keyPress(k);
            robot.delay(10);
        }
        robot.delay(500);
        for (int k : ks) {
            robot.keyRelease(k);
            robot.delay(10);
        }
        robot.delay(500);
    }
    public static void delay(){
        robot.delay(Constant.SECOND);
    }
    public static void delay(int ms){
        robot.delay(ms);
    }
    public static void moveClick(int x, int y){
        robot.delay(Constant.SECOND);
        robot.mouseMove(x, y);
        pressMouse(InputEvent.BUTTON1_DOWN_MASK );
    }
    public static void rightClick(){
        robot.delay(10);
        pressMouse(InputEvent.BUTTON3_DOWN_MASK);
    }
}