package hachtech.tannersdev.com.hacktech;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    private Runnable mTimer2;
    private LineGraphSeries<DataPoint> series;
    private double graph2LastXValue = 5d;
//    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GraphView graph = (GraphView) findViewById(R.id.temp_graph);

        graph.getViewport().setXAxisBoundsManual(true);

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);

        series = new LineGraphSeries<>();
        graph.addSeries(series);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("data/dataset");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                float x = dataSnapshot.child("x").getValue(Float.class);
                float y = dataSnapshot.child("y").getValue(Float.class);
                Log.d("FIREBASE", "x is: " + x);
                Log.d("FIREBASE", "y is: " + y);

                series.appendData(new DataPoint(x, y), true, 100);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });





//
//        GraphView graph2 = (GraphView) fragView.findViewById(R.id.graph2);
//        mSeries2 = new LineGraphSeries<>();
//        graph2.addSeries(mSeries2);
//        graph2.getViewport().setXAxisBoundsManual(true);
//        graph2.getViewport().setMinX(0);
//        graph2.getViewport().setMaxX(40);
//
//        new Runnable() {
//            @Override
//            public void run() {
//
////                for(int i = 0 ; i < 100000; i++)
////                    series.resetData(generateData());
////                    Log.i("LOOP", "DEBUG");
//                graph2LastXValue += 5d;
//                series.appendData(new DataPoint(graph2LastXValue, getRandom()), true, 40);
//            }
//        }.run();
//
//
//        mTimer2 = new Runnable() {
//            @Override
//            public void run() {
//
//
//                graph2LastXValue += 1d;
//                series.appendData(new DataPoint(graph2LastXValue, getRandom()), true, 40);
//                mHandler.postDelayed(this, 200);
//            }
//        };
//        mHandler.postDelayed(mTimer2, 1000);


//
//
//        final Handler handler = new Handler();
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                graph2LastXValue += 1d;
//                Log.i("DEBUG", "DEBUG : " + graph2LastXValue);
//                series.appendData(new DataPoint(graph2LastXValue, getRandom()), true, 40);
//
//                handler.postDelayed(this, 500);
//            }
//        }, 1500);



    }

//    private DataPoint[] generateData() {
//        int count = 30;
//        DataPoint[] values = new DataPoint[count];
//        for (int i=0; i<count; i++) {
//            double x = i;
//            double f = mRand.nextDouble()*0.15+0.3;
//            double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
//            DataPoint v = new DataPoint(x, y);
//            values[i] = v;
//        }
//        return values;
//    }
//
//    double mLastRandom = 2;
//    Random mRand = new Random();
//
//    private double getRandom() {
//        return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
//    }
}


//public class RealtimeUpdates extends Fragment {
//    private final Handler mHandler = new Handler();
//    private Runnable mTimer1;
//    private Runnable mTimer2;
//    private LineGraphSeries<DataPoint> mSeries1;
//    private LineGraphSeries<> mSeries2;
//    private double graph2LastXValue = 5d;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
//
//        GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
//        mSeries1 = new LineGraphSeries<>(generateData());
//        graph.addSeries(mSeries1);
//
//        GraphView graph2 = (GraphView) rootView.findViewById(R.id.graph2);
//        mSeries2 = new LineGraphSeries<>();
//        graph2.addSeries(mSeries2);
//        graph2.getViewport().setXAxisBoundsManual(true);
//        graph2.getViewport().setMinX(0);
//        graph2.getViewport().setMaxX(40);
//
//        return rootView;
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mTimer1 = new Runnable() {
//            @Override
//            public void run() {
//                mSeries1.resetData(generateData());
//                mHandler.postDelayed(this, 300);
//            }
//        };
//        mHandler.postDelayed(mTimer1, 300);
//
//        mTimer2 = new Runnable() {
//            @Override
//            public void run() {
//                graph2LastXValue += 1d;
//                mSeries2.appendData(new DataPoint(graph2LastXValue, getRandom()), true, 40);
//                mHandler.postDelayed(this, 200);
//            }
//        };
//        mHandler.postDelayed(mTimer2, 1000);
//    }
//
//    @Override
//    public void onPause() {
//        mHandler.removeCallbacks(mTimer1);
//        mHandler.removeCallbacks(mTimer2);
//        super.onPause();
//    }
//
//    private DataPoint[] generateData() {
//        int count = 30;
//        DataPoint[] values = new DataPoint[count];
//        for (int i=0; i<count; i++) {
//            double x = i;
//            double f = mRand.nextDouble()*0.15+0.3;
//            double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
//            DataPoint v = new DataPoint(x, y);
//            values[i] = v;
//        }
//        return values;
//    }
//
//    double mLastRandom = 2;
//    Random mRand = new Random();
//    private double getRandom() {
//        return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
//    }
//}