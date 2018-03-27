package com.skoo.stock.util.robotutil;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
public class RobotExp {
    private static void pressMouse(Robot robot, int m){
        robot.mousePress(m);
        //robot.delay(10);
        robot.mouseRelease(m);
        //robot.delay(500);
    }
    //键盘输入
    private static void pressKeys(Robot robot, int[] ks){
        for(int i=0; i<ks.length; i++){
            robot.keyPress(ks[i]);
            robot.delay(1000);
            robot.keyRelease(ks[i]);
            //robot.delay(1000);
        }
    }
    public static void pressKey(Robot robot, int keyvalue) {
        robot.keyPress(keyvalue);
        robot.keyRelease(keyvalue);
    }
    public static void pressKeyWithShift(Robot robot, int keyvalue) {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(keyvalue);
        robot.keyRelease(keyvalue);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }
    public static void closeApplication(Robot robot) {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_F4);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_F4);
//for linux.
// robot.keyPress(KeyEvent.VK_ALT);
// robot.keyPress(KeyEvent.VK_W);
// robot.keyRelease(KeyEvent.VK_ALT);
// robot.keyRelease(KeyEvent.VK_W);
        robot.keyPress(KeyEvent.VK_N);
        robot.keyRelease(KeyEvent.VK_N);
    }
    public static void main(String[] args) throws IOException {
        try {
            Robot robot = new Robot();
            Runtime.getRuntime().exec("E:\\zszq\\zd_zszq\\TdxW.exe");
// For linux.
//Runtime.getRuntime().exec("gedit");
//定义5秒的延迟以便你打开notepad 哈哈
// Robot 开始写
            robot.delay(1000);
            robot.mouseMove(675, 250);
            robot.delay(500);
            pressMouse(robot, InputEvent.BUTTON1_MASK);
            pressKey(robot, KeyEvent.VK_ENTER);
            robot.delay(30000);
            /*pressKey(robot, KeyEvent.VK_1);
            robot.delay(1000);
            pressKey(robot, KeyEvent.VK_ENTER);
            robot.delay(10000);*/
            pressKeys(robot, new int[]{KeyEvent.VK_7, KeyEvent.VK_ENTER});
            robot.delay(1000);
            pressKeys(robot, new int[]{KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_ENTER});
            robot.delay(1000);
            robot.mouseMove(588, 390);
            pressMouse(robot, InputEvent.BUTTON1_MASK);
            pressKey(robot, KeyEvent.VK_ENTER);
            /*Point mousePoint = MouseInfo.getPointerInfo().getLocation();
            System.out.println(mousePoint.x);
            System.out.println(mousePoint.y);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            System.out.println(dim.width);
            System.out.println(dim.height);*/
            /*robot.delay(60000);
            pressKey(robot, KeyEvent.VK_ENTER);
            robot.delay(10000);*/
            /*for (int i = 0; i < 100; i++) {
                pressKeyWithShift(robot, KeyEvent.VK_H);
                pressKey(robot, KeyEvent.VK_I);
                pressKey(robot, KeyEvent.VK_SPACE);
//pressKeyWithShift(robot, KeyEvent.VK_H);
                pressKeyWithShift(robot, KeyEvent.VK_I);
                pressKey(robot, KeyEvent.VK_SPACE);
                pressKey(robot, KeyEvent.VK_A);
                pressKey(robot, KeyEvent.VK_M);
                pressKey(robot, KeyEvent.VK_SPACE);
                pressKey(robot, KeyEvent.VK_T);
                pressKey(robot, KeyEvent.VK_H);
                pressKey(robot, KeyEvent.VK_E);
                pressKey(robot, KeyEvent.VK_SPACE);
                pressKey(robot, KeyEvent.VK_J);
                pressKey(robot, KeyEvent.VK_A);
                pressKey(robot, KeyEvent.VK_V);
                pressKey(robot, KeyEvent.VK_A);
                pressKey(robot, KeyEvent.VK_SPACE);
                pressKey(robot, KeyEvent.VK_R);
                pressKey(robot, KeyEvent.VK_O);
                pressKey(robot, KeyEvent.VK_B);
                pressKey(robot, KeyE
                vent.VK_O);
                pressKey(robot, KeyEvent.VK_T);
// VK_ENTER
                pressKey(robot, KeyEvent.VK_ENTER);
//pressKey(robot, KeyEvent.);
            }*/
            //closeApplication(robot);
//robot.keyPress(KeyEvent.VK_SPACE);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        //System.exit(0);
    }
}
