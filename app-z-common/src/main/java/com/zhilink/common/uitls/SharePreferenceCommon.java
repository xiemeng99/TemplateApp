package com.zhilink.common.uitls;

import com.zhilink.common.base.BaseApplication;
import com.zhilink.utils.SharedPreferencesUtils;

/**
 * 公共share
 *
 * @author xiemeng
 * @date 2018-10-9 10:32
 */

public class SharePreferenceCommon {
    private static BaseApplication getContext() {
        return BaseApplication.getInstance();
    }

    /**
     * token
     */
    public static void setToken(String isFirst) {
        SharedPreferencesUtils.put(getContext(), "token", isFirst);
    }

    /**
     * token
     */
    public static String getToken() {
        return (String) SharedPreferencesUtils.get(getContext(), "token", "");
    }

    /**
     * url
     */
    public static void setUrl(String url) {
        SharedPreferencesUtils.put(getContext(), "url", url);
    }

    /**
     * url,默认值为asset里面文件
     */
    public static String getUrl() {
        String defaultUrl = ModuleUtils.getProperties(BaseApplication.getInstance(), "serviceUrl", "defaultUrl");
        return (String) SharedPreferencesUtils.get(getContext(), "url", defaultUrl);
    }


    /**
     * 震动开关
     */
    public static void setVibrator(boolean isOpen) {
        SharedPreferencesUtils.put(getContext(), "vibrator", isOpen);
    }

    /**
     * 震动开关
     */
    public static boolean getVibrator() {
        return (boolean) SharedPreferencesUtils.get(getContext(), "vibrator", false);
    }

    /**
     * 分页
     */
    public static void setPage(int page) {
        SharedPreferencesUtils.put(getContext(), "page", page);
    }

    /**
     * 分页
     */
    public static int getPage() {
        return (int) SharedPreferencesUtils.get(getContext(), "page", Constant.PAGE_DEFAULT);
    }


    /**
     * 装箱拆箱使用
     */
    public static void setLoadBox(String boxNo) {
        SharedPreferencesUtils.put(getContext(), "loadBox", boxNo);
    }

    /**
     * 装箱拆箱使用
     */
    public static String getLoadBox() {
        return (String) SharedPreferencesUtils.get(getContext(), "loadBox", "");
    }
}
