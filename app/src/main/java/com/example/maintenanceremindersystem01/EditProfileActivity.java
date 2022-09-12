package com.example.maintenanceremindersystem01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button cancelUpdate, updateButton;

     EditText updateName, updateEmail, updatePassword;

    String FULLNAME, EMAIL, PASSWORD;

    DatabaseReference reference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());;

        updateName = findViewById(R.id.updateName);
        updateEmail = findViewById(R.id.updateEmail);
        updatePassword = findViewById(R.id.updatePassword);
        
        cancelUpdate = (Button) findViewById(R.id.cancelUpdate);
        cancelUpdate.setOnClickListener(this);

        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(this);

        showAllUserData();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("some") != null){
                Toast.makeText(getApplicationContext(), "" + bundle.getString("some"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showAllUserData() {

        Intent intent = getIntent();
        FULLNAME = intent.getStringExtra("name");
        EMAIL = intent.getStringExtra("email");
        PASSWORD = intent.getStringExtra("password");

        updateName.setText(FULLNAME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelUpdate:
                startActivity(new Intent(this, homePage.class));
                break;
        }
    }

    public void update(View view) {

        if(isNameChanged() ){
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Data is same and cannot be updated", Toast.LENGTH_LONG).show();
        }

    }



    private boolean isNameChanged() {

        if(!FULLNAME.equals(updateName.getText().toString())){

            reference.child(FULLNAME).child("fullName").setValue(updateName.getText().toString());
            return true;

        }else{
            return false;
        }
    }
}