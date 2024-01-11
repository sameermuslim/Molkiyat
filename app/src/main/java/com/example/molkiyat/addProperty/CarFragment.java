package com.example.molkiyat.addProperty;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.molkiyat.Constant;
import com.example.molkiyat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarFragment extends Fragment {


    private Button carButton;
    private EditText price, city;

    public CarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_car, container, false);

        Bundle gameData1 = getArguments();
        if (gameData1 != null)
        {
            String over = gameData1.getString(Constant.CITY);
            String ee=gameData1.getString(Constant.PRICE);
            Toast.makeText(getActivity(), over+ee, Toast.LENGTH_SHORT).show();

        }
        else         {
            Toast.makeText(getActivity(), "Bundle is null", Toast.LENGTH_SHORT).show();
        }
        carButton =rootView.findViewById(R.id.button);
        carButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                price=rootView.findViewById(R.id.add_price_et);
                city=rootView.findViewById(R.id.add_city_et);
                TextView f=rootView.findViewById(R.id.carfragbtn);
                Toast.makeText(getContext(), price.getText()+" "+f.getText(), Toast.LENGTH_SHORT).show();

            }
        });
        return rootView;
    }

}
