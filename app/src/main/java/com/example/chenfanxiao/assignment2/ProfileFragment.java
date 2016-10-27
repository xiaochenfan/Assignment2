package com.example.chenfanxiao.assignment2;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    ArrayAdapter<String> adapter;
    ArrayList<String> relationshipList,nameList,currentRelationshipList;
    ListView listView;
    String name;

    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            return;
        }
        name=getName();
        nameList=new ArrayList<String>(getnameList());


        SharedPreferences prefs = this.getActivity().getSharedPreferences("Contacts_Phone",MODE_PRIVATE);
        String phone=prefs.getString(name,null);
        TextView nameText=(TextView)getActivity().findViewById(R.id.Display_Name);
        TextView phoneText=(TextView)getActivity().findViewById(R.id.Display_PhoneNumber);
        listView=(ListView)getActivity().findViewById(R.id.ListView_Profile);
        nameText.setText(name);
        phoneText.setText(phone);
        prefs = this.getActivity().getSharedPreferences("Contacts_Relationship",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor=prefs.edit();
        String relationship=prefs.getString(name,"");
        String currentRelationship="";
        relationshipList=new ArrayList<String>(Arrays.asList(relationship.split("\t")));
        currentRelationshipList=new ArrayList<String>();
        for (String currentName:relationshipList)
        {
            if (nameList.contains(currentName))
            {
                currentRelationshipList.add(currentName);
                currentRelationship+=currentName+"\t";
            }
        }
        prefsEditor.putString(name,currentRelationship);
        prefsEditor.commit();
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,currentRelationshipList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> list,
                                    View row,
                                    int index,
                                    long rowID)
            {
                Fragment details=(Fragment)getFragmentManager().findFragmentById(R.id.DisplayFrame);
                details=ProfileFragment.newInstance(currentRelationshipList.get(index),nameList);
                FragmentTransaction ft=getFragmentManager().beginTransaction();
                ft.replace(R.id.DisplayFrame,details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }
        });


    }

    public  static ProfileFragment newInstance(String name,ArrayList<String> s)
    {
        ProfileFragment f=new ProfileFragment();
        Bundle args=new Bundle();
        args.putStringArrayList("nameList",s);
        args.putString("name",name);
        f.setArguments(args);
        return f;
    }
    public ArrayList<String> getnameList()

    {

        return getArguments().getStringArrayList("nameList");
    }
    public String getName()
    {
        return getArguments().getString("name");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     return inflater.inflate(R.layout.activity_contact_profile,container,false);
    }

}
