package com.ijustyce.weekly1601;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ijustyce.fastandroiddev3.base.BaseActivity;
import com.ijustyce.fastandroiddev3.baseLib.utils.AutoScrollView;
import com.ijustyce.weekly1601.databinding.ItemView;
import com.ijustyce.weekly1601.databinding.ScrollView;

import java.util.ArrayList;

/**
 * Created by yangchun on 2017/2/14.
 */

public class ScrollActivity extends BaseActivity<ScrollView> {

    private ArrayList<String> data;
    @Override
    public void afterCreate() {
        ItemView itemView1 = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_view, null, false);
        ItemView itemView2 = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_view, null, false);

        contentView.container.addView(itemView1.getRoot());
        contentView.container.addView(itemView2.getRoot());

        itemView1.text.setText("早上好！");
        itemView2.text.setText("中午好!");
        data = new ArrayList<>();
        data.add("早上好！");
        data.add("中午好！");
        data.add("下午好！");
        data.add("傍晚好！");
        data.add("晚安啦！");

        AutoScrollView autoScrollView = new AutoScrollView(itemView1.getRoot(), itemView2.getRoot());
        autoScrollView.setMaxNum(data.size());
        autoScrollView.setUpDateViewCall(new AutoScrollView.UpDateViewCall() {
            @Override
            public void onUpdate(View view, int position) {
                TextView textView = (TextView) view.findViewById(R.id.text);
                if (textView != null) {
                    textView.setText(data.get(position));
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scroll;
    }
}
