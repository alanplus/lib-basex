package com.lib.basex.widget.titlebar;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatImageView;

import com.lib.basex.R;

/**
 * @author Alan
 * 时 间：2020-05-08
 * 简 述：
 * 1. 支持左侧 图标、文字（大小、颜色）
 * 2. 支持右侧 图标、文字（大小、颜色）
 * 3. 支持主标题、副标题
 * 4. 支持底部分割线 高度、颜色
 */
public class TitleBar extends RelativeLayout {

    // 标题属性
    private String mTitle;
    private String mSubTitle;

    private int mTitleTextSize;
    private int mSubTitleTextSize;

    private int mTitleColor;
    private int mSubTitleColor;

    // 分割线属性
    private int mDividerColor;
    private int mDividerHeight;

    // 左侧 图标
    private int mLeftDrawableResId;

    // 布局背景颜色
    private int mBgColor;

    // 左侧文本 属性
    private String mLeftText;
    private int mLeftTextColor;
    private int mLeftTextSize;

    // 右侧 文本属性
    private String mRightText;
    private int mRightTextColor;
    private int mRightPressTextColor;
    private int mRightTextSize;

    // 右侧 按钮资源ID
    private int mRightDrawableID;

    private View mDividerView;
    private ImageView mLeftImageView;
    private ImageView mRightImageView;
    private TextView mLeftTextView;
    private TextView mRightTextView;

    private TextView mTitleView;
    private TextView mSubTitleView;

    private LinearLayout mTitleContainer;

