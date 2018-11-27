package com.xie.template.base;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.zhilink.common.base.BaseApplication;

/**
 * @author xiemeng
 * @des
 * @date 2018-10-9 10:36
 */

public class AppApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void initMul() {
        super.initMul();
    }

    @Override
    protected long getCurrentRealmVer() {
        return 2;
    }
}
