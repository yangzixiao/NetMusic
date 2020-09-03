package com.yzx.lib_base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;

/**
 * @author yzx
 * @date 2020/7/15
 * Description
 */
public class MaskImageView extends ShapeableImageView {

    private int maskColor=0x00000000;

    public MaskImageView(Context context) {
        this(context,null);
    }

    public MaskImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MaskImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(maskColor);
    }

    public void setMaskColor(int maskColor){
        this.maskColor = maskColor;
        invalidate();
    }
}
