package com.example.admin.umbrella.view.SettingsActivity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.umbrella.R;
import com.example.admin.umbrella.model.Option.Option;
import com.example.admin.umbrella.util.CONSTANTS;

import java.util.ArrayList;

/**
 * Created by Admin on 9/5/2017.
 */

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder>{
    private static final String TAG = "OptionsAdapter";
    ArrayList<Option> optionsList;
    Context context;
    private int lastPosition = -1;

    public OptionsAdapter(ArrayList<Option> optionsList, SettingsActivity settingsActivity) {
        this.optionsList = optionsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.options_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Option entry = optionsList.get(position);

        holder.tvOption.setText(entry.getName());
        holder.tvSetting.setText(entry.getValue());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entry.getOption_key().equals(CONSTANTS.ZIP_KEY)) {
                    ZipDialog foo = new ZipDialog(context);
                    foo.show();
                } else if(entry.getOption_key().equals(CONSTANTS.UNIT_KEY)){
                    UnitDialog foo = new UnitDialog(context);
                    foo.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(optionsList != null) {
            return optionsList.size();
        }
        else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOption;
        TextView tvSetting;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOption = itemView.findViewById(R.id.tvOption);
            tvSetting = itemView.findViewById(R.id.tvSetting);
        }
    }
}

