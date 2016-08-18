package cz.schrek.indoornavigation;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

/**
 * Trida obsluhujici vykreslovani na mape
 * <p>
 * <p>
 * Created by ondra on 18. 8. 2016.
 */
public class IndoorMap extends SubsamplingScaleImageView {

    private PositionPoint positionPoint;
    private DistanceConverter distConv;

    public IndoorMap(Context context) {
        super(context);
    }

    public IndoorMap(Context context, AttributeSet attr) {
        super(context, attr);
    }


    public void setPosition(PositionPoint positionPoint) {
        this.positionPoint = positionPoint;
        invalidate();
    }

    public PointF getPosition() {
        return this.positionPoint;
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

        if (positionPoint != null) {



            float vX = positionPoint.getRelPosX();
            float vY = positionPoint.getRelPosY();


            DistanceConverter dc = new DistanceConverter(getContext());
            float[] loc = dc.relToAbs(vX, vY, this);
            PointF pp = new PointF(loc[0], loc[1]);
            PointF vPin = sourceToViewCoord(pp);

            canvas.drawBitmap(positionPoint.getIcon(), positionPoint.getIconPosX(loc[0]), positionPoint.getIconPosY(loc[1]), paint);


        }

    }


}
