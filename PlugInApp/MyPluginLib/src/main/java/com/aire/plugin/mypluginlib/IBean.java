package com.aire.plugin.mypluginlib;

public interface IBean {
    void registerCallback(ICallback callback);

    String getName();

    void setName(String paramStr);
}
