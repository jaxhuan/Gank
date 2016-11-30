package com.skyzone.gank.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.elvishew.xlog.XLog;
import com.skyzone.gank.Base.LazyFragment;
import com.skyzone.gank.Data.Bean.Info;
import com.skyzone.gank.Data.Result;
import com.skyzone.gank.Detail.DetailActivity;
import com.skyzone.gank.R;
import com.skyzone.gank.RetrofitWrapper;
import com.skyzone.gank.Util.RxJava.RxUtil;
import com.skyzone.gank.Util.RxJava.WebFalseActionUI;
import com.skyzone.gank.Util.RxJava.WebTrueAction;
import com.skyzone.gank.View.DiverRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Skyzone on 11/24/2016.
 */
public class MainFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView mFragmentMainRecyclerView;
    @BindView(R.id.fragment_main_refresh_layout)
    SwipeRefreshLayout mFragmentMainRefreshLayout;
    TypeFilter mFilter;
    MainAdapter mAdapter;
    Intent mIntent;

    private CompositeSubscription mSubscription;

    public MainFragment() {
    }

    public static MainFragment newInstance(TypeFilter typeFilter) {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", typeFilter);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilter = (TypeFilter) getArguments().getSerializable("type");
        mAdapter = new MainAdapter(getContext(), new ArrayList<Info>(0));

        mAdapter.setItemListener(new MainAdapter.InfoItemListener() {
            @Override
            public void onInfoClick(Info info) {
                mIntent = new Intent(getActivity(), DetailActivity.class);
                mIntent.putExtra("info", info);
                startActivity(mIntent);
            }

            @Override
            public void onInfoImgClick(Info info) {
                mIntent = new Intent(getActivity(), DetailActivity.class);
                mIntent.putExtra("info", info);
                startActivity(mIntent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        XLog.d("pause:" + mFilter.name());

        mSubscription.unsubscribe();
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        mFragmentMainRefreshLayout.setColorSchemeResources(new int[]{android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light});
        mFragmentMainRefreshLayout.setOnRefreshListener(this);
        mFragmentMainRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mFragmentMainRecyclerView.setAdapter(mAdapter);
        mFragmentMainRecyclerView.addItemDecoration(new DiverRecyclerView(getContext()));
    }

    @Override
    protected void loadData() {
        XLog.d("load data:" + mFilter.name());

        mFragmentMainRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mFragmentMainRefreshLayout.setRefreshing(true);
            }
        });
        mSubscription = new CompositeSubscription();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        switch (mFilter) {
            case Android:
                Subscription s_android = RetrofitWrapper.Instance.getDemoApi().getInfoAndroid(1)
                        .compose(RxUtil.<Result<List<Info>>>normalSchedulers())
                        .subscribe(new WebTrueAction<Result<List<Info>>>() {
                            @Override
                            public void onSuccess(Result<List<Info>> listResult) {
                                mAdapter.refresh(listResult.results);
                                mFragmentMainRefreshLayout.setRefreshing(false);
                            }
                        }, new WebFalseActionUI() {
                            @Override
                            public void onFailed() {
                                mFragmentMainRefreshLayout.setRefreshing(false);
                            }
                        });
                mSubscription.add(s_android);
                break;
            case IOS:
                Subscription s_ios = RetrofitWrapper.Instance.getDemoApi().getInfoIOS(1)
                        .compose(RxUtil.<Result<List<Info>>>normalSchedulers())
                        .subscribe(new WebTrueAction<Result<List<Info>>>() {
                            @Override
                            public void onSuccess(Result<List<Info>> listResult) {
                                mAdapter.refresh(listResult.results);
                                mFragmentMainRefreshLayout.setRefreshing(false);
                            }
                        }, new WebFalseActionUI() {
                            @Override
                            public void onFailed() {
                                mFragmentMainRefreshLayout.setRefreshing(false);
                            }
                        });
                mSubscription.add(s_ios);
                break;
            case Web:
                Subscription s_web = RetrofitWrapper.Instance.getDemoApi().getInfoWeb(1)
                        .compose(RxUtil.<Result<List<Info>>>normalSchedulers())
                        .subscribe(new WebTrueAction<Result<List<Info>>>() {
                            @Override
                            public void onSuccess(Result<List<Info>> listResult) {
                                mAdapter.refresh(listResult.results);
                                mFragmentMainRefreshLayout.setRefreshing(false);
                            }
                        }, new WebFalseActionUI() {
                            @Override
                            public void onFailed() {
                                mFragmentMainRefreshLayout.setRefreshing(false);
                            }
                        });
                mSubscription.add(s_web);
                break;
        }
    }
}
