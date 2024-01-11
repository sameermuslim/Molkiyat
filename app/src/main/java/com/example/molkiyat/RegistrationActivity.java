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
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity extends AppCompatActivity {
    private EditText mEmaliTV, mConfirmTV, mPasswordTV;
    private Button mCreateAccountBtn, mLoginBtn;
    private FirebaseAuth mAuth;
    private ProgressBar mReg_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth=FirebaseAuth.getInstance();

        mCreateAccountBtn = findViewById(R.id.create_account_btn);
        mLoginBtn = findViewById(R.id.already_have_accont_btn);

        mEmaliTV = findViewById(R.id.reg_email_tv);
        mConfirmTV = findViewById(R.id.reg_confirm_tv);
        mPasswordTV = findViewById(R.id.reg_pass_tv);
        mReg_progress=findViewById(R.id.login_pbar);

        mCreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mEmaliTV.getText().toString();
                String password=mPasswordTV.getText().toString();
                String confirm=mConfirmTV.getText().toString();


                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)&& !TextUtils.isEmpty(confirm)){
                    if (password.equals(confirm)){
                        mReg_progress.setVisibility(view.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegistrationActivity.this, "User Created Successful", Toast.LENGTH_SHORT).show();
                                    mReg_progress.setVisibility(View.INVISIBLE);
                                    Intent mainIntent = new Intent(RegistrationActivity.this,MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            }
                        });

                    }else {
                        Toast.makeText(RegistrationActivity.this, "Confirm Password and Password Field doesn't Match", Toast.LENGTH_SHORT).show();

                    }

                }else {
                    Toast.makeText(RegistrationActivity.this, "Empty Field!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegistrationActivity.this,MainActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }


}
