package cz.schrek.indoornavigation;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.davemorrissey.labs.subscaleview.ImageSource;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button rotateBut;
    private Button pointBut;
    private IndoorMap indoorMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indoorMap = (IndoorMap) findViewById(R.id.imageView);
        rotateBut = (Button) findViewById(R.id.rotateBut);
        pointBut = (Button) findViewById(R.id.pointBut);

        indoorMap.setImage(ImageSource.resource(R.drawable.map));

        indoorMap.setPin(new PointF(20,20));


        rotateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indoorMap.setRotation(indoorMap.getRotation() + 10);
            }
        });

        pointBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rand = new Random();
                int x = rand.nextInt((indoorMap.getWidth()) + 1);
                int y = rand.nextInt((indoorMap.getHeight()) + 1);
                indoorMap.setPin(new PointF(x,y));
            }
        });

    }
}
