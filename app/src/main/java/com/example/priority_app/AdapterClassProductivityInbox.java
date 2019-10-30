package com.example.priority_app;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class AdapterClassProductivityInbox extends ArrayAdapter<DataClassforProductivityInbox> {


    private Context cont;
    FileWriter fstream;
    File folder= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    File myFile=new File(folder,"content2.txt");

    private List<DataClassforProductivityInbox> resc=new ArrayList<>();

    public AdapterClassProductivityInbox(@NonNull Context context, ArrayList<DataClassforProductivityInbox> list) {
        super(context,0,list);
        cont=context;
        resc=list;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View listitem=convertView;
        if(listitem==null)
        {
            listitem= LayoutInflater.from(cont).inflate(R.layout.inbox_productivity_sleep_mode_row,parent,false);

        }
        final DataClassforProductivityInbox currentapp=resc.get(position);

        ImageView image=(ImageView) listitem.findViewById(R.id.octopus1);
        image.setImageDrawable(currentapp.getIcon());

        final TextView tview=(TextView) listitem.findViewById(R.id.nameTextViewID);
        tview.setText(currentapp.getName());

        final TextView tview4=(TextView) listitem.findViewById(R.id.infoTextViewID);
        String date=currentapp.getdate();
        tview4.setText(currentapp.getTime()+"\n"+date);

        TextView tview5=(TextView)listitem.findViewById(R.id.infoTextViewID2);
        tview5.setText(currentapp.getMessage());

        return listitem;
    }
}
