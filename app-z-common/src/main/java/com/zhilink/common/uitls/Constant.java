package com.zhilink.common.uitls;

import android.Manifest;
import android.os.Environment;

/**
 * 存储常量interface
 *
 * @author xiemeng
 * @date 2018-9-11 09:18
 */

public interface Constant {
    /**
     * activity传递对象使用
     */
    String ITEM_DATA_KEY = "itemDataKey";

    /**
     * activity传递对象使用
     */
    String ITEM_DATA_KEY2 = "itemDataKey2";
    /**
     * 权限请求码
     */
    int PERMISSIONS_CODE = 100;

    /**
     * 调用相机扫描使用
     */
    String[] PERMISSIONS_SCAN = {
        Manifest.permission.CAMERA};
    /**
     * 登陆时统一获取
     */
    String[] PERMISSIONS_LOGIN = {
        Manifest.permission.WRITE_SETTINGS,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.ACCESS_WIFI_STATE};
    /**
     * start Activity For result 使用
     */
    int ACTIVITY_RESULT_CODE_1 = 10001;

    /**
     * start Activity For result 使用
     */
    int ACTIVITY_RESULT_CODE_2 = 10002;

    /**
     * 分页设置-最小页
     */
    int PAGE_MINI = 1;
    /**
     * 分页设置-最大页
     */
    int PAGE_MAX = 100;
    /**
     * 分页设置-默认页
     */
    int PAGE_DEFAULT = 10;

    /**
     * 入库上架容器类型
     */
    String CONTAINER_TYPE_IN = "20";
    /**
     * 移库下架容器类型
     */
    String CONTAINER_TYPE_MOVE = "80";

    /**
     * 智互联文件存储
     */
    interface LocalFilePath {
        /**
         * 根目录
         */
        String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "A-ZHL";
        /**
         * 下载好的安装包路径
         */
        String APK_PATH = ROOT_PATH + "/" + "apk";
        /**
         * 日志,暂未使用
         */
        String LOGS_PATH = ROOT_PATH + "/" + "logs";
    }
}
