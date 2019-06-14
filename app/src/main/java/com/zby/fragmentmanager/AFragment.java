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
public class AFragment extends FSFragment {
    private Activity mActivity;


    public View view;
    public TextView tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment, null);

        tv = view.findViewById(R.id.tv);
        tv.setBackgroundColor(Color.parseColor("#FF" + String.format(Locale.getDefault(), "%06d", new Random().nextInt(999999))));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().addFragment(B.class);
            }
        });

        mActivity = getActivity();
        if (getArguments() != null) {
            toast(getArguments().toString());
        }


        init();

        return view;
    }

    protected void init(){
        view.findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().addFragment(B.class);
            }
        });

        view.findViewById(R.id.b2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("a", "aaaa");
                getManager().addFragment(B.class, bundle);
            }
        });

        view.findViewById(R.id.b3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("a", "aaaa");
                getManager().addFragmentForResult(B.class, 1, bundle);
            }
        });

        view.findViewById(R.id.b4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().popFragment();
            }
        });

        view.findViewById(R.id.b5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("a", "bbbb");
                getManager().popFragment(bundle);
            }
        });

        view.findViewById(R.id.b6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("a", "aaaa");
                setResult(RESULT_OK);
                getManager().popFragment(bundle);
            }
        });

        view.findViewById(R.id.b7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().addFragment(B.class);
            }
        });

        view.findViewById(R.id.b8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().addFragment(C.class);
            }
        });

        view.findViewById(R.id.b9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().addFragment(A.class);
            }
        });
        view.findViewById(R.id.b10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().addFragment(B.class);
            }
        });
        view.findViewById(R.id.b11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().addFragment(C.class);
            }
        });
        view.findViewById(R.id.b12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().addFragment(D.class);
            }
        });
        view.findViewById(R.id.b13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().popToFragment(A.class);
            }
        });
        view.findViewById(R.id.b14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().popToFragment(B.class);
            }
        });
        view.findViewById(R.id.b15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().popToFragment(C.class);
            }
        });
        view.findViewById(R.id.b16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().popToFragment(D.class);
            }
        });
    }



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
