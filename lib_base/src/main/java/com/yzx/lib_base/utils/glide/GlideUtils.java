package com.yzx.lib_base.utils.glide;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yzx.lib_base.R;


import jp.wasabeef.glide.transformations.BlurTransformation;

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
    public static final int TYPE_COLOR_IMG = 5;
    public static final int TYPE_PLAY_ALBUM = 6;
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
            case TYPE_COLOR_IMG:
                drawableResource = R.drawable.cbh;
                break;
            case TYPE_PLAY_ALBUM:
                drawableResource = R.drawable.ic_default_poster;
                break;
            case TYPE_HEAD:
                drawableResource = R.drawable.ic_default_head;
                break;
            default:
                drawableResource = R.color.colorTransparent;
                break;
        }

        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.placeholder(drawableResource).error(drawableResource);
        requestBuilder
                .transition(new DrawableTransitionOptions().crossFade(300))
                .apply(requestOptions)
                .into(target);
    }


    public static void getBitmapColor(Object resource, ImageView target, final ColorCallBack callBack) {
        Glide.with(target.getContext()).asBitmap().load(resource).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                Palette.from(resource)
                        .generate(palette -> {
                            if (palette == null) {
                                callBack.onCallBack( 0xffffff);
                            } else {
                                callBack.onCallBack(palette.getVibrantColor(0xffffff));
                            }
                        });
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                callBack.onCallBack( 0xffffff);
            }
        });
    }
    public static void loadBitmap(Object resource, @DrawableRes int placeholder, ImageView target, final BitmapColorCallBack callBack) {
        loadBitmap(resource, placeholder, target, false, callBack);
    }

    public static void loadBitmap(Object resource, @DrawableRes final int placeholder, final ImageView target, final boolean needColor, final BitmapColorCallBack callBack) {
        Glide.with(target.getContext()).asBitmap().placeholder(placeholder).load(resource).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                if (needColor) {
                    getColor(bitmap, callBack);
                } else {
                    callBack.onCallBack(bitmap, 0x00000000);
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                Bitmap bitmap = BitmapFactory.decodeResource(target.getContext().getApplicationContext().getResources(), placeholder);
                if (needColor) {
                    getColor(bitmap, callBack);
                } else {
                    callBack.onCallBack(bitmap, 0x00000000);
                }
            }
        });
    }

    private static void getColor(final Bitmap resource, final BitmapColorCallBack callBack) {
        Palette.from(resource)
                .generate(palette -> {
                    if (palette == null) {
                        callBack.onCallBack(resource, 0x212121);
                    } else {
                        callBack.onCallBack(resource, palette.getVibrantColor(0x212121));
                    }
                });
    }


    public static void simpleLoadImg(Object resource, ImageView target) {
        Glide.with(target).load(resource).into(target);
    }

    public static void loadImgWithAnim(Object resource, ImageView target) {
        Glide.with(target).load(resource).transition(new DrawableTransitionOptions().crossFade(300)).into(target);
    }

    public static void loadDrawable(Object resource, ImageView target, int radius, int sampling) {
        MultiTransformation<Bitmap> transformation = new MultiTransformation<>(new BlurTransformation(radius, sampling));
        Glide.with(target).load(resource).apply(RequestOptions.bitmapTransform(transformation)).into(target);
    }

    public static void loadBlurImage(Object resource, ImageView target, DrawableCallBack callBack) {
        Glide.with(target).load(resource).apply(RequestOptions.bitmapTransform(new BlurTransformation(100, 6))).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                callBack.onGetDrawable(resource);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                callBack.onGetDrawable(null);
            }
        });
    }


}
