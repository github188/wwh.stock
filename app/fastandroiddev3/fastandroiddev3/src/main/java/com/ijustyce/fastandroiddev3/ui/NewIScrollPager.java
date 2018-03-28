package com.ijustyce.fastandroiddev3.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ijustyce.fastandroiddev3.IApplication;
import com.ijustyce.fastandroiddev3.R;
import com.ijustyce.fastandroiddev3.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev3.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev3.glide.ImageLoader;

import java.util.ArrayList;

/**
 * Created by liguangchun on 2017/9/10.
 */

public class NewIScrollPager extends RelativeLayout{
    public View mView;
    public ArrayList<String> url = new ArrayList<>();
    public ArrayList<View> points = new ArrayList<>();
    public int imgWidth = -1, imgHeight = -1;
    public int size = 0;
    public ViewGroup.LayoutParams imgParams;
    public @IdRes
    int imageId = R.id.imageView;

    public static double scaleH = 1.0, scaleW = 1.0;

    public NewIScrollPager(Context context) {
        super(context);
    }

    public NewIScrollPager init() {
        return init(R.layout.fastandroiddev3_view_ipager);
    }

    public NewIScrollPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewIScrollPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NewIScrollPager init(@LayoutRes int id) {
        mView = LayoutInflater.from(getContext()).inflate(id, this);
        return this;
    }

    static {
        double target_width = StringUtils.getDouble(CommonTool.getMetaData(IApplication.getInstance(),
                "design_width"));
        double target_height = StringUtils.getDouble(CommonTool.getMetaData(IApplication.getInstance(),
                "design_height"));
        if (target_width >= 480 || target_height >= 800) {
            scaleH = (double) CommonTool.getScreenHeight(IApplication.getInstance()) / target_height;
            scaleW = (double) CommonTool.getScreenWidth(IApplication.getInstance()) / target_width;
        }
    }

    public NewIScrollPager setImgWidthAndHeight(int width, int height) {
        imgWidth = width;
        imgHeight = height;
        if (scaleW == 1.0 || scaleH == 1.0) return this;
        int viewWidth = (int) (imgWidth * scaleW);
        int viewHeight = (int) (imgHeight * scaleH);
        imgParams = new ViewGroup.LayoutParams(viewWidth, viewHeight);
        mView.setLayoutParams(imgParams);
        return this;
    }

    private int pointMarginBottom = (int)(12 * scaleW);

    public NewIScrollPager setPointMarginBottom(int marginBottom){
        this.pointMarginBottom = (int) (marginBottom * scaleH);
        return this;
    }

