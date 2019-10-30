package com.example.priority_app.ui.main;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.priority_app.MainActivity;
import com.example.priority_app.R;

public class Productivityzone  extends Activity
{

        public int seconds = 60;
        public int minutes = 24;
        String status="START";

        FileWriter fstream;
        FileReader freader;
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File myFile = new File(folder, "ProductivityStatus.txt");
        int flag=0;

        @Override

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            setContentView(R.layout.productivity_zone);


            final Button start=(Button)findViewById(R.id.start);
            final Button pause=(Button)findViewById(R.id.pause);
            //Button exit=(Button)findViewById(R.id.exit);


            Button about=(Button)findViewById(R.id.about);
            final TextView tv = (TextView) findViewById(R.id.main_timer_text);
            tv.setText("25:00");
            final Button resume=(Button)findViewById(R.id.resume);
            resume.setVisibility(View.INVISIBLE);



            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    start.setVisibility(View.INVISIBLE);

                    try {
                        fstream = new FileWriter(myFile, true);
                        fstream.close();
                        freader = new FileReader(myFile);
                        BufferedReader bfr = new BufferedReader(freader);
                        String line = bfr.readLine();
                        if (line != null) {
                            if (!line.equals("ON")) {
                                flag = 1;
                            }
                            freader.close();
                        }
                        else
                        {
                            flag=1;
                        }
                    }
                    catch (Exception e)
                    {

                        Toast.makeText(getApplicationContext(), "File Error", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "The error is here", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    if (flag == 1)
                        alertbox3();
                    else {
                        Timer t = new Timer();

                        //Set the schedule function and rate
                        t.scheduleAtFixedRate(new TimerTask() {

                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        if (status.equals("FALSE")) {
                                            return;
                                        }
                                        if (minutes == 0 && seconds == 1) {

                                            status = "FALSE";
                                            alertfunction();
                                        }

                                        tv.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                                        seconds -= 1;

                                        if (seconds == 0) {
                                            tv.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));

                                            seconds = 60;
                                            minutes = minutes - 1;

                                        }


                                    }

                                });
                            }

                        }, 0, 1000);
                    }


                }
            });


            pause.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v)

                {
                    pause.setVisibility(View.INVISIBLE);

                    resume.setVisibility(View.VISIBLE);
                    status="FALSE";
                }
            });
            {

            }

            resume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resume.setVisibility(View.INVISIBLE);
                    pause.setVisibility(View.VISIBLE);
                    status="TRUE";
                }
            });

            about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aboutpomodoro();
                }
            });

        }
public void aboutpomodoro()
{
    Dialog builder = new Dialog(this);
    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
    builder.getWindow().setBackgroundDrawable(
            new ColorDrawable(android.graphics.Color.TRANSPARENT));
    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            //nothing;
        }
    });

    ImageView imageView = new ImageView(this);
    Drawable obj=getResources().getDrawable(R.drawable.pomo);
    imageView.setImageDrawable(obj);
    builder.addContentView(imageView, new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
    builder.show();

}


    public void alertfunction()
    {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(Productivityzone.this);
        builder.setMessage("HURRAY!!! YOU'VE BEEN PRODUCTIVE FOR 25 MINUTES!!!");
        builder.setTitle("CONGRATULATIONS!!!!");
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
                                Productivityzone.this.finish();
                            }
                        });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    public void alertfunction2()
    {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(Productivityzone.this);
        builder.setMessage("WAIT!!! ARE YOU SURE YOU WANT TO STOP BEING PRODUCTIVE??");
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
                                //dialog.cancel();
                                Productivityzone.this.finish();
                            }
                        });
        builder.setNegativeButton("CANCEL",new  DialogInterface.OnClickListener(){

        @Override
            public void onClick(DialogInterface dialog,int which)
        {
            dialog.cancel();
        }
        });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }



    public void alertbox3()
    {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(Productivityzone.this);
        builder.setMessage("PLEASE SWITCH ON PRODUCTIVITY MODE BEFORE ENTERING POMODORO MODE!!!");
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
                                //dialog.cancel();
                                Productivityzone.this.finish();
                            }
                        });
        /*builder.setNegativeButton("CANCEL",new  DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog,int which)
            {
                dialog.cancel();
            }
        });*/
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        alertfunction2();
        }
}

