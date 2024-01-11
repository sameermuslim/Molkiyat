package com.example.molkiyat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button mLoginBtn, mSinupBtn;
    EditText mEmail, mPassword;
    ProgressBar mLoginProgerss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginBtn = findViewById(R.id.login_btn);
        mSinupBtn = findViewById(R.id.new_account);
        mLoginProgerss = findViewById(R.id.login_pbar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Login button onclick query to Firebase Auth to chick user is in database
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initializing Email and password EditText
                mEmail = findViewById(R.id.email_tv);
                mPassword = findViewById(R.id.password_tv);
                //getting data of EditTexts
                String password = mPassword.getText().toString();
                String email = mEmail.getText().toString();
                //Valuethsion for EditText is not empty
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {
                   // showing progress bar
                    mLoginProgerss.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, goto next page
                                        mLoginProgerss.setVisibility(View.INVISIBLE);
                                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(mainIntent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        mLoginProgerss.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });


                } else {
                    // If EditText are empty, display a message to the user.
                    Toast.makeText(LoginActivity.this, "Enter Email and Password it's require ", Toast.LENGTH_SHORT).show();
                }
            }
        });

           mSinupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sinpuIntent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(sinpuIntent);
                finish();

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}
