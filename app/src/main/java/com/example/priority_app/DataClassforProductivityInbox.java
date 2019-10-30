package com.example.priority_app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.String;

public class DataClassforProductivityInbox {

    private int image;
    private String name;
    private Drawable icon;
    private String message,time;
    private String date;
    int size1;
    public DataClassforProductivityInbox( Drawable icon, String name,String message,String time,String date)
    {

        this.icon=icon;
        this.name=name;
        this.message=message;
        this.time=time;
        this.date=date;
    }
    public int getimgcode()
    {
        return image;
    }



    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getMessage()
    {
        return message;
    }

    public String getTime()
    {
        return time;
    }
    public String getdate(){
        return date;
    }


    public void setimage(int value)
    {
        this.image=value;
    }
}
