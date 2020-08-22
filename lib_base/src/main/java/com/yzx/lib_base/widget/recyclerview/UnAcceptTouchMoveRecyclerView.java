package com.yzx.lib_base.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author yzx
 * @date 2020/7/17
 * Description
 */
public class UnAcceptTouchMoveRecyclerView extends RecyclerView {
    public UnAcceptTouchMoveRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public UnAcceptTouchMoveRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UnAcceptTouchMoveRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }
}