    private LinearLayout pointContainer;
    public void setUrl(ArrayList<String> url) {
        pointContainer = mView == null ?
                null : (LinearLayout) mView.findViewById(R.id.pointLayout);
        if (pointContainer == null) {
            ILog.e("error view !!! in IScrollPager");
            return;
        }
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, pointMarginBottom);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        pointContainer.setLayoutParams(params);
        if (url == null) {
            ILog.e("error url is null !!! in IScrollPager");
            return;
        }
        if (normalDrawable == null || selectDrawable == null){
            setPointsColor(R.color.white, R.color.black);
        }
        onSizeChanged(url.size());
        updatePoints();
        this.url.addAll(url);
        setUpAdapter();
    }

    private void updatePoints(){
        if (points == null) points = new ArrayList<>();
        pointContainer.removeAllViews();
        points.clear();

        if (points.size() == size) return;      //  数量没变，不予处理
        if (size < minScrollNum) return;        //  数量不够，不处理

        int currentId = size > 0 ? currentBannerId % size : 0;
        for (int i = 0; i < size; i++) {
            View view = buildPoint(i == currentId);
            pointContainer.addView(view);
            points.add(view);
        }
    }

    public NewIScrollPager setOffscreenPageLimit(int size){
        if(viewPager != null) viewPager.setOffscreenPageLimit(size);
        return this;
    }

    public NewIScrollPager changeUrl(ArrayList<String> newUrls){
        if (url == null || adapter == null || newUrls == null || viewPager == null) return this;
        url.clear();
        url.addAll(newUrls);
        onSizeChanged(url.size());
        adapter.notifyDataSetChanged();
        updatePoints();
        beginAutoScroll(0);
        return this;
    }

    private NewIScrollPager.PageAdapter adapter;

    private void setUpAdapter() {
        viewPager = mView == null ? null : (IViewPager) mView.findViewById(R.id.viewPager);
        if (viewPager == null) return;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position < COUNT_NUM) return;
                currentBannerId = position;
                onSelectPage(position % size);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (viewPager == null) {
            ILog.e("error viewpager is null !!! in IScrollPager");
            return;
        }
        adapter = new NewIScrollPager.PageAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(size > 2 ? 2 : size);
        if (currentBannerId >= COUNT_NUM) {
            viewPager.setCurrentItem(currentBannerId);
        }

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                if (Build.VERSION.SDK_INT >= 11) {
                    float alpha = Math.abs(Math.abs(position) - 1);
                    page.setAlpha(alpha);
                }
            }
        });

        handler = new Handler();
        handler.post(scrollBanner);
    }

    public void beginAutoScroll(int delay) {
        if (handler != null && adapter != null && viewPager != null){
            handler.postDelayed(scrollBanner, delay);
        }
    }

    public void cancelAutoScroll() {
        if (handler != null) handler.removeCallbacksAndMessages(null);
    }

    private OnClickListener listener;

    public NewIScrollPager setClickListener(OnClickListener listener){
        this.listener = listener;
        return this;
    }

    private class PageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return size <= 0 ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup paramView, int position) {
            final int paramInt = position % size;
            View view = buildItemView(paramInt);
            paramView.addView(view);
            view.setOnClickListener(listener);
            return view;
        }

        public void destroyItem(ViewGroup paramView, int paramInt, Object paramObject) {
            if (paramObject instanceof View) {
                View view = (View) paramObject;
                views.put(paramInt % size, view);
            }
        }
    }

    private IViewPager viewPager;
    private Handler handler;
    private int currentBannerId;
    private int delay = 3000;
    private int minScrollNum = 2;
    private static final int COUNT_NUM = 10000;
    private Runnable scrollBanner = new Runnable() {
        @Override
        public void run() {
            if (handler == null) return;
            handler.removeCallbacksAndMessages(null);
            if (url == null || size < minScrollNum) return;
            if (viewPager != null && getContext() != null) {
                viewPager.setCurrentItem(currentBannerId, true);
            }
            if (handler != null) {
                handler.postDelayed(scrollBanner, delay);
            }
            currentBannerId++;
        }
    };

    /**
     * 设置自动轮播需要的最少图片数量，低于这个数量将不自动轮播
     * @param minNum    自动轮播需要的最少图片数量
     */
    public NewIScrollPager setMinScrollNum(int minNum){
        minScrollNum = minNum;
        return this;
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (handler == null) return;
        if (visibility != View.VISIBLE){
            cancelAutoScroll();
        }else{
            beginAutoScroll(0);
        }
    }

    public void destroy(){
        if (url != null) url.clear();
        if (adapter != null) adapter.notifyDataSetChanged();
        if (handler != null) handler.removeCallbacksAndMessages(null);
        viewPager = null;
        handler = null;
        mView = null;
        adapter = null;
    }

    public NewIScrollPager setScrollDelay(int delay){
        this.delay = delay;
        return this;
    }

    private SparseArray<View> views = new SparseArray<>();

    private View buildItemView(int position) {
        View view = views.get(position);
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view);
            }
        }
        else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fastandroiddev3_item_scrollpager, null);
            ImageView imageView = view == null ? null : (ImageView) view.findViewById(imageId);
            if (imageView == null) {
                return view;
            }if (imgParams != null){
                imageView.setLayoutParams(imgParams);
            }
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setTag(R.string.item_position, position);
            ImageLoader.load(imageView, getUrl(position), imgWidth, imgHeight);
            ILog.e("===value===", "create view ...");
            addTouchListener(view);
        }
        ImageView imageView = (ImageView) view.findViewById(imageId);
        if (imageView != null) {
            ImageLoader.load(imageView, getUrl(position), imgWidth, imgHeight);
        }
        return view;
    }

    private String getUrl(int position){
        if (url == null || url.isEmpty() || url.size() <= position || position < 0) return null;
        return url.get(position);
    }

    private void onSizeChanged(int newSize) {
        size = newSize;
        if (currentBannerId < COUNT_NUM){
            currentBannerId = size * COUNT_NUM;
        }
        if (viewPager != null) viewPager.setCanScroll(size >= minScrollNum);
    }

    private void addTouchListener(View view) {
        if (view == null) return;
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventValue = event.getAction();
                switch (eventValue) {
                    case MotionEvent.ACTION_DOWN:
                        cancelAutoScroll();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        beginAutoScroll(10000);
                        break;
                    default:
                        break;

                }
                return false;
            }
        });
    }

    private GradientDrawable normalDrawable, selectDrawable;
    private int pointSize = (int)(12 * scaleW);
    private int margin = (int)(8 * scaleW);

    public NewIScrollPager setPointsMarginAndSize(int margin, int pointSize){
        this.margin = (int) (margin * scaleW) / 2;
        this.pointSize = (int) (pointSize * scaleW);
        if (normalDrawable != null && selectDrawable != null){
            normalDrawable.setCornerRadius(pointSize);
            selectDrawable.setCornerRadius(pointSize);
        }
        return this;
    }

    private View buildPoint(boolean isSelected) {
        View view = new View(getContext());
        view.setBackgroundDrawable(isSelected ? selectDrawable : normalDrawable);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointSize, pointSize);
        params.setMargins(margin, 0, margin, 0);
        view.setLayoutParams(params);
        return view;
    }

    public NewIScrollPager setPointsColor(@ColorRes int normal, @ColorRes int selected) {
        normalDrawable = (GradientDrawable)getResources().getDrawable(R.drawable.point_normal);
        selectDrawable = (GradientDrawable)getResources().getDrawable(R.drawable.point_selected);
        normalDrawable.setColor(getResources().getColor(normal));
        selectDrawable.setColor(getResources().getColor(selected));
        if (pointSize > 1){
            normalDrawable.setCornerRadius(pointSize);
            selectDrawable.setCornerRadius(pointSize);
        }
        return this;
    }

    private synchronized void onSelectPage(int id) {
        if (normalDrawable == null || selectDrawable == null) {
            return;
        }
        for (View view : points) {
            if (view == null) continue;
            view.setBackgroundDrawable(normalDrawable);
        }
        points.get(id).setBackgroundDrawable(selectDrawable);
    }
}
