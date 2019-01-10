package com.example.shahed.yararch;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SignUpActivity extends AppCompatActivity {
    private EditText personFullNameET;
    private EditText prsonUsernameET;
    private EditText passwordET;
    private LoginModel loginModel;
    private TextView errorMsgTV;
    private YarDatabaseSource yarDatabaseSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        personFullNameET    = (EditText) findViewById(R.id.sign_up_full_name);
        prsonUsernameET     = (EditText) findViewById(R.id.sign_up_username);
        passwordET          = (EditText) findViewById(R.id.sign_up_password);
        errorMsgTV          = (TextView) findViewById(R.id.sign_up_usertype);
        yarDatabaseSource =new YarDatabaseSource(this);
    }

    public void save_user(View view) {
        String name         =   personFullNameET.getText().toString();
        String user         =   prsonUsernameET.getText().toString();
        String pass         =   passwordET.getText().toString();
        Integer type         = 2;
        if(name.isEmpty()){
            personFullNameET.setError("This field must not be Empty !");
        }
        if(user.isEmpty()){
            prsonUsernameET.setError("This field must not be Empty !");
        }

        if(pass.isEmpty()){
            passwordET.setError("This field must not be Empty !");
        }
        else {
            loginModel =   new LoginModel(name,user,pass,type);
            //Toast.makeText(this, name+" ,"+user+", "+type, Toast.LENGTH_SHORT).show();
            //errorMsgTV.setText(name+", "+type);
            Integer status  =   yarDatabaseSource.addSignUp(loginModel);
            if(status==00) {
                errorMsgTV.setText("This user  name already exits");
                errorMsgTV.setTextColor(Color.RED);
                //Toast.makeText(this, "This user  name already exits", Toast.LENGTH_SHORT).show();
            }else if(status==1){
                //Toast.makeText(this, "User added sucessfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class)
                        .putExtra("userName",user));
            }else{
               // Toast.makeText(this, "Could not save", Toast.LENGTH_SHORT).show();
                errorMsgTV.setText("Error !!! Unsuccessful Attempt !");
                errorMsgTV.setTextColor(Color.RED);
            }

        }
    }
}
