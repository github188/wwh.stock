package com.ijustyce.fastandroiddev3.baseLib.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ijustyce.fastandroiddev3.IApplication;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by yc on 16-2-6. 通用工具类
 */
public class CommonTool {

    //  使状态栏透明，用图片作为其背景的时候用
    public static void setTranslucent(Activity activity) {
        if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    //  对Android 6.0 即以上设置状态栏icon为深色
    public static void darkStatusIcon(Activity activity) {
        if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 对double 类型数据进行四舍五入，并保留两位小数
     */
    public static double getShortDouble(double value) {

        return Math.round(value * 100) / 100.0;
    }

    public static String getMetaData(Context context, String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        Object res = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager
                        .getApplicationInfo(context.getPackageName(),
                                PackageManager.GET_META_DATA);
                if (applicationInfo != null && applicationInfo.metaData != null) {
                    res = applicationInfo.metaData.get(key);
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // 有可能是String, 有可能是Integer
        return String.valueOf(res);
    }

    /**
     * 判断是否是wifi
     *
     * @return 如果是wifi 则返回true，否则返回false
     */

    public static boolean isWifi(Context context) {

        if (context == null) return false;
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectMgr == null) return false;
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取android当前可用内存大小,以M为单位
     */
    public static int getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        int avail = (int) mi.availMem / (1024 * 1024);
        ILog.i("===CommonTool===", "getAvailMemory " + avail);
        return avail;
    }

    /**
     * 获取总内存大小，以M为单位
     *
     * @return 总内存大小M
     */
    public static int getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        int initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            if (StringUtils.isEmpty(str2)) return 0;
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                ILog.i(str2, num + "\t");
            }
            initial_memory = Integer.valueOf(arrayOfString[1]);// 获得系统总内存，单位是KB
            localBufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return initial_memory / 1024;
    }

    /**
     * play notification sound
     *
     * @param context mContext
     * @return soundId
     */
    public static int playNotifi(Context context) {

        if (context == null) return -1;
        NotificationManager mgr = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification nt = new Notification();
        nt.defaults = Notification.DEFAULT_SOUND;
        int soundId = new Random(System.currentTimeMillis())
                .nextInt(Integer.MAX_VALUE);
        mgr.notify(soundId, nt);
        return soundId;
    }

    public interface PlayFinish {

        public void onFinish();
    }

