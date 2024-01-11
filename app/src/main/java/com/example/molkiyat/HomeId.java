package com.example.molkiyat;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class HomeId{
  @Exclude
  public String HomeID;
  public <T extends HomeId>T withId(@NonNull final String id){
    this.HomeID= id;
    return (T)this;
  }
}