package com.example.priority_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Check extends AppCompatActivity {


    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    private View mView;
    ListView listView;

    FileWriter fstream;
    File folder= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    File myFile=new File(folder,"inboxproductivity.txt");
    FileReader fread;

    private AlertDialog enableNotificationListenerAlertDialog;
    public ImageView interceptedNotificationImageView;
    private ImageChangeBroadcastReceiver imageChangeBroadcastReceiver;

    private TextView t2,Title,Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_inbox_productivitymode);


            int i=0;

            listView = (ListView)findViewById(R.id.listview2);




            final PackageManager pm =getPackageManager();

            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

            List <PackageInfo>pk2=getPackageManager().getInstalledPackages(0);

            ArrayList<DataClassforProductivityInbox> appsList = new ArrayList<>();

            // If the user did not turn the notification listener service on we prompt him to do so



        try {


        if(!isNotificationServiceEnabled()) {
            //alertfunction();
            Toast.makeText(getApplicationContext(),"PLEASE GIVE THE NOTIFICATION LISTENER PERMISSION FOR PRIORITY_APP",Toast.LENGTH_LONG).show();
            startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
        }
            }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

            // Finally we register a receiver to tell the MainActivity when a notification has been received
            imageChangeBroadcastReceiver = new ImageChangeBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.example.priority_app");
            registerReceiver(imageChangeBroadcastReceiver,intentFilter);
            Toast.makeText(getApplicationContext(),"Receiver Registered",Toast.LENGTH_SHORT).show();

            try
            {
            Drawable icon;
            String line;
            fstream=new FileWriter(myFile,true);
            fread=new FileReader(myFile);
            BufferedReader bfr=new BufferedReader(fread);
            line=bfr.readLine();
            while(line!=null)
            {
                String[] line2 = line.split("NOTIFY");
                String pkg=line2[0];
                icon = getPackageManager().getApplicationIcon(pkg);
                appsList.add(new DataClassforProductivityInbox(icon, line2[2], line2[1], line2[3],line2[4]));
                line=bfr.readLine();
            }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        AdapterClassProductivityInbox mAdapter = new AdapterClassProductivityInbox(getApplicationContext(), appsList);
        listView.setAdapter(mAdapter);
}

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imageChangeBroadcastReceiver);
    }

    

    private boolean isNotificationServiceEnabled(){
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat))
        {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Image Change Broadcast Receiver.
     * We use this Broadcast Receiver to notify the Main Activity when
     * a new notification has arrived, so it can properly change the
     * notification image
     * */
    public class ImageChangeBroadcastReceiver extends BroadcastReceiver {
        ArrayList<DataClassforProductivityInbox> appsList = new ArrayList<>();
        Drawable icon;
        @Override
        public void onReceive(Context context, Intent intent) {


            try
            {

                String line;
            fstream=new FileWriter(myFile,true);
            fread=new FileReader(myFile);
            BufferedReader bfr=new BufferedReader(fread);
            line=bfr.readLine();
            appsList.clear();
            while(line!=null)
            {
                String[] line2 = line.split("NOTIFY");
                icon = getPackageManager().getApplicationIcon(line2[0]);
                appsList.add(new DataClassforProductivityInbox(icon, line2[2], line2[1], line2[3],line2[4]));
                line=bfr.readLine();
            }
            }
            catch(Exception e)
            {
                    e.printStackTrace();
            }
            AdapterClassProductivityInbox mAdapter = new AdapterClassProductivityInbox(getApplicationContext(), appsList);
            listView.setAdapter(mAdapter);

        }
    }


    /**
     * Build Notification Listener Alert Dialog.
     * Builds the alert dialog that pops up if the user has not turned
     * the Notification Listener Service on yet.
     * @return An alert dialog which leads to the notinotfication enabling screen
     */
    /*private AlertDialog buildNotificationServiceAlertDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getApplicationContext(), R.style.myDialog));


        alertDialogBuilder.setTitle("Notification Listener");
        alertDialogBuilder.setMessage("Message");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
                    }
                });
        alertDialogBuilder.setNegativeButton("no",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If you choose to not enable the notification listener
                        // the app. will not work as expected
                    }
                });
        return(alertDialogBuilder.create());
    }*/

    public void alertfunction()
    {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(getApplicationContext());
        builder.setMessage("GIVE ACCESS TO NOTIFICATIONS?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder
                .setPositiveButton(
                        "OK",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                dialog.cancel();
                                startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
                            }
                        });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }


}
