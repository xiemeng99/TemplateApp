package com.zhilink.common.bean.local;

import com.zhilink.common.R;
import com.zhilink.common.base.BaseApplication;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * 模块
 *
 * @author xiemeng
 * @date 2018-9-28 15:57
 */
@RealmClass
public class ModuleBean implements RealmModel, Serializable {
    /**
     * 入库
     */
    @Ignore
    public static final String MODULE_TYPE_IN = BaseApplication.getInstance().getString(R.string.app_in);
    /**
     * 出库
     */
    @Ignore
    public static final String MODULE_TYPE_OUT = BaseApplication.getInstance().getString(R.string.app_out);

    /**
     * 库内
     */
    @Ignore
    public static final String MODULE_TYPE_STOCK = BaseApplication.getInstance().getString(R.string.app_stock);

    /**
     * 系统
     */
    @Ignore
    public static final String MODULE_TYPE_SYSTEM = BaseApplication.getInstance().getString(R.string.app_system);
    /**
     * 客制
     */
    @Ignore
    public static final String MODULE_TYPE_CUSTOM = BaseApplication.getInstance().getString(R.string.app_custom);



    @PrimaryKey
    private String moduleNo;

    private String titleName;

    private String routerProperties;

    private String routerPath;

    private String moduleType;

    private int icon;

    private boolean isMostUse;

    private boolean isMyModule;

    private boolean isHasPermission;


    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getModuleNo() {
        return moduleNo;
    }

    public void setModuleNo(String moduleNo) {
        this.moduleNo = moduleNo;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getRouterProperties() {
        return routerProperties;
    }

    public void setRouterProperties(String routerProperties) {
        this.routerProperties = routerProperties;
    }

    public String getRouterPath() {
        return routerPath;
    }

    public void setRouterPath(String routerPath) {
        this.routerPath = routerPath;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public boolean isMostUse() {
        return isMostUse;
    }

    public void setMostUse(boolean mostUse) {
        isMostUse = mostUse;
    }

    public boolean isMyModule() {
        return isMyModule;
    }

    public void setMyModule(boolean myModule) {
        isMyModule = myModule;
    }

    public boolean isHasPermission() {
        return isHasPermission;
    }

    public void setHasPermission(boolean hasPermission) {
        isHasPermission = hasPermission;
    }
}
