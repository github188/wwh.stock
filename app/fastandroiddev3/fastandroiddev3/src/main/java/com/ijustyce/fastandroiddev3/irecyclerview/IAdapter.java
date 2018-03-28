package com.ijustyce.fastandroiddev3.irecyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ijustyce.fastandroiddev3.base.BaseViewModel;
import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangchun on 16/4/15.  通用的RecyclerView adapter
 */
public class IAdapter<T> extends RecyclerView.Adapter<IBindingHolder> {

    private List<T> mData;
    private ArrayList<View> header, footer;
    private Context mContext;
    public int position, headerSize, footerSize;
    private RecyclerView recyclerView;
    private Handler handler;
    public static final int TYPE_NORMAL = -10_000;
    public int layoutId;
    private BindingInfo bindingInfos;
    public boolean scrollFinished = true;

    public IAdapter(Context mContext, List<T> mData, BindingInfo bindingInfos) {

        this.mData = mData;
        this.mContext = mContext;
        this.layoutId = bindingInfos == null ? 0 : bindingInfos.layoutId;
        this.bindingInfos = bindingInfos;
        handler = new Handler();
    }

    @Override
    public void onViewAttachedToWindow(IBindingHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && (holder.getLayoutPosition() == 0 || holder.getLayoutPosition() == 1)) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    public final Context getContext() {
        return mContext;
    }

    public final void removeItem(int position) {

        if (mData == null || position < 0 || position >= mData.size()) return;
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public final boolean isFooterVisible() {
        return position >= getItemCount() - 2;
    }

    /**
     * 返回最后可见的item的position
     */
    public final int getLastPosition() {

        return position;
    }

    public final void addFooterView(View footerView) {
        if (footer == null) {
            footer = new ArrayList<>();
            footerSize = 0;
        }
        int position = footerSize - 1;
        position = position > -1 ? position : 0;
        footer.add(position, footerView);
        footerSize++;
    }

    public final void addHeaderView(View headerView) {
        if (header == null) {
            header = new ArrayList<>();
            headerSize = 0;
        }
        header.add(headerSize, headerView);
        headerSize++;
    }

    public final int getDataSize() {

        return mData == null ? 0 : mData.size();
    }

    @Override
    public final int getItemViewType(int position) {

         if (position > -1 && position < headerSize) {
            return position;
        }
        if (position >= getDataSize() + headerSize) {
            return position;
        } else {
            if (bindingInfos.viewHolderCallBack != null) {
                return bindingInfos.viewHolderCallBack.getViewType(getObject(position - headerSize));
            }else {
                return TYPE_NORMAL;
            }
        }
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public final int getItemCount() {
        int size = getDataSize();
        return size + headerSize + footerSize;
    }

    @Override
    public final IBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IBindingHolder  commonHolder;
        switch (viewType) {
            case TYPE_NORMAL:
                commonHolder = createViewHolder(layoutId, mContext, parent);
                break;

            default:
                //  header
                if (viewType > -1 && viewType < headerSize) {
                    commonHolder = new IBindingHolder(header.get(viewType));
                }
                //  footer
                else if (viewType >= getDataSize() + headerSize) {
                    commonHolder = new IBindingHolder(footer.get(viewType - headerSize - getDataSize()));
                }
                //  normal item
                else {
                    //  use very layout
                    if (bindingInfos.viewHolderCallBack != null) {
                        commonHolder = createViewHolder(bindingInfos.viewHolderCallBack.getLayoutId(viewType), mContext, parent);
                    }else {
                        commonHolder = createViewHolder(layoutId, mContext, parent);    //  use default layout
                    }
                }
                break;
        }
        return commonHolder;
    }

    public IBindingHolder createViewHolder(int layoutId, Context mContext, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext), layoutId, parent, false);
        IBindingHolder holder = new IBindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }


    @Override
    public final void onBindViewHolder(IBindingHolder holder, int position) {

        this.position = position;
        if (position < headerSize || position >= getDataSize() + headerSize) {
            ILog.i("===object===", "is footer or header not OnBinding ...");
            return;
        }

        if (!scrollFinished) {
            ILog.e("===scroll===", "scrolling , not init...");
            return;
        }

        if (holder == null) return;
        if (holder.getBinding() != null) {
            holder.itemPosition = position;
            int index = position - headerSize;
            T item = getObject(index);//  扣除header占用的位置
            if (item == null) return;
            if (item instanceof BaseViewModel) ((BaseViewModel) item).position = index;
            ViewDataBinding binding = holder.getBinding();
            for (int i = 0; i < bindingInfos.size; i++) {
                Object value = bindingInfos.info.valueAt(i);
                binding.setVariable(bindingInfos.info.keyAt(i), value == null ? item : value);
            }
            binding.executePendingBindings();
            if (bindingInfos.callBack != null) bindingInfos.callBack.onCreated(item, position, binding);
        }
    }

    /**
     * 处理之前的任务，比如itemchanged、datachanged、itemremoved等 一般不需要手动触发
     */
    public final void doEvent() {
        if (recyclerView == null || recyclerView.isComputingLayout()) {
            doDelayEvent();
            return;
        }
        if (handler != null) handler.removeCallbacksAndMessages(null);
        int size = changedItem == null ? 0 : changedItem.size();
        for (int i = 0; i < size; i++) {
            int position = changedItem.indexOfKey(i);
            int type = changedItem.indexOfValue(i);
            switch (type) {
                case 1:
                    itemChanged(position);
                    break;
                case 2:
                    removeItem(position);
                    break;
                case 3:
                    itemInsert(position);
                    break;
                case 4:
                    dataChanged();
                    break;

            }
        }
    }

    private void doDelayEvent() {
        if (mData == null || mContext == null) {
            ILog.i("===IAdapter===", "mData or mContext is null, destroy handler ...");
            mData = null;
            mContext = null;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                handler = null;
            }
            return;
        }
        if (handler == null) return;
        if (changedItem == null || changedItem.size() < 1) handler.removeCallbacksAndMessages(null);
        else handler.postDelayed(run, 737);
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            doEvent();
        }
    };

    private SparseIntArray changedItem = new SparseIntArray();
    //  key is position and value is type, 1->changed, 2->remove, 3->insert, 4->DataChanged

    public final void itemChanged(int position) {
        if (recyclerView == null || !recyclerView.isComputingLayout()) notifyItemChanged(position);
        else {
            changedItem.put(position, 1);
            doDelayEvent();
        }
    }

    public final void itemRemove(int position) {
        if (recyclerView == null || !recyclerView.isComputingLayout()) notifyItemRemoved(position);
        else {
            changedItem.put(position, 2);
            doDelayEvent();
        }
    }

    public final void itemInsert(int position) {
        if (recyclerView == null || !recyclerView.isComputingLayout()) notifyItemInserted(position);
        else {
            changedItem.put(position, 3);
            doDelayEvent();
        }
    }

    public final void dataChanged() {
        if (recyclerView == null || !recyclerView.isComputingLayout()) notifyDataSetChanged();
        else {
            changedItem.put(4, 0);
            doDelayEvent();
        }
    }

    public final T getObject(int position) {

        if (mData == null || position < 0 || position >= mData.size()) {

            return null;
        }
        return mData.get(position);
    }
}