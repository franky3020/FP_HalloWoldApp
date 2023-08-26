package com.example.my_first_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();

    EditText email;
    EditText mNickName;

    EditText password;
    EditText cPassword;
    Button signUpBtn;

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
    }

    // Todo 呼叫順序要改
    private void createUserOnDB(String firebaseUID, String nickName) {
    }

}
