package com.example.admin.umbrella.view.SettingsActivity;

import android.content.SharedPreferences;
import android.view.View;

import com.example.admin.umbrella.BasePresenter;
import com.example.admin.umbrella.BaseView;
import com.example.admin.umbrella.model.Option.Option;

import java.util.ArrayList;

/**
 * Created by Admin on 9/2/2017.
 */

public interface SettingsActivityContract {
    interface SettingsView extends BaseView {
        SharedPreferences GetSharedPref();
        void updateView();
    }

    interface SettingsPresenter extends BasePresenter<SettingsActivityContract.SettingsView> {
        void SaveZipCode(String s);
        void SaveUnitLocale(String s);
        ArrayList<Option> GetSettings();
    }
}
