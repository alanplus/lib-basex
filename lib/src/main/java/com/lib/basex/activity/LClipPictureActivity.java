package com.lib.basex.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;


import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.lib.basex.R;
import com.lib.basex.activity.vm.LClipPictureVm;
import com.lib.basex.databinding.LClipPictureActivityBinding;
import com.lib.basex.utils.LPathUtils;
import com.lib.basex.utils.LUtils;
import com.lib.basex.utils.Logger;

import java.io.File;
import java.io.IOException;

/**
 * 用户裁剪头像
 *
 * @author huangxiaotao
 */
public class LClipPictureActivity extends LActivity<LClipPictureVm, LClipPictureActivityBinding> implements OnClickListener {

    public static void start(Activity activity, String path, Uri uri, int requestCode) {
        Intent intent = new Intent(activity, LClipPictureActivity.class);
        if (path != null) {
            intent.putExtra("user_img_path", path);
        } else if (uri != null) {
            intent.putExtra("user_uri", uri);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void start(Activity activity, Intent intent, int requestCode) {
        Uri originalUri = intent.getData();
        String imgPath;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            imgPath = LPathUtils.getPath(activity, originalUri);
        } else {
            imgPath = LPathUtils.getFilePathFromIntentData(originalUri, activity);
        }
        start(activity, imgPath, originalUri, requestCode);
    }


    private final static int CLIP_STYLE = 2;

    @Override
    public int getContentId() {
        return R.layout.l_clip_picture_activity;
    }

    @Override
    public void initView() {
        d.rotateLeft.setOnClickListener(this);
        d.rotateRight.setOnClickListener(this);
        d.sure.setOnClickListener(this);
        d.cancel.setOnClickListener(this);
        initBitmap();
    }

    private void initBitmap() {
        Uri imgUri = getIntent().getParcelableExtra("user_uri");
        String imgPath = getIntent().getStringExtra("user_img_path");
        int degree = 0;
        if (!TextUtils.isEmpty(imgPath)) {
            degree = getBitmapDegree(imgPath);
        }
        Rect clipRect = initClipFrame();

        if (imgPath != null && checkFilePath(imgPath)) {
            d.clipview.setImage(imgPath, clipRect, CLIP_STYLE, degree);
        } else if (imgUri != null) {
            d.clipview.setImage(imgUri, clipRect, CLIP_STYLE, degree);
        } else {
            this.finish();
        }
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            Logger.error(e);

        }
        return degree;
    }

    private boolean checkFilePath(String imgPath) {
        try {
            File f = new File(imgPath);
            if (f.exists()) {
                return true;
            }
        } catch (Exception e) {
            Logger.error(e);
        }
        return false;
    }

    private Rect initClipFrame() {
        int top = LUtils.dip2px(10);
        int[] screenSize = LUtils.getScreenSize(this);
        int screenWidth = screenSize[0];
        int screenHeight = screenSize[0];
        Rect clipRect = new Rect(0, top, screenWidth, top + screenWidth);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) d.clipFrame.getLayoutParams();
        params.width = screenWidth;
        params.height = screenHeight;
        return clipRect;
    }

    @Override
    protected void onDestroy() {
        d.clipview.onDestroy();
        super.onDestroy();
    }

    private void clipBitmap() {
        String imgName = d.clipview.getBitmap();
        Intent intent = new Intent();
        intent.putExtra("pic_name", imgName);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sure) {
            clipBitmap();
        } else if (v.getId() == R.id.cancel) {
            finish();
        } else if (v.getId() == R.id.rotate_left) {
            d.clipview.rotate(-90.0f);
        } else if (v.getId() == R.id.rotate_right) {
            d.clipview.rotate(90.0f);
        }
    }

}