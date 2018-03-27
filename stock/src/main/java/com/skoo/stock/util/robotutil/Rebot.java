package com.skoo.stock.util.robotutil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Rebot extends Robot {
    String s3="";
    public Rebot() throws AWTException {
        super();
    }

    public void YiDong(int a, int b) {
        this.mouseMove(a, b);
    }

    public void ZanTing(int time) {
        try {
            new Thread().sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void DianJi() {
        this.mousePress(MouseEvent.BUTTON1_MASK);
        this.mouseRelease(MouseEvent.BUTTON1_MASK);
    }
    public void HuiChe(){
        this.keyPress(KeyEvent.VK_ENTER);
        this.keyRelease(KeyEvent.VK_ENTER);
    }
}