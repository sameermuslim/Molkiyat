package com.example.molkiyat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
   Spinner provinceNameSpn,provinceTypesSpn,maxSpn,minSpn;
    ArrayAdapter<CharSequence> provinceNameAdapter,provinceTypeAdapter,maxAdapter,minAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // filling province name spinner
        provinceNameSpn = findViewById(R.id.province_sp);
        provinceNameAdapter = ArrayAdapter.createFromResource(this,R.array.province_name,android.R.layout.simple_spinner_item);
        provinceNameAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        provinceNameSpn.setAdapter(provinceNameAdapter);
        provinceNameSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+" selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // filling province type spinner
        provinceTypesSpn = findViewById(R.id.property_type_spn);
        provinceTypeAdapter = ArrayAdapter.createFromResource(this,R.array.property_type,android.R.layout.simple_spinner_item);
        provinceTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        provinceTypesSpn.setAdapter(provinceTypeAdapter);
        provinceTypesSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+" selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        minSpn = findViewById(R.id.min_spn);
        minAdapter = ArrayAdapter.createFromResource(this,R.array.min_price,android.R.layout.simple_spinner_item);
        minAdapter .setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        minSpn.setAdapter(minAdapter);
        minSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        maxSpn = findViewById(R.id.max_spn);
        maxAdapter = ArrayAdapter.createFromResource(this,R.array.min_price,android.R.layout.simple_spinner_item);
        maxAdapter .setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        maxSpn.setAdapter(minAdapter);
        maxSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
