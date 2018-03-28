package com.ijustyce.fastandroiddev3.irecyclerview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ijustyce.fastandroiddev3.R;
import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev3.baseLib.utils.ScrollUtils;

/**
 * Created by yangchun on 16/4/15.  封装的 RecyclerView
 */
public class IRecyclerView extends RelativeLayout {

    public RecyclerView mRecyclerView;
    private LinearLayout mFooter, mFooterLoading;
    private RelativeLayout mHeader;
    private Context mContext;
    private Handler handler;
    private int marginTop = 0;
    private static int headerHeight;
    public boolean isCanPullDown = true; //是否能下拉
    public IAdapter adapter;
    private boolean hasMore = true; //  是否还有更多数据
    private boolean refreshing = false;

    private TextView refreshResult, loadMoreResult;
    private ImageView refreshHeader, loadImg, loadMoreImg;
    private boolean canRefresh = true;
    private int pageSize = 10;
    private final static String TAG = "IRecyclerView";
    private boolean noLoadWhileScroll = false;

    private boolean showRefreshEndAnim = true;

    public final void onRefresh(){
        onRefresh(true);
    }

    public final void onRefresh(boolean showAnim) {
        if (!isFirstVisible() || !showAnim) {
            showRefreshEndAnim = false;
            doRefresh();
            return;
        }
        showRefreshAnimA();
        if (handler == null) return;
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(autoRefresh, 1000);
    }

    public IRecyclerView enAbleNoLoadWhileScroll() {
        this.noLoadWhileScroll = true;
        if (adapter != null) {
            adapter.scrollFinished = true;
            adapter.dataChanged();
        }
        return this;
    }

    public IRecyclerView disAbleNoLoadWhileScroll() {
        this.noLoadWhileScroll = false;
        if (adapter != null) {
            adapter.scrollFinished = true;
            adapter.dataChanged();
        }
        return this;
    }

