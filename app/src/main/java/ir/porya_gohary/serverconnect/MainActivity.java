package ir.porya_gohary.serverconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    AppCompatEditText username;
    AppCompatEditText password;
    TextInputLayout t1,t2;
    Button login;
    Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();

        typeface = Typeface.createFromAsset(getAssets(), "fonts/Shabnam.ttf");

        username = (AppCompatEditText) findViewById(R.id.username);
       username.setTypeface(typeface);

        password =(AppCompatEditText) findViewById(R.id.password);
        password.setTypeface(typeface);

        t1=(TextInputLayout) findViewById(R.id.textInputLayout);
        t1.setTypeface(typeface);

        t2=(TextInputLayout) findViewById(R.id.textInputLayout2);
        t2.setTypeface(typeface);


        login =(Button) findViewById(R.id.login);
        login.setTypeface(typeface);
        final SessionManager manager =new SessionManager(this);
         if(manager.isLogedIn()){
             startActivity(new Intent(MainActivity.this,Main.class));
             finish();
         }
        AndroidNetworking.initialize(this);

         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (username.length() == 0 || password.length() == 0){
                     Toast.makeText(MainActivity.this,"فیلدهای لازم را تکمیل کنید!",Toast.LENGTH_SHORT).show();

                     return;
                 }
                 login(username.getText().toString(),password.getText().toString(),manager,v);
             }
         });


    }
    private void login(final String username, String password, final SessionManager manager, final View view){
        AndroidNetworking.post(Urls.host+Urls.login)
                .addBodyParameter("username",username)
                .addBodyParameter("password",password)
                .setTag("LOGIN")
                .build()
                .getAsObject(User.class, new ParsedRequestListener<User>() {

                    @Override
                    public void onResponse(User response) {
                            if(response.getUsername().equals(username)){
                                manager.setLogedIn(true);
                                SharedPreferences preferences=getSharedPreferences("prefs",MODE_PRIVATE);
                                preferences.edit().putString("username",response.getUsername()).apply();
                                preferences.edit().putString("email",response.getEmail()).apply();
                                preferences.edit().putString("image",response.getImageUrl()).apply();
                                startActivity(new Intent(MainActivity.this,Main.class));
                                finish();

                            }else{
                                Snackbar snackbar=Snackbar.make(view,"نام کاربری یا کلمه‌ی عبور اشتباه است!",Snackbar.LENGTH_LONG);
                                ViewCompat.setLayoutDirection(snackbar.getView(),ViewCompat.LAYOUT_DIRECTION_RTL);
                                TextView text = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                                text.setTypeface(typeface);
                                snackbar.show();


                            }
                    }

                    @Override
                    public void onError(ANError anError) {

                        //Snackbar.make(view,"خطا در اتصال به سرور",Snackbar.LENGTH_LONG).show();
                        Snackbar snackbar=Snackbar.make(view,"خطا در اتصال به سرور",Snackbar.LENGTH_LONG);
                        ViewCompat.setLayoutDirection(snackbar.getView(),ViewCompat.LAYOUT_DIRECTION_RTL);
                        TextView text = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                        text.setTypeface(typeface);
                        snackbar.show();
                    }
                });
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("پاسخ",String.valueOf(response));
//                        try {
//                            String un=response.getString("username");
//                            String em=response.getString("email");
//                            String im=response.getString("imageUrl");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Log.d("خطا",String.valueOf(anError));
//                    }
//                });
    }
}
