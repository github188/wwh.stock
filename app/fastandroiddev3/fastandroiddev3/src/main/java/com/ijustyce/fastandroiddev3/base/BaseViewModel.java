package com.ijustyce.fastandroiddev3.base;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ijustyce.fastandroiddev3.IApplication;
import com.ijustyce.fastandroiddev3.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev3.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev3.glide.ImageLoader;

/**
 * Created by yangchun on 2016/11/12.
 */

public class BaseViewModel extends BaseObservable {

    public int position;

    public static double scaleWidth = -1;
    public static double scaleHeight = -1;
    private static boolean useAutoLayout = true;

    private static int MIN_HEIGHT = 1;
    private static int MIN_WIDTH = 1;

    /**
     * 加载图片
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        ImageLoader.load(view, imageUrl);
    }

    /**
     * 加载图片，指定宽高
     */
    @BindingAdapter({"imageUrl", "width", "height"})
    public static void loadImageWithSize(ImageView view, String imageUrl, int width, int height){
        ImageLoader.load(view, imageUrl, width, height);
    }

    /**
     * 加载圆形图片
     */
    @BindingAdapter({"imageUrl", "circle"})
    public static void loadCircle(ImageView view, String imageUrl, int circle){
        if (circle == 1) {
            ImageLoader.loadCircle(view, imageUrl);
        }else{
            ImageLoader.load(view, imageUrl);
        }
    }

    /**
     * 加载圆形图片，指定宽高
     */
    @BindingAdapter({"imageUrl", "width", "height", "circle"})
    public static void loadCircle(ImageView view, String imageUrl, int width, int height, int circle){
        if (circle == 1) {
            ImageLoader.loadCircle(view, imageUrl, width, height);
        }else{
            ImageLoader.load(view, imageUrl, width, height);
        }
    }

    /**
     * 加载圆形图片
     */
    @BindingAdapter({"imageUrl", "corner", "type"})
    public static void loadWithCorner(ImageView view, String imageUrl, int corner, int type){
        if (corner > 0) {
            ImageLoader.loadWidthCorner(view, imageUrl, corner, type);
        }else{
            ImageLoader.load(view, imageUrl);
        }
    }

    /**
     * 加载圆形图片，指定宽高
     */
    @BindingAdapter({"imageUrl", "width", "height", "corner", "type"})
    public static void loadWithCorner(ImageView view, String imageUrl, int width, int height, int corner, int type){
        if (corner > 0) {
            ImageLoader.loadWidthCorner(view, imageUrl, width, height, corner, type);
        }else{
            ImageLoader.load(view, imageUrl, width, height);
        }
    }

    //  实现其他功能

    @BindingAdapter("android:background")
    public static void setBackground(View view, int resId) {
        if (resId == -100 || view == null) return;
        try {
            view.setBackgroundResource(resId);
        }catch (Exception ignore) {
            try {
                view.setBackgroundColor(resId);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        if (bitmap == null) return;
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        if (resId == -100) return;
        view.setImageResource(resId);
    }

    @BindingAdapter("android:textColor")
    public static void setTextColor(TextView view, int color) {
        if (view == null || color == -100) return;
        view.setTextColor(color);
    }

    //  基于MVVM实现的自动布局

    private static boolean initView(){
        if (scaleWidth == 1 && scaleHeight == 1) return false;
        if (scaleHeight > 0 || scaleWidth > 0) return true;
        if (!useAutoLayout) return false;
        int screenWidth = CommonTool.getScreenWidth(IApplication.getInstance());
        int screenHeight = CommonTool.getScreenHeight(IApplication.getInstance());
        int targetWidth, targetHeight;
        try{
            targetWidth = StringUtils.getInt(CommonTool.getMetaData(IApplication.getInstance(),
                    "design_width"));
            targetHeight = StringUtils.getInt(CommonTool.getMetaData(IApplication.getInstance(),
                    "design_height"));
        }catch (Exception e) {
            e.printStackTrace();
            useAutoLayout = false;
            return false;
        }
        if (targetWidth < 1 || targetHeight < 1) {
            useAutoLayout = false;
            ILog.e("===autoLayout===", "target width or height is too low, make sure you have " +
                    "set  design_width and design_height in your AndroidManifest");
            return false;
        }if (screenHeight < 1 || screenWidth < 1) {
            useAutoLayout = false;
            ILog.e("===autoLayout===", "not get your screen width or height , value less than 1");
            return false;
        }
        scaleWidth = (double) screenWidth / (double) targetWidth;
        scaleHeight = (double) screenHeight / (double) targetHeight;
        return true;
    }

    public static int getHeight(int height) {
        int value = (int)Math.round(scaleHeight * height);
        return  value < MIN_HEIGHT ? MIN_HEIGHT : value;
    }

    public static int getWidth(int width) {
        int value = (int)Math.round(scaleWidth * width);
        return value < MIN_WIDTH ? MIN_WIDTH : value;
    }

    //  设置宽高
    @BindingAdapter("layout_width")
    public static void setWidth(View view, int width) {
        if (view == null || width < 0 || !initView()) return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            params.width = getWidth(width);
        }
    }

    @BindingAdapter("layout_height")
    public static void setHeight(View view, int height) {
        if (view == null || height < 0 || !initView()) return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            params.height = getHeight(height);
        }
    }

