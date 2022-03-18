package com.rifatul.trackroom.ui.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.rifatul.trackroom.ActivityEditBio;
import com.rifatul.trackroom.ActivityEditProfile;
import com.rifatul.trackroom.R;
import com.rifatul.trackroom.models.Token;
import com.rifatul.trackroom.models.User;
import com.rifatul.trackroom.ui.BaseDataFragment;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends BaseDataFragment {
    AppCompatButton btnLogout;
    TextView userName;
    TextView userEmail;
    TextView userBio;
    AppCompatButton btnEditProfile;
    AppCompatButton btnEditBio;
    CircleImageView profileImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnLogout = view.findViewById(R.id.btn_logout);
        userName = view.findViewById(R.id.tv_username);
        userEmail = view.findViewById(R.id.tv_useremail);
        userBio = view.findViewById(R.id.tv_userbio);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnEditBio = view.findViewById(R.id.btn_edit_bio);
        profileImage = view.findViewById(R.id.img_profile_photo);

        //displayProfileInfo();
        //Token token = retrieveToken();
        getAccountInfo();

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

        btnEditBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBio();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        return view;
    }

    private void getAccountInfo() {
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

                    displayProfileInfo(u.getUsername(), u.getEmail(), u.getBio());

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
        Glide.with(getContext()).load(url).into(profileImage);
    }

    private void displayProfileInfo(String username, String email, String bio) {
        /*User u = retrieveUser();
        if (u.getUsername().equals("fail")) {
            Token token = retrieveToken();
            getUserInfo(token.getRefresh(),token.getAccess());
        }
        userName.setText(u.getUsername());
        userEmail.setText(u.getEmail());*/

        userName.setText(username);
        userEmail.setText(email);
        userBio.setText(bio);
    }

    private void editProfile() {
        Intent editProfile = new Intent(getContext(), ActivityEditProfile.class);
        startActivity(editProfile);
    }

    private void editBio() {
        Intent editProfile = new Intent(getContext(), ActivityEditBio.class);
        startActivity(editProfile);
    }

    public void logoutUser(){
        Log.d("Function", "logoutUser");
        Token t = retrieveToken();
        blacklistToken(t.getRefresh());
    }
}