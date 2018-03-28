package com.ijustyce.fastandroiddev3.baseLib.utils;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.ijustyce.fastandroiddev3.IApplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import okhttp3.ResponseBody;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by yc on 16-2-6. 文件操作类
 */
public class FileUtils {

    /**
     * 递归计算文件夹下面的文件的大小，以M为单位,结果保留两位小数(不四舍五入)
     *
     * @param file 文件夹或者文件名
     * @return 文件大小 以bites 为单位
     */
    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file != null && file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                if (children != null && children.length > 0) {
                    for (File f : children)
                        size += getDirSize(f);
                }
                return CommonTool.getShortDouble(size);
            } else {//如果是文件则直接返回其大小,以“bites”为单位
                return CommonTool.getShortDouble(file.length());
            }
        } else {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }

    /**
     * 返回不存在的文件，一般用于文件下载
     * @param baseName  文件名称，带扩展，比如  xxx.apk 或者  xxx.png
     * @param basePath  可能需要的二级目录，随意即可，也可以不传
     * @return  一个不存在的文件
     */
    public static File getUnExistsFile(String baseName, String basePath) {
        String availablePath = getAvailablePath(basePath == null ? "fastandroiddev" : basePath);
        File tmp = new File(availablePath + "/" + baseName);
        return renameIfExists(tmp);
    }

    /**
     * 获取可用的 文件目录，先尝试sdcard、然后尝试内部存储空间，最后则是data目录了，如果是sdcard，则会创建name这个文件夹
     *
     * @param name    文件夹名字,如果sdcard可用，会在sdcard创建这个目录
     * @return 成功返回true，失败返回false
     */
    public static String getAvailablePath(String name) {

        if (name == null || IApplication.getInstance() == null) return null;
        File f = IApplication.getInstance().getExternalFilesDir(null);
        if (f == null) {
            f = Environment.getExternalStorageDirectory();
            if (f == null) {
                f = IApplication.getInstance().getFilesDir();
            } else {
                f = new File(f.getAbsolutePath() + "/" + name + "/");
                f.mkdir();
            }
        }
        return f == null ? null : f.getAbsolutePath();
    }

    /**
     * 复制文件
     *
     * @param oldPath 要复制的文件路径
     * @param newPath 目标文件路径
     * @return 成功返回true，失败返回false
     */
    public static boolean copyFile(String oldPath, String newPath) {

        if (oldPath == null || newPath == null) return false;
        try {
            int bytesum = 0;
            int byteread;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                fs.close();
                inStream.close();
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }

    /**
     * 复制 assets 下的文件到 toPath 目录
     *
     * @param context  mContext
     * @param toPath   目标文件路径, 如果存在，将会被覆盖
     * @param fileName assets 下的文件名称
     * @return 成功返回true，失败返回false
     */
    public static boolean copyAssets(Context context, String toPath, String fileName) {

        if (context == null || toPath == null || fileName == null) return false;
        try {
            File toFile = new File(toPath);
            if (toFile.exists()) {
                toFile.delete();
            }
            InputStream myInput;
            OutputStream myOutput;
            myOutput = new FileOutputStream(toPath);
            myInput = context.getAssets().open(fileName);
            byte[] buffer = new byte[1024];
            int length = myInput.read(buffer);
            while (length > 0) {
                myOutput.write(buffer, 0, length);
                length = myInput.read(buffer);
            }
            myOutput.flush();
            myInput.close();
            myOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 保存bitmap 为 jpg文件
     *
     * @param mBitmap bitmap
     * @param bitName 文件路径 , 必须 .jpg 结尾 比如 /sdcard/tmp/1.jpg
     */
    public static boolean savBitmapToPng(Bitmap mBitmap, String bitName) {

        if (mBitmap == null || bitName == null) return false;

        File f = new File(bitName);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 保存 bitmap 为 .jpg 文件
     *
     * @param mBitmap bitmap
     * @param bitName 文件路径 , 必须以 .jpg 结尾 比如 /sdcard/tmp/1.jpg
     */
    public static boolean savBitmapToJpg(Bitmap mBitmap, String bitName) {

        if (mBitmap == null || bitName == null) return false;

        File f = new File(bitName);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读取文件
     *
     * @param file 文本文件
     * @return 文件内容 发生异常时，返回null
     */
    public static String readTextFile(File file) {

        if (file == null || !file.exists()) return null;

        String text = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            text = readTextInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    /**
     * 从流中读取文件
     *
     * @param is 流对象
     * @return 文件内容
     */
    public static String readTextInputStream(InputStream is) {

        if (is == null) return null;
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 将文本内容写入文件
     *
     * @param file 要写入的文件
     * @param str  要写入的内容
     */
    public static void writeTextFile(File file, String str) {

        if (file == null || str == null) return;
        DataOutputStream out;
        try {
            out = new DataOutputStream(new FileOutputStream(file));
            out.write(str.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历删除文件 如果是目录，则删除目录下的一切内容，目录不会删除，如果是文件，则删除文件
     */
    public static void deleteFile(String path) {

        ILog.i("===path===", "path is " + path);
        if (StringUtils.isEmpty(path)) {
            ILog.e("===path is null , return ...===");
            return;
        }

        File file = new File(path);
        if (!file.exists()) {
            ILog.e("===file not exists , return ...===");
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        File[] list = file.isDirectory() ? file.listFiles() : null;
        if (list != null && list.length > 0) {
            for (File delete : list) {
                deleteFile(delete.getAbsolutePath());
            }
        }
    }

    /**
     * 返回选择文件时 选定的文件路径
     *
     * @param context context
     * @param uri     onActivityResult里 Intent 对象 getData
     * @return 如果存在，返回路径，否则返回null
     */
    public static String getPath(Context context, Uri uri) {
        if (context == null || uri == null) {
            return null;
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { MediaStore.Images.Media.DATA };
            CursorLoader loader = new CursorLoader(context, uri, projection, null, null, null);
            Cursor cursor = loader.loadInBackground();
            if (cursor == null) return null;
            try {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    String result = cursor.getString(column_index);
                    cursor.close();
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean writeInputStream(InputStream inputStream, File file){
        OutputStream outputStream = null;

        try {
            byte[] fileReader = new byte[4096];

//            long fileSize = body.contentLength();
//            long fileSizeDownloaded = 0;

            outputStream = new FileOutputStream(file);

            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, read);
                //    fileSizeDownloaded += read;
            }
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {

            }
        }
    }

    public static void setupApk(File file, Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Intent chooser = Intent.createChooser(intent, "请选择您需要打开的软件！");
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(chooser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFileName(String fullName){
        if (StringUtils.isEmpty(fullName)) return null;
        int index = fullName.lastIndexOf(".");
        if (index < 0) return null;
        return fullName.substring(0, index);
    }

    public static String getFileExtraName(String fullName) {
        return fullName.substring(fullName.lastIndexOf(".")).split("\\?")[0];
    }

    private static File autoRenameFile(File file) {
        String fileName = getFileName(file.getName());
        String extraName = getFileExtraName(file.getName());
        int firstIndex = fileName.lastIndexOf("(");
        int lastIndex = fileName.lastIndexOf(")");
        int value = 1;
        if (firstIndex != -1 && lastIndex != -1 && lastIndex > firstIndex + 1) {
            value = StringUtils.getInt(fileName.substring(firstIndex + 1, lastIndex)) + 1;
            fileName = fileName.substring(0, firstIndex);
        }
        return new File(file.getParent() + "/" + fileName + "(" + value + ")" + extraName);
    }

    /**
     * 重命名文件，如果文件已经存在，一般用于下载，不过推荐使用 getUnExistsFile
     * @param file  一个文件
     * @return  返回一个不存在的文件
     */
    public static File renameIfExists(File file) {
        if (file.exists()) {
            file = renameIfExists(autoRenameFile(file));
        }
        return file;
    }

    private static final String[][] FileTypeMap = {
            //{后缀名，MIME类型}
            {".3gp",    "video/3gpp"},
            {".apk",    "application/vnd.android.package-archive"},
            {".asf",    "video/x-ms-asf"},
            {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"},
            {".bmp",    "image/bmp"},
            {".c",  "text/plain"},
            {".class",  "application/octet-stream"},
            {".conf",   "text/plain"},
            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls",    "application/vnd.ms-excel"},
            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"},
            {".gtar",   "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h",  "text/plain"},
            {".htm",    "text/html"},
            {".html",   "text/html"},
            {".jar",    "application/java-archive"},
            {".java",   "text/plain"},
            {".jpeg",   "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log",    "text/plain"},
            {".m3u",    "audio/x-mpegurl"},
            {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"},
            {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"},
            {".m4v",    "video/x-m4v"},
            {".mov",    "video/quicktime"},
            {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"},
            {".mp4",    "video/mp4"},
            {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"},
            {".mpeg",   "video/mpeg"},
            {".mpg",    "video/mpeg"},
            {".mpg4",   "video/mp4"},
            {".mpga",   "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"},
            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop",   "text/plain"},
            {".rc", "text/plain"},
            {".rmvb",   "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"},
            {".sh", "text/plain"},
            {".tar",    "application/x-tar"},
            {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"},
            {".wav",    "audio/x-wav"},
            {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            {".xml",    "text/plain"},
            {".z",  "application/x-compress"},
            {".zip",    "application/x-zip-compressed"},
            {"",        "*/*"}
    };

    public static String getFileType(File file) {
        String type="*/*";
        String fName = file.getName();
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
        String end = fName.substring(dotIndex,fName.length()).toLowerCase();
        if(StringUtils.isEmpty(end))return type;
        for (String[] tmp : FileTypeMap){
            if(end.equals(tmp[0])) {
                type = tmp[1];
            }
        }
        return type;
    }

    public static boolean writeResponseBodyToFile(ResponseBody body, File file) {
        return writeInputStream(body.byteStream(), file);
    }

    /**
     * 调用文件管理器显示某个文件
     *
     * @param path    文件路径
     * @param context Context
     * @return 如果参数有误或者文件不存在，返回0，如果没有文件管理器返回-1，如果成功返回1
     */
    public static int showFile(String path, Context context) {

        if (path == null || context == null || !new File(path).exists()) {

            return 0;
        }

        Uri selectedUri = Uri.parse(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedUri, "resource/folder");

        if (intent.resolveActivityInfo(context.getPackageManager(), 0) != null) {
            Intent chooser = Intent.createChooser(intent, "请选择您需要打开的软件！");
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(chooser);
            return 1;
        }
        return -1;
    }
}