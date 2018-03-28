package com.ijustyce.weekly1601;

import android.content.Intent;

import com.ijustyce.fastandroiddev3.base.BaseActivity;
import com.ijustyce.fastandroiddev3.baseLib.utils.FileUtils;
import com.ijustyce.fastandroiddev3.baseLib.utils.IJson;
import com.ijustyce.fastandroiddev3.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev3.baseLib.utils.TaskUtils;
import com.ijustyce.fastandroiddev3.baseLib.utils.ToastUtil;
import com.ijustyce.fastandroiddev3.contentprovider.CommonBean;
import com.ijustyce.fastandroiddev3.contentprovider.CommonData;
import com.ijustyce.weekly1601.bean.Bean;
import com.ijustyce.weekly1601.databinding.MainView;
import com.ijustyce.weekly1601.event.DownloadAndUploadEvent;
import com.ijustyce.weekly1601.event.ShowFunctionEvent;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public class MainActivity extends BaseActivity<MainView> {

    public final static int SCAN = 100;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterCreate() {
        contentView.setClickEvent(new DownloadAndUploadEvent(this));
        contentView.setFunctionEvent(new ShowFunctionEvent(this));

        new TaskUtils<String>(){
            @Override
            protected void onPostExecute(String value) {

            }

            @Override
            protected String doInBackground(Integer... params) {
                try {
                    String value =  FileUtils.readTextInputStream(getAssets().open("2.txt"));
                    if (!StringUtils.isEmpty(value)) {
                        ArrayList<Bean> beans = IJson.fromJson(value, ArrayList.class);
                        if (beans == null || beans.isEmpty()) return null;
                        ArrayList<CommonBean> commonBeen = new ArrayList<>();
                        for (Bean bean : beans) {
                            CommonBean tmp = new CommonBean();
                            tmp.key = bean.number;
                            tmp.value = bean.city;
                            tmp.userId = bean.province;
                            commonBeen.add(tmp);
                        }
                        CommonData.saveCommonList(commonBeen);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

//        ToastUtil.show("1 + 2 = " + JniUtils.getInstance().add(1, 2));

//        ILog.e("===java===", "start");
//
//        HttpManager.send(new Callback<ResponseBody>() {
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String result = response == null ? null : response.body().string();
//                    ILog.e("===java===", "返回的json数据是 " + result);
//                    weath s = IJson.fromJson(result, weath.class);
//                    ILog.e("===java===", "数据解析完成！");
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, HttpManager.service(httpService.class).send());

//        CommonTool.showNotify("ceshi", "ceshi", new Intent(), this, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

//        HttpManager.setBaseUrl("http://www.lzhplus.cn");
//        HttpManager.send(new Callback<Login>() {
//            @Override
//            public void onResponse(Call<Login> call, Response<Login> response) {
//                if (response == null || response.body() == null) {
//                    ILog.e("===error===", "error while ...");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Login> call, Throwable t) {
//                t.printStackTrace();
//            }
//        }, HttpManager.service(ssl.class).doLogin("ijustyce", "ijustyce@163.com", "123456", 0));

    }

    public static interface httpService {

        @GET("http://www.weather.com.cn/data/sk/101280601.html")
        public Call<ResponseBody> send();
    }

    public class weath {

        public WeatherinfoEntity weatherinfo;

        public class WeatherinfoEntity {
            public String city;
            public String cityid;
            public String temp;
            public String WD;
            public String WS;
            public String SD;
            public String WSE;
            public String time;
            public String isRadar;
            public String Radar;
            public String njd;
            public String qy;
            public String rain;
        }
    }

    @Override
    public void backPress() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case SCAN:
                String text = data.getExtras().getString("result");
                ToastUtil.show(text);
                break;
            default:
                break;
        }
    }
}