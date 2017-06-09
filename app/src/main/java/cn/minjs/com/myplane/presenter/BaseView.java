package cn.minjs.com.myplane.presenter;

import android.view.View;

/**
 * Created by js.min on 2017/6/7.
 * mail : js.min@flyingwings.cn
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void initViews(View view);
}
