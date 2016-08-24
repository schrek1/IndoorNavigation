package cz.schrek.indoornavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.qozix.tileview.TileView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BeaconReciever, PositionReceiver {

    private Button centerBut;
    private TileView tile;
    private TextView beaconLab;
    private TextView positionLab;
    private DistanceConverter dc;
    private Button distPlus, distMinus;

    private List<Beacon> beacons = new ArrayList<>();
    private PositionIndicator position;


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
                Beacon selected = null;
                for (Beacon beacon : beacons) {
                    if (beacon.isSelected()) {
                        selected = beacon;
                        break;
                    }
                }
                if (selected != null) {
                    float dist = selected.getDistance() - 10;
                    selected.setDistance((dist > 0) ? dist : 0);
                }
            }
        });

        distPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Beacon selected = null;
                for (Beacon beacon : beacons) {
                    if (beacon.isSelected()) {
                        selected = beacon;
                        break;
                    }
                }
                if (selected != null) {
                    float dist = selected.getDistance() + 10;
                    selected.setDistance(dist);
                }
            }
        });


        beacons.add(new Beacon(getApplicationContext(), "24:6F", 10, 20, this));
        beacons.add(new Beacon(getApplicationContext(), "33:45", 10, 390, this));
        beacons.add(new Beacon(getApplicationContext(), "24:57", 690, 10, this));
        beacons.add(new Beacon(getApplicationContext(), "34:03", 690, 390, this));
        beacons.add(new Beacon(getApplicationContext(), "29:03", 350, 390, this));


        tile.setPadding(0, 0, 0, 0);
        tile.setSize(827, 486);
        tile.addDetailLevel(1f, "tile-%d_%d.png");
        tile.setShouldRenderWhilePanning(true);


        for (Beacon beacon : beacons) {
            tile.addMarker(beacon, 0, 0, null, null);
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
        for (Beacon beacon : beacons) {
            beacon.setSelected(false);
        }
    }

    @Override
    public void recievePositonInfo(String info) {
        positionLab.setText(info);
    }
}
