package com.example.Bachelors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;
import android.support.v7.widget.AppCompatCheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends Activity {

    Button email_sign_in_button;
    EditText email;
    EditText password;
    String Email;
    String Password;
    FirebaseAuth firebaseAuth;
    FirebaseUser mUser;
    CheckBox checkBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_login);

        // initilaising firebase auth here
        firebaseAuth = FirebaseAuth.getInstance();
        mUser = firebaseAuth.getCurrentUser();
        //

        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        email_sign_in_button = findViewById(R.id.email_sign_in_button);
        checkBox = findViewById(R.id.checkbox);
        //edtPassword = (EditText) findViewById(R.id.password);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // show password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(Email ,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this,"Login success",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,Dashboard_common.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Not a valid client",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
    }
}



