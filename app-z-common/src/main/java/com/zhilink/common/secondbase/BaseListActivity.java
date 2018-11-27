package com.zhilink.common.secondbase;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhilink.common.base.BaseTitleActivity;
import com.zhilink.common.dialogs.FailedDialog;
import com.zhilink.common.dialogs.TwoDatePickerDialog;
import com.zhilink.common.net.BaseMethods;
import com.zhilink.common.net.LinkObserver;
import com.zhilink.common.uitls.ModuleUtils;
import com.zhilink.common.uitls.UrlPath;
import com.zhilink.common.view.ViewVisibleAnimator;
import com.zhilink.utils.ViewUtils;

import io.realm.Realm;

/**
 * 基础列表界面
 * 列表筛选功能封装
 * 分页初始化
 *
 * @author xiemeng
 * @date 2018-10-10 09:11
 */

public abstract class BaseListActivity extends BaseTitleActivity {
    /**
     * 分页使用当前页面
     */
    protected int mCurrentPage = 1;
    /**
     * 总笔数
     */
    protected int mCount;
    /**
     * 数据库使用
     */
    protected Realm mRealm;

    protected String mStartDate;

    protected String mEndDate;


    @Override
    protected void titleClick() {
        super.titleClick();
        final View maskView = getMaskView();
        final View condView = getSearchCondView();
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != condView) {
                    int condVisibility = condView.getVisibility();
                    if (condVisibility == View.GONE) {
                        ViewVisibleAnimator.getInstance().animateOpen(condView);
                        condView.setVisibility(View.VISIBLE);
                        if (null != maskView) {
                            maskView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ViewVisibleAnimator.getInstance().animateClose(condView);
                        condView.setVisibility(View.GONE);
                        if (null != maskView) {
                            maskView.setVisibility(View.GONE);
                        }
                    }
                }

            }
        });
        if (null != condView) {
            condView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        if (null != maskView) {
            maskView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewVisibleAnimator.getInstance().animateClose(condView);
                    if (condView != null) {
                        condView.setVisibility(View.GONE);
                    }
                    maskView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    protected void beforeCreateView() {
        super.beforeCreateView();
        mCurrentPage = 1;
        mRealm = Realm.getDefaultInstance();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 双向日期选择
     */
    protected void initTwoDate(final TextView tvDate) {
        tvDate.setKeyListener(null);
        ViewUtils.getViewRightListener(tvDate, new ViewUtils.ClickListener() {
            @Override
            public void click() {
                TwoDatePickerDialog.getInstance().openDatePicker(activity, new TwoDatePickerDialog.GetDateListener() {
                    @Override
                    public void showDate(String startDate, String endDate, String show) {
                        mStartDate = startDate;
                        mEndDate = endDate;
                        tvDate.setText(show);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        });
    }


    /**
     * 搜索条件
     *
     * @return 条件view
     */
    public View getSearchCondView() {
        return null;
    }

    ;

    /**
     * 搜索结果
     *
     * @return 结果 view
     */
    public View getSearchResultView() {
        return null;
    }

    ;

    /**
     * 遮罩
     *
     * @return view
     */
    public View getMaskView() {
        return null;
    }

    ;

    @Override
    public boolean isShowMessage() {
        return true;
    }

    @Override
    public boolean isShowScan() {
        return true;
    }

    @Override
    public boolean isShowSearch() {
        return true;
    }

    @Override
    public boolean isHasExitDialog() {
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