    private int mLeftMargin;
    private int mRightMargin;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs, R.style.TitleBarLightStyle);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        mLeftMargin = 0;
        mRightMargin = dip2px(10);

        if (null == attrs) {
            return;
        }

        // 设置分割线
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.LTitleBar, R.attr.title_bar_style, R.style.TitleBarLightStyle);
        mDividerColor = array.getColor(R.styleable.LTitleBar_title_bar_divider_color, Color.WHITE);
        mDividerHeight = (int) array.getDimension(R.styleable.LTitleBar_title_bar_divider_height, 0);
        setDividerColor(mDividerColor);
        setDividerHeight(mDividerHeight);

        // 设置背景颜色
        mBgColor = array.getColor(R.styleable.LTitleBar_title_bar_bg_color, Color.WHITE);
        setBackgroundColor(mBgColor);


        // 设置左侧按钮
        mLeftDrawableResId = array.getResourceId(R.styleable.LTitleBar_title_bar_left_drawable, R.drawable.l_title_bar_back);
        setLeftImageView(mLeftDrawableResId);

        // 设置左侧文字
        mLeftText = array.getString(R.styleable.LTitleBar_title_bar_left_text);
        mLeftTextColor = array.getColor(R.styleable.LTitleBar_title_bar_left_text_color, Color.parseColor("#333333"));
        mLeftTextSize = (int) array.getDimension(R.styleable.LTitleBar_title_bar_left_text_size, dip2px(14));
        setLeftText(mLeftText);
        setLeftTextColor(mLeftTextColor);
        setLeftTextSize(mLeftTextSize);

        // 设置右侧 文字
        mRightText = array.getString(R.styleable.LTitleBar_title_bar_right_text);
        mRightTextColor = array.getColor(R.styleable.LTitleBar_title_bar_right_text_color, Color.WHITE);
        mRightPressTextColor = array.getColor(R.styleable.LTitleBar_title_bar_right_press_text_color, Color.WHITE);
        mRightTextSize = (int) array.getDimension(R.styleable.LTitleBar_title_bar_right_text_size, dip2px(15));
        setRightText(mRightText);
        setRightTextColor(mRightTextColor, mRightPressTextColor);
        setRightTextSize(mRightTextSize);

        // 设置右侧按钮
        mRightDrawableID = array.getResourceId(R.styleable.LTitleBar_title_bar_right_drawable, View.NO_ID);
        setRightImageView(mRightDrawableID);

        // 生成标题 容器
        initTitleContainer();
        mTitle = array.getString(R.styleable.LTitleBar_tb_text);
        mSubTitle = array.getString(R.styleable.LTitleBar_tb_sub_text);

        mTitleTextSize = (int) array.getDimension(R.styleable.LTitleBar_title_bar_text_size, dip2px(17));
        mSubTitleTextSize = (int) array.getDimension(R.styleable.LTitleBar_title_bar_sub_text_size, dip2px(14));
        mTitleColor = array.getColor(R.styleable.LTitleBar_title_bar_text_color, Color.parseColor("#333333"));
        mSubTitleColor = array.getColor(R.styleable.LTitleBar_title_bar_sub_text_color, Color.parseColor("#535353"));
        Log.d("test_title", "text_size:" + mTitleTextSize);
        setTitle(mTitle);
        setSubTitle(mSubTitle);

        array.recycle();

        setLeftButtonClickListener(v -> {
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).onBackPressed();
            }
        });

        setClickEffect(mLeftImageView);

    }

    public void setLeftButtonClickListener(OnClickListener onClickListener) {
        if (null != mLeftImageView) {
            mLeftImageView.setOnClickListener(onClickListener);
        }
    }

    public void setRightTextClickListener(OnClickListener onClickListener) {
        if (null != mRightTextView) {
            mRightTextView.setOnClickListener(onClickListener);
        }
    }

    public void setRightImageClickListener(OnClickListener onClickListener) {
        if (null != mRightImageView) {
            mRightImageView.setOnClickListener(onClickListener);
        }
    }

    public void setLeftTextClickListener(OnClickListener onClickListener) {
        if (null != mLeftTextView) {
            mLeftTextView.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置子标题
     *
     * @param text
     */
    public void setSubTitle(String text) {
        this.mSubTitle = text;
        if (TextUtils.isEmpty(this.mSubTitle)) {
            if (null != mSubTitleView) {
                mSubTitleView.setVisibility(View.GONE);
            }
            return;
        }

        if (null == mSubTitleView) {
            generateSubTitleView();
        }
        mSubTitleView.setVisibility(View.VISIBLE);
        mSubTitleView.setText(text);
    }

    /**
     * 设置主标题
     *
     * @param text
     */
    public void setTitle(String text) {
        this.mTitle = text;
        if (null == mTitleView) {
            generateTitleView();
        }
        mTitleView.setText(text);
    }

    /**
     * 生成子标题 组件
     */
    private void generateSubTitleView() {
        mSubTitleView = new TextView(getContext());
        mSubTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSubTitleTextSize);
        mSubTitleView.setTextColor(mSubTitleColor);
        mSubTitleView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0);
        layoutParams.weight = 1;
        mSubTitleView.setLayoutParams(layoutParams);
        mTitleContainer.addView(mSubTitleView);
    }

    /**
     * 生成主标题组件
     */
    private void generateTitleView() {
        mTitleView = new TextView(getContext());
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        mTitleView.setTextColor(mTitleColor);
        mTitleView.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0);
        layoutParams.weight = 2;
        mTitleView.setLayoutParams(layoutParams);
        mTitleContainer.addView(mTitleView);
    }

    /**
     * 生成标题容器
     */
    private void initTitleContainer() {
        mTitleContainer = new LinearLayout(getContext());
        mTitleContainer.setOrientation(LinearLayout.VERTICAL);
        mTitleContainer.setGravity(Gravity.CENTER);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mTitleContainer, layoutParams);
    }

    /**
     * 设置右侧 图片 icon
     *
     * @param drawableId
     */
    public void setRightImageView(int drawableId) {
        this.mRightDrawableID = drawableId;
        if (this.mRightDrawableID == View.NO_ID) {
            if (mRightImageView != null) {
                mRightImageView.setVisibility(View.GONE);
            }
            return;
        }
        if (null == mRightImageView) {
            generateRightImageView();
        }
        this.mRightImageView.setImageResource(drawableId);
        this.mRightImageView.setVisibility(View.VISIBLE);
    }

    /**
     * 生成右侧 图片 组件
     */
    private void generateRightImageView() {
        mRightImageView = new ImageView(getContext());
        mRightImageView.setImageResource(this.mRightDrawableID);

        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.rightMargin = mRightMargin;

        mRightImageView.setLayoutParams(layoutParams);
        addView(mRightImageView);

    }

    /**
     * 设置右侧文本 文本大小
     *
     * @param rightTextSize
     */
    public void setRightTextSize(int rightTextSize) {
        this.mRightTextSize = rightTextSize;
        if (null != mRightTextView) {
            mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
        }
    }


    /**
     * 设置右侧文本 文本颜色
     *
     * @param color
     * @param pressColor
     */
    public void setRightTextColor(@ColorInt int color, @ColorInt int pressColor) {
        this.mRightTextColor = color;
        this.mRightPressTextColor = pressColor;
        if (null != mRightTextView) {
            int[][] array = new int[][]{
                    new int[]{android.R.attr.state_pressed},
                    new int[]{}
            };
            mRightTextView.setTextColor(new ColorStateList(array, new int[]{mRightPressTextColor, mRightTextColor}));
        }

    }


    /**
     * 设置右侧文本按钮 文本
     *
     * @param text
     */
    public void setRightText(String text) {
        this.mRightText = text;
        if (TextUtils.isEmpty(this.mRightText)) {
            if (mRightTextView != null) {
                mRightTextView.setVisibility(View.GONE);
            }
            return;
        }

        if (null == mRightTextView) {
            generateRightTextView();
        }

        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText(this.mRightText);
    }

    /**
     * 生成右侧文本按钮
     */
    private void generateRightTextView() {
        mRightTextView = new TextView(getContext());
        mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
        mRightTextView.setGravity(Gravity.CENTER);
        int[][] array = new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{}
        };
        mRightTextView.setTextColor(new ColorStateList(array, new int[]{mRightPressTextColor, mRightTextColor}));
        int p = dip2px(10);
        mRightTextView.setPadding(p, 0, p, 0);
        LayoutParams layoutParams = new LayoutParams(-2, -1);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.rightMargin = mRightMargin;
        mRightTextView.setLayoutParams(layoutParams);
        addView(mRightTextView);

    }


    /**
     * 设置左侧文本 大小
     *
     * @param textSize
     */
    public void setLeftTextSize(int textSize) {
        this.mLeftTextSize = textSize;
        if (null != mLeftTextView) {
            mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.mLeftTextSize);
        }
    }

    /**
     * 设置左侧文本颜色
     *
     * @param color
     */
    public void setLeftTextColor(int color) {
        this.mLeftTextColor = color;
        if (null != mLeftTextView) {
            mLeftTextView.setTextColor(color);
        }
    }

    /**
     * 设置左侧文本按钮
     *
     * @param text
     */
    public void setLeftText(String text) {
        this.mLeftText = text;
        if (TextUtils.isEmpty(mLeftText)) {
            if (mLeftTextView != null) {
                mLeftTextView.setVisibility(View.GONE);
            }
            return;
        }
        if (null == mLeftTextView) {
            generateLeftTextView();
        }
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText(text);
    }

    /**
     * 生成左侧文本组件
     */
    private void generateLeftTextView() {
        mLeftTextView = new TextView(getContext());
        mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
        mLeftTextView.setTextColor(mLeftTextColor);
        mLeftTextView.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(-2, -1);
        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.title_bar_left_image_view);
        mLeftTextView.setLayoutParams(layoutParams);
        addView(mLeftTextView);
    }


    /**
     * 设置左侧图标
     *
     * @param res
     */
    public void setLeftImageView(int res) {
        this.mLeftDrawableResId = res;
        if (null == mLeftImageView) {
            generateLeftImageView();
        }
        mLeftImageView.setImageResource(mLeftDrawableResId);
    }

    /**
     * 设置左侧图标背景
     *
     * @param res
     */
    public void setLeftImageBackground(int res) {
        if (null == mLeftImageView) {
            generateLeftImageView();
        }
        mLeftImageView.setBackgroundResource(res);
    }

    /**
     * 生成左侧图标
     */
    private void generateLeftImageView() {
        int size = dip2px(48);
        mLeftImageView = new AppCompatImageView(getContext());
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(size, size);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.leftMargin = mLeftMargin;
        mLeftImageView.setLayoutParams(layoutParams);
        mLeftImageView.setId(R.id.title_bar_left_image_view);
        mLeftImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        addView(mLeftImageView);
    }


    public void setDividerEnable(boolean enable) {
        mDividerHeight = enable ? mDividerHeight : 0;
        setDividerHeight(mDividerHeight);
    }

    public void setDividerHeight(int height) {
        this.mDividerHeight = height;
        if (height == 0) {
            if (null != mDividerView) {
                mDividerView.setVisibility(View.GONE);
            }
            return;
        }

        if (null == mDividerView) {
            mDividerView = new View(getContext());
            mDividerView.setBackgroundColor(mDividerColor);
            LayoutParams layoutParams = new LayoutParams(-1, mDividerHeight);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mDividerView.setLayoutParams(layoutParams);
            addView(mDividerView);
            return;
        }
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mDividerView.getLayoutParams();
        layoutParams.height = height;
    }

    public void setDividerColor(int color) {
        this.mDividerColor = color;
        if (null != mDividerView) {
            mDividerView.setBackgroundColor(mDividerColor);
        }
    }

    private int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    public static int getScreenWidth(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.x;
    }

