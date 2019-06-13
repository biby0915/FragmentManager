package com.zby.fragmentstackmanager.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author ZhuBingYang
 */
public class FSFragment extends Fragment implements IFSFragment {

    private int mRequestCode = DEFAULT_REQUEST_CODE;
    private int mResultCode;

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle bundle) {

    }

    @Override
    public void onNewBundle(Bundle bundle) {

    }

    @Override
    public void setRequestCode(int requestCode) {
        mRequestCode = requestCode;
    }

    @Override
    public int getRequestCode() {
        return mRequestCode;
    }

    @Override
    public void setResult(int resultCode) {
        mResultCode = resultCode;
    }

    @Override
    public int getResultCode() {
        return mResultCode;
    }
}
