package ir.porya_gohary.serverconnect.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.porya_gohary.serverconnect.Main;
import ir.porya_gohary.serverconnect.R;
import ir.porya_gohary.serverconnect.Urls;
import ir.porya_gohary.serverconnect.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class settings extends Fragment {


    public settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    AppCompatEditText ed_pass,ed_pass2;
    CircleImageView ed_img;
    AppCompatButton sbt;
    ImageView circle;
    File im;
    String path=null;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ed_img=getActivity().findViewById(R.id.edit_userimage);
        ed_pass2=getActivity().findViewById(R.id.edit_pass2);
        ed_pass=getActivity().findViewById(R.id.edit_pass);
        sbt=getActivity().findViewById(R.id.submit);
        circle=getActivity().findViewById(R.id.Circle);

        AndroidNetworking.initialize(getContext());

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askReadPermission();
            }
        });

        sbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });



    }

    private void update(){

        if(ed_pass.getText().toString().equals(ed_pass2.getText().toString())) {
            final SharedPreferences preferences = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
            AndroidNetworking.upload(Urls.host + Urls.update)
                    .addMultipartFile("image",new File(path))
                    .addMultipartParameter("username", preferences.getString("username", ""))
                    .addMultipartParameter("new_password", ed_pass.getText().toString())
                    .setTag("UPDATE")
                    .build()
                    .getAsObject(User.class, new ParsedRequestListener<User>() {

                        @Override
                        public void onResponse(User response) {
                            try {
                                preferences.edit().putString("image",response.getImageUrl()).apply();
                                Toast.makeText(getContext(),"با موفقیت به روزرسانی شد!",Toast.LENGTH_SHORT).show();
                                getContext().startActivity(new Intent(getActivity(), Main.class));
                                getActivity().finish();
                            }catch (Exception e){
                                Toast.makeText(getContext(),"خطا در ارتباط با سرور!",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.d("خطا",String.valueOf(anError));
                            Toast.makeText(getContext(),"خطا در ارتباط با سرور!",Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Snackbar.make(sbt,"کلمه‌های عبور با هم یکی نیست!",Snackbar.LENGTH_LONG).show();
        }
    }

    private void askReadPermission(){
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                            pickImage();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response != null && response.isPermanentlyDenied()){
                            Snackbar.make(sbt,"برای انتخاب فایل دسترسی ضروری است!",Snackbar.LENGTH_LONG).setAction("اجازه دادن", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    i.setData(Uri.fromParts("package",getContext().getPackageName(),null));
                                    startActivity(i);
                                }
                            }).show();
                        }else {
                            Snackbar.make(sbt,"برای انتخاب فایل دسترسی ضروری است!",Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, final PermissionToken token) {
                        Snackbar.make(sbt,"برای انتخاب فایل دسترسی ضروری است!",Snackbar.LENGTH_LONG).setAction("اجازه دادن", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                token.continuePermissionRequest();
                            }
                        }).show();
                    }
                }).check();



    }

    private void pickImage(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i,"انتخاب تصویر"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data.getData() != null){
            path = data.getData().getPath();
            im= new File(data.getData().getPath());
            try {
                FileInputStream inputStream= new FileInputStream(im);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            sbt.setEnabled(true);
            ed_img.setImageURI(data.getData());
        }
    }
}
