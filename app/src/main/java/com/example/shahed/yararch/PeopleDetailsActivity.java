package com.example.shahed.yararch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PeopleDetailsActivity extends AppCompatActivity {

    private TextView fullnameTv, fatherNameTv, phoneNoTV, addressTV, registerDateTV, detailsTV,vipTextTV;
    private TextView emptyDonationTV, donateDateHeadTV, donateAmountHeadTV ,donateTotalHeadTV, totalDonateTV;
    private ImageView showImageIV;
    private ListView donationLV;
    private String username;
    private int profileId;
    private ProfileModel profileModel;
    private DonationModel donationModel;
    private DonationListAdapter donationListAdapter;
    private ArrayList<DonationModel> donationModelArrayList;
    private YarDatabaseSource yarDatabaseSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_details);

        getSupportActionBar().setLogo(R.drawable.app_logo);
        profileId = getIntent().getIntExtra("id",0);
        username = getIntent().getStringExtra("userName");
        showImageIV = (ImageView) findViewById(R.id.showPeopleImage);
        vipTextTV = (TextView) findViewById(R.id.vipText);
        fullnameTv = (TextView) findViewById(R.id.showFullName);
        fatherNameTv =(TextView) findViewById(R.id.showFatherName);
        phoneNoTV=(TextView) findViewById(R.id.showPhoneNo);
        addressTV=(TextView) findViewById(R.id.showAddress);
        registerDateTV=(TextView) findViewById(R.id.showRegisterDate);
        detailsTV=(TextView) findViewById(R.id.showDetails);

        emptyDonationTV=(TextView) findViewById(R.id.emptyDonationList);
        donateDateHeadTV=(TextView) findViewById(R.id.donationDateHead);
        donateAmountHeadTV=(TextView) findViewById(R.id.donationAmountHead);
        donateTotalHeadTV=(TextView) findViewById(R.id.donationTotalText);
        totalDonateTV=(TextView) findViewById(R.id.donationTotalAmount);
        donationLV = (ListView) findViewById(R.id.donationList);
        donationLV.setEmptyView(emptyDonationTV);
        final ListViewAutoScrollHelper scrollHelper = new ListViewAutoScrollHelper(donationLV);
        scrollHelper.setEnabled(true);
        donationLV.setOnTouchListener(scrollHelper);



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
            int vip = profileModel.getVipPerson();
            String imagePath = profileModel.getImagePath();
            Bitmap myBitmap = BitmapFactory.decodeFile(imagePath);

            if(vip == 1){
                vipTextTV.setText("VIP");
            }
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

        try{
            /*ExpandableListView: view = listAdapter.getView(0, view, donationLV);
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.MATCH_PARENT, View.MeasureSpec.EXACTLY);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY);
            view.measure(widthMeasureSpec, heightMeasureSpec);*/
            /*donationModelArrayList = yarDatabaseSource.getSinglePeopleDonation(profileId);
            donationListAdapter = new DonationListAdapter(this, donationModelArrayList);
            donationLV.setAdapter(donationListAdapter);*/
            donationModelArrayList = yarDatabaseSource.getSinglePeopleDonation(profileId);
            donationListAdapter = new DonationListAdapter(this, donationModelArrayList);
            donationLV.setAdapter(donationListAdapter);
            setListViewHeightBasedOnChildren(donationLV);

            if(donationLV.getCount() != 0) {
                donateDateHeadTV.setText("Donation Date");
                donateAmountHeadTV.setText("Amount");
                donateTotalHeadTV.setText("Total = ");
                //int index = donationLV.getCount()-1;
                totalDonateTV.setText(String.valueOf(donationModelArrayList.get(donationModelArrayList.size()-1).getTotalDonationAmount())+" TK.");
            }
            //totalDonateTV.setText(donationModelArrayList.lastIndexOf(donationModel.getTotalDonationAmount()));
           // donationLV.setAdapter(donationListAdapter);

        }catch (Exception e){
            emptyDonationTV.setText("... Loading Failed !");
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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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
            //this.finish();
            finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }
}
