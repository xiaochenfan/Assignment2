package com.example.chenfanxiao.assignment2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactDetails extends AppCompatActivity {


    ArrayList<String> nameList;
    private  EditText nameText,phoneText;
    DetailsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        Intent intent=getIntent();
        nameList=intent.getStringArrayListExtra("nameList");
        nameText=(EditText)findViewById(R.id.Display_Name);
        phoneText=(EditText)findViewById(R.id.Display_PhoneNumber);
        ListView listView=(ListView)findViewById(R.id.ListView_Details);
        adapter=new DetailsAdapter(this,nameList);
        listView.setAdapter(adapter);


    }

    public void addPersonOnClick(View view)
    {
        String relationship="";
        String name=nameText.getText().toString();
        String phone=phoneText.getText().toString();

        SharedPreferences prefs = this.getSharedPreferences("Contacts_Phone",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor=prefs.edit();
        prefsEditor.putString(name,phone);
        prefsEditor.commit();
        prefs = this.getSharedPreferences("Contacts_Relationship",MODE_PRIVATE);
        prefsEditor=prefs.edit();
        ListView listView=(ListView)findViewById(R.id.ListView_Details);
        int itemCount = listView.getCount();
        for(int i=itemCount-1; i >= 0; i--){
            if(adapter.isSelected.get(i)){
                relationship+=nameList.get(i)+"\t";
                String corresponding=prefs.getString(nameList.get(i),"");
                corresponding+=name+"\t";
                prefsEditor.putString(nameList.get(i),corresponding);

            }
        }
        prefsEditor.putString(name,relationship);
        prefsEditor.commit();



        Intent resultIntent=new Intent();
        resultIntent.putExtra("name",name);
        setResult(RESULT_OK,resultIntent);
        finish();
    }
}
