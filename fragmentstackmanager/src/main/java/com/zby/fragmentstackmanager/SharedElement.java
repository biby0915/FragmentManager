package com.zby.fragmentstackmanager;

import android.view.View;

/**
 * @author ZhuBingYang
 */
public class SharedElement {
    private View view;
    private String name;

    public SharedElement(View view, String name) {
        this.view = view;
        this.name = name;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SharedElement{" +
                "view=" + view +
                ", name='" + name + '\'' +
                '}';
    }
}
