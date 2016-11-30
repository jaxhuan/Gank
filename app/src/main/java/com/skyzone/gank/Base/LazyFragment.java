package com.skyzone.gank.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 当fragment对用户可见的时候，再加载数据,用于ViewPager.
 * Created by Skyzone on 11/30/2016.
 */
public abstract class LazyFragment extends Fragment {

    private boolean isCreateView = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(setLayout(), container, false);
        ButterKnife.bind(this, view);
        initView();
        isCreateView = true;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreateView)
            loadData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint())
            loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected abstract int setLayout();

    protected abstract void initView();

    protected abstract void loadData();
}
