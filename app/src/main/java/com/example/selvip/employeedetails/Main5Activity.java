package com.example.selvip.employeedetails;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main5Activity extends AppCompatActivity {

    private ListView mMessageView2;
    private TextView mToEmail;

    private ArrayList<String> mMessages = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        mMessageView2 = (ListView) findViewById(R.id.messageView2);
        mToEmail = (TextView) findViewById(R.id.toRepEmail);
        arrayAdapter = new ArrayAdapter<String>(Main5Activity.this, android.R.layout.simple_list_item_1, mMessages);
        mMessageView2.setAdapter(arrayAdapter);

        rootRef = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mToEmail.setText(getIntent().getExtras().getString("ToUserName"));
        String userName = mToEmail.getText().toString();


                rootRef.child("User Info").child("Data").child("Messages").child(userName).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        String value = dataSnapshot.getValue(String.class);
                        mMessages.add(value);
                        arrayAdapter.insert(value, 0);
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                });

    }
}
