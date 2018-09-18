package com.szreach.ybolotv.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.szreach.ybolotv.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getFirst()){
            recordFirst();
            Intent login=new Intent(this,LoginActivity.class);
            startActivity(login);
            finish();
        }else {
            if (getstatus()) {
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                finish();
            } else {
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                finish();
            }
        }
    }



    private void recordFirst() {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("First", false);
        editor.apply();
    }

    private boolean getFirst() {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        return sp.getBoolean("First", true);
    }

    private boolean getstatus() {
        SharedPreferences sp = getSharedPreferences("Status", Context.MODE_PRIVATE);
        boolean status = sp.getBoolean("logout", true);
        return status;
    }
}
