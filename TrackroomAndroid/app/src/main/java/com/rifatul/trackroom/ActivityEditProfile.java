package com.rifatul.trackroom;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.rifatul.trackroom.models.ChangePassword;
import com.rifatul.trackroom.models.User;

import org.jetbrains.annotations.Nullable;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditProfile extends BaseDataActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
    private static final int SELECT_REQUEST_CODE = 1;
    TextView tvUsername;
    EditText etUsername;
    EditText etEmail;
    EditText etCurrentPassword;
    EditText etNewPassword;
    EditText etNewPassword2;
    Button btnSaveChanges;
    //Button btnUploadProfilePicture;
    //CircleImageView miniProfileImage;
    CircleImageView profileImage;


    Uri uriProfilePicture;

    int imageUpdate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        tvUsername = findViewById(R.id.tv_username);
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etCurrentPassword = findViewById(R.id.et_current_password);
        etNewPassword = findViewById(R.id.et_password);
        etNewPassword2 = findViewById(R.id.et_password2);
        btnSaveChanges = findViewById(R.id.btn_save_profile_information);
        //miniProfileImage = findViewById(R.id.img_profile_photo);
        profileImage = findViewById(R.id.img_change_profile_photo);
        //btnUploadProfilePicture = findViewById(R.id.btn_upload_profile_photo);

        //displayProfileInfo();
        getAccountInfo(getAccess());

        /*btnUploadProfilePicture.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                if(CheckPermission()) {
                    Intent select = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(select, SELECT_REQUEST_CODE);
                }
            }
        });*/

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (validateEditText(etUsername))
                    updateAccount();*/

                if(validateEditText(etCurrentPassword) && validateEditText(etNewPassword) && validateEditText(etNewPassword2)) {
                    if (checkPasswords(etNewPassword, etNewPassword2)) {
                        updatePassword();
                    }
                }

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uriProfilePicture = data.getData();
        profileImage.setImageURI(uriProfilePicture);

        imageUpdate = 1;

    }


    @SuppressWarnings("deprecation")
    private void updateAccount() {
        String username = etUsername.getText().toString();

        RequestBody body;

        if (imageUpdate == 1) {
            File file = new File(getRealPathFromURI(uriProfilePicture));
            body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("username",username)
                    .addFormDataPart("profile_image","image.jpg", RequestBody.create(MediaType.parse(getContentResolver().getType(uriProfilePicture)), file))
                    .build();

            Log.d("Function Register file", uriProfilePicture.getPath());
            Log.d("Function Register file", file.toString());
        }
        else {
            body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("username",username).build();
        }

        Call<ResponseBody> updateAccount = getApi().updateAccount(getAccess(), (MultipartBody) body);

        updateAccount.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Function registerUser", "Successful response");
                    Toast.makeText(getApplicationContext(), "Account Information Update Successful", Toast.LENGTH_SHORT).show();
                    //startTrackroom();
                }
                else {
                    Log.d("Function registerUser response code", String.valueOf(response.code()));
                    Log.d("Function registerUser", "Wrong response");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Function registerUser", "Server not found");
                Log.d("Function registerUser t", t.toString());
            }
        });
    }

    private void updatePassword() {
        String currentPassword = etCurrentPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        String newPassword2 = etNewPassword2.getText().toString();

        ChangePassword changePassword = new ChangePassword(newPassword, newPassword2, currentPassword);
        Call<ResponseBody> postChangePassword = getApi().changePassword(getAccess(), changePassword);
        postChangePassword.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Information Update Successful", Toast.LENGTH_SHORT).show();
                    deleteToken();
                    startLogin();
                }
                else
                    Toast.makeText(getApplicationContext(), "Passwords Not Changed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("changePassword on failure", t.toString());
                Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getAccountInfo(String access) {
        //String token = "Bearer " + access;
        Call<User> getUserInfo = getApi().account(getAccess());

        getUserInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User u = response.body();
                    //saveUser(u);
                    Log.d("Function ActivityLogin getAccountInfo", "Response Success");
                    Log.d("Function ActivityLogin getAccountInfo", "Calling saveUser");
                    saveUser(u);

                    //displayProfileInfo(u.getUsername(), u.getEmail(), u.getBio());

                    if (u.getProfileImage() != null)
                        displayProfilePicture(u.getProfileImage());
                }
                else {
                    Log.d("Function getUserInfo", "Response Unauthorized");
                    //getToken(refresh);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Function getUserInfo", "Failure");
                Log.d("Function getUserInfo Throwable T", t.toString());
            }
        });
    }
    private void displayProfilePicture (String url) {
        Log.d("Function displayProfilePicture name", url);
        //Glide.with(getApplicationContext()).load(url).into(miniProfileImage);
        Glide.with(getApplicationContext()).load(url).into(profileImage);
    }

    /*private void displayProfileInfo(String username, String email, String bio) {
        /*User u = retrieveUser();
        etUsername.setText(u.getUsername());
        etEmail.setText(u.getEmail());*/
        //tvUsername.setText(username);
        //etUsername.setText(username);
        //etEmail.setText(email);
        //etUsername.setEnabled(false);
        //etEmail.setEnabled(false);
    //}

    public boolean CheckPermission() {
        if (ContextCompat.checkSelfPermission(ActivityEditProfile.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(ActivityEditProfile.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(ActivityEditProfile.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityEditProfile.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(ActivityEditProfile.this,
                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(ActivityEditProfile.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new android.app.AlertDialog.Builder(ActivityEditProfile.this)
                        .setTitle("Permission")
                        .setMessage("Please accept the permissions")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(ActivityEditProfile.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);


                                startActivity(new Intent(ActivityEditProfile
                                        .this, ActivityEditProfile.class));
                                ActivityEditProfile.this.overridePendingTransition(0, 0);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(ActivityEditProfile.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if(validateEditText(etCurrentPassword) && validateEditText(etNewPassword) && validateEditText(etNewPassword2))
            showAlert();
        else {
            startTrackroom();
        }
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Are you sure you want to go back, unsaved will be lost?");
        //builder.setMessage("All unsaved data will be discarded off.");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ActivityEditProfile.super.onBackPressed();
            }
        });
        builder.show();
    }



}