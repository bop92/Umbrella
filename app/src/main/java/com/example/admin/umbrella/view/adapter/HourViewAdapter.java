package com.example.admin.umbrella.view.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.umbrella.R;
import com.example.admin.umbrella.model.HourlyReport.HourlyForecast;
import com.example.admin.umbrella.util.CONSTANTS;
import com.example.admin.umbrella.view.MainActivity.MainActivityPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 9/4/2017.
 */

public class HourViewAdapter extends BaseAdapter{

    private static final String TAG = "HourViewAdapter";
    Context context;
    ArrayList<HourlyForecast> hourlyList;
    MainActivityPresenter presenter;
    int min;
    int max;

    public HourViewAdapter(Context c, ArrayList<HourlyForecast> hourlyList, MainActivityPresenter presenter, int min, int max) {
        this.context = c;
        this.hourlyList = hourlyList;
        this.presenter = presenter;
        this.min = min;
        this.max = max;
    }

    @Override
    public int getCount() {
        if(hourlyList == null){
            return 0;
        }
        else{
            return hourlyList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {

            grid = inflater.inflate(R.layout.hourly_entry, null);
            TextView tvTime = grid.findViewById(R.id.tvTime);
            ImageView imageView = grid.findViewById(R.id.vecIcon);
            TextView tvTemp = grid.findViewById(R.id.tvTemp);

            tvTime.setText(hourlyList.get(i).getFCTTIME().getCivil());


            String resource = CONSTANTS.myMap.get(hourlyList.get(i).getIcon());
            resource = resource.substring(0, resource.length()-4);
            int foo = context.getResources().getIdentifier(resource, "drawable", context.getPackageName());

            Log.d(TAG, "getView: " + resource + " : " + foo);
            Drawable vec = ContextCompat.getDrawable(context, foo);
            imageView.setImageDrawable(vec);

            if(presenter.GetUnitLocaleSetting().equals("metric")) {
                String bar = hourlyList.get(i).getTemp().getMetric() + "°";
                tvTemp.setText(bar);
            }
            else{
                String bar = hourlyList.get(i).getTemp().getEnglish() + "°";
                tvTemp.setText(bar);
            }

            if(min != max){
                if(i == min){
                    tvTemp.setTextColor(ContextCompat.getColor(context, R.color.cold));
                    tvTime.setTextColor(ContextCompat.getColor(context, R.color.cold));
                    DrawableCompat.setTint(imageView.getDrawable(), ContextCompat.getColor(context, R.color.cold));
                }
                if(i == max){
                    tvTemp.setTextColor(ContextCompat.getColor(context, R.color.hot));
                    tvTime.setTextColor(ContextCompat.getColor(context, R.color.hot));
                    DrawableCompat.setTint(imageView.getDrawable(), ContextCompat.getColor(context, R.color.hot));
                }
            }

        } else {
            grid = view;
        }

        return grid;
    }
}
