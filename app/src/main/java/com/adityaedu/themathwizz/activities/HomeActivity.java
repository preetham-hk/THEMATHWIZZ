package com.adityaedu.themathwizz.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.fragments.AlertDialogFragment;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.adityaedu.themathwizz.helpers.SessionManager;
import com.adityaedu.themathwizz.quiz.RecentQuiz;
import com.adityaedu.themathwizz.topics.Topics_Class;
import com.parse.LogOutCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;


/**
 * Created by preetham on 2/7/2018.
 */

public class HomeActivity extends AppCompatActivity {

    String[] classColNames = new String[]{"class_1", "class_2", "class_3", "class_4", "class_5", "class_6", "class_7", "class_8", "class_9"};

    String[] classTitles = new String[]{"Class 1", "Class 2", "Class 3", "Class 4", "Class 5", "Class 6", "Class 7", "Class 8", "Class 9",};

    protected ImageButton class1_imageButton, class2_imageButton, class3_imageButton, class4_imageButton, class5_imageButton, class6_imageButton, class7_imageButton, class8_imageButton, class9_imageButton;

    SessionManager sessionManager;
    FlipperLayout flipperLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.setTitle("Home");
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        class1_imageButton = findViewById(R.id.class1_imageButton);
        class2_imageButton = findViewById(R.id.class2_imageButton);
        class3_imageButton = findViewById(R.id.class3_imageButton);
        class4_imageButton = findViewById(R.id.class4_imageButton);
        class5_imageButton = findViewById(R.id.class5_imageButton);
        class6_imageButton = findViewById(R.id.class6_imageButton);
        class7_imageButton = findViewById(R.id.class7_imageButton);
        class8_imageButton = findViewById(R.id.class8_imageButton);
        class9_imageButton = findViewById(R.id.class9_imageButton);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        flipperLayout = findViewById(R.id.flipper_layout);

        setFlipperLayout();

        Button recentButton = findViewById(R.id.home_recent_activities_Button);
        recentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecentQuiz.class);
                startActivity(intent);
            }
        });

    }


    private void setFlipperLayout() {
        int images[] = new int[]{
                R.drawable.m1, R.drawable.m2, R.drawable.m3, R.drawable.m4
        };

        for (int i = 0; i < 4; i++) {
            FlipperView flipperView = new FlipperView(getBaseContext());
            flipperView.setImageDrawable(images[i]);
            flipperLayout.addFlipperView(flipperView);

        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.class1_imageButton) {
            ImageButtonActivity(0);
        } else if (view.getId() == R.id.class2_imageButton) {
            ImageButtonActivity(1);
        } else if (view.getId() == R.id.class3_imageButton) {
            ImageButtonActivity(2);
        } else if (view.getId() == R.id.class4_imageButton) {
            ImageButtonActivity(3);
        } else if (view.getId() == R.id.class5_imageButton) {
            ImageButtonActivity(4);
        } else if (view.getId() == R.id.class6_imageButton) {
            ImageButtonActivity(5);
        } else if (view.getId() == R.id.class7_imageButton) {
            ImageButtonActivity(6);
        } else if (view.getId() == R.id.class8_imageButton) {
            ImageButtonActivity(7);
        } else if (view.getId() == R.id.class9_imageButton) {
            ImageButtonActivity(8);
        }
    }

    private void ImageButtonActivity(int i) {
        Intent intent = new Intent(getApplicationContext(), Topics_Class.class);
        intent.putExtra("className", classColNames[i]);
        intent.putExtra("classTitle", classTitles[i]);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, R.id.menu_profile, Menu.NONE, "Profile");
        menu.add(Menu.NONE, R.id.menu_logout, Menu.NONE, "Logout");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile:
                Intent intent = new Intent(getApplicationContext(), UsersProfileActivity.class);
                startActivity(intent);

                return true;
            case R.id.menu_logout:

                final ProgressDialog progressDialog = ProgressDialogSpinner.showProgressDialog(this, "Logging Out");
                sessionManager.logoutSessionUser();
                ParseUser.logOutInBackground(new LogOutCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();
                        } else {
                            Log.i("tag", "Logout failed");
                        }
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {


        AlertDialogFragment.showAlertDialog(HomeActivity.this, "Cancel", "Exit", "Confirm Exit",
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