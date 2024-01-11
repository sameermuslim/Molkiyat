package com.example.molkiyat.addProperty;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.molkiyat.Constant;
import com.example.molkiyat.Property;
import com.example.molkiyat.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.MapView;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {
    private Toolbar mToolbar;
    private MapView mMapView;
    private EditText mCity, mDistrict, mLocationArea, mStreet, mHomeNumber, mAddress;
    private View mView;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private Property mProperty;

    SendToAdditionalFrag mInterFace;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_location, container, false);
        if (isServicessOk()) {
            //initializing Content of Fragment
            contentInitiation();

        }
        return mView;
    }

    public void init() {
                AdditionalDetails fragment = new AdditionalDetails();
                doFragmentTransaction(fragment, "sameer", true, "");
    }

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle gameData1 = getArguments();

        final String price = gameData1.getString(Constant.PRICE);
        final String propertyName = gameData1.getString(Constant.TITLE);
        final String purpose = gameData1.getString(Constant.DEAL_TYPE);
        final String propertyType = gameData1.getString(Constant.PROPERTY_TYPE);
        final String description = gameData1.getString(Constant.DESCRIPTION);
        final String area = gameData1.getString(Constant.AREA);
        final String bedrooms = gameData1.getString(Constant.BED_ROOMS);
        final String bathrooms = gameData1.getString(Constant.BATH_ROOMS);
        final String floor = gameData1.getString(Constant.FLOOR);
        // from this fragment
        final String city = mCity.getText().toString();
        final String district = mDistrict.getText().toString();
        final String locationArea = mLocationArea.getText().toString();
        final String homeNumber = mHomeNumber.getText().toString();
        final String address = mAddress.getText().toString();
        final String street = mStreet.getText().toString();


        Bundle gameData = new Bundle();

        gameData.putString(Constant.PRICE, price);
        gameData.putString(Constant.TITLE, propertyName);
        gameData.putString(Constant.DEAL_TYPE, purpose);
        gameData.putString(Constant.PROPERTY_TYPE, propertyType);
        gameData.putString(Constant.DESCRIPTION, description);
        gameData.putString(Constant.AREA, area);
        gameData.putString(Constant.BED_ROOMS, bedrooms);
        gameData.putString(Constant.BATH_ROOMS, bathrooms);
        gameData.putString(Constant.FLOOR, floor);
        //from this fragment
        gameData.putString(Constant.CITY, city);
        gameData.putString(Constant.DISTRICT, district);
        gameData.putString(Constant.LOCATION_ARIA, locationArea);
        gameData.putString(Constant.STREET, street);
        gameData.putString(Constant.ADDRESS, address);
        gameData.putString(Constant.HOUSE_NUMBER, homeNumber);


        transaction.replace(R.id.container, fragment, tag);
        transaction.addToBackStack(null);
        fragment.setArguments(gameData);
        transaction.commit();
    }

    public boolean isServicessOk() {
        Log.d(TAG, "isServicesOk: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());
        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: google play services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicessOk: an error occured but we fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getContext(), "you cant make map requests", Toast.LENGTH_SHORT).show();

        }
        return false;
    }


    public void initToolbar() {
       // mToolbar = (Toolbar) mView.findViewById(R.id.location_frag_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        mToolbar.setTitle("Property Location");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void contentInitiation() {

        //Initiation of MapView
        mMapView = mView.findViewById(R.id.add_map_view);

        mMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        // Edit Text
        mCity = mView.findViewById(R.id.add_city_et);
        mDistrict = mView.findViewById(R.id.add_district_et);
        mStreet = mView.findViewById(R.id.add_street_et);
        mLocationArea = mView.findViewById(R.id.add_locatin_area_et);
        mHomeNumber = mView.findViewById(R.id.add_house_number_et);
        mAddress = mView.findViewById(R.id.add_address_et);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.next_menu_item:
                String city=mCity.getText().toString().trim();
               String district= mDistrict.getText().toString().trim();
                String locationArea= mLocationArea.getText().toString().trim();
                String address = mAddress.getText().toString().trim();
                String street= mStreet.getText().toString().trim();
                String homeNumber=mHomeNumber.getText().toString().trim();
                Property propertyLocation=new Property(city,district,locationArea,address,street,homeNumber,mProperty);
                mInterFace.sendToAdditionalFrag(propertyLocation);

                ((AddProperty)getActivity()).selectFragment(2);

                break;
        }
        return true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mInterFace=(SendToAdditionalFrag)getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException("Error in retrieving data. Please try again");

        }
    }

    public void getDataFromMainFrag(Property property) {
            mProperty= property;

    }
    interface SendToAdditionalFrag{
        void sendToAdditionalFrag(Property property);
    }

}
