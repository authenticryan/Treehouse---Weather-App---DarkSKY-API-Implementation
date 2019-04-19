package com.example.stormy;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stormy.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    private CurrentWeather currentWeather = new CurrentWeather();
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding = null;
    private ImageView imageViewWeatherIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        imageViewWeatherIcon = findViewById(R.id.icon_image_view);

        TextView darkSky = findViewById(R.id.dark_sky_attribution);
        darkSky.setMovementMethod(LinkMovementMethod.getInstance());


        getForecast();
    }

    private void getForecast() {
        if (isNetworkAvailable())
            this.darkAPICall();

        else
            this.errorNetworkNotAvailable();
    }

    private void errorNetworkNotAvailable() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.setErrorTitle("Network Unavailable");
        dialog.setErrorMessage("Please try switching on WIFI/mobile data and check if Airplane mode is deactivated");
        dialog.show(getSupportFragmentManager(), "error_dialog");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected())
            isAvailable = true;

        return isAvailable;
    }

    private void darkAPICall() {

        String apiKey = "c2a1cb7ac0e259ada56774398fe1325d";
        Double latitude = 37.8267;
        Double longitude = -122.4233;

        String forecastURL = "https://api.darksky.net/forecast/" + apiKey + "/" + latitude + "," + longitude;

        this.okHTTTPRequest(forecastURL);
    }

    private void okHTTTPRequest(String url){

        Request request = new Request.Builder()
                .url(url)
                .build();

        // Process API request through Asynchronous GET
        client.newCall(request).enqueue(new Callback() {

            @Override public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Failed to process the request. Check Internet Connection or Restart your phone");
            }

            @Override public void onResponse(Call call, Response response){
                try {

                    assert response.body() != null;

                    if (response.isSuccessful()) {
//                        Log.v(TAG, response.body().string());
                        currentWeather = getCurrentDetails(response.body().string());
                    }
                    else {
                        alertUserAboutError();
                    }

                } catch (IOException e) {
                    Log.e(TAG, "IO Exception caught");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSON parser (getCurrentDetails) threw an error");
                }
            }
        });
    }

    private CurrentWeather getCurrentDetails(String JSONData) throws JSONException {
        JSONObject forecast = new JSONObject(JSONData);
        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather(
                forecast.getString("timezone"),
                currently.getString("icon"),
                currently.getLong("time"),
                currently.getDouble("temperature"),
                currently.getDouble("humidity"),
                currently.getDouble("precipProbability"),
                currently.getString("summary")
        );

        binding.setWeather(currentWeather);


        runOnUiThread(() -> {
            Drawable drawableWeatherIcon = getResources().getDrawable(currentWeather.getIconId());
            imageViewWeatherIcon.setImageDrawable(drawableWeatherIcon);
        });

        Log.v(TAG, "Ryan Formatted time is " + currentWeather.getFormattedTime());
        Log.v(TAG, "Ryan Formatted time is " + currentWeather.getLocationLabel());

        return currentWeather;
    }

    // displays dialog error
    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.setErrorTitle("Opps! Sorry");
        dialog.setErrorMessage("There was an error. Please try again");
        dialog.show(getSupportFragmentManager(), "error_dialog");
    }

    public void refreshOnClick(View view) {
        Toast.makeText(this, "Refreshing Data", Toast.LENGTH_LONG).show();
        getForecast();
    }
}
