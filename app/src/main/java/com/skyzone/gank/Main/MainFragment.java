package com.skyzone.gank.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyzone.gank.Data.Bean.Info;
import com.skyzone.gank.Data.Result;
import com.skyzone.gank.Detail.DetailActivity;
import com.skyzone.gank.R;
import com.skyzone.gank.RetrofitWrapper;
import com.skyzone.gank.Util.RxJava.RxUtil;
import com.skyzone.gank.Util.RxJava.WebTrueAction;
import com.skyzone.gank.View.DiverRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Skyzone on 11/24/2016.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView mFragmentMainRecyclerView;
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
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void onResume() {
        super.onResume();

        switch (mFilter) {
            case Android:
                Subscription s_android = RetrofitWrapper.Instance.getDemoApi().getInfoAndroid(1)
                        .compose(RxUtil.<Result<List<Info>>>normalSchedulers())
                        .subscribe(new WebTrueAction<Result<List<Info>>>() {
                            @Override
                            public void onSuccess(Result<List<Info>> listResult) {
                                mAdapter.refresh(listResult.results);
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
                            }
                        });
                mSubscription.add(s_web);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        mSubscription.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mFilter = (TypeFilter) getArguments().getSerializable("type");
        mFragmentMainRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mFragmentMainRecyclerView.setAdapter(mAdapter);
        mFragmentMainRecyclerView.addItemDecoration(new DiverRecyclerView(getContext()));
        return view;
    }
}
