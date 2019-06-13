package com.zby.fragmentstackmanager.observer;


import android.support.v4.app.Fragment;

/**
 * @author ZhuBingYang
 */
public interface OnBackStackChangeListener {
    void onStackChanged(Fragment fragment, StackChangeType type);
}
