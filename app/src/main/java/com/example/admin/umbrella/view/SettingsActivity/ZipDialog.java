package com.example.admin.umbrella.view.SettingsActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.umbrella.R;
import com.example.admin.umbrella.util.CONSTANTS;

/**
 * Created by Admin on 9/27/2017.
 */

class ZipDialog extends Dialog{

    MyDialogInterface zipInt;
    EditText etZip;
    Button btnSave;
    TextView tvError;
    Context context;


    public ZipDialog(Context c){
        super(c);
        this.context = c;
        zipInt = ((MyDialogInterface) context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zip_dialogue);
        etZip = findViewById(R.id.etZip);
        tvError = findViewById(R.id.tvError);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zip = etZip.getText().toString();
                if(validZip(zip)){
                    SharedPreferences.Editor editor = zipInt.GetSharedPref().edit();
                    editor.putString(CONSTANTS.ZIP_KEY, zip);
                    editor.commit();
                    Toast.makeText(context, context.getString(R.string.zip_saved), Toast.LENGTH_SHORT).show();
                    zipInt.updateView();
                    dismiss();
                }
                else{
                    tvError.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //TODO do stuff
    private boolean validZip(String zip){
        return true;
    }
}
