package com.yzx.lib_base.widget.recyclerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author yzx
 * @date 2018/9/26
 * Description RecyclerView纵向分割线
 */
public class ExtraLinearItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "ExtraLinearItemDecoration";
    private final int space;
    private Paint mPaint;
    /**
     * 默认分割线颜色
     */
    private int dividerColor = 0x00000000;
    private int edg;


    public ExtraLinearItemDecoration(int space) {
        this.space = space;

        init();
    }

    public ExtraLinearItemDecoration(int edg, int space) {
        this.edg = edg;
        this.space = space;

        init();
    }


    public ExtraLinearItemDecoration(int edg, int space, int dividerColor) {
        this.edg = edg;
        this.space = space;
        this.dividerColor = dividerColor;

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(dividerColor);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();
            int childCount = parent.getAdapter().getItemCount();
            if (isHaveEdg()) {
                haveEdg(position, childCount, outRect, orientation);
            } else {
                notHaveEdg(outRect, position, orientation);
            }

        }
    }

    private void haveEdg(int position, int childCount, Rect outRect, int orientation) {

        if (isLayoutManagerHorizontal(orientation)) {
            if (position == 0) {
                outRect.left = edg;
            } else if (position == childCount - 1) {
//                Log.i(TAG, position+"haveEdg: " + childCount);
                outRect.left = space;
                outRect.right = edg;
            } else {
                outRect.left = space;
            }
        } else {
            if (position != 0) {
                outRect.top = space;
                if (isHaveEdg()) {
                    outRect.left = edg;
                    outRect.right = edg;
                }
            }
        }

    }

    private void notHaveEdg(Rect outRect, int position, int orientation) {
        if (position != 0) {
            if (isLayoutManagerHorizontal(orientation)) {
                outRect.left = space;
            } else {
                outRect.top = space;
            }
        }
    }

    private boolean isLayoutManagerHorizontal(int orientation) {
        return orientation == LinearLayoutManager.HORIZONTAL;
    }

    private boolean isHaveEdg() {
        return edg != 0;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();

            int childCount = parent.getChildCount();

            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(child);

                if (position == 0) {
                    continue;
                }

                if (isLayoutManagerHorizontal(orientation)) {
                    drawHorizontalDivider(child, c, parent);
                } else {
                    drawVerticalDivider(child, c, parent);
                }
            }

        }
    }

    private void drawVerticalDivider(View child, Canvas c, RecyclerView parent) {

        c.drawRect(0, child.getTop(), child.getRight(), child.getTop() + space, mPaint);
    }

    private void drawHorizontalDivider(View child, Canvas c, RecyclerView parent) {

        c.drawRect(0, 0, child.getRight() - space, child.getBottom(), mPaint);
    }
}
