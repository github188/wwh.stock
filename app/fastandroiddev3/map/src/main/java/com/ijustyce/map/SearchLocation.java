package com.ijustyce.map;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.ijustyce.fastandroiddev3.IApplication;
import com.ijustyce.fastandroiddev3.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev3.baseLib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 17-5-7.
 */

public class SearchLocation {

    private static PoiSearch poiSearch;
    private static SuggestionSearch suggestionSearch;
    private static String city, keyWord;
    private AddressListener listener;

    private static volatile SearchLocation searchLocation;

    private SearchLocation() {
        poiSearch = PoiSearch.newInstance();
        suggestionSearch = SuggestionSearch.newInstance();
    }

    public static SearchLocation initInCity(String city) {
        SearchLocation.city = city;
        SearchLocation result = searchLocation;
        if (result == null) {
            synchronized (SearchLocation.class) {
                result = searchLocation;
                if (result == null) {
                    SDKInitializer.initialize(IApplication.getInstance());
                    result = searchLocation = new SearchLocation();
                }
            }
        }
        return result;
    }

    public SearchLocation addListener(AddressListener listener) {
        this.listener = listener;
        return this;
    }

    public void searchKeyWord(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            ToastUtil.show("关键字不能为空！");
            return;
        }
        SearchLocation.keyWord = keyword;
        if (poiSearch != null) {
            poiSearch.setOnGetPoiSearchResultListener(getPoiSearchListener());
            poiSearch.searchInCity((new PoiCitySearchOption()).city(city).keyword(keyword).pageNum(15));
        }
    }

    public interface AddressListener {
        public void onGetAddress(List<PoiInfo> list);
    }

    private OnGetSuggestionResultListener suggestionResult = new OnGetSuggestionResultListener() {
        public void onGetSuggestionResult(SuggestionResult res) {
            if (res == null || res.getAllSuggestions() == null) {
                return;
            }
            List<SuggestionResult.SuggestionInfo> lists = res.getAllSuggestions();
            List<PoiInfo> result = new ArrayList<>();
            for (SuggestionResult.SuggestionInfo tmp : lists){
                PoiInfo info = new PoiInfo();
                info.location = tmp.pt;
                info.address = tmp.city + tmp.district + tmp.key;
                info.name = tmp.key;
                result.add(info);
            }
            if (listener != null) {
                listener.onGetAddress(result);
            }
        }
    };

    private OnGetPoiSearchResultListener getPoiSearchListener() {
        return new OnGetPoiSearchResultListener(){
            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }

            public void onGetPoiResult(PoiResult result){
                List<PoiInfo> list = poiSearch == null || result == null || result.getAllPoi() == null
                        || result.getAllPoi().isEmpty() ? null : result.getAllPoi();
                if (list == null || list.isEmpty()) {
                    suggestionSearch.setOnGetSuggestionResultListener(suggestionResult);
                    suggestionSearch.requestSuggestion(new SuggestionSearchOption().city(city).keyword(keyWord));
                    return;
                }
                if (listener != null) {
                    listener.onGetAddress(list);
                }
            }
            public void onGetPoiDetailResult(PoiDetailResult result){
                //获取Place详情页检索结果
            }
        };
    }
}
