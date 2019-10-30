package com.example.priority_app;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class InboxFragment1 extends Fragment {



    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    private View mView;
    ListView listView;

    private AlertDialog enableNotificationListenerAlertDialog;
    public ImageView interceptedNotificationImageView;
    private ImageChangeBroadcastReceiver imageChangeBroadcastReceiver;

    private TextView t2;


    public InboxFragment1() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.listener_tester, container, false);
        t2=(TextView)mView.findViewById(R.id.time);

        int i=0;

        //listView = (ListView) mView.findViewById(R.id.listview2);




        final PackageManager pm = getActivity().getPackageManager();

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        List <PackageInfo>pk2=getActivity().getPackageManager().getInstalledPackages(0);

        ArrayList<DataClassforProductivityInbox> appsList = new ArrayList<>();

       /* for (ApplicationInfo packageInfo : packages) {
            //Log.d("test", "App: " + packageInfo.name + " Package: " + packageInfo.packageName);
            ApplicationInfo applicationInfo=packageInfo;
            try {
                i=i+1;
                PackageInfo p4=pm.getPackageInfo(packageInfo.packageName, PackageManager.GET_PERMISSIONS);
                String[] requestedPermissions = p4.requestedPermissions;
                if(requestedPermissions!=null)
                {
                    if((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 1)
                    {
                        appsList.add(new DataClassforProductivityInbox( packageInfo.loadUnbadgedIcon(pm), p4.applicationInfo.loadLabel(getActivity().getPackageManager()).toString()));
                        for (int j = 0; j < requestedPermissions.length; j++) {
                            Log.d(TAG, "APPLICATION" + " " + packageInfo.packageName + " " + requestedPermissions[j]);

                        }

                    }
                }

            }
            catch(PackageManager.NameNotFoundException e)
            {
                e.printStackTrace();
            }


        }*/

       /* AdapterClassProductivityInbox mAdapter = new AdapterClassProductivityInbox(getActivity().getApplicationContext(), appsList);
        listView.setAdapter(mAdapter);*/

        // If the user did not turn the notification listener service on we prompt him to do so

        if(!isNotificationServiceEnabled()) {
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
            enableNotificationListenerAlertDialog.show();

        }

        // Finally we register a receiver to tell the MainActivity when a notification has been received
        //imageChangeBroadcastReceiver = new ImageChangeBroadcastReceiver();
        if(imageChangeBroadcastReceiver==null)
        {
            Toast.makeText(getActivity().getApplicationContext(),"imageChangeBroadcastReceiver is null",Toast.LENGTH_SHORT).show();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.priority_app");
        getActivity().registerReceiver(imageChangeBroadcastReceiver,intentFilter);

        Toast.makeText(getActivity().getApplicationContext(),"Receiver Registered",Toast.LENGTH_SHORT).show();

        interceptedNotificationImageView = (ImageView) mView.findViewById(R.id.logo);

        if(interceptedNotificationImageView==null)
        {
            Toast.makeText(getActivity().getApplicationContext(),"Image object is null",Toast.LENGTH_LONG).show();
        }
        interceptedNotificationImageView.setImageResource(R.drawable.instagram);

      /*  NotificationListenerService o=new NotificationListenerService() {
            @Override
            public void onNotificationPosted(StatusBarNotification sbn) {
                super.onNotificationPosted(sbn);
                String pkgname=sbn.getPackageName();
                String msg="message of"+pkgname+"is noted";
                //StatusBarNotification [] activeNotifications=this.getActiveNotifications();
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                if(pkgname!=null)
                {
                    Intent intent = new  Intent("com.example.priority_app");
                    intent.putExtra("Package Name",pkgname);
                    sendBroadcast(intent);
                }

            }
        };*/

        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(imageChangeBroadcastReceiver);
    }

    /*    private boolean isNotificationServiceEnabled() {

    }*/


    /**
     * Change Intercepted Notification Image
     * Changes the MainActivity image based on which notification was intercepted
     *
     */
    private void changeInterceptedNotificationImage(String pkgname)
    {
        interceptedNotificationImageView.setImageResource(R.drawable.instagram);
        Toast.makeText(getActivity().getApplicationContext(),"Notification Intercepted",Toast.LENGTH_SHORT).show();


    }


    /**
     * Is Notification Service Enabled.
     * Verifies if the notification listener service is enabled.
     * Got it from: https://github.com/kpbird/NotificationListenerService-Example/blob/master/NLSExample/src/main/java/com/kpbird/nlsexample/NLService.java
     * @return True if enabled, false otherwise.
     */

    private boolean isNotificationServiceEnabled(){
        String pkgName = getActivity().getPackageName();
        final String flat = Settings.Secure.getString(getActivity().getContentResolver(), ENABLED_NOTIFICATION_LISTENERS);
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
        @Override
        public void onReceive(Context context, Intent intent) {
            //        Toast.makeText(getApplicationContext(),"Notification Intercepted",Toast.LENGTH_SHORT).show();
            String pkgname = intent.getStringExtra("Notification Code");
            t2.setText(pkgname);
            //changeInterceptedNotificationImage(pkgname);
        }
    }


    /**
     * Build Notification Listener Alert Dialog.
     * Builds the alert dialog that pops up if the user has not turned
     * the Notification Listener Service on yet.
     * @return An alert dialog which leads to the notification enabling screen
     */
    private AlertDialog buildNotificationServiceAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.myDialog));


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
    }


}







