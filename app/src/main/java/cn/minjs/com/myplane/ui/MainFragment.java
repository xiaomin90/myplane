package cn.minjs.com.myplane.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cn.minjs.com.myplane.R;
import cn.minjs.com.myplane.adapter.MainPagerAdapter;
import cn.minjs.com.myplane.presenter.ZhihuDailyPresenter;

/**
 * Created by js.min on 2017/6/7.
 * mail : js.min@flyingwings.cn
 */

public class MainFragment extends Fragment {

    private Context mContext;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private int mViewPagerLimit = 3;

    private GuokrFragment mGuokrFragment;
    private ZhihuDailyFragment mZhihuDailyFragment;
    private DoubanMomentFragment mDoubanMomentFragment;

    private ZhihuDailyPresenter mZhihuDailyPresenter;

    private MainPagerAdapter mMainPagerAdapter;

    public  MainFragment() {}

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();

        // view
        if(savedInstanceState != null) {
            FragmentManager manager = this.getChildFragmentManager();
            mZhihuDailyFragment = (ZhihuDailyFragment) manager.getFragment(savedInstanceState,"zhihu");
            mGuokrFragment = (GuokrFragment) manager.getFragment(savedInstanceState,"guokr");
            mDoubanMomentFragment = (DoubanMomentFragment) manager.getFragment(savedInstanceState,"douban");
        } else {
            mZhihuDailyFragment = ZhihuDailyFragment.newInstance();
            mGuokrFragment = GuokrFragment.newInstance();
            mDoubanMomentFragment = DoubanMomentFragment.newInstance();
        }

        // presenter
        mZhihuDailyPresenter = new ZhihuDailyPresenter(mContext,mZhihuDailyFragment);

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(mViewPagerLimit);

        mMainPagerAdapter = new MainPagerAdapter(this.getChildFragmentManager(),
                                mContext,
                                mZhihuDailyFragment,
                                mGuokrFragment,
                                mDoubanMomentFragment);
        mViewPager.setAdapter(mMainPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        setHasOptionsMenu(true);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                if(tab.getPosition() ==1) {
                    fab.hide();
                    return;
                }
                fab.show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState, "zhihu", mZhihuDailyFragment);
        manager.putFragment(outState, "guokr", mGuokrFragment);
        manager.putFragment(outState, "douban", mDoubanMomentFragment);
    }
}
