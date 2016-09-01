package cz.schrek.indoornavigation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by ondra on 23. 8. 2016.
 */
public class PositionIndicator extends MapElement {

    private PositionReceiver activity;
    private Bitmap pin;

    private static class CircleArea {
        int radius;
        int centerX;
        int centerY;

        CircleArea(int centerX, int centerY, int radius) {
            this.radius = radius;
            this.centerX = centerX;
            this.centerY = centerY;
        }

        @Override
        public String toString() {
            return "Circle[" + centerX + ", " + centerY + ", " + radius + "]";
        }
    }

    public PositionIndicator(Context context, float roomPosX, float roomPosY, PositionReceiver activity) {
        super(context, roomPosX, roomPosY);
        this.activity = activity;

        //init icon
        float density = getResources().getDisplayMetrics().densityDpi;
        pin = BitmapFactory.decodeResource(this.getResources(), R.drawable.pushpin_blue);
        float w = (density / 420f) * pin.getWidth() * 0.5f;
        float h = (density / 420f) * pin.getHeight() * 0.5f;
        pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);

        setRoomPos(roomPosX, roomPosY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(pin, pixPosX, pixPosY, null);
    }

    @Override
    protected void setPixPosX(float x, float y) {
        if (pin != null) {
            float[] val = distConvert.cmToPx(x, y);
            pixPosX = val[0] - (pin.getWidth() / 2);
            pixPosY = val[1] - pin.getHeight();
            invalidate();
        }
    }

    @Override
    public String toString() {
        return "PositionIndicator{" + "\n" +
                "\n, pixPosX=" + pixPosX + " px" +
                "\n, pixPosY=" + pixPosY + " px" +
                "\n, roomPosX=" + roomPosX + " cm" +
                "\n, roomPosY=" + roomPosY + " cm" +
                "}";
    }
}
