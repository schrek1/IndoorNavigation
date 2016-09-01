package cz.schrek.indoornavigation;

import java.util.List;

/**
 * Created by ondra on 1. 9. 2016.
 */
public interface BeaconListSource {

    public List<BeaconView> getActiveBeacons();
}
