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
public class Beacon extends View implements View.OnClickListener {
    private final float CIRCLE_SIZE = 20;
    private int CIRCLE_COLOR;

    private DistanceConverter distConvert;
    private Context context;
    private BeaconReciever activity;

    private String id; //adresa majaku
    private boolean selected = false;

    //umisteni majaku v mistnosti (cm)
    private float roomPosX;
    private float roomPosY;

    //umisteni na  bitmape (px)
    private float pixPosX;
    private float pixPosY;

    private float distance = 0;

    private Bitmap icon; //ikona majaku
    private float[] bitmapSize = new float[]{30, 30};

    public Beacon(Context context, String id, float x, float y, BeaconReciever activity) {
        super(context);
        this.context = context;
        this.activity = activity;
        distConvert = new DistanceConverter(context);
        this.id = id;
        setRoomPos(x, y); // umisteni upravovat pouze pres setRoomPos tak aby se aktualizovalo i umisteni na bitmape

        Random rnd = new Random();
        CIRCLE_COLOR = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint style = new Paint();
        style.setColor((!selected) ? Color.RED : Color.BLUE);
        style.setStyle(Paint.Style.FILL);
        canvas.drawCircle(pixPosX, pixPosY, CIRCLE_SIZE, style);

        style = new Paint();
        style.setColor(CIRCLE_COLOR);
        style.setStyle(Paint.Style.STROKE);
        style.setStrokeWidth(3);
        canvas.drawCircle(pixPosX, pixPosY, distance, style);
    }

    public void setRoomPos(float x, float y) {
        this.roomPosX = x;
        this.roomPosY = y;
        setPixPosX(x, y);

    }

    private void setPixPosX(float x, float y) {
        float[] val = distConvert.cmToPx(x, y);
        pixPosX = val[0];
        pixPosY = val[1];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touched_x = event.getX();
        float touched_y = event.getY();

        if (touched_x > (pixPosX - 30) && touched_x < (pixPosX + 30)) {
            if (touched_y > (pixPosY - 30) && touched_y < (pixPosY + 30)) {
                activity.recieveBeaconInfo(toString());
                activity.unselectAllBeacon();
                selected = true;
                invalidate();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this.context, roomPosX + "", Toast.LENGTH_SHORT).show();
    }


    public void setSelected(boolean selected) {
        this.selected = selected;
        invalidate();
    }

    public boolean isSelected() {
        return selected;
    }


    public void setDistance(float distance) {
        this.distance = distance;
        invalidate();
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

    public double[] getRoomPos() {
        return new double[]{this.roomPosX, this.roomPosY};
    }

    public double[] getPixPos() {
        return new double[]{this.pixPosX, this.pixPosY};
    }

    public float getDistance() {
        return distance;
    }
}
