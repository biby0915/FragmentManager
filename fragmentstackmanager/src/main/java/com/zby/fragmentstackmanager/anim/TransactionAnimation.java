package com.zby.fragmentstackmanager.anim;

import android.support.v4.app.FragmentTransaction;

/**
 * @author ZhuBingYang
 */
public class TransactionAnimation {
    /**
     * fragment转场动画
     */
    private int mEnterAnim;
    private int mExitAnim;
    private int mPopEnterAnim;
    private int mPopExitAnim;

    public TransactionAnimation(int enterAnim, int exitAnim, int popEnterAnim, int popExitAnim) {
        this.mEnterAnim = enterAnim;
        this.mExitAnim = exitAnim;
        this.mPopEnterAnim = popEnterAnim;
        this.mPopExitAnim = popExitAnim;
    }

    public int getEnterAnim() {
        return mEnterAnim;
    }

    public void setEnterAnim(int enterAnim) {
        this.mEnterAnim = enterAnim;
    }

    public int getExitAnim() {
        return mExitAnim;
    }

    public void setExitAnim(int exitAnim) {
        this.mExitAnim = exitAnim;
    }

    public int getPopEnterAnim() {
        return mPopEnterAnim;
    }

    public void setPopEnterAnim(int popEnterAnim) {
        this.mPopEnterAnim = popEnterAnim;
    }

    public int getPopExitAnim() {
        return mPopExitAnim;
    }

    public void setPopExitAnim(int popExitAnim) {
        this.mPopExitAnim = popExitAnim;
    }

    public void applyAnimation(FragmentTransaction transaction) {
        transaction.setCustomAnimations(mEnterAnim, mExitAnim, mPopEnterAnim, mPopExitAnim);
    }
}
