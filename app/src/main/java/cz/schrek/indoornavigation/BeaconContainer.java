package cz.schrek.indoornavigation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ondra on 30. 8. 2016.
 */
public class BeaconContainer {
    private List<BeaconView> beaconViews = new ArrayList<>();

    public void addBeacon(BeaconView beacon) {
        beaconViews.add(beacon);
    }

    public BeaconView getBeacon(String mac) {
        for (BeaconView bc : beaconViews) {
            if (bc.getMac().equals(mac)) {
                return bc;
            }
        }
        return null;
    }

    public List<BeaconView> getBeaconViews() {
        return beaconViews;
    }

}
