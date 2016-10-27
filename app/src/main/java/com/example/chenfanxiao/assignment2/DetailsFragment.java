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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private ArrayList<String> nameList;
    private EditText nameText,phoneText;
    DetailsAdapter adapter;

    public DetailsFragment() {
        // Required empty public constructor
    }

   public  static DetailsFragment newInstance(ArrayList<String> s)
   {
       DetailsFragment f=new DetailsFragment();
       Bundle args=new Bundle();
       args.putStringArrayList("nameList",s);
       f.setArguments(args);
       return f;
   }
    public ArrayList<String> getnameList()

    {

        return getArguments().getStringArrayList("nameList");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return inflater.inflate(R.layout.details_fragment,container,false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {

        super.onActivityCreated(savedInstanceState);
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            return;
        }

        nameText=(EditText)getActivity().findViewById(R.id.F_InputName);
        phoneText=(EditText)getActivity().findViewById(R.id.F_InputPhone);
        ListView listView=(ListView)getActivity().findViewById(R.id.F_ListView);
        nameList=new ArrayList<String>(getnameList());
        adapter=new DetailsAdapter(getActivity(),nameList);
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
                details=ProfileFragment.newInstance(nameList.get(index),getnameList());
                FragmentTransaction ft=getFragmentManager().beginTransaction();
                ft.replace(R.id.DisplayFrame,details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }
        });
        Button button_Add_Person=(Button)getActivity().findViewById(R.id.F_AddPerson);
        button_Add_Person.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String relationship="";
                String name=nameText.getText().toString();
                String phone=phoneText.getText().toString();

                SharedPreferences prefs = getActivity().getSharedPreferences("Contacts_Phone",MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor=prefs.edit();
                prefsEditor.putString(name,phone);
                prefsEditor.commit();
                prefs = getActivity().getSharedPreferences("Contacts_Relationship",MODE_PRIVATE);
                prefsEditor=prefs.edit();
                ListView listView=(ListView)getActivity().findViewById(R.id.F_ListView);
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
                ContactsFragment fragment=(ContactsFragment) getActivity().getFragmentManager().findFragmentById(R.id.ContactsFragment);
                fragment.addContacts(name);

            }
        });
    }

}
