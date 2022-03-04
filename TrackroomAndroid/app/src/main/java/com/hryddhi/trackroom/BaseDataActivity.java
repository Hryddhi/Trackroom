package com.hryddhi.trackroom;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.hryddhi.trackroom.interfaces.ApiInterface;
import com.hryddhi.trackroom.models.Token;
import com.hryddhi.trackroom.models.User;

public class BaseDataActivity extends AppCompatActivity {
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public Intent startActivity;

    public ApiInterface getApi () {
        return AppPrefs.getInstance(getApplicationContext()).getApi();
    }

    public void saveToken(String access, String refresh) { AppPrefs.getInstance(getApplicationContext()).saveToken(access,refresh); }

    public Token retrieveToken () { return AppPrefs.getInstance(getApplicationContext()).retrieveToken(); }

    public void saveUser(User user) { AppPrefs.getInstance(getApplicationContext()).saveUser(user); }

    public User retrieveUser (){ return AppPrefs.getInstance(getApplicationContext()).retrieveUser(); }

    public void deleteToken () {
        AppPrefs.getInstance(getApplicationContext()).deleteToken();
    }

    public void getUserInfo(String refresh, String access) { AppPrefs.getInstance(getApplicationContext()).getUserInfo(refresh, access); }

    public void getToken(String refresh) { AppPrefs.getInstance(getApplicationContext()).getToken(refresh); }

    public void blacklistToken(String refresh) { AppPrefs.getInstance(getApplicationContext()).blacklistToken(refresh); }

    public String getAccess() { return AppPrefs.getInstance(getApplicationContext()).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(getApplicationContext()).getRefresh(); }


    public void startTrackroom() {
        startActivity = new Intent(getApplicationContext(),ActivityTrackroom.class);
        startActivity(startActivity);
    }

    public void startLogin() {
        startActivity = new Intent(getApplicationContext(),ActivityLogin.class);
        startActivity(startActivity);
    }

    public void startRegister() {
        startActivity = new Intent(getApplicationContext(),ActivityRegister.class);
        startActivity(startActivity);
    }

    /*public void startUploadMaterial() {
        startActivity = new Intent(getApplicationContext(), ActivityUploadMaterial.class);
        startActivity(startActivity);
    }

    public void startInviteStudent() {
        startActivity = new Intent(getApplicationContext(), ActivityInviteStudent.class);
        startActivity(startActivity);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public String getRealPDFPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }*/

    public void isFirstLogin() {
        Log.d("Function isFirstLogin", "Inside");
        Token token = retrieveToken();
        User user = retrieveUser();
        //Log.d("Function isFirstLogin isFirstLogin", retrieveUser().getIsFirstLogin().toString());
        /*if (user.getIsFirstLogin()) {
            startUserSelection();
        }*/
         if (!user.getIsFirstLogin()) {
            startTrackroom();
        }
    }

    public boolean validateEditText(EditText editText) {
        if (editText.getText().toString().trim().length() >= 4 && editText.getText().toString().trim().length() <= 32) {
            return true;
        }
        return false;
    }

    public boolean validateInformation(EditText editText) {
        if (editText.getText().toString().trim().length() >= 4 && editText.getText().toString().trim().length() <= 32) {
            return true;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }


    public Boolean checkPasswords(EditText etPassword, EditText etPassword2) {
        String password = etPassword.getText().toString();
        String password2 = etPassword2.getText().toString();

        if (password.equals(password2)) return true;
        return false;
    }

    public boolean validateEmail(String email, EditText editText) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if(email.matches(regex))
            return true;
        else {
            editText.setError("Please Enter A Valid Email");
            editText.requestFocus();
            return false;
        }
    }
}
