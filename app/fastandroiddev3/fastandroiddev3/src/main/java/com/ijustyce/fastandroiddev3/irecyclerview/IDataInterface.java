package com.ijustyce.fastandroiddev3.irecyclerview;

import android.content.Context;

import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangchun on 2016/12/29.
 */

public abstract class IDataInterface<Bean, Model extends IResponseData<Bean>> {

    public int pageNo = 1, startPage = 1;
    public abstract boolean loadData(int pageNo, Callback<Model> callback);
    public void dataLoadFailed(Throwable throwable){}
    public boolean onDataGet(Model data){return true;}
    public void onDataShow(ArrayList<Bean> data){}
    public void afterBindToView(Model model){}

    public ArrayList<Bean> data;
    public IAdapter<Bean> adapter;
    private IRecyclerView iRecyclerView;
    private Callback<Model> callback;

    public IDataInterface(Context context, BindingInfo bindingInfo){
        if (context == null || bindingInfo == null) return;
        data = new ArrayList<>();
        adapter = new IAdapter<>(context, data, bindingInfo);
        callback = new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response == null) {
                    onFailure(call, new NullPointerException("response is null ..."));
                }
                else if(onDataGet(response.body())){
                    bindDataToView(response.body());
                }
                onDataShow(data);
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                t.printStackTrace();
                dataLoadFailed(t);
                onDataGet(null);
                onDataShow(data);
                if (iRecyclerView != null) {
                    ILog.e("===load===", "failed " + t.getMessage());
                    iRecyclerView.loadFinish(true, false, false);
                }
            }
        };
    }

    public void bindDataToView(Model data){
        if (hasNull()) return;
        ArrayList<Bean> arrayList = data == null ? null : data.getList();
        boolean isRefresh = pageNo == startPage;
        if (arrayList == null) {
            ILog.e("===load===", "failed list is null");
            iRecyclerView.loadFinish(isRefresh, false, false);
        }else {
            if (isRefresh) this.data.clear();
            if (!arrayList.isEmpty()) this.data.addAll(arrayList);
            adapter.dataChanged();
            int size = arrayList.size();
            ILog.e("===load===", "load finish , size is " + size);
            if(iRecyclerView.getPageSize() <= size){
                pageNo++;
                iRecyclerView.loadFinish(isRefresh, true, size > 0);
            }else {
                iRecyclerView.loadFinish(isRefresh, false, size > 0);
            }
        }
        afterBindToView(data);
    }

    public boolean hasNull(){
        return adapter == null || data == null || callback == null || iRecyclerView == null;
    }

    public void setStartPage(int startPage){
        this.startPage = startPage;
    }

    void refresh(){
        if (hasNull()) return;
        pageNo = startPage;
        if (!loadData(pageNo, callback) && !hasNull()) {
            ILog.e("===load===", "failed , network");
            iRecyclerView.loadFinish(true, false, false);
            onDataGet(null);
            onDataShow(data);
        }
    }

    void loadMore(){
        if (hasNull()) return;
        if (!loadData(pageNo, callback) && !hasNull()) {
            ILog.e("===load===", "failed , network");
            iRecyclerView.loadFinish(false, true, false);
            onDataGet(null);
            onDataShow(data);
        }
    }

    public boolean hasNoData(){
        return data != null && data.isEmpty();
    }

    void destroy(){
        if (data != null) data.clear();
        if (adapter != null) adapter.dataChanged();
        data = null;
        adapter = null;
        callback = null;
        iRecyclerView = null;
    }

    void setIRecyclerView(IRecyclerView iRecyclerView) {
        if (iRecyclerView == null || data == null || adapter == null || callback == null)return;
        this.iRecyclerView = iRecyclerView;
    }
}
