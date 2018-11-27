package com.zhilink.common.base;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.zhilink.common.uitls.LanguageUtil;
import com.zhilink.crashexception.CrashHandler;
import com.zhilink.utils.TelephonyUtils;

import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 定义BaseApplication集成友盟统计等其他工具
 *
 * @author xiemeng
 * @date 2018-8-29 14:59
 */

public class BaseApplication extends Application {
    private static BaseApplication applicationContext;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        initMul();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> str) {
                uM();
                initRouter();
                initRealm();
                languageWork();
            }
        }).subscribeOn(Schedulers.newThread())
            .subscribe();
        registerActivity();
    }

    protected void registerActivity() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
//                CurrentActivityUtils.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }


    protected void initMul() {
    }

    public static BaseApplication getInstance() {
        return applicationContext;
    }

    /**
     * 初始化router路由
     */
    private void initRouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (TelephonyUtils.isApkInDebug(this)) {
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！
            //线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    /**
     * 建立数据库
     * deleteRealmIfMigrationNeeded该方法版本变更时会直接删除旧版本所有数据
     * 如果需要保留序使用MyMigration进行升级配置
     */
    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
            .name("xie_template.realm")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(getCurrentRealmVer())
            .build();
        Realm.setDefaultConfiguration(config);

    }

    /**
     * 友盟统计
     */
    private void uM() {
        MobclickAgent.setScenarioType(applicationContext, MobclickAgent.EScenarioType.E_UM_NORMAL);
        int deviceType = UMConfigure.DEVICE_TYPE_PHONE;
        if (TelephonyUtils.isTabletDevice(applicationContext)) {
            deviceType = UMConfigure.DEVICE_TYPE_BOX;
        }
        //清单文件配置AppKey和渠道，最后一个为推送，暂不集成
        UMConfigure.init(applicationContext, deviceType, "");
    }

    /**
     * 全局异常捕获
     * 太坑了不建议使用
     */
    private void crashException() {
        final CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext(), new CrashHandler.CrashExceptionListener() {
            @Override
            public void crashException(Throwable ex) {
                MobclickAgent.reportError(applicationContext, ex);

            }
        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        languageWork();
    }

    private void languageWork() {
        Locale locale = LanguageUtil.getLocale(this);
        LanguageUtil.updateLocale(this, locale);
    }

    protected long getCurrentRealmVer() {
        return 3;
    }

}
