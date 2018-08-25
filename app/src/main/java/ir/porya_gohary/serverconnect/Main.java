package ir.porya_gohary.serverconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import ir.porya_gohary.serverconnect.fragments.Home;
import ir.porya_gohary.serverconnect.fragments.about;
import ir.porya_gohary.serverconnect.fragments.calculate;
import ir.porya_gohary.serverconnect.fragments.charts;
import ir.porya_gohary.serverconnect.fragments.outputs;
import ir.porya_gohary.serverconnect.fragments.settings;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ANImageView pro_image;
    TextView pro_username;
    TextView pro_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new Home()).commit();


        //Set Profile Settings
        View header=navigationView.getHeaderView(0);
        pro_image=header.findViewById(R.id.pro_image);
        pro_username=header.findViewById(R.id.pro_username);
        pro_email=header.findViewById(R.id.pro_email);

        SharedPreferences preferences=getSharedPreferences("prefs",MODE_PRIVATE);
        pro_username.setText(preferences.getString("username",""));
        pro_email.setText(preferences.getString("email",""));
        pro_image.setImageUrl(Urls.host+preferences.getString("image",""));
        pro_image.setDefaultImageResId(R.drawable.user);
        pro_image.setErrorImageResId(R.mipmap.ic_launcher_round);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new Home()).commit();
                break;
            case R.id.nav_chart:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new charts()).commit();
                break;
            case R.id.nav_output:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new outputs()).commit();
                break;
            case R.id.nav_calculate:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new calculate()).commit();
                break;
            case R.id.nav_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new settings()).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new about()).commit();
                break;
            case R.id.nav_logout:
                SessionManager manager=new SessionManager(Main.this);
                manager.setLogedIn(false);
                startActivity(new Intent(Main.this,MainActivity.class));
                finish();

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
