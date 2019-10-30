package com.example.priority_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priority_app.ui.main.Productivityzone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Spinner spn;
    String x;
    private static final int STORAGE_PERMISSION_CODE = 101;
    Button btn,phonebtn2,userbtn3,smartbtn;

    File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    File myFile = new File(folder, "ProductivityStatus.txt");
    FileWriter fstream;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listener();
        writePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);

    }

    public void listener()
    {
        spn = (Spinner) findViewById(R.id.spinner);
        btn = (Button) findViewById(R.id.button);
        phonebtn2 = (Button) findViewById(R.id.button2);
        userbtn3 = (Button) findViewById(R.id.button3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x=String.valueOf(spn.getSelectedItem());
                if(x.equals("PRODUCTIVITY MODE"))
                {
                    Intent intent2=new Intent(MainActivity.this,ProductivityMode.class);
                    startActivity(intent2);
                }
                else
                if (x.equals("SLEEP MODE"))
                {

                }
                else
                if(x.equals("-------------------- SELECT---------------------"))
                {
                    //THIS IS THE ALERT WINDOW THAT COMES WHEN YOU DON'T SELECT AN OPTION.
                    alertfunction();
                }
            }
        });
        userbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            //   public void onClick(View v) {
              /*  Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,Inbox_activity.class);
                startActivity(intent);*/

            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Check.class));
            }


        });

        phonebtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Productivityzone.class);
                startActivity(intent);
            }
        });

    }

    public void alertfunction()
    {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);
        builder.setMessage("PLEASE ENTER AN OPTION");
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
                            }
                        });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // Do something in response to button

    }
    public void displaytoast()
    {

    }


    public void writePermission(String permission, int code)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission} , code);
            try
            {
                fstream=new FileWriter(myFile);
                fstream.write("OFF");
                fstream.close();
            }

            catch (IOException e)
            {
                Toast.makeText(getApplicationContext(),"File Error",Toast.LENGTH_SHORT).show();
            }
        }


    }


}
