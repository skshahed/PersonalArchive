package com.example.shahed.yararch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PeopleDetailsActivity extends AppCompatActivity {

    private TextView fullnameTv, fatherNameTv, phoneNoTV, addressTV, registerDateTV, detailsTV;
    private ImageView showImageIV;
    private String username;
    private int profileId;
    private ProfileModel profileModel;
    private YarDatabaseSource yarDatabaseSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_details);

        profileId = getIntent().getIntExtra("id",0);
        username = getIntent().getStringExtra("userName");
        showImageIV = (ImageView) findViewById(R.id.showPeopleImage);
        fullnameTv = (TextView) findViewById(R.id.showFullName);
        fatherNameTv =(TextView) findViewById(R.id.showFatherName);
        phoneNoTV=(TextView) findViewById(R.id.showPhoneNo);
        addressTV=(TextView) findViewById(R.id.showAddress);
        registerDateTV=(TextView) findViewById(R.id.showRegisterDate);
        detailsTV=(TextView) findViewById(R.id.showDetails);

        //username = "yousuf";
        yarDatabaseSource = new YarDatabaseSource(this);
        //fatherNameTv.setText(profileId);
        //registerDateTV.setText(getIntent().getStringExtra("rDate"));
        try {
            //profileModel = new ProfileModel();
            profileModel = yarDatabaseSource.getSinglePeople(profileId);
            String fullName = profileModel.getName();
            String fathername = profileModel.getFatherName();
            String phoneNo = profileModel.getPhoneNo();
            String address = profileModel.getAddress();
            String details = profileModel.getUserDetails();
            String registerDate = profileModel.getRegisterDate();
            String imagePath = profileModel.getImagePath();

            Bitmap myBitmap = BitmapFactory.decodeFile(imagePath);
            showImageIV.setImageBitmap(myBitmap);
            fullnameTv.setText(fullName);
            fatherNameTv.setText(fathername);
            phoneNoTV.setText(phoneNo);
            addressTV.setText(address);
            detailsTV.setText(details);
            registerDateTV.setText(registerDate);
        }
        catch (Exception e){
            //fullnameTv.setError("No data Found !!!");
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "You Are In Home.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(PeopleDetailsActivity.this,MainActivity.class)
                        .putExtra("userName",username));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent loginActivity=new Intent(this,LoginActivity.class);
            loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginActivity);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
