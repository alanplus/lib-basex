package com.lib.basex.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;

import androidx.annotation.NonNull;

import com.lib.basex.R;
import com.lib.basex.databinding.LLightDialogLoadingBinding;
import com.lib.basex.utils.LAnimation;

import java.util.Objects;

/**
 * @author Alan
 * 时 间：2020/11/17
 * 简 述：<功能简述>
 */
public class LightLoadingDialog extends LDialog<LLightDialogLoadingBinding> {
    public LightLoadingDialog(@NonNull Context context) {
        super(context,R.style.LTranslateDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
//        Objects.requireNonNull(getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Animation animation = new LAnimation(LAnimation.TYPE_ROTATE).setDuration(1000).build();
        d.lLightLoading.startAnimation(animation);

    }

    @Override
    public int getContentRes() {
        return R.layout.l_light_dialog_loading;
    }
}
