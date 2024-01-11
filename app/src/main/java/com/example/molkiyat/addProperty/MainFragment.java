package com.example.molkiyat.addProperty;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.molkiyat.Constant;
import com.example.molkiyat.MainActivity;
import com.example.molkiyat.Property;
import com.example.molkiyat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    final static int ACTIVITY_SELECT_IMAGE = 7;
    private static final int MAX_LENGTH = 100;


    FromMainFragToActivity mInterFace;
    ArrayAdapter<CharSequence> proprtyTypeAdapter, provinceTypeAdapter, maxAdapter, provinceNameAdapter, minAdapter;

    private View mView;
    private Toolbar mToolbar;
    private FirebaseFirestore mCloudStore;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;

    private String u_id;
    private Button selectimage;
    private EditText mPropertyTitle, mPrice, mDescription, mArea, mRooms, mBathrooms, mFloor;
    private ImageView mPropImage;
    private ProgressBar mAddProgress;
    private Uri mImageUri = null;
    private RadioButton mRent, mSale, mWanted;
    private Spinner mTitleSpn, mAreaSpn, mPriceSpn, mPropertyTypeSpn, provinceTypesSpn, maxSpn, minSpn, provinceNameSpn;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_main, container, false);

        // filling spinner
        mAreaSpn = mView.findViewById(R.id.add_area_spn);

        mPriceSpn = mView.findViewById(R.id.add_price_spn);


        mPropertyTypeSpn = mView.findViewById(R.id.p_type_spn);
        proprtyTypeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.property_type, android.R.layout.simple_spinner_item);
        proprtyTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mPropertyTypeSpn.setAdapter(proprtyTypeAdapter);

        //radio buttons
        mRent = mView.findViewById(R.id.rent_radio_button);
        mSale = mView.findViewById(R.id.sale_radio_button);
        mWanted = mView.findViewById(R.id.wanted_radio_button);

        // Edit Text
        mPropertyTitle = mView.findViewById(R.id.property_title_et);
        mPrice = mView.findViewById(R.id.add_price_et);
        mDescription = mView.findViewById(R.id.add_description_ed);
        mArea = mView.findViewById(R.id.add_locatin_area_et);
        mRooms = mView.findViewById(R.id.add_rooms_et);
        mBathrooms = mView.findViewById(R.id.add_bathroome_et);
        mFloor = mView.findViewById(R.id.add_floor_et);


        mCloudStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        //   u_id = mAuth.getCurrentUser().getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        mPropImage = mView.findViewById(R.id.prop_image);

        //open image picker in onClick of image view
        mPropImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setMultiTouchEnabled(true)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(getActivity(), MainFragment.this);


            }
        });


//                ((AddProperty)getActivity()).selectFragment(1)
//                final String price = mPrice.getText().toString();
//                final String propertyName = mPropertyTitle.getText().toString();
//                final String purpose = checkedRadioValue();
//                final String propertyType = mPropertyTypeSpn.getSelectedItem().toString();
//                final String description= mDescription.getText().toString();
//                final String area=mArea.getText().toString();
//                final String rooms=mRooms.getText().toString();
//                final String bathrooms=mBathrooms.getText().toString();
//                final String floor=mFloor.getText().toString();
//
//                Toast.makeText(getContext(), propertyType, Toast.LENGTH_SHORT).show();
//
//
//                if (!TextUtils.isEmpty(price) && !TextUtils.isEmpty(propertyName) && mImageUri != null && !TextUtils.isEmpty(purpose) && !TextUtils.isEmpty(propertyType)&& !TextUtils.isEmpty(area)&& !TextUtils.isEmpty(rooms)&& !TextUtils.isEmpty(bathrooms)) {
//                    mAddProgress.setVisibility(VISIBLE);
//                    String randomStr = random();
//                    final StorageReference imge_path = mStorageRef.child("propery_images").child(randomStr + ".jpg");
//
//                    imge_path.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
//                            if (task.isSuccessful()) {
//                                imge_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        Uri downloadUri = uri;
//                                        String download_url = uri.toString();
//                                        Map<String, String> addproperty = new HashMap<>();
//                                        addproperty.put("title", propertyName);
//                                        addproperty.put("price", price);
//                                        addproperty.put("purpose", purpose);
//                                        addproperty.put("type", propertyType);
//                                        addproperty.put("description",description);
//                                        addproperty.put("image",download_url);
//                                        addproperty.put("rooms",rooms);
//                                        addproperty.put("bathrooms",bathrooms);
//                                        addproperty.put("floor",floor);
//
//
//
//
//                                        mCloudStore.collection("Property").add(addproperty).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                                                if (task.isSuccessful()) {
//
//                                                    Toast.makeText(getActivity().getApplicationContext(), "data added", Toast.LENGTH_SHORT).show();
//                                                    mAddProgress.setVisibility(INVISIBLE);
//                                                    Intent mainIntent = new Intent(getContext(), MainActivity.class);
//                                                    startActivity(mainIntent);
//
//                                                }
//                                            }
//                                        });
//                                        Toast.makeText(getActivity().getApplicationContext(), "photo uploded ", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            } else {
//                                mAddProgress.setVisibility(INVISIBLE);
//                                String error = task.getException().getMessage();
//                                Toast.makeText(getActivity().getApplicationContext(), "not uplode becouse " + error, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//                } else {
//                    Toast.makeText(getContext(), "values are required", Toast.LENGTH_SHORT).show();
//                }
//
//

        return mView;
    }




    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                mPropImage.setImageURI(mImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else {
            Toast.makeText(getContext(), "noo", Toast.LENGTH_SHORT).show();
        }

    }


    public String checkedRadioValue() {

        if (mRent.isChecked()) {
            return "rent";
        } else if (mSale.isChecked()) {
            return "sale";
        } else if (mWanted.isChecked()) {
            return "wanted";
        } else {
            return null;
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.next_menu_item:
                final String purpose = checkedRadioValue();
                final String propertyType = mPropertyTypeSpn.getSelectedItem().toString();
                final String propertyName = mPropertyTitle.getText().toString();
                final String description = mDescription.getText().toString();
                final String price = mPrice.getText().toString();
                final String area = mArea.getText().toString();
                final String rooms = mRooms.getText().toString();
                final String bathrooms = mBathrooms.getText().toString();
                final String floor = mFloor.getText().toString();

                Property  property= new Property(purpose,propertyType,propertyName,description,price,area,rooms,bathrooms,floor,mImageUri);

                mInterFace.fromMainFragToActivity(property);
                ((AddProperty) getActivity()).selectFragment(1);
                break;
        }
        return true;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    interface FromMainFragToActivity {
        void fromMainFragToActivity(Property property);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
        mInterFace =(FromMainFragToActivity)(getActivity());
        }catch (ClassCastException e){
            throw  new ClassCastException("Error in retrieving data. Please try again");
        }

     }
}

