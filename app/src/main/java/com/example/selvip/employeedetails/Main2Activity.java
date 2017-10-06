package com.example.selvip.employeedetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Main2Activity extends AppCompatActivity {

    private EditText mLoginEmail, mPasscode2;
    private Button mLogin;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mLoginEmail = (EditText) findViewById(R.id.loginEmail);
        mPasscode2 = (EditText) findViewById(R.id.passcode2);
        mLogin = (Button) findViewById(R.id.login);
        mProgress = new ProgressDialog(Main2Activity.this);

        mAuth = FirebaseAuth.getInstance();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mLoginEmail.getText().toString();
                String pattern = "^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+$";
                String passwordLogin = mPasscode2.getText().toString();

                if (mLoginEmail.getText().length() == 0) {
                    mLoginEmail.setError("Field cannot be empty");
                } else if (!email.matches(pattern)) {
                    Toast.makeText(Main2Activity.this, "Email ID is invalid", Toast.LENGTH_SHORT).show();
                } else if (mPasscode2.getText().length() == 0) {
                    mPasscode2.setError("Field cannot be empty");
                } else {

                    mProgress.setMessage("Logging in... Please wait!");
                    mProgress.setCancelable(false);
                    mProgress.setInverseBackgroundForced(false);
                    mProgress.show();

                    mAuth.signInWithEmailAndPassword(email, passwordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgress.dismiss();

                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                                startActivity(intent);
                                finish();
                            }


                            if (!task.isSuccessful()) {
                                Toast.makeText(Main2Activity.this.getApplicationContext(), "Authentication failed!", Toast.LENGTH_LONG).show();
                                mProgress.dismiss();

                            }
                        }

                    });
                }
            }
        });

    }

}
