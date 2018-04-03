package com.adityaedu.themathwizz.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.fragments.AlertDialogFragment;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.adityaedu.themathwizz.helpers.FacebookHelpers;
import com.adityaedu.themathwizz.helpers.GoogleHelpers;
import com.adityaedu.themathwizz.helpers.SessionManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    Context context;
    FacebookHelpers facebookHelpers = new FacebookHelpers(MainActivity.this);
    GoogleHelpers googleHelpers = new GoogleHelpers(MainActivity.this);
    SessionManager sessionManager;


    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1000;

    //Facebook User Info
    public static final List<String> mPermissions = new ArrayList<String>(){{
        add("public_profile");
        add("email");
        //add("user_birthday");
    }};


    public void showHomeActivity(){
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void  AddChildActivity(){
        Intent intent = new Intent(getApplicationContext(),AddChildActivity.class);
        startActivity(intent);
        finish();
    }

    public void showProgressDialog(final String message) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                progressDialog = ProgressDialogSpinner.showProgressDialog(context, message);
            }
        }, 1);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        ParseInstallation.getCurrentInstallation().saveInBackground();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        context=this;

        sessionManager = new SessionManager(MainActivity.this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.e("Failed", "failed" + connectionResult.getErrorMessage());
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        GoogleSignInButton signInButton = findViewById(R.id.google_sign_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

        public void onClick(View view) {

        if (view.getId() == R.id.Login_textView2) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.button_register) {
           Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

        else if(view.getId() == R.id.fb_login_button){

            ParseFacebookUtils.logInWithReadPermissionsInBackground(MainActivity.this, mPermissions, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user == null) {
                        Log.d("Login", "Uh oh. The user cancelled the Facebook login.");
                        Toast.makeText(getApplicationContext(),"An Error occurred, Try Again!",Toast.LENGTH_LONG).show();
                    } else if (user.isNew()) {
                        Log.d("Login", "User signed up and logged in through Facebook!");
                        showProgressDialog("Finishing Sign up");
                        facebookHelpers.getUserDetailsFromFB();

                    } else {
                        Log.d("Login", "User logged in through Facebook!");
                        facebookHelpers.getUserDetailsFromParse();
                        }

                }
            });
        }

        }

        public  void SessionCreate(String username, String password){
            sessionManager = new SessionManager(context);
            sessionManager.createLoginSession(username,password);

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Facebook
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);

        //Google
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            googleHelpers.handleSignInResult(result);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialogFragment.showAlertDialog(MainActivity.this, "Cancel", "Exit", "Confirm Exit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                        moveTaskToBack(true);
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }
        );
    }


}