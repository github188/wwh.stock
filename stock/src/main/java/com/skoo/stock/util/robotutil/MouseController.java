package com.skoo.stock.util.robotutil;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.util.Random;

/**
 *
 * @author Xiaofeng Wang
 */
public class MouseController implements Runnable {
    private Dimension dim;
    private Random rand;
    private Robot robot;
    private volatile boolean stop = false;
    /** Creates a new instance of Main */
    public MouseController() {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        rand = new Random();
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        while(!stop) {
            int x = rand.nextInt(dim.width);
            int y = rand.nextInt(dim.height);
            robot.mouseMove(x, y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    public synchronized void stop() {
        stop = true;
    }
    /** * @param args the command line arguments */
    public static void main(String[] args) {
        MouseController mc = new MouseController();
        Thread mcThread = new Thread(mc);
        System.out.println("Mouse Controller start");
        mcThread.start();
        try {
            Thread.sleep(60000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        mc.stop();
        System.out.println("Mouse Controller stoped");
    }
}