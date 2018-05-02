package com.wekancode.request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.wekancode.InternetCheck;

/**
 * Created by Mani on 17-03-2017.
 */

public class Utility {

  public static boolean isNetworkStatusAvialable(Context context, String checkme) {
    ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
    if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
      Intent main = new Intent(context, InternetCheck.class);
      main.putExtra("Screen", checkme);
      context.startActivity(main);
      ((Activity) context).finish();
      //alerts(context, "Network Not Available! Please turn on Wifi or use mobile data.", "0");
      return false;
    }
    return true;
  }
//strType=0 -finish
  //strType=1 -Restart the Activity
  //strType=2 -Dismiss dialog


}
