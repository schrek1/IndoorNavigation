package cz.schrek.indoornavigation;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

/**
 * Created by ondra on 18. 8. 2016.
 */
public class IndoorMap extends SubsamplingScaleImageView {

    private PointF sPin;
    private Bitmap pin;

    public IndoorMap(Context context) {
        super(context);
    }

    public IndoorMap(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    private void initialise() {
        float density = getResources().getDisplayMetrics().densityDpi;
        pin = BitmapFactory.decodeResource(this.getResources(), R.drawable.pushpin_blue);
        float w = (density / 420f) * pin.getWidth() * 0.5f;
        float h = (density / 420f) * pin.getHeight() * 0.5f;
        pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
    }

    public void setPin(PointF sPin) {
        this.sPin = sPin;
        initialise();
        invalidate();
    }

    public PointF getPin() {
        return this.sPin;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pin before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        if (sPin != null && pin != null) {
            PointF vPin = sourceToViewCoord(sPin);
            float vX = vPin.x - (pin.getWidth() / 2);
            float vY = vPin.y - pin.getHeight();
            canvas.drawBitmap(pin, vX, vY, paint);
        }

    }
}
