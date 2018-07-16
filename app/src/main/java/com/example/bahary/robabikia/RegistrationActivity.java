
package com.example.bahary.robabikia;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bahary.robabikia.utils.Constants;
import com.example.bahary.robabikia.utils.Helper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity {


    @BindView(R.id.Login_Email)
    EditText email;
    @BindView(R.id.Login_Button)
    Button go;
    String useremail = null;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressDialog dialog;

    @Override
    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Hawk.init(this).build();
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        //mAuth=FirebaseAuth.getInstance();


        if (Hawk.contains(Constants.mEmail_Key)) {
            email.setText(Hawk.get(Constants.mEmail_Key) + "");
        }
        if (Hawk.contains(Constants.loginflag)) {
            if (Hawk.get(Constants.loginflag).equals("1")) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("Loging..");
                dialog.setMessage("it may take few seconds..");


                dialog.show();
                useremail = email.getText().toString();
                if (useremail.equals("")) {
                    Helper.printText(RegistrationActivity.this, "please enter your email");

                } else {
                    Hawk.put(Constants.mEmail_Key, useremail);
                    mAuth.signInWithEmailAndPassword(useremail, "12345678"
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Helper.printText(RegistrationActivity.this, "Welcome Again" + useremail);
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                dialog.dismiss();
                                //if flag=="0" there is no internet connection
                                Hawk.put(Constants.loginflag, "1");
                                Hawk.put(Constants.UserID,mAuth.getCurrentUser().getUid());


                            } else {

                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    Helper.printText(RegistrationActivity.this, "Please write Valid Email");
                                    dialog.dismiss();


                                } catch (Exception e) {
                                    Helper.printText(RegistrationActivity.this, "Weak internet Connection");
                                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    dialog.dismiss();
                                    //if flag=="0" there is no internet connection
                                    Hawk.put(Constants.loginflag, "0");
                                }

                                mAuth.createUserWithEmailAndPassword(useremail, "12345678")
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    Helper.printText(RegistrationActivity.this, "Welcome first time" + useremail);
                                                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    dialog.dismiss();
                                                    //if flag=="1" there is internet connection
                                                    Hawk.put(Constants.UserID,mAuth.getCurrentUser().getUid());
                                                    Hawk.put(Constants.loginflag, "1");


                                                } else {

                                                }
                                            }
                                        });


                            }
                        }
                    });
                }
            }


        });
        dialog.dismiss();

    }


}

                    /*
                    Hawk.put(Constants.mEmail_Key, useremail);

                    mAuth.createUserWithEmailAndPassword(useremail, "12345678").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Helper.printText(getApplicationContext(), "Task complete" + useremail);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                mAuth.signInWithEmailAndPassword(useremail, "12345678");
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });*/
