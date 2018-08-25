package ir.porya_gohary.serverconnect.fragments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import ir.porya_gohary.serverconnect.DateAsXAxisLabelFormatter2;
import ir.porya_gohary.serverconnect.R;
import ir.porya_gohary.serverconnect.Urls;
import ir.porya_gohary.serverconnect.Inputs;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class charts extends Fragment {


    public charts() {
        // Required empty public constructor
    }

    GraphView graph1,graph2;
    LineGraphSeries<DataPoint> mSeries,mSeries2;
    Runnable mTimer;
    final Handler mHandler = new Handler();
    double graphLastXValue = 5d;
    String username;
    String time;
    String time2[]={"00","00","00"};
    long i=0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_charts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        graph1=(GraphView) getActivity().findViewById(R.id.graph1);
        graph2=(GraphView) getActivity().findViewById(R.id.graph2);
        initGraph(graph1);
        setmSeries();
        initGraph(graph2);
        setmSeries2();

        AndroidNetworking.initialize(getContext());

        SharedPreferences preferences=getActivity().getSharedPreferences("prefs",MODE_PRIVATE);
        username=preferences.getString("username","");

    }

    private void initGraph(GraphView graph1) {
        graph1.getViewport().setXAxisBoundsManual(true);
        graph1.getViewport().setMinY(0);
        graph1.getViewport().setMaxY(330);
        graph1.getViewport().setMinX(0);
        graph1.getViewport().setMaxX(4);
        graph1.getGridLabelRenderer().setVerticalAxisTitle("ولتاژ(mV)");
        graph1.getGridLabelRenderer().setHorizontalAxisTitle("زمان");
        graph1.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter2(graph1.getContext()));
        graph1.getViewport().setScalable(true);





        // styling grid/labels
        graph1.getGridLabelRenderer().setHighlightZeroLines(false);
        graph1.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.LEFT);
        graph1.getGridLabelRenderer().setLabelVerticalWidth(100);
        graph1.getGridLabelRenderer().setTextSize(40);

        graph1.getGridLabelRenderer().setHorizontalLabelsAngle(120);
        graph1.getGridLabelRenderer().reloadStyles();



    }

    void setmSeries(){
        // first mSeries is a line
        mSeries = new LineGraphSeries<>();
        mSeries.setDrawDataPoints(true);
        mSeries.setDrawBackground(true);
        graph1.addSeries(mSeries);
    }

    void setmSeries2(){
        // first mSeries is a line
        mSeries2 = new LineGraphSeries<>();
        mSeries2.setDrawDataPoints(true);
        mSeries2.setDrawBackground(true);
        graph2.addSeries(mSeries2);
        mSeries2.setColor(Color.argb(255, 255, 60, 60));
        mSeries2.setBackgroundColor(Color.argb(100, 204, 119, 119));
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer = new Runnable() {
            @Override
            public void run() {
                graphLastXValue += 1d;
                //mSeries.appendData(new DataPoint(10, 100), true, 22);
                AndroidNetworking.post(Urls.host+Urls.getInputs)
                        .addBodyParameter("username",username)
                        .setTag("LOGIN")
                        .build()
                        .getAsObject(Inputs.class, new ParsedRequestListener<Inputs>() {
                            @Override
                            public void onResponse(Inputs response) {
                               // Toast.makeText(getContext()," "+response.getAnalog1()+" ",Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getContext()," "+response.getTime()+" ",Toast.LENGTH_SHORT).show();
                               time=response.getTime();
                                time2=time.split(":");
                                long x=1000*(((Long.parseLong(time2[0])-3)*3600)+((Long.parseLong(time2[1])-30)*60)+Long.parseLong(time2[2]));
                                //Toast.makeText(getContext()," "+x+" ",Toast.LENGTH_SHORT).show();

//
//                                Calendar c;
//                                c=Calendar.getInstance();
//                                c.set(2018,07,22,Integer.parseInt(time2[0]),Integer.parseInt(time2[1]),Integer.parseInt(time2[2]));
//                                Date d=c.getTime();


                                mSeries.appendData(new DataPoint(x, response.getAnalog1()), true, 22);
                                mSeries2.appendData(new DataPoint(x, response.getAnalog2()), true, 100);
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("خطا",String.valueOf(anError));
                                Toast.makeText(getContext(),"خطا در دریافت اطلاعات",Toast.LENGTH_SHORT).show();
                                onPause();
                            }
                        });


                mHandler.postDelayed(this, 1000);
                i++;
            }
        };
        mHandler.postDelayed(mTimer, 1000);

    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mTimer);
    }


    double mLastRandom = 2;
    Random mRand = new Random();
    private double getRandom() {
        return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
    }








}
