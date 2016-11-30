package com.skyzone.gank.Main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.skyzone.gank.App;
import com.skyzone.gank.MeiZi.MeiZiFragment;
import com.skyzone.gank.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_toolbar)
    Toolbar mActivityMainToolbar;
    @BindView(R.id.activity_main_tab)
    TabLayout mActivityMainTab;
    @BindView(R.id.activity_main_view_pager)
    ViewPager mActivityMainViewPager;

    MainPagerAdapter mPagerAdapter;
    List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mActivityMainToolbar);

        //init
        mFragments = new ArrayList<>();
        mFragments.add(MainFragment.newInstance(TypeFilter.Android));
        mFragments.add(MainFragment.newInstance(TypeFilter.IOS));
        mFragments.add(MainFragment.newInstance(TypeFilter.Web));
        mFragments.add(MeiZiFragment.newInstance());
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragments);
        mActivityMainViewPager.setAdapter(mPagerAdapter);
        mActivityMainViewPager.setOffscreenPageLimit(0);  //0==1

        mActivityMainTab.setupWithViewPager(mActivityMainViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //设置搜索框hint内容
        mSearchView.setQueryHint("QueryHint...");
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete)
                mSearchView.findViewById(R.id.search_src_text);
        //搜索内容监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private static class MainPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public MainPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return App.mContext.getString(R.string.android);
                case 1:
                    return App.mContext.getString(R.string.ios);
                case 2:
                    return App.mContext.getString(R.string.web);
                case 3:
                    return App.mContext.getString(R.string.meizi);
                default:
                    return App.mContext.getString(R.string.android);
            }
        }
    }
}
