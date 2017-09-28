package com.example.admin.umbrella.view.SettingsActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.umbrella.R;
import com.example.admin.umbrella.util.CONSTANTS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 9/27/2017.
 */

class UnitDialog extends Dialog{

    MyDialogInterface unitInt;
    Context context;

    Spinner mySpinner;
    Button btnSave;

    public UnitDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        this.unitInt = ((MyDialogInterface) context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_dialogue);

        btnSave = findViewById(R.id.btnSave);

        mySpinner = findViewById(R.id.spinOptions);
        List<String> list = new ArrayList<String>();
        list.add("metric");
        list.add("english");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(dataAdapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = unitInt.GetSharedPref().edit();
                editor.putString(CONSTANTS.UNIT_KEY, mySpinner.getSelectedItem().toString());
                editor.commit();
                unitInt.updateView();
                Toast.makeText(context, context.getString(R.string.unit_saved), Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }




}
