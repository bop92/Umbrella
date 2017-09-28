package com.example.admin.umbrella.view.MainActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.answers.Answers;
import com.example.admin.umbrella.R;
import com.example.admin.umbrella.model.HourlyReport.Report;
import com.example.admin.umbrella.util.CONSTANTS;
import com.example.admin.umbrella.view.SettingsActivity.SettingsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements MainActivityContract.MainView{
    private static final String TAG = "MainActivityView";
    //do stuff

    RecyclerView dailyView;

    RecyclerView.LayoutManager linearLayout;
    RecyclerView.LayoutManager gridLayout;

    MainActivityPresenter presenter;
    TextView tvCity, tvTemp, tvForecast;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this ,R.color.grey));
        setSupportActionBar(toolbar);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvTemp = (TextView) findViewById(R.id.tvTemp);
        tvForecast = (TextView) findViewById(R.id.tvForecast);


        linearLayout = new LinearLayoutManager(this);
        gridLayout = new GridLayoutManager(this, 4);
        dailyView = (RecyclerView) findViewById(R.id.recViewDaily);
        presenter = new MainActivityPresenter();
        presenter.attachView(this);

        if(presenter.GetZipCode() == null) {

            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            LayoutInflater inflater = this.getLayoutInflater();
            final View layout = inflater.inflate(R.layout.zip_dialogue, null);

            alertDialog.setTitle("No ZipCode Found");
            alertDialog.setMessage("ZipCode");
            alertDialog.setView(layout);
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText editText = (EditText) layout.findViewById(R.id.etZip);
                    presenter.SaveZipCode(editText.getText().toString());


                }
            });
            alertDialog.show();
        }

        if(presenter.GetUnitLocaleSetting() == null) {
            SharedPreferences sharedPref = getSharedPrefs();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(CONSTANTS.UNIT_KEY, "metric");
            editor.commit();
        }
    }



    public void SetTitleCity(String s){
        tvCity.setText(s);
    }

    public void SetTitleTemp(String s) {
        if (presenter.GetUnitLocaleSetting().equals("metric")){
            if (Integer.valueOf(s.substring(0, s.length() - 3)) >= 15.55) {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.hot));
            } else {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.cold));
            }
        } else {
            if (Integer.valueOf(s.substring(0, s.length() - 3)) >= 60) {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.hot));
            } else {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.cold));
            }
        }
        tvTemp.setText(s);
    }

    @Override
    public void SetForecast(String s) {
        tvForecast.setText(s);
    }

    @Override
    public void showError(String error) {
        Log.d(TAG, "showError: " + error);
    }


    public void startSettingsActivity(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //avoid passing context to the presenter
    @Override
    public SharedPreferences getSharedPrefs() {
        SharedPreferences sharedPref = this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        return  sharedPref;
    }

    @Override
    public RecyclerView.LayoutManager getLinearLayoutManager() {
        return this.linearLayout;
    }

    @Override
    public RecyclerView GetDailyRecView() {
        return dailyView;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter.GetCacheReport();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CONSTANTS.MessageEvent event) {
        if(event.getAction().equals(CONSTANTS.LOAD_REPORT)){
            presenter.ParseReportLoad((Report) event.getObject());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
