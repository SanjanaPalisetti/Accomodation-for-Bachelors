package com.example.Bachelors;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
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
    DatabaseReference Ref_tenants, Ref_User, Ref_review, Ref_property, PostsRef;
    String uname, tname;
    int flag = 0;

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
                upload();
                int check = upload();
                if (((TextUtils.isEmpty(name)) || (TextUtils.isEmpty(property)) || (TextUtils.isEmpty(address)))) {
                    Toast.makeText(Review.this, "Fill in all the required details", Toast.LENGTH_LONG).show();
                    //check=0;
                } else if (check == 1) {
                    Toast.makeText(Review.this, "Thank you for your feedback", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Review.this, Dashboard_common.class));
                } else if(check==0)
                    Toast.makeText(Review.this, "Please attempt to review a property you have actally lived in!", Toast.LENGTH_LONG).show();

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
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            Ref_tenants = database.getReference("Property/tenants");
            Log.d("uid", mAuth.getUid());
            Ref_User = database.getReference("User").child(mUser.getUid()).child("name");
            Ref_User.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    uname = dataSnapshot.getValue().toString();
                    Log.d("DATASNAPSHOT", uname);

                    Ref_tenants.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            String value = dataSnapshot.getValue().toString();
                            Log.d("VALUE", value);
                            Log.d("TNAME",uname);
                            //database.getReference("User").child(mUser.getUid()).child('name');
                            if (value.equalsIgnoreCase(uname)) {
                                flag = 1;
                                Log.d("asdfl","fdgkj");
                                //add review option here

                            }


                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //Ref_review = database.getReference("Reviews");
            String revname = Details.getName();



            if (flag == 0) {
                Toast.makeText(Review.this, "Please attempt to review a property you have actally lived in!", Toast.LENGTH_LONG).show();

            } else {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Reviews");
                databaseReference.push().setValue(Details);
                //Toast.makeText(Review.this, "Thank you for your feedback", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(Review.this, Dashboard_common.class));

            }


            //FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            //DatabaseReference databaseReference = firebaseDatabase.getReference("Reviews");
            //DatabaseReference databaseReference = firebaseDatabase.getReference().child("Reviews");
            //databaseReference.child(mUser.getUid()).push().setValue(Details);
            //databaseReference.push().setValue(Details);

            return flag;
        } catch (NullPointerException e) {

            return 0;

        }

    }
}


//7338070913