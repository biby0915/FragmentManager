package com.zby.fragmentstackmanager;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.zby.fragmentstackmanager.anim.SlideAnimation;
import com.zby.fragmentstackmanager.anim.TransactionAnimation;
import com.zby.fragmentstackmanager.observer.OnBackStackChangeListener;
import com.zby.fragmentstackmanager.observer.StackChangeType;
import com.zby.fragmentstackmanager.page.IFSFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhuBingYang
 */
public class FragmentStackManager implements Transaction {
    public static final String TAG = "FragmentStackManager";

    /**
     * 直接加载，不做重复检查
     */
    public static final int LOAD_TYPE_STANDARD = 1;

    /**
     * 同名fragment只加载一次
     */
    public static final int LOAD_TYPE_SINGLE = 2;

    /**
     * 栈顶有相同类型fragment时跳过加载
     * 防止连点操作加载多个相同页面
     */
    public static final int LOAD_TYPE_SINGLE_TOP = 3;

    @IntDef({LOAD_TYPE_STANDARD, LOAD_TYPE_SINGLE, LOAD_TYPE_SINGLE_TOP})
    @Retention(RetentionPolicy.SOURCE)
    @interface LoadStrategy {
    }

    /**
     * 加载策略
     */
    private int mLoadStrategy = LOAD_TYPE_STANDARD;

    /**
     * 转场动画
     */
    private TransactionAnimation mTransactionAnimation;


    private FragmentActivity mActivity;
    private FragmentManager mFragmentManager;
    private final int mContainerId;

    /**
     * 加载的页面集合
     */
    private List<IFSFragment> mFragmentStack = new ArrayList<>();

    private List<OnBackStackChangeListener> mBackStackObserverList = new ArrayList<>();


    private boolean debug = true;

    public FragmentStackManager(FragmentActivity activity, int containerId) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
        mContainerId = containerId;

        for (Fragment fragment : mFragmentManager.getFragments()) {
            mFragmentStack.add((IFSFragment) fragment);
        }

        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                List<Fragment> fragments = mFragmentManager.getFragments();

