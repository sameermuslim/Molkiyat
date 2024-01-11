package com.example.molkiyat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DescriptionActivity extends Activity {

  TextView mDescription, mAddrees, mPriec;
  ImageView mpic;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_description);
    mDescription = findViewById(R.id.desctription_tv);
    mAddrees = findViewById(R.id.address_tv);
    mPriec = findViewById(R.id.price_tv);
    mpic = findViewById(R.id.pic_iv);


    Intent intent = getIntent();
    String dill = intent.getExtras().getString("Dill");
    int price = intent.getExtras().getInt("Price");
    String address = intent.getExtras().getString("Address");
    int pic = intent.getExtras().getInt("Pic");

    mDescription.setText(dill);
    mAddrees.setText(address);
    mpic.setImageResource(pic);
    mPriec.setText(String.valueOf(price));


  }
}
