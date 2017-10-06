package com.example.selvip.employeedetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    Boolean doubleBackToExitPressedOnce = false;
    private Button mAdminRegister, mAdminEdit, mAdminSignout;
    private ListView mAddedUsers;
    private ArrayList<String> mUsersName = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private DatabaseReference rootRef;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mAdminRegister = (Button) findViewById(R.id.adminRegister);
        mAdminEdit = (Button) findViewById(R.id.adminEdit);
        mAdminSignout = (Button) findViewById(R.id.adminSignout);
        mAddedUsers = (ListView) findViewById(R.id.addedUsers);
        dialog=new ProgressDialog(AdminActivity.this);

        arrayAdapter = new ArrayAdapter<String>(AdminActivity.this, android.R.layout.simple_list_item_1, mUsersName);
        mAddedUsers.setAdapter(arrayAdapter);

        rootRef = FirebaseDatabase.getInstance().getReference().child("User Info").child("Users Name");

        dialog.setMessage("Setting up things... Please wait!");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
       dialog.show();

        rootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                mUsersName.add(value);
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                dialog.dismiss();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                dialog.dismiss();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
            }
        });

        mAdminRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, MainActivity.class));
            }
        });

        mAdminEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, DetailsEditActivity.class));
            }
        });

        mAdminSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminActivity.this, "You are logged out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminActivity.this, ChooseActivity.class));
                finish();
            }
        });

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
