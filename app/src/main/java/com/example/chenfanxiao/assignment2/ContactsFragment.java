package com.example.chenfanxiao.assignment2;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class ContactsFragment extends Fragment {
    private static final int REQ_CODE = 123;


    ContactsAdapter adapter;
    private ArrayList<String> nameList;
    String nameAdd;
    ListView listView;
    boolean mDualPane;


    private void readData()
    {
        String Names;
        SharedPreferences prefs=getActivity().getSharedPreferences("Contacts_Name",MODE_PRIVATE);
        Names=prefs.getString("Names","");
        if ( Names!="") {
            nameList=new ArrayList<String>(Arrays.asList(Names.split("\t")));
        }
        else
            nameList=new ArrayList<String>();


    }
    private  void writeData()
    {
        String nameSet="";
        for (String singleName: nameList)
        {
            nameSet+=singleName+'\t';
        }
        SharedPreferences prefs = getActivity().getSharedPreferences("Contacts_Name",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor=prefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("Names",nameSet);
        prefsEditor.commit();
    }

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public  void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        readData();
        adapter = new ContactsAdapter(getActivity(),nameList);

        listView=(ListView)getActivity().findViewById(R.id.ListView_Main);
        listView.setAdapter(adapter);
        if (savedInstanceState!=null) {
            adapter.setIsSelected((ArrayList<Boolean>) savedInstanceState.getSerializable("CheckList"));
            Log.wtf("Bundle", ":read successfully");
        }
        View DisplayFrame=getActivity().findViewById(R.id.DisplayFrame);
        mDualPane = DisplayFrame != null && DisplayFrame.getVisibility() == View.VISIBLE;

        if (mDualPane)
        {
            Log.wtf("Fragment", ":restart successfully");
            Fragment details=(Fragment)getFragmentManager().findFragmentById(R.id.DisplayFrame);
            if (details!=null)
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.DisplayFrame)).commit();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> list,
                                    View row,
                                    int index,
                                    long rowID)
            {
                if (mDualPane)
                {
                    Fragment details=(Fragment)getFragmentManager().findFragmentById(R.id.DisplayFrame);
                    details=ProfileFragment.newInstance(nameList.get(index),nameList);
                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                    ft.replace(R.id.DisplayFrame,details);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();

                }
                else {
                    Intent intent_profile = new Intent(row.getContext(), contact_profile.class);
                    intent_profile.putExtra("name", nameList.get(index));
                    intent_profile.putStringArrayListExtra("nameList", nameList);
                    startActivity(intent_profile);
                }
            }
        });

        Button button_Delete=(Button)getActivity().findViewById(R.id.button_Delete);
        button_Delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                listView=(ListView)getActivity().findViewById(R.id.ListView_Main);

                int itemCount = listView.getCount();
                for(int i=itemCount-1; i >= 0; i--){
                    if(adapter.isSelected.get(i)){
                        adapter.remove(adapter.getItem(i));
                        adapter.isSelected.remove(i);
                    }
                }

                writeData();
            }
        });
        Button button_Add=(Button)getActivity().findViewById(R.id.button_Add);
        button_Add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if(mDualPane)
                {
                    Fragment details=(Fragment)getFragmentManager().findFragmentById(R.id.DisplayFrame);
                    details=DetailsFragment.newInstance(nameList);
                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                    ft.replace(R.id.DisplayFrame,details);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
                else {
                    Intent intent = new Intent(getActivity(), ContactDetails.class);
                    intent.putStringArrayListExtra("nameList", nameList);
                    startActivityForResult(intent, REQ_CODE);
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQ_CODE)
            if (resultCode == RESULT_OK)
            {
                nameAdd=data.getStringExtra("name");

                adapter.add(nameAdd);
                adapter.isSelected.add(false);
                writeData();
                Log.wtf("task", ": successfully");
            }


    }
    public void addContacts(String nameAdd)
    {
        adapter.add(nameAdd);
        adapter.isSelected.add(false);
        writeData();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("CheckList",adapter.getIsSelected());
        Log.wtf("Bundle", ":save successfully");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.wtf("view", ": successfully");
        return inflater.inflate(R.layout.activity_contacts,container,false);
    }


}
