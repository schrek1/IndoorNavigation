package cz.schrek.indoornavigation;


import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.qozix.tileview.TileView;
import org.altbeacon.beacon.*;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

import java.util.Collection;

public class MainActivity extends AppCompatActivity implements BeaconReciever, PositionReceiver, BeaconConsumer {
    private static final String BEACON_LAYOUT = "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25";

    private Button centerBut;
    private TileView tile;
    private TextView beaconLab;
    private TextView positionLab;
    private DistanceConverter dc;
    private Button distPlus, distMinus;

    private PositionCalculator calculator = new PositionCalculator(this);
    private BeaconContainer bcontainer = new BeaconContainer(calculator);
    private PositionIndicator position;

    private BackgroundPowerSaver backgroundPowerSaver;
    private BeaconManager beaconManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        centerBut = (Button) findViewById(R.id.center);
        tile = (TileView) findViewById(R.id.tile);
        dc = new DistanceConverter(getApplicationContext());
        beaconLab = (TextView) findViewById(R.id.beaconLab);
        positionLab = (TextView) findViewById(R.id.positonLab);
        distMinus = (Button) findViewById(R.id.distMinus);
        distPlus = (Button) findViewById(R.id.distPlus);

        backgroundPowerSaver = new BackgroundPowerSaver(this);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BEACON_LAYOUT));
        beaconManager.bind(this);

        centerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float[] val = dc.cmToPx(350, 200);
                tile.slideToAndCenter(val[0], val[1]);
            }
        });


        distMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeaconView selected = null;
                for (BeaconView beaconView : bcontainer.getAllBeacons()) {
                    if (beaconView.isSelected()) {
                        selected = beaconView;
                        break;
                    }
                }
                if (selected != null) {
                    float dist = selected.getDistance() - 10;
                    selected.setDistance((dist > 0) ? dist : 0, true);
                }
            }
        });

        distPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeaconView selected = null;
                for (BeaconView beaconView : bcontainer.getAllBeacons()) {
                    if (beaconView.isSelected()) {
                        selected = beaconView;
                        break;
                    }
                }
                if (selected != null) {
                    float dist = selected.getDistance() + 10;
                    selected.setDistance(dist, true);
                }
            }
        });

        bcontainer.addBeacon(new BeaconView(getApplicationContext(), "0C:F3:EE:09:3F:3C", 10, 20, this));
        bcontainer.addBeacon(new BeaconView(getApplicationContext(), "0C:F3:EE:09:3F:E6", 10, 390, this));
        bcontainer.addBeacon(new BeaconView(getApplicationContext(), "0C:F3:EE:09:3B:23", 690, 10, this));


//        bcontainer.addBeacon(new BeaconView(getApplicationContext(), "0C:F3:EE:09:37:D3", 10, 20, this));
//        bcontainer.addBeacon(new BeaconView(getApplicationContext(), "0C:F3:EE:09:38:17", 10, 390, this));
//        bcontainer.addBeacon(new BeaconView(getApplicationContext(), "0C:F3:EE:09:38:37", 690, 10, this));
        bcontainer.addBeacon(new BeaconView(getApplicationContext(), "0C:F3:EE:09:38:03", 690, 390, this));
        bcontainer.addBeacon(new BeaconView(getApplicationContext(), "0C:F3:EE:09:38:02", 350, 390, this));


        tile.setPadding(0, 0, 0, 0);
        tile.setSize(827, 486);
        tile.addDetailLevel(1f, "tile-%d_%d.png");
        tile.setShouldRenderWhilePanning(true);


        for (BeaconView beaconView : bcontainer.getAllBeacons()) {
            tile.addMarker(beaconView, 0, 0, null, null);
        }


        position = new PositionIndicator(this, 350, 200, this);
        tile.addMarker(position, 0, 0, null, null);

    }

    @Override
    public void recieveBeaconInfo(String info) {
        beaconLab.setText(info);
    }

    @Override
    public void unselectAllBeacon() {
        for (BeaconView beaconView : bcontainer.getAllBeacons()) {
            beaconView.setSelected(false);
        }
    }

    @Override
    public void recievePositonInfo(float posX, float posY){
        position.setPixPosX(posX,posY);
        positionLab.setText(position.toString());
    }


    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<org.altbeacon.beacon.Beacon> collection, Region region) {
                if (collection.size() > 0) {
                    final Beacon bc = collection.iterator().next();
                    Log.wtf(bc.getBluetoothAddress(), bc.getDistance() + " "+bc.getRssi()+" "+bc.getTxPower());
                    final BeaconView beacon = bcontainer.getBeacon(bc.getBluetoothAddress());
                    if (beacon != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                beacon.setDistance((float) bc.getDistance() * 100, false);
                                bcontainer.updateActiveList(beacon);
                            }
                        });
                    }
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
}




