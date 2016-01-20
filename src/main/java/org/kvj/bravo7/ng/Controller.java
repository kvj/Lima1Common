package org.kvj.bravo7.ng;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import org.kvj.bravo7.log.AndroidLogger;
import org.kvj.bravo7.log.Logger;
import org.kvj.bravo7.ng.conf.Configurator;
import org.kvj.bravo7.ng.conf.SharedPreferencesConfigurable;
import org.kvj.bravo7.util.Compat;
import org.kvj.bravo7.util.DataUtil;

import java.util.Date;

/**
 * Created by kvorobyev on 4/8/15.
 */

public class Controller {

    protected final Context context;
    protected Logger logger = Logger.forInstance(this);

    public Controller(Context context, String name) {
        Logger.setOutput(new AndroidLogger(name));
        this.context = context;
    }

    protected void init() {}

    public Configurator settings() {
        return new Configurator(new SharedPreferencesConfigurable(context, PreferenceManager.getDefaultSharedPreferences(context)));
    }

    public SharedPreferences preferences(String s) {
        if (TextUtils.isEmpty(s)) { // Use app level
            return PreferenceManager.getDefaultSharedPreferences(context);
        }
        return context.getSharedPreferences(s, Context.MODE_PRIVATE);
    }

    public Configurator settings(String s) {
        return new Configurator(new SharedPreferencesConfigurable(context, preferences(s)));
    }

    public Context context() {
        return context;
    }

    public void messageShort(String message) {
        logger.w("Toast:", message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void messageLong(String message) {
        logger.w("Toast:", message);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void question(Context context, String message, final Runnable yesHandler, final Runnable noHandler) {
        new AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (null != yesHandler) yesHandler.run();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (null != noHandler)
                    noHandler.run();
            }
        }).show();
    }

    public PowerManager.WakeLock lock() {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock lock =
            powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Lima1");
        return lock;
    }

    public void cancelAlarm(PendingIntent intent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(intent);
    }

    public void scheduleAlarm(final Date when, final PendingIntent intent) {
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Compat.levelAware(Build.VERSION_CODES.KITKAT, new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, when.getTime(), intent);
            }
        }, new Runnable() {
            @Override
            public void run() {
                alarmManager.set(AlarmManager.RTC_WAKEUP, when.getTime(), intent);
            }
        });
    }

    public void input(Context context, String message, String value, final DataUtil.Callback<CharSequence> yesHandler,
                      final DataUtil.Callback<CharSequence> noHandler) {
        final EditText input = new EditText(context);
        input.setSingleLine();
        if (!TextUtils.isEmpty(value)) {
            input.setText(value);
        }
        new AlertDialog.Builder(context)
            .setView(input)
            .setTitle(message)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (null != yesHandler)
                        yesHandler.call(input.getText());
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (null != noHandler) noHandler.call(input.getText());
            }
        }).show();
        input.selectAll();
        input.requestFocus();
    }
}
