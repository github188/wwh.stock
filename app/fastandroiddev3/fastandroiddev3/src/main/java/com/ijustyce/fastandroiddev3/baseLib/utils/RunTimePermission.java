package com.ijustyce.fastandroiddev3.baseLib.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by yangchun on 16/8/22.
 */

public class RunTimePermission {

    private static final int RECORD_PERMISSION_REQUEST_CODE = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 3;
    private static final int CALL_PERMISSION_REQUEST_CODE = 4;
    private static final int CONTACTS_PERMISSION_REQUEST_CODE = 5;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 5;
    private static final int DIY_PERMISSION_REQUEST_CODE = 6;
    private Activity activity;

    public RunTimePermission(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermissionForRecord() {
        if (activity == null) return false;
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForExternalStorage() {
        if (activity == null) return false;
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForCamera() {
        if (activity == null) return false;
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForCall() {
        if (activity == null) return false;
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForContacts() {
        if (activity == null) return false;
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForLocation() {
        if (activity == null) return false;
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int result2 = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED && result == result2;
    }

    public void requestPermissionForRecord() {
        if (activity == null) return;
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
                ToastUtil.show("请允许录音");
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_PERMISSION_REQUEST_CODE);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestPermissions(String[] permissions) {
        if (activity == null || permissions == null || permissions.length < 1) return;
        try{
            ActivityCompat.requestPermissions(activity, permissions, DIY_PERMISSION_REQUEST_CODE);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestPermissionForExternalStorage() {
        if (activity == null) return;
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ToastUtil.show("请允许写入文件");
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestPermissionForCamera() {
        if (activity == null) return;
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                ToastUtil.show("请允许拍照");
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestPermissionForCall() {
        if (activity == null) return;
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {
                ToastUtil.show("请允许拨打电话");
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestPermissionForContacts() {
        if (activity == null) return;
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)) {
                ToastUtil.show("请允许读取通讯录");
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS_PERMISSION_REQUEST_CODE);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestPermissionForLocation() {
        if (activity == null) return;
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ToastUtil.show("请允许定位");
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
