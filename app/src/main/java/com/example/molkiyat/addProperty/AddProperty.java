package com.example.molkiyat.addProperty;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import android.widget.ToggleButton;

import com.example.molkiyat.Constant;
import com.example.molkiyat.MainActivity;
import com.example.molkiyat.Property;
import com.example.molkiyat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class AddProperty extends AppCompatActivity implements MainFragment.FromMainFragToActivity,LocationFragment.SendToAdditionalFrag{

    final static int ACTIVITY_SELECT_IMAGE = 7;
    private static final int MAX_LENGTH = 100;

    private FirebaseFirestore mCloudStore;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;

    private String u_id;
    private Button mAddBtn, mPrevBtn;
    private EditText mProName, mPrice;
    private ImageView mPropImage;
    private ProgressBar mAddProgress;
    private Uri mImageUri = null;
    private ToggleButton mCarToggle, mPropertyToggle;
    private EditText mPropertyTitle, mDescription, mArea, mRooms, mBathrooms, mFloor;

    private RadioButton mRent, mSale, mWanted;
    private Spinner mTitleSpn, mAreaSpn, mPriceSpn, mPropertyTypeSpn, provinceTypesSpn, maxSpn, minSpn, provinceNameSpn;


    Spinner propertyTypeSpn;
    ArrayAdapter<CharSequence> proprtyTypeAdapter, provinceTypeAdapter, maxAdapter, provinceNameAdapter, minAdapter;


  private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        //from fragment
        mAreaSpn = findViewById(R.id.add_area_spn);
        mPriceSpn = findViewById(R.id.add_price_spn);
        mPropertyTypeSpn = findViewById(R.id.p_type_spn);
        //radio buttons
        mRent = findViewById(R.id.rent_radio_button);
        mSale = findViewById(R.id.sale_radio_button);
        mWanted = findViewById(R.id.wanted_radio_button);

        // Edit Text
        mPropertyTitle = findViewById(R.id.property_title_et);
        mPrice = findViewById(R.id.add_price_et);
        mDescription = findViewById(R.id.add_description_ed);
        mArea = findViewById(R.id.add_locatin_area_et);
        mRooms = findViewById(R.id.add_rooms_et);
        mBathrooms = findViewById(R.id.add_bathroome_et);
        mFloor = findViewById(R.id.add_floor_et);
        mAddProgress = findViewById(R.id.add_prop_bar);
        mPropImage = findViewById(R.id.prop_image);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mToolbar = findViewById(R.id.add_pro_toolbar);
        mToolbar.setTitle("");

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //      mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setCurrentItem(1, true);

//        mViewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });
        //    mViewPager.setAdapter(mSectionsPagerAdapter);

         mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.next_per_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_prop_bar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void fromMainFragToActivity(Property property) {
        String tag = "android:switcher:" + R.id.container + ":" + 1;
        LocationFragment   locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentByTag(tag);
        locationFragment.getDataFromMainFrag(property);
    }

    @Override
    public void sendToAdditionalFrag(Property property) {
        String tag = "android:switcher:" + R.id.container + ":" + 2;
        AdditionalDetails   additionalDetails = (AdditionalDetails) getSupportFragmentManager().findFragmentByTag(tag);
        additionalDetails.getDataFromLocationFrag(property);

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new MainFragment();
                    break;

                case 1:
                    fragment = new LocationFragment();
                    break;

                default:
                    fragment = new AdditionalDetails();

            }
            return fragment;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
    public void selectFragment(int position) {

        mViewPager.setCurrentItem(position, true);

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
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        }else {
            selectFragment(mViewPager.getCurrentItem()-1);
        }
    }
    public void saveDataToFirebase() {
        final String price = mPrice.getText().toString();
        final String propertyName = mPropertyTitle.getText().toString();
        final String purpose = checkedRadioValue();
        final String propertyType = mPropertyTypeSpn.getSelectedItem().toString();
        final String description = mDescription.getText().toString();
        final String area = mArea.getText().toString();
        final String rooms = mRooms.getText().toString();
        final String bathrooms = mBathrooms.getText().toString();
        final String floor = mFloor.getText().toString();

        Toast.makeText(AddProperty.this, propertyType, Toast.LENGTH_SHORT).show();


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

                                            Toast.makeText(AddProperty.this.getApplicationContext(), "data added", Toast.LENGTH_SHORT).show();
                                            mAddProgress.setVisibility(INVISIBLE);
                                            Intent mainIntent = new Intent(AddProperty.this, MainActivity.class);
                                            startActivity(mainIntent);

                                        }
                                    }
                                });
                                Toast.makeText(AddProperty.this.getApplicationContext(), "photo uploded ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        mAddProgress.setVisibility(INVISIBLE);
                        String error = task.getException().getMessage();
                        Toast.makeText(AddProperty.this, "not uplode becouse " + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(AddProperty.this, "values are required", Toast.LENGTH_SHORT).show();
        }


    }
}


