package com.rifatul.trackroom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.rifatul.trackroom.models.User;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityViewPostCreated extends BaseDataActivity {

    PDFView pdfView;
    ImageView imgView;
    TextView tvAssignmentName;

    int assignmentPk;
    String assignmentName;
    String assignmentMaterialLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post_created);

        pdfView = findViewById(R.id.pdfView_teacher_assignment);
        imgView = findViewById(R.id.imageView);
        tvAssignmentName = findViewById(R.id.txt_Classroom_Title);

        Intent postInfo = getIntent();
       // assignmentPk = postInfo.getIntExtra("taskPk" , 0);
        //assignmentName = postInfo.getStringExtra("taskName");
        assignmentMaterialLink = postInfo.getStringExtra("postFile");

        tvAssignmentName.setText(assignmentMaterialLink);

        //getAccountInfo();

        new downloadFile().execute(assignmentMaterialLink);

    }


    private class downloadFile extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                if (httpURLConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);


            pdfView.fromStream(inputStream)
                    .enableSwipe(true) // allows to block changing pages using swipe
                    //.swipeHorizontal(true)
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    //.enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(0)
                    .load();

        }
    }

    /*private void getAccountInfo() {
        Call<User> getUserInfo = getApi().account(getAccess());

        getUserInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User u = response.body();
                    if (u.getProfileImage() != null)
                        setInfo(u.getProfileImage());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Unable To Get Profile Information.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Function getUserInfo", "Failure");
                Log.d("Function getUserInfo Throwable T", t.toString());
            }
        });
    }

    private void setInfo(String url) {
       // tvAssignmentName.setText(assignmentName);
        //Glide.with(getApplicationContext()).load(url).into(profileImage);
    }*/
}