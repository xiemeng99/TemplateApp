package com.zhilink.common.net;

import com.zhilink.common.uitls.SharePreferenceCommon;
import com.zhilink.retrofit.RxActionManager;
import com.zhilink.utils.LogUtils;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 增加token
 *
 * @author xiemeng
 * @date 2018-10-9 14:57
 */

public class TokenInterceptor implements Interceptor {

    private static String headerToken = SharePreferenceCommon.getToken();


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .addHeader("token", headerToken)
                .addHeader("header-key", "value2");
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }


    /**
     * 根据Response，判断Token是否失效
     * 401表示token过期
     */
    private static boolean isTokenExpired(Response response) {
        if (response.code() == 401) {
            return true;
        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     */
    public static String getNewToken() throws IOException {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://xxxxx")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        retrofit2.Response<JsonObject> tokenJson = retrofit.create(CommonApi.class).ccbTestGetToken().execute();
//        headerToken = tokenJson.body().get("Token").toString();
        return headerToken;
    }


}
