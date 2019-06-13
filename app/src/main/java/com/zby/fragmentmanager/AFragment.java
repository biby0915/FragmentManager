package com.zby.fragmentmanager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zby.fragmentstackmanager.FragmentStackManager;
import com.zby.fragmentstackmanager.page.FSFragment;
import com.zby.fragmentstackmanager.page.FragmentHost;

import java.util.Locale;
import java.util.Random;

/**
 * @author ZhuBingYang
 */
public abstract class AFragment extends FSFragment {
    private Activity mActivity;


    public View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment, null);
        TextView tv = view.findViewById(R.id.tv);

        tv.setBackgroundColor(Color.parseColor("#FF" + String.format(Locale.getDefault(), "%06d", new Random().nextInt(999999))));
        tv.setText(getShowText());
        tv.setOnClickListener(getListener());

        if (getArguments() != null) {
            toast(getArguments().toString());
        }

        mActivity = getActivity();
        return view;
    }

    protected abstract View.OnClickListener getListener();

    protected abstract String getShowText();


    public FragmentStackManager getManager() {
        return ((FragmentHost) getActivity()).getFSManager();
    }

    @Override
    public void onNewBundle(Bundle bundle) {
        super.onNewBundle(bundle);
        if (bundle == null) {
            toast("onNewBundle  bundle null");
        } else {
            toast("onNewBundle:" + bundle.toString());
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle bundle) {
        super.onFragmentResult(requestCode, resultCode, bundle);

        toast("requestCode:" + requestCode + " resultCode:" + resultCode
                + (bundle == null ? "  null" : "  " + bundle.toString()));
    }

    public void toast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        Log.d("qwerty", msg);
    }
}
