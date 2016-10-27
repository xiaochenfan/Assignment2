package com.example.chenfanxiao.assignment2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class contact_profile extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    ArrayList<String> relationshipList,nameList,currentRelationshipList;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_profile);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        nameList=intent.getStringArrayListExtra("nameList");
        SharedPreferences prefs = this.getSharedPreferences("Contacts_Phone",MODE_PRIVATE);
        String phone=prefs.getString(name,null);
        TextView nameText=(TextView)findViewById(R.id.Display_Name);
        TextView phoneText=(TextView)findViewById(R.id.Display_PhoneNumber);
        listView=(ListView)findViewById(R.id.ListView_Profile);
        nameText.setText(name);
        phoneText.setText(phone);
        prefs = this.getSharedPreferences("Contacts_Relationship",MODE_PRIVATE);
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
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,currentRelationshipList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> list,
                                    View row,
                                    int index,
                                    long rowID)
            {
                Intent intent_profile= new Intent(row.getContext(),contact_profile.class);
                intent_profile.putExtra("name",currentRelationshipList.get(index));
                intent_profile.putStringArrayListExtra("nameList",nameList);
                startActivity(intent_profile);

            }
        });


    }

}
