package com.hryddhi.trackroom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hryddhi.trackroom.models.Login;
import com.hryddhi.trackroom.models.Token;
import com.hryddhi.trackroom.models.User;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends BaseDataActivity{
    EditText email;
    EditText password;
    TextView txtRegister;
    Button btnSignIn;
    Button btnSignInWithGoogle;

    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        Log.d("Activity", "Login On Create");

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        txtRegister = findViewById(R.id.tv_login_sign_up);
        btnSignIn = findViewById(R.id.btn_login);
        btnSignInWithGoogle = findViewById(R.id.btn_login_using_google);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Function", "btnSignIn");
                //Getting the texts in the text fields
                if (checkData()) {
                    loginUser();
                }

            }
        });


        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });

        btnSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }


    private boolean checkData() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        if (validateInformation(email) && validateInformation(password)) {
            if(validateEmail(userEmail,email)) {
                Log.d("Function", "login");
                return true;
            }
            return false;
        }
        return false;
    }

    private void loginUser() {
        Log.d("Function loginUser", "Inside");
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        Login loginUser = new Login(userEmail, userPassword);
        Call<Token> callLogin = getApi().login(loginUser);
        callLogin.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                if(response.isSuccessful()) {
                    Log.d("Function loginUser", "Response Success");
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                    Token token = response.body();
                    Log.d("Function loginUser", "Calling saveToken");
                    saveToken(token.getAccess(), token.getRefresh());
                    getAccountInfo(token.getAccess());
                }
                else if(response.code() == BAD_REQUEST) {
                    Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_LONG).show();
                }
                else if(response.code() == UNAUTHORIZED) {
                    Toast.makeText(getApplicationContext(), "Unauthorized Request", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Email or Password Incorrect", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(ActivityLogin.this, "Server Not Found , Make Sure You Are Connected To The Internet", Toast.LENGTH_SHORT).show();

            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @SuppressLint("LongLogTag")
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                Log.d("google sing in button information ", personName);
                Log.d("google sing in button information ", personGivenName);
                Log.d("google sing in button information ", personFamilyName);
                Log.d("google sing in button information ", personEmail);
                Log.d("google sing in button information ", personId);
                Log.d("google sing in button information ", String.valueOf(personPhoto));
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Function handleSignInRsult", "signInResult:failed code=" + e.getStatusCode());
        }

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();

            Log.d("Sign in with google token id", idToken);

            // TODO(developer): send ID Token to server and validate

            saveToken(idToken, "fail");

            updateUI(idToken);
        } catch (ApiException e) {
            Log.w("Signin with google exception", "handleSignInResult:error", e);
            //updateUI(null);
        }

    }

    private void updateUI(String idToken) {
        MultipartBody googleIdToken = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("auth_token", idToken)
                .build();
        Call<Token> googleSignin = getApi().googleSignIn(googleIdToken);

        googleSignin.enqueue(new Callback<Token>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Log.d("Sign in with google token id response ", "response is successful : Code " + response.code());
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                    Token token = response.body();
                    Log.d("Function loginUser", "Calling saveToken");
                    saveToken(token.getAccess(), token.getRefresh());
                    getAccountInfo(token.getAccess());
                }
                else {
                    Log.d("Sign in with google token id response ", "response is failed " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }

    private void getAccountInfo(String access) {
        String token = "Bearer " + access;
        Call<User> getUserInfo = getApi().account(token);

        getUserInfo.enqueue(new Callback<User>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User u = response.body();
                    Log.d("Function ActivityLogin getAccountInfo", "Response Success");
                    Log.d("Function ActivityLogin getAccountInfo", "Calling saveUser");
                    saveUser(u);


                     if (!u.getIsFirstLogin()) {
                        Log.d("Function ActivityLogin getAccountInfo if not isFirstLogin", "inside");
                        startTrackroom();
                    }

                }
                else if(response.code() == UNAUTHORIZED) {
                    Log.d("Function getUserInfo", "Response Unauthorized");
                    //getToken(refresh);
                }
                else {
                    Log.d("Function getUserInfo", "Response Failed");
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Function getUserInfo", "Failure");
                Log.d("Function getUserInfo Throwable T", t.toString());
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
