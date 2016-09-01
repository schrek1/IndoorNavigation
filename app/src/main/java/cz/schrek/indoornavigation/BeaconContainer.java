package cz.schrek.indoornavigation;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ondra on 30. 8. 2016.
 */
public class BeaconContainer implements BeaconListSource {
    private List<BeaconView> allBeacons = new ArrayList<>();
    private List<BeaconView> activeBeacons = new ArrayList<>();

    private PositionCalculator calculator;

    public BeaconContainer(PositionCalculator calculator) {
        this.calculator = calculator;
    }

    public void addBeacon(BeaconView beacon) {
        allBeacons.add(beacon);
    }

    public BeaconView getBeacon(String mac) {
        for (BeaconView bc : allBeacons) {
            if (bc.getMac().equals(mac)) {
                return bc;
            }
        }
        return null;
    }

    public List<BeaconView> getAllBeacons() {
        return allBeacons;
    }

    public void updateActiveList(BeaconView bc) {
        BeaconView updatedBC = null;
        if ((updatedBC = findInActive(bc)) != null) {
            updatedBC.addLive();
            updatedBC.setDistance(bc.getDistance(), true);
        } else {
            bc.setInitLives();
            bc.invalidate();
            activeBeacons.add(bc);
            updatedBC = bc;
        }
        decreaseLives(updatedBC);

        calculator.vectorCalc(this);

    }

    private void decreaseLives(BeaconView findBC) {
        Iterator<BeaconView> iter = activeBeacons.iterator();

        while (iter.hasNext()) {
            BeaconView tmp = iter.next();
            if (tmp != findBC) {
                tmp.decraseLive();
                if (tmp.getLives() == 0) {
                    iter.remove();
                }
            }
        }
    }

    public List<BeaconView> getActiveBeacons() {
        return this.activeBeacons;
    }

    private BeaconView findInActive(BeaconView bc) {
        for (BeaconView tmp : activeBeacons) {
            if (tmp.getMac().equals(bc.getMac())) {
                return tmp;
            }
        }
        return null;
    }

}
