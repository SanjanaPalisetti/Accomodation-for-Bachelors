package com.example.Bachelors;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

public class Wallet extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String CHANNEL_ID = "Bach";
    private static final String CHANNEL_NAME = "Bach";
    private static final String CHANNEL_DESC = "Bach";
    CardView balance, payment, transactions, scratch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
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

        balance = findViewById(R.id.balance_card);
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent bal = new Intent(Wallet.this, Balance.class);
                startActivity(bal);

            }
        });

        scratch = findViewById(R.id.scratch_card);
        scratch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent scr = new Intent(Wallet.this, ScratchCrad.class);
                startActivity(scr);

            }
        });

        payment = findViewById(R.id.payment_card);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pay = new Intent(Wallet.this, Payment.class);
                startActivity(pay);

            }
        });

        transactions = findViewById(R.id.transactions_card);
        transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tra = new Intent(Wallet.this, TransactionHistory.class);
                startActivity(tra);

            }
        });
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
        getMenuInflater().inflate(R.menu.wallet, menu);
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

        switch (id) {

            case R.id.nav_add_property:
                Intent a = new Intent(Wallet.this,AddProperty.class);
                startActivity(a);
                break;
            case R.id.nav_chat:
                Intent c = new Intent(Wallet.this,UsersActivity.class);
                startActivity(c);
                break;
            case R.id.nav_dashboard:
                Intent d = new Intent(Wallet.this, Dashboard_common.class);
                startActivity(d);
                break;
            case R.id.nav_notifications:
                //Intent n = new Intent(Dashboard_common.this,Notification.class);
                //startActivity(n);
                displayNotif();
                break;
            case R.id.nav_profile:
                Intent p = new Intent(this, UserProfileEdit.class);
                startActivity(p);
                break;
            case R.id.nav_sign_out:
                Intent l = new Intent(this, MainActivity.class);
                startActivity(l);
                break;
            case R.id.nav_suggestions:
                Intent sug = new Intent(this,Review.class);
                startActivity(sug);
                break;
            case R.id.nav_wallet :
                Intent w = new Intent(Wallet.this,Wallet.class);
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
