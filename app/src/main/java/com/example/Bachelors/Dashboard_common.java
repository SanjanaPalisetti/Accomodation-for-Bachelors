package com.example.Bachelors;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Timer;
import java.util.TimerTask;

import in.myinnos.androidscratchcard.ScratchCard;


public class Dashboard_common extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;                    // new
    NavigationView navigationView;          // new
    Toolbar toolbar = null;                 // new
    private static final String CHANNEL_ID = "Bach";
    private static final String CHANNEL_NAME = "Bach";
    private static final String CHANNEL_DESC = "Bach";
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;

    Button  xcoupons;
    Button scratchCard;
    int currentPage = 0;
    int Num_pages = 3;
    ViewPager viewPager;
    MyCustomPageAdapter myCustomPageAdapter;
    Context context;
    //ClipData.Item item;
    // variable for storage reference
    StorageReference storageReference ;
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_common);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ////
    storageReference = FirebaseStorage.getInstance().getReference().child("Adds");
   myCustomPageAdapter = new MyCustomPageAdapter(Dashboard_common.this);
   viewPager = (ViewPager) findViewById(R.id.viewpage);
   viewPager.setAdapter(myCustomPageAdapter);
   imageView1 = (ImageView) findViewById(R.id.person_photo1);
   temp = "appartment1.jpg";
   fetch(temp,imageView1);
   imageView2 = (ImageView) findViewById(R.id.person_photo2);
         temp = "appartment2.jpg";
   fetch(temp,imageView2);
   imageView3 =(ImageView) findViewById(R.id.person_photo3);
         temp = "appartment3.jpg";
   fetch(temp,imageView3);
   imageView4=(ImageView) findViewById(R.id.person_photo4);
         temp = "appartment4.jpg";
   fetch(temp,imageView4);
   final Handler handler = new Handler();
   final Runnable Update = new Runnable() {
       @Override
       public void run() {
           if (currentPage == Num_pages) {
               currentPage = 0;
           }
           viewPager.setCurrentItem(currentPage++,true);
       }

   };
   Timer swipeTimer = new Timer();
   swipeTimer.schedule(new TimerTask() {
       @Override
       public void run() {

           handler.post(Update);
       }
   },800,4500);
//////////////////////////////////////

        xcoupons = findViewById(R.id.coupons);
        xcoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, new coupons(), "coupons");
                fragmentTransaction.commit();
            }
        });
///////////////////////////////////////
///////////////////////////////////////
        scratchCard = findViewById(R.id.Scratch_card);
        scratchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ScratchCrad.class));
            }
        });
///////////////////////////////////////
//        item function
///////////////////////////////////////



///////////////////////////////////////

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);                                   // changed
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);                              // changed
        navigationView.setNavigationItemSelectedListener(this);


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
        getMenuInflater().inflate(R.menu.dashboard_common, menu);
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
                Intent a = new Intent(Dashboard_common.this,AddProperty.class);
                startActivity(a);
                break;
            case R.id.nav_chat:
                Intent c = new Intent(Dashboard_common.this,UsersActivity.class);
                startActivity(c);
                break;
            case R.id.nav_dashboard:
                Intent d = new Intent(Dashboard_common.this, Dashboard_common.class);
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
                Intent w = new Intent(Dashboard_common.this,Wallet.class);
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
    public void fetch(String id , final ImageView view)
    {
        try{
            StorageReference mroot =storageReference.child(id);
            mroot.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String imageurl = uri.toString();
                    Glide.with(getApplicationContext()).load(imageurl).into(view);
                    Log.v("check here","its done");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Dashboard_common.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });


        }catch (NullPointerException e)
        {}

    }


}

