package com.example.kushal.Handy;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.widget.ImageView;

/**
 * Created by Matthias on 16-02-20.
 *
 * Used for ImageView in AddPhoto.
 * Contains three variables: // Photo / Image / Handwriting = {Bitmap source, Bitmap bigVersion, boolean isSourceSize
 */
public class ClickableImage
{
    private ImageView imageView;        // tag. ImageView contains ClickableImage
    private Bitmap source;
    private Bitmap bigVersion;
//    private int scale;
    private boolean isSourceSize;

    public ClickableImage(ImageView imageView, Bitmap source, int scale)
    {
        this.imageView = imageView;
        this.source = source;
//        this.scale = scale;
        this.isSourceSize = true;

        int reqWidth = source.getWidth() * scale;
        int reqHeight = source.getHeight() * scale;
        this.bigVersion = makeScaledBitmap(source, reqWidth, reqHeight);
    }

    // http://stackoverflow.com/questions/24961797/android-resize-bitmap-keeping-aspect-ratio
    // http://stackoverflow.com/questions/12778806/android-get-bitmap-width-and-height-after-scaled-by-matrix
    private Bitmap makeScaledBitmap(Bitmap b, int reqWidth, int reqHeight)
    {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, b.getWidth(), b.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
    }

    // in the current version, the tag image view will switch between the small size and the big size.
    // maybe later, show a fullscreen to perform "see big size / rotate / write on it / etc"
    void performClick()
    {
        isSourceSize = !isSourceSize;

        Bitmap target;
        if (isSourceSize)
            target = bigVersion;
        else
            target = source;

        imageView.setImageBitmap(target);
    }
}
