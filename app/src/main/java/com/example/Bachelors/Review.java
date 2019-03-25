package com.example.Bachelors;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.jar.Attributes;




public class Review extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    EditText xName;
    EditText xProperty;
    EditText xAddress;
    EditText xDuration;
    EditText xFeedback;
    RatingBar xRatingBar;
    Button xsubmit;
    review_get_set Details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        xName = (EditText) findViewById(R.id.Name);
        xProperty = (EditText) findViewById(R.id.Property);
        xAddress = (EditText) findViewById(R.id.Address);
        xDuration = (EditText) findViewById(R.id.Duration);
        xFeedback = (EditText) findViewById(R.id.Feedback);
        xsubmit = (Button) findViewById(R.id.submit);
        xRatingBar = (RatingBar) findViewById(R.id.ratingBar3);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        xsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int check = upload();
                String name = xName.getText().toString().trim();
                String property = xProperty.getText().toString().trim();
                String address = xAddress.getText().toString().trim();
                int check=upload();
                if(((TextUtils.isEmpty(name))||(TextUtils.isEmpty(property))||(TextUtils.isEmpty(address))))
                {
                    Toast.makeText(Review.this,"Fill in the details",Toast.LENGTH_LONG).show();
                    //check=0;
                }

                 else if(check ==1) {
                    Toast.makeText(Review.this, "Thank you for your feedback", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Review.this, Dashboard_common.class));
                }
                else
                    Toast.makeText(Review.this,"Error",Toast.LENGTH_LONG).show();
            }
        });



    }


//database code

    public int upload() {
        Details = new review_get_set();

        try {

            Details.setName(xName.getText().toString());
            Details.setProperty(xProperty.getText().toString());
            Details.setDuration(xDuration.getText().toString());
            Details.setAddress(xAddress.getText().toString());
            Details.setFeedback(xFeedback.getText().toString());
            Details.setRating(xRatingBar.getRating());

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("Reviews");
            //DatabaseReference databaseReference = firebaseDatabase.getReference().child("Reviews");
            //databaseReference.child(mUser.getUid()).push().setValue(Details);
            databaseReference.push().setValue(Details);

            return 1;

        } catch (NullPointerException e) {

            return 0;

        }

    }

}
//7338070913