//    public static DisplayMetrics getDisplayMetrics(Context context) {
//        DisplayMetrics metric = new DisplayMetrics();
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        wm.getDefaultDisplay().getMetrics(metric);
//        Display display = wm.getDefaultDisplay();
//        int rawWidth = metric.widthPixels;
//        int rawHeight = metric.heightPixels;
//
//        try {
//            Method mGetRawH = Display.class.getMethod("getRawHeight");
//            Method mGetRawW = Display.class.getMethod("getRawWidth");
//            rawWidth = (Integer) mGetRawW.invoke(display);
//            rawHeight = (Integer) mGetRawH.invoke(display);
//        } catch (Exception e) {
//            Log.d("error", Log.getStackTraceString(e));
//        }
//
//        metric.widthPixels = rawWidth;
//        metric.heightPixels = rawHeight;
//        return metric;
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (null != mTitleContainer) {

            int leftWidth = mLeftMargin;
            if (null != mLeftImageView && mLeftImageView.getVisibility() == View.VISIBLE) {
                leftWidth += mLeftImageView.getMeasuredWidth();
                Log.d("test_title", "leftWidth:" + leftWidth);
            }

            if (null != mLeftTextView && mLeftTextView.getVisibility() == View.VISIBLE) {
                leftWidth += mLeftTextView.getMeasuredWidth();
            }

            int rightWidth = mRightMargin;
            if (null != mRightTextView && mRightTextView.getVisibility() == View.VISIBLE) {
                rightWidth += mRightTextView.getMeasuredWidth();
            }

            if (null != mRightImageView && mRightImageView.getVisibility() == View.VISIBLE) {
                rightWidth += mRightImageView.getMeasuredWidth();
            }

            // 两侧留间距
            int p = Math.max(leftWidth, rightWidth) + dip2px(10);
            int widthPixels = getScreenWidth((Activity) getContext());
            int width = widthPixels - 2 * p;
            RelativeLayout.LayoutParams layoutParams = (LayoutParams) mTitleContainer.getLayoutParams();
            if (layoutParams.width != width) {
                layoutParams.width = width;
            }
        }

    }

    private void setClickEffect(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.6f);
                        break;
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_SCROLL:
                        v.setAlpha(1.0f);
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }

    public void hiddenLeftImageView() {
        if (null != mLeftImageView) {
            mLeftImageView.setVisibility(View.GONE);
        }
    }
}
