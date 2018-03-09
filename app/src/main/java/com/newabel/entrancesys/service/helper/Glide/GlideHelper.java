package com.newabel.entrancesys.service.helper.Glide;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.newabel.entrancesys.R;

/**
 * @desc
 * @auth clh
 * @time 2017/3/28.
 */
public class GlideHelper {

    public static RequestOptions mRequestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.mipmap.ic_user_head_default)
            .error(R.mipmap.ic_user_head_default)
            .transform(new GlideCircleTransformation());

    /**展示圆形图片
     * @param context
     * @param avatar
     * @param iv
     */
    public static void showAvatar(Context context, String avatar, ImageView iv) {
        Glide.with(context)
                .load(avatar)
                .apply(mRequestOptions)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(iv);
    }

    /**展示圆形图片
     * @param context
     * @param avatar
     * @param iv
     */
    public static void showAvatar(Context context, Integer  avatar, ImageView iv) {
        Glide.with(context)
                .load(avatar)
                .apply(mRequestOptions)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(iv);
    }

    /**展示图片
     * @param context
     * @param imageUrl
     * @param iv
     */
    public static void showImage(Context context, String imageUrl, ImageView iv) {
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(200))
                .thumbnail(0.1f)
                .into(iv);
    }
    /**展示图片
     * @param context
     * @param imageUrl
     * @param iv
     */
    public static void showImage(Context context, Integer imageUrl, ImageView iv) {
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(200))
                .thumbnail(0.1f)
                .into(iv);
    }
}
