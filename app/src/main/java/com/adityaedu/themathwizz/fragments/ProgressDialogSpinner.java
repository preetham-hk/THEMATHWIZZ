package com.adityaedu.themathwizz.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by preet on 2/7/2018.
 */

public class ProgressDialog1 {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        public void cc{
            progressDialog = new ProgressDialog(ProgressDialog1.class.this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
        }


    }

}