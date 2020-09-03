package com.yzx.lib_base.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

/**
 * @author yzx
 * @date 2020/7/17
 * Description
 */
public class UnAcceptTouchMoveRecyclerView extends RecyclerView {

    private TabLayout tabLayout;

    public UnAcceptTouchMoveRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public UnAcceptTouchMoveRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UnAcceptTouchMoveRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void bindTabLayout(TabLayout tabLayout){
        this.tabLayout = tabLayout;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        Log.i("ygl", "onScrolled: ");
        if (getLayoutManager() instanceof LinearLayoutManager){
            int firstVisibleItemPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            Log.i("ygl", "onScrolled: "+firstVisibleItemPosition);
            tabLayout.selectTab(tabLayout.getTabAt(firstVisibleItemPosition));
        }

    }
}
