package com.example.priority_app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class AdapterClassProductivityMain extends ArrayAdapter<DataClassforProductivityMode> {

    private Context cont;
    FileWriter fstream,fstream2;
    FileReader fread;
    File folder= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    File myFile=new File(folder,"ListofAllowedApps.txt");
    File myFile2=new File(folder,"temp.txt");
    private List<DataClassforProductivityMode> resc=new ArrayList<>();
    DataClassforProductivityMode currentapp;

    public AdapterClassProductivityMain(@NonNull Context context,  ArrayList<DataClassforProductivityMode> list) {
        super(context, 0,list);
        cont=context;
        resc=list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
       // final int flag1=0;
        View listitem=convertView;

        if(listitem==null)
        {
            listitem= LayoutInflater.from(cont).inflate(R.layout.productivity_mode_listview_row,parent,false);

        }
        currentapp=resc.get(position);

        final TextView tview=(TextView) listitem.findViewById(R.id.switchtext);
        tview.setText("NOT SELECTED");

        int flag2=0;
        final Switch switch1 = (Switch) listitem.findViewById(R.id.switch2);
        switch1.setChecked(false);
        try {
            fstream=new FileWriter(myFile,true);
            fread = new FileReader(myFile);
            BufferedReader bfr = new BufferedReader(fread);
            String line=bfr.readLine();
            while(line!=null)
            {
                if(line.equals(currentapp.pkgname()))
                {
                    flag2=1;
                    line=bfr.readLine();
                }
                line=bfr.readLine();
            }
            fread.close();
            if(flag2==1)
            {
                switch1.setChecked(true);
                tview.setText("SELECTED");
            }
            else {
                switch1.setChecked(false);
                tview.setText("NOT SELECTED");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        TextView t1= (TextView)listitem.findViewById(R.id.switchtext2);
        t1.setText(currentapp.getName());

        ImageView image=(ImageView) listitem.findViewById(R.id.imageView3);
        image.setImageDrawable(currentapp.getIcon());


        Boolean switchState = switch1.isChecked();


        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch1.isChecked())
                {
                    tview.setText("SELECTED");
                    Parallel o=new Parallel(1,position);
                    o.start();
                }
                else
                {
                    tview.setText("NOT SELECTED");
                    Parallel2 o1=new Parallel2(position);
                    o1.start();
                }
            }
        });

            return listitem;
    }

class Parallel extends Thread
{

    int y;
    private static final String TAG="Debug";

    Parallel(int value,int position)
    {
        y=value;
        currentapp=resc.get(position);
    }
    public void run()
    {
       if(y==1)
        {

            try {
                int flag1=0;
                fstream=new FileWriter(myFile,true);
                fread=new FileReader(myFile);
                if(fread!=null)
                {
                    BufferedReader bfr = new BufferedReader(fread);
                    String line = bfr.readLine();
                    while (line != null) {
                        if (line.equals(currentapp.pkgname()))
                        {
                            flag1 = 1;
                            line = bfr.readLine();
                            //Toast.makeText(getContext(), "The package is already present", Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"The package is already present");
                        }
                        line = bfr.readLine();
                    }
                }
                fread.close();
                if(flag1 ==0) {
                    fstream = new FileWriter(myFile, true);
                    fstream.append(currentapp.pkgname() + "\n");
                    //Toast.makeText(getContext(),"Writing into file",Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Writing into file");
                    fstream.close();
                }
            }
            catch(Exception e1)
            {
                e1.printStackTrace();
            }
        }






    }
}

class Parallel2 extends Thread
{
    Parallel2(int position)
    {
        //y=value;
        currentapp=resc.get(position);
    }


    public void run()
    {

        try
       {
           BufferedReader reader = new BufferedReader(new FileReader(myFile));
           BufferedWriter writer = new BufferedWriter(new FileWriter(myFile2));

           String lineToRemove = currentapp.pkgname();
           String currentLine;

           while((currentLine = reader.readLine()) != null) {
               // trim newline when comparing with lineToRemove
               String trimmedLine = currentLine.trim();
               if(trimmedLine.equals(lineToRemove)) continue;
               writer.write(currentLine + "\n");
           }
           writer.close();
           reader.close();
           boolean successful = myFile2.renameTo(myFile);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
     }
    }
}
