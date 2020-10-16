package com.yzx.lib_base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yzx.lib_base.R;
import com.yzx.lib_core.utils.LogUtils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @author yzx
 * @date 2020/5/16
 * Description
 */
public class ImgTitleView extends LinearLayout {

    public static final String TAG = "ImgTitleView";
    private int imgResource;
    private int imgResourceTint;
    private String title;
    private int titleColor;
    private float titleSize;

    private int redPointColor;
    private float redPointSize;
    private float topMargin;
    private boolean showRedPoint;
    private RedPointImgView redPointImgView;

    public ImgTitleView(Context context) {
        this(context, null);
    }

    public ImgTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImgTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(LinearLayout.VERTICAL);
        initAttr(context, attrs);

        initView(context);
    }


    private void initAttr(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImgTitleView);
        imgResource = typedArray.getResourceId(R.styleable.ImgTitleView_itv_img, 0);
        showRedPoint = typedArray.getBoolean(R.styleable.ImgTitleView_itv_show_red_point, false);
        imgResourceTint = typedArray.getColor(R.styleable.ImgTitleView_itv_img_tint, 0);
        redPointColor = typedArray.getColor(R.styleable.ImgTitleView_itv_red_point_color, context.getResources().getColor(R.color.colorRed));
        redPointSize = typedArray.getDimension(R.styleable.ImgTitleView_itv_red_point_size, 5);
        title = typedArray.getString(R.styleable.ImgTitleView_itv_title);
        titleColor = typedArray.getColor(R.styleable.ImgTitleView_itv_title_color, 0xffffff);
        titleSize = typedArray.getDimension(R.styleable.ImgTitleView_itv_title_size, sp2px(context, 8));
        topMargin = typedArray.getDimension(R.styleable.ImgTitleView_itv_title_margin_top, dip2px(context, 8));
        typedArray.recycle();
    }

    private void initView(Context context) {


        try {
            if (imgResource == 0) {
                throw new NullPointerException("没有设置图片资源");
            }
            redPointImgView = new RedPointImgView(context);
            redPointImgView.setRedPointColor(redPointColor);
            redPointImgView.setRedPointSize(redPointSize);
            redPointImgView.setImgResource(imgResource);
            redPointImgView.isShowRedPoint(showRedPoint);
            if (imgResourceTint != 0) {
                redPointImgView.setImgResourceTint(imgResourceTint);
        }
        redPointImgView.invalidateView();
        addView(redPointImgView, getViewLayoutParams());
    } catch (Exception e) {
        LogUtils.e(TAG, "initView: " + e.toString());
    }
        if (!TextUtils.isEmpty(title)) {
        TextView titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextColor(titleColor);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
//            LogUtils.e(TAG, "initView: " + titleSize);
            LayoutParams viewLayoutParams = getViewLayoutParams();
            viewLayoutParams.topMargin = (int) topMargin;
            addView(titleView, viewLayoutParams);
        }

    }

    private LinearLayout.LayoutParams getViewLayoutParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        return layoutParams;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int sp2px(Context context, float dpValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void showRedPoint() {
        redPointImgView.isShowRedPoint(true);
        redPointImgView.invalidateView();
    }

    public void hideRedPoint() {
        redPointImgView.isShowRedPoint(false);
        redPointImgView.invalidateView();
    }

    public void setImgResourceTint(int imgResourceTint) {
        this.imgResourceTint = imgResourceTint;
        redPointImgView.setImgResourceTint(imgResourceTint);
    }
}
