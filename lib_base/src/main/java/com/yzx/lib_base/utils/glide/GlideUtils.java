package com.yzx.lib_base.utils.glide;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yzx.lib_base.R;

import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author yzx
 * @date 2018/10/30
 * Description
 */
public class GlideUtils {
    public static final int TYPE_DEFAULT = 0;

    public static final int TYPE_HEAD = 1;
    public static final int TYPE_COIN = 2;
    public static final int TYPE_GOOD_CLASSIFY = 3;
    public static final int TYPE_BACKGROUND = 4;
    static RequestOptions requestOptions = new RequestOptions();

    public static void loadImg(Object resource, ImageView target) {
        loadImg(resource, TYPE_DEFAULT, target);
    }

    @SuppressLint("CheckResult")
    public static void loadImg(Object resource, int type, final ImageView target) {
        RequestBuilder<Drawable> requestBuilder = Glide.with(target).load(resource);

        final int drawableResource;

        switch (type) {
            case TYPE_BACKGROUND:
                drawableResource = R.drawable.cbh;
                break;
            default:
                drawableResource = R.color.colorImg;
                break;
        }

        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.placeholder(drawableResource).error(drawableResource);
        RequestBuilder<Drawable> apply = requestBuilder
                .transition(new DrawableTransitionOptions().crossFade(500))
                .apply(requestOptions);

        if (target instanceof CircleImageView) {
            apply.into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    target.setImageDrawable(resource);
                }
            });
        } else {
            apply.into(target);
        }
    }


    public static void loadBitmap(Object resource, @DrawableRes int placeholder, ImageView target, final CallBack callBack) {
        loadBitmap(resource, placeholder, target, false, callBack);
    }

    public static void loadBitmap(Object resource, @DrawableRes final int placeholder, final ImageView target, final boolean needColor, final CallBack callBack) {
        Glide.with(target).asBitmap().placeholder(placeholder).load(resource).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                loadImg(bitmap,target);
                if (needColor) {
                    getColor(bitmap, callBack);
                } else {
                    callBack.onCallBack(bitmap, 0x00000000);
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                loadImg(errorDrawable,target);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) errorDrawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (needColor) {
                    getColor(bitmap, callBack);
                } else {
                    callBack.onCallBack(bitmap, 0x00000000);
                }

            }
        });
    }

    private static void getColor(final Bitmap resource, final CallBack callBack) {
        Palette.from(resource)
                .generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@Nullable Palette palette) {
                        int vibrantColor = palette.getVibrantColor(0x212121);
                        if (callBack != null) {
                            callBack.onCallBack(resource, vibrantColor);
                        }
                    }
                });
    }


    public static void simpleLoadImg(Object resource, ImageView target) {
        Glide.with(target).load(resource).into(target);
    }


}