                if (mFragmentStack.size() < fragments.size()) {
                    List<Fragment> addedFragments = fragments.subList(mFragmentStack.size(), fragments.size());
                    for (Fragment fragment : addedFragments) {
                        mFragmentStack.add((IFSFragment) fragment);
                        for (OnBackStackChangeListener listener : mBackStackObserverList) {
                            listener.onStackChanged(fragment, StackChangeType.ADD);
                        }
                        Log("add Fragment:" + fragment.getClass().getSimpleName());
                    }
                } else if (mFragmentStack.size() > fragments.size()) {
                    int count = mFragmentStack.size() - fragments.size();

                    for (int i = 0; i < count; i++) {
                        IFSFragment fragment = mFragmentStack.get(mFragmentStack.size() - 1);
                        for (OnBackStackChangeListener listener : mBackStackObserverList) {
                            listener.onStackChanged((Fragment) fragment, StackChangeType.POP);
                        }
                        mFragmentStack.remove(mFragmentStack.size() - 1);
                        Log("pop Fragment:" + fragment.getClass().getSimpleName());
                    }
                }
            }
        });
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void UseDefaultAnimation() {
        setTransactionAnimation(new SlideAnimation());
    }

    @Override
    public boolean addFragment(Class<? extends IFSFragment> clazz) {
        return addFragment(clazz, null);
    }

    public boolean addFragment(Class<? extends IFSFragment> clazz, Bundle bundle) {
        IFSFragment fragment = (IFSFragment) Fragment.instantiate(mActivity, clazz.getName(), bundle);
        ((Fragment) fragment).setArguments(bundle);
        return addFragment(fragment);
    }

    @Override
    public boolean addFragment(Class<? extends IFSFragment> clazz, Bundle bundle, List<SharedElement> elements) {
        IFSFragment fragment = (IFSFragment) Fragment.instantiate(mActivity, clazz.getName(), bundle);
        ((Fragment) fragment).setArguments(bundle);
        return addFragment(fragment, elements, true);
    }

    @Override
    public boolean addFragment(IFSFragment fragment) {
        return addFragment(fragment, null, true);
    }

    public boolean addFragment(IFSFragment fragment, List<SharedElement> elements, boolean addToBackStack) {
        if (!canLoad(fragment.getClass())) {
            Log("fragment already added:" + ((Fragment) fragment).getClass().getSimpleName());
            return false;
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (getTransactionAnimation() != null) {
            getTransactionAnimation().applyAnimation(transaction);
        }

        if (elements != null) {
            for (SharedElement element : elements) {
                transaction.addSharedElement(element.getView(), element.getName());
            }
            transaction.replace(mContainerId, (Fragment) fragment, fragment.getClass().getSimpleName());
        } else {
            transaction.add(mContainerId, (Fragment) fragment, fragment.getClass().getSimpleName());
        }

        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
        return true;
    }

    @Override
    public boolean addFragmentForResult(Class<? extends IFSFragment> fragment, int requestCode) {
        return addFragmentForResult(fragment, requestCode, null);
    }

    @Override
    public boolean addFragmentForResult(Class<? extends IFSFragment> clazz, int requestCode, Bundle bundle) {
        IFSFragment fragment = (IFSFragment) Fragment.instantiate(mActivity, clazz.getName(), bundle);
        fragment.setRequestCode(requestCode);
        ((Fragment) fragment).setArguments(bundle);
        return addFragment(fragment);
    }

    @Override
    public boolean popFragment() {
        return popFragment(null);
    }

    @Override
    public boolean popFragment(Bundle bundle) {
        if (mFragmentStack.size() > 1) {
            IFSFragment fragment = mFragmentStack.get(mFragmentStack.size() - 1);
            mFragmentManager.popBackStack();
            if (fragment.getRequestCode() != IFSFragment.DEFAULT_REQUEST_CODE) {
                fragment.onFragmentResult(fragment.getRequestCode(), fragment.getResultCode(), bundle);
            } else if (bundle != null) {
                fragment.onNewBundle(bundle);
            }

            return true;
        } else {
            mActivity.finish();
            Log("no fragment to handle bundle");
            return false;
        }
    }

    @Override
    public boolean popToFragment(Class<? extends IFSFragment> clazz) {
        return popToFragment(clazz, null);
    }

    @Override
    public boolean popToFragment(Class<? extends IFSFragment> clazz, Bundle bundle) {
        if (mFragmentStack.size() <= 1) {
            Log("fragment count is less than 2,can't do 'pop to' operation.");
            return false;
        }

        if (mFragmentStack.get(mFragmentStack.size() - 1).getClass() == clazz) {
            Log("make sure it's not the current fragment.");
            return false;
        }

        if (findFragment(clazz) == null) {
            Log("No corresponding fragment was found.");
            return false;
        }

        List<IFSFragment> temp = new ArrayList<>(mFragmentStack);
        for (int i = temp.size() - 1; i >= 0; i--) {
            IFSFragment fragment = temp.get(i);
            if (fragment.getClass() != clazz) {
                popFragment();
            } else {
                if (bundle != null) {
                    fragment.onNewBundle(bundle);
                }
                return true;
            }
        }

        return false;
    }

    /**
     * 是否可以加载页面
     * {@link #LOAD_TYPE_SINGLE} 和 {@link #LOAD_TYPE_SINGLE_TOP} 需要判断不能加载情况
     *
     * @param clazz 加载的页面类型
     */
    private boolean canLoad(Class<? extends IFSFragment> clazz) {
        switch (getLoadStrategy()) {
            case LOAD_TYPE_STANDARD:
            default:
                return true;
            case LOAD_TYPE_SINGLE:
                return findFragment(clazz) == null;
            case LOAD_TYPE_SINGLE_TOP:
                return mFragmentStack.isEmpty()
                        || mFragmentStack.get(mFragmentStack.size() - 1).getClass() != clazz;
        }
    }

    @Override
    public IFSFragment findFragment(Class<? extends IFSFragment> clazz) {
        for (int i = mFragmentStack.size() - 1; i >= 0; i--) {
            IFSFragment fragment = mFragmentStack.get(i);
            if (fragment.getClass() == clazz) {
                return fragment;
            }
        }

        return null;
    }

    public void onBackPressed() {
        popFragment();
    }

    private void Log(String msg) {
        if (isDebug()) {
            Log.d(TAG, msg);
        }
    }

    public void setTransactionAnimation(TransactionAnimation animation) {
        mTransactionAnimation = animation;
    }

    public TransactionAnimation getTransactionAnimation() {
        return mTransactionAnimation;
    }

    public int getLoadStrategy() {
        return mLoadStrategy;
    }

    public void setLoadStrategy(@LoadStrategy int loadStrategy) {
        mLoadStrategy = loadStrategy;
    }


    public void registerBackStackObserver(OnBackStackChangeListener listener) {
        mBackStackObserverList.add(listener);
    }

    public void unregisterBackStackObserver(OnBackStackChangeListener listener) {
        mBackStackObserverList.remove(listener);
    }
}
