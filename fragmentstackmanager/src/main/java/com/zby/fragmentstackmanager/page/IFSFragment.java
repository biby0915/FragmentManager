package com.zby.fragmentstackmanager.page;


import android.os.Bundle;

/**
 * @author ZhuBingYang
 */
public interface IFSFragment {
    int RESULT_CANCELED = 0;
    int RESULT_OK = 1;

    int DEFAULT_REQUEST_CODE = -1000;

    void onFragmentResult(int requestCode, int resultCode, Bundle bundle);

    void onNewBundle(Bundle bundle);

    void setRequestCode(int requestCode);

    int getRequestCode();

    void setResult(int resultCode);

    int getResultCode();
}
