package com.zby.fragmentstackmanager.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zby.fragmentstackmanager.FragmentStackManager;

/**
 * @author ZhuBingYang
 */
public abstract class FSActivity extends AppCompatActivity implements FragmentHost {
    private FragmentStackManager mFsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFsManager = initFSManager();
    }

    protected abstract FragmentStackManager initFSManager();

    @Override
    public FragmentStackManager getFSManager() {
        return mFsManager;
    }

    @Override
    public void onBackPressed() {
        mFsManager.onBackPressed();
    }
}
