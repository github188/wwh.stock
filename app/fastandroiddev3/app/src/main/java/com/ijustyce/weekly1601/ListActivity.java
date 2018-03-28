package com.ijustyce.weekly1601;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ijustyce.fastandroiddev3.base.BaseListActivity;
import com.ijustyce.fastandroiddev3.databinding.ListActivityView;
import com.ijustyce.fastandroiddev3.event.TitleBarEvent;
import com.ijustyce.fastandroiddev3.http.HttpManager;
import com.ijustyce.fastandroiddev3.irecyclerview.BindingInfo;
import com.ijustyce.weekly1601.bean.AlbumBean;
import com.ijustyce.weekly1601.databinding.FooterView;
import com.ijustyce.weekly1601.http.UserService;
import com.ijustyce.weekly1601.model.AlbumList;

import retrofit2.Call;

/**
 * Created by yangchun on 2016/11/12.
 */

public class ListActivity extends BaseListActivity<ListActivityView, AlbumBean, AlbumList> {

    @Override
    public void afterCreate() {
        contentView.titleBar.getViewModel().titleText.set("列表展示");
        contentView.titleBar.getViewModel().rightText.set("刷新");

        contentView.titleBar.setClickEvent(new TitleBarEvent(){
            @Override
            public void rightTextClick(View view) {
                refresh();
            }
        });

        contentView.recyclerView.list.addHeaderView(buildView());
        contentView.recyclerView.list.addFooterView(buildView());
        contentView.recyclerView.list.setFooterHintHeight(360);
    }

    private View buildView(){

        FooterView footerView = DataBindingUtil.inflate(LayoutInflater.from(activity),
                R.layout.item_discovery_bottom, null, false);
        View header1 = footerView.getRoot();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 360);
        header1.setLayoutParams(params);
        return header1;
    }

    @Override
    public BindingInfo getBindingInfo() {
        return BindingInfo.createByLayoutIdAndBindName(R.layout.item_person, BR.personView);
    }

    @Override
    public Call<AlbumList> getListCall(int pageNo) {
        return HttpManager.service(UserService.class).listAlbum();
    }
}