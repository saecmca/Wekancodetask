package com.wekancode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class InternetCheck extends AppCompatActivity implements View.OnClickListener {

  public static int TYPE_WIFI = 1;
  public static int TYPE_MOBILE = 2;
  public static int TYPE_NOT_CONNECTED = 0;

  private LinearLayout coordinatorLayout;
  private boolean internetConnected = true;
  private Button btnTry;
  private String screenName = "";
  private TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_internet_check);
    coordinatorLayout = findViewById(R.id.constraint);
    textView = findViewById(R.id.textView);
    btnTry = findViewById(R.id.btnTry);
    btnTry.setOnClickListener(this);
    try {
      screenName = getIntent().getStringExtra("Screen");
    } catch (Exception e) {
      e.printStackTrace();
    }



  }

  @Override
  protected void onResume() {
    super.onResume();
    registerInternetCheckReceiver();
  }

  @Override
  protected void onPause() {
    super.onPause();
    unregisterReceiver(broadcastReceiver);
  }

  /**
   * Method to register runtime broadcast receiver to show snackbar alert for internet connection..
   */
  private void registerInternetCheckReceiver() {
    IntentFilter internetFilter = new IntentFilter();
    internetFilter.addAction("android.net.wifi.STATE_CHANGE");
    internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    registerReceiver(broadcastReceiver, internetFilter);
  }

  /**
   * Runtime Broadcast receiver inner class to capture internet connectivity events
   */
  public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String status = getConnectivityStatusString(context);
      setSnackbarMessage(status, false);
    }
  };

  public static int getConnectivityStatus(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    if (null != activeNetwork) {
      if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
        return TYPE_WIFI;

      if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
        return TYPE_MOBILE;
    }
    return TYPE_NOT_CONNECTED;
  }

  public static String getConnectivityStatusString(Context context) {
    int conn = getConnectivityStatus(context);
    String status = null;
    if (conn == TYPE_WIFI) {
      status = "Wifi enabled";
    } else if (conn == TYPE_MOBILE) {
      status = "Mobile data enabled";
    } else if (conn == TYPE_NOT_CONNECTED) {
      status = "Not connected to Internet";
    }
    return status;
  }

  private void setSnackbarMessage(String status, boolean showBar) {
    String internetStatus = "";
    Snackbar snackbar;

    if (status.equalsIgnoreCase("Wifi enabled") || status.equalsIgnoreCase("Mobile data enabled")) {

      textView.setText("Netconnected");
      textView.setTextColor(ContextCompat.getColor(this, R.color.green));

    } else {

      textView.setText("No Internet");
      textView.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

    }
    snackbar = Snackbar.make(coordinatorLayout, internetStatus, Snackbar.LENGTH_LONG);

    // Changing action button text color
    View sbView = snackbar.getView();
    if (internetStatus.equals("Netconnected"))
      sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
    else
      sbView.setBackgroundColor(Color.RED);

    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
    textView.setTextColor(Color.WHITE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    } else {
      textView.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    if (internetStatus.equalsIgnoreCase("Lost internet")) {
      if (internetConnected) {
        snackbar.show();
        internetConnected = false;
      }
    } else {
      if (!internetConnected) {
        internetConnected = true;
        snackbar.show();
      }
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnTry:
        if (internetConnected) {
          if (screenName.equals("Login")) {
            Intent main = new Intent(this, Loginscreen.class);

            startActivity(main);
            finish();
          }else {
            Intent main = new Intent(this, Dotcreate.class);

            startActivity(main);
            finish();
          }

        } else {
          Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }
        break;

    }

  }
}
