package com.example.admin.umbrella.data;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.admin.umbrella.model.HourlyReport.Report;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public class WeatherService extends Service {

    private static final String BASE_URL = "http://api.wunderground.com/";
    private WeatherApi myWeatherApi;

    //key:1abced5fd624551e
    //http://api.wunderground.com/api/1abced5fd624551e/hourly10day/geolookup/q/94107.json
    //http://api.wunderground.com/api/1abced5fd624551e/forecast10day/geolookup/q/78746.json

    public WeatherService() {
        //created a logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //create a custom client to add the interceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        myWeatherApi = retrofit.create(WeatherApi.class);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public WeatherApi getWeatherApi(){
        return myWeatherApi;
    }


    public interface WeatherApi {

        @GET("api/{key}/{info_type}/{query_type}/q/{zip}.json")
        Observable<Report> getWeatherObservable(@Path("key") String API_KEY,
                                                @Path("info_type") String INFO_TYPE,
                                                @Path("query_type") String QUERY_TYPE,
                                                @Path("zip") String MY_ZIP
                                                );
    }


}
