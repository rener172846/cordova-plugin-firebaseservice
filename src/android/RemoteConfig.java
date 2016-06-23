package com.rener.firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Rener on 5/21/2016.
 */

public class RemoteConfig extends CordovaPlugin {

  private static final String TAG = "FBRemoteConfig";

  private Activity mActivity;
  private CallbackContext mCallbackContext;

  private FirebaseRemoteConfig mFirebaseRemoteConfig;

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

    // Get Remote Config instance.
    // [START get_remote_config_instance]
    mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    // [END get_remote_config_instance]

    // Create Remote Config Setting to enable developer mode.
    // Fetching configs from the server is normally limited to 5 requests per hour.
    // Enabling developer mode allows many more requests to be made per hour, so developers
    // can test different config values during development.
    // [START enable_dev_mode]
    FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
      .setDeveloperModeEnabled(true/*BuildConfig.DEBUG*/)
      .build();
    mFirebaseRemoteConfig.setConfigSettings(configSettings);
    // [END enable_dev_mode]
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
    } else if ("fetch".equals(action)) {
      fetchData(callbackContext);
      return true;
    } else if ("setDefaults".equals(action)) {
      JSONObject arg1 = args.getJSONObject(0);
      String arg2 = args.getString(1);
      mFirebaseRemoteConfig.setDefaults(toMap(arg1), arg2);
      return true;
    } else if ("getLong".equals(action)) {
      String arg1 = args.getString(0);
      String arg2 = args.getString(1);
      Long value = mFirebaseRemoteConfig.getLong(arg1, arg2);
      callbackContext.success(value.toString());
      return true;
    } else if ("getByteArray".equals(action)) {
      String arg1 = args.getString(0);
      String arg2 = args.getString(1);
      byte[] value = mFirebaseRemoteConfig.getByteArray(arg1, arg2);
      callbackContext.success(Base64.encodeToString(value, Base64.DEFAULT));
      return true;
    } else if ("getString".equals(action)) {
      String arg1 = args.getString(0);
      String arg2 = args.getString(1);
      String value = mFirebaseRemoteConfig.getString(arg1, arg2);
      callbackContext.success(value);
      return true;
    } else if ("getBoolean".equals(action)) {
      String arg1 = args.getString(0);
      String arg2 = args.getString(1);
      boolean value = mFirebaseRemoteConfig.getBoolean(arg1, arg2);
      callbackContext.success(value? "ture" : "false");
      return true;
    } else if ("getDouble".equals(action)) {
      String arg1 = args.getString(0);
      String arg2 = args.getString(1);
      Double value = mFirebaseRemoteConfig.getDouble(arg1, arg2);
      callbackContext.success(value.toString());
      return true;
    }
    callbackContext.error("Action not Recognized.");

    return false;
  }

  /**
   * Fetch discount from server.
   */
  private void fetchData(final CallbackContext callbackContext) {
    long cacheExpiration = 3600; // 1 hour in seconds.
    // If in developer mode cacheExpiration is set to 0 so each fetch will retrieve values from
    // the server.
    if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
      cacheExpiration = 0;
    }

    // [START fetch_config_with_callback]
    // cacheExpirationSeconds is set to cacheExpiration here, indicating that any previously
    // fetched and cached config would be considered expired because it would have been fetched
    // more than cacheExpiration seconds ago. Thus the next fetch would go to the server unless
    // throttling is in progress. The default expiration duration is 43200 (12 hours).
    mFirebaseRemoteConfig.fetch(cacheExpiration)
      .addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
          if (task.isSuccessful()) {
            Log.d(TAG, "Fetch Succeeded");
            // Once the config is successfully fetched it must be activated before newly fetched
            // values are returned.
            mFirebaseRemoteConfig.activateFetched();
          } else {
            Log.d(TAG, "Fetch failed");
          }
          callbackContext.success();
        }
      });
    // [END fetch_config_with_callback]
  }

  public static Map<String, Object> toMap(JSONObject object) throws JSONException {
    Map<String, Object> map = new HashMap();
    Iterator keys = object.keys();
    while (keys.hasNext()) {
      String key = (String) keys.next();
      map.put(key, object.getString(key));
    }
    return map;
  }
}
