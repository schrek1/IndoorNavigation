package cz.schrek.indoornavigation;

/**
 * Created by ondra on 24. 8. 2016.
 */
public interface BeaconReciever {
    public void recieveBeaconInfo(String info);

    public void unselectAllBeacon();
}
