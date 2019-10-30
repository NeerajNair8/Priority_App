package com.example.priority_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ProductivityMode extends AppCompatActivity {

    private static final String TAG = "abc";
    //private static final String TAG = ;
    TextView text1;
    Switch switch1;
    private Adapter myadapter;
    ConstraintLayout l;

    private final String CHANNEL_ID = "productivity_notification";
    private final int NOTIFICATION_ID = 001;


    private PackageManager pkg;
    private ApplicationInfo appln;
    private Context context;
    private TextView search;




    FileWriter fstream, fstream2;
    File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    File myFile = new File(folder, "ProductivityStatus.txt");
    File myFile2 = new File(folder, "inboxproductivity.txt");
    FileReader fread;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productivity_mode);

        createNotificationChannel();


        text1 = (TextView) findViewById(R.id.textview1);
        switch1 = (Switch) findViewById(R.id.switch1);
        //search = (TextView) findViewById(R.id.searchtext);


        //Boolean switchState = switch1.isChecked();
        final ListView lapps = (ListView) findViewById(R.id.listapps);

        try {
            fread = new FileReader(myFile);
            fstream = new FileWriter(myFile, true);
            BufferedReader bfr = new BufferedReader(fread);
            String line = bfr.readLine();
            if (line.equals("ON")) {
                switch1.setChecked(true);
                text1.setText("Productivity Mode Enabled");
                lapps.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                l = (ConstraintLayout) findViewById(R.id.Layout1);
                makeNotification();
                l.setBackgroundResource(R.drawable.bktwo);

            } else

                switch1.setChecked(false);

        } catch (Exception e) {
            e.printStackTrace();
        }


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    l = (ConstraintLayout) findViewById(R.id.Layout1);

                    l.setBackgroundResource(R.drawable.bktwo);

                    text1.setText("Productivity Mode Enabled");
                    lapps.setVisibility(View.VISIBLE);
                    //search.setVisibility(View.VISIBLE);

                    makeNotification();


                    try {
                        fstream = new FileWriter(myFile);
                        fstream.append("ON");
                        fstream.close();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "File Error", Toast.LENGTH_LONG).show();
                    }

                } else {

                    String ns = Context.NOTIFICATION_SERVICE;
                    NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                    nMgr.cancel(NOTIFICATION_ID);

                    text1.setText("Tap to enable Productivity Mode");
                    lapps.setVisibility(View.INVISIBLE);
                    //search.setVisibility(View.INVISIBLE);

                    try {
                        fstream = new FileWriter(myFile);
                        fstream2 = new FileWriter(myFile2);
                        fstream.append("OFF");
                        fstream.close();
                        fstream2.close();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "File Error", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });




        ListView listView = (ListView) findViewById(R.id.listapps);
        final PackageManager pm = getPackageManager();
        int i = 0;
        //get a list of installed apps.

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        List<PackageInfo> pk2 = getPackageManager().getInstalledPackages(0);

        ArrayList<DataClassforProductivityMode> appsList = new ArrayList<>();


        for (ApplicationInfo packageInfo : packages) {
            //Log.d("test", "App: " + packageInfo.name + " Package: " + packageInfo.packageName);
            ApplicationInfo applicationInfo = packageInfo;
            try {
                i = i + 1;
                PackageInfo p4 = pm.getPackageInfo(packageInfo.packageName, PackageManager.GET_PERMISSIONS);
                String[] requestedPermissions = p4.requestedPermissions;
                if (requestedPermissions != null) {
                    if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 1 & !packageInfo.packageName.equals("com.example.priority_app")) {
                        appsList.add(new DataClassforProductivityMode(packageInfo.loadUnbadgedIcon(pm), p4.applicationInfo.loadLabel(getPackageManager()).toString(), packageInfo.packageName));
                        for (int j = 0; j < requestedPermissions.length; j++) {
                            Log.d(TAG, "APPLICATION" + " " + packageInfo.packageName + " " + requestedPermissions[j]);

                        }

                    }
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }


        }

        AdapterClassProductivityMain mAdapter = new AdapterClassProductivityMain(this, appsList);
        listView.setAdapter(mAdapter);




    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.productivity_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
//respond to menu item selection
        switch (item.getItemId()) {
            case R.id.menuitem1:
                startActivity(new Intent(this, ProductivitySettings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "productivity_notification";
            String description = "CHANNEL FOR PRODUCTIVITY MODE";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void makeNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.andic)
                .setContentTitle("Priority_App")
                .setContentText("Productivity Mode is Active")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Productivity Mode is Active. Some App Notifications won't be Displayed"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }




    //public void onRequestPermissionsResult(int code)
}
