package com.adityaedu.themathwizz.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


/**
 * Created by preet on 3/7/2018.
 *
 */

public class AlertDialogFragment {

    public static void showAlertDialog(Context context, String negative, String positive, String message,
                                       DialogInterface.OnClickListener onClickListenerP, DialogInterface.OnClickListener onClickListenerN ){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(positive,onClickListenerP);
        alertDialog.setNegativeButton(negative,onClickListenerN);
        alertDialog.show();

}
}

