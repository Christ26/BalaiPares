package com.app.balaipares;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    ProgressBar pBar;
    FirebaseAuth fAuth;

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() !=null){
            Intent intent = new Intent(LoginActivity.this,MainMenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void SignIn(View view) {
        pBar = findViewById(R.id.progress_bar);
        pBar.setVisibility(View.VISIBLE);
        username = findViewById(R.id.login_userNameEt);
        password = findViewById(R.id.login_passwordEt);
        fAuth = FirebaseAuth.getInstance();

        String userLogin = username.getText().toString();
        String passLogin = password.getText().toString();

        if (TextUtils.isEmpty(userLogin)) {
            username.setError("username is Required");
            pBar.setVisibility(View.GONE);
            return;
        } else if (TextUtils.isEmpty(passLogin)) {
            password.setError("Password is Required");
            pBar.setVisibility(View.GONE);
            return;
        } else if(!TextUtils.isEmpty(userLogin) && !TextUtils.isEmpty(passLogin)) {
            String emailLogin = userLogin+"@balaipares.com";
            fAuth.signInWithEmailAndPassword(emailLogin,passLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText( LoginActivity.this, "Welcome to Balai Pares Main Menu", Toast.LENGTH_SHORT).show();
                        pBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(),MainMenuActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        Toast.makeText( LoginActivity.this, "Logged In Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        pBar.setVisibility(View.GONE);

                    }

                }
            });

        }


    }

    public void registerhere(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void AdminLogin(View view) {
        Intent intent = new Intent(LoginActivity.this,AdminLogin.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       finishAffinity();
    }
}