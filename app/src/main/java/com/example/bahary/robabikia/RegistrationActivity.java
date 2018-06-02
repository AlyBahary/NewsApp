package com.example.bahary.robabikia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bahary.robabikia.utils.Constants;
import com.example.bahary.robabikia.utils.Helper;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity {


     @BindView(R.id.Login_Email) EditText email;
     @BindView(R.id.Login_Button) Button go;
    String useremail=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Hawk.init(this).build();
        ButterKnife.bind(this);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useremail=email.getText().toString();
                if(useremail.equals("")){
                    Helper.printText(getApplicationContext(),"please enter your email");
                }
                else{
                    Hawk.put(Constants.mEmail_Key,useremail);
                    Helper.printText(getApplicationContext(),"Task complete"+useremail);
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


}
