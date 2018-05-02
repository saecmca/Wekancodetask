package com.wekancode.request;

public class LoginReq {
  String email,password,device_id,device_type;
  public LoginReq(String email,String password,String device_id,String device_type){
    this.email=email;
    this.password=password;
    this.device_id=device_id;
    this.device_type=device_type;
  }
}
