package com.library.yang.eliyet.elibrary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.TextView;

import com.library.yang.eliyet.elibrary.watched.Titles;
import com.library.yang.eliyet.elibrary.widget.SlidingTabLayout;

import java.util.Observable;
import java.util.Observer;

public class ViewPagerAdapter extends FragmentPagerAdapter implements Observer {


    private Titles mTitles;
    private Class<Fragment>[] mFragmentClasses;
    private Fragment[] mFragments;
    private SlidingTabLayout mSlidingTabLayout;

    public ViewPagerAdapter(FragmentManager fm, String[] titles, Class<Fragment>[] fragmentClasses, SlidingTabLayout slidingTabLayout) {
        this(fm, titles, new Fragment[fragmentClasses.length], slidingTabLayout);
        mFragmentClasses = fragmentClasses;
    }

    public ViewPagerAdapter(FragmentManager fm, String[] titles, Fragment[] fragments, SlidingTabLayout slidingTabLayout) {
        super(fm);
        mSlidingTabLayout = slidingTabLayout;
        mFragments = fragments;
        checkTitles(mFragments.length, titles);
    }

    public void checkTitles(int total, String[] titles) {
        if (titles.length < total) {
            Log.w("ViewPagerAdapter", "fragment count not same with title count");
            titles = new String[total];
            for (int i = 0; i < titles.length; i++) {
                if (i < titles.length) {
                    titles[i] = titles[i];
                } else {
                    titles[i] = "" + i;
                }
            }
        }
        mTitles = new Titles(titles);
        mTitles.addObserver(this);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        try {
            if (mFragments[position] == null) {
                if (mFragmentClasses == null || mFragmentClasses[position] == null) {
                    return null;
                }
                if (mFragments[position] == null) {
                    mFragments[position] = mFragmentClasses[position].newInstance();
                }
            }
            fragment = mFragments[position];
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    public CharSequence getPageTitle(int position) {
        return mTitles.getTitleByIndex(position);
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (Titles.class.isInstance(observable)) {
            Titles titles = (Titles) observable;
            int position = (int) data;
            String text = titles.getTitleByIndex(position);
            TextView view = mSlidingTabLayout.getTabTitleViewByPosition(position);
            if (view != null) {
                view.setText(text);
            }
        }
    }

    public void setTitleByIndex(String title, int index) {
        //increase other action to make this method work as a decorator.
        mTitles.setTitleByIndex(title, index);
    }

    public void addTitleObserver(Observer observer) {
        //add a watcher for titles;
        mTitles.addObserver(observer);
    }

    public Fragment getFragmentByIndex(int index) {
        if (mFragments[index] == null) {
            try {
                mFragments[index] = mFragmentClasses[index].newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return mFragments[index];
    }
}