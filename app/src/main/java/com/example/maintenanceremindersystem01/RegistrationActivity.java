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
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private Button registerButton;
    private TextView signInRegister;
    private EditText etFullName, etEmail, etPassword;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        registerButton = (Button) findViewById(R.id.signInButton);
        registerButton.setOnClickListener(this);

        signInRegister = (TextView) findViewById(R.id.signInRegister);
        signInRegister.setOnClickListener(this);

        etFullName = (EditText) findViewById(R.id.registerName);
        etEmail = (EditText) findViewById(R.id.loginEmail);
        etPassword = (EditText) findViewById(R.id.loginPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signInButton:
                registerUser();
                break;
            case R.id.signInRegister:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(fullName.isEmpty()){
            etFullName.setError("Full Name is required !");
            etFullName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            etEmail.setError("Email is required !");
            etEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Pls insert valid email !");
            etEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etPassword.setError("Password is required !");
            etPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            etPassword.setError("Min password length should be 6 characters !");
            etPassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(fullName, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegistrationActivity.this, "User has been registered successful !", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                //redirect to login layout!
                                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                            }else{
                                                Toast.makeText(RegistrationActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        }else{
                            Toast.makeText(RegistrationActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}