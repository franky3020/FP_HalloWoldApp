package com.example.my_first_application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    EditText mEmail;
    EditText mPassword;
    EditText mCPassword;
    Button mSignUpBtn;

    //progressbar to display while registering user
    ProgressDialog progressDialog;

    //declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sign_up);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mEmail = findViewById(R.id.editText_sign_up_email);
        mPassword = findViewById(R.id.editText_sign_up_password);
        mCPassword = findViewById(R.id.editText_sign_up_password_again);
        mSignUpBtn = findViewById(R.id.button_sign_up);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User");

        //In the onCreate() method, initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mEmail.getText().toString().trim();
                String pwd = mPassword.getText().toString().trim();
                String cPwd = mCPassword.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    mEmail.setError("Invalid Email");
                    mEmail.setFocusable(true);
                } else if (pwd.length() < 8) {
                    mPassword.setError("Password length at least 8 characters");
                    mPassword.setFocusable(true);
                } else if (!cPwd.equals(pwd)) {
                    mCPassword.setError("Password not equal");
                    mCPassword.setFocusable(true);
                }else {
                    registerUser(mail, pwd);
                }
            }
        });
    }

    private void registerUser(String mail, String pwd) {
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(mail, mail)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, dismiss dialog and start register
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, "Registered...\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, HomePageActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //error, dismiss progress dialog and get and show the error message
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
