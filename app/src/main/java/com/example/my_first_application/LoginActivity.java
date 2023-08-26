package com.example.my_first_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
// import com.google.firebase.auth.FirebaseUser;

import User.User;
import User.GetLoginUser;
import User.UserAPIService;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        TextView loginTitle = findViewById(R.id.textView_login_title);
        loginTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainActivity();
            }
        });

        LinearLayout sign_up_linear_layout = findViewById(R.id.sign_up_linear_layout);
        sign_up_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSignUpActivity();
            }
        });

    }

    public void onClickToSingIn(View view) {
        EditText emailField = findViewById(R.id.editText_login_username);
        EditText passwordField = findViewById(R.id.editText_login_password);
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (email.isEmpty() || password.isEmpty()) { // 避免 firebaseAuth.signInWithEmailAndPassword 壞掉
            Toast.makeText(LoginActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // email, password 不能是空的字串
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            UserAPIService userAPIService = new UserAPIService();
                            userAPIService.getAUserByFirebaseUID(firebaseAuth.getUid(), new UserAPIService.UserListener() {
                                @Override
                                public void onResponseOK(User user) {
                                    GetLoginUser.registerUser(user); // 登記使用者已登入系統
                                    toShowTask();
                                }

                                @Override
                                public void onFailure() {
                                    Log.w(LOG_TAG, "userAPIService : failure");
                                    Toast.makeText(LoginActivity.this, "userAPIService failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(LOG_TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void toShowTask() {
        Intent intent = new Intent();
        intent.setClass(this, ShowTaskActivity.class);
        startActivity(intent);
    }

    private void toMainActivity() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    private void toSignUpActivity() {
        Intent intent = new Intent();
        intent.setClass(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}