//        mCloudStore = FirebaseFirestore.getInstance();
//        mAuth = FirebaseAuth.getInstance();
//        u_id = mAuth.getCurrentUser().getUid();
//        mStorageRef = FirebaseStorage.getInstance().getReference();
//
//        mAddProgress = findViewById(R.id.add_prop_bar);
//        mAddBtn = findViewById(R.id.add_btn);
//        mPrice = findViewById(R.id.price_et);
//        mProName = findViewById(R.id.property_name_et);
//        mPropImage = findViewById(R.id.prop_image);
//
//        //open image picker in onClick of image view
//        mPropImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (ContextCompat.checkSelfPermission(AddProperty.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(AddProperty.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//                        performFileSearch();
//                    }
//                }
//
//            }
//        });
//
//
//        mAddBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final String price = mPrice.getText().toString();
//                final String propertyName = mProName.getText().toString();
//
//                if (!TextUtils.isEmpty(price) && !TextUtils.isEmpty(propertyName) && mImageUri != null) {
//                    mAddProgress.setVisibility(VISIBLE);
//                    String randomStr = random();
//                    StorageReference imge_path = mStorageRef.child("propery_images").child(randomStr + ".jpg");
//
//                    imge_path.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                            if (task.isSuccessful()) {
//                                Map<String, String> addproperty = new HashMap<>();
//                                addproperty.put("name", propertyName);
//                                addproperty.put("price", price);
//
//                                mCloudStore.collection("Property").add(addproperty).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                                        if (task.isSuccessful()) {
//
//                                            Toast.makeText(AddProperty.this, "data added", Toast.LENGTH_SHORT).show();
//                                            mAddProgress.setVisibility(INVISIBLE);
//                                        }
//                                    }
//                                });
//                                Toast.makeText(AddProperty.this, "photo uploded ", Toast.LENGTH_SHORT).show();
//                            } else {
//                                mAddProgress.setVisibility(INVISIBLE);
//                                String error = task.getException().getMessage();
//                                Toast.makeText(AddProperty.this, "not uplode becouse " + error, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//                }
//
//
//            }
//        });
//    }
//
//    public void performFileSearch() {
//
//        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
//        // browser.
//        Intent i = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        startActivityForResult(Intent.createChooser(i, "Select Picture"), ACTIVITY_SELECT_IMAGE);
//    }
//
//    public static String random() {
//        Random generator = new Random();
//        StringBuilder randomStringBuilder = new StringBuilder();
//        int randomLength = generator.nextInt(MAX_LENGTH);
//        char tempChar;
//        for (int i = 0; i < randomLength; i++) {
//            tempChar = (char) (generator.nextInt(96) + 32);
//            randomStringBuilder.append(tempChar);
//        }
//        return randomStringBuilder.toString();
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK && null != data) {
//            mImageUri = data.getData();
//            mPropImage.setImageURI(mImageUri);
//        }
//
//    }


//}
