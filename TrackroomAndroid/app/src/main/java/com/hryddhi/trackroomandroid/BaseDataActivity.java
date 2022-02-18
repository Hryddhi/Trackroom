package com.hryddhi.trackroomandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BaseDataActivity extends AppCompatActivity {
    public Intent startActivity;

    public void startLogin() {
        startActivity = new Intent(getApplicationContext(), ActivityLogin.class);
        startActivity(startActivity);
    }

    public void startRegister() {
        startActivity = new Intent(getApplicationContext(), ActivityRegister.class);
        startActivity(startActivity);
    }
}
