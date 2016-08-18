package cz.schrek.indoornavigation;

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

    public float[] relToAbs(float x, float y, IndoorMap map) {
        int[] loc = new int[2];
        map.getLocationOnScreen(loc);

        float constanat = map.getY() +
                map.getPivotY() +
                map.getScaleY() +
                map.getScrollY() +
                map.getPaddingTop() +
                map.getTranslationY() +
                map.getTop();

        constanat = map.getY();
        Log.wtf("const", constanat + "");
        y = ((map.getHeight() - IMG_HEIGHT) / 2.0f)  + y;

        constanat = map.getLeft() +
                map.getPaddingLeft() +
                map.getHorizontalFadingEdgeLength() +
                map.getX() +
                map.getScrollX() +
                map.getPivotX() +
                map.getScaleX() +
                map.getTranslationX();

        constanat= map.getLeft();
        Log.wtf("const", constanat + "");
        x = ((map.getWidth() - IMG_WIDTH) / 2.0f) + x ;

        return new float[]{x, y};
    }

}
