package com.adityaedu.themathwizz.fragments;

import android.app.ProgressDialog;
import android.content.Context;


/**
 * Created by Preetham on 2/7/2018.
 *
 */

public class ProgressDialogSpinner {

        public static ProgressDialog showProgressDialog(Context context, String message){
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(true);
            //progressDialog.setTitle("Loading");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            return progressDialog;
        }
    }
