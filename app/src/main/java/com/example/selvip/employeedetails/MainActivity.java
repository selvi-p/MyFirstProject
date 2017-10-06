package com.example.selvip.employeedetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private EditText email, passcode1;
    private Button register;

    private ProgressDialog mProgress;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference rootRef, demoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.email);
        passcode1 = (EditText) findViewById(R.id.passcode1);
        register = (Button) findViewById(R.id.register);

        mProgress = new ProgressDialog(MainActivity.this);

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("User Info").child("Users").push();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pattern = "^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+$";
                String Email_ID = email.getText().toString().trim();
                String Pass_code = passcode1.getText().toString().trim();


                if (email.getText().length() == 0) {
                    email.setError("Field cannot be empty");
                } else if (!Email_ID.matches(pattern)) {
                    Toast.makeText(MainActivity.this, "Please enter valid Email ID", Toast.LENGTH_SHORT).show();
                } else if (passcode1.getText().length() == 0) {
                    passcode1.setError("Field cannot be empty");
                } else if (passcode1.getText().length() < 6) {
                    Toast.makeText(MainActivity.this, "Please enter a 6-digit passcode", Toast.LENGTH_SHORT).show();
                } else {

                    mProgress.setMessage("Registering... Please wait!");
                    mProgress.setCancelable(false);
                    mProgress.setInverseBackgroundForced(false);
                    mProgress.show();

                    mAuth.createUserWithEmailAndPassword(Email_ID, Pass_code).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgress.dismiss();

                            if (task.isSuccessful()) {

                                String Email_ID = email.getText().toString().trim();
                                String Pass_code = passcode1.getText().toString().trim();
                                String User_key = demoRef.getKey();

                                demoRef.child("Email ID").setValue(Email_ID);
                                demoRef.child("Passcode").setValue(Pass_code);
                                demoRef.child("User Key").setValue(User_key);

                                Toast.makeText(MainActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, AdminEmpLogin.class);
                                intent.putExtra("Email1", mAuth.getCurrentUser().getEmail());
                                intent.putExtra("Passcode", Pass_code);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }

            }
        });


    }

}


