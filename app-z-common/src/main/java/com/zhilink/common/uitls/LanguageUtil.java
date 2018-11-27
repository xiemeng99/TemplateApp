package com.zhilink.common.uitls;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

import com.google.gson.Gson;

import java.util.Locale;

/**
 * 作者：Allen___
 * 链接：https://www.jianshu.com/p/b9e32fad36a2
 * 來源：简书
 * 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
 *
 * @author xiemeng
 * @date 2018-11-23 17:01
 */
public class LanguageUtil {
    /**
     * 中文
     */
    public static final Locale LOCALE_CHINESE = Locale.SIMPLIFIED_CHINESE;
    /**
     * 英文
     */
    public static final Locale LOCALE_ENGLISH = Locale.ENGLISH;
    /**
     * 中文
     */
    public static final Locale LOCALE_TRAN = Locale.TRADITIONAL_CHINESE;

    private static final String LOCALE_SP = "LOCALE_SP";
    private static final String LOCALE_SP_KEY = "LOCALE_SP_KEY";




    public static Locale getLocale(Context context) {
        SharedPreferences spLocale = context.getSharedPreferences(LOCALE_SP, Context.MODE_PRIVATE);
        String localeJson = spLocale.getString(LOCALE_SP_KEY, "");
        Gson gson = new Gson();
        return gson.fromJson(localeJson, Locale.class);
    }


    private static void setLocale(Context pContext, Locale pUserLocale) {
        SharedPreferences spLocal = pContext.getSharedPreferences(LOCALE_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spLocal.edit();
        String json = new Gson().toJson(pUserLocale);
        edit.putString(LOCALE_SP_KEY, json);
        edit.apply();
    }


    public static boolean updateLocale(Context context, Locale locale) {
        if (needUpdateLocale(context, locale)) {
            Configuration configuration = context.getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(locale);
            } else {
                configuration.locale = locale;
            }
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            context.getResources().updateConfiguration(configuration, displayMetrics);
            setLocale(context, locale);
            return true;
        }
        return false;
    }

    public static boolean needUpdateLocale(Context pContext, Locale newUserLocale) {
        return newUserLocale != null && !getCurrentLocale(pContext).equals(newUserLocale);
    }
    public static Locale getCurrentLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0有多语言设置获取顶部的语言
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }

}

