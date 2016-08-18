package cz.schrek.indoornavigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.davemorrissey.labs.subscaleview.ImageSource;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button rotateBut;
    private Button pointBut;
    private IndoorMap indoorMap;
    private TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indoorMap = (IndoorMap) findViewById(R.id.imageView);
        rotateBut = (Button) findViewById(R.id.rotateBut);
        pointBut = (Button) findViewById(R.id.pointBut);
        label = (TextView) findViewById(R.id.label);


        indoorMap.setImage(ImageSource.resource(R.drawable.map));
        indoorMap.setPadding(20, 0, 20, 0);
        indoorMap.setZoomEnabled(false);

        DistanceConverter dc = new DistanceConverter(this);
        float[] dim;
        dim = dc.cmToPx(0, 0);

        indoorMap.setPosition(new PositionPoint(dim[0], dim[1], this));


        rotateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indoorMap.setRotation(indoorMap.getRotation() + 10);
            }
        });

        pointBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DistanceConverter dc = new DistanceConverter(MainActivity.this);
                float[] dim;
                Random rnd = new Random();
                float x = rnd.nextInt(700);
                float y = rnd.nextInt(400);

                dim = dc.cmToPx(350, 200);
                PositionPoint point = new PositionPoint(dim[0], dim[1], MainActivity.this);
                indoorMap.setPosition(point);
                label.setText(point.toString() + "\n");
                label.append(x + "   " + y);
            }
        });

    }
}
