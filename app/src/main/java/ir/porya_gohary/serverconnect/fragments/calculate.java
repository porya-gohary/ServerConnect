package ir.porya_gohary.serverconnect.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import ir.porya_gohary.serverconnect.Exec;
import ir.porya_gohary.serverconnect.R;
import ir.porya_gohary.serverconnect.Urls;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class calculate extends Fragment {

    RadioButton rb1,rb2,rb3,rb4,rb5,rb6,rb7,rb8;
    RadioGroup rg1,rg2,rg3;
    RadioButton rb9,rb10,rb11,rb12,rb13,rb14,rb15,rb16;
    RadioGroup rg4,rg5,rg6;
    RadioButton rb17,rb18;
    RadioGroup rg7;
    RadioButton rb19,rb20;
    RadioGroup rg8;
    ToggleButton tb1,tb2,tb3,tb4;
    String username;
    Exec ex;

    int t1,t2,t3,t4;

    Button btn;

    public calculate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AndroidNetworking.initialize(getContext());

        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        username = preferences.getString("username", "");

        btn=(Button) getActivity().findViewById(R.id.btn3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Analog1
                if(tb1.isChecked()) {
                    if (rb1.isChecked() && rb3.isChecked() && rb8.isChecked()) t1 = -1;
                    else if (rb2.isChecked() && rb3.isChecked() && rb7.isChecked()) t1 = -2;
                    else if (rb1.isChecked() && rb3.isChecked() && rb7.isChecked()) t1 = -3;
                    else if (rb2.isChecked() && rb3.isChecked() && rb8.isChecked()) t1 = -4;
                    else if (rb1.isChecked() && rb4.isChecked() && rb8.isChecked()) t1 = -5;
                    else if (rb2.isChecked() && rb4.isChecked() && rb7.isChecked()) t1 = -6;
                    else if (rb1.isChecked() && rb4.isChecked() && rb7.isChecked()) t1 = -7;
                    else if (rb2.isChecked() && rb4.isChecked() && rb8.isChecked()) t1 = -8;
                    else if (rb1.isChecked() && rb5.isChecked() && rb8.isChecked()) t1 = -9;
                    else if (rb2.isChecked() && rb5.isChecked() && rb7.isChecked()) t1 = -10;
                    else if (rb1.isChecked() && rb5.isChecked() && rb7.isChecked()) t1 = -11;
                    else if (rb2.isChecked() && rb5.isChecked() && rb8.isChecked()) t1 = -12;
                    else if (rb1.isChecked() && rb6.isChecked() && rb8.isChecked()) t1 = -13;
                    else if (rb2.isChecked() && rb6.isChecked() && rb7.isChecked()) t1 = -14;
                    else if (rb1.isChecked() && rb6.isChecked() && rb7.isChecked()) t1 = -15;
                    else if (rb2.isChecked() && rb6.isChecked() && rb8.isChecked()) t1 = -16;
                }else t1=0;


                //Analog2
                if(tb2.isChecked()) {
                    if (rb9.isChecked() && rb11.isChecked() && rb16.isChecked()) t2 = -1;
                    else if (rb10.isChecked() && rb11.isChecked() && rb15.isChecked()) t2 = -2;
                    else if (rb9.isChecked() && rb11.isChecked() && rb15.isChecked()) t2 = -3;
                    else if (rb10.isChecked() && rb11.isChecked() && rb16.isChecked()) t2 = -4;
                    else if (rb9.isChecked() && rb12.isChecked() && rb16.isChecked()) t2 = -5;
                    else if (rb10.isChecked() && rb12.isChecked() && rb15.isChecked()) t2 = -6;
                    else if (rb9.isChecked() && rb12.isChecked() && rb15.isChecked()) t2 = -7;
                    else if (rb10.isChecked() && rb12.isChecked() && rb16.isChecked()) t2 = -8;
                    else if (rb9.isChecked() && rb13.isChecked() && rb16.isChecked()) t2 = -9;
                    else if (rb10.isChecked() && rb13.isChecked() && rb15.isChecked()) t2 = -10;
                    else if (rb9.isChecked() && rb13.isChecked() && rb15.isChecked()) t2 = -11;
                    else if (rb10.isChecked() && rb13.isChecked() && rb16.isChecked()) t2 = -12;
                    else if (rb9.isChecked() && rb14.isChecked() && rb16.isChecked()) t2 = -13;
                    else if (rb10.isChecked() && rb14.isChecked() && rb15.isChecked()) t2 = -14;
                    else if (rb9.isChecked() && rb14.isChecked() && rb15.isChecked()) t2 = -15;
                    else if (rb10.isChecked() && rb14.isChecked() && rb16.isChecked()) t2 = -16;
                }else t2=0;

                //Digital1
                if(tb3.isChecked()) {
                    if (rb17.isChecked()) t3 = -1;
                    else if (rb18.isChecked()) t3 = -2;
                }else t3=0;


                //Digital2
                if(tb4.isChecked()) {
                    if (rb19.isChecked()) t4 = -1;
                    else if (rb20.isChecked()) t4 = -2;
                }else t4=0;



                AndroidNetworking.post(Urls.host+Urls.setExec)
                        .addBodyParameter("main", String.valueOf(1))
                        .addBodyParameter("analog1", String.valueOf(t1))
                        .addBodyParameter("analog2", String.valueOf(t2))
                        .addBodyParameter("digital1",t3+"")
                        .addBodyParameter("digital2",t4+"")
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
        });



        //-->> Analog Output 1
        tb1 = (ToggleButton) getActivity().findViewById(R.id.toggleButton3);
        //Input1
        rb1 = (RadioButton) getActivity().findViewById(R.id.radioButton15);
        rb2 = (RadioButton) getActivity().findViewById(R.id.radioButton16);
        //Ex
        rb3 = (RadioButton) getActivity().findViewById(R.id.radioButton2);
        rb4 = (RadioButton) getActivity().findViewById(R.id.radioButton3);
        rb5 = (RadioButton) getActivity().findViewById(R.id.radioButton4);
        rb6 = (RadioButton) getActivity().findViewById(R.id.radioButton5);
        //Input2
        rb7 = (RadioButton) getActivity().findViewById(R.id.radioButton17);
        rb8 = (RadioButton) getActivity().findViewById(R.id.radioButton18);
        //Group
        rg1 = (RadioGroup) getActivity().findViewById(R.id.radioGroup2);
        rg2 = (RadioGroup) getActivity().findViewById(R.id.radioGroup3);
        rg3 = (RadioGroup) getActivity().findViewById(R.id.radioGroup);

        tb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tb1.isChecked()) {
                    rb1.setEnabled(true);
                    rb2.setEnabled(true);
                    rb3.setEnabled(true);
                    rb4.setEnabled(true);
                    rb5.setEnabled(true);
                    rb6.setEnabled(true);
                    rb7.setEnabled(true);
                    rb8.setEnabled(true);
                } else {
                    rb1.setEnabled(false);
                    rb2.setEnabled(false);
                    rb3.setEnabled(false);
                    rb4.setEnabled(false);
                    rb5.setEnabled(false);
                    rb6.setEnabled(false);
                    rb7.setEnabled(false);
                    rb8.setEnabled(false);
                }
            }
        });


        //-->> Analog Output 2
        tb2=(ToggleButton) getActivity().findViewById(R.id.toggleButton4);
        //Input1
        rb9=(RadioButton) getActivity().findViewById(R.id.radioButton19);
        rb10=(RadioButton) getActivity().findViewById(R.id.radioButton20);
        //Ex
        rb11=(RadioButton) getActivity().findViewById(R.id.radioButton21);
        rb12=(RadioButton) getActivity().findViewById(R.id.radioButton22);
        rb13=(RadioButton) getActivity().findViewById(R.id.radioButton23);
        rb14=(RadioButton) getActivity().findViewById(R.id.radioButton24);
        //Input2
        rb15=(RadioButton) getActivity().findViewById(R.id.radioButton25);
        rb16=(RadioButton) getActivity().findViewById(R.id.radioButton26);
        //Group
        rg4=(RadioGroup) getActivity().findViewById(R.id.radioGroup4);
        rg5=(RadioGroup) getActivity().findViewById(R.id.radioGroup5);
        rg6=(RadioGroup) getActivity().findViewById(R.id.radioGroup6);





        tb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(tb2.isChecked()){

                   rb9.setEnabled(true);
                   rb10.setEnabled(true);
                   rb11.setEnabled(true);
                   rb12.setEnabled(true);
                   rb13.setEnabled(true);
                   rb14.setEnabled(true);
                   rb15.setEnabled(true);
                   rb16.setEnabled(true);

               }else{
                   rb9.setEnabled(false);
                   rb10.setEnabled(false);
                   rb11.setEnabled(false);
                   rb12.setEnabled(false);
                   rb13.setEnabled(false);
                   rb14.setEnabled(false);
                   rb15.setEnabled(false);
                   rb16.setEnabled(false);
               }
            }
        });



        //-->> Digital Output 1
        tb3=(ToggleButton) getActivity().findViewById(R.id.toggleButton5);
        //Ex
        rb17=(RadioButton) getActivity().findViewById(R.id.radioButton27);
        rb18=(RadioButton) getActivity().findViewById(R.id.radioButton28);
        //Group
        rg7=(RadioGroup) getActivity().findViewById(R.id.radioGroup7);

        tb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tb3.isChecked()){
                    rb17.setEnabled(true);
                    rb18.setEnabled(true);
                }else{
                    rb17.setEnabled(false);
                    rb18.setEnabled(false);
                }
            }
        });

        //-->> Digital Output 2
        tb4=(ToggleButton) getActivity().findViewById(R.id.toggleButton2);
        //Ex
        rb19=(RadioButton) getActivity().findViewById(R.id.radioButton29);
        rb20=(RadioButton) getActivity().findViewById(R.id.radioButton30);
        //Group
        rg8=(RadioGroup) getActivity().findViewById(R.id.radioGroup8);

        tb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tb4.isChecked()){
                    rb19.setEnabled(true);
                    rb20.setEnabled(true);
                }else{
                    rb19.setEnabled(false);
                    rb20.setEnabled(false);
                }
            }
        });



        AndroidNetworking.post(Urls.host + Urls.getExec)
                .addBodyParameter("username", username)
                .setTag("LOGIN")
                .build()
                .getAsObject(Exec.class, new ParsedRequestListener<Exec>() {


                    @Override
                    public void onResponse(Exec response) {
                        ex=response;
                        if(response.getAnalog1()>=0){
                            tb1.setChecked(false);
                            rb1.setEnabled(false);
                            rb2.setEnabled(false);
                            rb3.setEnabled(false);
                            rb4.setEnabled(false);
                            rb5.setEnabled(false);
                            rb6.setEnabled(false);
                            rb7.setEnabled(false);
                            rb8.setEnabled(false);

                        }else{
                            tb1.setChecked(true);
                            rb1.setEnabled(true);
                            rb2.setEnabled(true);
                            rb3.setEnabled(true);
                            rb4.setEnabled(true);
                            rb5.setEnabled(true);
                            rb6.setEnabled(true);
                            rb7.setEnabled(true);
                            rb8.setEnabled(true);

                            switch (response.getAnalog1()){

                                case -1:
                                    rb1.setChecked(true);
                                    rb3.setChecked(true);
                                    rb8.setChecked(true);
                                    break;

                                case -2:
                                    rb2.setChecked(true);
                                    rb3.setChecked(true);
                                    rb7.setChecked(true);
                                    break;

                                case -3:
                                    rb1.setChecked(true);
                                    rb3.setChecked(true);
                                    rb7.setChecked(true);
                                    break;

                                case -4:
                                    rb2.setChecked(true);
                                    rb3.setChecked(true);
                                    rb8.setChecked(true);
                                    break;

                                case -5:
                                    rb1.setChecked(true);
                                    rb4.setChecked(true);
                                    rb8.setChecked(true);
                                    break;

                                case -6:
                                    rb2.setChecked(true);
                                    rb4.setChecked(true);
                                    rb7.setChecked(true);
                                    break;

                                case -7:
                                    rb1.setChecked(true);
                                    rb4.setChecked(true);
                                    rb7.setChecked(true);
                                    break;

                                case -8:
                                    rb2.setChecked(true);
                                    rb4.setChecked(true);
                                    rb8.setChecked(true);
                                    break;

                                case -9:
                                    rb1.setChecked(true);
                                    rb5.setChecked(true);
                                    rb8.setChecked(true);
                                    break;

                                case -10:
                                    rb2.setChecked(true);
                                    rb5.setChecked(true);
                                    rb7.setChecked(true);
                                    break;

                                case -11:
                                    rb1.setChecked(true);
                                    rb5.setChecked(true);
                                    rb7.setChecked(true);
                                    break;

                                case -12:
                                    rb2.setChecked(true);
                                    rb5.setChecked(true);
                                    rb8.setChecked(true);
                                    break;

                                case -13:
                                    rb1.setChecked(true);
                                    rb6.setChecked(true);
                                    rb8.setChecked(true);
                                    break;

                                case -14:
                                    rb2.setChecked(true);
                                    rb6.setChecked(true);
                                    rb7.setChecked(true);
                                    break;

                                case -15:
                                    rb1.setChecked(true);
                                    rb6.setChecked(true);
                                    rb7.setChecked(true);
                                    break;

                                case -16:
                                    rb2.setChecked(true);
                                    rb6.setChecked(true);
                                    rb8.setChecked(true);
                                    break;
                            }

                        }

                        if(response.getAnalog2()>=0){
                            tb2.setChecked(false);
                            rb9.setEnabled(false);
                            rb10.setEnabled(false);
                            rb11.setEnabled(false);
                            rb12.setEnabled(false);
                            rb13.setEnabled(false);
                            rb14.setEnabled(false);
                            rb15.setEnabled(false);
                            rb16.setEnabled(false);
                        }else{
                            tb2.setChecked(true);
                            rb9.setEnabled(true);
                            rb10.setEnabled(true);
                            rb11.setEnabled(true);
                            rb12.setEnabled(true);
                            rb13.setEnabled(true);
                            rb14.setEnabled(true);
                            rb15.setEnabled(true);
                            rb16.setEnabled(true);

                            switch (response.getAnalog2()){
                                //****
                                case -1:
                                    rb9.setChecked(true);
                                    rb11.setChecked(true);
                                    rb16.setChecked(true);
                                    break;
                                case -2:
                                    rb10.setChecked(true);
                                    rb11.setChecked(true);
                                    rb15.setChecked(true);
                                    break;
                                case -3:
                                    rb9.setChecked(true);
                                    rb11.setChecked(true);
                                    rb15.setChecked(true);
                                    break;
                                case -4:
                                    rb10.setChecked(true);
                                    rb11.setChecked(true);
                                    rb16.setChecked(true);
                                    break;
                                 ///////
                                case -5:
                                    rb9.setChecked(true);
                                    rb12.setChecked(true);
                                    rb16.setChecked(true);
                                    break;
                                case -6:
                                    rb10.setChecked(true);
                                    rb12.setChecked(true);
                                    rb15.setChecked(true);
                                    break;
                                case -7:
                                    rb9.setChecked(true);
                                    rb12.setChecked(true);
                                    rb15.setChecked(true);
                                    break;
                                case -8:
                                    rb10.setChecked(true);
                                    rb12.setChecked(true);
                                    rb16.setChecked(true);
                                    break;
                                //++++
                                case -9:
                                    rb9.setChecked(true);
                                    rb13.setChecked(true);
                                    rb16.setChecked(true);
                                    break;
                                case -10:
                                    rb10.setChecked(true);
                                    rb13.setChecked(true);
                                    rb15.setChecked(true);
                                    break;
                                case -11:
                                    rb9.setChecked(true);
                                    rb13.setChecked(true);
                                    rb15.setChecked(true);
                                    break;
                                case -12:
                                    rb10.setChecked(true);
                                    rb13.setChecked(true);
                                    rb16.setChecked(true);
                                    break;
                                //----
                                case -13:
                                    rb9.setChecked(true);
                                    rb14.setChecked(true);
                                    rb16.setChecked(true);
                                    break;
                                case -14:
                                    rb10.setChecked(true);
                                    rb14.setChecked(true);
                                    rb15.setChecked(true);
                                    break;
                                case -15:
                                    rb9.setChecked(true);
                                    rb14.setChecked(true);
                                    rb15.setChecked(true);
                                    break;
                                case -16:
                                    rb10.setChecked(true);
                                    rb14.setChecked(true);
                                    rb16.setChecked(true);
                                    break;

                            }
                        }

                        if(response.getDigital1()>=0){
                            tb3.setChecked(false);
                            rb17.setEnabled(false);
                            rb18.setEnabled(false);
                        }else {
                            tb3.setChecked(true);
                            rb17.setEnabled(true);
                            rb18.setEnabled(true);

                            switch (response.getDigital1()){
                                case -1:
                                    rb17.setChecked(true);
                                    break;
                                case -2:
                                    rb18.setChecked(true);
                                    break;
                            }
                        }


                        if(response.getDigital2()>=0){
                            tb4.setChecked(false);
                            rb19.setEnabled(false);
                            rb20.setEnabled(false);
                        }else{
                            tb4.setChecked(true);
                            rb19.setEnabled(true);
                            rb20.setEnabled(true);

                            switch (response.getDigital2()){
                                case -1:
                                    rb19.setChecked(true);
                                    break;
                                case -2:
                                    rb20.setChecked(true);
                                    break;

                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(),"خطا در دریافت اطلاعات",Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
