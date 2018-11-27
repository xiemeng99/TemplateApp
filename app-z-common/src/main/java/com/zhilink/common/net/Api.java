package com.zhilink.common.net;


import com.zhilink.common.uitls.SharePreferenceCommon;
import com.zhilink.common.uitls.UrlPath;
import com.zhilink.retrofit.RetrofitUtils;

/**
 * WMS进一步封装网络请求
 * 如SRM等其他平台新写一个Api即可
 *
 * @author xiemeng
 * @date 2018-8-24 11:59
 */

public class Api {

    private static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    new Api();
                }
            }
        }
        return apiService;
    }


    private Api() {
        RetrofitUtils baseApi = new RetrofitUtils();
        String baseUrl = SharePreferenceCommon.getUrl()+ UrlPath.MAIN_PART;
        apiService = baseApi.getRetrofit(baseUrl, new TokenInterceptor()).create(ApiService.class);
    }

}
