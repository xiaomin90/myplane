package cn.minjs.com.myplane.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.minjs.com.myplane.bean.StringModelImpl;
import cn.minjs.com.myplane.bean.ZhihuDailyNews;
import cn.minjs.com.myplane.interfaze.OnStringListener;
import cn.minjs.com.myplane.ui.ZhihuDailyContract;
import cn.minjs.com.myplane.uitls.Api;
import cn.minjs.com.myplane.uitls.DateFormatter;

/**
 * Created by js.min on 2017/6/7.
 * mail : js.min@flyingwings.cn
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {

    private ZhihuDailyContract.View mView;
    private Context mContext;
    private StringModelImpl mStringModel;
    private DateFormatter mFormatter = new DateFormatter();

    private ArrayList<ZhihuDailyNews.Question> list = new ArrayList<ZhihuDailyNews.Question>();

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view) {
        this.mContext = context;
        this.mView = view;
        // view 和 presenter相关绑定
        this.mView.setPresenter(this);
        mStringModel = new StringModelImpl(mContext);

    }

    @Override
    public void loadPosts(long date, boolean clearing) {
        if (clearing) {
            mView.showLoading();
        }

        // 网络请求数据 volley 获取数据
        mStringModel.load(Api.ZHIHU_HISTORY + mFormatter.ZhihuDailyDateFormat(date), new OnStringListener() {
            @Override
            public void onSuccess(String result) {
                try {
                    ZhihuDailyNews post = new Gson().fromJson(result, ZhihuDailyNews.class);
                    if (clearing) {
                        list.clear();
                    }
                    for (ZhihuDailyNews.Question item : post.getStories()) {
                        list.add(item);
                    }
                    mView.showResults(list);
                } catch (JsonSyntaxException e) {
                    mView.showError();
                }
                mView.stopLoading();
            }

            @Override
            public void onError(VolleyError error) {
                mView.stopLoading();
                mView.showError();
            }
        });

    }

    @Override
    public void refresh() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    @Override
    public void startReading(int position) {

    }

    @Override
    public void feelLucky() {

    }

    @Override
    public void start() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }
}
