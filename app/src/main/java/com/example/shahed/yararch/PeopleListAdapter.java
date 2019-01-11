package com.example.shahed.yararch;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.requestPermissions;

public class PeopleListAdapter extends ArrayAdapter<ProfileModel> {

    private Context context;
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private DonationModel donationModel;
    private YarDatabaseSource yarDatabaseSource;
    private ArrayList<ProfileModel> peopleProfile;

    public PeopleListAdapter(@NonNull Context context, ArrayList<ProfileModel> peopleProfile) {
        super(context, R.layout.row_layout_people_list, peopleProfile);
        this.context = context;
        this.peopleProfile = peopleProfile;
    }

    class ViewHolder {
        ImageView personImageIV;
        ImageView callIconIV;
        TextView fullNameTV;
        TextView phoneNoTV;
        //String profileIdS;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        yarDatabaseSource = new YarDatabaseSource(getContext());

        final PeopleListAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new PeopleListAdapter.ViewHolder();

            convertView = inflater.inflate(R.layout.row_layout_people_list, parent, false);
            holder.personImageIV = (ImageView) convertView.findViewById(R.id.showUserImage);
            holder.callIconIV = (ImageView) convertView.findViewById(R.id.callIcon1);
            holder.fullNameTV = (TextView) convertView.findViewById(R.id.fullName);
            holder.phoneNoTV = (TextView) convertView.findViewById(R.id.userPhone);
            // holder.profileIdS = ;
            //holder.estBudjetTV  = (TextView) convertView.findViewById(R.id.estBudjet);
            //holder.modifyBtn  = (Button) convertView.findViewById(R.id.modify);

            convertView.setTag(holder);
        } else {
            holder = (PeopleListAdapter.ViewHolder) convertView.getTag();
        }

        String imagePathName = peopleProfile.get(position).getImagePath();
        //Toast.makeText(context, imagePathName, Toast.LENGTH_SHORT).show();
//        File file = new File(imagePathName);
        //      if(file.exists()){
        Bitmap myBitmap = BitmapFactory.decodeFile(imagePathName);
        holder.personImageIV.setImageBitmap(myBitmap);
        //    }
        final int profileID = peopleProfile.get(position).getProfileId();
        final String userName = peopleProfile.get(position).getAddress();

        //holder.personImageIV.setImageDrawable(peopleProfile.get(position).getImagePath(),R.drawable.user);
        holder.fullNameTV.setText(peopleProfile.get(position).getName());
        holder.phoneNoTV.setText(peopleProfile.get(position).getPhoneNo());


        holder.callIconIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = holder.phoneNoTV.getText().toString();
                //Intent call = new Intent(Intent.ACTION_DIAL);
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + phoneNumber));

                //ActivityCompat.requestPermissions(getContext(), new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_CALL_PHONE);
                //return;
                if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    return;
                }
                context.startActivity(call);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                dialog.setContentView(R.layout.details_update_donate_delete_popup);

                Button detailsBtn = (Button) dialog.findViewById(R.id.details);
                Button deleteBtn = (Button) dialog.findViewById(R.id.delete);
                Button donateBtn = (Button) dialog.findViewById(R.id.donate);
                Button updateBtn = (Button) dialog.findViewById(R.id.update);
                Button cancelBtn = (Button) dialog.findViewById(R.id.back);

                detailsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getContext().startActivity(new Intent(getContext(), PeopleDetailsActivity.class)
                                .putExtra("userName", userName)
                                .putExtra("id", profileID));

                        notifyDataSetChanged();
                        dialog.dismiss();


                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Delete People !!!");
                        alert.setMessage("Are you sure to delete this People ?");
                        alert.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean status = yarDatabaseSource.deletePeople(profileID);
                                if (status) {
                                    Toast.makeText(context, "People Deleted", Toast.LENGTH_SHORT).show();
                                    getContext().startActivity(new Intent(getContext(), MainActivity.class)
                                            .putExtra("userName", userName));

                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(context, "Couldn't Delete", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alert.setNegativeButton("Cancel", null);
                        alert.show();

                    }
                });

                donateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final LinearLayout layout = new LinearLayout(context);
                        layout.setOrientation(LinearLayout.VERTICAL);

                        final TextView errorMsg = new TextView(getContext());
                        errorMsg.setTextColor(Color.parseColor("#DC143C"));
                        errorMsg.setGravity(TextView.TEXT_ALIGNMENT_GRAVITY);
                        layout.addView(errorMsg);
                        final EditText donationAmount = new EditText(getContext());
                        donationAmount.setHint("Donation Amount");
                        donationAmount.setTextColor(Color.parseColor("#9932CC"));
                        //donationAmount.setGravity(EditText.TEXT_ALIGNMENT_GRAVITY);
                        layout.addView(donationAmount);
                        final EditText donationCause = new EditText(getContext());
                        donationCause.setHint("Donation Cause or details");
                        donationCause.setTextColor(Color.parseColor("#9932CC"));
                        layout.addView(donationCause);


                        final AlertDialog dialog = new AlertDialog.Builder(context)
                                .setView(layout)
                                .setTitle("Add Donation !!!")
                                .setPositiveButton("Add", null) //Set to null. We override the onclick
                                .setNegativeButton(android.R.string.cancel, null)
                                .create();

                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                            @Override
                            public void onShow(DialogInterface dialogInterface) {

                                Button buttonPositive = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                                buttonPositive.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View view) {
                                        // TODO Do something

                                        String amount = donationAmount.getText().toString();
                                        String cause = donationCause.getText().toString();
                                        if (amount.isEmpty()) {
                                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                            alert.setView(layout);
                                            donationAmount.setError("Can't be Empty");
                                            //return;
                                        } else {
                                            try {
                                                double finalAmount = Double.parseDouble(amount);
                                                donationModel = new DonationModel(profileID, finalAmount, userName, cause);
                                                boolean status = yarDatabaseSource.addDonation(donationModel);
                                                if (status) {
                                                    errorMsg.setText("Successfully Saved.");

                                                    /*try {

                                                        //wait(1000);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }*/
                                                    //Dismiss once everything is OK.
                                                    dialog.dismiss();
                                                } else {
                                                    errorMsg.setText("Couldn't Save ! Try Again ?");
                                                }
                                            } catch (NumberFormatException e) {
                                                donationAmount.setError("Please Enter Only Number !!!");
                                                // handle the exception
                                            }


                                        }
                                    }
                                });
                            }
                        });
                        dialog.show();
                        //alert.setMessage("Do you want to add donation ?");
                        /*alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String amount = donationAmount.getText().toString();
                                String cause = donationCause.getText().toString();
                                if(amount.isEmpty()){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                    alert.setView(layout);
                                    donationAmount.setError("Can't be Empty");
                                    //return;
                                }
                                else {
                                    boolean status = yarDatabaseSource.deletePeople(profileID);
                                    if (status) {
                                        //Toast.makeText(context, "People Deleted", Toast.LENGTH_SHORT).show();
                                        getContext().startActivity(new Intent(getContext(), MainActivity.class)
                                                .putExtra("userName", userName));

                                        notifyDataSetChanged();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(context, "Couldn't Delete", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        alert.setNegativeButton("Cancel",null);
                        alert.show();*/

                    }
                });


                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();

            }
        });
        return convertView;
    }
}
