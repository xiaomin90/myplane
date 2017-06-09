package cn.minjs.com.myplane.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.minjs.com.myplane.ui.DoubanMomentFragment;
import cn.minjs.com.myplane.ui.GuokrFragment;
import cn.minjs.com.myplane.ui.ZhihuDailyFragment;

/**
 * Created by js.min on 2017/6/7.
 * mail : js.min@flyingwings.cn
 */

public class MainPagerAdapter extends FragmentPagerAdapter {


    private List<String> mtitles = new ArrayList<>();
    private Context mContext;
    private ZhihuDailyFragment mZhihuDailyFragment;
    private GuokrFragment mGuokrFragment;
    private DoubanMomentFragment mDoubanMomentFragment;

    public ZhihuDailyFragment getZhihuDailyFragment() {
        return this.mZhihuDailyFragment;
    }

    public GuokrFragment getGuokrFragment() {
        return this.mGuokrFragment;
    }

    public DoubanMomentFragment getDoubanMomentFragment() {
        return this.mDoubanMomentFragment;
    }



    public MainPagerAdapter(FragmentManager fm,
                            Context context,
                            ZhihuDailyFragment zhihuDailyFragment,
                            GuokrFragment guokrFragment,
                            DoubanMomentFragment doubanMomentFragment) {
        super(fm);
        mContext = context;
        mtitles.add("知乎日报");
        mtitles.add("果壳精选");
        mtitles.add("豆瓣一刻");
        this.mZhihuDailyFragment = zhihuDailyFragment;
        this.mGuokrFragment = guokrFragment;
        this.mDoubanMomentFragment = doubanMomentFragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mZhihuDailyFragment;
        switch (position) {
            case 0:
                fragment = mZhihuDailyFragment;
                break;
            case 1:
                fragment = mGuokrFragment;
                break;
            case 2:
                fragment = mDoubanMomentFragment;
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mtitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mtitles.get(position);
    }


}
