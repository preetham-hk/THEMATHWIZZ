package com.adityaedu.themathwizz.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.adityaedu.themathwizz.activities.MainActivity;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

/**
 * Created by Preetham on 3/14/2018.
 *
 */

public class GoogleHelpers {

    private Context context;

    public GoogleHelpers(Context context) {
        this.context = context;
    }

    public ProgressDialog progressDialog;


    private void showProgressDialog(final String message) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                progressDialog = ProgressDialogSpinner.showProgressDialog(context, message);
            }
        }, 1);
    }


    public void handleSignInResult(GoogleSignInResult result) {
        Log.e("handleSignIn", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            final GoogleSignInAccount userAccount = result.getSignInAccount();
            if (userAccount != null) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                query.setLimit(10000);
                query.whereEqualTo("email", userAccount.getEmail());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, com.parse.ParseException e) {
                        if (e == null) {
                            if (objects.size() == 0) {
                                showProgressDialog("Finishing Sign Up");
                                Log.d("Execution", "1");
                                saveNewUserGoogle(userAccount);
                            } else {
                                showProgressDialog("Logging you");
                                Log.d("Execution", "2");
                                loginGoogleUser(objects.get(0), userAccount);
                            }
                        } else {
                            saveNewUserGoogle(userAccount);
                            Log.d("Execution", "3");

                        }
                    }
                });

            } else {
                Log.e("failed", "failed to sign in");
            }
        }

    }

    private void loginGoogleUser(ParseObject j, GoogleSignInAccount acct) {

        ParseUser.logInInBackground(j.getString("username"), String.valueOf(acct.getId()), new LogInCallback() {
            @Override
            public void done(ParseUser user, com.parse.ParseException e) {
                if (user != null) {
                    String username = user.getEmail();
                    String password = user.getSessionToken();
                    //Creating Session
                    ((MainActivity)context).SessionCreate(username,password);
                    ((MainActivity)context).showHomeActivity();
                    Toast.makeText(context, "Welcome Back ", Toast.LENGTH_LONG).show();
                    Log.d("Logged", "Success");
                } else {
                    Log.e("failed", "could not be validated");
                }
            }
        });
    }

    private void saveNewUserGoogle(GoogleSignInAccount userAccount) {
        final ParseUser user = new ParseUser();
        String Name = userAccount.getDisplayName();
        String Email = userAccount.getEmail();
        final String Username = userAccount.getId();
        final String idToken = userAccount.getIdToken();
        String password = userAccount.getId();
        user.setUsername(Username);
        user.setEmail(Email);
        user.put("Name", Name);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Log.e("SaveTest", "Successful");
                    //Creating Session
                    ((MainActivity)context).SessionCreate(Username,idToken);
                    ((MainActivity)context).AddChildActivity();
                    Toast.makeText(context, "Successfully Registered", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("Failed", "" + e.getMessage());
                }
            }


        });
    }
}
