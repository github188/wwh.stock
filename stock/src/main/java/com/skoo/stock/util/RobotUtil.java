package com.skoo.stock.util;

import com.skoo.stock.util.robotutil.RobotManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotUtil {
    public static void main(String[] args) throws Exception {
        RobotManager robot = new RobotManager();
        /*robot.delay(10000);
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();
        System.out.println(mousePoint.x);
        System.out.println(mousePoint.y);
        robot.moveClick(715, 440);*/
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
}