package cz.schrek.indoornavigation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by ondra on 18. 8. 2016.
 */
public class PositionPoint extends PointF {

    private Bitmap icon;
    private Context context;

    public PositionPoint(float x, float y, Context context) {
        super(x, y);
        this.context = context;
        setIcon();
    }

    private void setIcon() {
        float density = context.getResources().getDisplayMetrics().densityDpi;
        icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pushpin_blue);
        float w = (density / 420f) * icon.getWidth() * 0.5f;
        float h = (density / 420f) * icon.getHeight() * 0.5f;
        icon = Bitmap.createScaledBitmap(icon, (int) w, (int) h, true);
    }


    public float getRelPosX() {
        return x;
    }

    public float getRelPosY() {
        return this.y;
    }

    public Bitmap getIcon(){
        return icon;
    }

    public float getIconPosX(float x){
        return x - (icon.getWidth() / 2);
    }

    public float getIconPosY(float y){
        return y - icon.getHeight();
    }

    public void setRelPosX(float x) {
        this.x = x;
    }

    public void setRelPosY(float y) {
        this.y = y;
    }


}
