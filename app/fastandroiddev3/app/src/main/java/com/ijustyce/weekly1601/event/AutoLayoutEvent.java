package com.ijustyce.weekly1601.event;

import android.databinding.ObservableField;

import com.ijustyce.fastandroiddev3.baseLib.utils.ToastUtil;
import com.ijustyce.weekly1601.viewmodel.PersonView;

/**
 * Created by yangchun on 2017/5/19.
 */

public class AutoLayoutEvent {

    public ObservableField<String> name = new ObservableField<>();

    public AutoLayoutEvent() {
        buildObservableField();
    }

    private void buildObservableField() {
        PersonView personView = new PersonView();
        personView.name = "非正经项目架构";

        name.set(personView.name);
    }

    public void clickPerson() {
        ToastUtil.show("你好， 我叫 " + name.get());
        name.set("你点击了非正规项目架构！");
    }
}
