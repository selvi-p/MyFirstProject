package com.example.selvip.employeedetails;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Main4Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference rootRef;

    private TextView mProfileView, mUserEdit, mEmpEdit, mReportEdit, mMobileEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        mProfileView = (TextView) findViewById(R.id.profileView);
        mUserEdit = (TextView) findViewById(R.id.userEdit);
        mEmpEdit = (TextView) findViewById(R.id.empEdit);
        mReportEdit = (TextView) findViewById(R.id.reportEdit);
        mMobileEdit = (TextView) findViewById(R.id.mobileEdit);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String UserId = user.getUid();

        rootRef = FirebaseDatabase.getInstance().getReference("User Info").child("Data").child(UserId);

        rootRef.child("User Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String UserName = dataSnapshot.getValue(String.class);
                mUserEdit.setText(UserName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        rootRef.child("Employee ID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String EmpID = dataSnapshot.getValue(String.class);
                mEmpEdit.setText(EmpID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        rootRef.child("Reporting to").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String reportTo = dataSnapshot.getValue(String.class);
                mReportEdit.setText(reportTo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        rootRef.child("Mobile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mobile = dataSnapshot.getValue(String.class);
                mMobileEdit.setText(mobile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mAuth = FirebaseAuth.getInstance();

        mProfileView.setText(user.getEmail());

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Main4Activity.this, Main2Activity.class));
                    finish();
                }
            }
        };


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
}


