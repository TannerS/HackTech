package hachtech.tannersdev.com.hacktech;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONObject;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private TextView intensity;
    private TextView heartbeat;
    private TextView temp;
    private LineGraphSeries<DataPoint> series;
    private float t = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CardView card = (CardView) findViewById(R.id.main_card_view);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION_CODES.LOLLIPOP == Build.VERSION.SDK_INT)
        {
            params.setMargins(15, 15, 15,
                    15);
        } else {
            card.setMaxCardElevation(15);
        }
        card.setCardElevation(15);
        card.setLayoutParams(params);


        GraphView graph = (GraphView) findViewById(R.id.heart_graph);

        graph.getViewport().setXAxisBoundsManual(true);

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(100);
        graph.getViewport().setMaxY(100);
        graph.getViewport().setMinY(0);

        series = new LineGraphSeries<>();
        graph.addSeries(series);

        intensity = (TextView) findViewById(R.id.hurt_joints_id);


        FirebaseDatabase fire_base_database = FirebaseDatabase.getInstance();
        
        DatabaseReference flex_ref = fire_base_database.getReference("live/flex");

        flex_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float flex = dataSnapshot.child("intensity").getValue(Float.class);
                intensity.setText(Float.toString(flex));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });

        intensity.setText("");



        heartbeat = (TextView) findViewById(R.id.heart_rate_id);

        DatabaseReference beat = fire_base_database.getReference("live/heartbeat/");

        // Read from the fire_base+database
        beat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i("DEBUG:", dataSnapshot.toString());
                Log.i("DEBUG:", dataSnapshot.child("change").toString());
//                Log.i("DEBUG:", dataSnapshot.child("bpm").getValue().toString());

                float beat =  dataSnapshot.child("change").getValue(Float.class);

                t += .5;
                series.appendData(new DataPoint(t, beat), true, 100);


                heartbeat.setText(Float.toString(beat));


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });


        heartbeat.setText("");


        temp = (TextView) findViewById(R.id.temp_id);


        DatabaseReference temp_ref = fire_base_database.getReference("live/temp");

        // Read from the fire_base+database
        temp_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float temp_ = ( dataSnapshot.child("F").getValue(Float.class));
                temp.setText(Float.toString(temp_));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });

        temp.setText("");
















    }

}