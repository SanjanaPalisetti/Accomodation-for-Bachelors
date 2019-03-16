package com.example.Bachelors;

import android.content.Intent;
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

public class SignupActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextPassword2;
    Button buttonAdd, buttonOU;
    Spinner sp;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);


        buttonAdd = (Button) findViewById(R.id.buttonAddBachelor);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
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

                String name = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String password2 = editTextPassword2.getText().toString().trim();
                String genre = sp.getSelectedItem().toString();

                if(!((TextUtils.isEmpty(name))||(TextUtils.isEmpty(password)))){

                    if((password).equals(password2)) {

                        if((genre).equals("Tenant")) {
                            Toast.makeText(SignupActivity.this, "Tenant added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(v.getContext(), RegistrationTenant.class));
                        }

                        else {
                            Toast.makeText(SignupActivity.this, "Owner added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(v.getContext(), ownerReg.class));
                        }


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
    }
