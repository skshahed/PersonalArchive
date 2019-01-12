package com.example.shahed.yararch;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
    private CheckBox vipPersonCB;
    private String fullAddress, userPhotoPath;
    private Button savePeopleBtn;
    private TextView errorMsgTV, showImagepathTV;
    private ImageView ivImage;
    private int userId = 0;
    private ProfileModel profileModel;
    private YarDatabaseSource yarDatabaseSource;
    private Calendar calendar;
    private String currentDate, district;
    private String user,userName, userChoosenTask;
    private Integer vipPerson;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

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
        userName = getActivity().getIntent().getStringExtra("userName");
        district = "N/A";
        unionNameS = (Spinner) inflatedView.findViewById(R.id.unionName);
        thanaNameS = (Spinner) inflatedView.findViewById(R.id.thanaName);

        showImagepathTV =(TextView) inflatedView.findViewById(R.id.showUserImagepath);
        ivImage=(ImageView) inflatedView.findViewById(R.id.ivImage);
        errorMsgTV = (TextView) inflatedView.findViewById(R.id.showMessage);
        fullNameET = (EditText) inflatedView.findViewById(R.id.personFullName);
        fatherNameET = (EditText) inflatedView.findViewById(R.id.personFatherName);
        detailsET = (EditText) inflatedView.findViewById(R.id.personDetail);
        phoneET = (EditText) inflatedView.findViewById(R.id.personPhoneNumber);
        addressET = (EditText) inflatedView.findViewById(R.id.personAddress);
        vipPersonCB= (CheckBox) inflatedView.findViewById(R.id.personImportant);
        savePeopleBtn = (Button) inflatedView.findViewById(R.id.btnAddPeople);
        yarDatabaseSource = new YarDatabaseSource(getContext());

        //Toolbar toolbar = (Toolbar) inflatedView.findViewById(R.id.toolbar);
        //getActivity().setSupportActionBar(toolbar);
        Bundle args = getArguments();
        try {
            userId = args.getInt("profileId");
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) showImagepathTV.getLayoutParams();
            //params.width = 200; params.leftMargin = 100;
            params.topMargin = 300;
            /*String here = args.getString("here");
            if(here.contentEquals("here")){
                Toast.makeText(getContext(), here+" Fragment= "+str, Toast.LENGTH_SHORT).show();
            }*/
            if(userId > 0){
                profileModel = yarDatabaseSource.getSinglePeople(userId);
                String fullName = profileModel.getName();
                String fathername = profileModel.getFatherName();
                String phoneNo = profileModel.getPhoneNo();
                String address = profileModel.getAddress();
                String details = profileModel.getUserDetails();
                int isVip = profileModel.getVipPerson();
               // String registerDate = profileModel.getRegisterDate();
                userPhotoPath = profileModel.getImagePath();

               // Bitmap myBitmap = BitmapFactory.decodeFile(userPhotoPath);

                fullNameET.setText(fullName);
                fatherNameET.setText(fathername);
                phoneET.setText(phoneNo);
                addressET.setText(address);
                detailsET.setText(details);
                if(isVip == 1){
                    vipPersonCB.setChecked(true);
                }
                else{
                    vipPersonCB.setChecked(false);
                }

                File imgFile = new  File(userPhotoPath);

                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ivImage.setImageBitmap(myBitmap);
                    showImagepathTV.setText(userPhotoPath);
                }
                savePeopleBtn.setText("Update");
            }
        }catch (Exception e){
            //Toast.makeText(getContext(), "No Data Added", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<CharSequence> spinnerUnionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.not_selected,android.R.layout.simple_spinner_item);
        spinnerUnionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unionNameS.setAdapter(spinnerUnionAdapter);

        ArrayAdapter<CharSequence> spinnerThanaAdapter = ArrayAdapter.createFromResource(getContext(),R.array.thana_name,android.R.layout.simple_spinner_item);
        spinnerThanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        thanaNameS.setAdapter(spinnerThanaAdapter);

        thanaNameS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectItemS = String.valueOf(thanaNameS.getSelectedItem());
                if(selectItemS.contentEquals("Ashashuny")){
                    district = "Satkhira, Khulna";
                    ArrayAdapter<CharSequence> spinnerUnionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.ashashuny_union_name,android.R.layout.simple_spinner_item);
                    spinnerUnionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unionNameS.setAdapter(spinnerUnionAdapter);
                }
                else if(selectItemS.contentEquals("Debhata")){
                    district = "Satkhira, Khulna";
                    ArrayAdapter<CharSequence> spinnerUnionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.debhata_union_name,android.R.layout.simple_spinner_item);
                    spinnerUnionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unionNameS.setAdapter(spinnerUnionAdapter);
                }
                else if(selectItemS.contentEquals("Kaligonj")){
                    district = "Satkhira, Khulna";
                    ArrayAdapter<CharSequence> spinnerUnionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.kaligonj_union_name,android.R.layout.simple_spinner_item);
                    spinnerUnionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unionNameS.setAdapter(spinnerUnionAdapter);
                }
                else if(selectItemS.contentEquals("Kolaroa")){
                    district = "Satkhira, Khulna";
                    ArrayAdapter<CharSequence> spinnerUnionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.kolaroa_union_name,android.R.layout.simple_spinner_item);
                    spinnerUnionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unionNameS.setAdapter(spinnerUnionAdapter);
                }
                else if(selectItemS.contentEquals("Satkhira Sadar")){
                    district = "Satkhira, Khulna";
                    ArrayAdapter<CharSequence> spinnerUnionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.satkhira_union_name,android.R.layout.simple_spinner_item);
                    spinnerUnionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unionNameS.setAdapter(spinnerUnionAdapter);
                }
                else if(selectItemS.contentEquals("Shymnogor")){
                    district = "Satkhira, Khulna";
                    ArrayAdapter<CharSequence> spinnerUnionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.shymnogor_union_name,android.R.layout.simple_spinner_item);
                    spinnerUnionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unionNameS.setAdapter(spinnerUnionAdapter);
                }
                else if(selectItemS.contentEquals("Tala")){
                    district = "Satkhira, Khulna";
                    ArrayAdapter<CharSequence> spinnerUnionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.tala_union_name,android.R.layout.simple_spinner_item);
                    spinnerUnionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unionNameS.setAdapter(spinnerUnionAdapter);
                }
                else{
                    district = "N/A";
                    ArrayAdapter<CharSequence> spinnerUnionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.not_selected,android.R.layout.simple_spinner_item);
                    spinnerUnionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unionNameS.setAdapter(spinnerUnionAdapter);
                }
                //thanaNameS = (Spinner) inflatedView.findViewById(R.id.thanaName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        unionNameS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //unionNameS = (Spinner) inflatedView.findViewById(R.id.unionName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        savePeopleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullAddress = addressET.getText().toString()+", "+unionNameS.getSelectedItem()+", "+thanaNameS.getSelectedItem()+", "+district;
                if(vipPersonCB.isChecked()){
                    vipPerson=1;
                }
                else{
                    vipPerson=0;
                }
                addPeopleToDb();
            }
        });
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        //calendar = Calendar.getInstance(Locale.getDefault());
        currentDate = dateFormat.format(date);
        /*year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);*/

        return inflatedView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(getContext());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ivImage.setImageBitmap(thumbnail);
        Bitmap bm = BitmapFactory.decodeFile(destination.getAbsolutePath());
        ivImage.setImageBitmap(bm);
        showImagepathTV.setText(destination.getAbsolutePath());
        //imagePath = destination.getAbsolutePath();
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bm1 = BitmapFactory.decodeFile(destination.getAbsolutePath());
        ivImage.setImageBitmap(bm1);
        // String selectedImageUri = data.getData().getPath();
        showImagepathTV.setText(destination.getAbsolutePath());
    }


    private void addPeopleToDb() {
        String fullname         =   fullNameET.getText().toString();
        String fatherName         =   fatherNameET.getText().toString();
        String details         =   detailsET.getText().toString();
        String phone        =   phoneET.getText().toString();
        String address = fullAddress;
        String dateNow = currentDate;
        userPhotoPath    =   showImagepathTV.getText().toString();

        //String userName = "yousuf";
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
                this.profileModel =   new ProfileModel(vipPerson,fullname,fatherName,phone,address,details,dateNow,userPhotoPath);
                Integer status  =   yarDatabaseSource.updatePeople(this.profileModel, userId);
                if(status==00) {
                    errorMsgTV.setText("This People already have duplicate.");
                    //Toast.makeText(getContext(), "This user  name already exits", Toast.LENGTH_SHORT).show();
                }else if(status==1){
                    errorMsgTV.setText("People Updated sucessfully.");
                    startActivity(new Intent(getContext(),MainActivity.class)
                            .putExtra("userName",userName));
                    // Toast.makeText(getContext(), "User added sucessfully", Toast.LENGTH_SHORT).show();
                   /* startActivity(new Intent(this,ActivityInternal.class)
                            .putExtra("userName",user));*/
                }else{
                    errorMsgTV.setText("Error! Couldn't Update!!!");
                    //Toast.makeText(getContext(), "Could not save", Toast.LENGTH_SHORT).show();
                }
                // end update
            }
            else{
                //Log.d("TEST", "btnSignUpProcess: "+imagePath);
                this.profileModel =   new ProfileModel(vipPerson,fullname,fatherName,phone,address,details,dateNow,userPhotoPath);
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
