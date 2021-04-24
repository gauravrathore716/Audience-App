package com.b2c.audience;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class DataBindingAdapter {

    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        try {
            textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/" + fontName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"bind:et_font"})
    public static void setFont(EditText editText, String fontName) {
        try {
            editText.setTypeface(Typeface.createFromAsset(editText.getContext().getAssets(), "fonts/" + fontName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @BindingAdapter({"bind:src_profile_url"})
    public static void setProfileSrcUrl(final ImageView view, String sourceUrl) {
        if (sourceUrl == null) {
            view.setImageResource(R.drawable.logo);
        } else {
            GlideApp.with(view.getContext())
                    .load(sourceUrl)
                    .apply(new RequestOptions().fitCenter())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo).into(view);
        }
    }   @BindingAdapter({"bind:src_profile_url"})
    public static void setProfileSrcUrl1(final ImageView view, String sourceUrl) {
        if (sourceUrl == null) {
            view.setImageResource(R.drawable.logo);
        } else {
            GlideApp.with(view.getContext())
                    .load(sourceUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo).into(view);
        }
    }

    @BindingAdapter({"bind:src_url"})
    public static void setSrcUrl(final ImageView view, String sourceUrl) {
        if (sourceUrl != null) {
            GlideApp.with(view.getContext())
                    .load(sourceUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view);
        }
    }
}
