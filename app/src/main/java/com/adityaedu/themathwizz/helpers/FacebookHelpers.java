package com.adityaedu.themathwizz.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.adityaedu.themathwizz.activities.MainActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Preetham on 3/13/2018.
 *
 */

public class FacebookHelpers {

    private Context context;
    private String fbName, fbEmail,fbGender, fbId;
    public FacebookHelpers(Context context) {
        this.context = context;
    }
    public  ProgressDialog progressDialog;


    //Facebook Login

    public void getUserDetailsFromParse() {
        ((MainActivity)context).showProgressDialog("Logging you in");
        ParseUser.getCurrentUser();
        String username = ParseUser.getCurrentUser().getUsername();
        String password = ParseUser.getCurrentUser().getSessionToken();
        ((MainActivity)context).SessionCreate(username,password);
        ((MainActivity)context).showHomeActivity();
        Toast.makeText(context, "Welcome back", Toast.LENGTH_LONG).show();

    }

    public void getUserDetailsFromFB() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                try {
                    fbName = jsonObject.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    fbEmail = jsonObject.getString("email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    fbId = jsonObject.getString("id");
                } catch (JSONException e){
                    e.printStackTrace();
                }
                try {
                    fbGender=jsonObject.getString("gender");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                saveNewUser();
                //Session
                ((MainActivity)context).SessionCreate(fbId,fbEmail);

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email,id,gender");
        request.setParameters(parameters);
        request.executeAsync();
        ((MainActivity)context).AddChildActivity();
    }

    private void saveNewUser() {
        ParseUser parseUser = ParseUser.getCurrentUser();
        try
        {
            parseUser.setUsername(fbId);
            parseUser.setEmail(fbEmail);
            parseUser.put("Name", fbName);
            parseUser.put("Gender",fbGender);
        }
        catch(Exception e)
        {
            Log.d("Facebook Sign Up Error",""+e.getMessage());
        }
        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(context, "Successfully Registered", Toast.LENGTH_LONG).show();
            }
        });
    }
}
