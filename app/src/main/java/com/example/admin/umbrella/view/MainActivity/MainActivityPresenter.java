package com.example.admin.umbrella.view.MainActivity;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.admin.umbrella.data.WeatherService;
import com.example.admin.umbrella.model.HourlyReport.HourlyForecast;
import com.example.admin.umbrella.model.HourlyReport.Report;
import com.example.admin.umbrella.util.CONSTANTS;
import com.example.admin.umbrella.view.adapter.DailyListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;


import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Admin on 9/2/2017.
 */

public class MainActivityPresenter implements MainActivityContract.MainPresenter{

    private static final String TAG = "MainActivityPresenter";

    MainActivityContract.MainView view;
    WeatherService service;

    RecyclerView dailyView;
    RecyclerView.LayoutManager linearLayout;
    RecyclerView.ItemAnimator itemAnimator;
    DailyListAdapter dailyAdapter;

    ArrayList<HourlyForecast> forecastList;
    ArrayList<ArrayList<HourlyForecast>> dayList;

    //this needs to turn forecastList into a list of hourlyAdapters


    public void attachView(MainActivityContract.MainView view) {
        this.view = view;
        service = new WeatherService();

        forecastList = new ArrayList<>();
        dayList = new ArrayList<>();

        linearLayout = view.getLinearLayoutManager();

        itemAnimator = new DefaultItemAnimator();

        LoadForecast();

        //TODO this should be in the view
        dailyView = view.GetDailyRecView();
        dailyView.setItemAnimator(itemAnimator);
        dailyView.setLayoutManager(linearLayout);
        dailyAdapter = new DailyListAdapter(dayList, this);
        dailyView.setAdapter(dailyAdapter);
        dailyView.setHasFixedSize(true);
        dailyView.setItemViewCacheSize(20);
        dailyView.setDrawingCacheEnabled(true);
        dailyView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

    }

    public void LoadForecast(){
        service.getWeatherApi()
                .getWeatherObservable(CONSTANTS.API_KEY, CONSTANTS.INFO_TYPE, CONSTANTS.QUERY_TYPE, GetZipCode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Report>() {

                    @Override
                    public void onNext(@NonNull Report report) {
                        Log.d(TAG, "onNext: Got Report" + report.getHourlyForecast());
                        ParseReportLoad(report);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }
                });
    }

    public void ParseReportLoad(Report report){
        forecastList.clear();
        forecastList.addAll(report.getHourlyForecast());
        dayList.clear();
        populateDayList();
        view.SetTitleCity(report.getLocation().getCity() + "," +report.getLocation().getState());
        switch(GetUnitLocaleSetting()){
            case "metric":
                view.SetTitleTemp(report.getHourlyForecast().get(0).getTemp().getMetric() + " °C");
                break;
            case "english":
                view.SetTitleTemp(report.getHourlyForecast().get(0).getTemp().getEnglish() + " °F");
                break;
        }
        view.SetForecast(report.getHourlyForecast().get(0).getCondition());
        dailyAdapter.notifyDataSetChanged();

    }

    public void populateDayList(){
        for (HourlyForecast forecast : forecastList) {
            boolean add_flag = false;
            for(ArrayList<HourlyForecast> hourlyList : dayList){
                if(forecast.getFCTTIME().getYday().equals(hourlyList.get(0).getFCTTIME().getYday())){
                    hourlyList.add(forecast);
                    add_flag = true;
                    break;
                }
            }
            if(!add_flag){
                dayList.add(new ArrayList<HourlyForecast>(Arrays.asList(forecast)));
                Log.d(TAG, "AddHourToDay: " + forecast.getFCTTIME().getYday());
            }
        }
    }

    //TODO implement this on screen flip or reload
    public void GetCacheReport(){
        SharedPreferences prefs = view.getSharedPrefs();
        Gson gson = new Gson();
        String json = prefs.getString(CONSTANTS.MY_REPORT_CACHE, null);
        Type type = new TypeToken<Report>(){}.getType();
        ParseReportLoad((Report) gson.fromJson(json, type));
    }


    @Override
    public void removeView() {
        this.view = null;
    }

    @Override
    public String GetZipCode() {
        //read zip from shared prefs
        Log.d(TAG, "GetZipCode: ");
        SharedPreferences sharedPref = view.getSharedPrefs();
        return sharedPref.getString(CONSTANTS.ZIP_KEY, null);
    }

    public String GetUnitLocaleSetting(){
        Log.d(TAG, "GetUnitLocaleSetting: ");
        SharedPreferences sharedPref = view.getSharedPrefs();
        return sharedPref.getString(CONSTANTS.UNIT_KEY, null);
    }

    @Override
    public void SaveZipCode(String zip) { //save zip to shared prefs
        Log.d(TAG, "saveZip: " + zip);
        SharedPreferences sharedPref = view.getSharedPrefs();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CONSTANTS.ZIP_KEY, zip);
        editor.commit();
    }

}