    private void showRefreshAnim() {
        showRefreshEndAnim = true;
        final int tmp = 3 * headerHeight;
        ValueAnimator ani = ValueAnimator.ofInt(0 , tmp);
        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                if (mRecyclerView != null) {
                    //修改了
                    if (isNullHead())return;
                    mHeader.setPadding(0, value  - headerHeight, 0, mHeader.getPaddingBottom());
                }
            }
        });
        ani.setDuration(1000);
        ani.start();
    }

    private void showRefreshAnimA() {
        showRefreshEndAnim = true;
        final int tmp = 3 * headerHeight;
        ValueAnimator ani = ValueAnimator.ofInt(0 , tmp);
        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                if (mRecyclerView != null) {
                    //修改了
                    if (isNullHead())return;
                    mHeader.setPadding(0, value, 0, mHeader.getPaddingBottom());
                }
            }
        });
        ani.setDuration(1000);
        ani.start();
    }

    private void doRefresh(){
        if (dataInterface != null && !refreshing && canRefresh) {
            refreshing = true;
            dataInterface.refresh();
        }
    }

    public final void setBackGround(@ColorRes int color) {
        if (mRecyclerView != null) mRecyclerView.setBackgroundColor(getResources().getColor(color));
    }

    public final void setBackGroundDrawable(@DrawableRes int drawable) {
        if (mRecyclerView != null) mRecyclerView.setBackgroundResource(drawable);
    }

    public final void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    public final void setPageSize(int size) {

        this.pageSize = size;
    }

    public final int getPageSize() {

        return pageSize;
    }

    @Deprecated
    public final void showFooterWhenNoMoreData(boolean showFooterWhenNoMoreData) {
    }

    @Deprecated
    public final void showFooterLabel(boolean show) {
//        initFooter();
//        if (footerLabel != null) {
//            footerLabel.setVisibility(show ? VISIBLE : GONE);
//        }
    }

    public final boolean stopScroll() {
        if (mRecyclerView == null) return false;
        mRecyclerView.stopScroll();
        return true;
    }

    public final void showFooter(boolean showFooter) {
        if (mFooterLoading == null) return;
        mFooterLoading.setVisibility(showFooter ? VISIBLE : GONE);
    }

    @Deprecated
    public final void setFooterLabel(String text) {

//        initFooter();
//        if (footerLabel == null) return;
//        footerLabel.setText(text);
    }

    private void createRecyclerView() {

        View mView = LayoutInflater.from(mContext).inflate(R.layout.fastandroiddev3_view_irecyclerview_recycler, this);
        if (mView == null) {
            ILog.e(TAG, "mView is null, it is a fastandroiddev error, please contact developer... ");
            return;
        }
        View view = mView.findViewById(R.id.irecyclerview_list);
        if (view instanceof RecyclerView){
            mRecyclerView = (RecyclerView) view;
        }
        else throw new RuntimeException("can not get RecyclerView, is your xml file " +
                "already have a view and it's id is R.id.irecyclerview_list ? ");

        loadImg = (ImageView) mView.findViewById(R.id.loadImg);
        Glide.with(mContext).load(R.mipmap.loading_three).into(loadImg);

        mRecyclerView.setVisibility(INVISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        handler = new Handler();
    }

    public final void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (mRecyclerView == null) return;
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public IDataInterface dataInterface;

    public final <Bean, Model extends IResponseData<Bean>> void setDataInterface(IDataInterface<Bean, Model> dataInterface) {
        this.dataInterface = dataInterface;
        this.adapter = dataInterface.adapter;
        this.adapter.setRecyclerView(mRecyclerView);
        dataInterface.setIRecyclerView(this);
        addListener();
    }

    public final void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void loadFinish(boolean isRefresh, boolean hasMore, boolean success) {
        if (isRefresh) {
            if (refreshResult != null) {
                showRefreshEndAnim = true;
                refreshResult.setText(success ? "刷新成功" : "刷新成功");
            }
            onRefreshEnd();
        }
        setHasMore(hasMore);
        showLoad(false);
    }

    public final boolean hasFooter() {
        return adapter.footerSize > 1;
    }

    public final boolean hasHeader() {
        return adapter.headerSize > 1;
    }

    private Runnable autoRefresh = new Runnable() {
        @Override
        public void run() {
            marginTop = 3 * headerHeight;
            onTouchUpA();
        }
    };

    private Runnable doOnRefreshEnd = new Runnable(){
        @Override
        public void run() {
            final int tmp = headerHeight;
            ValueAnimator ani = ValueAnimator.ofInt(0 , tmp);
            ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    value = tmp - value;
                    if (mRecyclerView != null) {
                        if(isNullHead()) return;
                        mHeader.setPadding(0, value, 0,  mHeader.getPaddingBottom());
                        //setPadding(0, value - headerHeight, 0,getPaddingBottom());
                    }
                }
            });
            ani.setDuration(200);
            ani.start();
            if (handler != null) handler.postDelayed(resetRefresh, 200);
        }
    };

    private Runnable resetRefresh = new Runnable() {
        @Override
        public void run() {
            if (refreshHeader != null && refreshResult != null) {
                refreshHeader.setVisibility(VISIBLE);
                refreshResult.setVisibility(GONE);
                if (refreshResult != null) {
                    refreshResult.setText("刷新中・・・");
                }
            }
        }
    };

    private void onRefreshEnd() {
        refreshing = false;
        if (mRecyclerView != null) mRecyclerView.setVisibility(VISIBLE);
        if (loadImg != null) loadImg.setVisibility(GONE);
        if (handler == null || !showRefreshEndAnim) return;
        if (refreshHeader != null && refreshResult != null) {
            refreshHeader.setVisibility(GONE);
            refreshResult.setVisibility(VISIBLE);
        }
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(doOnRefreshEnd, 1000);
    }

    private Runnable loadMoreFailed = new Runnable() {
        @Override
        public void run() {
            if (loadMoreResult == null || loadMoreImg == null) return;
            loadMoreImg.setVisibility(GONE);
            loadMoreResult.setVisibility(VISIBLE);
            if (handler != null) handler.postDelayed(resetFooter, 3000);
        }
    };

    private void showLoadFinishAnim() {
        final int tmp = headerHeight;
        ValueAnimator ani = ValueAnimator.ofInt(0 , tmp);
        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                value = tmp - value;
                if (mRecyclerView != null) {
                    if(mFooter == null) return;
                    mFooter.setPadding(0, mFooter.getPaddingTop(), 0, value  - headerHeight);
                }
            }
        });
        ani.setDuration(200);
        ani.start();
        showFooter(false);
    }

    private Runnable resetFooter = new Runnable() {
        @Override
        public void run() {
            if (loadMoreResult == null || loadMoreImg == null) return;
            loadMoreImg.setVisibility(VISIBLE);
            loadMoreResult.setVisibility(GONE);
            showLoadFinishAnim();
        }
    };

    /**
     * 瀑布流式 布局
     *
     * @param num      列数或行数（竖直即true为列数，false为行数）
     * @param vertical 是否为竖直布局
     */
    public final void setStaggeredGridLayout(int num, boolean vertical) {

        if (num <= 0) {
            num = 1;
        }
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(num, vertical ?
                StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL));
    }

    /**
     * gird layout
     *
     * @param num 列数
     */
    public final void setGirdLayout(int num) {
        setGirdLayout(num, null);
    }

    public static GridLayoutManager buildGridLayout(Context context, int num
            , final boolean scrollHorizontally, final boolean scrollVertically){
        return new GridLayoutManager(context, num){
            @Override
            public boolean canScrollHorizontally() {
                return scrollHorizontally;
            }

            @Override
            public boolean canScrollVertically() {
                return scrollVertically;
            }
        };
    }

    public static GridLayoutManager buildGridLayout(Context context, int num){
        return new GridLayoutManager(context, num);
    }

    public static LinearLayoutManager buildLinearLayout(Context context) {
        return new LinearLayoutManager(context);
    }

    public static LinearLayoutManager buildHorizontalLinearLayout(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return layoutManager;
    }

    public final void setGirdLayout(int num, GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        if (num <= 0) {
            num = 1;
        }
        if (num > 3) {
            ILog.e("===IRecyclerView===", "you set girdlayout with " +
                    "more than 3 span count ...");
        }
        final int size = num;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), num);
        mRecyclerView.setLayoutManager(layoutManager);
        if (spanSizeLookup != null) {
            layoutManager.setSpanSizeLookup(spanSizeLookup);
            return;
        }
        //  如果没有lookup
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == IAdapter.TYPE_NORMAL) {
                    return 1;
                }
                return size;
            }
        });
    }

    private void initHeader(){
        if (mHeader == null) {
            headerHeight = (int) (getResources().getDisplayMetrics().density * 35);
            mHeader = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.fastandroiddev3_view_irecyclerview_header, null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT );
            mHeader.setLayoutParams(params);

            View refresh = mHeader.findViewById(R.id.refreshImg);
            View textView = mHeader.findViewById(R.id.refreshResult);
            if (refresh instanceof ImageView) {
                Glide.with(mContext).load(R.mipmap.loading_three).into((ImageView) refresh);
                refreshHeader = (ImageView) refresh;
            }if (textView instanceof TextView) {
                refreshResult = (TextView) textView;
            }
        }
    }

    public final void addHeaderView(View view) {
        if (adapter != null && view != null) {
            adapter.addHeaderView(view);
        }
    }

    private void initFooter() {

        if (mFooter == null) {
            mFooter = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.fastandroiddev3_view_irecyclerview_footer, null)
                    .findViewById(R.id.container);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mFooter.setLayoutParams(params1);

            mFooterLoading = (LinearLayout) mFooter.findViewById(R.id.footerLoading);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    headerHeight);
            mFooterLoading.setLayoutParams(params);
            loadMoreImg = (ImageView) mFooter.findViewById(R.id.loadImg);
            loadMoreResult = (TextView) mFooter.findViewById(R.id.loadResult);
            Glide.with(mContext).load(R.mipmap.loading_three).into(loadMoreImg);
        }
    }

    public final void addFooterView(View view) {
        if (adapter != null && view != null) {
            adapter.addFooterView(view);
        }
    }

    public final boolean isFirstVisible() {
        if (mRecyclerView == null) return false;
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int firstCompleteVisibleItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
            return firstCompleteVisibleItem == 0 || gridLayoutManager.findLastVisibleItemPosition() == 0;
        }if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;

            return linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0 || linearLayoutManager.findLastVisibleItemPosition() == 0;

        }else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] firstVisibleItems = null;
            int[] lastVisibleItems = null;
            firstVisibleItems = ((StaggeredGridLayoutManager)layoutManager).findFirstCompletelyVisibleItemPositions(firstVisibleItems);
            lastVisibleItems = ((StaggeredGridLayoutManager)layoutManager).findLastVisibleItemPositions(lastVisibleItems);
            if ((firstVisibleItems != null && firstVisibleItems.length > 0) && (lastVisibleItems!= null && lastVisibleItems.length > 0)) {
                return firstVisibleItems[0] == 0 || lastVisibleItems[0] == 0;
            }
        }
        return false;
    }

    public final boolean isLastVisible(int position) {
        if (mRecyclerView == null) return false;
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if(layoutManager == null) return  false;
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int lastCompleteVisibleItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
            return lastCompleteVisibleItem == position || gridLayoutManager.findLastVisibleItemPosition() == position
                    || gridLayoutManager.findLastVisibleItemPosition() == position +1;
        }if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            return linearLayoutManager.findFirstCompletelyVisibleItemPosition() == position || linearLayoutManager.findLastVisibleItemPosition() == position
                    || linearLayoutManager.findLastVisibleItemPosition() == position + 1;
        }else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] firstVisibleItems = null;
            int[] lastVisibleItems = null;
            firstVisibleItems = ((StaggeredGridLayoutManager)layoutManager).findFirstCompletelyVisibleItemPositions(firstVisibleItems);
            lastVisibleItems = ((StaggeredGridLayoutManager)layoutManager).findLastVisibleItemPositions(lastVisibleItems);
            if ((firstVisibleItems != null && firstVisibleItems.length > 0) && (lastVisibleItems!= null && lastVisibleItems.length > 0)) {
                return firstVisibleItems[0] == position || lastVisibleItems[0] == position;
            }
        }
        return false;
    }

    private Runnable endRefresh = new Runnable() {
        @Override
        public void run() {
            showRefreshEndAnim = true;
            if (refreshResult != null) {
                refreshResult.setText("刷新超时");
            }
            onRefreshEnd();
        }
    };

    private int footerHintHeight;
    public void setFooterHintHeight(int height) {
        footerHintHeight = height;
        setPadding(0, -headerHeight, 0, -height);
    }

    private void showReturnToBottomAnim() {
        if (mRecyclerView == null) return;
        if(mFooter == null) return;
        final int totalPadding = mFooter.getPaddingBottom();
        ValueAnimator ani = ValueAnimator.ofInt(0 , mFooter.getPaddingBottom());
        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                if (mRecyclerView != null) {
                    if(mFooter == null) return;
                    mFooter.setPadding(0, mFooter.getPaddingTop(), 0, totalPadding - value);

                }
            }
        });

        ani.setDuration(500);
        ani.start();
    }

    private boolean showRefreshEndAnim() {
        boolean result = marginTop > 2 * headerHeight;
        final int tmp = marginTop;
        marginTop = 0;
        ValueAnimator ani = ValueAnimator.ofInt(0 , result ? tmp - headerHeight : tmp);
        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                value = tmp - value;
                if (mRecyclerView != null) {
                    setPadding(0, value  - headerHeight, 0, getPaddingBottom());
                }
            }
        });
        ani.setDuration(500);
        ani.start();
        return result;
    }

    private void onTouchUp(){
        if (!showRefreshEndAnim() || refreshing || !canRefresh) {
            return;
        }
        doRefresh();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(endRefresh, 10_000);
        }
    }

    private void onTouchUpA(){
        if (!showRefreshEndAnimA() || refreshing || !canRefresh) {
            return;
        }
        doRefresh();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(endRefresh, 10_000);
        }
    }


    private boolean showRefreshEndAnimA() {
        boolean result = marginTop > 2 * headerHeight;
        final int tmp = marginTop;
        marginTop = 0;
        ValueAnimator ani = ValueAnimator.ofInt(0 , result ? tmp - headerHeight : tmp);
        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                value = tmp - value;
                if (mRecyclerView != null) {
                    if (isNullHead()) return;
                    mHeader.setPadding(0, value, 0,  mHeader.getPaddingBottom());
                }
            }
        });
        ani.setDuration(800);
        ani.start();
        return result;
    }

    private ScrollUtils.ScrollListener scrollListener = new ScrollUtils.ScrollListener() {
        @Override
        public void scrollUp(float value) {
            //  下拉到底的逻辑
            if (value > 0 && canPullUp()) {
                if(mFooter == null) return;
                mFooter.setPadding(0, mFooter.getPaddingTop(), 0, mFooter.getPaddingBottom() + ((int)(value)/3));
                return;
            }if (value < 0 && canPullUp()) {
                showReturnToBottomAnim();
            }
            //  刷新部分的逻辑
            if (!canRefresh) return;
            if (marginTop > 0 && value <= 0) {
                if(!isCanPullDown)return;
                onTouchUpA();
            }
        }

        @Override
        public void scrollDown(float value) {
            if (!canRefresh || !isFirstVisible()) return;
            if (value >= 0 && marginTop > 0) {
                onTouchUpA();
                return;
            }
            //  已经在刷新或者第一条不可见的时候不padding
            if (mRecyclerView == null || refreshing) return;
            marginTop -= (int) value / 3;
            //改了
            if(isNullHead()) return;
            if(!isCanPullDown)return;
            mHeader.setPadding(0, marginTop, 0, mHeader.getPaddingBottom());
        }
    };

    //  判断是否是 没有更多数据且数据源不为空的场景。用于判断目前是否可以上滑
    private boolean canPullUp() {
        return  !hasMore && dataInterface != null && !dataInterface.hasNoData() && isLastVisible(dataInterface.adapter.getItemCount() -2);
    }

    private void addTouchEvent(){
        final ScrollUtils scrollUtils = new ScrollUtils(mContext, scrollListener);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                //下拉刷新的逻辑
                if (marginTop > 0 || isFirstVisible()) {
                    scrollUtils.onScroll(e);
                } else if (canPullUp()) { //
                    //没数据且上拉的场景。
                    scrollUtils.onScroll(e);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void showLoad(boolean showAnim) {
        if (mFooterLoading != null) {
            showFooter(showAnim);
        }if (showAnim && handler != null) {
            handler.postDelayed(loadMoreFailed, 10_000);
        }
    }

    private void addListener() {
        initHeader();
        initFooter();
        setPadding(0, -headerHeight, 0, getPaddingBottom());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (dataInterface == null || adapter == null) return;
                if (noLoadWhileScroll) {
                    adapter.scrollFinished = newState == RecyclerView.SCROLL_STATE_IDLE;
                    if (adapter.scrollFinished) {
                        adapter.dataChanged();
                    }
                }
                if (hasMore && newState == RecyclerView.SCROLL_STATE_IDLE && adapter.isFooterVisible()) {
                    showLoad(true);
                    dataInterface.loadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

        });
        addFooterView(mFooter);
        addHeaderView(mHeader);
        adapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        addTouchEvent();
    }

    public final void onDestroy() {
        if (dataInterface != null) {
            dataInterface.destroy();
            dataInterface = null;
        }
        if(handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        handler = null;
        mContext = null;
        mHeader = null;
        mFooter = null;
        mFooterLoading = null;
        mRecyclerView = null;
    }

    /**
     * 修改当前分页数，
     * @param value 修改的数值，正为增加，负为减少
     */
    public final void addPage(int value){
        dataInterface.pageNo += value;
    }

    /**
     * 初始化入口
     *
     * @param context context
     * @param attrs   参数
     */
    private void doInit(Context context, AttributeSet attrs) {
        if (this.mContext != null) return;
        this.mContext = context;
        createRecyclerView();
    }

    public IRecyclerView(Context context) {
        super(context);
        doInit(context, null);
    }

    public IRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        doInit(context, attrs);
    }

    public IRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        doInit(context, attrs);
    }

    private boolean isNullHead(){
        if(mHeader == null) return true;
        else {
            return false;
        }
    }
}