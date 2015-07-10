package ua.southwall.vkbookmarksfeed.util;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * Created by mac on 7/2/15.
 */
public class BlurTransformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap bitmap) {
        Bitmap blurredImage =  BlurEffect.fastblur(bitmap, 10);
        bitmap.recycle();
        return blurredImage;

    }

    @Override
    public String key() {
        return "blur()";
    }
}
