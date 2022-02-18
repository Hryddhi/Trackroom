package com.hryddhi.trackroomandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityRegister extends BaseDataActivity {
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        txtLogin = (TextView) findViewById(R.id.tv_login);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogin();
            }
        });
    }
}
