package com.rener.firebase;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by Rener on 5/27/2016.
 */

public class FBPushActivity extends Activity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle extras = getIntent().getExtras();

    if (extras != null) {
      String notificationExtras = extras.getString("extras");

      Intent intent = new Intent(Notification.MSG_RECEIVED_BROADCAST_KEY);
      intent.putExtra("data", notificationExtras);

      Log.d(TAG, "Booting GPPActivity with data: "+notificationExtras);

      SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
      sharedPreferences.edit().putString(Notification.LAST_PUSH_KEY, notificationExtras).commit();

      LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    finish();

    PackageManager pm = getPackageManager();
    Intent launchIntent = pm.getLaunchIntentForPackage(getApplicationContext().getPackageName());
    startActivity(launchIntent);
  }

  @Override
  protected void onResume() {
    super.onResume();
    final NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.cancelAll();
  }

}
