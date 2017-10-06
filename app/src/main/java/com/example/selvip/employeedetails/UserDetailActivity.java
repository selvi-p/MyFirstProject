package com.example.selvip.employeedetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity {

    ArrayAdapter<String> mAdapter;
    private EditText mEmpName, mLastName, mEmpMobile, mEmpId;
    private TextView mEmpReport;
    private Button mDoneBtn;
    private Spinner mSpinner;
    private TextView mTextView6;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef, demoRef;
    private FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        mEmpName = (EditText) findViewById(R.id.empName);
        mLastName = (EditText) findViewById(R.id.lastName);
        mEmpMobile = (EditText) findViewById(R.id.empMobile);
        mEmpId = (EditText) findViewById(R.id.empId);
        mEmpReport = (TextView) findViewById(R.id.empReport);
        mDoneBtn = (Button) findViewById(R.id.doneBtn);
        mTextView6 = (TextView) findViewById(R.id.textView6);
        mSpinner = (Spinner) findViewById(R.id.spinner);


        mTextView6.setText(getIntent().getExtras().getString("Email"));

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String UserId = user.getUid();

        mProgress = new ProgressDialog(UserDetailActivity.this);

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("User Info").child("Data").child(UserId);

        final List<String> users = new ArrayList<String>();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        rootRef.child("User Info").child("Names").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array


                for (com.google.firebase.database.DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String userName = areaSnapshot.child("User Name").getValue(String.class);
                    users.add(userName);
                }

                ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(UserDetailActivity.this, android.R.layout.simple_spinner_item, users);
                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(mAdapter);
                users.add(0, "Admin");
                mSpinner.setSelection(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                        mEmpReport.setText(adapterView.getItemAtPosition(position).toString());
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mEmpName.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });

        mLastName.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });


        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String User_Name = mEmpName.getText().toString();
                String Last_Name = mLastName.getText().toString();
                String pattern1 = "^[7-9]{1}[0-9]{9}$";
                String Mobile_No = mEmpMobile.getText().toString().trim();
                String pattern2 = "^[E]{1}[0-9]{4}$";
                String Emp_ID = mEmpId.getText().toString().trim();
                String Emp_report = mEmpReport.getText().toString().trim();
                String EmailID = mTextView6.getText().toString();


                if (mEmpName.getText().length() == 0) {
                    mEmpName.setError("Field cannot be empty");

                } else if (mLastName.getText().length() == 0) {
                    mLastName.setError("Field cannot be empty");

                } else if (mEmpMobile.getText().length() == 0) {
                    mEmpMobile.setError("Field cannot be empty");

                } else if (mEmpMobile.getText().length()<10) {
                    Toast.makeText(UserDetailActivity.this, "Please enter 10-digit mobile number", Toast.LENGTH_SHORT).show();

                } else if (!Mobile_No.matches(pattern1)) {
                    mEmpMobile.setError("Please enter valid mobile number");

                } else if (mEmpId.getText().length() == 0) {
                    mEmpId.setError("Field cannot be empty");

                } else if (!Emp_ID.matches(pattern2)) {
                    mEmpId.setError("Employee ID should be of this format: E0123");

                } else if (mEmpReport.getText().length() == 0) {
                    mEmpReport.setError("Field cannot be empty");

                } else {

                    demoRef.child("User Name").setValue(User_Name);
                    rootRef.child("User Info").child("Users Name").child(UserId).setValue(User_Name);
                    rootRef.child("User Info").child("Names").child(UserId).child("User Name").setValue(User_Name);
                    rootRef.child("User Info").child("Users Email").child(UserId).child("Email").setValue(EmailID);
                    demoRef.child("Mobile").setValue(Mobile_No);
                    demoRef.child("Employee ID").setValue(Emp_ID);
                    demoRef.child("Last Name").setValue(Last_Name);
                    demoRef.child("Reporting to").setValue(Emp_report);

                    Toast.makeText(UserDetailActivity.this, "Details saved!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserDetailActivity.this, AdminActivity.class));
                    finish();
                }
            }
        });


    }

}
