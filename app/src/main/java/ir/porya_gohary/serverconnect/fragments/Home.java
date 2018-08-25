package ir.porya_gohary.serverconnect.fragments;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.jjoe64.graphview.series.DataPoint;

import ir.porya_gohary.serverconnect.Exec;
import ir.porya_gohary.serverconnect.Inputs;
import ir.porya_gohary.serverconnect.Outputs;
import ir.porya_gohary.serverconnect.R;
import ir.porya_gohary.serverconnect.Urls;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    Typeface typeface;
    Switch sw1,sw2,sw3,sw4,sw5;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
    Runnable mTimer;
    final Handler mHandler = new Handler();
    String username;
    Exec ex;



    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Shabnam.ttf");
        sw1=(Switch) getActivity().findViewById(R.id.switch1);
        sw2=(Switch) getActivity().findViewById(R.id.switch2);
        sw3=(Switch) getActivity().findViewById(R.id.switch3);
        sw4=(Switch) getActivity().findViewById(R.id.switch5);
        sw5=(Switch) getActivity().findViewById(R.id.switch6);

        sw1.setTypeface(typeface);
        sw2.setTypeface(typeface);
        sw3.setTypeface(typeface);
        sw4.setTypeface(typeface);
        sw5.setTypeface(typeface);

        tv1=(TextView) getActivity().findViewById(R.id.textView4);
        tv2=(TextView) getActivity().findViewById(R.id.textView7);
        tv3=(TextView) getActivity().findViewById(R.id.textView10);
        tv4=(TextView) getActivity().findViewById(R.id.textView9);
        tv5=(TextView) getActivity().findViewById(R.id.textView14);
        tv6=(TextView) getActivity().findViewById(R.id.textView16);
        tv7=(TextView) getActivity().findViewById(R.id.textView18);
        tv8=(TextView) getActivity().findViewById(R.id.textView20);

        //sw3.setChecked(false);
        AndroidNetworking.initialize(getContext());

        SharedPreferences preferences=getActivity().getSharedPreferences("prefs",MODE_PRIVATE);
        username=preferences.getString("username","");

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==false){
                    AndroidNetworking.post(Urls.host+Urls.setExec)
                            .addBodyParameter("main", String.valueOf(0))
                            .addBodyParameter("analog1", String.valueOf(ex.getAnalog1()))
                            .addBodyParameter("analog2", String.valueOf(ex.getAnalog2()))
                            .addBodyParameter("digital1",ex.getDigital1()+"")
                            .addBodyParameter("digital2",ex.getDigital2()+"")
                            .build()
                            .getAsString(new StringRequestListener() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("fail")){
                                        Toast.makeText(getContext(),"خطا در ارسال اطلاعات",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(getContext(),"خطا در ارسال اطلاعات",Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {

                    AndroidNetworking.post(Urls.host+Urls.setExec)
                            .addBodyParameter("main", String.valueOf(1))
                            .addBodyParameter("analog1", String.valueOf(ex.getAnalog1()))
                            .addBodyParameter("analog2", String.valueOf(ex.getAnalog2()))
                            .addBodyParameter("digital1",ex.getDigital1()+"")
                            .addBodyParameter("digital2",ex.getDigital2()+"")
                            .build()
                            .getAsString(new StringRequestListener() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("fail")){
                                        Toast.makeText(getContext(),"خطا در ارسال اطلاعات",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(getContext(),"خطا در ارسال اطلاعات",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer = new Runnable() {
            @Override
            public void run() {


                AndroidNetworking.post(Urls.host+Urls.getExec)
                        .addBodyParameter("username",username)
                        .setTag("LOGIN")
                        .build()
                        .getAsObject(Exec.class, new ParsedRequestListener<Exec>() {
                            @Override
                            public void onResponse(Exec response)
                            {
                               // Log.d("خطا",String.valueOf(response.getMain()));
                                ex=response;
                                    if(response.getMain()==0){
                                        sw1.setChecked(false);
                                    }else {
                                        sw1.setChecked(true);
                                    }

                                    if(response.getAnalog1()>=0){
                                        tv5.setText("خاموش");
                                    }else{
                                        switch (response.getAnalog1()){
                                            case -1:
                                                tv5.setText("A*B");
                                                break;
                                            case -2:
                                                tv5.setText("B*A");
                                                break;
                                            case -3:
                                                tv5.setText("A*A");
                                                break;
                                            case -4:
                                                tv5.setText("B*B");
                                                break;
                                            case -5:
                                                tv5.setText("A/B");
                                                break;
                                            case -6:
                                                tv5.setText("B/A");
                                                break;
                                            case -7:
                                                tv5.setText("A/A");
                                                break;
                                            case -8:
                                                tv5.setText("B/B");
                                                break;
                                            case -9:
                                                tv5.setText("A+B");
                                                break;
                                            case -10:
                                                tv5.setText("B+A");
                                                break;
                                            case -11:
                                                tv5.setText("A+A");
                                                break;
                                            case -12:
                                                tv5.setText("B+B");
                                                break;
                                            case -13:
                                                tv5.setText("A-B");
                                                break;
                                            case -14:
                                                tv5.setText("B-A");
                                                break;
                                            case -15:
                                                tv5.setText("A-A");
                                                break;
                                            case -16:
                                                tv5.setText("B-B");
                                                break;

                                        }
                                    }

                                if(response.getAnalog2()>=0){
                                    tv6.setText("خاموش");
                                }else{
                                    switch (response.getAnalog2()){
                                        case -1:
                                            tv6.setText("A*B");
                                            break;
                                        case -2:
                                            tv6.setText("B*A");
                                            break;
                                        case -3:
                                            tv6.setText("A*A");
                                            break;
                                        case -4:
                                            tv6.setText("B*B");
                                            break;
                                        case -5:
                                            tv6.setText("A/B");
                                            break;
                                        case -6:
                                            tv6.setText("B/A");
                                            break;
                                        case -7:
                                            tv6.setText("A/A");
                                            break;
                                        case -8:
                                            tv6.setText("B/B");
                                            break;
                                        case -9:
                                            tv6.setText("A+B");
                                            break;
                                        case -10:
                                            tv6.setText("B+A");
                                            break;
                                        case -11:
                                            tv6.setText("A+A");
                                            break;
                                        case -12:
                                            tv6.setText("B+B");
                                            break;
                                        case -13:
                                            tv6.setText("A-B");
                                            break;
                                        case -14:
                                            tv6.setText("B-A");
                                            break;
                                        case -15:
                                            tv6.setText("A-A");
                                            break;
                                        case -16:
                                            tv6.setText("B-B");
                                            break;

                                    }
                                }

                                if(response.getDigital1()>=0){
                                tv7.setText("خاموش");
                            }else {
                                switch (response.getDigital1()){
                                    case -1:
                                        tv7.setText("A AND B");
                                        break;
                                    case -2:
                                        tv7.setText("A OR B");
                                        break;


                                }
                            }

                                if(response.getDigital2()>=0){
                                    tv8.setText("خاموش");
                                }else {
                                    switch (response.getDigital2()){
                                        case -1:
                                            tv8.setText("A AND B");
                                            break;
                                        case -2:
                                            tv8.setText("A OR B");
                                            break;


                                    }
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("خطا",String.valueOf(anError));
                                Toast.makeText(getContext(),"خطا در دریافت اطلاعات",Toast.LENGTH_SHORT).show();
                                onPause();
                            }
                        });

                AndroidNetworking.post(Urls.host+Urls.getInputs)
                        .addBodyParameter("username",username)
                        .setTag("LOGIN")
                        .build()
                        .getAsObject(Inputs.class, new ParsedRequestListener<Inputs>() {

                            @Override
                            public void onResponse(Inputs response) {

                                if(response.getDigital1()==0){
                                    sw2.setChecked(false);
                                }else {
                                    sw2.setChecked(true);
                                }

                                if(response.getDigital2()==0){
                                    sw3.setChecked(false);
                                }else {
                                    sw3.setChecked(true);
                                }
                                tv1.setText(response.getAnalog1()+"");
                                tv2.setText(response.getAnalog2()+"");





                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getContext(),"خطا در دریافت اطلاعات",Toast.LENGTH_SHORT).show();
                                onPause();
                            }
                        });


                AndroidNetworking.post(Urls.host+Urls.getOutputs)
                        .addBodyParameter("username",username)
                        .setTag("LOGIN")
                        .build()
                        .getAsObject(Outputs.class, new ParsedRequestListener<Outputs>() {
                            @Override
                            public void onResponse(Outputs response) {
                                if(response.getDigital1()==0){
                                    sw4.setChecked(false);
                                }else {
                                    sw4.setChecked(true);
                                }

                                if(response.getDigital2()==0){
                                    sw5.setChecked(false);
                                }else {
                                    sw5.setChecked(true);
                                }

                                tv3.setText(response.getAnalog1()+"");
                                tv4.setText(response.getAnalog2()+"");



                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getContext(),"خطا در دریافت اطلاعات",Toast.LENGTH_SHORT).show();
                                onPause();
                            }
                        });


                mHandler.postDelayed(this, 200);

            }
        };
       mHandler.postDelayed(mTimer, 200);

    }


    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mTimer);
    }

}
