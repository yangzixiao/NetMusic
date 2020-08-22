package com.yzx.lib_base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yzx.lib_base.R;

/**
 * @author yzx
 * @date 2019/7/9
 * Description
 */
public class AnimCompatActivity extends AppCompatActivity {

    private int exitAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        int activityEnterAnim = getActivityEnterAnim();
        int activityExitAnim = getActivityExitAnim();
        int enterAnim = activityEnterAnim == 0 ? R.anim.slide_in_right : activityEnterAnim;
        exitAnim = activityExitAnim == 0 ? R.anim.slide_out_right : activityExitAnim;
        overridePendingTransition(enterAnim, R.anim.anim_no);
        super.onCreate(savedInstanceState);


    }

    protected int getActivityExitAnim() {
        return 0;
    }

    protected int getActivityEnterAnim() {
        return 0;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_no, exitAnim);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_no, exitAnim);
    }
}