    /**
     * return VersionName
     *
     * @param context Activity.this
     * @return versionName
     */
    public static String getVersionName(Context context) {

        if (context == null) return null;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * return packageName
     *
     * @param context Activity.this
     * @return packageName
     */
    public static String getPkgName(Context context) {

        if (context == null) return null;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.applicationInfo.loadLabel(
                    context.getPackageManager()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * return VersionCode
     *
     * @param context Activity.this
     * @return versionCode
     */
    public static int getVersionCode(Context context) {

        if (context == null) return -1;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param context Activity.this
     * @return pkgName+" "+versionName , if fail return ""
     */
    public static String getVersion(Context context) {

        return getPkgName(context) + getVersionName(context);
    }

    public static void setText(TextView view, String text) {
        if (view != null) view.setText(text);
    }

    public static void setVisibleOrGone(View view, boolean visible) {
        if (view != null) view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public static void setVisibleOrInVisible(View view, boolean visible) {
        if (view != null) view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    public static void setEditAble(boolean editAble, EditText editText) {

        if (editText == null) {
            return;
        }
        editText.setFocusableInTouchMode(editAble);
        editText.setLongClickable(editAble);
        if (editAble) {
            editText.requestFocus();
        } else {
            editText.clearFocus();
        }
    }

    public static void copyToClipboard(String text) {
        ClipboardManager manager = (ClipboardManager) IApplication.getInstance()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setText(text);
    }

    /**
     * play incoming call sound
     *
     * @param context mContext
     * @return MediaPlayer , by it you call stop current play
     */
    public static MediaPlayer playSound(Context context) {

        if (context == null) return null;

        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        MediaPlayer mMediaPlayer = new MediaPlayer();

        try {
            mMediaPlayer.setDataSource(context, alert);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
        mMediaPlayer.setLooping(true);
        try {
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
        return mMediaPlayer;
    }

    /**
     * play out call sound
     *
     * @param context mContext
     * @param id      id of sound , usually in raw path
     * @return MediaPlayer , by it you call stop current play
     */
    public static MediaPlayer playSound(Context context, int id) {

        if (context == null) return null;

        MediaPlayer mMediaPlayer = MediaPlayer.create(context, id);

        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        return mMediaPlayer;
    }

    private static MediaPlayer mPlayer;

    /**
     * play voice , use to voice chat
     *
     * @param file       file
     * @param playFinish PlayFinish listener , 可以为空
     */
    public static MediaPlayer startPlaying(String file, PlayFinish playFinish) {

        if (StringUtils.isEmpty(file) || !new File(file).exists()) return null;

        final WeakReference<PlayFinish> playListener = new WeakReference<>(playFinish);
        try {
            if (mPlayer != null) {
                stopPlaying();
            }
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(file);
            mPlayer.prepare();
            mPlayer.start();
            ILog.i("===file===", file);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                    ILog.i("===Common Tools play finish===");
                    if (playListener.get() != null) {
                        playListener.get().onFinish();
                    } else {
                        ILog.e("===playListener is null, return===");
                    }
                    stopPlaying();
                }
            });
        } catch (IOException e) {
            stopPlaying();
        }
        return mPlayer;
    }

    public static String getSignInfo(Context context) {
        if (context == null) return null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            Signature sign = info.signatures[0];
            ILog.i("test", "hashCode : " + sign.hashCode());
            return sign.toCharsString();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取amr 文件的播放长度，返回秒 4舍5入
     *
     * @param file file
     */
    public static int getAmrTime(String file) {

        int time = -1;
        try {
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(file);
            mPlayer.prepare();
            time = mPlayer.getDuration();
            stopPlaying();
        } catch (IOException e) {
            stopPlaying();
        }
        return time / 1000 + (time % 1000 > 500 ? 1 : 0);
    }

    /**
     * stop play voice
     */
    public static void stopPlaying() {
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            } catch (Exception ignore) {

            }
        }
    }

    /**
     * is phone connect to network
     *
     * @param context mContext
     * @return true if connect or return false
     */
    public static boolean isConnected(Context context) {

        if (context == null) return false;
        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conManager == null) return false;
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }

    public static String getString(Context context, int id) {
        if (context == null) return null;
        return context.getResources().getString(id);
    }

    public static String getUUID(Context context) {

        if (context == null) return null;
        String res;
        TelephonyManager manage = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            res = manage.getDeviceId();
            if (res != null && res.length() > 10) {
                return res;
            }
        } catch (Exception e) {
            ILog.e("error", e.getMessage());
        }
        res = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (res == null || res.length() < 5 || "9774d56d682e549c".equals(res)) {
            res = UUID.randomUUID().toString();
        }
        ;
        return res;
    }

    /**
     * hide soft keyboard
     *
     * @param context Activity
     */
    public static boolean closeIme(Activity context) {

        if (context == null) return false;
        View view = context.getWindow().peekDecorView();
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (view != null) {
            InputMethodManager inputManger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            return inputManger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return false;
    }

    /**
     * @param drawable drawable
     * @return bitmap bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 显示或隐藏密码
     *
     * @param showPw true 则显示，否则隐藏
     * @param view   若为空，则直接return
     */
    public static void showPw(boolean showPw, TextView view) {

        if (view == null) {
            ILog.e("===CommonTool===", "showPw view can not be null ...");
            return;
        }
        if (showPw) {
            //如果选中，显示密码
            view.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //否则隐藏密码
            view.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return bitmap
     */
    public static Bitmap bitmapToRound(Bitmap bitmap) {

        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * bitmap中的透明色用白色替换
     *
     * @param bitmap
     * @return
     */
    public static Bitmap changeColor(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] colorArray = new int[w * h];
        int n = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int color = getMixtureWhite(bitmap.getPixel(j, i));
                colorArray[n++] = color;
            }
        }
        return Bitmap.createBitmap(colorArray, w, h, Bitmap.Config.ARGB_8888);
    }

    /**
     * 获取和白色混合颜色
     *
     * @return
     */
    private static int getMixtureWhite(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.rgb(getSingleMixtureWhite(red, alpha), getSingleMixtureWhite
                        (green, alpha),
                getSingleMixtureWhite(blue, alpha));
    }

    /**
     * 获取单色的混合值
     *
     * @param color
     * @param alpha
     * @return
     */
    private static int getSingleMixtureWhite(int color, int alpha) {
        int newColor = color * alpha / 255 + 255 - alpha;
        return newColor > 255 ? 255 : newColor;
    }

    /**
     * 根据路径获得并压缩返回bitmap用于显示
     *
     * @param filePath
     * @return
     */
    @SuppressWarnings("static-access")
    public static Bitmap compressImageFromFile(String filePath) {

        if (StringUtils.isEmpty(filePath) || !new File(filePath).exists()) return null;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * @param filePath
     * @return
     */
    public static Bitmap compressImageFromFile(String filePath, int angle) {

        if (StringUtils.isEmpty(filePath) || !new File(filePath).exists()) return null;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        if (angle != 0) {
            // 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(angle); // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
        }
        return bitmap;
    }

    public static String getRandom(int length) {

        String res = "";
        for (int i = 0; i < length; i++) {
            Random random = new Random();
            res += random.nextInt(10);
        }
        return res;
    }

    /**
     * 获取bitmap的大小，返回kb
     *
     * @param bitmap
     * @return
     */
    public static long getBitmapsize(Bitmap bitmap) {

        if (bitmap == null) return 0;
        long size;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            size = bitmap.getByteCount() / 1024;
        } else {
            size = bitmap.getRowBytes() * bitmap.getHeight() / 1024;
        }
        ILog.i("===bitmap size is " + size + " ===");
        return size;
    }

    /**
     * 压缩图片，如果其大小小于notComPressSize kb 则不压缩
     *
     * @param image
     * @param notComPressSize
     * @return
     */
    public static Bitmap compressImage(Bitmap image, long notComPressSize) {

        if (image == null) return null;
        if (getBitmapsize(image) > notComPressSize) {

            compressImage(image);
        }
        return image;
    }

    /**
     * compress image
     *
     * @param image image
     * @return bitmap
     */
    public static Bitmap compressImage(Bitmap image) {

        if (image == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        int options = 100;
        int size;
        do {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            size = baos.toByteArray().length / 1024;
            ILog.i("===size===" + size);
            options -= 10;
        } while (size > 60 && options > 0);  //  60 almost 600kb
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
    }

    /**
     * get screen width
     *
     * @param context context
     * @return
     */
    public static int getScreenWidth(Context context) {

        if (context == null) return 0;
        WindowManager vm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return vm.getDefaultDisplay().getWidth();
    }

    /**
     * get screen height
     *
     * @param context context
     * @return
     */
    public static int getScreenHeight(Context context) {

        if (context == null) return 0;
        WindowManager vm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return vm.getDefaultDisplay().getHeight();
    }

    public static String ACTION_NOTIFY_DELETE = "fastandroiddev_action_notify_delete";
    public static String NOTIFY_ID = "fastandroiddev_notify_id";

    public static int showNotify(String title, String msg, Intent intent,
                                 Context context, int resSmallIcon) {
        return showNotify(title, msg, intent, context, resSmallIcon, -1, true);
    }

    public static int showNotify(String title, String msg, Intent intent,
                                 Context context, int resSmallIcon, boolean autoCancel) {
        return showNotify(title, msg, intent, context, resSmallIcon, -1, autoCancel);
    }

    public static int showNotify(String title, String msg, Intent intent,
                                 Context context, int resSmallIcon, int largeIcon) {
        return showNotify(title, msg, intent, context, resSmallIcon, largeIcon, true);
    }

    /**
     * ==============显示一个通知，并注册清除事件=======================
     **/
    public static int showNotify(String title, String msg, Intent intent,
                                 Context context, int resSmallIcon, int largeIcon, boolean autoCancel) {

        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(msg)) return -1;

        int id = (int) System.currentTimeMillis();
        if (intent == null) intent = new Intent();
        intent.putExtra(NOTIFY_ID, id);
        PendingIntent deleteIntent = PendingIntent.getBroadcast(context, id,
                new Intent(ACTION_NOTIFY_DELETE).putExtra(NOTIFY_ID, id), 0);
        PendingIntent pi = PendingIntent.getActivity(context, id, intent, 0);
        NotificationCompat.Builder builder  = new NotificationCompat.Builder(context)
                .setTicker(title)
                .setSmallIcon(resSmallIcon)
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(pi)
                .setDeleteIntent(deleteIntent)
                .setAutoCancel(autoCancel)
                .setDefaults(Notification.DEFAULT_SOUND); //设置声音，此为默认声音
                //    .setVibrate(vT) //设置震动，此震动数组为：long vT[]={300,100,300,100};
                //.setLights(argb, onMs, offMs)
                //   .setOngoing(true)      //true，用户不能手动清除
                //  .setNumber(3)         //设置信息条数
                //  .setContentInfo("3")      //作用同上，设置信息的条数
                //  .setLargeIcon(resSmallIcon)
        if (largeIcon != -1) builder.setLargeIcon(BitmapFactory.decodeResource
                (IApplication.getInstance().getResources(), largeIcon));
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
        return id;
    }

    @TargetApi(19)
    public static boolean isNotificationEnabled(Context context) {

        if (Build.VERSION.SDK_INT < 19) return true;
        try {
            final String CHECK_OP_NO_THROW = "checkOpNoThrow";
            final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
            AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;
            Class appOpsClass = null; /* Context.APP_OPS_MANAGER */
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (int) opPostNotificationValue.get(Integer.class);
            return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getText(TextView view) {

        return view == null ? null : view.getText().toString();
    }

    //  隐式启动intent
    public static boolean sendEmail(String email, String title, String content, Context mContext) {

        Intent mIntent = new Intent(Intent.ACTION_SENDTO);
        mIntent.setData(Uri.parse("mailto:" + email));
        if (!StringUtils.isEmpty(title)) {
            mIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        }
        if (!StringUtils.isEmpty(content)) {
            mIntent.putExtra(Intent.EXTRA_TEXT, content);
        }
        if (mIntent.resolveActivity(mContext.getPackageManager()) == null) return false;
        try {
            Intent chooser = Intent.createChooser(mIntent, "请选择您需要打开的软件！");
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(chooser);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean viewInMap(double latitude, double longitude, String addressName, Context mContext) {

        Uri mUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + addressName);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
        if (mIntent.resolveActivity(mContext.getPackageManager()) == null) return false;
        try {
            Intent chooser = Intent.createChooser(mIntent, "请选择您需要打开的软件！");
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(chooser);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean openUrl(String url, Activity mContext) {

        if (mContext == null || !RegularUtils.isCommonUrl(url)) return false;

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        if (intent.resolveActivity(mContext.getPackageManager()) == null) return false;
        try {
            Intent chooser = Intent.createChooser(intent, "请选择您需要打开的软件！");
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(chooser);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean chooseFile(Activity mContext, String type, int requestCode) {

        if (mContext == null) return false;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type == null ? "*/*" : type + "/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (intent.resolveActivity(mContext.getPackageManager()) == null) {
            return false;
        }
        try {
            Intent chooserIntent = Intent.createChooser(intent, "请选择文件");
            mContext.startActivityForResult(chooserIntent, requestCode);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * open system share dialog
     *
     * @param mContext mContext
     * @param text    text to share
     * @return true if success or false
     */
    public static boolean systemShare(Context mContext, String text) {

        if (mContext == null || StringUtils.isEmpty(text)) return false;
        Intent intent = new Intent(Intent.ACTION_SEND);
        //  intent.putExtra(Intent.EXTRA_SUBJECT, text);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        if (intent.resolveActivity(mContext.getPackageManager()) == null) return false;
        try {
            Intent chooser = Intent.createChooser(intent, "请选择您需要打开的软件！");
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(chooser);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * open system share dialog
     *
     * @param mContext  mContext
     * @param text     text to share
     * @param filePath file to share, usually is picture
     */
    public static boolean systemShare(Context mContext, String text,
                                      String filePath) {

        if (mContext == null || (StringUtils.isEmpty(text) && StringUtils.isEmpty(filePath))) return false;
        File f = new File(filePath);
        if (!f.exists()) return false;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
//		intent.putExtra(Intent.EXTRA_SUBJECT, text);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
        if (intent.resolveActivity(mContext.getPackageManager()) == null) return false;
        try {
            Intent chooser = Intent.createChooser(intent, "请选择您需要打开的软件！");
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(chooser);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 直接呼叫指定的号码
     *
     * @param mContext    上下文Context
     * @param phoneNumber 需要呼叫的手机号码
     */
    public static boolean callPhone(Context mContext, String phoneNumber) {

        if (mContext == null || StringUtils.isEmpty(phoneNumber)) return false;
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent call = new Intent(Intent.ACTION_CALL, uri);
        if (call.resolveActivity(mContext.getPackageManager()) == null) {
            return toCallPhoneActivity(mContext, phoneNumber);
        }
        try {
            Intent chooser = Intent.createChooser(call, "请选择您需要打开的软件！");
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(chooser);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 跳转至拨号界面
     *
     * @param mContext    上下文Context
     * @param phoneNumber 需要呼叫的手机号码
     */
    public static boolean toCallPhoneActivity(Context mContext, String phoneNumber) {

        if (mContext == null || StringUtils.isEmpty(phoneNumber)) return false;
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent call = new Intent(Intent.ACTION_DIAL, uri);
        if (call.resolveActivity(mContext.getPackageManager()) == null) {
            return false;
        }
        try {
            Intent chooser = Intent.createChooser(call, "请选择您需要打开的软件！");
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(chooser);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 跳转至发送短信界面(自动设置接收方的号码以及短信内容)
     *
     * @param mContext context
     * @param strPhone 手机号码
     * @param strMsg   短信内容
     */
    public static boolean toSendMessageActivity(Context mContext, String strPhone, String strMsg) {

        if (mContext == null || StringUtils.isEmpty(strPhone) || StringUtils.isEmpty(strMsg))
            return false;
        if (PhoneNumberUtils.isGlobalPhoneNumber(strPhone)) {
            Uri uri = Uri.parse("smsto:" + strPhone);
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
            if (!StringUtils.isEmpty(strMsg)) {
                sendIntent.putExtra("sms_body", strMsg);
            }
            if (sendIntent.resolveActivity(mContext.getPackageManager()) == null) {
                return false;
            }
            try {
                Intent chooser = Intent.createChooser(sendIntent, "请选择您需要打开的软件！");
                chooser.addFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(chooser);
            }catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @TargetApi(19)
    public static boolean setNotify(Context context) {
        if (context == null || Build.VERSION.SDK_INT < 19) return false;

        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        intent.putExtra("app_package", context.getPackageName());
        intent.putExtra("app_uid", context.getApplicationInfo().uid);
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return viewCurrentAppDetail(context);
        }
        try {
            Intent chooser = Intent.createChooser(intent, "请选择您需要打开的软件！");
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(chooser);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean viewUrl(Context context, Uri uri){
        if (context == null) return false;
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }
        try {
            Intent chooser = Intent.createChooser(intent, "请选择您需要打开的软件！");
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(chooser);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean viewCurrentAppDetail(Context context) {
        if (context == null) return false;

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }
        try {
            Intent chooser = Intent.createChooser(intent, "请选择您需要打开的软件！");
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(chooser);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}