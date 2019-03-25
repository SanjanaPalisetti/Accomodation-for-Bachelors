package com.example.Bachelors;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class SignupActivity extends AppCompatActivity implements Serializable {

    EditText editTextusername;
    EditText editTextPassword;
    EditText editTextPassword2;
    Button buttonAdd, buttonOU;
    Spinner sp;
    String s;
    UserDetailsApp details;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);


        buttonAdd = (Button) findViewById(R.id.buttonAddBachelor);
        editTextusername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPassword2 = (EditText) findViewById(R.id.editTextPassword2);
        sp = (Spinner) findViewById(R.id.spinnerGenres);


        ///////////////////////////////////////////////


        final String[] spstr = getResources().getStringArray(R.array.type);
        sp = findViewById(R.id.spinnerGenres);
        final ArrayAdapter<String> ar = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spstr);
        sp.setAdapter(ar);
//        super.onCreate(savedInstanceState);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   s = sp.getText().toString();
                s = ((TextView) view).getText().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );



        buttonAdd =(Button)findViewById(R.id.buttonAddBachelor);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editTextusername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String password2 = editTextPassword2.getText().toString().trim();
                String genre = sp.getSelectedItem().toString();

                if(!((TextUtils.isEmpty(name))||(TextUtils.isEmpty(password)))){

                    if((password).equals(password2)) {
                        register();


//                        if((genre).equals("Tenant")) {
////                            Toast.makeText(SignupActivity.this, "Tenant added", Toast.LENGTH_SHORT).show();
////                            startActivity(new Intent(v.getContext(), RegistrationTenant.class));
//                        }
//
//                        else {
////                            Toast.makeText(SignupActivity.this, "Owner added", Toast.LENGTH_SHORT).show();
////                            startActivity(new Intent(v.getContext(), ownerReg.class));
//                        }


                    }

                    else{
                        Toast.makeText(SignupActivity.this,"Passwords do not match!",Toast.LENGTH_LONG).show();
                    }
                }

                else{
                    Toast.makeText(SignupActivity.this,"Enter details",Toast.LENGTH_LONG).show();
                }


                //if (s.equals("Tenant")) {
                //    startActivity(new Intent(v.getContext(), RegistrationTenant.class));
                //} else if (s.equals("Owner")) {
                //    startActivity(new Intent(v.getContext(), ownerReg.class));
                //}
            }
        });

        buttonOU =(Button)findViewById(R.id.buttonLogin);
        buttonOU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), LoginActivity.class));
            }
        });
    }

    public  void  register()
    {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(editTextusername.getText().toString().trim(),editTextPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                { mUser = mAuth.getCurrentUser();
                    int check= upload_details();
                    if(check == 1) {
                        Intent intent = new Intent(SignupActivity.this,UserProfile.class);
//                        intent.putExtra("type", (Serializable) details);
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(SignupActivity.this,"Error",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public  int upload_details()
    {
        details = new UserDetailsApp();
        try {
            String temp = editTextusername.getText().toString();

            details.setUsername(temp);
            String sr = sp.getSelectedItem().toString().trim();
            details.setType(sr);
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("User");



            //DatabaseReference root = databaseReference.child("user");
            databaseReference.child(mUser.getUid()).push().setValue(details);

            return 1;
        }catch (NullPointerException e){
            return 0;
        }

    }}

