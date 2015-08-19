package com.library.yang.eliyet.elibrary.watched;

import java.util.Observable;

/**
 * Created by eliyetyang on 15-8-17.
 */
public class Titles extends Observable {
    private String mTitle[];

    public Titles(String[] title) {
        this.mTitle = title;
    }

    public int size() {
        return mTitle.length;
    }

    public String[] getTitles() {
        return mTitle;
    }

    public String getTitleByIndex(int index) {
        if (index >= mTitle.length) {
            return null;
        }
        return mTitle[index];
    }

    public void setTitleByIndex(String title, int index) {
        if (title == null) {
            title = "";
        }
        if (!mTitle[index].equals(title)) {
            mTitle[index] = title;
            setChanged();
            notifyObservers(index);
        }
    }
}
