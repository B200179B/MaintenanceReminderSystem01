package com.example.maintenanceremindersystem01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private TextView registerInSignIn;
    private EditText etEmail, etPassword;
    private Button signInButton;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        registerInSignIn = (TextView) findViewById(R.id.registerInSignIn);
        registerInSignIn.setOnClickListener(this);

        signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);

        etEmail = (EditText) findViewById(R.id.loginEmail);
        etPassword = (EditText) findViewById(R.id.loginPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerInSignIn:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;

            case R.id.signInButton:
                loginUser();
                break;
        }
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(email.isEmpty()){
            etEmail.setError("Email is required !");
            etEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Pls enter valid email !");
            etEmail.requestFocus();
            return;
        }

        if(password.length() < 6){
            etPassword.setError("Min password length should be 6 characters !");
            etPassword.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etPassword.setError("Password is required !");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //redirect to user profile
                    startActivity(new Intent(LoginActivity.this, homePage.class));
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(LoginActivity.this, "Failed to login! Pls make sure your email or password is correct !", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}