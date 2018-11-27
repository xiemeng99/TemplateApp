package com.zhilink.common.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * fragment公用adapter
 *
 * @author xiemeng
 * @date 2018-10-10
 */
public class ModuleViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> strings;

    public ModuleViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> strings) {
        super(fm);
        this.fragments = fragments;
        this.strings = strings;
    }

    public ModuleViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }
}
