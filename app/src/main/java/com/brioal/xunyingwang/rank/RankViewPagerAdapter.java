package com.brioal.xunyingwang.rank;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.brioal.xunyingwang.douban.DouBanFragment;
import com.brioal.xunyingwang.recenthot.RecentHotFragment;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/8/1.
 */

public class RankViewPagerAdapter extends FragmentStatePagerAdapter {

    public RankViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? RecentHotFragment.getInstance() : DouBanFragment.getInstance();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "最近热门" : "豆瓣TOP250";
    }
}
