package com.adityaedu.themathwizz.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

/**
 * Created by preetham on 3/7/2018.
 *
 */

public class DialogPopupFragment {

    public static Dialog showDialog(Context context){
        Dialog dialog = new Dialog(context);
        if (dialog.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialog.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialog.setCancelable(false);
        dialog.show();
        return dialog;

    }
}
