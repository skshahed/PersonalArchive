package com.example.shahed.yararch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class DonationListAdapter extends ArrayAdapter<DonationModel> {
    private Context context;
    private DonationModel donationModel;
    private YarDatabaseSource yarDatabaseSource;
    private ArrayList<DonationModel> donationModelArrayList;


    public DonationListAdapter(@NonNull Context context,ArrayList<DonationModel> donationModels) {
        super(context, R.layout.row_layout_donation, donationModels);
        this.context = context;
        this.donationModelArrayList = donationModels;
    }

    class ViewHolder {
        TextView donationDateTV;
        TextView amountTV;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        yarDatabaseSource = new YarDatabaseSource(getContext());

        final DonationListAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new DonationListAdapter.ViewHolder();

            convertView = inflater.inflate(R.layout.row_layout_donation, parent, false);
            holder.donationDateTV = (TextView) convertView.findViewById(R.id.donationDate);
            holder.amountTV = (TextView) convertView.findViewById(R.id.donationAmount);
            convertView.setTag(holder);
        } else {
            holder = (DonationListAdapter.ViewHolder) convertView.getTag();
        }

        final int donationId = donationModelArrayList.get(position).getDonationId();
        final int pId = donationModelArrayList.get(position).getDonationProfileId();
        final String userName = donationModelArrayList.get(position).getUserName();
        String donationAmount = String.valueOf(donationModelArrayList.get(position).getDonationAmount());
        holder.donationDateTV.setText(donationModelArrayList.get(position).getDonationDate());
        holder.amountTV.setText(donationAmount+" TK.");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                dialog.setContentView(R.layout.edit_delete_popup);

                Button deleteBtn = (Button) dialog.findViewById(R.id.deleteDonation);
                Button updateBtn = (Button) dialog.findViewById(R.id.updateDonation);
                Button cancelBtn = (Button) dialog.findViewById(R.id.backDonation);

                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final LinearLayout layout = new LinearLayout(context);
                        layout.setOrientation(LinearLayout.VERTICAL);

                        final TextView errorMsg = new TextView(getContext());
                        errorMsg.setTextColor(Color.parseColor("#DC143C"));
                        errorMsg.setGravity(TextView.TEXT_ALIGNMENT_GRAVITY);
                        layout.addView(errorMsg);
                        final EditText donationAmount = new EditText(getContext());
                        donationAmount.setText(String.valueOf(donationModelArrayList.get(position).getDonationAmount()));
                        donationAmount.setTextColor(Color.parseColor("#9932CC"));
                        //donationAmount.setGravity(EditText.TEXT_ALIGNMENT_GRAVITY);
                        layout.addView(donationAmount);
                        final EditText donationCause = new EditText(getContext());
                        donationCause.setText(donationModelArrayList.get(position).getDonationCause());
                        donationCause.setTextColor(Color.parseColor("#9932CC"));
                        layout.addView(donationCause);


                        final AlertDialog dialog = new AlertDialog.Builder(context)
                                .setView(layout)
                                .setTitle("Edit Donation !!!")
                                .setPositiveButton("Update", null) //Set to null. We override the onclick
                                .setNegativeButton(android.R.string.cancel, null)
                                .create();

                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                Button buttonPositive = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                                buttonPositive.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
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
                                                donationModel = new DonationModel(donationId, finalAmount, cause);
                                                boolean status = yarDatabaseSource.updateDonation(donationModel);
                                                if (status) {
                                                    errorMsg.setText("Successfully Updated.");
                                                    Toast.makeText(context, "Donation Updated.", Toast.LENGTH_SHORT).show();
                                                    getContext().startActivity(new Intent(getContext(), PeopleDetailsActivity.class)
                                                            .putExtra("userName", userName)
                                                            .putExtra("id",pId));
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
                    }
                    // }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Delete Donation !!!");
                        alert.setMessage("Do you want to delete this Donation ?");
                        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean status = yarDatabaseSource.deleteDonation(donationId);
                                if (status) {
                                    Toast.makeText(context, "Donation Deleted", Toast.LENGTH_SHORT).show();
                                    getContext().startActivity(new Intent(getContext(), PeopleDetailsActivity.class)
                                            .putExtra("userName", userName)
                                            .putExtra("id",pId));
                                    //notifyDataSetChanged();
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
