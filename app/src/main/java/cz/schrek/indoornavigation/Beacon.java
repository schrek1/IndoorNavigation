package cz.schrek.indoornavigation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by ondra on 23. 8. 2016.
 */
public class Beacon extends MapElement implements View.OnClickListener {
    private final float CIRCLE_SIZE = 20;
    private final float CIRCLE_STROKE = 3;
    private final int CIRCLE_COLOR;


    private BeaconReciever activity;

    private String id; //adresa majaku
    private float distance = 0;

    public Beacon(Context context, String id, float roomPosX, float roomPosY, BeaconReciever activity) {
        super(context, roomPosX, roomPosY);
        this.activity = activity;
        this.id = id;
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
        style = new Paint();
        style.setColor(CIRCLE_COLOR);
        style.setStyle(Paint.Style.STROKE);
        style.setStrokeWidth(CIRCLE_STROKE);
        canvas.drawCircle(pixPosX, pixPosY, distance, style);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touched_x = event.getX();
        float touched_y = event.getY();

        if (touched_x > (pixPosX - 30) && touched_x < (pixPosX + 30)) {
            if (touched_y > (pixPosY - 30) && touched_y < (pixPosY + 30)) {
                activity.recieveBeaconInfo(toString());
                activity.unselectAllBeacon();
                setSelected(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this.context, roomPosX + "", Toast.LENGTH_SHORT).show();
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
                "id='" + id + '\'' +
                "\n, pixPosX=" + pixPosX +
                "\n, pixPosY=" + pixPosY +
                "\n, roomPosX=" + roomPosX +
                "\n, roomPosY=" + roomPosY +
                '}';
    }

}
