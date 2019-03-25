package com.example.Bachelors;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

public class UserProfileEdit extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference databaseUsers;
    FirebaseAuth firebaseAuth;
    FirebaseUser mUser;
    UserReg userReg;

    private static final String CHANNEL_ID = "Bach";
    private static final String CHANNEL_NAME = "Bach";
    private static final String CHANNEL_DESC = "Bach";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final Button buttonEdit = (Button) findViewById(R.id.buttonEdit);
        final Button buttonSaveChanges = (Button) findViewById(R.id.buttonSaveChanges);
        final EditText username = (EditText) findViewById(R.id.editText_username);
        final EditText firstname = (EditText) findViewById(R.id.editText_first_name);
        final EditText lastname = (EditText) findViewById(R.id.editText_last_name);
        final EditText phoneno = (EditText) findViewById(R.id.editText_phone_no);
        firstname.setText(null);
        lastname.setText(null);
        phoneno.setText(null);
        username.setText(null);
        firebaseAuth = FirebaseAuth.getInstance();
        mUser = firebaseAuth.getCurrentUser();
        databaseUsers = FirebaseDatabase.getInstance().getReference("User");
        String id = mUser.getUid();

        // retrieve from database here
        databaseUsers.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                firstname.setText(dataSnapshot.child("name").getValue().toString());
                phoneno.setText(dataSnapshot.child("contanct").getValue().toString());
                username.setText(dataSnapshot.child("email").getValue().toString());
                lastname.setText(dataSnapshot.child("age").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(UserProfileEdit.this, "Cannot retrieve data", Toast.LENGTH_LONG).show();

            }
        });

//        String sometext = databaseUsers.child(id).child("name").getKey();
//        Toast.makeText(UserProfileEdit.this, sometext, Toast.LENGTH_SHORT).show();



        Toast.makeText(UserProfileEdit.this,"Loading details...",Toast.LENGTH_LONG).show();

        username.setInputType(0);
        phoneno.setInputType(0);
        firstname.setInputType(0);
        lastname.setInputType(0);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    username.setInputType(0);
                    username.setEnabled(false);
                    phoneno.setInputType(1);
                    firstname.setInputType(1);
                    lastname.setInputType(1);
                    buttonSaveChanges.setVisibility(View.VISIBLE);
                    buttonEdit.setVisibility(View.INVISIBLE);

    }});

        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernameString = username.getText().toString().trim();
                String firstnameString = firstname.getText().toString().trim();
                String lastnameString = lastname.getText().toString().trim();
                String phonenoString = phoneno.getText().toString().trim();

                if(!(TextUtils.isEmpty(phonenoString)||TextUtils.isEmpty(lastnameString)||TextUtils.isEmpty(firstnameString))){

                    // check and update database
                    try {
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        String id = firebaseUser.getUid();
                        databaseUsers.child(id).child("name").setValue(firstnameString);
                        databaseUsers.child(id).child("contanct").setValue(phonenoString);
                        databaseUsers.child(id).child("age").setValue(lastnameString);
                        databaseUsers.child(id).child("email").setValue(usernameString);
                        Toast.makeText(UserProfileEdit.this,"Details updated", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(UserProfileEdit.this, "Unable to update", Toast.LENGTH_LONG).show();
                        //                    e.printStackTrace();
                    }


                    username.setInputType(0);
                    username.setEnabled(true);
                    phoneno.setInputType(0);
                    firstname.setInputType(0);
                    lastname.setInputType(0);
                    buttonSaveChanges.setVisibility(View.INVISIBLE);
                    buttonEdit.setVisibility(View.VISIBLE);

                }

//                else if(StringUtils.isCreatable(lastnameString))

                else {

                    Toast.makeText(UserProfileEdit.this, "Enter valid details", Toast.LENGTH_LONG).show();

                }

            }});

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id) {

            case R.id.nav_add_property :
                Intent a = new Intent(UserProfileEdit.this,AddProperty.class);
                startActivity(a);
                break;
            case R.id.nav_chat :
                Intent c = new Intent(this,UsersActivity.class);
                startActivity(c);
                break;
            case R.id.nav_dashboard :
                Intent d = new Intent(this,Dashboard_common.class);
                startActivity(d);
                break;
            case R.id.nav_notifications :
//                Intent a = new Intent(Dashboard_common.this,Dashboard_common.class);
//                startActivity(a);
                displayNotif();
                break;
            case R.id.nav_profile :
                Intent p = new Intent(UserProfileEdit.this, UserProfileEdit.class);
                startActivity(p);
                break;
            case R.id.nav_sign_out :
                Intent l = new Intent(this,MainActivity.class);
                startActivity(l);
                break;
            case R.id.nav_suggestions :
                Intent s = new Intent(UserProfileEdit.this,Review.class);
                startActivity(s);
                break;

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayNotif() {
        NotificationCompat.Builder notif_builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        notif_builder.setSmallIcon(R.drawable.ic_notification);
        notif_builder.setContentTitle("Bachelors: New notification");
        notif_builder.setContentText("New request for Property.");
        notif_builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, notif_builder.build());
    }
}
