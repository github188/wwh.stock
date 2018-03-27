package com.skoo.stock.util.robotutil;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
/**
 * @author bean
 *
 */
public class RobotDemo {

    private Robot robot = null;

    public RobotDemo() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    /** 可以弹出QQ */
    public void keyBoardDemo() {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_Z);
        robot.keyRelease(KeyEvent.VK_Z);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_ALT);
    }
    /** 前提是有个最大化的窗口，功能是移动到标题栏，然后拖拽到600,600的位置*/
    public void mouseDemo(){
        robot.mouseMove(80, 10);
        robot.mousePress(KeyEvent.BUTTON1_MASK);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.mouseMove(600, 600);
        robot.mouseRelease(KeyEvent.BUTTON1_MASK);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        RobotDemo demo=new RobotDemo();
        demo.keyBoardDemo();
        demo.mouseDemo();
    }

}