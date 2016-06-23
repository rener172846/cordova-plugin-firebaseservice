package com.rener.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Rener on 5/27/2016.
 */

public class Notification extends CordovaPlugin {

  private final String TAG = "FBNotification";
  public static final String LAST_PUSH_KEY = "LAST_PUSH";

  public static final String MSG_RECEIVED_BROADCAST_KEY = "MESSAGE_RECEIVED";

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
  }

  @Override
  public boolean execute(String action, final JSONArray args, CallbackContext callbackContext) throws JSONException {

    if ("init".equals(action)) {
      Log.d(TAG, "InstanceID token: " + FirebaseInstanceId.getInstance().getToken());
      callbackContext.success();
    }
    callbackContext.error("Action not Recognized.");

    return false;
  }
}
