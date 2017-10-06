package com.example.selvip.employeedetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Main3Activity extends AppCompatActivity {

    private static final String TAG = "ViewDatabase";
    Boolean doubleBackToExitPressedOnce = false;
    private Button mViewBtn, mViewInbox, mSentItem, mLogout;
    private ImageButton send;
    private String UserId;
    private TextView mWelcome, mReportingTo, mToTextView, mDate;
    private EditText message;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference rootRef, demoRef;
    private FirebaseDatabase mFirebaseDatabase;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mReportingTo = (TextView) findViewById(R.id.reportingTo);
        mWelcome = (TextView) findViewById(R.id.welcome);
        mToTextView = (TextView) findViewById(R.id.toTextView);
        mDate = (TextView) findViewById(R.id.date);
        message = (EditText) findViewById(R.id.message);
        send = (ImageButton) findViewById(R.id.send);
        mViewInbox = (Button) findViewById(R.id.viewInbox);
        mViewBtn = (Button) findViewById(R.id.viewBtn);
        mLogout = (Button) findViewById(R.id.logout);
        mSentItem = (Button) findViewById(R.id.sentItem);
        dialog=new ProgressDialog(Main3Activity.this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserId = user.getUid();

        rootRef = FirebaseDatabase.getInstance().getReference("User Info").child("Data").child(UserId);
        demoRef = rootRef.child("Messages");

        dialog.setMessage("Setting up things... Please wait!");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        rootRef.child("Reporting to").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String TL_Name = dataSnapshot.getValue(String.class);
                mReportingTo.setText(" " + TL_Name);
                mToTextView.setText(" " + TL_Name);
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
            }
        });

        rootRef.child("User Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Emp_Name = dataSnapshot.getValue(String.class);
                mWelcome.setText(" " + Emp_Name);
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
            }
        });


        rootRef = mFirebaseDatabase.getReference();

        mViewInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName = mWelcome.getText().toString();
                Intent intent = new Intent(Main3Activity.this, Main5Activity.class);
                intent.putExtra("ToUserName", UserName);
                startActivity(intent);
            }
        });

        mSentItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main3Activity.this, SentActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };

        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();

        DateFormat formatter = new SimpleDateFormat("hh:mm a\nEE dd-MMM-yy");
        String formattedDateString = formatter.format(currentDate);
        mDate.setText(formattedDateString);

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userName = mWelcome.getText().toString();
                String toEmail = mToTextView.getText().toString();
                String Message = message.getText().toString();
                String dateTime = mDate.getText().toString();
                if (Message.length() == 0) {

                    Toast.makeText(Main3Activity.this, "Cannot send empty message", Toast.LENGTH_SHORT).show();
                } else {

                    rootRef.child("User Info").child("Data").child("Messages").child(toEmail).push().setValue("\n" + userName + ": " + Message + "\n\n" + dateTime + "\n");
                    demoRef.push().setValue("\n" + Message + "\n\n" + dateTime + "\n" + "To: " + toEmail);
                    Toast.makeText(Main3Activity.this, "Message sent!", Toast.LENGTH_LONG).show();
                    message.setText("");


                }
            }
        });


        mViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main3Activity.this, Main4Activity.class);
                startActivity(i);
            }
        });


        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Main3Activity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}
