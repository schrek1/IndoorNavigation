package cz.schrek.indoornavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.qozix.tileview.TileView;

public class MainActivity extends AppCompatActivity {

    private Button centerBut;
    private TileView tile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        centerBut = (Button) findViewById(R.id.center);
        tile = (TileView) findViewById(R.id.tile);


        tile.setSize(827, 486);
        tile.addDetailLevel(1f, "tile-%d_%d.png");
        tile.setShouldRenderWhilePanning(true);


        centerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tile.post(new Runnable() {
                    @Override
                    public void run() {
                        ImageView img = new ImageView(getApplicationContext());
                        img.setImageResource(R.drawable.pushpin_blue);
//                        double x =
                        tile.addMarker(img, 0, 0, null, null);
                    }
                });
            }
        });


    }
}
