package com.zhilink.common.uitls;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.method.TextKeyListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zhilink.common.R;
import com.zhilink.utils.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

/**
 * 具体常用工具
 *
 * @author xiemeng
 * @date 2018-9-13 09:19
 */

public class ModuleUtils {

    /**
     * 读取asset 中properties文件某个属性
     *
     * @param context        context
     * @param propertiesName 文件名含扩展名
     * @param path           属性名
     * @return 返回
     */
    public static String getProperties(Context context, String propertiesName, String path) {
        Properties properties = new Properties();
        try {
            properties.load(context.getAssets().open(propertiesName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties.getProperty(path);
    }

    public static boolean isCanRequest = true;

    /**
     * 是否为enter键,并且不为空，目前模拟器存在多次触发
     */
    public static boolean isEnter(EditText v, int actionId, KeyEvent keyEvent) {
        if (isCanRequest && v.hasFocus() && !StringUtils.isBlank(v.getText().toString().trim())) {
            return
                actionId == EditorInfo.IME_ACTION_GO ||
                    (keyEvent != null && KeyEvent.KEYCODE_ENTER ==
                        keyEvent.getKeyCode() && KeyEvent.ACTION_DOWN == keyEvent.getAction());
        } else {
            return false;
        }
    }

    /**
     * 主动触发enter事件
     */
    public final static int ACTION_ID = EditorInfo.IME_ACTION_GO;

    /**
     * 清空栏位
     *
     * @param editTexts 需要被清空的栏位
     */
    public static void clearEditTexts(List<EditText> editTexts) {
        for (EditText editText : editTexts) {
            editText.setText("");
        }
    }

    public static String getTextString(TextView textView) {
        return textView.getText().toString().trim();
    }

    public static boolean isBlank(TextView textView) {
        String textString = getTextString(textView);
        return StringUtils.isBlank(textString);
    }

    /**
     * TextView Change
     */
    public static void tvChange(Context context, List<EditText> editTexts, List<TextView> list, TextView... tvs) {
        if (list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTextColor(ContextCompat.getColor(context, R.color.main_local_use_color));
        }
        for (int i = 0; i < editTexts.size(); i++) {
            editTexts.get(i).setTextColor(ContextCompat.getColor(context, R.color.main_local_use_color));
        }
        TypedArray a = context.obtainStyledAttributes(new int[]{
            R.color.local_text_choose_color
        });
        if (null != tvs) {
            for (int i = 0; i < tvs.length; i++) {
                if (null != tvs[i]) {
                    tvs[i].setTextColor(a.getColor(0, ContextCompat.getColor(context, R.color.local_text_choose_color)));
                }
            }
        }
        a.recycle();
    }

    /**
     * 设置触发事件
     */
    public static void setAction(EditText editText) {
        if (null != editText) {
            editText.onEditorAction(ACTION_ID);
        }
    }

    /**
     * 设置输入框不可输入
     */
    public static void setCantInput(EditText editText) {
        editText.setKeyListener(null);
    }

    /**
     * 设置输入框输入字符串
     */
    public static void setKeyListener(EditText editText) {
        editText.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
    }

    /**
     * 设置输入框输入数字
     */
    public static void setInput(EditText editText) {
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    /**
     * 获取其中较小的数量
     */
    public static String getMinQty(String qty1, String qty2) {
        if (null == qty1) {
            return qty2;
        }
        if (null == qty2) {
            return qty1;
        }
        BigDecimal bd1 = new BigDecimal(StringUtils.isBlank(qty1) ? "0" : qty1);
        BigDecimal bd2 = new BigDecimal(StringUtils.isBlank(qty2) ? "0" : qty2);
        double subtract = bd1.subtract(bd2).doubleValue();
        return subtract < 0 ? String.valueOf(bd1) : String.valueOf(bd2);
    }
}
