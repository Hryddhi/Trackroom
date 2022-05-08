package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.rifatul.trackroom.models.QuizContent;
import com.rifatul.trackroom.models.QuizData;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCreateQuiz extends BaseDataActivity {
    AppCompatButton btn_add_qn, btn_create_quiz;
    int viewNum = 1;
    LinearLayout linear1, linear2, linear3, linear4;
    EditText et_qn1, et_option1, et_option2, et_option3, et_option4, et_ans;
    EditText et_qn2, et_qn2_option1, et_qn2_option2, et_qn2_option3, et_qn2_option4, et_qn2_ans;
    EditText et_qn3, et_qn3_option1, et_qn3_option2, et_qn3_option3, et_qn3_option4, et_qn3_ans;
    EditText et_qn4, et_qn4_option1, et_qn4_option2, et_qn4_option3, et_qn4_option4, et_qn4_ans;

    //String[] option = {"A", "B", "C", "D"};


    String post_Title, post_Descrip;
    String start_time = "20-04-2022 00:00";
    String end_time = "20-04-2022 00:20";
    //ArrayList<QuizContent> quizContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        linear4 = findViewById(R.id.linear4);

        btn_create_quiz = findViewById(R.id.btn_create_quiz);
        btn_add_qn = findViewById(R.id.btn_add_qn);
        et_qn1 = findViewById(R.id.et_qn1);
        et_qn2 = findViewById(R.id.et_qn2);
        et_qn3 = findViewById(R.id.et_qn3);
        et_qn4 = findViewById(R.id.et_qn4);
        et_option1 = findViewById(R.id.et_option1);
        et_option2 = findViewById(R.id.et_option2);
        et_option3 = findViewById(R.id.et_option3);
        et_option4 = findViewById(R.id.et_option4);
        et_ans = findViewById(R.id.et_ans);
        et_qn2_option1 = findViewById(R.id.et_qn2_option1);
        et_qn2_option2 = findViewById(R.id.et_qn2_option2);
        et_qn2_option3 = findViewById(R.id.et_qn2_option3);
        et_qn2_option4 = findViewById(R.id.et_qn2_option4);
        et_qn2_ans = findViewById(R.id.et_qn2_ans);
        et_qn3_option1 = findViewById(R.id.et_qn3_option1);
        et_qn3_option2 = findViewById(R.id.et_qn3_option2);
        et_qn3_option3 = findViewById(R.id.et_qn3_option3);
        et_qn3_option4 = findViewById(R.id.et_qn3_option4);
        et_qn3_ans = findViewById(R.id.et_qn3_ans);
        et_qn4_option1 = findViewById(R.id.et_qn4_option1);
        et_qn4_option2 = findViewById(R.id.et_qn4_option2);
        et_qn4_option3 = findViewById(R.id.et_qn4_option3);
        et_qn4_option4 = findViewById(R.id.et_qn4_option4);
        et_qn4_ans = findViewById(R.id.et_qn4_ans);



        Intent PostInfo = getIntent();
        String postTitle = PostInfo.getStringExtra("postTitle");
        String postDescription = PostInfo.getStringExtra("postDescription");
        int classPk = PostInfo.getIntExtra("classPk" , 0);
        post_Title = postTitle;
        post_Descrip = postDescription;
        Log.d("Post title in Create Quiz: ", post_Title);
        Log.d("Post description in Create Quiz: ", post_Descrip);
        Log.d("Class pk in Create Quiz: ", String.valueOf(classPk));
        //getData();

        btn_add_qn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), String.valueOf(viewNum), Toast.LENGTH_SHORT).show();;
                addQnView();
            }
        });



        btn_create_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQuiz(classPk);
            }
        });
    }

    private void createQuiz(int classPk) {
       // QuizData quizData = new QuizData(post_Title, post_Descrip, startTime, endTime, quizContent);

        //quizContent = new ArrayList<>();



       // ArrayList<String> quizContent = new ArrayList<>();



        //ArrayList<QuizContent> quizContent = new ArrayList<>();
        ArrayList<QuizContent> quizContent = new ArrayList<>();

        int numOfLinearLayout = checkEditText();
        switch (numOfLinearLayout) {
            case 1:
                quizContent.add(new QuizContent(et_qn1.getText().toString() , new String[]{et_option1.getText().toString(), et_option2.getText().toString(), et_option3.getText().toString(), et_option4.getText().toString()}, Integer.parseInt(et_ans.getText().toString())));
                break;
            case 2:
                quizContent.add(new QuizContent(et_qn1.getText().toString() , new String[]{et_option1.getText().toString(), et_option2.getText().toString(), et_option3.getText().toString(), et_option4.getText().toString()},Integer.parseInt(et_ans.getText().toString())));
                quizContent.add(new QuizContent(et_qn2.getText().toString() , new String[]{et_qn2_option1.getText().toString(), et_qn2_option2.getText().toString(), et_qn2_option3.getText().toString(), et_qn2_option4.getText().toString()},Integer.parseInt(et_qn2_ans.getText().toString())));
                break;

            case 3:
                quizContent.add(new QuizContent(et_qn1.getText().toString() , new String[]{et_option1.getText().toString(), et_option2.getText().toString(), et_option3.getText().toString(), et_option4.getText().toString()},Integer.parseInt(et_ans.getText().toString())));
                quizContent.add(new QuizContent(et_qn2.getText().toString() , new String[]{et_qn2_option1.getText().toString(), et_qn2_option2.getText().toString(), et_qn2_option3.getText().toString(), et_qn2_option4.getText().toString()},Integer.parseInt(et_qn2_ans.getText().toString())));
                quizContent.add(new QuizContent(et_qn3.getText().toString() , new String[]{et_qn3_option1.getText().toString(), et_qn3_option2.getText().toString(), et_qn3_option3.getText().toString(), et_qn3_option4.getText().toString()},Integer.parseInt(et_qn3_ans.getText().toString())));
                break;
            case 4:
                quizContent.add(new QuizContent(et_qn1.getText().toString() , new String[]{et_option1.getText().toString(), et_option2.getText().toString(), et_option3.getText().toString(), et_option4.getText().toString()},Integer.parseInt(et_ans.getText().toString())));
                quizContent.add(new QuizContent(et_qn2.getText().toString() , new String[]{et_qn2_option1.getText().toString(), et_qn2_option2.getText().toString(), et_qn2_option3.getText().toString(), et_qn2_option4.getText().toString()},Integer.parseInt(et_qn2_ans.getText().toString())));
                quizContent.add(new QuizContent(et_qn3.getText().toString() , new String[]{et_qn3_option1.getText().toString(), et_qn3_option2.getText().toString(), et_qn3_option3.getText().toString(), et_qn3_option4.getText().toString()},Integer.parseInt(et_qn3_ans.getText().toString())));
                quizContent.add(new QuizContent(et_qn4.getText().toString() , new String[]{et_qn4_option1.getText().toString(), et_qn4_option2.getText().toString(), et_qn4_option3.getText().toString(), et_qn4_option4.getText().toString()},Integer.parseInt(et_qn4_ans.getText().toString())));
                break;
            /*case 5:

                email.add(etInviteEmail2.getText().toString());
                email.add(etInviteEmail3.getText().toString());
                email.add(etInviteEmail4.getText().toString());
                email.add(etInviteEmail5.getText().toString());
                break;
            case 6:

                email.add(etInviteEmail2.getText().toString());
                email.add(etInviteEmail3.getText().toString());
                email.add(etInviteEmail4.getText().toString());
                email.add(etInviteEmail5.getText().toString());
                email.add(etInviteEmail6.getText().toString());
                break;*/
        }

        QuizData quizData = new QuizData(post_Title, post_Descrip, start_time, end_time, quizContent);

        Call<ResponseBody> createQuiz = getApi().createQuiz(getAccess(), classPk, quizData);
        createQuiz.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Log.d("Quiz Content:", String.valueOf(quizContent));
                //Log.d("Quiz Data:", String.valueOf(quizData.getTitle(), quizData.getDescription(), quizData.getStartTime(), quizData.getEndTime());

                if (response.isSuccessful()) {
                   // QuizData quiz = response.body();
                   // quiz.getTitle();
                    Log.d("Response on create quiz", String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    startTrackroom();

                }
                /*else {
                    Toast.makeText(getApplicationContext(), "Cannot Create Quiz", Toast.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }



    private int checkEditText() {
        int numOfLinearLayout = 0;
        if (et_qn1.getText().toString().length() > 4 && et_option1.getText().toString().length() >= 1 && et_option2.getText().toString().length() >= 1 && et_option3.getText().toString().length() >= 1  && et_option4.getText().toString().length() >= 1 && et_ans.getText().toString().length() == 1)
            numOfLinearLayout++;
        if (et_qn2.getText().toString().length() > 4 && et_qn2_option1.getText().toString().length() >= 1 && et_qn2_option2.getText().toString().length() >= 1 && et_qn2_option3.getText().toString().length() >= 1 && et_qn2_option4.getText().toString().length() >= 1 && et_qn2_ans.getText().toString().length() == 1)
            numOfLinearLayout++;
        if (et_qn3.getText().toString().length() > 4 && et_qn3_option1.getText().toString().length() >= 1 && et_qn3_option2.getText().toString().length() >= 1 && et_qn3_option3.getText().toString().length() >= 1 && et_qn3_option4.getText().toString().length() >= 1 && et_qn3_ans.getText().toString().length() == 1)
            numOfLinearLayout++;
        if (et_qn4.getText().toString().length() > 4 && et_qn4_option1.getText().toString().length() >= 1 && et_qn4_option2.getText().toString().length() >= 1 && et_qn4_option3.getText().toString().length() >= 1 && et_qn4_option4.getText().toString().length() >= 1 && et_qn4_ans.getText().toString().length() == 1)
            numOfLinearLayout++;
        /*if (et_qn2.getText().toString().length() > 4)
            numOfLinearLayout++;
        if (etInviteEmail5.getText().toString().length() > 4)
            numOfLinearLayout++;
        if (etInviteEmail6.getText().toString().length() > 4)
            numOfLinearLayout++;*/
        return numOfLinearLayout;
    }


    private void addQnView() {
        switch (viewNum) {
            case 1:
                linear2.setAlpha(1);
                /*et_qn2.setAlpha(1);
                et_qn2_option1.setAlpha(1);
                et_qn2_option2.setAlpha(1);
                et_qn2_option3.setAlpha(1);
                et_qn2_option4.setAlpha(1);
                et_qn2_ans.setAlpha(1);*/
                viewNum++;
                break;
            case 2:
                linear3.setAlpha(1);
                /*et_qn3.setAlpha(1);
                et_qn3_option1.setAlpha(1);
                et_qn3_option2.setAlpha(1);
                et_qn3_option3.setAlpha(1);
                et_qn3_option4.setAlpha(1);
                et_qn3_ans.setAlpha(1);*/
                viewNum++;
                break;
            case 3:
               linear4.setAlpha(1);
                viewNum++;
                break;
            /*case 4:
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
}