    @BindingAdapter("minHeight")
    public static void setMinHeight(View view, int height) {
        view.setMinimumHeight((int)Math.round(height * scaleHeight));
    }

    @BindingAdapter("minWidth")
    public static void setMinWidth(View view, int height) {
        view.setMinimumWidth((int)Math.round(height * scaleWidth));
    }

    //  设置上下左右距离

    @BindingAdapter({"layout_margin"})
    public static void setMargin(View view, int margin) {
        if (view == null || !initView()) return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof ViewGroup.MarginLayoutParams)) return;
        int widthValue = (int)Math.round(margin * scaleWidth);
        int heightValue = (int)Math.round(margin * scaleHeight);
        ((ViewGroup.MarginLayoutParams) params).leftMargin = widthValue;
        ((ViewGroup.MarginLayoutParams) params).topMargin = heightValue;
        ((ViewGroup.MarginLayoutParams) params).rightMargin = widthValue;
        ((ViewGroup.MarginLayoutParams) params).bottomMargin = heightValue;
    }

    @BindingAdapter({"layout_marginTop"})
    public static void setMarginTop(View view, int marginTop) {
        if (view == null || !initView()) return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof ViewGroup.MarginLayoutParams)) return;
        ((ViewGroup.MarginLayoutParams) params).topMargin = (int)Math.round (marginTop * scaleHeight);
    }

    @BindingAdapter({"layout_marginBottom"})
    public static void setMarginBottom(View view, int marginBottom) {
        if (view == null || !initView()) return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof ViewGroup.MarginLayoutParams)) return;
        ((ViewGroup.MarginLayoutParams) params).bottomMargin = (int) Math.round(marginBottom * scaleHeight);
    }

    @BindingAdapter({"layout_marginLeft"})
    public static void setMarginLeft(View view, int marginLeft) {
        if (view == null || !initView()) return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof ViewGroup.MarginLayoutParams)) return;
        ((ViewGroup.MarginLayoutParams) params).leftMargin = (int)Math.round (marginLeft * scaleWidth);
    }

    @BindingAdapter({"layout_marginRight"})
    public static void setMarginRight(View view, int marginRight) {
        if (view == null || !initView()) return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof ViewGroup.MarginLayoutParams)) return;
        ((ViewGroup.MarginLayoutParams) params).rightMargin = (int)Math.round (marginRight * scaleWidth);
    }

    //  设置字体大小
    @BindingAdapter("textSize")
    public static void setTextSize(View view, int textSize) {
        if (view == null || !initView()) return;
        if (!(view instanceof TextView)) return;
        ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)Math.round(textSize * scaleWidth));
    }

    //  设置padding

    @BindingAdapter("padding")
    public static void setPadding(View view, int padding) {
        if (view == null || !initView()) return;
        //  int left, int top, int right, int bottom
        int widthValue = (int)Math.round(padding * scaleWidth);
        int heightValue = (int)Math.round(padding * scaleHeight);
        view.setPadding(widthValue, heightValue, widthValue, heightValue);
    }

    @BindingAdapter("paddingLeft")
    public static void setPaddingLeft(View view, int paddingLeft) {
        if (view == null || !initView()) return;
        //  int left, int top, int right, int bottom
        view.setPadding((int)Math.round(paddingLeft * scaleWidth), view.getPaddingTop(),
                view.getPaddingRight(), view.getPaddingBottom());
    }

    @BindingAdapter("paddingRight")
    public static void setPaddingRight(View view, int paddingRight) {
        if (view == null || !initView()) return;
        //  int left, int top, int right, int bottom
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(),
                (int)Math.round(paddingRight * scaleWidth), view.getPaddingBottom());
    }

    @BindingAdapter("paddingTop")
    public static void setPaddingTop(View view, int paddingTop) {
        if (view == null || !initView()) return;
        //  int left, int top, int right, int bottom
        view.setPadding(view.getPaddingLeft(), (int)Math.round(paddingTop * scaleHeight),
                view.getPaddingRight(), view.getPaddingBottom());
    }

    @BindingAdapter("paddingBottom")
    public static void setPaddingBottom(View view, int paddingBottom) {
        if (view == null || !initView()) return;
        //  int left, int top, int right, int bottom
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(),
                view.getPaddingRight(), (int)Math.round(paddingBottom * scaleHeight));
    }
}