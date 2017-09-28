package com.example.admin.umbrella.view.MainActivity;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;

import com.example.admin.umbrella.BasePresenter;
import com.example.admin.umbrella.BaseView;


/**
 * Created by Admin on 9/2/2017.
 */

public interface MainActivityContract {

    interface MainView extends BaseView {
        SharedPreferences getSharedPrefs();
        RecyclerView.LayoutManager getLinearLayoutManager();
        RecyclerView GetDailyRecView();
        void SetTitleCity(String s);
        void SetTitleTemp(String s);
        void SetForecast(String s);
    }

    interface MainPresenter extends BasePresenter<MainActivityContract.MainView> {
        String GetZipCode();
        void SaveZipCode(String s);
    }
}
