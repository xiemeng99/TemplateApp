package com.zhilink.common.uitls;

import com.zhilink.common.base.BaseApplication;

/**
 * url
 *
 * @author xiemeng
 * @date 2018-10-15 11:18
 */

public interface UrlPath {
    /**
     * 公共
     */
    String MAIN_PART = ModuleUtils.getProperties(BaseApplication.getInstance(), "serviceUrl", "mainPort");
    ;

}
