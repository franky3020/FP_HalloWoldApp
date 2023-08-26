package com.example.over_task;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import User.User;
import User.UserBuilder;
import User.UserAPIService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();

    EditText email;
    EditText mNickName;

    EditText password;
    EditText cPassword;
    Button signUpBtn;

    //progressbar to display while registering user
    ProgressDialog progressDialog;

    //declare an instance of FirebaseAuth
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sign_up);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        email = findViewById(R.id.editText_sign_up_email);
        mNickName = findViewById(R.id.editText_nick_name);

        password = findViewById(R.id.editText_sign_up_password);
        cPassword = findViewById(R.id.editText_sign_up_password_again);
        signUpBtn = findViewById(R.id.button_sign_up);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User");

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                String nickName = mNickName.getText().toString().trim();

                String pwd = password.getText().toString().trim();
                String cPwd = cPassword.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    email.setError("Invalid Email");
                    email.setFocusable(true);
                } else if (nickName.length() > 12){
                    mNickName.setError("nickName length over 12");
                    mNickName.setFocusable(true);
                } else if (pwd.length() < 8) {
                    password.setError("Password length at least 8 characters");
                    password.setFocusable(true);
                } else if (!cPwd.equals(pwd)) {
                    cPassword.setError("Password not equal");
                    cPassword.setFocusable(true);
                } else {
                    registerUser(mail, pwd, nickName);
                }
            }
        });

        LinearLayout login_linear_layout = findViewById(R.id.login_linear_layout);
        login_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLoginActivity();
            }
        });

    }

    private void toLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
    }


    // Todo 應該用介面解偶
    private void registerUser(String mail, String pwd, final String nickName) {
        progressDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // Todo 呼叫順序要改
    private void createUserOnDB(String firebaseUID, String nickName) {
        User user = UserBuilder.anUser(0)
                .withFirebaseUID(firebaseUID)
                .withName(nickName)
                .build();

        UserAPIService userAPIService = new UserAPIService();
        try {
            userAPIService.createUser(user, new Callback(){

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    progressDialog.dismiss();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Fail on create user in db.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d(LOG_TAG, e.getMessage());
            progressDialog.dismiss();
            Toast.makeText(SignUpActivity.this, "Fail on create user in db.",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
