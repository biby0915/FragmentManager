package com.zby.fragmentmanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zby.fragmentstackmanager.FragmentStackManager;
import com.zby.fragmentstackmanager.observer.OnBackStackChangeListener;
import com.zby.fragmentstackmanager.observer.StackChangeType;
import com.zby.fragmentstackmanager.page.FragmentHost;

public class MainActivity extends AppCompatActivity implements FragmentHost {

    private FragmentStackManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.addFragment(B.class);
            }
        });

        manager = new FragmentStackManager(this, R.id.frame);
        manager.UseDefaultAnimation();

        manager.registerBackStackObserver(new OnBackStackChangeListener() {
            @Override
            public void onStackChanged(Fragment fragment, StackChangeType type) {
                System.out.println(fragment + "   " + type);
            }
        });
    }

    @Override
    public void onBackPressed() {
        manager.onBackPressed();
    }

    @Override
    public FragmentStackManager getFSManager() {
        return manager;
    }
}
