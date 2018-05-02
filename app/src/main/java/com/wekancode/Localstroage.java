package com.wekancode;

import android.content.Context;
import android.content.SharedPreferences;

public class Localstroage {
  public static void saveLoginPref(Context context, boolean isLoginSuccessful, String access_token, String refresh_token) {
    SharedPreferences prefMember = context.getSharedPreferences("save_pref", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = prefMember.edit();
    editor.putBoolean("isLoginSuccessful", isLoginSuccessful);
    editor.putString("access_token", access_token);
    editor.putString("refresh_token", refresh_token);


    editor.commit();
  }

  public static boolean isAlreadyLoggedIn(Context context) {
    SharedPreferences prefLogin = context.getSharedPreferences("save_pref", Context.MODE_PRIVATE);
    return prefLogin.getBoolean("isLoginSuccessful", false);
  }

  public static String getAccesstoken(Context context) {
    SharedPreferences pref = context.getSharedPreferences("save_pref", Context.MODE_PRIVATE);
    return pref.getString("access_token", "");
  }

  public static String getRefreshtoken(Context context) {
    SharedPreferences pref = context.getSharedPreferences("save_pref", Context.MODE_PRIVATE);
    return pref.getString("refresh_token", "");
  }
  public static void clearAllPreferences(Context context) {
    SharedPreferences.Editor editor, editor1;
    SharedPreferences pref, pref1;
    pref = context.getSharedPreferences("save_pref", Context.MODE_PRIVATE);
    pref1 = context.getSharedPreferences("save_login", Context.MODE_PRIVATE);
    editor = pref.edit();
    editor.clear();
    editor.commit();
    editor1 = pref1.edit();
    editor1.clear();
    editor1.commit();
  }
}
