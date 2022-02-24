package com.hryddhi.trackroomandroid.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hryddhi.trackroomandroid.ActivityLogin;
import com.hryddhi.trackroomandroid.ActivityRegister;
import com.hryddhi.trackroomandroid.AppPrefs;
import com.hryddhi.trackroomandroid.interfaces.ApiInterface;
import com.hryddhi.trackroomandroid.models.Token;
import com.hryddhi.trackroomandroid.models.User;

public class BaseDataFragment extends Fragment {
    private Context context;

    public ApiInterface getApi() { return AppPrefs.getInstance(context).getApi(); }

    public void saveToken(String access, String refresh) {AppPrefs.getInstance(context).saveToken(access, refresh);}

    public Token retrieveToken() {return AppPrefs.getInstance(context).retrieveToken();}

    public void saveUser(User user) { AppPrefs.getInstance(context).saveUser(user);}

    public User retrieveUser() { return AppPrefs.getInstance(context).retrieveUser();}

    public void deleteToken() { AppPrefs.getInstance(context).deleteToken();}

    public void getUserInfo(String refresh, String access) { AppPrefs.getInstance(context).getUserInfo(refresh, access);}

    public void getToken(String refresh, int pk) { AppPrefs.getInstance(context).getToken(refresh);}

    public void blacklistToken(String refresh) { AppPrefs.getInstance(context).blacklistToken(refresh);}

    public String getAccess() { return AppPrefs.getInstance(context).getAccess();}

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh();}

    public void startLogin() {
        Intent startTrackroom = new Intent(getContext(), ActivityLogin.class);
        startActivity(startTrackroom);
    }

    public void startRegister() {
        Intent startTrackroom = new Intent(getContext(), ActivityRegister.class);
        startActivity(startTrackroom);
    }
}
