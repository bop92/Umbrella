package com.example.admin.umbrella.view.SettingsActivity;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.admin.umbrella.data.WeatherService;
import com.example.admin.umbrella.model.HourlyReport.Report;
import com.example.admin.umbrella.model.Option.Option;
import com.example.admin.umbrella.util.CONSTANTS;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SettingsActivityPresenter implements SettingsActivityContract.SettingsPresenter {

    private static final String TAG = "SettingsPresenter";
    SettingsActivityContract.SettingsView view;

    @Override
    public void attachView(SettingsActivityContract.SettingsView view) {
        this.view = view;
    }

    @Override
    public void removeView() {
        view = null;
    }

    @Override
    public void SaveZipCode(String s) {
        Log.d(TAG, "saveZip:" + s);
        SharedPreferences sharedPref = view.GetSharedPref();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CONSTANTS.ZIP_KEY, s);
        editor.commit();
        view.updateView();
    }

    @Override
    public void SaveUnitLocale(String s) {
        SharedPreferences sharedPref = view.GetSharedPref();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CONSTANTS.UNIT_KEY, s);
        editor.commit();
        view.updateView();
    }

    public ArrayList<Option> GetSettings(){
        ArrayList<Option> foo = new ArrayList<>();
        foo.add(new Option(CONSTANTS.ZIP_KEY, CONSTANTS.ZIPCODE_VAL, GetZipCode()));
        foo.add(new Option(CONSTANTS.UNIT_KEY, CONSTANTS.UNIT_VAL, GetUnitLocaleSetting()));
        Log.d(TAG, "GetSettings: " + foo.get(0).getValue());
        return foo;
    }

    public String GetZipCode() {
        //read zip from shared prefs
        Log.d(TAG, "GetZipCode: ");
        SharedPreferences sharedPref = view.GetSharedPref();
        return sharedPref.getString(CONSTANTS.ZIP_KEY, null);
    }

    public String GetUnitLocaleSetting(){
        Log.d(TAG, "GetUnitLocaleSetting: ");
        SharedPreferences sharedPref = view.GetSharedPref();
        return sharedPref.getString(CONSTANTS.UNIT_KEY, null);
    }


    WeatherService service = new WeatherService();
    public void LoadForecast(){
        service.getWeatherApi()
                .getWeatherObservable(CONSTANTS.API_KEY, CONSTANTS.INFO_TYPE, CONSTANTS.QUERY_TYPE, GetZipCode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Report>() {

                    @Override
                    public void onNext(@NonNull Report report) {
                        Log.d(TAG, "onNext: Got Report" + report.getHourlyForecast());
                        CONSTANTS.MessageEvent event = new CONSTANTS.MessageEvent(CONSTANTS.LOAD_REPORT, report);
                        EventBus.getDefault().post(event);
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
}
