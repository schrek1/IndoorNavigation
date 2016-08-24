package cz.schrek.indoornavigation;

/**
 * Created by ondra on 23. 8. 2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.net.ConnectException;

/**
 * Created by ondra on 18. 8. 2016.
 */
public class DistanceConverter {

    public static final float REAL_HEIGHT = 415;
    public static final float REAL_WIDTH = 700;

    public static float IMG_HEIGHT;
    public static float IMG_WIDTH;

    public static int imageIndex = R.drawable.map;

    public DistanceConverter(Context context) {
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), imageIndex, dimensions);
        IMG_HEIGHT = dimensions.outHeight;
        IMG_WIDTH = dimensions.outWidth;
    }


    public float[] cmToPx(float x, float y) {
        float[] dimens = new float[2];

        //x dimension
        dimens[0] = (float) (IMG_WIDTH / REAL_WIDTH * x);
        //y dimension
        dimens[1] = (float) (IMG_HEIGHT / REAL_HEIGHT * y);

        return dimens;
    }

    public float[] pxToCm(float x, float y) {
        float[] dimens = new float[2];

        //x dimension
        dimens[0] = (float) (REAL_WIDTH / IMG_WIDTH * x);
        //y dimension
        dimens[1] = (float) (REAL_HEIGHT / IMG_HEIGHT * y);

        return dimens;
    }

}