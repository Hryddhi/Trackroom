package com.hryddhi.trackroomandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView et_email = findViewById(R.id.et_email);
        TextView et_password = findViewById(R.id.et_password);
        Button btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_email.getText().toString().equals("admin") && et_password.getText().toString().equals("admin")){
                    Toast.makeText(ActivityLogin.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ActivityLogin.this, "Login Failed!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
