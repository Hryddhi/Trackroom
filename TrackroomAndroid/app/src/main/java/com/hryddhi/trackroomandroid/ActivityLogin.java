package com.hryddhi.trackroomandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityLogin extends BaseDataActivity {
    TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtRegister = (TextView) findViewById(R.id.tv_login_sign_up);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();

            }
        });
    }
}
