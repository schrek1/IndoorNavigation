package cz.schrek.indoornavigation;

import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ondra on 1. 9. 2016.
 */
public class PositionCalculator {

    private PositionReceiver activity;

    public PositionCalculator(PositionReceiver receiver){
        activity = receiver;
    }

    public void vectorCalc(BeaconListSource container) {
        List<BeaconView> beacons = container.getActiveBeacons();

        int count = beacons.size();
        if (count == 1) {

        } else if (count == 2) {

        } else if (count >= 3) {
            Collections.sort(beacons, new Comparator<BeaconView>() {
                @Override
                public int compare(BeaconView lhs, BeaconView rhs) {
                    if (lhs.getDistance() == rhs.getDistance()) {
                        return 0;
                    } else if (lhs.getDistance() > rhs.getDistance()) {
                        return 1;
                    }else{
                        return -1;
                    }
                }
            });

            BeaconView[] nearest = new BeaconView[3];
            nearest[0] = beacons.get(0);
            nearest[1] = beacons.get(1);
            nearest[2] = beacons.get(2);

            float x = (nearest[0].getPixPosX()+nearest[1].getPixPosX()+nearest[2].getPixPosX())/3;
            float y = (nearest[0].getPixPosY()+nearest[1].getPixPosY()+nearest[2].getPixPosY())/3;

            activity.recievePositonInfo(x,y);

            Log.wtf("sorted", beacons.toString());
        }

    }

    public void triangCalc(BeaconContainer container) {

    }

}
