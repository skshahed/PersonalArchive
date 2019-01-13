package com.example.shahed.yararch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
        String donationAmount = String.valueOf(donationModelArrayList.get(position).getDonationAmount());
        holder.donationDateTV.setText(donationModelArrayList.get(position).getDonationDate());
        holder.amountTV.setText(donationAmount+" TK.");


        return convertView;
    }
}
