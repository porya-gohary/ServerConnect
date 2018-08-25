package ir.porya_gohary.serverconnect.fragments;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import ir.porya_gohary.serverconnect.Outputs;
import ir.porya_gohary.serverconnect.R;
import ir.porya_gohary.serverconnect.Urls;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class outputs extends Fragment {

    Typeface typeface;
    Switch sw1,sw2;
    EditText ed1,ed2;
    Runnable mTimer;
    final Handler mHandler = new Handler();
    String username;
    int s1,s2;

    Button btn;


    public outputs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outputs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Shabnam.ttf");
        sw1=(Switch)getActivity().findViewById(R.id.switch4);
        sw2=(Switch)getActivity().findViewById(R.id.switch7);

        ed1=(EditText)getActivity().findViewById(R.id.editText);
        ed2=(EditText)getActivity().findViewById(R.id.editText2);

        btn=(AppCompatButton)getActivity().findViewById(R.id.btn1);
        sw1.setTypeface(typeface);
        sw2.setTypeface(typeface);


        AndroidNetworking.initialize(getContext());

        SharedPreferences preferences=getActivity().getSharedPreferences("prefs",MODE_PRIVATE);
        username=preferences.getString("username","");



        AndroidNetworking.post(Urls.host+Urls.getOutputs)
                .addBodyParameter("username",username)
                .setTag("LOGIN")
                .build()
                .getAsObject(Outputs.class, new ParsedRequestListener<Outputs>() {



                    @Override
                    public void onResponse(Outputs response) {
                        if(response.getDigital1()==0){
                            sw1.setChecked(false);
                        }else {
                            sw1.setChecked(true);
                        }

                        if(response.getDigital2()==0){
                            sw2.setChecked(false);
                        }else {
                            sw2.setChecked(true);
                        }

                        ed1.setText(response.getAnalog1()+"");
                        ed2.setText(response.getAnalog2()+"");


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(ed1.getText().toString()) >= 0 && Integer.parseInt(ed1.getText().toString()) <= 3300
                        && Integer.parseInt(ed2.getText().toString()) >= 0 && Integer.parseInt(ed2.getText().toString()) <= 3300 ) {
                    if (sw1.isChecked()) {
                        s1 = 1;
                    } else {
                        s1 = 0;
                    }

                    if (sw2.isChecked()) {
                        s2 = 1;
                    } else {
                        s2 = 0;
                    }


                    AndroidNetworking.post(Urls.host + Urls.setExec)
                            .addBodyParameter("main", "1")
                            .addBodyParameter("analog1", String.valueOf(ed1.getText()))
                            .addBodyParameter("analog2", String.valueOf(ed2.getText()))
                            .addBodyParameter("digital1", s1 + "")
                            .addBodyParameter("digital2", s2 + "")
                            .build()
                            .getAsString(new StringRequestListener() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("fail")) {
                                        Toast.makeText(getContext(), "خطا در ارسال اطلاعات", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(getContext(), "خطا در ارسال اطلاعات", Toast.LENGTH_SHORT).show();
                                }
                            });


                }else{
                    Toast.makeText(getContext(), "مقدار دیجیتال باید بین 0 تا 3300 باشد!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        mTimer=new Runnable() {
//            @Override
//            public void run() {
//
//                mHandler.postDelayed(this, 200);
//            }
//
//        };
//        mHandler.postDelayed(mTimer, 200);
//    }
}
