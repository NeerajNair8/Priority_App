package com.example.priority_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class InboxFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String[] nameArray = {"Gmail","Whatsapp","Facebook","Instagram"};

    String[] infoArray = {"Click to read your unread mails","Click to read your unread messages","Click to read your unread notifications","Click to read your unread notifications"};
    Integer[] imageArray = {R.drawable.gmail1,R.drawable.whatsapp,R.drawable.facebook,R.drawable.instagram};
    ListView listView;
    private View mView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public InboxFragment2() {
        // Required empty public constructor
    }


    public static InboxFragment2 newInstance(String param1, String param2) {
        InboxFragment2 fragment = new InboxFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_inbox_productivitymode, container, false);
        AdapterClassInboxSleepMode whatever = new AdapterClassInboxSleepMode(getActivity(), nameArray, infoArray, imageArray);
        listView = (ListView) mView.findViewById(R.id.listview2);
        listView.setAdapter(whatever);
        return mView;
    }


}
