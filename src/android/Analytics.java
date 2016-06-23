package com.rener.firebase;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rener on 5/27/2016.
 */

public class Analytics extends CordovaPlugin {

  private static final String TAG = "FBAnalytics";

  private Activity mActivity;
  private CallbackContext mCallbackContext;

  private FirebaseAnalytics mFirebaseAnalytics;

  /**
   * Sets the context of the Command. This can then be used to do things like
   * get file paths associated with the Activity.
   *
   * @param cordova The context of the main Activity.
   * @param webView The CordovaWebView Cordova is running in.
   */
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    mActivity = cordova.getActivity();

    // [START shared_app_measurement]
    // Obtain the FirebaseAnalytics instance.
    mFirebaseAnalytics = FirebaseAnalytics.getInstance(mActivity);
    // [END get_remote_config_instance]
  }

  /**
   * Executes the request and returns PluginResult.
   *
   * @param action            The action to execute.
   * @param args              JSONArry of arguments for the plugin.
   * @param callbackContext   The callback id used when calling back into JavaScript.
   * @return                  True if the action was valid, false if not.
   */

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if ("init".equals(action)) {
      callbackContext.success();
      return true;
    } else if ("setUserProperty".equals(action)) {
      String userKey = args.getString(0);
      String userProperty = args.getString(1);
      mFirebaseAnalytics.setUserProperty(userKey, userProperty);
      callbackContext.success();
      return true;
    } else if ("logEvent".equals(action)) {
      String key = args.getString(0);
      JSONArray log = args.getJSONArray(1);

      Bundle params = new Bundle();
      int len = log.length();
      String tempTag, tempValue;
      for (int i=0; i<len; i++) {
        tempTag = log.getJSONObject(i).getString("tag");
        tempValue = log.getJSONObject(i).getString("value");
        params.putString(tempTag, tempValue);
      }

      mFirebaseAnalytics.logEvent(key, params);
      callbackContext.success();
      return true;
    }
    callbackContext.error("Action not Recognized.");

    return false;
  }
}
