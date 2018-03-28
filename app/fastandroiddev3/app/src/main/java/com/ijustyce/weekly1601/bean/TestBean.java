package com.ijustyce.weekly1601.bean;


import com.ijustyce.fastandroiddev3.base.BaseViewModel;
import com.ijustyce.fastandroiddev3.irecyclerview.IResponseData;

import java.util.ArrayList;

/**
 * Created by liguangchun on 2017/5/19.
 */

public class TestBean extends BaseViewModel {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public static class  TestModel extends IResponseData<TestBean>{
        public ArrayList<TestBean> mList;

        public void setList(ArrayList<TestBean> mList) {
            this.mList = mList;
        }

        @Override
        public ArrayList<TestBean> getList() {
            return mList;
        }
    }
}