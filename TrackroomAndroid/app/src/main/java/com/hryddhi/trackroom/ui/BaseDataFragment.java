package com.hryddhi.trackroom.ui;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.hryddhi.trackroom.ActivityLogin;
import com.hryddhi.trackroom.ActivityRegister;
import com.hryddhi.trackroom.ActivityTrackroom;
import com.hryddhi.trackroom.AppPrefs;
import com.hryddhi.trackroom.interfaces.ApiInterface;
import com.hryddhi.trackroom.models.Token;
import com.hryddhi.trackroom.models.User;

public class BaseDataFragment extends Fragment {
    private Context context;

    public ApiInterface getApi () { return AppPrefs.getInstance(context).getApi(); }

    public void saveToken(String access, String refresh) { AppPrefs.getInstance(context).saveToken(access,refresh); }

    public Token retrieveToken () { return AppPrefs.getInstance(context).retrieveToken(); }

    public void saveUser(User user) { AppPrefs.getInstance(context).saveUser(user); }

    public User retrieveUser (){ return AppPrefs.getInstance(context).retrieveUser(); }

    public void deleteToken () { AppPrefs.getInstance(context).deleteToken(); }

    public void getUserInfo(String refresh, String access) { AppPrefs.getInstance(context).getUserInfo(refresh, access); }

    public void getToken(String refresh, int pk) { AppPrefs.getInstance(context).getToken(refresh); }

    public void blacklistToken(String refresh) { AppPrefs.getInstance(context).blacklistToken(refresh); }

    public String getAccess() { return AppPrefs.getInstance(context).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh(); }


    public void startTrackroom() {
        Intent startTrackroom = new Intent(getContext(), ActivityTrackroom.class);
        startActivity(startTrackroom);
    }

    public void startLogin() {
        Intent startTrackroom = new Intent(getContext(), ActivityLogin.class);
        startActivity(startTrackroom);
    }

    public void startRegister() {
        Intent startTrackroom = new Intent(getContext(), ActivityRegister.class);
        startActivity(startTrackroom);
    }

   /* public void startCreateClass() {
        Intent startTrackroom = new Intent(getContext(), ActivityCreateClassroom.class);
        startActivity(startTrackroom);
    }

    public void startJoinClass() {
        Intent startTrackroom = new Intent(getContext(), ActivityJoinClass.class);
        startActivity(startTrackroom);
    }

    public String getProfileType() {
        return AppPrefs.getInstance(getContext()).retrieveUser().getProfileType();
    }*/
}
