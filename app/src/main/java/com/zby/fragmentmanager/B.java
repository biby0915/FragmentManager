package com.zby.fragmentmanager;

import android.os.Bundle;
import android.view.View;

/**
 * @author ZhuBingYang
 */
public class B extends AFragment {

    @Override
    protected View.OnClickListener getListener() {
        view.findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().addFragment(B.this.getClass());
            }
        });

        view.findViewById(R.id.b2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("a", "aaaa");
                getManager().addFragment(B.this.getClass(), bundle);
            }
        });

        view.findViewById(R.id.b3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("a", "aaaa");
                getManager().addFragmentForResult(B.this.getClass(), 1, bundle);
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

//        view.findViewById(R.id.b9).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getManager().addFragmentNoBackStack(C.class);
//            }
//        });


        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManager().addFragment(B.this.getClass());
            }
        };
    }

    @Override
    protected String getShowText() {
        return "B";
    }
}
