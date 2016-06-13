package org.kvj.bravo7.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
        final AlertDialog dialog;
        Button[] items = new Button[captions.length];
        if (captions.length == 0) {
            // OK/Cancel
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) callback.run(0);
                    if (which == DialogInterface.BUTTON_NEGATIVE) callback.run(1);
                    dialog.dismiss();
                }
            };
            builder
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", listener);
        } else {
            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams llp =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1);

            for (int i = 0; i < captions.length; i++) { // Create buttons
                Button b = new AppCompatButton(context);
                b.setText(captions[i]);
                items[i] = b;
                ll.addView(b, llp);
            }
            builder.setView(ll);
        }
        dialog = builder.show();
        for (int i = 0; i < items.length; i++) { // $COMMENT
            final int index = i;
            items[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    callback.run(index);
                }
            });
        }
        return dialog;
    }

    public static void toast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

}
