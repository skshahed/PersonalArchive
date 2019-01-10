package com.example.shahed.yararch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PeopleListAdapter extends ArrayAdapter<ProfileModel> {

    private Context context;
    private YarDatabaseSource yarDatabaseSource;
    private ArrayList<ProfileModel> peopleProfile;
    public PeopleListAdapter(@NonNull Context context, ArrayList<ProfileModel> peopleProfile) {
        super(context, R.layout.row_layout_people_list, peopleProfile);
        this.context = context;
        this.peopleProfile = peopleProfile;
    }
    class ViewHolder{
        ImageView personImageIV;
        TextView fullNameTV;
        TextView phoneNoTV;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        yarDatabaseSource = new YarDatabaseSource(getContext());

        final PeopleListAdapter.ViewHolder holder;
        if(convertView==null) {
            holder          =   new PeopleListAdapter.ViewHolder();

            convertView     = inflater.inflate(R.layout.row_layout_people_list, parent, false);
            holder.personImageIV= (ImageView) convertView.findViewById(R.id.showUserImage);
            holder.fullNameTV   = (TextView) convertView.findViewById(R.id.fullName);
            holder.phoneNoTV     = (TextView) convertView.findViewById(R.id.userPhone);
            //holder.estBudjetTV  = (TextView) convertView.findViewById(R.id.estBudjet);
            //holder.modifyBtn  = (Button) convertView.findViewById(R.id.modify);

            convertView.setTag(holder);
        }else{
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

        //holder.personImageIV.setImageDrawable(peopleProfile.get(position).getImagePath(),R.drawable.user);
        holder.fullNameTV.setText(peopleProfile.get(position).getName());
        holder.phoneNoTV.setText(peopleProfile.get(position).getPhoneNo());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                dialog.setContentView(R.layout.details_update_donate_delete_popup);

                Button detailsBtn= (Button) dialog.findViewById(R.id.details);
                Button deleteBtn = (Button) dialog.findViewById(R.id.delete);
                Button donateBtn = (Button) dialog.findViewById(R.id.donate);
                Button updateBtn = (Button) dialog.findViewById(R.id.update);
                Button cancelBtn = (Button) dialog.findViewById(R.id.back);

                detailsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getContext().startActivity(new Intent(getContext(),PeopleDetailsActivity.class)
                                .putExtra("rDate",peopleProfile.get(position).getRegisterDate())
                                .putExtra("id",peopleProfile.get(position).getProfileId()));
                                /*.putExtra("userName",userName)

                                .putExtra("destination",events.get(position).getTraDestination())
                                .putExtra("fdate",events.get(position).getTraFromDate())
                                .putExtra("tdate",events.get(position).getTraToDate())
                                .putExtra("bgt",events.get(position).getTraBudget())
                                .putExtra("totalExpense",events.get(position).getTotalExpAmt()));*/
                        notifyDataSetChanged();
                        dialog.dismiss();


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
