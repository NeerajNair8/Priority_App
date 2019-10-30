package com.example.priority_app;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Environment;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * MIT License
 *
 *  Copyright (c) 2016 Fábio Alves Martins Pereira (Chagall)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class NotificationListener extends NotificationListenerService {

    /*
        These are the package names of the apps. for which we want to
        listen the notifications
     */


    /*
        These are the return codes we use in the method which intercepts
        the notifications, to decide whether we should do something or not
     */


    FileWriter fstream;
    FileReader fread;
    FileReader freader;
    File folder= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    File myFile=new File(folder,"inboxproductivity.txt");
    File myFile2=new File(folder,"ListofAllowedApps.txt");
    File myFile3=new File(folder,"ProductivityStatus.txt");



    @Override
    public IBinder onBind(Intent intent)
    {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){


        int flag=0,flag2=0;
        try
        {
            freader=new FileReader(myFile3);
            BufferedReader bfr=new BufferedReader(freader);
            String line=bfr.readLine();
            if(line.equals("OFF"))
                flag2=0;
            else
                flag2=1;

        }

        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error in Productivity Mode File",Toast.LENGTH_LONG).show();
        }

        String pkg=sbn.getPackageName();

        try
        {
            fstream=new FileWriter(myFile2,true);
            freader=new FileReader(myFile2);
            BufferedReader bfr=new BufferedReader(freader);
            String line=bfr.readLine();
            while(line!=null)
            {
                if(line.equals(pkg))
                {
                    flag=1;
                }
                line=bfr.readLine();
            }
            fstream.close();
            freader.close();
        }


        catch(Exception e)
        {
            e.printStackTrace();
        }


      if(flag==1 && flag2==1)
        {


            int id = sbn.getId();
            String KEY = sbn.getKey();

            Calendar calender = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
            String time = simpleDateFormat.format(calender.getTime());


            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());



            String Text = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString();
            String Title = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TITLE).toString();


            Toast.makeText(getApplicationContext(), "Notification Intercepted", Toast.LENGTH_SHORT).show();



        try {
            int flag1=0;
            String content=pkg+Text+Title+date+"\n";
            fstream=new FileWriter(myFile,true);
            fread=new FileReader(myFile);
            if(fread!=null)
            {
                BufferedReader bfr = new BufferedReader(fread);
                String line = bfr.readLine();
                while (line != null)
                {
                    if (line.equals(pkg))
                    {
                        flag1 = 1;
                        //line = bfr.readLine();

                        Toast.makeText(getApplicationContext(), "The message is already present", Toast.LENGTH_SHORT).show();
                    }
                    line = bfr.readLine();
                }
            }
            fread.close();
            if(flag1==0)
            {
                Toast.makeText(getApplicationContext(), "Writing into File", Toast.LENGTH_SHORT).show();
                fstream = new FileWriter(myFile, true);
                fstream.append(pkg + "NOTIFY");
                fstream.append( Text+ "NOTIFY");
                fstream.append(Title + "NOTIFY");
                fstream.append(time + "NOTIFY");
                fstream.append(date + "\n");
                fstream.close();
            }
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

            Intent intent = new Intent("com.example.priority_app");
            cancelNotification(KEY);
            sendBroadcast(intent);
        }

    }






}

