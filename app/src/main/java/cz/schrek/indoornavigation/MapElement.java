package cz.schrek.indoornavigation;

import android.content.Context;
import android.view.View;

/**
 * Created by ondra on 24. 8. 2016.
 */
public class MapElement  extends View {

    protected DistanceConverter distConvert;
    protected Context context;

    //umisteni majaku v mistnosti (cm)
    protected float roomPosX;
    protected float roomPosY;

    //umisteni na  bitmape (px)
    protected float pixPosX;
    protected float pixPosY;

    protected boolean selected = false;


    public MapElement(Context context, float roomPosX, float roomPosY) {
        super(context);
        this.context = context;
        distConvert = new DistanceConverter(context);

        setRoomPos(roomPosX, roomPosY); // umisteni upravovat pouze pres setRoomPos tak aby se aktualizovalo i umisteni na bitmape

    }

    public void setRoomPos(float x, float y) {
        this.roomPosX = x;
        this.roomPosY = y;
        setPixPosX(x, y);
    }

    protected void setPixPosX(float x, float y) {
        float[] val = distConvert.cmToPx(x, y);
        pixPosX = val[0];
        pixPosY = val[1];
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        invalidate();
    }

    public boolean isSelected() {
        return selected;
    }
}
