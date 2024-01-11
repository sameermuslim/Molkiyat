package com.example.molkiyat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.molkiyat.addProperty.AddProperty;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    List<Property> propertyList;

    private Button imagebtn;
    private FirebaseAuth fA;
    private Toolbar mMainToolbar;
    private FirebaseFirestore firebaseFirestore;
    private PropertyRecyclerAdapter mPropertyRecyclerAdapter;
    private ListView mListView;
    private RecyclerView propertyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        propertyList = new ArrayList<>();
        propertyListView = findViewById(R.id.recyclerId);

        mPropertyRecyclerAdapter = new PropertyRecyclerAdapter(propertyList);
        propertyListView.setLayoutManager(new GridLayoutManager(this, 2));
        propertyListView.setAdapter(mPropertyRecyclerAdapter);

        fA = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Property").addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    for (DocumentChange dec : queryDocumentSnapshots.getDocumentChanges()) {
                        if (dec.getType() == DocumentChange.Type.ADDED) {
                            String homeId = dec.getDocument().getId();
                            Property property = dec.getDocument().toObject(Property.class).withId(homeId);
                            propertyList.add(property);
                            mPropertyRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });


        mMainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mMainToolbar);
        getSupportActionBar().setTitle("Main Page");

//         propertyList.add(new Home(23000,"Mazar Choqhdak","sell",R.drawable.pice));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (user != null) {
            Toast.makeText(this, "Your Login", Toast.LENGTH_SHORT).show();
        } else {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }

    public void sinout() {
        fA.signOut();
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_property:
                Intent addpIntent = new Intent(this, AddProperty.class);
                startActivity(addpIntent);
                return true;
            case R.id.log_out_menu:
                sinout();
            case R.id.search_menu_item:
                Intent SearchpIntent = new Intent(this, SearchActivity.class);
                startActivity(SearchpIntent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void openSerchActiviy(View view) {


    }
}
