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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Payment extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String CHANNEL_ID = "Bach";
    private static final String CHANNEL_NAME = "Bach";
    private static final String CHANNEL_DESC = "Bach";

    DatabaseReference databaseUsers,databaseTransactions;
    FirebaseAuth firebaseAuth;
    FirebaseUser mUser;
    Button transaction,navigate_back;
    EditText editText_username, editText_amount;
    String required_id,input_username;
    int input_amount;
    int check = 0;
    int balance_sender,balance_receiver;
    int lalala = 0;
    int lalala2 = 0;
    int lalala3 = 0;
    Transactions sender = new Transactions();
    Transactions receiver = new Transactions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        mUser = firebaseAuth.getCurrentUser();
        databaseUsers = FirebaseDatabase.getInstance().getReference("User");
        databaseTransactions = FirebaseDatabase.getInstance().getReference("Transactions");
        final String id = mUser.getUid();
        transaction = findViewById(R.id.button_pay_user);
        navigate_back = findViewById(R.id.button_check_balance);
        editText_amount = findViewById(R.id.editText_amount_to_pay);
        editText_username = findViewById(R.id.editText_user_email);
        required_id = "none";


        String dt = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        sender.setDate(dt);
        receiver.setDate(dt);

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

        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_username = editText_username.getText().toString().trim();
                if(editText_amount.getText().toString().trim().isEmpty()||editText_username.getText().toString().trim().isEmpty())
                    Toast.makeText(Payment.this,"Enter valid details",Toast.LENGTH_LONG).show();
                else {
                    input_amount = Integer.parseInt(editText_amount.getText().toString().trim());
                    databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                if ((ds.child("email").getValue().toString().trim()).compareTo(input_username) == 0) {
                                    required_id = ds.getKey();
                                    check=1;
                                    lalala();
                                    break;
                                }
                            }

                            if(check==0)
                                Toast.makeText(Payment.this, "Enter valid username", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

        });

        databaseUsers.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                balance_sender = Integer.parseInt(dataSnapshot.child("balance").getValue().toString());
                lalala = 1;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Payment.this,"Unable to retrieve balance",Toast.LENGTH_LONG);

            }
        });
//

        navigate_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(Payment.this,Balance.class);
                startActivity(a);

            }
        });
    }

    public void lalala()
    {
        String id = mUser.getUid();
        {
            //retrieving balance of current user

            //setting new balance
            if(lalala==1) {

                if (input_amount > balance_sender)
                    Toast.makeText(getApplicationContext(), "Not sufficient Balance"+balance_sender, Toast.LENGTH_SHORT).show();

                else {
                    System.out.println("----------------------" + (balance_sender) );
                    balance_sender -= input_amount;
                    databaseUsers.child(id).child("balance").setValue(balance_sender);
                    lalala3 = 1;


                    //retrieving balance of recepient user
                    databaseUsers.child(required_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            balance_receiver = Integer.parseInt(dataSnapshot.child("balance").getValue().toString());
                            System.out.println("--------------"+balance_receiver+"---------000---------");
                            lalala2 = 1;


                            //setting new balance
                            if (lalala2 == 1 && lalala3 == 1) {
                                System.out.println("--------------"+balance_receiver+"------------------");
                                balance_receiver += input_amount;
                                databaseUsers.child(required_id).child("balance").setValue(balance_receiver);
                                lalala2 = 0;
                                lalala3 = 0;
                            }

                            Toast.makeText(Payment.this, "Transaction complete", Toast.LENGTH_LONG).show();
                            editText_amount.setText("");
                            editText_username.setText("");

                            receiver.setAmount(input_amount);
                            sender.setAmount(input_amount);
                            receiver.setName(mUser.getEmail());
                            sender.setName(input_username);
                            receiver.setDirection("received");
                            sender.setDirection("sent");
                            databaseTransactions.child(mUser.getUid()).push().setValue(sender);
                            databaseTransactions.child(required_id).push().setValue(receiver);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(Payment.this, "Unable to retrieve balance", Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        }
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
        getMenuInflater().inflate(R.menu.payment, menu);
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
