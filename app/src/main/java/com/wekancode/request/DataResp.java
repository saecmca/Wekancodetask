package com.wekancode.request;

public class DataResp {
  String id,access_token,refresh_token;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public String getRefresh_token() {
    return refresh_token;
  }

  public void setRefresh_token(String refresh_token) {
    this.refresh_token = refresh_token;
  }

  public double getExpires_in() {
    return expires_in;
  }

  public void setExpires_in(double expires_in) {
    this.expires_in = expires_in;
  }

  double expires_in;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  String message;
}
