package com.ijustyce.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.ijustyce.fastandroiddev3.base.BaseActivity;
import com.ijustyce.fastandroiddev3.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev3.irecyclerview.BindingInfo;
import com.ijustyce.fastandroiddev3.irecyclerview.IAdapter;
import com.ijustyce.fastandroiddev3.irecyclerview.IRecyclerView;
import com.ijustyce.map.databinding.SelectAddressView;
import com.ijustyce.map.BR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 17-5-7.
 */

public class SelectAddressActivity extends BaseActivity<SelectAddressView> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_address;
    }

    private ArrayList<PoiInfo> list;
    private IAdapter<PoiInfo> adapter;

    @Override
    public void afterCreate() {
        contentView.list.setLayoutManager(IRecyclerView.buildLinearLayout(context));
        BindingInfo bindingInfo = BindingInfo.createByLayoutIdAndBindName
                (R.layout.item_address, BR.item).add(BR.event, new ClickEvent(this));
        list = new ArrayList<>();
        adapter = new IAdapter<>(context, list, bindingInfo);
        contentView.list.setAdapter(adapter);

        getData();
        initSearch();
    }

    public static class ClickEvent {

        private Activity activity;

        public ClickEvent(Activity activity) {
            this.activity = activity;
        }

        public void itemClick(PoiInfo info) {
            if (activity == null || info == null) return;
            Bundle bundle = new Bundle();
            bundle.putParcelable("item", info);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
        }
    }

    private void getData() {
        contentView.input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchAddress(contentView.input.getText().toString());
            }
        });
    }

    private SearchLocation searchLocation;

    private void initSearch() {
        new LocationHelper(activity, new LocationHelper.LocationListener() {
            @Override
            public void onUpdateLocation(BDLocation location) {
                if (LocationHelper.isLocationValid(location)) {
                    searchLocation = SearchLocation.initInCity(location.getCity());
                }
            }
        }).request();
    }

    private void updateSearch(List<PoiInfo> list) {
        if (this.list != null) {
            this.list.clear();
            this.list.addAll(list);
        }
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    private void searchAddress(String s) {
        if (searchLocation == null) {
            initSearch();
            return;
        }
        if (StringUtils.isEmpty(s) || s.length() < 2) return;
        searchLocation.addListener(new SearchLocation.AddressListener() {
            @Override
            public void onGetAddress(List<PoiInfo> list) {
                if (contentView == null || list == null || list.isEmpty()) return;
                updateSearch(list);
            }
        }).searchKeyWord(s);
    }
}
