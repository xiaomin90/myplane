package cn.minjs.com.myplane.ui;

import java.util.ArrayList;

import cn.minjs.com.myplane.bean.ZhihuDailyNews;
import cn.minjs.com.myplane.presenter.BasePresenter;
import cn.minjs.com.myplane.presenter.BaseView;

/**
 * Created by js.min on 2017/6/7.
 * mail : js.min@flyingwings.cn
 */

public interface ZhihuDailyContract {


    interface View extends BaseView<Presenter> {

        void showError();

        void showLoading();

        void stopLoading();

        void showResults(ArrayList<ZhihuDailyNews.Question> list);

    }

    interface Presenter extends BasePresenter {

        void loadPosts(long date, boolean clearing);

        void refresh();

        void loadMore(long date);

        void startReading(int position);

        void feelLucky();

    }



}
