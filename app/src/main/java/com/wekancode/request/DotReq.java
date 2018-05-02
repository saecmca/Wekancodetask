package com.wekancode.request;

public class DotReq {
  String name,ph_country_code,phone_number,category_id,address,description,lat,lng,post_code,street,city,state,country,folder_id;
  public DotReq(String name,String ph_country_code,String phone_number,String category_id,String address,String description,
                String lat,String lng,String post_code,String street,String city,String state,String country,String folder_id){
    this.name=name;
    this.ph_country_code=ph_country_code;
    this.phone_number=phone_number;
    this.category_id=category_id;
    this.address=address;
    this.description=description;
    this.lat=lat;
    this.lng=lng;
    this.post_code=post_code;
    this.street=street;
    this.city=city;
    this.state=state;
    this.country=country;
    this.folder_id=folder_id;

  }
}
