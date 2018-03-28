/**
 * date:2014-04-21
 * rewrite ToastUtil
 */
package com.ijustyce.fastandroiddev3.baseLib.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ijustyce.fastandroiddev3.IApplication;
import com.ijustyce.fastandroiddev3.R;

import java.util.ArrayList;
import java.util.List;

public class ToastUtil {

    private static List<String> notShowList;

    static {
        notShowList = new ArrayList<>();
    }

    public static void addNotShowWord(String word) {
        if (!notShowList.contains(word)) {
            notShowList.add(word);
        }
    }

    private static boolean shouldShow(String text) {

        if (StringUtils.isEmpty(text) || notShowList.contains(text)) {
            return false;
        }

        return true;
    }

    public static void show(int id) {

        final String text = IApplication.getInstance().getResources().getString(id);
        if (!shouldShow(text)) {
            return;
        }
        try {
            LayoutInflater mInflater = LayoutInflater.from(IApplication.getInstance());
            View toastView = mInflater.inflate(R.layout.fastandroiddev3_toast, null);
            TextView toastText = (TextView) toastView.findViewById(R.id.message);
            toastText.setText(text);
            Toast toastStart = new Toast(IApplication.getInstance());
            toastStart.setGravity(Gravity.CENTER, 0, 0);
            toastStart.setDuration(Toast.LENGTH_SHORT);
            toastStart.setView(toastView);
            toastStart.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void show(String text) {

        if (!shouldShow(text)) {
            return;
        }

        try {
            LayoutInflater mInflater = LayoutInflater.from(IApplication.getInstance());
            View toastView = mInflater.inflate(R.layout.fastandroiddev3_toast, null);
            TextView toastText = (TextView) toastView.findViewById(R.id.message);
            toastText.setText(text);
            Toast toastStart = new Toast(IApplication.getInstance());
            toastStart.setGravity(Gravity.CENTER, 0, 0);
            toastStart.setDuration(Toast.LENGTH_SHORT);
            toastStart.setView(toastView);
            toastStart.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param id
     */
    public static void showTop(int id) {

        String text = IApplication.getInstance().getResources().getString(id);
        if (!shouldShow(text)) {
            return;
        }

        try {
            LayoutInflater mInflater = LayoutInflater.from(IApplication.getInstance());
            View toastTopView = mInflater.inflate(R.layout.fastandroiddev3_toast_top, null);
            TextView toastTopText = (TextView) toastTopView.findViewById(R.id.message);
            toastTopText.setText(text);
            Toast toastStart = new Toast(IApplication.getInstance());
            toastStart.setGravity(Gravity.TOP, 0, 90);
            toastStart.setDuration(Toast.LENGTH_SHORT);
            toastStart.setView(toastTopView);
            toastStart.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showTop(String text) {

        if (!shouldShow(text)) {
            return;
        }
        try {
            LayoutInflater mInflater = LayoutInflater.from(IApplication.getInstance());
            View toastTopView = mInflater.inflate(R.layout.fastandroiddev3_toast_top, null);
            TextView toastTopText = (TextView) toastTopView.findViewById(R.id.message);
            toastTopText.setText(text);
            Toast toastStart = new Toast(IApplication.getInstance());
            toastStart.setGravity(Gravity.TOP, 0, 90);
            toastStart.setDuration(Toast.LENGTH_SHORT);
            toastStart.setView(toastTopView);
            toastStart.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param id
     * @param yOffset dp , height of ToastUnit
     */
    public static void showTop(int id, int yOffset) {

        String text = IApplication.getInstance().getResources().getString(id);
        if (!shouldShow(text)) {
            return;
        }

        try {
            LayoutInflater mInflater = LayoutInflater.from(IApplication.getInstance());
            View toastTopView = mInflater.inflate(R.layout.fastandroiddev3_toast_top, null);
            TextView toastTopText = (TextView) toastTopView.findViewById(R.id.message);
            toastTopText.setText(text);
            Toast toastStart = new Toast(IApplication.getInstance());
            toastStart.setGravity(Gravity.TOP, 0, yOffset);
            toastStart.setDuration(Toast.LENGTH_SHORT);
            toastStart.setView(toastTopView);
            toastStart.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showTop(String text, int yOffset) {
        if (!shouldShow(text)) {
            return;
        }

        try {
            LayoutInflater mInflater = LayoutInflater.from(IApplication.getInstance());
            View toastTopView = mInflater.inflate(R.layout.fastandroiddev3_toast_top, null);
            TextView toastTopText = (TextView) toastTopView.findViewById(R.id.message);
            toastTopText.setText(text);
            Toast toastStart = new Toast(IApplication.getInstance());
            toastStart.setGravity(Gravity.TOP, 0, yOffset);
            toastStart.setDuration(Toast.LENGTH_SHORT);
            toastStart.setView(toastTopView);
            toastStart.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
