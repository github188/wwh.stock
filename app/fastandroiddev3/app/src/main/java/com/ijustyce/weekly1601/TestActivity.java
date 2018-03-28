package com.ijustyce.weekly1601;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.ijustyce.fastandroiddev3.base.BaseListActivity;
import com.ijustyce.fastandroiddev3.irecyclerview.BindingInfo;
import com.ijustyce.fastandroiddev3.irecyclerview.IAdapter;
import com.ijustyce.fastandroiddev3.irecyclerview.IRecyclerView;
import com.ijustyce.weekly1601.bean.TestBean;
import com.ijustyce.weekly1601.databinding.MyTestView;
import com.ijustyce.weekly1601.databinding.MyTextActivity;

import java.util.ArrayList;


import retrofit2.Call;

public class TestActivity extends AppCompatActivity{
    private String[] array=new String[]{
            "q","a","1","2","3","4","5","6","7","8"
    };
    private ArrayList<TestBean> mList=new ArrayList<>();
    private IRecyclerView iRecyclerView;




    private void innitData(){
        for (int i=0;i<10;i++){
            TestBean testBean=new TestBean();
            String s=i+"dcdcdcdcdvdvdvdv";
            testBean.setText(s);
            mList.add(testBean);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_test);
        innitData();
        BindingInfo bindingInfo=BindingInfo.createByLayoutIdAndBindName(R.layout.item_test, com.ijustyce.weekly1601.BR.item);
        IAdapter iAdapter=new IAdapter(TestActivity.this,mList,bindingInfo);
        iRecyclerView= (IRecyclerView) findViewById(R.id.test_iRecyclerView);
        iRecyclerView.mRecyclerView.setAdapter(iAdapter);
        iRecyclerView.loadFinish(true,false,true);
    }

    //iRecyclerView=new IRecyclerView(getApplicationContext());

        // iRecyclerView.mRecyclerView=new RecyclerView(getApplicationContext());
        //contentVie.testIRecyclerView.mRecyclerView.setLayoutManager(IRecyclerView.buildHorizontalLinearLayout(activity));
       // contentView.testIRecyclerView.mRecyclerView.setAdapter(iAdapter);
       /// contentView.testIRecyclerView.loadFinish(true,true,true);


}
