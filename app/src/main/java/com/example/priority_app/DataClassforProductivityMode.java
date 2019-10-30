package com.example.priority_app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.String;

public class DataClassforProductivityMode {

    private int image;
    private String switchstatus,name;
    private String textvalue,pkgname;
    private Drawable icon;

    private StringBuilder obj=new StringBuilder();
    int size1;
    public DataClassforProductivityMode(  Drawable icon, String name,String pkg)
    {


        this.icon=icon;
        this.name=name;
        this.pkgname=pkg;

    }
    public int getimgcode()
    {
        return image;
    }

    public String getswitch()
    {
     return switchstatus;
    }
    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String pkgname()
    {
        return pkgname;
    }

    public void setswitch(String value)
    {
        this.switchstatus=value;


    }

    public void settext(String value)
    {
        this.textvalue=value ;
    }
    public void setimage(int value)
    {
        this.image=value;
    }


}
