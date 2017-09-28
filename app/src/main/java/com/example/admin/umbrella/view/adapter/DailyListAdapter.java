package com.example.admin.umbrella.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.umbrella.R;
import com.example.admin.umbrella.model.HourlyReport.HourlyForecast;
import com.example.admin.umbrella.view.MainActivity.MainActivityPresenter;

import java.util.ArrayList;

/**
 * Created by Admin on 9/2/2017.
 */

public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.ViewHolder>{

    private static final String TAG = "DailyListAdapter";

    private Context context;
    private ArrayList<ArrayList<HourlyForecast>> forecastList;
    MainActivityPresenter presenter;

    public DailyListAdapter(ArrayList<ArrayList<HourlyForecast>> forecastList, MainActivityPresenter presenter){
        this.forecastList = forecastList;
        this.presenter = presenter;
    }

    @Override
    public DailyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.daily_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyListAdapter.ViewHolder holder, int position) {
        ArrayList<HourlyForecast> hourlyList = forecastList.get(position);
        if (position == 0) {
            holder.tvDay.setText(context.getString(R.string.today));
        } else if (position == 1) {
            holder.tvDay.setText(context.getString(R.string.tommorow));
        } else {
            holder.tvDay.setText(hourlyList.get(0).getFCTTIME().getWeekdayName());
        }
        int biggest = 0;
        int max = 0;
        int smallest = 1000;
        int min = 0;
        for(int i = hourlyList.size()-1; i >= 0; i--) {
            int bar;
            if (presenter.GetUnitLocaleSetting().equals("metric")) {
                bar = Integer.valueOf(hourlyList.get(i).getTemp().getMetric());
            } else {
                bar = Integer.valueOf(hourlyList.get(i).getTemp().getEnglish());
            }

            if(biggest <= bar){
                biggest = bar;
                max = i;
            }
            if(smallest >= bar) {
                smallest = bar;
                min = i;
            }

        }
        Log.d(TAG, "onBindViewHolder: color is " + smallest + " " + biggest);
        holder.hourView.setAdapter(new HourViewAdapter(context, hourlyList, presenter, min, max));
    }

    @Override
    public int getItemCount() {
        if(forecastList == null){
            return 0;
        }
        else{
            return forecastList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        MyGridView hourView;
        TextView tvDay;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            hourView = itemView.findViewById(R.id.gridViewHourly);
        }
    }

}
