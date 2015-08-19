package com.library.yang.eliyet.eliyetlibrarydemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.library.yang.eliyet.elibrary.watched.Titles;

import java.util.Observable;
import java.util.Observer;


public class SlidingTabFragment extends Fragment implements Observer {

    private static int mIndex = 0;
    private int mColor;
    private int textColor;

    private TextView mContent;

    private String mLastContent;


    public SlidingTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int src = 0x40;
        int model = mIndex % 3;
        int r = 0;
        int g = 0;
        int b = 0;
        int a = 0xff000000;
        int mul = mIndex / 3 + 1;
        switch (model) {
            case 0:
                r = src * mul << 16 & 0xff0000;
                break;
            case 1:
                g = src * mul << 8 & 0x00ff00;
                break;
            case 2:
                b = src * mul & 0x0000ff;
                break;
            default:
                break;
        }

        mColor = a + r + g + b;
        textColor = (~(r + g + b)) + a;
        mIndex++;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sliding_tab, container, false);
        view.setBackgroundColor(mColor);
        mContent = (TextView) view.findViewById(R.id.fragment_sliding_tab_content);
        mContent.setTextColor(textColor);
        if (!TextUtils.isEmpty(mLastContent)) {
            mContent.setText(mLastContent);
        }
        return view;
    }

    @Override
    public void update(Observable observable, Object data) {
        //refresh content when the watched has changed
        //you can add some conditions to decide your action.
        //ex:add a member of index to determine witch title has changed then you should do what.
        if (Titles.class.isInstance(observable)) {
            Titles titles = (Titles) observable;
            int position = (int) data;
            String text = titles.getTitleByIndex(position);
            if (mContent != null) {
                mContent.setText(text);
            }
            mLastContent = text;//when fragment view is not create.save the new content and set it after onCreateView
        }
    }
}
