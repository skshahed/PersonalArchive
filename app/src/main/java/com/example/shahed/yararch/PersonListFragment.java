package com.example.shahed.yararch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonListFragment extends Fragment {
    private View firstInflatedView;
    private String userName, nameOrPhone, addressSearch;
    private ListView peopleLV;
    private TextView emptyText;
    private EditText searhET;
    private Button searchBtn;
    private Spinner thanaNameS, unionNameS;
    private PeopleListAdapter peopleListAdapter;
    private ArrayList<ProfileModel> profileModels;
    private YarDatabaseSource yarDatabaseSource;

    public PersonListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (firstInflatedView==null) {
            firstInflatedView = inflater.inflate(R.layout.fragment_person_list, container, false);
        }
        userName = "yousuf";
        //userName = getIntent().getStringExtra("userName");
        thanaNameS = (Spinner) firstInflatedView.findViewById(R.id.thanaName);
        unionNameS = (Spinner) firstInflatedView.findViewById(R.id.unionName);
        searhET = (EditText) firstInflatedView.findViewById(R.id.searchResult);
        searchBtn = (Button) firstInflatedView.findViewById(R.id.searchBy);

        emptyText = (TextView) firstInflatedView.findViewById(R.id.emptyPeopleList);
        peopleLV = (ListView) firstInflatedView.findViewById(R.id.peopleList);
        peopleLV.setEmptyView(emptyText);
        yarDatabaseSource = new YarDatabaseSource(getContext());


        ArrayAdapter<CharSequence> spinnerThanaAdapter = ArrayAdapter.createFromResource(getContext(),R.array.thana_name,android.R.layout.simple_spinner_item);
        spinnerThanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        thanaNameS.setAdapter(spinnerThanaAdapter);

        ArrayAdapter<CharSequence> spinnerUnionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.union_name,android.R.layout.simple_spinner_item);
        spinnerUnionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unionNameS.setAdapter(spinnerUnionAdapter);

        thanaNameS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                thanaNameS = (Spinner) firstInflatedView.findViewById(R.id.thanaName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        unionNameS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unionNameS = (Spinner) firstInflatedView.findViewById(R.id.unionName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        nameOrPhone = null;
        addressSearch = null;
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameOrPhone = searhET.getText().toString();
                if(nameOrPhone.isEmpty()){
                    nameOrPhone = null;
                }
                if ((thanaNameS.getSelectedItem().toString() == "Upazila-N/A") && (unionNameS.getSelectedItem() == "Union-N/A")){
                    addressSearch = null;
                }
                else if(unionNameS.getSelectedItem().toString() == "Union-N/A"){
                    addressSearch = thanaNameS.getSelectedItem()+ ",";
                }
                else if(unionNameS.getSelectedItem().toString() == "Upazila-N/A"){
                    addressSearch = unionNameS.getSelectedItem()+ "";
                }
                else {
                    addressSearch = thanaNameS.getSelectedItem().toString() + ", " + unionNameS.getSelectedItem().toString();
                }
                try {
                    profileModels = yarDatabaseSource.getAllPeople(userName, nameOrPhone, addressSearch);
                    peopleListAdapter = new PeopleListAdapter(getContext(), profileModels);
                    peopleLV.setAdapter(peopleListAdapter);
                }catch (Exception e){

                }
            }
        });
        profileModels = yarDatabaseSource.getAllPeople(userName,nameOrPhone,addressSearch);
        peopleListAdapter = new PeopleListAdapter(getContext(), profileModels);
        peopleLV.setAdapter(peopleListAdapter);
        return firstInflatedView;
    }

}
