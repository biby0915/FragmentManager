package com.zby.fragmentstackmanager;

import android.os.Bundle;

import com.zby.fragmentstackmanager.page.IFSFragment;

import java.util.List;

/**
 * @author ZhuBingYang
 * <p>
 * 以下boolean return都是操作是否成功的意思
 */
public interface Transaction {

    boolean addFragment(Class<? extends IFSFragment> clazz);

    /**
     * 通过fragment类名加载
     *
     * @param clazz  FSFragment子类
     * @param bundle 传递数据
     */
    boolean addFragment(Class<? extends IFSFragment> clazz, Bundle bundle);

    boolean addFragment(Class<? extends IFSFragment> clazz, Bundle bundle, List<SharedElement> elements);

    /**
     * 显示一个新的fragment，并加入回退栈
     *
     * @param fragment 需要显示的页面
     */
    boolean addFragment(IFSFragment fragment);


    boolean addFragmentForResult(Class<? extends IFSFragment> fragment, int requestCode);

    /**
     * 启动一个fragment，并要求返回数据
     * 注意requestCode不要和{@link IFSFragment#DEFAULT_REQUEST_CODE}相同,code相同时传递bundle会调用onNewBundle
     * 成功时调用{@link IFSFragment#setResult(int)} 标明操作完成，然后直接{@link #popFragment(Bundle)}即可
     */
    boolean addFragmentForResult(Class<? extends IFSFragment> fragment, int requestCode, Bundle bundle);

    /**
     * 回退一个页面
     */
    boolean popFragment();

    /**
     * fragment退栈，向下层fragment传递一个bundle，调用onNewBundle，如果fragment存在的话
     *
     * @param bundle 回传数据
     * @return 是否返回上一页面成功
     */
    boolean popFragment(Bundle bundle);

    boolean popToFragment(Class<? extends IFSFragment> clazz);

    /**
     * 回退到一个指定类型的页面，如果有多个相同页，返回到最上层的页面
     * 找不到对应的页面，不会做任何操作，返回false
     *
     * @param clazz 回退到页面的指定类型
     */
    boolean popToFragment(Class<? extends IFSFragment> clazz, Bundle bundle);

    /**
     * 根据所给页面类型查找fragment
     * 如果有多个，返回最上层
     *
     * @return 找到的对应的fragment 或 null
     */
    IFSFragment findFragment(Class<? extends IFSFragment> clazz);
}
