package com.aire.plugin.pluginbundle;

import com.aire.plugin.mypluginlib.IBean;
import com.aire.plugin.mypluginlib.ICallback;

public class Bean implements IBean {
    private String mName;
    private ICallback mCallback;

    @Override
    public void registerCallback(ICallback callback) {
        mCallback = callback;

        clickButton();
    }

    private void clickButton() {
        mCallback.sendResult("Hello, " + mName);
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public void setName(String paramStr) {
        mName = paramStr;
    }
}
