package com.rifatul.trackroom;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rifatul.trackroom.models.InviteEmail;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityInviteStudent extends BaseDataActivity {

    FloatingActionButton addEmail;
    Button btnSendInvite;
    int viewNum = 1;

    EditText etInviteEmail1;
    EditText etInviteEmail2;
    EditText etInviteEmail3;
    EditText etInviteEmail4;
    EditText etInviteEmail5;
    EditText etInviteEmail6;
    EditText etInviteEmail7;

    TextView tvInviteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_student);

        addEmail = findViewById(R.id.fab_add_email);
        btnSendInvite = findViewById(R.id.btn_send_invite);
        etInviteEmail1 = findViewById(R.id.et_email1);
        etInviteEmail2 = findViewById(R.id.et_email2);
        etInviteEmail3 = findViewById(R.id.et_email3);
        etInviteEmail4 = findViewById(R.id.et_email4);
        etInviteEmail5 = findViewById(R.id.et_email5);
        etInviteEmail6 = findViewById(R.id.et_email6);
        etInviteEmail7 = findViewById(R.id.et_email7);
        //tvInviteText = findViewById(R.id.tv_invite_code);

        Intent ClassroomInfo = getIntent();
        //String classCode = ClassroomInfo.getStringExtra("InviteClassCode");
        int classPk = ClassroomInfo.getIntExtra("InviteClassPK" , 0);

        //Log.d("invite student classroom code", classCode);

        //setInfoToViews(classCode);

        addEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), String.valueOf(viewNum), Toast.LENGTH_SHORT).show();;
                addEmailView();
            }
        });

        btnSendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInvite(classPk);
            }
        });

        /*tvInviteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyCode(classCode);
            }
        });*/

    }

    private void sendInvite(int classPk) {
        ArrayList<String> email = new ArrayList<>();

        int numOfEmail = checkEditText();

        switch (numOfEmail) {
            case 1:
                email.add(etInviteEmail1.getText().toString());
                break;
            case 2:
                email.add(etInviteEmail1.getText().toString());
                email.add(etInviteEmail2.getText().toString());
                break;
            case 3:
                /*email.add(etInviteEmail1.getText().toString());*/
                email.add(etInviteEmail2.getText().toString());
                email.add(etInviteEmail3.getText().toString());
                break;
            case 4:
                /*email.add(etInviteEmail1.getText().toString());*/
                email.add(etInviteEmail2.getText().toString());
                email.add(etInviteEmail3.getText().toString());
                email.add(etInviteEmail4.getText().toString());
                break;
            case 5:
                /*email.add(etInviteEmail1.getText().toString());*/
                email.add(etInviteEmail2.getText().toString());
                email.add(etInviteEmail3.getText().toString());
                email.add(etInviteEmail4.getText().toString());
                email.add(etInviteEmail5.getText().toString());
                break;
            case 6:
                /*email.add(etInviteEmail1.getText().toString());*/
                email.add(etInviteEmail2.getText().toString());
                email.add(etInviteEmail3.getText().toString());
                email.add(etInviteEmail4.getText().toString());
                email.add(etInviteEmail5.getText().toString());
                email.add(etInviteEmail6.getText().toString());
                break;
        }

        InviteEmail inviteEmail = new InviteEmail(email);

        Call<ResponseBody> inviteStudents = getApi().inviteStudents(getAccess(),classPk , inviteEmail);

        inviteStudents.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    startTrackroom();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Email Not Registered To Any Subscriber", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private int checkEditText() {
        int emailNum = 0;
        if (etInviteEmail1.getText().toString().length() > 4)
            emailNum++;
        if (etInviteEmail2.getText().toString().length() > 4)
            emailNum++;
        if (etInviteEmail3.getText().toString().length() > 4)
            emailNum++;
        if (etInviteEmail4.getText().toString().length() > 4)
            emailNum++;
        if (etInviteEmail5.getText().toString().length() > 4)
            emailNum++;
        if (etInviteEmail6.getText().toString().length() > 4)
            emailNum++;
        return emailNum;
    }

    private void addEmailView() {
            switch (viewNum) {
                case 1:
                    etInviteEmail2.setAlpha(1);
                    viewNum++;
                    break;
                case 2:
                    etInviteEmail3.setAlpha(1);
                    viewNum++;
                    break;
                case 3:
                    etInviteEmail4.setAlpha(1);
                    viewNum++;
                    break;
                case 4:
                    etInviteEmail5.setAlpha(1);
                    viewNum++;
                    break;
                case 5:
                    etInviteEmail6.setAlpha(1);
                    viewNum++;
                    break;
                /*case 6:
                    etInviteEmail6.setAlpha(1);
                    viewNum++;
                    break;*/
                default:
                    Toast.makeText(getApplicationContext(), "Can not add anymore fields!", Toast.LENGTH_SHORT).show();
            }
    }

    private void setInfoToViews(String classCode) {
        tvInviteText.setText("Classroom Code : " + classCode);
    }

    private void copyCode(String classCode){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Classroom Code", classCode);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Copied To Clipboard", Toast.LENGTH_SHORT).show();
    }
}