package com.example.admin.umbrella.view.SettingsActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.admin.umbrella.R;
import com.example.admin.umbrella.model.Option.Option;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements SettingsActivityContract.SettingsView, MyDialogInterface {

    private static final String TAG = "SettingsActivity";
    ArrayList<Option> options;
    SettingsActivityPresenter presenter;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    RecyclerView recOptions;

    OptionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        presenter = new SettingsActivityPresenter();
        presenter.attachView(this);

        options = new ArrayList<>();
        options.addAll(presenter.GetSettings());

        adapter = new OptionsAdapter(options, this);
        recOptions = (RecyclerView) findViewById(R.id.recOptions);
        initRecyclerView();
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(getApplicationContext());
        itemAnimator = new DefaultItemAnimator();
        recOptions.setLayoutManager(layoutManager);
        recOptions.setItemAnimator(itemAnimator);
        recOptions.setHasFixedSize(true);
        recOptions.setItemViewCacheSize(20);
        recOptions.setDrawingCacheEnabled(true);
        recOptions.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recOptions.setAdapter(adapter);

        updateView();
    }

    @Override
    public void showError(String error) {
        Log.d(TAG, "showError: " + error);
    }

    @Override
    public SharedPreferences GetSharedPref() {
        SharedPreferences sharedPref = this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        return sharedPref;
    }

    public void updateView() {
        options.clear();
        options.addAll(presenter.GetSettings());
        Log.d(TAG, "updateView: " + options.get(0).getValue());
        adapter.notifyDataSetChanged();
        presenter.LoadForecast();
    }

    public void GoBack(View view){
        onBackPressed();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        updateView();
    }
}