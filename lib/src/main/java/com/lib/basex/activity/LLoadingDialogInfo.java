package com.lib.basex.activity;


import com.lib.basex.dialog.loading.LLoadDialogView;

/**
 * @author Alan
 * 时 间：2020/11/18
 * 简 述：<功能简述>
 */
public class LLoadingDialogInfo {

    /**
     * 1 显示 2 关闭（成功） 3 关闭（失败） 4 立即关闭
     */
    public int state;

    public String showText;

    public LLoadDialogView.OnFinishListener onFinishListener;

}
