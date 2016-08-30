package cz.schrek.indoornavigation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.Random;

/**
 * Created by ondra on 23. 8. 2016.
 */
public class BeaconView extends MapElement {
    private static final int TOUCH_AREA = 30;
    private static final float CIRCLE_SIZE = 20;
    private static final float CIRCLE_STROKE = 3;
    private final int CIRCLE_COLOR;


    private BeaconReciever activity;

    private String mac; //adresa majaku
    private float distance = 0;

    public BeaconView(Context context, String id, float roomPosX, float roomPosY, BeaconReciever activity) {
        super(context, roomPosX, roomPosY);
        this.activity = activity;
        this.mac = id;
        Random rnd = new Random();
        CIRCLE_COLOR = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw beacon
        Paint style = new Paint();
        style.setColor((!selected) ? Color.RED : Color.BLUE);
        style.setStyle(Paint.Style.FILL);
        canvas.drawCircle(pixPosX, pixPosY, CIRCLE_SIZE, style);

        // draw radius around beacon
        if (distance != 0) {
            style = new Paint();
            style.setColor(CIRCLE_COLOR);
            style.setStyle(Paint.Style.STROKE);
            style.setStrokeWidth(CIRCLE_STROKE);
            canvas.drawCircle(pixPosX, pixPosY, distance + CIRCLE_SIZE, style);

            style = new Paint();
            style.setColor(CIRCLE_COLOR);
            style.setStyle(Paint.Style.STROKE);
            style.setStrokeWidth(CIRCLE_STROKE + 4);
            canvas.drawCircle(pixPosX, pixPosY, CIRCLE_SIZE, style);

        }

        if (selected) {
            activity.recieveBeaconInfo(toString());
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touched_x = event.getX();
        float touched_y = event.getY();

        if (touched_x > (pixPosX - TOUCH_AREA) && touched_x < (pixPosX + TOUCH_AREA)) {
            if (touched_y > (pixPosY - TOUCH_AREA) && touched_y < (pixPosY + TOUCH_AREA)) {
                activity.recieveBeaconInfo(toString());
                activity.unselectAllBeacon();
                setSelected(true);
                return true;
            }
        }
        return false;
    }

    public void setDistance(float distance) {
        this.distance = distance;
        invalidate();
    }

    public float getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Beacon{\n" +
                "mac='" + mac + '\'' +
                "\n, dist= " + distance +
//                "\n, pixPosX=" + pixPosX + " px" +
//                "\n, pixPosY=" + pixPosY + " px" +
//                "\n, roomPosX=" + roomPosX + "cm" +
//                "\n, roomPosY=" + roomPosY + " cm" +
                '}';
    }

    public String getMac() {
        return mac;
    }
}
