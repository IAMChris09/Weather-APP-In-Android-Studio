package com.example.weatherapptutorial;

import  androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.net.Uri;
import android.view.MenuItem;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
public class MainActivity extends AppCompatActivity {
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";
    private Receiver mReceiver = new Receiver();
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    final String APP_ID = "dab3af44de7d24ae7ff86549334e45bd";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView NameofCity, weatherState, Temperature, Display;
    ImageView mweatherIcon;

//    RelativeLayout mCityFinder;
    ImageView mCityFinder;

    LocationManager mLocationManager;
    LocationListener mLocationListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);

        // Register the receiver using the activity context.
        this.registerReceiver(mReceiver, filter);

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mReceiver,
                        new IntentFilter(ACTION_CUSTOM_BROADCAST));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
            // run your one time code
            login();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        weatherState = findViewById(R.id.weatherCondition);
        Temperature = findViewById(R.id.temperature);
        mweatherIcon = findViewById(R.id.weatherIcon);
        mCityFinder = findViewById(R.id.cityFindd);
        NameofCity = findViewById(R.id.cityName);
        Display = findViewById(R.id.display);


        mCityFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, cityFinder.class);
                startActivity(intent);
            }
        });

        setUpToolbar();

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:

                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;


                    case  R.id.nav_share:{

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody =  "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));

                    }
                    break;
                    case  R.id.nav_aboutus:

                        Intent intent1 = new Intent(MainActivity.this, Aboutweather.class);
                        startActivity(intent1);
                        break;
                }
                return false;

            }
        });
    }

    public void setUpToolbar() {

        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        actionBarDrawerToggle.syncState();
    }


 /*   @Override
   protected void onResume() {
       super.onResume();
       getWeatherForCurrentLocation();
    }*/

    @Override
    protected void onResume() {

        super.onResume();
        Intent mIntent=getIntent();
        String city= mIntent.getStringExtra("City");
        if(city!=null)
        {
            getWeatherForNewCity(city);
        }
        else
        {
            getWeatherForCurrentLocation();
        }


    }


    private void getWeatherForNewCity(String city)
    {
        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",APP_ID);
        letsdoSomeNetworking(params);

    }




    private void getWeatherForCurrentLocation() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListner = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params =new RequestParams();
                params.put("lat" ,Latitude);
                params.put("lon",Longitude);
                params.put("appid",APP_ID);
                letsdoSomeNetworking(params);




            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //not able to get location
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListner);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode==REQUEST_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MainActivity.this,"Locationget Succesffully",Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            }
            else
            {
                //user denied the permission
            }
        }


    }



    private  void letsdoSomeNetworking(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast.makeText(MainActivity.this,"Data Get Success",Toast.LENGTH_SHORT).show();

                weatherData weatherD=weatherData.fromJson(response);
                updateUI(weatherD);


               // super.onSuccess(statusCode, headers, response);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });



    }

    private void updateUI(weatherData weather){


        Temperature.setText(weather.getmTemperature());
        NameofCity.setText(weather.getMcity());
        weatherState.setText(weather.getmWeatherType());
        int resourceID=getResources().getIdentifier(weather.getMicon(),"drawable",getPackageName());
        mweatherIcon.setImageResource(resourceID);

        if(weather.getDisplay() > 30){
            Display.setText("適合穿短袖短褲");
        } else if(weather.getDisplay() > 20) {
            Display.setText("適合穿長袖短褲");
        } else if(weather.getDisplay() > 10) {
            Display.setText("適合穿長袖長褲");
        } else if(weather.getDisplay() > 0) {
            Display.setText("適合穿長袖長褲加外套");
        } else {
            Display.setText("適合穿超多衣服");
        }

    }

    @Override
    protected void onRestart() {
        login();
        super.onRestart();
    }

//    @Override
//    protected void onStart() {
//        int j = 0;
//        if(j == 0){login();}
//        j++;
//        super.onStart();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mLocationManager!=null)
        {
            mLocationManager.removeUpdates(mLocationListner);
        }
    }

    @Override
    protected void onDestroy() {
        //Unregister the receiver
        this.unregisterReceiver(mReceiver);
        super.onDestroy();

        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mReceiver);
    }
    public void login() {
        UserLoginDialog userLoginDialog = new UserLoginDialog();
        userLoginDialog.show(getSupportFragmentManager(), "login_dialog");
    }
}