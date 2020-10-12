package com.lib.basex.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.lib.basex.dialog.LDialog;
import com.lib.basex.utils.LUtils;
import com.lib.basex.utils.LViewAnim;
import com.lib.basex.utils.Logger;

import java.util.Objects;


/**
 * Created by Mouse on 2017/10/11.
 */

public abstract class BottomSheet<D extends ViewDataBinding> extends LDialog<D> implements Animation.AnimationListener {

    // 动画时长
    private final static int mAnimationDuration = 200;
    // 持有 ContentView，为了做动画
    private View mContentView;
    private boolean mIsAnimating = false;

    public BottomSheet(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setCanceledOnTouchOutside(true);
    }


    private void initWindow() {
        Window window = Objects.requireNonNull(getWindow());
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 在底部，宽度撑满
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;

        int[] screenSize = LUtils.getScreenSize(getContext());
        int screenWidth = screenSize[0];
        int screenHeight = screenSize[1];
        params.width = Math.min(screenWidth, screenHeight);
        window.setAttributes(params);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void setContentView(@NonNull View view) {
        mContentView = view;
        super.setContentView(view);
    }

    /**
     * BottomSheet升起动画
     */
    private void animateUp() {
        if (mContentView == null) {
            return;
        }
        LViewAnim.translateToUpAnimation(mContentView);
    }

    /**
     * BottomSheet降下动画
     */
    private void animateDown() {
        if (mContentView == null) {
            return;
        }
        Animation animation = LViewAnim.translateToDownAnimation(mContentView);
        animation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mIsAnimating = false;
        try {
            super.dismiss();
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void show() {
        super.show();
        animateUp();
    }

    @Override
    public void dismiss() {
        if (mIsAnimating) {
            return;
        }
        animateDown();
    }
}
