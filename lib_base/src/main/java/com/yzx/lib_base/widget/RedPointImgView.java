package com.yzx.lib_base.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.yzx.lib_base.R;

/**
 * @author yzx
 * @date 2020/5/16
 * Description
 */
public class RedPointImgView extends androidx.appcompat.widget.AppCompatImageView {

    public static final String TAG = "RedPointImgView";

    private int redPointColor;
    private float redPointSize;
    private boolean showRedPoint;
    private Paint mPaint;
    private int measuredWidth;
    private int padding;
    private int margin;

    public RedPointImgView(Context context) {
        this(context, null);
    }

    public RedPointImgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedPointImgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);

    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RedPointImgView);
        showRedPoint = typedArray.getBoolean(R.styleable.RedPointImgView_rpv_show_red_point, false);
        redPointColor = typedArray.getColor(R.styleable.RedPointImgView_rpv_red_point_color, 0xff0000);
        redPointSize = typedArray.getDimension(R.styleable.RedPointImgView_rpv_red_point_size, dip2px(context, 3));
        typedArray.recycle();

        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setColor(redPointColor);

        padding = dip2px(context, 5);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setPadding(padding, padding, padding, padding);
//        LogUtils.e(TAG, "onDraw: "+redPointSize+":"+measuredWidth);
        if (showRedPoint) {
            canvas.drawCircle(measuredWidth - redPointSize / 2, redPointSize / 2, redPointSize/2, mPaint);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setRedPointColor(int redPointColor) {
        mPaint.setColor(redPointColor);
    }

    public void setRedPointSize(float redPointSize) {
        this.redPointSize = redPointSize;
    }

    public void setImgResource(int imgResource) {
        setImageResource(imgResource);
    }

    public void setImgResourceTint(int imgResourceTint) {
        if (Build.VERSION.SDK_INT > 21) {
            setImageTintList(ColorStateList.valueOf(imgResourceTint));
        }
    }

    public void invalidateView() {
        invalidate();
    }

    public void isShowRedPoint(boolean showRedPoint) {
        this.showRedPoint = showRedPoint;
    }
}
