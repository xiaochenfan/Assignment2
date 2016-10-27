package com.example.chenfanxiao.assignment2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Contacts extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

//    private static final int REQ_CODE = 123;
//
//
//    ContactsAdapter adapter;
//    ArrayList<String> nameList;
//    String nameAdd;
//    ListView listView;
//
//
//    private void readData()
//    {
//        String Names;
//        SharedPreferences prefs=this.getSharedPreferences("Contacts_Name",MODE_PRIVATE);
//        Names=prefs.getString("Names","");
//        if ( Names!="") {
//            nameList=new ArrayList<String>(Arrays.asList(Names.split("\t")));
//        }
//        else
//            nameList=new ArrayList<String>();
//
//
//    }
//    private  void writeData()
//    {
//        String nameSet="";
//        for (String singleName: nameList)
//        {
//            nameSet+=singleName+'\t';
//        }
//        SharedPreferences prefs = this.getSharedPreferences("Contacts_Name",MODE_PRIVATE);
//        SharedPreferences.Editor prefsEditor=prefs.edit();
//        prefsEditor.clear();
//        prefsEditor.putString("Names",nameSet);
//        prefsEditor.commit();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_contacts);
//        Log.wtf("create", ": successfully");
//        readData();
//        adapter = new ContactsAdapter(this,nameList);
//
//        listView=(ListView)findViewById(R.id.ListView_Main);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> list,
//                                    View row,
//                                    int index,
//                                    long rowID)
//            {
//                Intent intent_profile= new Intent(row.getContext(),contact_profile.class);
//                intent_profile.putExtra("name",nameList.get(index));
//                intent_profile.putStringArrayListExtra("nameList",nameList);
//                startActivity(intent_profile);
//
//            }
//        });
//        Log.wtf("read", ": data successfully");
//
//
//    }
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState)
//    {
//
//        savedInstanceState.putStringArrayList("nameList",nameList);
//        super.onSaveInstanceState(savedInstanceState);
//        writeData();
//        Log.wtf("task", ":save data successfully");
//        Log.wtf("task", ":onsave successfully");
//    }
////    @Override
////    public void onRestoreInstanceState(Bundle savedInstanceState)
////    {
////        super.onRestoreInstanceState(savedInstanceState);
////        nameList=savedInstanceState.getStringArrayList("nameList");
////        adapter = new ContactsAdapter(this,nameList);
////        ListView listView=(ListView)findViewById(R.id.ListView_Main);
////        listView.setAdapter(adapter);
////        Log.wtf("task", ":restore successfully");
////    }
//
//
//    public void DeleteOnClick(View view)
//    {
//       listView=(ListView)findViewById(R.id.ListView_Main);
//
//        int itemCount = listView.getCount();
//        for(int i=itemCount-1; i >= 0; i--){
//            if(adapter.isSelected.get(i)){
//                adapter.remove(adapter.getItem(i));
//                adapter.isSelected.remove(i);
//            }
//        }
//
//        writeData();
//    }
//    public void AddOnClick(View view)
//    {
//        Intent intent = new Intent(this,ContactDetails.class);
//        intent.putStringArrayListExtra("nameList",nameList);
//        startActivityForResult(intent, REQ_CODE);
//
//
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        if (requestCode == REQ_CODE)
//          if (resultCode == RESULT_OK)
//            {
//                nameAdd=data.getStringExtra("name");
//
//                adapter.add(nameAdd);
//                adapter.isSelected.add(false);
//                writeData();
//                Log.wtf("task", ": successfully");
//            }
//
//
//    }







}
