package com.skyzone.gank.MeiZi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.elvishew.xlog.XLog;
import com.skyzone.gank.Base.LazyFragment;
import com.skyzone.gank.Data.Bean.MeiZi;
import com.skyzone.gank.Data.Bean.Video;
import com.skyzone.gank.Data.Result;
import com.skyzone.gank.R;
import com.skyzone.gank.RetrofitWrapper;
import com.skyzone.gank.Util.DateUtil;
import com.skyzone.gank.Util.RxJava.RxUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Skyzone on 11/30/2016.
 */
public class MeiZiFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = MeiZiFragment.class.getSimpleName();

    @BindView(R.id.fragment_meizi_recycler_view)
    RecyclerView mFragmentMeiziRecyclerView;
    @BindView(R.id.fragment_meizi_refresh_layout)
    SwipeRefreshLayout mFragmentMeiziRefreshLayout;

    private MeiZiAdapter mMeiZiAdapter;

    private CompositeSubscription mSubscription;

    public MeiZiFragment() {
    }

    public static MeiZiFragment newInstance() {
        MeiZiFragment meiziFragment = new MeiZiFragment();
        return meiziFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMeiZiAdapter = new MeiZiAdapter(new ArrayList<MeiZi>(0));
    }


    @Override
    public void onPause() {
        super.onPause();

        mSubscription.unsubscribe();
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_meizi;
    }

    @Override
    protected void initView() {
        mFragmentMeiziRefreshLayout.setColorSchemeResources(new int[]{android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light});
        mFragmentMeiziRefreshLayout.setOnRefreshListener(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mFragmentMeiziRecyclerView.setLayoutManager(layoutManager);
        mFragmentMeiziRecyclerView.setAdapter(mMeiZiAdapter);
    }

    @Override
    protected void loadData() {
        XLog.d("get meizi data");
        mFragmentMeiziRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mFragmentMeiziRefreshLayout.setRefreshing(true);
            }
        });
        mSubscription = new CompositeSubscription();
        onRefresh();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        //viewPager的第一个Fragment数据加载
//        if (getUserVisibleHint()) {
//            updateData();
//        }
//    }

    @Override
    public void onRefresh() {
        Subscription ss = Observable.zip(RetrofitWrapper.Instance.getDemoApi().getMeiZi(1), RetrofitWrapper.Instance.getDemoApi().getVideo(1), new Func2<Result<List<MeiZi>>, Result<List<Video>>, List<MeiZi>>() {
            @Override
            public List<MeiZi> call(Result<List<MeiZi>> listResult, Result<List<Video>> listResult2) {
                for (MeiZi meizi : listResult.results) {
                    for (Video v : listResult2.results) {
                        if (DateUtil.isSameDay(meizi.getPublishedAt(), v.getPublishedAt())) {
                            meizi.setDesc(meizi.getDesc() + "  " + v.getDesc());
                            break;
                        }
                    }
                }
                return listResult.results;
            }
        })
                .flatMap(new Func1<List<MeiZi>, Observable<MeiZi>>() {
                    @Override
                    public Observable<MeiZi> call(List<MeiZi> meiZiList) {
                        return Observable.from(meiZiList);
                    }
                })
                .toSortedList(new Func2<MeiZi, MeiZi, Integer>() {
                    @Override
                    public Integer call(MeiZi meiZi, MeiZi meiZi2) {
                        return meiZi2.getPublishedAt().compareTo(meiZi.getPublishedAt());
                    }
                })
                .compose(RxUtil.<List<MeiZi>>normalSchedulers())
                .subscribe(new Action1<List<MeiZi>>() {
                    @Override
                    public void call(List<MeiZi> meiZiList) {
                        mMeiZiAdapter.refresh(meiZiList);
                        mFragmentMeiziRefreshLayout.setRefreshing(false);
                    }
                });
        mSubscription.add(ss);
    }
}
