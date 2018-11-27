package com.zhilink.common.uitls;

/**
 * 模组编号
 *
 * @author xiemeng
 * @date 2018-9-29 16:34
 */
public interface ModuleCode {
    /**
     * 打开更多模组
     */
    String MORE_USE_MODULE = "MoreUseModule";

    /**
     * 登陆设置使用
     */
    String OTHER = "other";

    /**
     * 收货
     */
    String ARRIVE_IN = "A001";
    /**
     * 快速收货
     */
    String FAST_ARRIVE_IN = "A002";
    /**
     * 质检
     */
    String QC = "A003";
    /**
     * 入库申请
     */
    String IN_STORE_APPLY = "A004";
    /**
     * 入库上架
     */
    String IN_STORE_ON_SHELF = "A005";
    /**
     * 容器上架
     */
    String CONTAINER = "A006";
    /**
     * 调拨入库
     */
    String ALLOT_IN_STORE = "A007";


    /**
     * 拣货任务
     */
    String PICK_TASK = "C001";

    /**
     * 拣货分拨
     */
    String PICK_ALLOCATE = "C002";

    /**
     * 直通分拨
     */
    String DIRECT_ALLOCATE = "C003";

    /**
     * 月台移储
     */
    String PLATFORM_MOVE = "C004";
    /**
     * 搬运
     */
    String CARRY = "C005";
    /**
     * 装车
     */
    String LOAD_CAR="C006";
    /**
     * 计划盘点
     */
    String PLAN_CHECK = "D001";
    /**
     * 移库下架
     */
    String TRANSFER_UNDERCARRIAGE = "D002";
    /**
     * 移库上架
     */
    String TRANSFER_ON_SHELF = "D003";
    /**
     * 一步移库
     */
    String ONE_STEP_MOVE = "D004";

    /**
     * 即时移库
     */
    String IMMEDIATE_MOVE_STORE = "D005";

    /**
     * 装箱
     */
    String LOAD_BOX = "D006";

    /**
     * 拆箱
     */
    String UNPACK_BOX = "D007";
    /**
     * 实时盘点
     */
    String REAL_TIME_CHECK = "D008";
    /**
     * 库存查询
     */
    String STORE_SEARCH="D009";

}
