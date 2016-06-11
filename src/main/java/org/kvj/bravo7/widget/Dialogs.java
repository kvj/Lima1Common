package org.kvj.bravo7.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by kvorobyev on 5/15/15.
 */
public class Dialogs {

    public interface Callback<T> {
        public void run(T data);
    }

    public static EditText inputTextDialog(Context context, String title, final Callback<String> callback, String... captions) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        final EditText editText = new EditText(context);
        if (null != title) { // Have title
            dialog.setTitle(title);
        }
        dialog.setView(editText, 5, 5, 5, 5);
        dialog.setPositiveButton(captions.length > 0 ? captions[0] : "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.run(editText.getText().toString().trim());
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton(captions.length > 1 ? captions[1] : "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return editText;
    }

    public static AlertDialog questionDialog(Context context, String title, String message, final Callback<Integer> callback, String... captions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message);
        if (captions.length == 0) {
            // OK/Cancel
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (which == 0) callback.run(0);
                }
            };
            builder
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", listener);
        } else {
            CharSequence[] items = new CharSequence[captions.length];
            for (int i = 0; i < captions.length; i++) { // $COMMENT
                items[i] = captions[i];
            }
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    callback.run(which);
                }
            };
            builder.setItems(items, listener);
        }
        return builder.show();
    }

    public static void toast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

}
