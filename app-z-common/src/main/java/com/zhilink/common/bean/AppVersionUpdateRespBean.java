package com.zhilink.common.bean;

/**
 * 版本更新
 *
 * @author xiemeng
 * @date 2018-11-20 16:04
 */
public class AppVersionUpdateRespBean{

    private String versionName;


    private String downloadUrl;


    private String updateInfo;


    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }
}
