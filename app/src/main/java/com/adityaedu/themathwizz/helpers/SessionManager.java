package com.adityaedu.themathwizz.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.adityaedu.themathwizz.activities.MainActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by Preetham on 3/16/2018.
 *
 */

public class SessionManager {

    private  SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    private Context context;

    private  static  final String preferences_Name ="LoginPreferences";

    private  static  final  String Login = "IsLoggedIn";

    private static final String KEY_Username = "username";

    private static final  String KEY_Password = "password";

    public SessionManager(Context context1){
        this.context = context1;
        preferences = context.getSharedPreferences(preferences_Name, 0);
        editor=preferences.edit();
        editor.apply();
    }

    public void createLoginSession(String username, String password){
        editor.putBoolean(Login,true);
        editor.putString(KEY_Username,username);
        editor.putString(KEY_Password,password);
        editor.commit();

    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_CLEAR_TASK |FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(intent);
        }
    }

    /*
    public HashMap<String, String> getSessionUserDetails(){
        HashMap<String,String> sessionUser = new HashMap<String, String>();
        sessionUser.put(KEY_Username, preferences.getString(KEY_Username, null));
        sessionUser.put(KEY_Password, preferences.getString(KEY_Password, null));
        return sessionUser;
    }
    */

    public void logoutSessionUser(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private boolean isLoggedIn(){
        return preferences.getBoolean(Login, false);
    }

}
