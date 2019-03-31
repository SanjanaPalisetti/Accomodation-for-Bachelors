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
import android.support.v7.widget.CardView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Balance extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String CHANNEL_ID = "Bach";
    private static final String CHANNEL_NAME = "Bach";
    private static final String CHANNEL_DESC = "Bach";

    Button add_money, pay;
    EditText editText_add_money;
    int input_money;
    int error = 0;
    DatabaseReference databaseUsers;
    FirebaseAuth firebaseAuth;
    FirebaseUser mUser;
    int balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        add_money = findViewById(R.id.add_money_button);
        pay = findViewById(R.id.pay_button);
        editText_add_money = findViewById(R.id.editText_enter_amount);
        final TextView display = findViewById(R.id.balance_display);
        firebaseAuth = FirebaseAuth.getInstance();
        mUser = firebaseAuth.getCurrentUser();
        databaseUsers = FirebaseDatabase.getInstance().getReference("User");
        final String id = mUser.getUid();


        // balance in card view
        databaseUsers.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                balance = Integer.parseInt(dataSnapshot.child("balance").getValue().toString());
                display.setText(dataSnapshot.child("balance").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Balance.this, "Cannot retrieve balance", Toast.LENGTH_LONG).show();

            }
        });


        // add money button
        add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openDialogue();

                //getting amount entered
                input_money = Integer.parseInt(editText_add_money.getText().toString().trim());

                //changing value
                balance += input_money;
                System.out.println("--------------------"+balance+"----------"+input_money);
                databaseUsers.child(id).child("balance").setValue(balance);
                display.setText("Rs. "+(balance));
                editText_add_money.setText("");
            }
        });

        //payment navigation
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(Balance.this,Payment.class);
                startActivity(a);

            }
        });
    }

//    public void openDialogue(){
//
//        VerificationDialogue verificationDialogue = new VerificationDialogue();
//        verificationDialogue.show(getSupportFragmentManager(), "Example dialogue");
//
//    }
//
//    @Override
//    public void applyTexts(String money){
//
//        final TextView display = findViewById(R.id.balance_display);
//        firebaseAuth = FirebaseAuth.getInstance();
//        mUser = firebaseAuth.getCurrentUser();
//        databaseUsers = FirebaseDatabase.getInstance().getReference("User");
//        final String id = mUser.getUid();
//
//        input_money = Integer.parseInt(money);
//        char symbol = money.charAt(0);
//        if((Character.toString(symbol).compareTo("-"))==0)
//            error = 1;
//
//    }

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
        getMenuInflater().inflate(R.menu.balance, menu);
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
                Intent a = new Intent(this,AddProperty.class);
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
                Intent p = new Intent(this, UserProfileEdit.class);
                startActivity(p);
                break;
            case R.id.nav_sign_out :
                Intent l = new Intent(this,MainActivity.class);
                startActivity(l);
                break;
            case R.id.nav_suggestions :
                Intent s = new Intent(this,Review.class);
                startActivity(s);
                break;
            case R.id.nav_wallet :
                Intent w = new Intent(this,Wallet.class);
                startActivity(w);
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
