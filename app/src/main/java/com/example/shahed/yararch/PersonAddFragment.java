package com.example.shahed.yararch;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonAddFragment extends Fragment {

    private Spinner thanaNameS, unionNameS;
    private View inflatedView;
    private EditText fullNameET, fatherNameET, detailsET, phoneET, addressET ;
    private String fullAddress, userPhotoPath;
    private Button savePeopleBtn;
    private TextView errorMsgTV;
    private int userId = 0;
    private ProfileModel profileModel;
    private YarDatabaseSource yarDatabaseSource;
    private Calendar calendar;
    private String currentDate;
    private String user;

    public PersonAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (inflatedView==null) {
            inflatedView = inflater.inflate(R.layout.fragment_person_add, container, false);
        }
        thanaNameS = (Spinner) inflatedView.findViewById(R.id.thanaName);
        unionNameS = (Spinner) inflatedView.findViewById(R.id.unionName);
        errorMsgTV = (TextView) inflatedView.findViewById(R.id.showMessage);
        fullNameET = (EditText) inflatedView.findViewById(R.id.personFullName);
        fatherNameET = (EditText) inflatedView.findViewById(R.id.personFatherName);
        detailsET = (EditText) inflatedView.findViewById(R.id.personDetail);
        phoneET = (EditText) inflatedView.findViewById(R.id.personPhoneNumber);
        addressET = (EditText) inflatedView.findViewById(R.id.personAddress);
        savePeopleBtn = (Button) inflatedView.findViewById(R.id.btnAddPeople);
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
                thanaNameS = (Spinner) inflatedView.findViewById(R.id.thanaName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        unionNameS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unionNameS = (Spinner) inflatedView.findViewById(R.id.unionName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        savePeopleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullAddress = addressET.getText().toString()+", "+thanaNameS.getSelectedItem()+", "+unionNameS.getSelectedItem();
                addPeopleToDb();
            }
        });

        calendar = Calendar.getInstance(Locale.getDefault());
        currentDate = String.valueOf(calendar.getTime());
        /*year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);*/

        return inflatedView;
    }

    private void addPeopleToDb() {
        String fullname         =   fullNameET.getText().toString();
        String fatherName         =   fatherNameET.getText().toString();
        String details         =   detailsET.getText().toString();
        String phone        =   phoneET.getText().toString();
        String address = fullAddress;
        String dateNow = currentDate;
        String imagePath = "N/A";
        String userName = "yousuf";
        //userPhotoPath    =   showImagepathTV.getText().toString();

        if(fullname.isEmpty()){
            fullNameET.setError("This field must not be Empty !");
        }
        if(fatherName.isEmpty()){
            fatherNameET.setError("This field must not be Empty !");
        }

        if(details.isEmpty()){
            //detailsET.setError("This field must not be Empty !");
            details = "N/A";
        }

        if(phone.isEmpty()){
            //phoneET.setError("This field must not be Empty !");
            phone = "N/A";
        }else{
            // it condition for update
            if(userId > 0){
               /* this.user =   new User(userPhotoPath,name,user,pass,phone);
                Integer status  = databaseSource.updateUserInfo(this.user,userId);

                if(status==00) {
                    Toast.makeText(getContext(), "This user  name already exits", Toast.LENGTH_SHORT).show();
                }else if(status==1){
                    Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                    *//*startActivity(new Intent(this,ActivityInternal.class)
                            .putExtra("userName",user));*//*
                }else{
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }*/
                // end update
            }
            else{
                //Log.d("TEST", "btnSignUpProcess: "+imagePath);
                this.profileModel =   new ProfileModel(fullname,fatherName,phone,address,details,dateNow,imagePath);
                Integer status  =   yarDatabaseSource.addPeople(this.profileModel, userName);
                if(status==00) {
                    errorMsgTV.setText("This People already exits.");
                    //Toast.makeText(getContext(), "This user  name already exits", Toast.LENGTH_SHORT).show();
                }else if(status==1){
                    errorMsgTV.setText("People added sucessfully.");
                    startActivity(new Intent(getContext(),MainActivity.class)
                            .putExtra("userName",userName));
                   // Toast.makeText(getContext(), "User added sucessfully", Toast.LENGTH_SHORT).show();
                   /* startActivity(new Intent(this,ActivityInternal.class)
                            .putExtra("userName",user));*/
                }else{
                    errorMsgTV.setText("Error! Couldn't Save!!!");
                    //Toast.makeText(getContext(), "Could not save", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
