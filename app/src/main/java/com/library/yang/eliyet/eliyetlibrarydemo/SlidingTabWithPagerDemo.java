package com.library.yang.eliyet.eliyetlibrarydemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.library.yang.eliyet.elibrary.adapter.ViewPagerAdapter;
import com.library.yang.eliyet.elibrary.widget.SlidingTabLayout;

public class SlidingTabWithPagerDemo extends AppCompatActivity {
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private Class<Fragment>[] mClasses = new Class[]{SlidingTabFragment.class, SlidingTabFragment.class, SlidingTabFragment.class, SlidingTabFragment.class, SlidingTabFragment.class};
    private String[] mTitles = new String[]{"first", "second", "third", "fourth", "fifth"};
    private EditText mNewTitle;
    private EditText mNewIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tab_with_pager_demo);
        mNewIndex = (EditText) findViewById(R.id.sliding_tab_index);
        mNewTitle = (EditText) findViewById(R.id.sliding_tab_title);

        findViewById(R.id.sliding_tab_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mNewIndex.getText().toString())) {
                    int index = Integer.valueOf(mNewIndex.getText().toString());
                    mViewPagerAdapter.setTitleByIndex(mNewTitle.getText().toString(), index);
                }
            }
        });

        initSliding();
    }

    private void initSliding() {
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab_group);
        mViewPager = (ViewPager) findViewById(R.id.sliding_tab_pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles, mClasses, mSlidingTabLayout);

        mViewPager.setAdapter(mViewPagerAdapter);


        mSlidingTabLayout.setCustomTabView(R.layout.sliding_tab_layout, R.id.sliding_tab_layout_title); //do not set tabView's background.it will make bottom indicator invisible.
        mSlidingTabLayout.setSelectedIndicatorColors(Color.WHITE);
        mSlidingTabLayout.setViewPager(mViewPager); //you should do this action after setting pageAdapter or you will get a NullPointException.
        SlidingTabFragment slidingTabFragment = (SlidingTabFragment) mViewPagerAdapter.getFragmentByIndex(3);
        mViewPagerAdapter.addTitleObserver(slidingTabFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sliding_tab_with_pager_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
