package com.example.molkiyat.addProperty;


import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.molkiyat.MainActivity;
import com.example.molkiyat.Property;
import com.example.molkiyat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdditionalDetails extends Fragment {

    private static final int MAX_LENGTH = 100;


    final static int ACTIVITY_SELECT_IMAGE = 7;

    private FirebaseFirestore mCloudStore;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;


    private View mView;
    private Toolbar mToolbar;
    Context context;
    private Property mProperty;
    private ProgressBar mAddProgress;

    public AdditionalDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_additional_details, container, false);
        Button button = mView.findViewById(R.id.recive);
        intiliazition();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToFirebase();
            }
        });
        return mView;
    }

    private void intiliazition() {
        mAddProgress = mView.findViewById(R.id.add_prop_bar);

        mCloudStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        //   u_id = mAuth.getCurrentUser().getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();

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

    public void saveDataToFirebase() {
        final String price = mProperty.getPrice();
        final String propertyName = mProperty.getTitle();
        final String purpose = mProperty.getPurpose();
        final String propertyType = mProperty.getPropertyType();
        final String description = mProperty.getDescription();
        final String area = mProperty.getArea();
        final String rooms = mProperty.getRooms();
        final String bathrooms = mProperty.getBathrooms();
        final String floor = mProperty.getFloor();
        final Uri mImageUri = mProperty.getImageUrl();
        final String district = mProperty.getDistrict();
        final String locationArea = mProperty.getLocationArea();
        final String address = mProperty.getAddress();
        final String city = mProperty.getCity();
        final String homeNumber = mProperty.getHomeNumber();

        if (!TextUtils.isEmpty(price) && !TextUtils.isEmpty(propertyName) && mImageUri != null && !TextUtils.isEmpty(purpose) && !TextUtils.isEmpty(propertyType) && !TextUtils.isEmpty(area) && !TextUtils.isEmpty(rooms) && !TextUtils.isEmpty(bathrooms)) {
            mAddProgress.setVisibility(VISIBLE);
            String randomStr = random();
            final StorageReference imge_path = mStorageRef.child("propery_images").child(randomStr + ".jpg");

            imge_path.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        imge_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUri = uri;
                                String download_url = uri.toString();
                                Map<String, String> addproperty = new HashMap<>();
                                addproperty.put("address",address);
                                addproperty.put("city",city);
                                addproperty.put("homeNumber",homeNumber);
                                addproperty.put("district",district);
                                addproperty.put("locationArea",locationArea);
                                addproperty.put("title", propertyName);
                                addproperty.put("price", price);
                                addproperty.put("purpose", purpose);
                                addproperty.put("type", propertyType);
                                addproperty.put("description", description);
                                addproperty.put("image", download_url);
                                addproperty.put("rooms", rooms);
                                addproperty.put("bathrooms", bathrooms);
                                addproperty.put("floor", floor);

                                mCloudStore.collection("Property").add(addproperty).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                        if (task.isSuccessful()) {

                                            Toast.makeText(getContext(), "data added", Toast.LENGTH_SHORT).show();
                                            mAddProgress.setVisibility(INVISIBLE);
                                            Intent mainIntent = new Intent(getContext(), MainActivity.class);
                                            startActivity(mainIntent);

                                        }
                                    }
                                });
                                Toast.makeText(getContext(), "photo uploded ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        mAddProgress.setVisibility(INVISIBLE);
                        String error = task.getException().getMessage();
                        Toast.makeText(getContext(), "not uplode becouse " + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(getContext(), "values are required", Toast.LENGTH_SHORT).show();
        }


    }



    public void initToolbar() {
//        mToolbar = (Toolbar) mView.findViewById(R.id.additional_frag_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        mToolbar.setTitle("Additional Details");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
        return true;
    }

    public void getDataFromLocationFrag(Property property) {
        mProperty = property;
    }
}
