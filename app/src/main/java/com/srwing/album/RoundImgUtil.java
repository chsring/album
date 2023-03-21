package com.srwing.album;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

/**
 * @author zxl
 * @data 2020/6/18
 * @function 圆角图片工具类
 */
public class RoundImgUtil {

    public static void setImg(Context context, Object url, ImageView imageView) {
        if (!isDestroy(context) && null != url) {
            Glide.with(context).load(url).into(imageView);
        }
    }

    public static void setImgAnima(Activity activity, Object url, ImageView imageView) {
        if (null != activity && !isDestroy(activity) && null != url) {
            //图片渐变模糊度始终
            AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
            //渐变时间
            aa.setDuration(800);
            //展示图片渐变动画
            Glide.with(activity).load(url).into(imageView);
            imageView.startAnimation(aa);
            //渐变过程监听
            aa.setAnimationListener(new Animation.AnimationListener() {
                /**
                 * 动画开始时
                 */
                @Override
                public void onAnimationStart(Animation animation) {
                }
                /**
                 * 重复动画时
                 */
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
                /**
                 * 动画结束时
                 */
                @Override
                public void onAnimationEnd(Animation animation) {
                }
            });
        }
    }

    //不裁剪 无缓存
    @SuppressLint("CheckResult")
    public static void setRoundImgNoCache(Context context, Object url, ImageView imageView, int roundSize) {
        if (null == context)
            return;
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing()) {
                if (!((Activity) context).isDestroyed()) {
                    roundImgNoCache(context, url, imageView, roundSize);
                }
            }
        } else {
            roundImgNoCache(context, url, imageView, roundSize);
        }
    }

    private static void roundImgNoCache(Context context, Object url, ImageView imageView, int roundSize) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(roundSize);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        options.skipMemoryCache(true);
        options.diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    //不裁剪 有缓存 大多用于设置头像
    @SuppressLint("CheckResult")
    public static void setRoundImg(Context context, String url, ImageView imageView, int roundSize) {
        if (null == context)
            return;
        boolean isRes;
        try {
            Integer.parseInt(url);
            isRes = true;
        } catch (Throwable throwable) {
            isRes = false;
        }
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing()) {
                if (!((Activity) context).isDestroyed()) {
                    if (isRes)
                        roundImg(context, Integer.parseInt(url), imageView, roundSize);
                    else
                        roundImg(context, url, imageView, roundSize);
                }
            }
        } else {
            if (isRes)
                roundImg(context, Integer.parseInt(url), imageView, roundSize);
            else
                roundImg(context, url, imageView, roundSize);
        }
    }

    @SuppressLint("ResourceType")
    private static void roundImg(Context context, String url, ImageView imageView, int roundSize) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(roundSize);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    @SuppressLint("ResourceType")
    private static void roundImg(Context context, int url, ImageView imageView, int roundSize) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(roundSize);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void setDownImg(Context context, Object url, ImageView imageView) {
        if (null == context)
            return;
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing()) {
                if (!((Activity) context).isDestroyed()) {
                    downImg(context, url, imageView);
                }
            }
        } else {
            downImg(context, url, imageView);
        }
    }

    @SuppressLint("CheckResult")
    private static void downImg(Context context, Object url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .skipMemoryCache(false)//开启内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//关闭硬盘缓存
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super File> transition) {
                        Uri uri = Uri.fromFile(resource);
                        try {
                            imageView.setImageURI(uri);
                        } catch (OutOfMemoryError outOfMemoryError) {
                            setImg(context, uri, imageView);
                        }
                    }
                });
    }

    //不裁剪
    public static void setRoundImgCache(Context context, Object url, ImageView imageView, int roundSize) {
        if (null == context)
            return;
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing()) {
                if (!((Activity) context).isDestroyed()) {
                    roundImgCache(context, url, imageView, roundSize);
                }
            }
        } else {
            roundImgCache(context, url, imageView, roundSize);
        }
    }

    private static void roundImgCache(Context context, Object url, ImageView imageView, int roundSize) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(roundSize);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    //裁剪
    public static void setRoundImgs(Context context, Object img, ImageView card, int dip2px) {
        if (null == context)
            return;
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing()) {
                if (!((Activity) context).isDestroyed()) {
                    roundImgs(context, img, card, dip2px);
                }
            }
        } else
            roundImgs(context, img, card, dip2px);
    }

    //裁剪
    public static void setRoundImgsCenterInside(Context context, Object img, ImageView card, int dip2px) {
        if (null == context)
            return;
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing()) {
                if (!((Activity) context).isDestroyed()) {
                    roundImgsCenterInside(context, img, card, dip2px);
                }
            }
        } else
            roundImgsCenterInside(context, img, card, dip2px);
    }

    private static void roundImgs(Context context, Object img, ImageView card, int dip2px) {
        Glide.with(context)
                .load(img)
                .apply(new RequestOptions().transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(dip2px))))
                .into(card);
    }

    private static void roundImgsCenterInside(Context context, Object img, ImageView card, int dip2px) {
        Glide.with(context)
                .load(img)
                .apply(new RequestOptions().transform(new MultiTransformation<>(new CenterInside(), new RoundedCorners(dip2px))))
                .into(card);
    }

    public static void setCircleImg(Context context, Object url, ImageView imageView) {
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing()) {
                if (!((Activity) context).isDestroyed()) {
                    RequestOptions requestOptions = RequestOptions.circleCropTransform();
                    Glide.with(context).load(url).apply(requestOptions).into(imageView);
                }
            }
        } else {
            RequestOptions requestOptions = RequestOptions.circleCropTransform();
            Glide.with(context).load(url).apply(requestOptions).into(imageView);
        }
    }

    /**
     * 判断Activity是否Destroy
     *
     * @return
     */
    public static boolean isDestroy(Context context) {
        if (context instanceof Activity)
            return ((Activity) context).isFinishing() || ((Activity) context).isDestroyed();
        else
            return false;
    }
}
