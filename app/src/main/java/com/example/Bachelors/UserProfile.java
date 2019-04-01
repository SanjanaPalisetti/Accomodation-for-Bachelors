package com.example.Bachelors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;




public class UserProfile extends AppCompatActivity implements Serializable {


    FirebaseAuth mAuth;
    FirebaseUser mUser;
    EditText Name;
    EditText Contact;
    EditText Email_Id;
    EditText Address;
    EditText Age;
    Spinner spinner;
    Button addo;
    UserReg Details;



    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();

        mUser= mAuth.getCurrentUser();




        //Code for Gender
        spinner = (Spinner) findViewById(R.id.Gender);

        List<String> list = new ArrayList<>();
        list.add("Select your gender from below");
        list.add("Male");
        list.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String itemvalue = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Code for database
        Name = (EditText) findViewById(R.id.Name);
        Contact = (EditText) findViewById(R.id.Contact);
        Email_Id = (EditText) findViewById(R.id.Email_id);
        Age = (EditText) findViewById(R.id.Age);
        Address = (EditText) findViewById(R.id.Address);
        addo = (Button) findViewById(R.id.email_sign_in_button);

        addo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         int check = 1;

                    if(Name.getText().toString().isEmpty() && Contact.getText().toString().isEmpty() && Email_Id.getText().toString().isEmpty() && isValidMobile(Contact.getText().toString())){
                        Toast.makeText(UserProfile.this,"Enter details!",Toast.LENGTH_LONG).show();
                        check = 0;
                    }

                        else if(Name.getText().toString().isEmpty())
                        {
                            Name.setError( "First name is required!" );
                            check = 0;
                        }

                        else if(Contact.getText().toString().isEmpty())
                        {
                            Contact.setError( "Contact is required!" );
                            check = 0;
                        }

                        else if(Email_Id.getText().toString().isEmpty())
                        {
                            Email_Id.setError( "Email id is required!" );
                            check = 0;

                        }
                        if(isEmailValid(Email_Id.getText().toString()))
                        {
                            Email_Id.setError("Please enter the same email used to sign up!");
                            check = 0;
                        }

                        if(isValidMobile(Contact.getText().toString()))
                        {
                            Contact.setError("Invalid Contact Number");
                            check = 0;
                        }

                if(check==1) {
                    upload();
                    startActivity(new Intent(UserProfile.this, DP_Activity.class));
                }
                else
                    Toast.makeText(UserProfile.this,"error",Toast.LENGTH_LONG).show();
            }
        });

    }
    private boolean isValidMobile(String phone) {
        boolean check;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() != 10) {
                // if(phone.length() != 10) {
                return true;
                //contact.setError("Not Valid Number");
            } else {
                return false;
            }
        } else return true;
    }
    private boolean isEmailValid(String email) {
        if(email.equals(mUser.getEmail()))
            return false;
        else
            return true;
    }

    private void addUser() {
        String User_Name = Name.getText().toString().trim();
        String User_contanct = Contact.getText().toString().trim();
        String User_email = Email_Id.getText().toString().trim();
        String User_age = Age.getText().toString().trim();
        String User_address = Address.getText().toString().trim();
        String User_Sex = spinner.getSelectedItem().toString();

        /* String id = Userdata.push().getKey();

        tenantReg tenant = new tenantReg(User_Name, User_Contact, User_email, User_age, User_address, User_sex);

        Userdata.child(Name).setValue(User);*/

    }

    public int upload()
    {
        Details = new UserReg();

        try {


            Details.setName(Name.getText().toString());
            Details.setContanct(Contact.getText().toString());
            Details.setEmail(Email_Id.getText().toString());
            Details.setAddress(Address.getText().toString());
            Details.setAge(Age.getText().toString());
            Details.setSex(spinner.getSelectedItem().toString().trim());
            Details.setBalance(0);


            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("User");
            //databaseReference.setValue(Details);
            databaseReference.child(mUser.getUid()).setValue(Details);

            //databaseReference.child(mUser.getUid()).push().setValue(Details);
            //DatabaseReference reg = root.child("Reg");
            //reg.setValue(Details);
            return 1;

        }catch (NullPointerException e){
            return 0;

        }

    }}

