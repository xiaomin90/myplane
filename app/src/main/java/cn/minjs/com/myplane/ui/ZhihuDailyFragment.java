package cn.minjs.com.myplane.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import cn.minjs.com.myplane.R;
import cn.minjs.com.myplane.adapter.MainPagerAdapter;
import cn.minjs.com.myplane.adapter.ZhihuDailyNewsAdapter;
import cn.minjs.com.myplane.bean.ZhihuDailyNews;
import cn.minjs.com.myplane.uitls.PlaneLog;

/**
 * Created by js.min on 2017/6/7.
 * mail : js.min@flyingwings.cn
 */

public class ZhihuDailyFragment extends Fragment  implements ZhihuDailyContract.View{

    private String TAG = "ZhihuDailyFragment";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private TabLayout mTabLayout;

    private ZhihuDailyContract.Presenter mPresenter;

    // 年月日
    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    // recycler adapter
    private ZhihuDailyNewsAdapter  mZhihuDailyNewsAdapter;

    public ZhihuDailyFragment() {}

    public static ZhihuDailyFragment newInstance() {
        return new ZhihuDailyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        // 下拉刷新的按钮颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setRippleColor(getResources().getColor(R.color.colorPrimaryDark));

        mTabLayout = (android.support.design.widget.TabLayout) getActivity().findViewById(R.id.tab_layout);

        mPresenter.start();

        //
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            PlaneLog.d(TAG,"swipRefreshlayout");
            mPresenter.refresh();
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private boolean isSlidingToLast = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // 滑动中需要隐藏floatingActionButton
                isSlidingToLast = dy > 0;
                if(isSlidingToLast) {
                    fab.hide();
                } else {
                    fab.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 不滑动
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        Calendar c = Calendar.getInstance();
                        c.set(mYear, mMonth, --mDay);
                        mPresenter.loadMore(c.getTimeInMillis());
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

       // fab.setOnClickListener();
        fab.setOnClickListener(v -> {
            if(mTabLayout.getSelectedTabPosition() == 0) {
                Calendar now = Calendar.getInstance();
                now.set(mYear,mMonth,mDay);
                DatePickerDialog mDatePickerDialog =  DatePickerDialog.newInstance((view1, year, monthOfYear, dayOfMonth) -> {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    Calendar temp = Calendar.getInstance();
                    temp.clear();
                    temp.set(year,monthOfYear,dayOfMonth);
                    mPresenter.loadPosts(temp.getTimeInMillis(),true);
                },mYear,mMonth,mDay);
                mDatePickerDialog.setMaxDate(Calendar.getInstance());
                Calendar minDate = Calendar.getInstance();
                // 知乎日报2013.5.20上线
                minDate.set(2013,5,20);
                mDatePickerDialog.setMinDate(minDate);
                mDatePickerDialog.show(getActivity().getFragmentManager(),"DatePickerDialog");
            } else if(mTabLayout.getSelectedTabPosition() == 2) {
                ViewPager mViewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);
                MainPagerAdapter mainPagerAdapter = (MainPagerAdapter) mViewPager.getAdapter();
                mainPagerAdapter.getDoubanMomentFragment().showPickDialog();
            }
        });
        return view;
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void showError() {
        Snackbar.make(fab,"加载失败",Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", v -> {
                        mPresenter.refresh();
                });
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
        });
    }

    @Override
    public void stopLoading() {
        mSwipeRefreshLayout.post(() -> {
           mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void showResults(ArrayList<ZhihuDailyNews.Question> list) {
       if(mZhihuDailyNewsAdapter == null) {
           mZhihuDailyNewsAdapter = new ZhihuDailyNewsAdapter(getContext(),list);
           mZhihuDailyNewsAdapter.setItemClickListener((v, position) -> {
                mPresenter.startReading(position);
           });
           mRecyclerView.setAdapter(mZhihuDailyNewsAdapter);
       } else {
           mZhihuDailyNewsAdapter.notifyDataSetChanged();
       }
    }


    @Override
    public void initViews(View view) {

    }
}
